package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.race.RaceDayDao;
import com.infinitechnic.horseracing.data.hkjc.entity.Status;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceEntry;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExtractEntryImpl implements ExtractEntry {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_HREF = "^javascript:ajaxNav\\('(.+)'\\)$";
    private static final Pattern PATTERN_HREF = Pattern.compile(REGEXP_HREF);

    private static final String REGEXP_HORSE = "^<a href=\"(.+)\">(.+)\\((.+)\\)</a>$";
    private static final Pattern PATTERN_HORSE = Pattern.compile(REGEXP_HORSE);

    @Autowired
    private RaceDayDao raceDayDao;

    @Override
    public RaceDay render(Date date, String venue) throws ServiceRenderException {
        if (date == null) {
            throw new ServiceRenderException(String.format("Date is null!"));
        }
        if (StringUtil.isEmpty(venue)) {
            throw new ServiceRenderException(String.format("Venue is null!"));
        }

        RaceDay raceDay = null;
        try {
            String dateStr = DateUtil.format(date, "yyyyMMdd");
            Document doc = Jsoup.connect(String.format("http://racing.hkjc.com/racing/Info/Meeting/Entries/English/Local/%s/%s/Overview", dateStr, venue)).get();
            Elements raceElements = doc.select("li[class=font13] a");
            Assert.isTrue(raceElements.size() > 0);

            raceDay = raceDayDao.findRaceDay(date);
            if (raceDay == null) {
                raceDay = new RaceDay(dateStr, venue);
            } else {
                raceDay.getRaces().clear();
            }

            for (int i = 0; i < raceElements.size(); i++) {
                Matcher lineMatcher = PATTERN_HREF.matcher(raceElements.get(i).attr("href"));
                Assert.isTrue(lineMatcher.matches());

                logger.info(String.format("http://racing.hkjc.com%s", lineMatcher.group(1)));
//                raceDay.getRaces().add(extractRace(raceDay, Jsoup.connect(String.format("http://racing.hkjc.com%s", lineMatcher.group(1))).get()));
                Race race = extractRace(raceDay, Jsoup.connect(String.format("http://racing.hkjc.com%s", lineMatcher.group(1))).get());
                raceDay.getRaces().add(race);
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
        return raceDay;
    }

    private Race extractRace(RaceDay raceDay, Document raceEntryDoc) throws ServiceFailureException {
        Race race = parseRace(raceDay, raceEntryDoc);
        parseRaceEntryTable(race, raceEntryDoc);
        return race;
    }

    private Race parseRace(RaceDay raceDay, Document raceEntryDoc) throws ServiceFailureException {
        // Race detail
        Elements entryDetailElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv5] ul[class=ulDiv] li");

        //TODO: get from RaceDay?
        Race race = new Race();

        Map<String, String> elementMap = new HashMap<String, String>();
        int listSize = entryDetailElements.size();
        for (int i=1; i<listSize; i++) {
            System.out.println(i + " - " + entryDetailElements.get(i).text());
            if (i % 2 == 0) {
                String val = entryDetailElements.get(i).text().replace(Character.toString ((char) 160), "").trim();
                switch (i/2) {
                    case 1:
                        race.setTrack(val);
                    case 2:
                        raceDay.setMeetingNo(NumberUtil.parseInteger(val));
                    case 3:
                        race.setCourse(val);
                    case 4:
                        race.setName(val);
                    case 5:
                        race.setDistance(NumberUtil.parseInteger(val.substring(0, val.length()-1)));
                    case 6:
                        race.setRaceClass(val);
                    case 7:
                        race.setSection(NumberUtil.parseInteger(val));
                    case 8:
                        race.setRatingRange(val);
                }
            }
        }
        return race;
    }

    private Race parseRaceEntryTable(Race race, Document raceEntryDoc) throws ServiceFailureException {
        // Normal entries
        Elements entryTableElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv10] table");
        Assert.isTrue(entryTableElements.size() == 1);
        entryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
            race.getRaceEntries().add(parseEntryRow(tr, Status.SELECT));
        });

        // Reserve entries
        Elements reserveEntryTableElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv5] table");
        if (reserveEntryTableElements.size() == 1) {
            reserveEntryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
                race.getRaceEntries().add(parseEntryRow(tr, Status.RESERVE));
            });
        }
        return race;
    }

    private RaceEntry parseEntryRow(Element entryRowElement, Status status) throws ServiceFailureException {
        Elements tdElements = entryRowElement.select("td");
        RaceEntry raceEntry = new RaceEntry();
        raceEntry.setEntryStatus(status);
        int idx = 0;
        if (Status.RESERVE.equals(status)) {
            raceEntry.setNo(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        }
        raceEntry.setHorse(extractHorse(tdElements.get(idx++).html()));
        raceEntry.setTrainer(extractTrainer(tdElements.get(idx++).html()));
        raceEntry.setWeight(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        raceEntry.setWeightForAge(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        raceEntry.setRating(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        raceEntry.setRatingDifference(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        raceEntry.setPriority(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        raceEntry.setRemarks(tdElements.get(idx++).html());
        return raceEntry;
    }

    private Horse extractHorse(String value) throws ServiceFailureException {
        if (StringUtil.isEmpty(value)) {
            throw new ServiceFailureException("Trainer value is null!"); //TODO: error code
        }

        Horse horse = new Horse();
        Matcher horseMatcher = PATTERN_HORSE.matcher(value);
        if (horseMatcher.matches()) {
            horse.setName(horseMatcher.group(2).trim());
            horse.setId(horseMatcher.group(3).trim());
        } else {
            throw new ServiceFailureException("Cannot match horse"); //TODO: error code
        }
        return horse;
    }

    public Trainer extractTrainer(String value) throws ServiceFailureException {
        if (StringUtil.isEmpty(value)) {
            throw new ServiceFailureException("Trainer value is null!"); //TODO: error code
        }

        Trainer trainer = new Trainer();
        //TODO: change to static later
        String lineRegExp = "^<a href=\"(.+)\">(.+)</a>$";
        String hrefRegExp = "^.+TrainerCode=(.+)$";
        Pattern linePattern = Pattern.compile(lineRegExp);
        Pattern hrefPattern = Pattern.compile(hrefRegExp);
        Matcher lineMatcher = linePattern.matcher(value);

        if (lineMatcher.matches()) {
            trainer.setName(lineMatcher.group(2));
            Matcher hrefMatcher = hrefPattern.matcher(lineMatcher.group(1));
            if(hrefMatcher.matches()) {
                trainer.setId(hrefMatcher.group(1));
            }
        } else {
            throw new ServiceFailureException("Cannot match trainer"); //TODO: error code
        }

        return trainer;
    }
}
