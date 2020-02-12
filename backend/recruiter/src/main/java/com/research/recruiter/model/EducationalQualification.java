package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class EducationalQualification {
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotEmpty(message = "institute must not be empty")
    private String institute;

    @NotEmpty(message = "certificate must not be empty")
    private String certificate;

    @NotEmpty(message = "gpa must not be empty")
    private String gpa;
}
