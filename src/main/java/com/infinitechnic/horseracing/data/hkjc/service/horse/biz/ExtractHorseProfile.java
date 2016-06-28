package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;

public interface ExtractHorseProfile {
    Horse render(String url);
}
