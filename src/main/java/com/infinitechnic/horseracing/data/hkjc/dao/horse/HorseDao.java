package com.infinitechnic.horseracing.data.hkjc.dao.horse;


import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;

public interface HorseDao {
    Horse getHorseById(String id);

    Horse saveHorse(Horse horse);
}
