package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.race.RaceDayDao;
import com.infinitechnic.horseracing.data.hkjc.entity.Status;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceEntry;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import com.infinitechnic.util.DateUtil;
import com.infinitechnic.util.NumberUtil;
import com.infinitechnic.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExtractRaceCardImpl implements ExtractRaceCard {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_RACE_NAME = "^<span class=\"bold\">Race ([0-9]{1,2})&nbsp;-&nbsp;(.+)</span>$";
    private static final Pattern PATTERN_RACE_NAME = Pattern.compile(REGEXP_RACE_NAME);

    @Autowired
    private RaceDayDao raceDayDao;

    @Override
    public RaceDay render(Date date, String venue) throws ServiceRenderException {
        if (date == null) {
            throw new ServiceRenderException("Date is null!");
        }
        if (StringUtil.isEmpty(venue)) {
            throw new ServiceRenderException("Venue is null!");
        }

        RaceDay raceDay = null;
        try {
            raceDay = raceDayDao.findRaceDay(date);
            if (raceDay == null) {
                throw new ServiceRenderException("Race Entry is empty!");
            }

            Map<String, Race> raceMap = new HashMap<>();
            for (Race race : raceDay.getRaces().getModels()) {
                raceMap.put(race.getBusinessKey(), race);
            }

            String dateStr = DateUtil.format(date, "yyyyMMdd");
            int totalRace = raceDay.getRaces().size();
            for (int i=1; i<=totalRace; i++) {
                int retryCount = 0;
                boolean success = false;

                Document doc = Jsoup.connect(String.format("http://racing.hkjc.com/racing/Info/Meeting/RaceCard/English/Local/%s/%s/%d", dateStr, venue, i)).get();
                while (!success && retryCount < 5) {
                    try {
                        parseRace(raceMap, doc);
                        success = true;
                    } catch (Exception e) {
                        retryCount++;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
        return raceDay;
    }

    private Race parseRace(Map<String, Race> raceMap, Document raceCardDoc) throws ServiceFailureException {
        Elements entryDetailElements = raceCardDoc.select("table[class=font13 lineH20 tdAlignL] td");
        Assert.isTrue(entryDetailElements.size() == 1);
        String[] values = entryDetailElements.get(0).html().split("<br>");

        Matcher raceNameMatcher = PATTERN_RACE_NAME.matcher(values[0].trim());
        Assert.isTrue(raceNameMatcher.matches());

        Integer raceNumber = NumberUtil.parseInteger(raceNameMatcher.group(1));
        String name = raceNameMatcher.group(2);

        Integer distance = NumberUtil.parseInteger(values[2].split(",")[2].trim().replace("M", ""));

        int lastIndex = values[3].lastIndexOf(",");
        String raceClass = values[3].substring(lastIndex+1, values[3].length()).trim();

        Integer prizeMoney = NumberUtil.parseInteger(values[3].substring(0, values[3].substring(0, lastIndex-1).lastIndexOf(",")).replace("Prize Money: $", ""));

        String bizKey = Race.getBusinessKey(name, raceClass, distance);
        Race race = raceMap.get(bizKey);
        Assert.notNull(race);
        race.setRaceNo(raceNumber);
        race.setPrizeMoney(prizeMoney);

        parseRaceEntryTable(race, raceCardDoc);

        return race;
    }

    private Race parseRaceEntryTable(Race race, Document raceCardDoc) throws ServiceFailureException {
        Map<String, RaceEntry> raceEntryMap = new HashMap<>();
        for (RaceEntry raceEntry : race.getRaceEntries().getModels()) {
            raceEntryMap.put(raceEntry.getHorse().getId(), raceEntry);
        }

        // Normal entries
        Elements entryTableElements = raceCardDoc.select("div[class=rowDiv10] table[class=draggable hiddenable]");
        Assert.isTrue(entryTableElements.size() == 1);
        entryTableElements.select("tbody tr.trBgGrey1,tr.trBgWhite").stream().forEach(tr -> {
            parseSelectEntryRow(tr, raceEntryMap);
        });

        // Reserve entries
        Elements reserveEntryTableElements = raceCardDoc.select("div[class=rowDiv10] table[class=tableBorderBlue]");
        if (reserveEntryTableElements.size() == 1) {
            reserveEntryTableElements.select("tbody tr.trBgGrey1,tr.trBgWhite").stream().forEach(tr -> {
                parseReserveEntryRow(tr, raceEntryMap);
            });
        }

        // Set DropOff for remaining entries
        raceEntryMap.values().stream().forEach(raceEntry -> {
            raceEntry.setFinalStatus(Status.DROPOFF);
        });

        return race;
    }

    private RaceEntry parseSelectEntryRow(Element entryRowElement, Map<String, RaceEntry> raceEntryMap) throws ServiceFailureException {
        Elements tdElements = entryRowElement.select("td");

        RaceEntry raceEntry = raceEntryMap.remove(tdElements.get(4).text());
        Assert.notNull(raceEntry);
        raceEntry.setNo(NumberUtil.parseInteger(tdElements.get(0).text()));
        raceEntry.setWeight(NumberUtil.parseInteger(tdElements.get(5).text()));
        raceEntry.setDraw(NumberUtil.parseInteger(tdElements.get(8).text()));
        raceEntry.setRating(NumberUtil.parseInteger(tdElements.get(10).text()));
        raceEntry.setRatingDifference(NumberUtil.parseInteger(tdElements.get(11).text().replace("+", "")));
        raceEntry.setHorseWeight(NumberUtil.parseInteger(tdElements.get(12).text()));
        raceEntry.setHorseWeightDifference(NumberUtil.parseInteger(tdElements.get(13).text().replace("+", "")));
        raceEntry.setBestTime(tdElements.get(14).text());
        raceEntry.setGear(tdElements.get(20).text());
        raceEntry.setFinalStatus(Status.SELECT);

        return raceEntry;
    }

    private RaceEntry parseReserveEntryRow(Element entryRowElement, Map<String, RaceEntry> raceEntryMap) throws ServiceFailureException {
        Elements tdElements = entryRowElement.select("td");

        String horseLink = tdElements.get(1).html();
        int equalIndex = horseLink.indexOf("=", 10);
        int quotIndex = horseLink.indexOf("'", equalIndex+1);
        String horseId = horseLink.substring(equalIndex+1, quotIndex);

        RaceEntry raceEntry = raceEntryMap.remove(horseId);
        Assert.notNull(raceEntry);
        raceEntry.setHorseWeight(NumberUtil.parseInteger(tdElements.get(2).text()));
        raceEntry.setWeight(NumberUtil.parseInteger(tdElements.get(3).text()));
        raceEntry.setGear(tdElements.get(9).text());
        raceEntry.setFinalStatus(Status.RESERVE);

        return raceEntry;
    }
}
