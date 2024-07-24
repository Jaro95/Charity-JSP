package pl.coderslab.charity.domain.institution;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/rest/institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping("/institution/list")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/institution/{id}")
    public InstitutionResponse getInstitution(@PathVariable Long id) {
        return new InstitutionResponse(institutionService.getInstitution(id));
    }

    @PostMapping("/institution/add")
    public String addInstitution(@RequestBody Optional<InstitutionRequest> institutionRequest) {
        return institutionService.addInstitution(institutionRequest);
    }

    @PutMapping("/institution/update/{id}")
    public String updateInstitution(@PathVariable Long id, @RequestBody Optional<InstitutionRequest> institutionRequest) {
        return institutionService.updateInstitution(id,institutionRequest);
    }

    @DeleteMapping("/institution/delete/{id}")
    public String deleteInstitution(@PathVariable Long id) {
        return institutionService.deleteInstitution(id);
    }
}
