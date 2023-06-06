package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.repository.ClienteRepository;
import br.com.internetbanking.api.usecase.DeleteCliente;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(DeleteCliente.class)
public class DefaultDeleteCliente implements DeleteCliente {

    private final ClienteRepository clienteRepository;
    private final RetrieveCliente retrieveCliente;

    @Override
    @Transactional
    public void execute(Long id) {
        if (id != null) {
            Cliente cliente = this.retrieveCliente.execute(id);
            this.clienteRepository.delete(cliente);
        } else {
            throw new ValidationException("O parâmetro id é obrigatório para localizar um cliente");
        }
    }
}
