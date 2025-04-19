package org.dogrula.kaynakdogrulamabackend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.dogrula.kaynakdogrulamabackend.dto.CitationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CitationService {

    public List<CitationResponse> processCitations(List<String> citationList) {
        return citationList.stream()
                .map(this::analyzeCitation)
                .collect(Collectors.toList());
    }

    public List<CitationResponse> processCitationsFromFile(MultipartFile file) throws IOException {
        List<String> rawCitations = extractCitationsFromFile(file);
        return processCitations(rawCitations);
    }

    private List<String> extractCitationsFromFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        if (fileName.endsWith(".txt")) return readTxt(file);
        if (fileName.endsWith(".pdf")) return readPdf(file);
        if (fileName.endsWith(".docx")) return readDocx(file);
        throw new IllegalArgumentException("Desteklenmeyen dosya formatı.");
    }

    private CitationResponse analyzeCitation(String citation) {
        CitationResponse response = new CitationResponse();
        response.setOriginal(citation);

        // 👇 Basit regex/parsing örneği - NLP model/LLM ile değiştirilebilir
        response.setYear(extractBetweenParentheses(citation));
        response.setAuthors(extractBeforeParentheses(citation));
        response.setTitle(extractAfterFirstDot(citation));
        response.setJournalOrPublisher(extractAfterYearAndTitle(citation));

        // 👇 DOI ya da Web API kontrolü yapılabilir
        response.setValid(false);
        response.setVerificationSource("Doğrulama yapılmadı");

        return response;
    }

    // === Dosya Okuyucular ===

    private List<String> readTxt(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines().filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private List<String> readPdf(MultipartFile file) throws IOException {
        PDDocument doc = PDDocument.load(file.getInputStream());
        String text = new PDFTextStripper().getText(doc);
        doc.close();
        return Arrays.stream(text.split("\n"))
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private List<String> readDocx(MultipartFile file) throws IOException {
        XWPFDocument doc = new XWPFDocument(file.getInputStream());
        List<String> lines = new ArrayList<>();
        for (XWPFParagraph para : doc.getParagraphs()) {
            String text = para.getText().trim();
            if (!text.isEmpty()) lines.add(text);
        }
        return lines;
    }

    // === Basit Bilgi Çıkarımı Fonksiyonları ===

    private String extractBetweenParentheses(String text) {
        int start = text.indexOf('('), end = text.indexOf(')');
        return (start != -1 && end != -1 && end > start) ? text.substring(start + 1, end) : "";
    }

    private String extractBeforeParentheses(String text) {
        int index = text.indexOf('(');
        return (index != -1) ? text.substring(0, index).trim() : "";
    }

    private String extractAfterFirstDot(String text) {
        int firstDot = text.indexOf('.');
        if (firstDot != -1 && firstDot + 1 < text.length()) {
            String rest = text.substring(firstDot + 1).trim();
            int secondDot = rest.indexOf('.');
            return (secondDot != -1) ? rest.substring(0, secondDot).trim() : rest;
        }
        return "";
    }

    private String extractAfterYearAndTitle(String text) {
        String[] parts = text.split("\\. ");
        return parts.length >= 3 ? parts[2] : "";
    }
}
