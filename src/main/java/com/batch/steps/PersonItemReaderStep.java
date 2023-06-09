package com.batch.steps;

import com.batch.entities.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

public class PersonItemReaderStep extends FlatFileItemReader<Person> {

    public PersonItemReaderStep(){
        setName("readPersons");
        setResource(new ClassPathResource("persons.csv"));
        setLinesToSkip(1);
        setEncoding(StandardCharsets.UTF_8.name());
        setLineMapper(getLineMapper());
    }

    private LineMapper<Person> getLineMapper(){
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>(); // Configurar propiedades de lectura de archivo
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        String[] columns = new String[]{"name","lastName","age"};
        int[] indexFields = new int[]{0,1,2};

        delimitedLineTokenizer.setNames(columns);
        delimitedLineTokenizer.setIncludedFields(indexFields);
        delimitedLineTokenizer.setDelimiter(",");

        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
