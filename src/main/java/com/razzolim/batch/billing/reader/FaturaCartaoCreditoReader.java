package com.razzolim.batch.billing.reader;

import com.razzolim.batch.billing.domain.FaturaCartaoCredito;
import com.razzolim.batch.billing.domain.Transacao;
import org.springframework.batch.item.*;

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito> {

    private ItemStreamReader<Transacao> delegate;
    private Transacao transacaoAtual;

    public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate) {
        this.delegate = delegate;
    }

    @Override
    public FaturaCartaoCredito read() throws Exception {
        if (transacaoAtual == null) {
            transacaoAtual = this.delegate.read();
        }

        FaturaCartaoCredito fcc = null;
        Transacao transacao = transacaoAtual;
        transacaoAtual = null;

        if (transacao != null) {
            fcc = new FaturaCartaoCredito();
            fcc.setCartaoCredito(transacao.getCartaoCredito());
            fcc.setCliente(transacao.getCartaoCredito().getCliente());
            fcc.getTransacoes().add(transacao);

            while (isTransacaoRelacionada(transacao)) {
                fcc.getTransacoes().add(transacaoAtual);
            }
        }

        return fcc;
    }

    private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
        return peek() != null &&
                transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual.getCartaoCredito().getNumeroCartaoCredito();
    }

    private Transacao peek() throws Exception {
        transacaoAtual = delegate.read();
        return transacaoAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        this.delegate.close();
    }
}
