package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.horse.HorseDao;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SaveAllHorseProfilesImpl implements SaveAllHorseProfiles {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtractAllHorseIds extractAllHorseIdsService;

    @Autowired
    private ExtractHorseProfile extractHorseProfileService;

    @Autowired
    private HorseDao horseDao;

    @Override
    public List<Horse> render() throws ServiceRenderException {
        List<Horse> horses = new ArrayList<>();
        try {
            List<String> ids = extractAllHorseIdsService.render();
            for (String id : ids) {
                System.out.println(id);
                horses.add(horseDao.saveHorse(extractHorseProfileService.render(id)));
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
        return horses;
    }
}
