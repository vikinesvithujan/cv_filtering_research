package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class Experience {
    @NotEmpty(message = "company must not be empty")
    private String company;

    @NotEmpty(message = "designation must not be empty")
    private String designation;

    @NotEmpty(message = "duration must not be empty")
    private int duration;
}
