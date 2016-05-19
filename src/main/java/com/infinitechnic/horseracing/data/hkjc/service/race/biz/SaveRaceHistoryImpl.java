package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.RaceDao;
import com.infinitechnic.horseracing.data.hkjc.entity.race.history.Race;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaveRaceHistoryImpl implements SaveRaceHistory {
    @Autowired
    private ExtractRaceHistory extractor;

    @Autowired
    private RaceDao dao;

    @Override
    public Race render(Date raceDate, String venue, Integer raceNumber) throws ServiceRenderException, ServiceFailureException {
        Race race = extractor.render(raceDate, venue, raceNumber);
        dao.saveRace(race);
        return race;
    }
}
