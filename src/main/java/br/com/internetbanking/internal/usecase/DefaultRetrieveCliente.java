package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.exception.ResourceBadRequestException;
import br.com.internetbanking.api.exception.ResourceNotFoundException;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.repository.ClienteRepository;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(RetrieveCliente.class)
public class DefaultRetrieveCliente implements RetrieveCliente {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Page<Cliente> execute(Pageable pageable) {
        Page<Cliente> clientes = this.clienteRepository.findAll(pageable);
        if (clientes.hasContent()) {
            return clientes;
        }
        throw new ResourceNotFoundException("Não foi possível localizar cliente cadastrado");
    }

    @Override
    @Transactional
    public Cliente execute(Long id) {
        if (id != null) {
            return this.clienteRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Não foi possível localizar um cliente para o id informado"));
        }
        throw new ValidationException("O parâmetro id é obrigatório para localizar um cliente");
    }

    @Override
    @Transactional
    public Cliente execute(String numeroConta) {
        if (numeroConta != null && !numeroConta.isBlank()) {
            Optional<Cliente> cliente = this.clienteRepository.findByNumeroConta(numeroConta);
            return cliente.orElse(null);
        }
        throw new ResourceBadRequestException("Numero da conta é obrigatório para buscar um cliente");
    }
}
