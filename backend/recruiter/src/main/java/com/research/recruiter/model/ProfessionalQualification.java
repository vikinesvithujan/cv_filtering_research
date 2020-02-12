package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class ProfessionalQualification {
    @NotEmpty(message = "name must not be empty")
    private String name;

    @NotEmpty(message = "institute must not be empty")
    private String institute;

    @NotEmpty(message = "level must not be empty")
    private String level;
}
