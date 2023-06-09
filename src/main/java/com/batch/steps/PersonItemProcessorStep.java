package com.batch.steps;

import com.batch.entities.Person;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PersonItemProcessorStep implements ItemProcessor<Person, Person> {


    @Override
    public Person process(Person person) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        person.setCreateAt(dateTimeFormatter.format(dateTime));

        return person;
    }
}
