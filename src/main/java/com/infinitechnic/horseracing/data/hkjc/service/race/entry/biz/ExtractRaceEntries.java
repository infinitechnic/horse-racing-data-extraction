package com.infinitechnic.horseracing.data.hkjc.service.race.entry.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.race.entry.RaceEntry;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.Date;
import java.util.List;

@Deprecated
public interface ExtractRaceEntries {
    List<RaceEntry> render(Date date) throws ServiceRenderException, ServiceFailureException;
}
