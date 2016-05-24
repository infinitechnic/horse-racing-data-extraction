package com.infinitechnic.horseracing.data.hkjc.service.race.fixture.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class RetrieveRaceCalendarTest extends BaseTest {
    @Autowired
    private RetrieveRaceCalendar service;

    @Test
    public void test() {
        try {
            service.render(2016, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
