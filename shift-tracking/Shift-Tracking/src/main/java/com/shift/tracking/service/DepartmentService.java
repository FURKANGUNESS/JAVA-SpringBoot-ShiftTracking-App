package com.shift.tracking.service;

import com.shift.tracking.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);
}
