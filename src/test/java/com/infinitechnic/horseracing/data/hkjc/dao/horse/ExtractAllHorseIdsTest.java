package com.infinitechnic.horseracing.data.hkjc.dao.horse;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.service.horse.biz.ExtractAllHorseIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

public class ExtractAllHorseIdsTest extends BaseTest {
    @Autowired
    private ExtractAllHorseIds service;

    @Test
    public void test() throws Exception {
        List<String> ids = service.render();
//        Assert.assertNotNull(horse);
        System.out.println("Total: " + ids.size());
    }
}
