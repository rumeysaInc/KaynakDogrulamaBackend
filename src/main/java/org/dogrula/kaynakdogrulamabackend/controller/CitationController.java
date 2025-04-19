package org.dogrula.kaynakdogrulamabackend.controller;

import org.dogrula.kaynakdogrulamabackend.dto.CitationRequest;
import org.dogrula.kaynakdogrulamabackend.dto.CitationResponse;
import org.dogrula.kaynakdogrulamabackend.service.CitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CitationController {

    @Autowired
    private CitationService citationService;

    @PostMapping("/validate-citation")
    public ResponseEntity<List<CitationResponse>> validateCitation(@RequestBody CitationRequest request) {
        return ResponseEntity.ok(citationService.processCitations(request.getCitations()));
    }

    @PostMapping("/file")
    public ResponseEntity<List<CitationResponse>> validateCitationsFromFile(@RequestParam("file") MultipartFile file) {
        try {
            List<CitationResponse> responses = citationService.processCitationsFromFile(file);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
