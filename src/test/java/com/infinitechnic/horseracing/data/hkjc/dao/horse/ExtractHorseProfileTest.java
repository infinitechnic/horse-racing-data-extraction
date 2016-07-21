package com.infinitechnic.horseracing.data.hkjc.dao.horse;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.service.horse.biz.ExtractHorseProfile;
import com.infinitechnic.horseracing.data.hkjc.service.race.biz.ExtractEntry;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtractHorseProfileTest extends BaseTest {
    @Autowired
    private ExtractHorseProfile service;

    @Test
    public void test() throws Exception {
        Horse horse = service.render("N011");
        //Assert.assertNotNull(raceDay);
    }
}
