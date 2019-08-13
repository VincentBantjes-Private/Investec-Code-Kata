package com.assessment.investec.codekata.api.models.response.reportrestcontroller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrettyPrintAddressesResponse implements Serializable {

    private List<String> addresses = new ArrayList<>();
}