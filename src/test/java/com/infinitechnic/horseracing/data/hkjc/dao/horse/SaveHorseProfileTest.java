package com.infinitechnic.horseracing.data.hkjc.dao.horse;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.service.horse.biz.ExtractHorseProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SaveHorseProfileTest extends BaseTest {
    @Autowired
    private ExtractHorseProfile service;

    @Autowired
    private HorseDao dao;

    @Test
    public void test() throws Exception {
        Horse horse = service.render("N011");
        dao.saveHorse(horse);
        Assert.assertNotNull(horse);
    }
}
