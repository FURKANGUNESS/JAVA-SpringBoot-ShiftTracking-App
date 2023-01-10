package com.shift.tracking.controller;

import com.shift.tracking.entity.User;
import com.shift.tracking.request.RegisterForm;
import com.shift.tracking.service.DepartmentService;
import com.shift.tracking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final DepartmentService departmentService;


    public RegisterController(UserService userService, DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }


    @GetMapping
    public String showRegistrationForm(Model model) {
        //register sayfasini acar, departman bilgilerini db den alarak kullaniciya secim yaptirilir.
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") RegisterForm registerForm,
                                      BindingResult result, HttpServletRequest request) {
        //Kayit ol formu post edildiginde, kayit olma islemleri yapilir.
        User existing = userService.findByEmail(registerForm.getEmail());
        if (existing != null) {//formda gonderilen maile ait bir user var ise hata verilir.
            result.rejectValue("email", "", "There is already an account registered with that email");
        }

        if (result.hasErrors()) {//herhangi bir hata varsa register sayfasi tekrar acilir.
            return "redirect:/register?error";
        }

        userService.save(registerForm);//herhangi bir hata yok ise formdaki bilgiler ile kullanicinin kayit olma islemleri baslar.
        return "redirect:/login";//login sayfasina yonlendirme yapar.
    }

}
