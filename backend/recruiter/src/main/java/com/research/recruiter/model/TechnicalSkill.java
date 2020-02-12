package com.research.recruiter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class TechnicalSkill {
    @NotEmpty(message = "skill must not be empty")
    private String skill;

    @NotEmpty(message = "rate must not be empty")
    private int rate;
}
