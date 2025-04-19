package org.dogrula.kaynakdogrulamabackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitationResponse {
    private String original;
    private String authors;
    private String title;
    private String year;
    private String journalOrPublisher;
    private boolean isValid;
    private String verificationSource; // örn: "DOI eşleşti", "Web'de bulundu", "Doğrulanamadı"
}
