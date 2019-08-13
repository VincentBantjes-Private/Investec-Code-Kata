package com.assessment.investec.codekata.api;

import com.assessment.investec.codekata.api.models.exception.DefaultExceptionResponse;
import com.assessment.investec.codekata.api.models.request.highestcommonfactor.HighestCommonFactorRequest;
import com.assessment.investec.codekata.api.models.response.highestcommonfactor.HighestCommonFactorResponse;
import com.assessment.investec.codekata.exceptions.HighestCommonFactorException;
import com.assessment.investec.codekata.services.HighestCommonFactorService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@SwaggerDefinition
@RequestMapping("/highest/common/factor")
public class HighestCommonFactorRestController {

    private HighestCommonFactorService highestCommonFactorService;

    public HighestCommonFactorRestController(HighestCommonFactorService highestCommonFactorService) {
        this.highestCommonFactorService = highestCommonFactorService;
    }

    @PutMapping
    @ApiOperation(value = "Retrieve the highest common factor for a list of values.", response = HighestCommonFactorResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The highest common divisor is returned.", response = HighestCommonFactorResponse.class),
            @ApiResponse(code = 404, message = "An exception is returned when the list either does not contain any values or if all of the values contained are 0.", response = DefaultExceptionResponse.class)
    })
    @ResponseStatus(HttpStatus.OK)
    public HighestCommonFactorResponse highestCommonFactor(@ApiParam(required = true, value = "The numbers for whom the highest common factor need to be retrieved.")
                                                           @NotNull
                                                           @RequestBody
                                                                   HighestCommonFactorRequest request) throws HighestCommonFactorException {
        return highestCommonFactorService.getHighestCommonFactor(request.getNumbers());
    }
}