package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.exception.ResourceBadRequestException;
import br.com.internetbanking.api.exception.ResourceConflictExeception;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.repository.ClienteRepository;
import br.com.internetbanking.api.usecase.CreateCliente;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(CreateCliente.class)
public class DefaultCreateCliente implements CreateCliente {

    private final ClienteRepository clienteRepository;
    private final RetrieveCliente retrieveCliente;

    @Override
    @Transactional
    public Cliente execute(ClienteRequestDto clienteRequestDto) {
        if (clienteRequestDto != null) {
            Cliente cliente = this.retrieveCliente.execute(clienteRequestDto.getNumeroConta());
            if (cliente == null) {
                return this.clienteRepository.save(ClienteRequestDto.fromDto(clienteRequestDto));
            }
            throw new ResourceConflictExeception("Já existe um cliente cadastrado com a conta informada!");
        }
        throw new ResourceBadRequestException("Objeto cliente não pode ser nulo");
    }
}
