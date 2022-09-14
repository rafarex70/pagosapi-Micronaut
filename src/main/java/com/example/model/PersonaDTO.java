package com.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PersonaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

}
