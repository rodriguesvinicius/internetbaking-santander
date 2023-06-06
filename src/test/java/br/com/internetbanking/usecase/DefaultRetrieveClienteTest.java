package br.com.internetbanking.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.exception.ResourceNotFoundException;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.configuration.InternetBankingTestConfiguration;
import br.com.internetbanking.internal.usecase.DefaultCreateCliente;
import br.com.internetbanking.internal.usecase.DefaultRetrieveCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
		classes = { InternetBankingTestConfiguration.class })
@Transactional
class DefaultRetrieveClienteTest {

	@Autowired
	private DefaultRetrieveCliente defaultRetrieveCliente;

	@Autowired
	private DefaultCreateCliente defaultCreateCliente;

	@BeforeEach
	public void init(){
		this.defaultCreateCliente.execute(this.createCliente());
	}

	@Test
	final void executeAllSuccess() {
		Page<Cliente> clientes = this.defaultRetrieveCliente.execute(Pageable.unpaged());
		Assertions.assertNotNull(clientes);
		Assertions.assertTrue(clientes.hasContent(), "Validando se a paginação tem conteúdo");
	}

	@Test
	final void errorValidationException() {
		Exception exception = assertThrows(ValidationException.class, () -> {
			this.defaultRetrieveCliente.execute((Long) null);
		});
		assertEquals("O parâmetro id é obrigatório para localizar um cliente", exception.getMessage());
	}

	@Test
	final void errorResourceNotFoundException() {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			this.defaultRetrieveCliente.execute(10L);
		});
		assertEquals("Não foi possível localizar um cliente para o id informado", exception.getMessage());
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
