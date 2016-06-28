package com.infinitechnic.horseracing.data.hkjc.service.race.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.Race;
import com.infinitechnic.horseracing.data.hkjc.entity.race.RaceDay;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.Date;
import java.util.List;

public interface ExtractEntry {
    RaceDay render(Date date, String venue) throws ServiceRenderException;
}
