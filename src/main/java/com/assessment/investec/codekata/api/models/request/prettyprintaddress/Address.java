package com.assessment.investec.codekata.api.models.request.prettyprintaddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    private String id;

    private AddressType type;

    @NotNull(message = "The address line details are required.")
    private AddressLineDetail addressLineDetail;

    private ProvinceOrState provinceOrState;

    private String cityOrTown;

    @Valid
    @NotNull(message = "A country is required.")
    private Country country;

    @Pattern(message = "The postal code may only contain digits.", regexp="^[0-9]+$")
    private String postalCode;

    private String suburbOrDistrict;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastUpdated;
}