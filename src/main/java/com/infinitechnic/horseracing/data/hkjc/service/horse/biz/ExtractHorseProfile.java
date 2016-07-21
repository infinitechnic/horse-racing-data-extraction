package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;

public interface ExtractHorseProfile {
    Horse render(String id) throws ServiceRenderException;
}
