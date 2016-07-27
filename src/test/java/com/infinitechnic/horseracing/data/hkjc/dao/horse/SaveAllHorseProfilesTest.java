package com.infinitechnic.horseracing.data.hkjc.dao.horse;

import com.infinitechnic.horseracing.data.BaseTest;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.service.horse.biz.ExtractHorseProfile;
import com.infinitechnic.horseracing.data.hkjc.service.horse.biz.SaveAllHorseProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SaveAllHorseProfilesTest extends BaseTest {
    @Autowired
    private SaveAllHorseProfiles service;

    @Test
    public void test() throws Exception {
        List<Horse> horses = service.render();
//        Assert.assertNotNull(horses);
        System.out.println(String.format("Saved %d horses", horses.size()));
    }
}
