package com.infinitechnic.horseracing.data.hkjc.service.race.history.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.race.RaceDao;
import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Deprecated
//@Service
public class SaveRaceResultImpl implements SaveRaceResult {
    @Autowired
    private ExtractRaceResult extractor;

    @Autowired
    private RaceDao dao;

    @Override
    public RaceResult render(Date raceDate, String venue, Integer raceNumber) throws ServiceRenderException, ServiceFailureException {
        RaceResult raceResult = extractor.render(raceDate, venue, raceNumber);
        dao.saveRaceResult(raceResult);
        return raceResult;
    }
}
