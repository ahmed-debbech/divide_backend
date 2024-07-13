package com.debbech.divide.divisor;

import com.debbech.divide.divisor.steps.*;
import com.debbech.divide.entity.division.Division;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DivisionStepsExecutor {

    private List<IDivisionStep> steps;

    public DivisionStepsExecutor(){
        steps = new ArrayList<>();
        steps.add(new InputValidationStep());
        steps.add(new CalculationValidationStep());
        steps.add(new SavingStep());
        steps.add(new UpdateForeignReceiptsStep());
    }

    public void executeStepsInOrder(Long id, Division division) throws Exception{
        for(IDivisionStep ids : steps){
            ids.execute(id, division);
        }
    }

}
