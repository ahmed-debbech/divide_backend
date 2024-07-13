package com.debbech.divide.divisor.steps;

import com.debbech.divide.entity.division.Division;

public interface IDivisionStep {
    void execute(Long id, Division division) throws Exception;
}
