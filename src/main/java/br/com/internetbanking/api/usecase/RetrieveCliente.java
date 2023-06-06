package br.com.internetbanking.api.usecase;

import br.com.internetbanking.api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetrieveCliente {
    Page<Cliente> execute(Pageable pageable);
    Cliente execute(Long id);

    Cliente execute(String numeroConta);
}
