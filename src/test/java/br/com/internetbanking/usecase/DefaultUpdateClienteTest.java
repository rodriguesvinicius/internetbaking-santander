package br.com.internetbanking.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.exception.ResourceNotFoundException;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.configuration.InternetBankingTestConfiguration;
import br.com.internetbanking.internal.usecase.DefaultCreateCliente;
import br.com.internetbanking.internal.usecase.DefaultUpdateCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
        classes = { InternetBankingTestConfiguration.class })
@Transactional
class DefaultUpdateClienteTest {

    @Autowired
    private DefaultCreateCliente defaultCreateCliente;

    @Autowired
    private DefaultUpdateCliente defaultUpdateCliente;

    @Test
    final void executeUpdateCliente() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        ClienteUpdateRequestDto clienteToUpdate = new ClienteUpdateRequestDto();
        clienteToUpdate.setNome("Antonio Rodrigues");

        Cliente clienteUpdated = this.defaultUpdateCliente.execute(cliente.getId(), clienteToUpdate);

        Assertions.assertNotNull(clienteUpdated);
        Assertions.assertEquals("Antonio Rodrigues", clienteUpdated.getNome());
    }

    @Test
    final void errorResourceNotFoundException() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            this.defaultUpdateCliente.execute(null, null);
        });
        assertEquals("O parâmetro id é obrigatório para localizar um cliente", exception.getMessage());
    }

    private ClienteRequestDto createCliente() {
        ClienteRequestDto cliente = new ClienteRequestDto();
        cliente.setNome("Vinicius Alves");
        cliente.setDataNascimento(LocalDate.now());
        cliente.setIsPlanoExclusive(Boolean.FALSE);
        cliente.setNumeroConta("1234567890");
        return cliente;
    }
}
