package com.batch.config;

import com.batch.entities.Person;
import com.batch.steps.PersonItemProcessorStep;
import com.batch.steps.PersonItemReaderStep;
import com.batch.steps.PersonItemWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public PersonItemReaderStep personItemReaderStep(){
        return new PersonItemReaderStep();
    }

    @Bean
    public PersonItemWriterStep personItemWriterStep(){
        return new PersonItemWriterStep();
    }

    @Bean
    public PersonItemProcessorStep personItemProcessorStep(){
        return new PersonItemProcessorStep();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor(); //Esto configura hilos con java
        taskExecutor.setCorePoolSize(1); //Inicie con un hilo
        taskExecutor.setMaxPoolSize(5); // Si se ve que necesita mas optimización que tome 5 hilos
        taskExecutor.setQueueCapacity(5); // si hay 5 tareas que no se pueden ejecutar osea que quedan en cola lo maximo que se pueden quedar ahi
        return taskExecutor;
    }

    @Bean
    public Step readFile(){

        return stepBuilderFactory.get("readFile")
                .<Person, Person>chunk(10)
                .reader(personItemReaderStep())
                .processor(personItemProcessorStep())
                .writer(personItemWriterStep())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("readFileWithChunk")
                .start(readFile())
                .build();
    }

}
