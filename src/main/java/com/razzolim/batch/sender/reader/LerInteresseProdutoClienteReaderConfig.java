package com.razzolim.batch.sender.reader;

import com.razzolim.batch.sender.domain.Cliente;
import com.razzolim.batch.sender.domain.InteresseProdutoCliente;
import com.razzolim.batch.sender.domain.Produto;
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
public class LerInteresseProdutoClienteReaderConfig {

    @Bean
    public JdbcCursorItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader(
            @Qualifier("appDataSource") DataSource ds) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from interesse_produto_cliente ");
        sql.append("join cliente on (cliente = cliente.id ");
        sql.append("join produto on (produto = produto.id)");

        return new JdbcCursorItemReaderBuilder<InteresseProdutoCliente>()
                .name("")
                .dataSource(ds)
                .sql(sql.toString())
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<InteresseProdutoCliente> rowMapper() {
        return new RowMapper<InteresseProdutoCliente>() {
            @Override
            public InteresseProdutoCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));

                Produto produto = new Produto();
                produto.setId(rs.getInt(6));
                produto.setNome(rs.getString(7));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));

                InteresseProdutoCliente ipc = new InteresseProdutoCliente();
                ipc.setCliente(cliente);
                ipc.setProduto(produto);

                return ipc;
            }
        };
    }

}
