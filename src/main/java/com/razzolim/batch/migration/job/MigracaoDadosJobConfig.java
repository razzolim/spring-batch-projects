package com.razzolim.batch.migration.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MigracaoDadosJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job migracaoDadosJob(@Qualifier("migrarPessoaStep") Step migracaoPessoasStep,
                                @Qualifier("migrarDadosBancariosStep") Step migracaoDadosBancariosStep) {
        return jobBuilderFactory
                .get("migracaoDadosJob")
                .start(migracaoPessoasStep)
                .next(migracaoDadosBancariosStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
