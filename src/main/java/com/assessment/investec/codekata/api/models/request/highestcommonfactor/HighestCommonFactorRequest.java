package com.assessment.investec.codekata.api.models.request.highestcommonfactor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HighestCommonFactorRequest implements Serializable {

    @NotEmpty
    private List<Integer> numbers = new ArrayList<>();
}