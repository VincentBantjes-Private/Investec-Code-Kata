package com.assessment.investec.codekata.services;

import com.assessment.investec.codekata.api.models.response.highestcommonfactor.HighestCommonFactorResponse;
import com.assessment.investec.codekata.exceptions.HighestCommonFactorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Slf4j
@Service
public class HighestCommonFactorService {

    public HighestCommonFactorResponse getHighestCommonFactor(@NotEmpty List<Integer> numbers) throws HighestCommonFactorException {
        Map<Integer, List<Integer>> factors = new HashMap<>(numbers.size());
        Set<Integer> allFactors = new HashSet<>();

        // Determine factors for each number
        for (Integer number : numbers) {
            List<Integer> numberDivisors = getDivisorsForNumber(number);
            allFactors.addAll(numberDivisors);
            factors.put(number, numberDivisors);
        }

        if(allFactors.size() == 0) {
            throw new HighestCommonFactorException("There should be at least one whole number that is not 0", HttpStatus.BAD_REQUEST);
        }

        // Find all common factors.
        TreeSet<Integer> allCommonFactors = new TreeSet<>(allFactors);
        for (Integer number : allFactors) {
            for (Integer key : factors.keySet()) {
                if (!factors.get(key).contains(number)) {
                    allCommonFactors.remove(number);
                    break;
                }
            }
        }

        // Return highest factor.
        return new HighestCommonFactorResponse(allCommonFactors.last());
    }

    private List<Integer> getDivisorsForNumber(int number) {
        List<Integer> numberDivisors = new ArrayList<>();

        if (number == 0) {
            return numberDivisors;
        } else if (number > 0) {
            for (int i = 1; i <= number; i++) {
                if (number % i == 0) {
                    numberDivisors.add(i);
                }
            }
        } else if (number < 0) {
            for (int i = -1; i >= number; i--) {
                if (number % i == 0) {
                    numberDivisors.add(i*-1);
                }
            }
        }
        return numberDivisors;
    }
}