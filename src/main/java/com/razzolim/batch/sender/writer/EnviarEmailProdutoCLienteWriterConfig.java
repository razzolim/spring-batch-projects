package com.razzolim.batch.sender.writer;

import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.item.mail.builder.SimpleMailMessageItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class EnviarEmailProdutoCLienteWriterConfig {

    @Bean
    public SimpleMailMessageItemWriter enviarEmailProdutoClienteWriter(MailSender sender) {
        return new SimpleMailMessageItemWriterBuilder()
                .mailSender(sender)
                .build();
    }

}
