package com.infinitechnic.horseracing.data.hkjc.service.race.fixture.calendar.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

@Deprecated
public class ExtractRaceCalendarTest extends BaseTest {
    @Autowired
    private ExtractRaceCalendar service;

    @Test
    public void test() {
        try {
            service.render(2016, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
