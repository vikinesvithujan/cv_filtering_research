package com.research.recruiter.controller;

import com.research.recruiter.model.Candidate;
import com.research.recruiter.model.Filter;
import com.research.recruiter.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<String> addCandidate(@Valid @RequestBody Candidate candidate) {
        candidateService.addCandidate(candidate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getCandidateList() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateByID(@PathVariable String id) {
        Candidate candidate = candidateService.getCandidateByID(id);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @PostMapping("/skills")
    public ResponseEntity<List<Candidate>> getCandidateBySkill(@Valid @RequestBody Filter filter) {
        List<Candidate> candidates = candidateService.getCandidatesBySkills(filter.getSkills(), filter.getPosition());
        candidates = candidateService.sortCandidates(candidates, filter.getSkills());
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

}
