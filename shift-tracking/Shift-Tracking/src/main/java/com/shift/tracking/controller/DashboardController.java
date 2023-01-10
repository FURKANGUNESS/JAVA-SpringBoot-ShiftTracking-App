package com.shift.tracking.controller;

import com.shift.tracking.entity.Shift;
import com.shift.tracking.request.ShiftForm;
import com.shift.tracking.service.ShiftService;
import com.shift.tracking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final UserService userService;
    private final ShiftService shiftService;

    public DashboardController(UserService userService, ShiftService shiftService) {
        this.userService = userService;
        this.shiftService = shiftService;
    }


    @GetMapping
    public String redirectDashboard() {
        if (userService.isAdminUser()) {//kullanici admin ise admin controllerina yonlendirilir.
            return "redirect:/admin/users";
        }
        return "redirect:/dashboard/employee";//admin degilse employee olarak devam eder.
    }


    @GetMapping("/employee")
    public String employeeDashboard(Model model) {
        //kullanici giris yaptiktan sonra kendi vardiya bilgilerini gormesi icin veriler model' e eklenir.
        List<Shift> shifts = shiftService.sortCurrentUserShiftsByDate();
        ShiftForm shiftForm = new ShiftForm();
        shiftForm.setShifts(shifts);


        model.addAttribute("user", userService.getCurrentUser());//session'daki user bilgileri model e eklenir.
        model.addAttribute("shifts", shifts);
        model.addAttribute("form", shiftForm);
        return "dashboard";
    }

}
