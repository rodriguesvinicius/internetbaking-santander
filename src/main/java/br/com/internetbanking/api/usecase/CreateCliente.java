package br.com.internetbanking.api.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.model.Cliente;

public interface CreateCliente {
    Cliente execute(ClienteRequestDto clienteRequestDto);
}
