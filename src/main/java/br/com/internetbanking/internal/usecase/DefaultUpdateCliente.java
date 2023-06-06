package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.repository.ClienteRepository;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import br.com.internetbanking.api.usecase.UpdateCliente;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

import static br.com.internetbanking.api.dto.ClienteUpdateRequestDto.prepareEntityToUpdate;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(UpdateCliente.class)
@Transactional
public class DefaultUpdateCliente implements UpdateCliente {

    private final ClienteRepository clienteRepository;
    private final RetrieveCliente retrieveCliente;

    @Override
    public Cliente execute(Long id, ClienteUpdateRequestDto clienteUpdateRequestDto) {
        if (id != null && clienteUpdateRequestDto != null) {
            Cliente clienteEntity = this.retrieveCliente.execute(id);
            prepareEntityToUpdate(clienteEntity, clienteUpdateRequestDto);
            return this.clienteRepository.save(clienteEntity);
        }
        throw new ValidationException("O parâmetro id é obrigatório para localizar um cliente");
    }
}
