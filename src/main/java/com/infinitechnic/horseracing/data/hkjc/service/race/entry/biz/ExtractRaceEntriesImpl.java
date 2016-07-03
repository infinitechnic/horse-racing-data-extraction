package com.infinitechnic.horseracing.data.hkjc.service.race.entry.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.EntryRecord;
import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.RaceEntry;
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
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
//@Service
public class ExtractRaceEntriesImpl implements ExtractRaceEntries {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_HREF = "^javascript:ajaxNav\\('(.+)'\\)$";
    private static final Pattern PATTERN_HREF = Pattern.compile(REGEXP_HREF);

    private static final String REGEXP_HORSE = "^<a href=\"(.+)\">(.+)\\((.+)\\)</a>$";
    private static final Pattern PATTERN_HORSE = Pattern.compile(REGEXP_HORSE);

    @Override
    public List<RaceEntry> render(Date date) throws ServiceRenderException, ServiceFailureException {
        if (date == null) {
            throw new ServiceRenderException("Date is null!");
        }
        List<RaceEntry> raceEntries = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format("http://racing.hkjc.com/racing/Info/Meeting/Entries/English/Local/%s/HV/Overview", DateUtil.format(date, "yyyyMMdd"))).get();
            Elements raceElements = doc.select("li[class=font13] a");
            Assert.isTrue(raceElements.size() > 0);
            for (int i = 0; i < raceElements.size(); i++) {
                Matcher lineMatcher = PATTERN_HREF.matcher(raceElements.get(i).attr("href"));
                Assert.isTrue(lineMatcher.matches());
                raceEntries.add(extractRaceEntry(date, Jsoup.connect(String.format("http://racing.hkjc.com%s", lineMatcher.group(1))).get()));
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
        return raceEntries;
    }

    private RaceEntry extractRaceEntry(Date date, Document raceEntryDoc) throws ServiceFailureException {
        RaceEntry raceEntry = parseRaceEntry(date, raceEntryDoc);
        parseEntryRecordTable(raceEntry, raceEntryDoc);
        return raceEntry;
    }

    private RaceEntry parseRaceEntry(Date date, Document raceEntryDoc) throws ServiceFailureException {
        RaceEntry raceEntry = new RaceEntry(date, null);
        // Race Entry detail
        Elements entryDetailElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv5] ul[class=ulDiv] li");
        entryDetailElements.stream().forEach(li -> {
            System.out.println(li.html());
        });
        return raceEntry;
    }

    private RaceEntry parseEntryRecordTable(RaceEntry raceEntry, Document raceEntryDoc) throws ServiceFailureException {
        // Normal entries
        Elements entryTableElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv10] table");
        Assert.isTrue(entryTableElements.size() == 1);
        entryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
            raceEntry.getEntryRecords().add(parseEntryRow(tr, false));
        });

        // Reserve entries
        Elements reserveEntryTableElements = raceEntryDoc.select("div[id=entries] div[class=rowDiv5] table");
        if (reserveEntryTableElements.size() == 1) {
            reserveEntryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
                raceEntry.getEntryRecords().add(parseEntryRow(tr, true));
            });
        }
        return raceEntry;
    }

    private EntryRecord parseEntryRow(Element entryRowElement, boolean reserve) throws ServiceFailureException {
        Elements tdElements = entryRowElement.select("td");
        EntryRecord entryRecord = new EntryRecord();
        entryRecord.setReserved(reserve);
        int idx = 0;
        if (reserve) {
            entryRecord.setNo(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        }
        entryRecord.setHorse(extractHorse(tdElements.get(idx++).html()));
        entryRecord.setTrainer(extractTrainer(tdElements.get(idx++).html()));
        entryRecord.setWeight(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        entryRecord.setWeightForAge(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        entryRecord.setRating(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        entryRecord.setRatingDifference(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        entryRecord.setPriority(NumberUtil.parseInteger(tdElements.get(idx++).html()));
        entryRecord.setRemarks(tdElements.get(idx++).html());
        return entryRecord;
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
