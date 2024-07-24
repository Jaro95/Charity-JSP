package pl.coderslab.charity.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Optional<Institution> getInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    public String addInstitution(Optional<InstitutionRequest> institutionRequest) {
        institutionRequest.ifPresent(i -> {
            institutionRepository.save(Institution.builder()
                    .name(institutionRequest.get().name())
                    .description(institutionRequest.get().description())
                    .build());
            log.info("Added new institution:\n{}\n{}", institutionRequest.get().name(), institutionRequest.get().description());
        });

        return institutionRequest.isPresent() ? "Added new institution:\n" + institutionRequest.get().name() + "\n" +
                institutionRequest.get().description() : "Institution not added";

    }

    public String updateInstitution(Long id, Optional<InstitutionRequest> institutionRequest ) {
        Optional<Institution> institution = institutionRepository.findById(id);
        institution.ifPresent(i -> {
            institutionRequest.ifPresent( ir -> {
                if (ir.name() != null) {
                    i.setName(ir.name());
                }
                if (ir.description() != null) {
                    i.setDescription(ir.description());
                }
                institutionRepository.save(i);
                log.info("Updated institution: {}", i.toString());
            });
        });
        return institution.isPresent() && institutionRequest.isPresent() ? "Updated institution:\n" + institution.get().getName() + "\n" +
                institution.get().getDescription() : "The institution to update was not found";
    }

    public String deleteInstitution(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        institution.ifPresent(i -> {
            institutionRepository.delete(i);
            log.info("Deleted institution: {}", i.toString());
        });
        return institution.isPresent() ? "Deleted institution:\n" + institution.get().getName() + "\n"
                + institution.get().getDescription() : "The institution to delete was not found";
    }



}
