package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtractRaceCardTest extends BaseTest {
    @Autowired
    private ExtractRaceCard service;

    @Test
    public void test() throws Exception {
        RaceDay raceDay = service.render(DateUtil.parseDate("20160518", "yyyyMMdd"), "HV");
        Assert.assertNotNull(raceDay);
    }
}
