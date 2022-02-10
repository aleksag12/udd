package com.udd.dto;

import javax.validation.constraints.NotNull;

import com.udd.model.Operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {
	@NotNull
    private String name;
    @NotNull
    private String value;
    @NotNull
    private String value2;
    @NotNull
    private Operator operator;
    @NotNull
    private boolean phrase;
}
