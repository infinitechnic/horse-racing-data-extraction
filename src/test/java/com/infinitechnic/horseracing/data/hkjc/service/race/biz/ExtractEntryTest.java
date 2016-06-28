package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExtractEntryTest extends BaseTest {
    @Autowired
    private ExtractEntry service;

    @Test
    public void test() throws Exception {
        RaceDay raceDay = service.render(DateUtil.parseDate("20160518", "yyyyMMdd"), "HV");
        Assert.assertNotNull(raceDay);
    }
}
