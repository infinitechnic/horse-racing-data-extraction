package com.infinitechnic.horseracing.data.hkjc.service.race.entry.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.EntryRecord;
import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.RaceEntry;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import com.infinitechnic.util.DateUtil;
import com.infinitechnic.util.NumberUtil;
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

@Service
public class RetrieveRaceEntriesImpl implements RetrieveRaceEntries {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_HREF = "^javascript:ajaxNav\\('(.+)'\\)$";
    private static final Pattern PATTERN_HREF = Pattern.compile(REGEXP_HREF);

    private static final String REGEXP_HORSE = "^<a href=\"(.+)\">(.+)</a>\\((.+)\\)$";
    private static final Pattern PATTERN_HORSE = Pattern.compile(REGEXP_HORSE);

    @Override
    public List<RaceEntry> render(Date date) throws ServiceRenderException, ServiceFailureException {
        List<RaceEntry> raceEntries = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format("http://racing.hkjc.com/racing/Info/Meeting/Entries/English/Local/%s/HV/Overview", DateUtil.format(date, "yyyyMMdd"))).get();
            Elements raceElements = doc.select("li[class=font13] a");
            Assert.isTrue(raceElements.size() > 0);
            for (int i = 0; i < raceElements.size(); i++) {
                Matcher lineMatcher = PATTERN_HREF.matcher(raceElements.get(i).attr("href"));
                Assert.isTrue(lineMatcher.matches());
                raceEntries.add(parseEntryTable(date, i + 1, Jsoup.connect(String.format("http://racing.hkjc.com%s", lineMatcher.group(1))).get()));
            }
        } catch (Exception ioe) {
            throw new ServiceFailureException(ioe);
        }
        return raceEntries;
    }

    private RaceEntry parseEntryTable(Date date, Integer raceNo, Document entryDoc) {
        RaceEntry raceEntry = new RaceEntry(date, raceNo);

        // Normal entries
        Elements entryTableElements = entryDoc.select("div[id=entries] div[class=rowDiv10] table");
        Assert.isTrue(entryTableElements.size() == 1);
        entryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
            Elements tdElements = tr.select("td");
            EntryRecord entryRecord = new EntryRecord();
            entryRecord.setReserved(Boolean.FALSE);
            entryRecord.setHorse(extractHorse(tdElements.get(0).toString()));
            entryRecord.setTrainer(extractTrainer(tdElements.get(1).toString()));
            entryRecord.setWeight(NumberUtil.parseInteger(tdElements.get(2).toString()));
            entryRecord.setWeightForAge(NumberUtil.parseInteger(tdElements.get(3).toString()));
            entryRecord.setRating(NumberUtil.parseInteger(tdElements.get(4).toString()));
            entryRecord.setRatingDifference(NumberUtil.parseInteger(tdElements.get(5).toString()));
            entryRecord.setPriority(NumberUtil.parseInteger(tdElements.get(6).toString()));
            entryRecord.setRemarks(tdElements.get(7).toString());
            raceEntry.getEntryRecords().add(entryRecord);
        });

        // Reserve entries
        Elements reserveEntryTableElements = entryDoc.select("div[id=entries] div[class=rowDiv5] table");
        if (reserveEntryTableElements.size() == 1) {
            reserveEntryTableElements.select("tbody tr.trBgGrey,tr.trBgWhite").stream().forEach(tr -> {
                Elements tdElements = tr.select("td");
                EntryRecord entryRecord = new EntryRecord();
                entryRecord.setReserved(Boolean.TRUE);
                entryRecord.setNo(NumberUtil.parseInteger(tdElements.get(0).toString()));
                entryRecord.setHorse(extractHorse(tdElements.get(1).toString()));
                entryRecord.setTrainer(extractTrainer(tdElements.get(2).toString()));
                entryRecord.setWeight(NumberUtil.parseInteger(tdElements.get(3).toString()));
                entryRecord.setWeightForAge(NumberUtil.parseInteger(tdElements.get(4).toString()));
                entryRecord.setRating(NumberUtil.parseInteger(tdElements.get(5).toString()));
                entryRecord.setRatingDifference(NumberUtil.parseInteger(tdElements.get(6).toString()));
                entryRecord.setPriority(NumberUtil.parseInteger(tdElements.get(7).toString()));
                entryRecord.setRemarks(tdElements.get(8).toString());
                raceEntry.getEntryRecords().add(entryRecord);
            });
        }
        return raceEntry;
    }

    private Horse extractHorse(String value) {
        Horse horse = new Horse();
        Matcher horseMatcher = PATTERN_HORSE.matcher(value);
        if (horseMatcher.matches()) {
            horse.setName(horseMatcher.group(2));
            horse.setId(horseMatcher.group(3));
        }
        return horse;
    }

    public Trainer extractTrainer(String value) {
        Trainer trainer = new Trainer();

        String lineRegExp = "^<a href=\"(.+)\">(.+)</a>$";
        String hrefRegExp = "^.+trainercode=(.+)&.+$";
        Pattern linePattern = Pattern.compile(lineRegExp);
        Pattern hrefPattern = Pattern.compile(hrefRegExp);
        Matcher lineMatcher = linePattern.matcher(value);

        if (lineMatcher.matches()) {
            trainer.setName(lineMatcher.group(2));
            Matcher hrefMatcher = hrefPattern.matcher(lineMatcher.group(1));
            if(hrefMatcher.matches()) {
                trainer.setId(hrefMatcher.group(1));
            }
        }

        return trainer;
    }
}
