package com.shift.tracking.service;

import com.shift.tracking.entity.Shift;

import java.util.List;

public interface ShiftService {

    List<Shift> sortCurrentUserShiftsByDate();

    Shift findById(Long id);

    Shift save(Shift shift);

    List<Shift> saveAll(List<Shift> shifts);
}
