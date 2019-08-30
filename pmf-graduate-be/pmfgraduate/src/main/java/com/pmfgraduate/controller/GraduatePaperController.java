package com.pmfgraduate.controller;

import com.pmfgraduate.service.GraduatePaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
