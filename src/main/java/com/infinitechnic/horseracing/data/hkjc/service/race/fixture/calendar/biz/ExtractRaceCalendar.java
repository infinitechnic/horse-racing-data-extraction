package com.infinitechnic.horseracing.data.hkjc.service.race.fixture.calendar.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.fixture.calendar.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.List;

@Deprecated
public interface ExtractRaceCalendar {
    List<RaceDay> render(Integer year, Integer month) throws ServiceRenderException, ServiceFailureException;
}
