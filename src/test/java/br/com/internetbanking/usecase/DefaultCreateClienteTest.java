package br.com.internetbanking.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.exception.ResourceBadRequestException;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.configuration.InternetBankingTestConfiguration;
import br.com.internetbanking.internal.usecase.DefaultCreateCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
		classes = { InternetBankingTestConfiguration.class })
@Transactional
class DefaultCreateClienteTest {

	@Autowired
	private DefaultCreateCliente defaultCreateCliente;

	@Test
	final void executeAllSuccess() {
		Cliente cliente = this.defaultCreateCliente.execute(this.createCliente());
		Assertions.assertNotNull(cliente);
		assertEquals("Vinicius Alves", cliente.getNome(), "validando se o nome foi cadastrado");
		assertEquals("1234567890", cliente.getNumeroConta(), "validando se o numero da conta corresponde ao que foi cadastrado");
		assertEquals(BigDecimal.ZERO, cliente.getSaldo(), "validando se o saldo é 0 ao criar o cliente");
		Assertions.assertFalse(cliente.getIsPlanoExclusive(), "Validando se o isPlanoExclusive é falso");
	}

	@Test
	final void errorResourceBadRequestException() {
		Exception exception = assertThrows(ResourceBadRequestException.class, () -> {
			this.defaultCreateCliente.execute(null);
		});
		assertEquals("Objeto cliente não pode ser nulo", exception.getMessage());
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
