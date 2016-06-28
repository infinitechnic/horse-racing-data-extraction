package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExtractRaceCalendarTest extends BaseTest {
    @Autowired
    private ExtractRaceCalendar service;

    @Test
    public void test() throws Exception {
        List<RaceDay> raceDays = service.render(2016, 5);
        Assert.assertEquals(raceDays.size(), 8);
    }
}
