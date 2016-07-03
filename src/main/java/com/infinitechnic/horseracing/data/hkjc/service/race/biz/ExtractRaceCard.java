package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.Date;
import java.util.List;

public interface ExtractRaceCard {
    RaceDay render(Date date, String venue) throws ServiceRenderException, ServiceFailureException;
}
