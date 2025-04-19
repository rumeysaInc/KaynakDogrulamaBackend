package org.dogrula.kaynakdogrulamabackend.service;

import org.dogrula.kaynakdogrulamabackend.entity.Reference;
import org.dogrula.kaynakdogrulamabackend.repository.ReferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReferenceService {

    private final ReferenceRepository referenceRepository;

    public ReferenceService(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public List<Reference> getAllReferences() {
        return referenceRepository.findAll();
    }

    public Optional<Reference> getReferenceById(Long id) {
        return referenceRepository.findById(id);
    }

    public Reference saveReference(Reference reference) {
        return referenceRepository.save(reference);
    }

    public void deleteReference(Long id) {
        referenceRepository.deleteById(id);
    }
}
