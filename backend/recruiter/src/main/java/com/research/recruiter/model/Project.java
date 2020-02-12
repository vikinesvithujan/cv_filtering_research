package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class Project {
    @NotEmpty(message = "title must not be empty")
    private String title;

    @NotEmpty(message = "description must not be empty")
    private String description;

    @NotEmpty(message = "technologies must not be empty")
    private String technologies;
}
