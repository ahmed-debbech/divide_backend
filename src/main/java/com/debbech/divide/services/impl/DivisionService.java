package com.debbech.divide.services.impl;

import com.debbech.divide.data.DivisionRepo;
import com.debbech.divide.entity.division.Division;
import com.debbech.divide.services.interfaces.IDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionService implements IDivisionService {

    @Autowired
    private DivisionRepo divisionRepo;

    @Override
    public Division save(Division division) {
        Division d = divisionRepo.save(division);
        return d;
    }
}
