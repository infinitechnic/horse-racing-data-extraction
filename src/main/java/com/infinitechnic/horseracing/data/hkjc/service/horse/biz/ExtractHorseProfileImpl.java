package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.horse.HorseDao;
import com.infinitechnic.horseracing.data.hkjc.dao.race.RaceDayDao;
import com.infinitechnic.horseracing.data.hkjc.entity.Status;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Record;
import com.infinitechnic.horseracing.data.hkjc.entity.jockey.Jockey;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceEntry;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractEntry;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExtractHorseProfileImpl implements ExtractHorseProfile {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_RACE_INDEX = "^<a href=\"results.asp\\?racedate=([0-9]{2}/[0-9]{2}/[0-9]{4})\\&amp;raceno=([0-9]{2})\\&amp;venue=([A-Z]{2})\" class=\"htable_eng_text\">(.+)</a>$";
    private static final Pattern PATTERN_RACE_INDEX = Pattern.compile(REGEXP_RACE_INDEX);

    private static final String REGEXP_TRAINER = "^<a href=\"TrainerWinStat.asp\\?trainercode=(.+)\\&amp;season=Current\" class=\"htable_eng_text\">(.+)</a>$";
    private static final Pattern PATTERN_TRAINER = Pattern.compile(REGEXP_TRAINER);

    private static final String REGEXP_JOCKEY = "^<a href=\"JockeyProfile.asp\\?JockeyCode=(.+)\\&amp;season=Current\" class=\"htable_eng_text\">(.+)</a>$";
    private static final Pattern PATTERN_JOCKEY = Pattern.compile(REGEXP_JOCKEY);

    @Autowired
    private HorseDao horseDao;

    @Override
    public Horse render(String id) throws ServiceRenderException {
        if (StringUtil.isEmpty(id)) {
            throw new ServiceRenderException(String.format("Horse ID is null!"));
        }

        int retryCount = 0;
        boolean success = false;
        Horse horse = null;
        while (!success && retryCount < 5) {
            try {
                Document doc = Jsoup.connect(String.format("http://www.hkjc.com/english/racing/horse.asp?HorseNo=%s&Option=1", id)).get();

                // Horse Detail
                horse = extractHorseDetail(id, doc);

                // Record
                Elements recordTables = doc.select("table[class=bigborder]");
                if (recordTables.size() > 0) {
                    Assert.isTrue(recordTables.size() > 0);
                    //                System.out.println(recordTables.size());
                    Elements recordElements = recordTables.get(0).select("tbody tr[bgcolor=#F8F4EF],tr[bgcolor=#E7E4DF]");
                    Iterator<Element> recordElementIt = recordElements.iterator();
                    while (recordElementIt.hasNext()) {
                        Element tr = recordElementIt.next();
                        //race.getRaceEntries().add(parseEntryRow(tr, Status.SELECT));
                        updateRecord(horse, tr);
                    }
                }
                success = true;
            } catch (Exception e) {
                success = false;
                retryCount++;
            }
        }
        return horse;
    }

    private Horse extractHorseDetail(String id, Document doc) {
        String name = doc.select("td[class=subsubheader]").text().replace(id, "").replace("()", "").replace(Character.toString ((char) 160), "").trim();
        Horse horse = horseDao.getHorseById(id);
        if (horse == null) {
            horse = new Horse();
            horse.setId(id);
        }
        horse.setName(name);
        return horse;
    }

    private void updateRecord(Horse horse, Element elementRow) throws ServiceFailureException {
        try {
            Record record = horse.getRecords().add(new Record());

            Elements cells = elementRow.select("td");
            int size = cells.size();
            for (int i = 0; i < size; i++) {
                String value = cells.get(i).text();
                switch (i) {
                    case 0: // Race Index
                        Matcher riMatcher = PATTERN_RACE_INDEX.matcher(value);
                        if (riMatcher.matches()) {
                            //record.setRaceIndex();
                            record.setRaceDate(DateUtil.parseDate(riMatcher.group(1), "dd/MM/yyyy"));
                            record.setRaceNo(NumberUtil.parseInteger(riMatcher.group(2)));
                            record.setVenue(riMatcher.group(3));
                            record.setRaceIndex(riMatcher.group(4));
                        } else {
                            record.setRaceIndex(value);
                        }
                        break;
                    case 1: // Placing
                        record.setPlacing(NumberUtil.parseInteger(value));
                        break;
                    case 2: // Date
                        record.setRaceDate(DateUtil.parseDate(value, "dd/MM/yy"));
                        break;
                    case 3: // Race Course/Track/Course
                        String[] values = value.split("/");
                        if (values.length == 3) {
                            if (StringUtil.isEmpty(record.getVenue())) {
                                record.setVenue(values[0].trim());
                            }
                            record.setTrack(values[1].replace("\"", "").trim());
                            record.setCourse(values[2].replace("\"", "").trim());
                        } else {
                            record.setVenue(values[0].trim());
                            record.setTrack(values[1].replace("\"", "").trim());
                        }
                        break;
                    case 4: // Distance
                        record.setDistance(NumberUtil.parseInteger(value));
                        break;
                    case 5: // Going
                        record.setGoing(value);
                        break;
                    case 6: // Race Class
                        record.setRaceClass(value);
                        break;
                    case 7: // Draw
                        record.setDraw(NumberUtil.parseInteger(value));
                        break;
                    case 8: // Rating
                        record.setRating(NumberUtil.parseInteger(value));
                        break;
                    case 9: // Trainer
                        Trainer trainer = new Trainer();
                        Matcher trainerMatcher = PATTERN_TRAINER.matcher(cells.get(i).html());
                        if (trainerMatcher.matches()) {
                            trainer.setId(trainerMatcher.group(1).trim());
                            trainer.setName(trainerMatcher.group(2).trim());
                        } else {
                            trainer.setName(value);
                        }
                        record.setTrainer(trainer);
                        break;
                    case 10: // Jockey
                        Jockey jockey = new Jockey();
                        Matcher jockeyMatcher = PATTERN_JOCKEY.matcher(cells.get(i).html());
                        if (jockeyMatcher.matches()) {
                            jockey.setId(jockeyMatcher.group(1).trim());
                            jockey.setName(jockeyMatcher.group(2).trim());
                        } else {
                            jockey.setName(value);
                        }
                        record.setJockey(jockey);
                        break;
                    case 11: // Length Behind Winner
                        record.setLengthBehindWinner(value);
                        break;
                    case 12: // Win Odds
                        if (NumberUtil.isNumber(value)) {
                            record.setWinOdds(new Double(value));
                        }
                        break;
                    case 13: // Actual Weight
                        record.setActualWeight(NumberUtil.parseInteger(value));
                        break;
                    case 14: // Running Position
//                        value = cells.get(i).text();
                        if (!"--".equals(value)) {
                            record.setRunningPositions(Arrays.stream(value.split("  ")).map(Integer::parseInt).toArray(Integer[]::new));
                        }
                        break;
                    case 15: // Finish Time
                        record.setFinishTime(value);
                        break;
                    case 16: // On Date Horse Rate
                        if (NumberUtil.isNumber(value)) {
                            record.setDeclaredHorseWeight(NumberUtil.parseInteger(value));
                        }
                        break;
                    case 17: // Gear
                        record.setGear(value);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
    }
}
