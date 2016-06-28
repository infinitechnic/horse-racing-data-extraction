package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.List;
import java.util.TimeZone;

public interface ExtractRaceCalendar {
    List<RaceDay> render(Integer year, Integer month) throws ServiceRenderException, ServiceFailureException;
}
