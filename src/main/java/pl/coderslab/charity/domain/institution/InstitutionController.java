package pl.coderslab.charity.domain.institution;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/rest/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping("")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{id}")
    public InstitutionResponse getInstitution(@PathVariable Long id) {
        return new InstitutionResponse(institutionService.getInstitution(id));
    }

    @PostMapping("/add")
    public String addInstitution(@RequestBody Optional<InstitutionRequest> institutionRequest) {
        return institutionService.addInstitution(institutionRequest);
    }

    @PutMapping("/update/{id}")
    public String updateInstitution(@PathVariable Long id, @RequestBody Optional<InstitutionRequest> institutionRequest) {
        return institutionService.updateInstitution(id,institutionRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteInstitution(@PathVariable Long id) {
        return institutionService.deleteInstitution(id);
    }
}
