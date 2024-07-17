package pl.coderslab.charity.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.UserRepository;
import pl.coderslab.charity.service.AdminService;

import java.util.ArrayList;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/charity/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;

    @GetMapping("/create-start")
    public String createBasicInstitution(){
        adminService.createBasicInstitution();
        adminService.createBasicCategory();
        adminService.createRole();
        adminService.createBasicAdmin();
        return "redirect:/";
    }

    @GetMapping("")
    public String panelAdmin(Model model) {
        model.addAttribute("userList",userRepository.findAll());
        return "admin/adminPanel";
    }

    @GetMapping("/category")
    public String getCategory(Model model) {
        model.addAttribute("categoryList",categoryRepository.findAll());
        return "admin/category";
    }

    @GetMapping("/institution")
    public String getInstitution(Model model) {
        model.addAttribute("institutionList",institutionRepository.findAll());
        return "admin/institution";
    }

    @GetMapping("/donation")
    public String getDonation(Model model) {
        model.addAttribute("donationList", donationRepository.findAll());
        return "admin/donation";
    }

}
