package com.batch.service.impl;

import com.batch.entities.Person;
import com.batch.persistence.IPersonDAO;
import com.batch.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public Iterable<Person> saveAll(List<Person> people) {
        return personDAO.saveAll(people);
    }


}
