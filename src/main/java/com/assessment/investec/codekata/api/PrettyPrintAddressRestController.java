package com.assessment.investec.codekata.api;

import com.assessment.investec.codekata.api.models.exception.DefaultExceptionResponse;
import com.assessment.investec.codekata.api.models.request.prettyprintaddress.Address;
import com.assessment.investec.codekata.api.models.request.prettyprintaddress.EnumAddressType;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.PrettyPrintAddressesResponse;
import com.assessment.investec.codekata.api.models.response.reportrestcontroller.ValidateAddressesResponse;
import com.assessment.investec.codekata.exceptions.AddressFileNotFoundException;
import com.assessment.investec.codekata.exceptions.AddressNotFoundException;
import com.assessment.investec.codekata.services.PrettyPrintAddressService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@SwaggerDefinition
@RequestMapping("/pretty/print/address")
public class PrettyPrintAddressRestController {

    private PrettyPrintAddressService prettyPrintAddressService;

    public PrettyPrintAddressRestController(PrettyPrintAddressService prettyPrintAddressService) {
        this.prettyPrintAddressService = prettyPrintAddressService;
    }

    @GetMapping(value = "/file")
    @ApiOperation(value = "View the list of addresses provided in the pretty print format.", response = PrettyPrintAddressesResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The formatted list addresses are returned.", response = PrettyPrintAddressesResponse.class),
            @ApiResponse(code = 404, message = "When the addresses.json file could not be found or if the filtered address type is not one of " +
                    "the types in the addresses.json file.", response = DefaultExceptionResponse.class)
    })
    public PrettyPrintAddressesResponse processAddressesInJsonFile(@ApiParam(value = "The filter for the address if applicable.")
                                                                   @RequestParam(value = "addressesFilter", required = false)
                                                                           EnumAddressType addressesRequest) throws AddressFileNotFoundException, AddressNotFoundException {
        return prettyPrintAddressService.processAddressesInJsonFile(addressesRequest);
    }

    @GetMapping(value = "/file/validate")
    @ApiOperation(value = "View any errors in the list of addresses in the addresses.json file.", response = ValidateAddressesResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of errors for each address are returned if any.", response = ValidateAddressesResponse.class),
            @ApiResponse(code = 404, message = "When the addresses.json file could not be found.", response = DefaultExceptionResponse.class)
    })
    public ValidateAddressesResponse validateAddressesInFile() throws AddressFileNotFoundException {
        return prettyPrintAddressService.validateAddressesInFile();
    }

    @PostMapping
    @ApiOperation(value = "View the list of addresses that are sent through, in the pretty print format.", response = PrettyPrintAddressesResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The formatted list addresses are returned.", response = PrettyPrintAddressesResponse.class),
            @ApiResponse(code = 404, message = "When the filtered address type is not one of the types in the list provided.", response = DefaultExceptionResponse.class)
    })
    public PrettyPrintAddressesResponse processAddresses(@ApiParam(required = true, value = "The list of addresses to format.")
                                                         @NotNull
                                                         @RequestBody
                                                                 List<Address> addressesRequest,
                                                         @ApiParam(value = "The filter for the address if applicable.")
                                                         @RequestParam(value = "addressesFilter", required = false)
                                                                 EnumAddressType addressesFilter) throws AddressNotFoundException {
        return prettyPrintAddressService.processAddresses(addressesRequest, addressesFilter);
    }
}