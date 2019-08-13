package com.assessment.investec.codekata.api.models.response.highestcommonfactor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HighestCommonFactorResponse implements Serializable {

    private Integer highestCommonFactor;
}