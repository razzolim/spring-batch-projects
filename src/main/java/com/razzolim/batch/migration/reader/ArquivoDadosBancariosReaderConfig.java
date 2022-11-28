package com.razzolim.batch.migration.reader;

import com.razzolim.batch.migration.domain.DadosBancarios;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoDadosBancariosReaderConfig {

    @Bean
    public FlatFileItemReader<DadosBancarios> dadosBancariosReader() {
        return new FlatFileItemReaderBuilder<DadosBancarios>()
                .name("dadosBancariosReader")
                .resource(new FileSystemResource("files/input/dados_bancarios.csv"))
                .delimited()
                .names("pessoaId", "agencia", "conta", "banco", "id")
                .addComment("--")
                .targetType(DadosBancarios.class)
                .build();
    }

}
