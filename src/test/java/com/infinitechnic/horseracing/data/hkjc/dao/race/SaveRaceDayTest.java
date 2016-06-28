package com.infinitechnic.horseracing.data.hkjc.dao.race;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractRaceCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SaveRaceDayTest extends BaseTest {
    @Autowired
    private ExtractRaceCalendar service;

    @Autowired
    private RaceDayDao dao;

    @Test
    public void test() throws Exception {
        List<RaceDay> raceDays = service.render(2016, 5);
        raceDays.stream().forEach(raceDay -> {
            dao.saveRaceDay(raceDay);
        });
        Assert.assertEquals(raceDays.size(), 8);
    }
}
