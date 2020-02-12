package com.research.recruiter.service;

import com.research.recruiter.handler.OntoHandler;
import com.research.recruiter.model.Candidate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private OntoHandler ontoHandler;

    @Override
    public void addCandidate(Candidate candidate) {
        candidate.setCandidateId('C' + RandomStringUtils.random(4, false, true).toUpperCase());
        ontoHandler.addCandidate(candidate);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidateList = ontoHandler.getCandidatesBasicDetails();
        candidateList.forEach(candidate -> {
            candidate.setTechnicalSkills(ontoHandler.getCandidateTechnicalSkills(candidate.getCandidateId()));
            candidate.setSoftSkills(ontoHandler.getCandidateSoftSkills(candidate.getCandidateId()));
            candidate.setProjects(ontoHandler.getCandidateProjects(candidate.getCandidateId()));
            candidate.setEducationalQualifications(ontoHandler.getCandidateEducationalQualification(candidate.getCandidateId()));
            candidate.setProfessionalQualifications(ontoHandler.getCandidateProfessionalQualification(candidate.getCandidateId()));
            candidate.setWorkExperience(ontoHandler.getCandidateExperience(candidate.getCandidateId()));
        });
        return candidateList;
    }

    @Override
    public Candidate getCandidateByID(String candidateId) {
        Candidate candidate = ontoHandler.getCandidateBasicDetails(candidateId);
        if (candidate.getCandidateId() != null) {
            candidate.setTechnicalSkills(ontoHandler.getCandidateTechnicalSkills(candidate.getCandidateId()));
            candidate.setSoftSkills(ontoHandler.getCandidateSoftSkills(candidate.getCandidateId()));
            candidate.setProjects(ontoHandler.getCandidateProjects(candidate.getCandidateId()));
            candidate.setEducationalQualifications(ontoHandler.getCandidateEducationalQualification(candidate.getCandidateId()));
            candidate.setProfessionalQualifications(ontoHandler.getCandidateProfessionalQualification(candidate.getCandidateId()));
            candidate.setWorkExperience(ontoHandler.getCandidateExperience(candidate.getCandidateId()));
            return candidate;
        } else {
            return null;
        }
    }

    @Override
    public List<Candidate> getCandidatesBySkills(List<String> skills, String position) {
        List<Candidate> candidateList = new ArrayList<>();

        if (position != null && !position.trim().equals("all") && !skills.isEmpty()) {
            ontoHandler.getCandidateIdsBySkillsAndPosition(skills, position).forEach(candidateId -> {
                Candidate candidate = ontoHandler.getCandidateBasicDetails(candidateId);
                candidate.setTechnicalSkills(ontoHandler.getCandidateTechnicalSkills(candidate.getCandidateId()));
                candidate.setSoftSkills(ontoHandler.getCandidateSoftSkills(candidate.getCandidateId()));
                candidate.setProjects(ontoHandler.getCandidateProjects(candidateId));
                candidate.setEducationalQualifications(ontoHandler.getCandidateEducationalQualification(candidateId));
                candidate.setProfessionalQualifications(ontoHandler.getCandidateProfessionalQualification(candidateId));
                candidate.setWorkExperience(ontoHandler.getCandidateExperience(candidateId));

                candidateList.add(candidate);
            });
        } else if (position != null && !position.trim().equals("all")) {
            ontoHandler.getCandidateIdsByPosition(position).forEach(candidateId -> {
                Candidate candidate = ontoHandler.getCandidateBasicDetails(candidateId);
                candidate.setTechnicalSkills(ontoHandler.getCandidateTechnicalSkills(candidate.getCandidateId()));
                candidate.setSoftSkills(ontoHandler.getCandidateSoftSkills(candidate.getCandidateId()));
                candidate.setProjects(ontoHandler.getCandidateProjects(candidateId));
                candidate.setEducationalQualifications(ontoHandler.getCandidateEducationalQualification(candidateId));
                candidate.setProfessionalQualifications(ontoHandler.getCandidateProfessionalQualification(candidateId));
                candidate.setWorkExperience(ontoHandler.getCandidateExperience(candidateId));

                candidateList.add(candidate);
            });
        } else if (!skills.isEmpty()) {
            ontoHandler.getCandidateIdsBySkills(skills).forEach(candidateId -> {
                Candidate candidate = ontoHandler.getCandidateBasicDetails(candidateId);
                candidate.setTechnicalSkills(ontoHandler.getCandidateTechnicalSkills(candidate.getCandidateId()));
                candidate.setSoftSkills(ontoHandler.getCandidateSoftSkills(candidate.getCandidateId()));
                candidate.setProjects(ontoHandler.getCandidateProjects(candidateId));
                candidate.setEducationalQualifications(ontoHandler.getCandidateEducationalQualification(candidateId));
                candidate.setProfessionalQualifications(ontoHandler.getCandidateProfessionalQualification(candidateId));
                candidate.setWorkExperience(ontoHandler.getCandidateExperience(candidateId));

                candidateList.add(candidate);
            });
        } else {
            return getAllCandidates();
        }
        return candidateList;
    }

    @Override
    public List<Candidate> sortCandidates(List<Candidate> candidates, List<String> skills) {
        HashMap<Candidate, Integer> candidatesMap = new HashMap<>();
        candidates.forEach(candidate -> {
            AtomicInteger points = new AtomicInteger();
            skills.forEach(skill -> {
                candidate.getTechnicalSkills().forEach(s -> {
                    if (skill.toUpperCase().equals(s.getSkill())) {
                        points.set(points.get() + s.getRate());
                    }
                });

            });
            candidate.getWorkExperience().forEach(experience -> {
                points.set(points.get() + experience.getDuration());
            });
            candidate.getProjects().forEach(project -> {
                skills.forEach(skill -> {
                    if (project.getTechnologies().toUpperCase().contains(skill.toUpperCase())) {
                        points.set(points.get() + 1);
                    }
                });
            });
            candidatesMap.put(candidate, points.get());
        });

        LinkedHashMap<Candidate, Integer> sortedMap = new LinkedHashMap<>();
        candidatesMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return new ArrayList<>(sortedMap.keySet());
    }
}
