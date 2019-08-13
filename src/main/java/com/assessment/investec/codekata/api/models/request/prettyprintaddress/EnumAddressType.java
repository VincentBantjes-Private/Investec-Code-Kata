package com.assessment.investec.codekata.api.models.request.prettyprintaddress;

import lombok.Getter;

@Getter
public enum EnumAddressType {

    PHYSICAL(1,"Physical Address"),
    POSTAL(2,"Postal Address"),
    BUSINESS(5,"Business Address");

    private Integer code;

    private String name;

    EnumAddressType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}