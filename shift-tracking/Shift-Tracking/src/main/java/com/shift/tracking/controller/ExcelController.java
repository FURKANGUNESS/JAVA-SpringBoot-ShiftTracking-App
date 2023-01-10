package com.shift.tracking.controller;

import com.shift.tracking.request.ShiftForm;
import com.shift.tracking.util.ExcelView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/excel")
public class ExcelController {


    @PostMapping
    public ModelAndView downloadExcel(Model model, ShiftForm form) {
        //Buradaki request e gore ExcelView class ı calisir.
        return new ModelAndView(new ExcelView(), "shift ", form.getShifts());
    }
}
