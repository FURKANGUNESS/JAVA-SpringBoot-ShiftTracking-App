package com.shift.tracking.request;

import com.shift.tracking.entity.Shift;

import java.util.List;

public class ShiftForm {

    List<Shift> shifts;
    Long userId;


    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
