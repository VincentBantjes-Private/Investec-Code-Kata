package com.assessment.investec.codekata.services;

import com.assessment.investec.codekata.api.models.request.prettyprintaddress.*;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.PrettyPrintAddressesResponse;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.ValidateAddressesResponse;
import com.assessment.investec.codekata.exceptions.AddressFileNotFoundException;
import com.assessment.investec.codekata.exceptions.AddressNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PrettyPrintAddressTest {

    private static final String PHYSICAL_ADDRESS = "Physical Address: Address 1 Line 2 - City 1 - Eastern Cape - 1234 - South Africa";
    private static final String POSTAL_ADDRESS = "Postal Address:  - City 2 -  - 2345 - Lebanon";
    private static final String BUSINESS_ADDRESS = "Business Address: Address 3 - City 3 -  - 3456 - South Africa";

    @InjectMocks
    private PrettyPrintAddressService prettyPrintAddress;

    @Test
    public void prettyPrintAddress_success_allInfo() {
        Assert.assertEquals(PHYSICAL_ADDRESS,
                prettyPrintAddress.prettyPrintAddress(getAddress(true, true, true, true)));
    }

    @Test
    public void prettyPrintAddress_success_no_addressline() {
        Assert.assertEquals("Physical Address:  - City 1 - Eastern Cape - 1234 - South Africa",
                prettyPrintAddress.prettyPrintAddress(getAddress(false, true, true, true)));
    }

    @Test
    public void prettyPrintAddress_success_no_type() {
        Assert.assertEquals(": Address 1 Line 2 - City 1 - Eastern Cape - 1234 - South Africa",
                prettyPrintAddress.prettyPrintAddress(getAddress(true, false, true, true)));
    }

    @Test
    public void prettyPrintAddress_success_no_country() {
        Assert.assertEquals("Physical Address: Address 1 Line 2 - City 1 - Eastern Cape - 1234 -",
                prettyPrintAddress.prettyPrintAddress(getAddress(true, true, false, true)));
    }

    @Test
    public void prettyPrintAddress_success_no_province() {
        Assert.assertEquals("Physical Address: Address 1 Line 2 - City 1 -  - 1234 - South Africa",
                prettyPrintAddress.prettyPrintAddress(getAddress(true, true, true, false)));
    }

    @Test
    public void prettyPrintAddress_success_processAddressesInJsonFile() throws AddressNotFoundException, AddressFileNotFoundException {
        Assert.assertEquals(loadPrettyPrintAddressesResponse(PHYSICAL_ADDRESS, POSTAL_ADDRESS, BUSINESS_ADDRESS), prettyPrintAddress.processAddressesInJsonFile(null));
    }

    @Test
    public void prettyPrintAddress_success_processAddressesInJsonFileForOneType() throws AddressNotFoundException, AddressFileNotFoundException {
        Assert.assertEquals(loadPrettyPrintAddressesResponse(PHYSICAL_ADDRESS), prettyPrintAddress.processAddressesInJsonFile(EnumAddressType.PHYSICAL));
    }

    @Test
    public void prettyPrintAddress_success_processAddresses_with_type_found() throws AddressNotFoundException {
        List<Address> addresses = new ArrayList<>();
        addresses.add(getAddress(true, true, true, true));
        Assert.assertEquals(loadPrettyPrintAddressesResponse(PHYSICAL_ADDRESS), prettyPrintAddress.processAddresses(addresses, null));
    }

    @Test(expected = AddressNotFoundException.class)
    public void prettyPrintAddress_success_processAddresses_with_type_not_found() throws AddressNotFoundException {
        List<Address> addresses = new ArrayList<>();
        addresses.add(getAddress(true, true, true, true));

        prettyPrintAddress.processAddresses(addresses, EnumAddressType.BUSINESS);
    }

    @Test(expected = AddressNotFoundException.class)
    public void prettyPrintAddress_success_processAddresses_with_no_address_list() throws AddressNotFoundException {
        List<Address> addresses = new ArrayList<>();
        addresses.add(getAddress(true, true, true, true));

        prettyPrintAddress.processAddresses(null, EnumAddressType.BUSINESS);
    }

    @Test(expected = AddressNotFoundException.class)
    public void prettyPrintAddress_success_processAddresses_with_empty_address_list() throws AddressNotFoundException {
        prettyPrintAddress.processAddresses(new ArrayList<>(), EnumAddressType.BUSINESS);
    }

    @Test
    public void prettyPrintAddress_success_validateAddressesInFile() throws AddressFileNotFoundException {
        ValidateAddressesResponse validateAddressesResponse = new ValidateAddressesResponse();
        validateAddressesResponse.getErrors().put("2", Arrays.asList("The address line details are required."));
        validateAddressesResponse.getErrors().put("3", Arrays.asList("Province is required for country id: 3."));
        Assert.assertEquals(validateAddressesResponse, prettyPrintAddress.validateAddressesInFile());
    }

    @Test
    public void prettyPrintAddress_success_validateAddress() {
        Address address = getAddress(true, true, true, true);
        address.getAddressLineDetail().setLine1(null);
        address.getAddressLineDetail().setLine2(null);
        Assert.assertEquals(Arrays.asList("At least one address detail must be present for Address id: 1."), prettyPrintAddress.validateAddress(address));
    }

    private PrettyPrintAddressesResponse loadPrettyPrintAddressesResponse(String... addresses) {
        PrettyPrintAddressesResponse prettyPrintAddressesResponse = new PrettyPrintAddressesResponse();
        for (String address: addresses) {
            prettyPrintAddressesResponse.getAddresses().add(address);
        }
        return prettyPrintAddressesResponse;
    }

    private static Address getAddress(boolean withAddressLineDetails, boolean withAddType, boolean withCountry, boolean withProviceOrState) {
        Address address = new Address();
        address.setId("1");
        address.setCityOrTown("City 1");
        address.setPostalCode("1234");
        if (withAddressLineDetails) {
            AddressLineDetail addressLineDetail = new AddressLineDetail();
            addressLineDetail.setLine1("Address 1");
            addressLineDetail.setLine2("Line 2");
            address.setAddressLineDetail(addressLineDetail);
        }
        if (withCountry) {
            Country country = new Country();
            country.setCode("ZA");
            country.setName("South Africa");
            address.setCountry(country);
        }
        if (withAddType) {
            AddressType addressType = new AddressType();
            addressType.setCode(EnumAddressType.PHYSICAL.getCode());
            addressType.setName(EnumAddressType.PHYSICAL.getName());
            address.setType(addressType);
        }
        if (withProviceOrState) {
            ProvinceOrState provinceOrState = new ProvinceOrState();
            provinceOrState.setId(1);
            provinceOrState.setName("Eastern Cape");
            address.setProvinceOrState(provinceOrState);
        }
        return address;
    }
}