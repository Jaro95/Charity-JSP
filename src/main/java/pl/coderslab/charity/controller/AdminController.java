package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InstitutionRepository institutionRepository;

    public AdminController(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @RequestMapping("/basicInstitution")
    public String createBasicInstitution(){
        List<Institution> basic = List.of(
                new Institution("Dbam o Zdrowie","Pomoc dzieciom z ubogich rodzin."),
                new Institution("A kogo","Pomoc wybudzaniu dzieci ze śpiączki"),
                new Institution("Dla dzieci","Pomoc osobom znajdującym się w trudnej sytuacji życiowej."),
                new Institution("Bez domu","Pomoc dla osób nie posiadających miejsca zamieszkania")
        );
        institutionRepository.saveAll(basic);
        return "redirect:/";
    }
}
