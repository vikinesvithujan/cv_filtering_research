package com.research.recruiter.handler;

import com.research.recruiter.model.*;

import java.util.List;

public interface OntoHandler {

    void addCandidate(Candidate candidate);

    List<Candidate> getCandidatesBasicDetails();

    Candidate getCandidateBasicDetails(String candidateId);

    List<TechnicalSkill> getCandidateTechnicalSkills(String candidateId);

    List<SoftSkill> getCandidateSoftSkills(String candidateId);

    List<Project> getCandidateProjects(String candidateId);

    List<Experience> getCandidateExperience(String candidateId);

    List<EducationalQualification> getCandidateEducationalQualification(String candidateId);

    List<ProfessionalQualification> getCandidateProfessionalQualification(String candidateId);

    List<String> getCandidateIdsBySkills(List<String> skills);

    List<String> getCandidateIdsByPosition(String position);

    List<String> getCandidateIdsBySkillsAndPosition(List<String> skills, String position);
}
