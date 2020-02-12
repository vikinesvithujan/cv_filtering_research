package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Candidate {
    private String candidateId;

    @NotEmpty(message = "firstName must not be empty")
    private String firstName;

    @NotEmpty(message = "lastName must not be empty")
    private String lastName;

    @NotEmpty(message = "gender must not be empty")
    private String gender;

    @NotEmpty(message = "dateOfBirth must not be empty")
    private String dateOfBirth;

    @NotEmpty(message = "address must not be empty")
    private String address;

    @NotEmpty(message = "country must not be empty")
    private String country;

    @NotEmpty(message = "email must not be empty")
    private String email;

    @NotEmpty(message = "phone must not be empty")
    private String phone;

    @NotEmpty(message = "nic must not be empty")
    private String nic;

    @NotEmpty(message = "linkedIn must not be empty")
    private String linkedIn;

    @NotEmpty(message = "position must not be empty")
    private String position;

    private List<TechnicalSkill> technicalSkills;

    private List<SoftSkill> softSkills;

    private List<EducationalQualification> educationalQualifications;

    private List<ProfessionalQualification> professionalQualifications;

    private List<Project> projects;

    private List<Experience> workExperience;
}
