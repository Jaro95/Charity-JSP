package pl.coderslab.charity.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.model.Institution;
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

    @GetMapping("/donation")
    public String getDonation(Model model) {
        model.addAttribute("donationList", donationRepository.findAll());
        return "admin/donation";
    }

    @GetMapping("/institution")
    public String getInstitution(@RequestParam(required = false) Long updateId ,Model model) {
        if(updateId != null) {
            model.addAttribute("updateId",updateId);
        }
        model.addAttribute("institutionList",institutionRepository.findAll());
        return "admin/institution";
    }

    @PostMapping("/institution")
    public String postInstitution(@RequestParam long institutionId, @RequestParam String institutionName,
                                  @RequestParam String institutionDescription,
                                  Model model,
                                  RedirectAttributes redirectAttributes ) {
        Institution institution = institutionRepository.findById(institutionId).get();
        institution.setName(institutionName);
        institution.setDescription(institutionDescription);
        institutionRepository.save(institution);
        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/admin/institution";
    }

    @GetMapping("/institution/add")
    public String getAddInstitution(Model model) {
        model.addAttribute("institution", new Institution());
        return "admin/addInstitution";
    }

    @PostMapping("/institution/add")
    public String getAddInstitution(@Valid Institution institution, BindingResult result,
                                    Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            model.addAttribute("institution", institution);
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("messageError", "Napotkano błędy w formularzu");
            return "admin/addInstitution";
        }
        institutionRepository.save(institution);
        redirectAttributes.addFlashAttribute("message", "Dodano fundację");
        return "redirect:/charity/admin/institution/add";
    }

    @GetMapping("/institution/delete")
    public String getAddInstitution(@RequestParam Long deleteId,RedirectAttributes redirectAttributes) {
        institutionRepository.deleteById(deleteId);
        redirectAttributes.addFlashAttribute("message", "Fundacja usunięta pomyślnie");
        return "redirect:/charity/admin/institution";
    }

}
