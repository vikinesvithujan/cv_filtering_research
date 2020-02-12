package com.research.recruiter.service;

import com.research.recruiter.model.Candidate;

import java.util.List;

public interface CandidateService {

    void addCandidate(Candidate candidate);

    List<Candidate> getAllCandidates();

    Candidate getCandidateByID(String candidateId);

    List<Candidate> getCandidatesBySkills(List<String> skills, String position);

    List<Candidate> sortCandidates(List<Candidate> candidates, List<String> skills);

}
