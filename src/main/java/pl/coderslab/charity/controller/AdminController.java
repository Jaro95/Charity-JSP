package pl.coderslab.charity.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.service.AdminService;


@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/create-start")
    public String createBasicInstitution(){
        adminService.createBasicInstitution();
        adminService.createBasicCategory();
        adminService.createRole();
        adminService.createBasicAdmin();
        return "redirect:/";
    }

}
