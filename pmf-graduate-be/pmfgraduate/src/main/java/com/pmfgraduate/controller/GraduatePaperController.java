package com.pmfgraduate.controller;

import com.pmfgraduate.service.GraduatePaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("api/graduatePapers")
@RestController
public class GraduatePaperController {

    @Autowired
    GraduatePaperService graduatePaperService;

    @GetMapping
    public ResponseEntity<?> getAllGraduatePapers(){
        return ResponseEntity.ok(graduatePaperService.getAllGraduatePapers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(graduatePaperService.getByID(id));
    }

    @GetMapping("/getPdf/{id}")
    public ResponseEntity<?> getPdfById(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(graduatePaperService.getGraduatePaperPdf(id));
    }

    @RequestMapping("/getSearchedFilter")
    public ResponseEntity<?> getAllBooksFromCategories(@RequestParam String title, @RequestParam String author, @RequestParam String mentor) {
        return ResponseEntity.ok(graduatePaperService.getSearchedFilter(title, author, mentor));
    }

}
