package com.infinitechnic.horseracing.data.hkjc.dao.race;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractRaceCalendar;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FindRaceDayTest extends BaseTest {
    @Autowired
    private RaceDayDao dao;

    @Test
    public void test() throws Exception {
        RaceDay raceDay = dao.findRaceDay(DateUtil.parseDate("20160518", "yyyyMMdd"));
        Assert.assertNotNull(raceDay);
    }
}
