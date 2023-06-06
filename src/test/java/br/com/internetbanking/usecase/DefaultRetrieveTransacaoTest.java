package br.com.internetbanking.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.exception.ResourceNotFoundException;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.configuration.InternetBankingTestConfiguration;
import br.com.internetbanking.internal.usecase.DefaultCreateCliente;
import br.com.internetbanking.internal.usecase.DefaultCreateTransacao;
import br.com.internetbanking.internal.usecase.DefaultRetrieveTransacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
        classes = { InternetBankingTestConfiguration.class })
@Transactional
public class DefaultRetrieveTransacaoTest {

    @Autowired
    private DefaultCreateTransacao defaultCreateTransacao;

    @Autowired
    private DefaultCreateCliente defaultCreateCliente;

    @Autowired
    private DefaultRetrieveTransacao defaultRetrieveTransacao;

    @Test
    final void executeRetrieveTransacaoDeposito() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        this.defaultCreateTransacao.execute(cliente.getId(), createDeposito());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Page<Transacao> transacoes = this.defaultRetrieveTransacao.execute(cliente.getId(), null, TipoTransacao.DEPOSITO, pageable);

        Assertions.assertNotNull(transacoes);
        Assertions.assertTrue(transacoes.hasContent());
        Assertions.assertEquals(TipoTransacao.DEPOSITO, transacoes.getContent().get(0).getTipoTransacao());
    }

    @Test
    final void executeRetrieveTransacaoSaque() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        this.defaultCreateTransacao.execute(cliente.getId(), createDeposito());

        this.defaultCreateTransacao.execute(cliente.getId(), createSaque());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Page<Transacao> transacoes = this.defaultRetrieveTransacao.execute(cliente.getId(), null, TipoTransacao.SAQUE, pageable);

        Assertions.assertNotNull(transacoes);
        Assertions.assertTrue(transacoes.hasContent());
        Assertions.assertEquals(TipoTransacao.SAQUE, transacoes.getContent().get(0).getTipoTransacao());
    }

    @Test
    final void executeRetrieveTransacaoSaqueInexistente() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        this.defaultCreateTransacao.execute(cliente.getId(), createDeposito());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.defaultRetrieveTransacao.execute(cliente.getId(), null, TipoTransacao.SAQUE, pageable);
        });
        assertEquals("Não foi possível localizar transações para o cliente informado", exception.getMessage());
    }

    private ClienteRequestDto createCliente() {
        ClienteRequestDto cliente = new ClienteRequestDto();
        cliente.setNome("Vinicius Alves");
        cliente.setDataNascimento(LocalDate.now());
        cliente.setIsPlanoExclusive(Boolean.FALSE);
        cliente.setNumeroConta("1234567890");
        return cliente;
    }

    private TransacaoRequestDto createSaque() {
        TransacaoRequestDto transacaoRequestDto = new TransacaoRequestDto();
        transacaoRequestDto.setTipoTransacao(TipoTransacao.SAQUE);
        transacaoRequestDto.setValor(BigDecimal.valueOf(10L));
        return transacaoRequestDto;
    }

    private TransacaoRequestDto createDeposito() {
        TransacaoRequestDto transacaoRequestDto = new TransacaoRequestDto();
        transacaoRequestDto.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacaoRequestDto.setValor(BigDecimal.valueOf(10L));
        return transacaoRequestDto;
    }
}
