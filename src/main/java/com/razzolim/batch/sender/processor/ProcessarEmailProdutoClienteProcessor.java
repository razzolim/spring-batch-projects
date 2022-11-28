package com.razzolim.batch.sender.processor;

import com.razzolim.batch.sender.domain.InteresseProdutoCliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import static java.text.NumberFormat.getCurrencyInstance;

@Component
public class ProcessarEmailProdutoClienteProcessor implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> {

    @Override
    public SimpleMailMessage process(InteresseProdutoCliente ipc) throws Exception {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("xpto@no-reply.com");
        email.setTo(ipc.getCliente().getEmail());
        email.setSubject("Promoção imperdível!!!");
        email.setText(gerarTextoPromocao(ipc));
        return email;
    }

    private String gerarTextoPromocao(InteresseProdutoCliente ipc) {
        StringBuilder writer = new StringBuilder();
        writer.append(String.format("Olá, %s!\n\n", ipc.getCliente().getNome()));
        writer.append("Essa promoção pode ser do seu interesse:\n\n");
        writer.append(String.format("%s - %s\n\n", ipc.getProduto().getNome(), ipc.getProduto().getDescricao()));
        writer.append(String.format("Por apenas: %s!", getCurrencyInstance().format(ipc.getProduto().getPreco())));
        return writer.toString();
    }

}
