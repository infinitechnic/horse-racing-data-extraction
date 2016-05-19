package com.infinitechnic.horseracing.data.hkjc.service.race.fixture.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.fixture.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.List;

public interface RetrieveRaceCalendar {
    List<RaceDay> render(Integer year, Integer month) throws ServiceRenderException, ServiceFailureException;
}
