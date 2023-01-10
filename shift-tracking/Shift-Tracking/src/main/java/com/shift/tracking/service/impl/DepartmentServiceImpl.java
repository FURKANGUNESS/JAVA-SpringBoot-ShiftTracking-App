package com.shift.tracking.service.impl;

import com.shift.tracking.entity.Department;
import com.shift.tracking.exception.WebServiceException;
import com.shift.tracking.repository.DepartmentRepository;
import com.shift.tracking.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        //kayitli tum departman bilgilerini liste halinde dondurur.
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        //parametre olarak verilen id' ye sahip departman var ise dondurur, yoksa hata firlatir.
        return departmentRepository.findById(id).orElseThrow(() -> new WebServiceException("Department not found"));
    }
}
