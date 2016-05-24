package com.infinitechnic.horseracing.data.hkjc.service.race.fixture.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.fixture.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.entity.race.fixture.RaceInfo;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RetrieveRaceCalendarImpl implements RetrieveRaceCalendar {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_IMAGE = "^<img src=\"images/(.+).gif\".+>$";
    private static final Pattern PATTERN_IMAGE = Pattern.compile(REGEXP_IMAGE);

    private static final String REGEXP_IMAGE_CLASS = "^<img src=\"images/class_(.+).gif\".+>$";
    private static final Pattern PATTERN_IMAGE_CLASS = Pattern.compile(REGEXP_IMAGE_CLASS);

    private static final String REGEXP_RACE_INFO = "^([0-9]+)\\(([0-9]+)\\)(.*)$";
    private static final Pattern PATTERN_RACE_INFO = Pattern.compile(REGEXP_RACE_INFO);

    private static final String VALUE_AWT = "AWT";
    private static final String VALUE_TURF = "TURF";

    @Override
    public List<RaceDay> render(Integer year, Integer month) throws ServiceRenderException, ServiceFailureException {
        List<RaceDay> raceDays = null;
        try {
            Document doc = Jsoup.connect(String.format("http://www.hkjc.com/english/racing/Fixture.asp?calMonth=%d&calYear=%d", month, year)).get();
            Elements elements = doc.select("table[width=755][bgcolor=005297]");
            Assert.isTrue(elements.size() == 1);
            if (elements.size() == 1) {
//                System.out.println(elements.html());
                raceDays = parseCalendarTable(year, month, elements.get(0));
            }
        } catch (IOException ioe) {
            throw new ServiceFailureException(ioe);
        }
        return raceDays;
    }

    private List<RaceDay> parseCalendarTable(Integer year, Integer month, Element element) {
        List<RaceDay> raceDays = new ArrayList<>();
        element.select("tbody tr[valign=top][bgcolor=#ffffff] td[bgcolor=#ffffcc]").forEach(td -> {
            raceDays.add(toRaceDay(year, month, td));
        });
        return raceDays;
    }

    private RaceDay toRaceDay(Integer year, Integer month, Element element) {
        RaceDay raceDay = new RaceDay();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, Integer.parseInt(element.select("table tbody tr td[bgcolor=ffcc66] font[size=2][color=#009966] b").html()));
        raceDay.setDate(calendar.getTime());

        Elements info = element.select("table tbody tr td[bgcolor=ffcc66] div img");
        Assert.isTrue(info.size() == 3);

        Matcher lineMatcher = null;
        // Venue
        lineMatcher = PATTERN_IMAGE.matcher(info.get(0).toString());
        Assert.isTrue(lineMatcher.matches());
        raceDay.setVenue(lineMatcher.group(1).toUpperCase());
        // Day/Night
        lineMatcher = PATTERN_IMAGE.matcher(info.get(1).toString());
        Assert.isTrue(lineMatcher.matches());
        raceDay.setDayNight(lineMatcher.group(1).toUpperCase());
        // Track
        lineMatcher = PATTERN_IMAGE.matcher(info.get(2).toString());
        Assert.isTrue(lineMatcher.matches());
        raceDay.setTrack(lineMatcher.group(1).toUpperCase());

        Elements raceInfoElements = element.select("table tbody tr");
        int totalRow = raceInfoElements.size();
        Assert.isTrue(totalRow > 0);
        for (int i=1; i<totalRow; i++) {
            toRaceInfo(raceDay.getDate(), i, raceInfoElements.get(i));
        }
        return raceDay;
    }

    private RaceInfo toRaceInfo(Date date, Integer raceNo, Element raceInfoElement) {
        RaceInfo raceInfo = new RaceInfo(date, raceNo);

        // Race Class
        Elements classElements = raceInfoElement.select("td[valign=top] img");
        int noOfImage = classElements.size();
        Assert.isTrue(noOfImage <= 2);
        Matcher classMatcher = PATTERN_IMAGE_CLASS.matcher(classElements.get(0).toString());
        Assert.isTrue(classMatcher.matches());
        raceInfo.setRaceClass(classMatcher.group(1).toUpperCase());

        // All Weather Track
        if (noOfImage > 1) {
            // All Weather Track
            raceInfo.setTrack(VALUE_AWT);
        } else {
            raceInfo.setTrack(VALUE_TURF);
        }

        // Distance (Section) - Type
        Elements raceInfoElements = raceInfoElement.select("td[width=80%] span[class=boldtext] font");
        Assert.isTrue(raceInfoElements.size() == 1);
        Matcher distanceMatcher = PATTERN_RACE_INFO.matcher(raceInfoElements.get(0).html());
        Assert.isTrue(distanceMatcher.matches());
        raceInfo.setDistance(Integer.parseInt(distanceMatcher.group(1)));
        raceInfo.setSection(Integer.parseInt(distanceMatcher.group(2)));
        raceInfo.setType(distanceMatcher.group(3).replace("-", ""));

        // Rating Range
        Elements ratingRangeElements = raceInfoElement.select("td[width=80%] span[class=unnamed1] font");
        Assert.isTrue(ratingRangeElements.size() == 1);
        raceInfo.setRatingRange(ratingRangeElements.get(0).html());

        return raceInfo;
    }
}
