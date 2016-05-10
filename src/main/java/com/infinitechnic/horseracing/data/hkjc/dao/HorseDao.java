package com.infinitechnic.horseracing.data.hkjc.dao;


import com.infinitechnic.horseracing.data.hkjc.entity.Horse;

public interface HorseDao {
    Horse getHorseById(String id);
}
