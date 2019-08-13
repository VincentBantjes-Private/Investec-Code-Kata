package com.assessment.investec.codekata.services;

import com.assessment.investec.codekata.api.models.request.prettyprintaddress.Address;
import com.assessment.investec.codekata.api.models.request.prettyprintaddress.EnumAddressType;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.PrettyPrintAddressesResponse;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.ValidateAddressesResponse;
import com.assessment.investec.codekata.exceptions.AddressFileNotFoundException;
import com.assessment.investec.codekata.exceptions.AddressNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PrettyPrintAddressService {

    // Question A
    public String prettyPrintAddress(Address address) {
        return String.format("%s: %s - %s - %s - %s - %s",
                (address.getType() != null ? address.getType().getName() : Strings.EMPTY),
                (address.getAddressLineDetail() != null ? (String.format("%s %s", address.getAddressLineDetail().getLine1(), address.getAddressLineDetail().getLine2()).trim()) : Strings.EMPTY),
                address.getCityOrTown(),
                (address.getProvinceOrState() != null ? address.getProvinceOrState().getName() : Strings.EMPTY),
                address.getPostalCode(),
                (address.getCountry() != null ? address.getCountry().getName() : Strings.EMPTY)
        ).trim();
    }

    // Question B & C
    /***
     * Returns the pretty print versions of the addresses in the addresses.json file.
     *
     * @param addressType optional parameter for filtering the list for a specific type of address.
     * @return
     * @throws AddressFileNotFoundException
     * @throws AddressNotFoundException
     */
    public PrettyPrintAddressesResponse processAddressesInJsonFile(EnumAddressType addressType) throws AddressFileNotFoundException, AddressNotFoundException {
        return processAddresses(loadAddresses(), addressType);
    }

    // Question C
    public PrettyPrintAddressesResponse processAddresses(List<Address> addresses, EnumAddressType addressType) throws AddressNotFoundException {

        if (addresses == null) {
            throw new AddressNotFoundException("No address found.", HttpStatus.NOT_FOUND);
        }

        PrettyPrintAddressesResponse prettyPrintAddressesResponse = new PrettyPrintAddressesResponse();
        for (Address address : addresses) {
            if (addressType == null || address.getType().getCode().equals(addressType.getCode())) {
                prettyPrintAddressesResponse.getAddresses().add(prettyPrintAddress(address));
            }
        }

        if (prettyPrintAddressesResponse.getAddresses().size() < 1) {
            throw new AddressNotFoundException(String.format("No address found for type %s.", addressType.getName()), HttpStatus.NOT_FOUND);
        }

        return prettyPrintAddressesResponse;
    }

    // Question E
    public ValidateAddressesResponse validateAddressesInFile() throws AddressFileNotFoundException {
        List<Address> addresses = loadAddresses();

        Map<String, List<String>> errors = new TreeMap<>();

        for (Address address : addresses) {
            List<String> errorsForAddress = validateAddress(address);
            if (!errorsForAddress.isEmpty()) {
                errors.put(address.getId(), errorsForAddress);
            }
        }
        return new ValidateAddressesResponse(errors);
    }

    // Question D
    public List<String> validateAddress(Address address) {
        List<String> errors = new ArrayList<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Address> violation : violations) {
                errors.add(violation.getMessage());
            }
        }

        if (address.getAddressLineDetail() != null &&
                (Strings.isEmpty(address.getAddressLineDetail().getLine1()) && Strings.isEmpty(address.getAddressLineDetail().getLine2()))) {
            errors.add(String.format("At least one address detail must be present for Address id: %s.", address.getId()));
        }

        if (address.getCountry() != null && address.getCountry().getCode() != null && address.getCountry().getCode().equals("ZA")) {
            if (address.getProvinceOrState() == null
                    || address.getProvinceOrState().getName() == null
                    || address.getProvinceOrState().getName().trim().isEmpty()) {
                errors.add(String.format("Province is required for country id: %s.", address.getId()));
            }
        }
        return errors;
    }

    private List<Address> loadAddresses() throws AddressFileNotFoundException {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(new ClassPathResource("addresses.json").getFile()));
        } catch (IOException e) {
            throw new AddressFileNotFoundException("The addresses.json file could not be found.", HttpStatus.NOT_FOUND);
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                (json, type, jsonDeserializationContext) -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
        return gson.fromJson(reader, new TypeToken<ArrayList<Address>>() {}.getType());
    }
}