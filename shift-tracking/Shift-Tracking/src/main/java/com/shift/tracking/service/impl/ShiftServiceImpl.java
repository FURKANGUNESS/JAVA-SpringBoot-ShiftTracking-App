package com.shift.tracking.service.impl;

import com.shift.tracking.entity.Shift;
import com.shift.tracking.entity.User;
import com.shift.tracking.repository.ShiftRepository;
import com.shift.tracking.service.ShiftService;
import com.shift.tracking.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final UserService userService;
    private final ShiftRepository shiftRepository;


    public ShiftServiceImpl(UserService userService, ShiftRepository shiftRepository) {
        this.userService = userService;
        this.shiftRepository = shiftRepository;
    }


    @Override
    public List<Shift> sortCurrentUserShiftsByDate() {
        User user = userService.getCurrentUser();
        List<Shift> shifts = user.getShifts();
        //Shift entitysinin implement aldığı Comparable arayüzü (implements Comparable<Shift>) ile birlikte Collections.sort metodu,
        //Override ettigimiz compareTo metodunun donusune gore verileri sıralar.
        //Shift.75 satır.
        Collections.sort(shifts);
        return shifts;
    }

    @Override
    public Shift findById(Long id) {
        return shiftRepository.findById(id).orElse(new Shift());
    }

    @Override
    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }

    @Override
    public List<Shift> saveAll(List<Shift> shifts) {
        return shiftRepository.saveAll(shifts);
    }
}
