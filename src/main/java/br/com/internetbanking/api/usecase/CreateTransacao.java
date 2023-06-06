package br.com.internetbanking.api.usecase;

import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.model.Transacao;

public interface CreateTransacao {
    Transacao execute(Long idCliente, TransacaoRequestDto transacaoRequestDto);
}
