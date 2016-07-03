package com.infinitechnic.horseracing.data.hkjc.dao.race;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractEntry;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractRaceCard;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SaveRaceCardTest extends BaseTest {
    @Autowired
    private ExtractRaceCard service;

    @Autowired
    private RaceDayDao dao;

    @Test
    public void test() throws Exception {
        RaceDay raceDay = service.render(DateUtil.parseDate("20160518", "yyyyMMdd"), "HV");
        dao.saveRaceDay(raceDay);
        Assert.assertNotNull(raceDay);
    }
}
