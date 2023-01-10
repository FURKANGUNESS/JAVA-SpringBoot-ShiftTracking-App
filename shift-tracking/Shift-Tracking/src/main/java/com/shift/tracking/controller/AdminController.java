package com.shift.tracking.controller;

import com.shift.tracking.entity.Shift;
import com.shift.tracking.entity.User;
import com.shift.tracking.request.CreateShiftForm;
import com.shift.tracking.request.ShiftForm;
import com.shift.tracking.service.ShiftService;
import com.shift.tracking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;
    private final ShiftService shiftService;

    public AdminController(UserService userService, ShiftService shiftService) {
        this.userService = userService;
        this.shiftService = shiftService;
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        //kullanicilari listeleyen sayfayi acar,
        //tüm kullanicilarin oldugu listeyi -> userService.getAllUsers() sonucunu doner
        model.addAttribute("createShiftForm", new CreateShiftForm());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }


    @GetMapping("/user/{id}")
    public String getUserShifts(@PathVariable Long id, Model model) {
        //id ile secilmis kullanicinin vardiya bilgilerini doner.

        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("shifts", user.getShifts());
        ShiftForm shiftForm = new ShiftForm();
        shiftForm.setShifts(user.getShifts());
        model.addAttribute("form", shiftForm);


        return "admin/user-shift-list";
    }


    @PostMapping("/user/{id}/add-shift")
    public String addShiftFormPage(@PathVariable Long id, Model model, CreateShiftForm form) {
        //id path variable' ı ile belirtilen user_id dir. Bu userId ye sahip kullanici icin,
        // formda secildigi sayida vardiya nesnesi olusturulur.
        User user = userService.findById(id);
        model.addAttribute("user", user);

        List<Shift> shifts = new ArrayList<>();
        for (int i = 0; i < form.getSize(); i++) {
            shifts.add(new Shift());
        }

        ShiftForm shiftForm = new ShiftForm();
        shiftForm.setShifts(shifts);
        shiftForm.setUserId(id);
        model.addAttribute("form", shiftForm);
        return "admin/add-shift";
    }


    @PostMapping("/user/create-shift")
    public String addShift(ShiftForm form, Model model) {
        //formdan gonderilen shift verileri, User' in uzerine eklenir.

        User user = userService.findById(form.getUserId());
        List<Shift> userShifts = user.getShifts();
        if (userShifts == null){//kullanicinin onceden bir shift verisi yoksa nullpointer almamasi icin kontrol
            userShifts = new ArrayList<>();
        }

        userShifts.addAll(form.getShifts());
        //eklenen verilerle birlikte user db ye kaydedilir.
         userService.save(user);
        model.addAttribute("shifts", user.getShifts());
        return "redirect:/admin/user/" + user.getId();
    }


    @GetMapping("/update/shift/{id}")
    public String updateShiftForm(@PathVariable Long id, Model model) {
        //admin sectigi shifti guncellemesi icin, ilgili shifte ait bilgileri dondurur
        model.addAttribute("shift", shiftService.findById(id));
        return "admin/shift-update-form";
    }

    @PostMapping("/update/shift")
    public String updateShift(Shift shift) {
        //adminin guncelledigi shift kaydedilir.
        shiftService.save(shift);
        //admini, shift' i guncelledigi kullanicinin tüm vardiya bilgilerinin bulundugu ekrana yonlendirilir.
        return "redirect:/admin/user/" + shift.getEmployee().getId();
    }
}
