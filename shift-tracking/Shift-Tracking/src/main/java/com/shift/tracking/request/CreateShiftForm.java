package com.shift.tracking.request;

public class CreateShiftForm {
    private int size = 0;
    private Long userId;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
