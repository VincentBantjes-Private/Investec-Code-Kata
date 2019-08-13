package com.assessment.investec.codekata.api.models.request.prettyprintaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country implements Serializable {

    @NotNull(message = "Country code must be specified.")
    private String code;

    @NotNull(message = "Country name must be specified.")
    private String name;
}