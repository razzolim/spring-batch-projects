package com.razzolim.batch.billing.reader;

import com.razzolim.batch.billing.domain.CartaoCredito;
import com.razzolim.batch.billing.domain.Cliente;
import com.razzolim.batch.billing.domain.Transacao;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class LerTransacoesReaderConfig {

    @Bean
    public JdbcCursorItemReader<Transacao> letTransacoesReader(@Qualifier("appDataSource") DataSource ds) {
        return new JdbcCursorItemReaderBuilder<Transacao>()
                .name("letTransacoesReader")
                .dataSource(ds)
                .sql("select * from transacao join cartao_credito using (numero_cartao_credito) order by numero_cartao_credito")
                .rowMapper(rowMapperTransacao())
                .build();
    }

    private RowMapper<Transacao> rowMapperTransacao() {
        return new RowMapper<Transacao>() {
            @Override
            public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {
                CartaoCredito cc = new CartaoCredito();
                cc.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente"));

                cc.setCliente(cliente);

                Transacao trx = new Transacao();
                trx.setCartaoCredito(cc);
                trx.setId(rs.getInt("id"));
                trx.setData(rs.getDate("data"));
                trx.setValor(rs.getDouble("valor"));
                trx.setDescricao(rs.getString("descricao"));

                return trx;
            }
        };
    }

}
