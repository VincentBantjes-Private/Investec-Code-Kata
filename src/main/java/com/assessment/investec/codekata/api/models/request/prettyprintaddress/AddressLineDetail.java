package com.assessment.investec.codekata.api.models.request.prettyprintaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressLineDetail {

    private String line1;

    private String line2;
}