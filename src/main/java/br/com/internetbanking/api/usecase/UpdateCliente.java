package br.com.internetbanking.api.usecase;

import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.model.Cliente;

public interface UpdateCliente {
    Cliente execute(Long id, ClienteUpdateRequestDto clienteRequestDto);
}
