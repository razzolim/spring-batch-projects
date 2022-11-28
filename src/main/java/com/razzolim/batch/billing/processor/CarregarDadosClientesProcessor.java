package com.razzolim.batch.billing.processor;

import com.razzolim.batch.billing.domain.Cliente;
import com.razzolim.batch.billing.domain.FaturaCartaoCredito;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CarregarDadosClientesProcessor implements ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public FaturaCartaoCredito process(FaturaCartaoCredito fcc) throws Exception {
        String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", fcc.getCliente().getId());
        ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ValidationException("Cliente não encontrado!");
        }

        fcc.setCliente(response.getBody());
        return fcc;
    }
}
