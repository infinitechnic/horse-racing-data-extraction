package com.infinitechnic.horseracing.data.hkjc.service.race.entry.biz;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.RaceEntry;
import com.infinitechnic.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

@Deprecated
public class ExtractEntryTest extends BaseTest {
    @Autowired
    private ExtractRaceEntries service;

    @Test
    public void test() {
        try {
            List<RaceEntry> raceEntries = service.render(DateUtil.parseDate("20160518", "yyyyMMdd"));
            raceEntries.stream().forEach(re -> {
                System.out.println(re);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
