package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

import java.util.List;

public interface SaveAllHorseProfiles {
    List<Horse> render() throws ServiceRenderException;
}
