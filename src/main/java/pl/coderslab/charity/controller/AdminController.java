package pl.coderslab.charity.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.repository.*;
import pl.coderslab.charity.service.AdminService;
import pl.coderslab.charity.service.CurrentUser;
import pl.coderslab.charity.service.UserService;

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
    private final RoleRepository roleRepository;
    private final UserService userService;

    @GetMapping("/create-start")
    public String createBasicInstitution(){
        adminService.createBasicInstitution();
        adminService.createBasicCategory();
        adminService.createRole();
        adminService.createBasicAdmin();
        return "redirect:/charity";
    }

    @GetMapping("")
    public String panelAdmin(Model model) {
        model.addAttribute("userList",userRepository.findAll());
        return "admin/adminPanel";
    }
    @GetMapping("/user/update")
    public String getUpdateUser(@RequestParam Long id,Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user",user);
        model.addAttribute("userId",user.getId());
        model.addAttribute("userEmail",user.getEmail());
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("userRole",user.getRoles());
        model.addAttribute("userPassword",user.getPassword());
        return "admin/updateUser";
    }

    @PostMapping("/user/update")
    public String postUpdateUser(@Valid User user ,@RequestParam(required = false) String password,
                                 Model model, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("user",user);
            model.addAttribute("userId",user.getId());
            model.addAttribute("userEmail",user.getEmail());
            model.addAttribute("roles",roleRepository.findAll());
            model.addAttribute("userRole",user.getRoles());
            model.addAttribute("userPassword",user.getPassword());
            model.addAttribute("errors",result.getAllErrors());
            return "admin/updateUser";
        }
        if (password != null) {
            userService.updateUser(user,password);
        } else {
            userService.updateUser(user);
        }

        redirectAttributes.addFlashAttribute("message", "Edycja przebiedła pomyslnie");
        return "redirect:/charity/admin";
    }

    @GetMapping("/user/delete")
    public String getDeleteUser(@RequestParam Long id, @AuthenticationPrincipal CurrentUser currentUser,
                                RedirectAttributes redirectAttributes) {
        if(currentUser.getUser().getId() == id) {
            redirectAttributes.addFlashAttribute("messageError", "Nie możesz usunąć samego siebie");
            return "redirect:/charity/admin";
        }
        User user = userRepository.findById(id).get();
        user.getRoles().clear();
        user.getDonations().clear();
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Użytkownik usunięty pomyślnie");
        return "redirect:/charity/admin";
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(required = false) Long updateId, Model model) {
        if (updateId != null) {
            model.addAttribute("updateId", updateId);
        }
        model.addAttribute("categoryList",categoryRepository.findAll());
        return "admin/category";
    }

    @PostMapping("/category")
    public String postCategory(@RequestParam Long categoryId, @RequestParam String categoryName,
                               RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setName(categoryName);
        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("message", "Edycja przebiegła pomyślnie");
        return "redirect:/charity/admin/category";
    }

    @GetMapping("/category/add")
    public String getAddCategory(Model model) {
        model.addAttribute("category",new Category());
        return "admin/addCategory";
    }

    @PostMapping("/category/add")
    public String postAddCategory(@Valid Category category, Model model,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            model.addAttribute("category",category);
            model.addAttribute("errors",result.getAllErrors());
            model.addAttribute("messageError","Napotkano błędy w formularzu");
            return "admin/addCategory";
        }
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Kategoria dodana pomyślnie");
        return "redirect:/charity/admin/category/add";
    }

    @GetMapping("/category/delete")
    public String getDeleteCategory(@RequestParam Long deleteId,RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(deleteId);
        redirectAttributes.addFlashAttribute("message", "Kategoria usunięta pomyślnie");
        return "redirect:/charity/admin/category";
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
    public String getDeleteInstitution(@RequestParam Long deleteId,RedirectAttributes redirectAttributes) {
        institutionRepository.deleteById(deleteId);
        redirectAttributes.addFlashAttribute("message", "Fundacja usunięta pomyślnie");
        return "redirect:/charity/admin/institution";
    }

}
