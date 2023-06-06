package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.exception.ResourceNotFoundException;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.api.repository.TransacaoRepository;
import br.com.internetbanking.api.usecase.RetrieveTransacao;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(RetrieveTransacao.class)
public class DefaultRetrieveTransacao implements RetrieveTransacao {

    private final TransacaoRepository transacaoRepository;

    @Override
    @Transactional
    public Page<Transacao> execute(Long idCliente, LocalDate dataCriacao, TipoTransacao tipoTransacao, Pageable pageable) {
        Page<Transacao> transacoes = this.transacaoRepository.
                findAll(idCliente, dataCriacao, tipoTransacao, pageable);
        if (transacoes.hasContent()) {
            return transacoes;
        }
        throw new ResourceNotFoundException("Não foi possível localizar transações para o cliente informado");
    }

}
