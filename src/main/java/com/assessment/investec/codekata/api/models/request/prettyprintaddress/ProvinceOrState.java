package com.assessment.investec.codekata.api.models.request.prettyprintaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinceOrState implements Serializable {

    private Integer id;

    private String name;
}