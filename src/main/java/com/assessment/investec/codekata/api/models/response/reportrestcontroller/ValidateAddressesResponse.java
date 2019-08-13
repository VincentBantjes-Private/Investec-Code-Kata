package com.assessment.investec.codekata.api.models.response.reportrestcontroller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateAddressesResponse implements Serializable {

    private Map<String, List<String>> errors = new TreeMap<>();
}