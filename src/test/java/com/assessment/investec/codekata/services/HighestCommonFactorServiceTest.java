package com.assessment.investec.codekata.services;

import com.assessment.investec.codekata.exceptions.HighestCommonFactorException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class HighestCommonFactorServiceTest {

    @InjectMocks
    private HighestCommonFactorService highestCommonFactorService;

    @Test(expected = HighestCommonFactorException.class)
    public void testHighestCommonFactorException() throws HighestCommonFactorException {
        highestCommonFactorService.getHighestCommonFactor(Arrays.asList(0));
    }

    @Test
    public void highestCommonFactor_success_for_one_number() throws HighestCommonFactorException {
        Assert.assertEquals(12, highestCommonFactorService.getHighestCommonFactor(Arrays.asList(12)).getHighestCommonFactor().intValue());
    }

    @Test
    public void highestCommonFactor_success_for_two_numbers() throws HighestCommonFactorException {
        Assert.assertEquals(3, highestCommonFactorService.getHighestCommonFactor(Arrays.asList(12, 27)).getHighestCommonFactor().intValue());
    }

    @Test
    public void highestCommonFactor_success_for_more_than_two_numbers() throws HighestCommonFactorException {
        Assert.assertEquals(512, highestCommonFactorService.getHighestCommonFactor(Arrays.asList(512, 8192, 2048)).getHighestCommonFactor().intValue());
    }

    @Test
    public void highestCommonFactor_success_With_Negative_Number() throws HighestCommonFactorException {
        Assert.assertEquals(3, highestCommonFactorService.getHighestCommonFactor(Arrays.asList(-12, 27)).getHighestCommonFactor().intValue());
    }
}