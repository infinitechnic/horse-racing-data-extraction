package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.history.Race;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.Date;

public interface SaveRaceHistory {
    Race render(Date raceDate, String venue, Integer raceNumber) throws ServiceRenderException, ServiceFailureException;
}
