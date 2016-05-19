package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.jockey.Jockey;
import com.infinitechnic.horseracing.data.hkjc.entity.race.history.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.history.Result;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import com.infinitechnic.util.DateUtil;
import com.infinitechnic.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ExtractRaceHistoryImpl implements ExtractRaceHistory {
    @Override
    public Race render(Date raceDate, String venue, Integer raceNumber) throws ServiceRenderException, ServiceFailureException {
        Race race = null;
        try {
            Document doc = Jsoup.connect(StringUtil.concat("http://racing.hkjc.com/racing/Info/Meeting/Results/English/Local/", DateUtil.format(raceDate, "yyyyMMdd"), "/", venue, "/", StringUtil.toString(raceNumber))).get();
            Elements elements = doc.select("div.clearDivFloat.rowDiv15 table.draggable");
            Assert.isTrue(elements.size() == 1);
            if (elements.size() == 1) {
                parseHtml(race = new Race(raceDate, raceNumber), elements.get(0));
            }
        } catch (IOException ioe) {
            throw new ServiceFailureException(ioe);
        }
        return race;
    }

    private void parseHtml(Race race, Element element) {
        element.select("tbody tr.trBgGrey,tr.trBgWhite").forEach(tr -> {
            Result result = toResult(tr);
            if (result != null) {
                race.getResults().add(result);
            }
        });
    }

    private Result toResult(Element element) {
        Elements elements = element.select("td[nowrap]");
        Result result = null;
        if (elements.size() == 12) {
            String place = elements.get(0).html();
            String horseNo = elements.get(1).html();
            // Horse
            //        System.out.println(elements.get(2).html());
            Horse horse = extractHorseProfile(elements.get(2));
            String horseId = horse.getId();
            String horseName = horse.getName();
            // Jockey
            //        System.out.println(elements.get(3).html());
            Jockey jockey = extractJockeyProfile(elements.get(3));
            String jockeyId = jockey.getId();
            String jockeyName = jockey.getName();
            // Trainer
            //        System.out.println(elements.get(4).html());
            Trainer trainer = extractTrainerProfile(elements.get(4));
            String trainerId = trainer.getId();
            String trainerName = trainer.getName();
            String actualWeight = elements.get(5).html();
            String horseWeight = elements.get(6).html();
            String draw = elements.get(7).html();
            String lbw = elements.get(8).html();
            List<Integer> runningPositions = extractRunningPositions(elements.get(9));
            String finishTime = elements.get(10).html();
            String winOdds = elements.get(11).html();

            result = new Result();
            result.setPlace(place);
            result.setHorseNo(horseNo);
            result.setHorse(horse);
            result.setJockey(jockey);
            result.setTrainer(trainer);
            result.setActualWeight(actualWeight);
            result.setHorseWeight(horseWeight);
            result.setDraw(draw);
            result.setLbw(lbw);
            result.getRunningPositions().addAll(runningPositions);
            result.setFinishTime(finishTime);
            result.setWinOdds(winOdds);
        }
        return result;
/*
        System.out.println("---------------------------------");
        System.out.println("place: " + place);
        System.out.println("horseNo: " + horseNo);
        System.out.println("horseId: " + horseId);
        System.out.println("horseName: " + horseName);
        System.out.println("jockeyId: " + jockeyId);
        System.out.println("jockeyName: " + jockeyName);
        System.out.println("trainerId: " + trainerId);
        System.out.println("trainerName: " + trainerName);
        System.out.println("actualWeight: " + actualWeight);
        System.out.println("horseWeight: " + horseWeight);
        System.out.println("draw: " + draw);
        System.out.println("lbw: " + lbw);
        System.out.println("runningPositions: " + runningPositions);
        System.out.println("finishTime: " + finishTime);
        System.out.println("winOdds: " + winOdds);
*/
    }

    public Horse extractHorseProfile(Element element) {
        //TODO: Retrieve if exist else create a new one
        Horse horse = new Horse();

        String lineRegExp = "^<a href=\"(.+)\">(.+)</a>\\((.+)\\)$";
        Pattern linePattern = Pattern.compile(lineRegExp);
        //Matcher lineMatcher = pattern.matcher("<a href=\"http://www.hkjc.com/english/racing/horse.asp?horseno=P227\">FUNNY FORTUNE</a>(P227)");
        Matcher lineMatcher = linePattern.matcher(element.html());

//        Assert.assertTrue(lineMatcher.matches());

        if (lineMatcher.matches()) {
            //System.out.println(matcher.group(1));
            horse.setName(lineMatcher.group(2));
            horse.setId(lineMatcher.group(3));
        }

        return horse;
    }

    public Jockey extractJockeyProfile(Element element) {
        //TODO: Retrieve if exist else create a new one
        Jockey jockey = new Jockey();

        String lineRegExp = "^<a href=\"(.+)\">(.+)</a>$";
        String hrefRegExp = "^.+jockeycode=(.+)&.+$";
        Pattern linePattern = Pattern.compile(lineRegExp);
        Pattern hrefPattern = Pattern.compile(hrefRegExp);
        Matcher lineMatcher = linePattern.matcher(element.html());

//        Assert.assertTrue(lineMatcher.matches());

        if (lineMatcher.matches()) {
            jockey.setName(lineMatcher.group(2));

            Matcher hrefMatcher = hrefPattern.matcher(lineMatcher.group(1));

//            Assert.assertTrue(hrefMatcher.matches());

            if(hrefMatcher.matches()) {
                jockey.setId(hrefMatcher.group(1));
            }
        }

        return jockey;
    }

    public Trainer extractTrainerProfile(Element element) {
        //TODO: Retrieve if exist else create a new one
        Trainer trainer = new Trainer();

        String lineRegExp = "^<a href=\"(.+)\">(.+)</a>$";
        String hrefRegExp = "^.+trainercode=(.+)&.+$";
        Pattern linePattern = Pattern.compile(lineRegExp);
        Pattern hrefPattern = Pattern.compile(hrefRegExp);
        Matcher lineMatcher = linePattern.matcher(element.html());

//        Assert.assertTrue(lineMatcher.matches());

        if (lineMatcher.matches()) {
            trainer.setName(lineMatcher.group(2));

            Matcher hrefMatcher = hrefPattern.matcher(lineMatcher.group(1));

//            Assert.assertTrue(hrefMatcher.matches());

            if(hrefMatcher.matches()) {
                trainer.setId(hrefMatcher.group(1));
            }
        }

        return trainer;
    }

    private List<Integer> extractRunningPositions(Element element) {
        return element.select("tr td").stream().map(td -> Integer.parseInt(td.html())).collect(Collectors.toList());
    }
}
