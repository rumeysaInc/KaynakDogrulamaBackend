package org.dogrula.kaynakdogrulamabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.dogrula.kaynakdogrulamabackend.entity.Reference;
import org.dogrula.kaynakdogrulamabackend.service.ReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/references")
@Tag(name = "Resource API", description = "Kaynak doğrulama API'si")
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping
    public List<Reference> getAllReferences() {
        return referenceService.getAllReferences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reference> getReferenceById(@PathVariable Long id) {
        return referenceService.getReferenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reference createReference(@RequestBody Reference reference) {
        return referenceService.saveReference(reference);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReference(@PathVariable Long id) {
        referenceService.deleteReference(id);
        return ResponseEntity.noContent().build();
    }
}
