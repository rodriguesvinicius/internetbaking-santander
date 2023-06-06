package br.com.internetbanking.api.usecase;

import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RetrieveTransacao {
    Page<Transacao> execute(Long idCliente, LocalDate dataCriacao, TipoTransacao tipoTransacao, Pageable pageable);
}
