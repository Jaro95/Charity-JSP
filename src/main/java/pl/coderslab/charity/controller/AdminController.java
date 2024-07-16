package pl.coderslab.charity.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final InstitutionRepository institutionRepository;
    private final CategoryRepository categoryRepository;

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

    @RequestMapping("/basicCategory")
    public String createBasicCategory(){
        List<Category> basic = List.of(
                Category.builder().name("ubrania, które nadają się do ponownego użycia").build(),
                Category.builder().name("ubrania, do wyrzucenia").build(),
                Category.builder().name("zabawki").build(),
                Category.builder().name("książki").build(),
                Category.builder().name("inne").build()
        );
        categoryRepository.saveAll(basic);
        return "redirect:/";
    }
}
