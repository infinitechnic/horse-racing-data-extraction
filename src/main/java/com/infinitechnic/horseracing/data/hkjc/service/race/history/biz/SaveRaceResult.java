package com.infinitechnic.horseracing.data.hkjc.service.race.history.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.history.RaceResult;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.Date;

@Deprecated
public interface SaveRaceResult {
    RaceResult render(Date raceDate, String venue, Integer raceNumber) throws ServiceRenderException, ServiceFailureException;
}
