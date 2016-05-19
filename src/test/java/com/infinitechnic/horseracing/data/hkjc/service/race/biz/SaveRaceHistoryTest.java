package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class SaveRaceHistoryTest extends BaseTest {
    @Autowired
    private SaveRaceHistory service;

    @Test
    public void test() {
        try {
            Date date = DateUtil.parseDate("20160427", "yyyyMMdd");
            Assert.assertNotNull(service.render(date, "HV", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
