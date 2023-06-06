package br.com.internetbanking.usecase;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.configuration.InternetBankingTestConfiguration;
import br.com.internetbanking.internal.usecase.DefaultCreateCliente;
import br.com.internetbanking.internal.usecase.DefaultCreateTransacao;
import br.com.internetbanking.internal.usecase.DefaultUpdateCliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class,
        classes = { InternetBankingTestConfiguration.class })
@Transactional
class DefaultCreateTransacaoTest {

    @Autowired
    private DefaultCreateTransacao defaultCreateTransacao;

    @Autowired
    private DefaultCreateCliente defaultCreateCliente;

    @Autowired
    private DefaultUpdateCliente defaultUpdateCliente;

    @Test
    final void executeCreateTransacaoDeposito() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        Transacao transacao = this.defaultCreateTransacao.execute(cliente.getId(), createDeposito());

        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.DOWN), transacao.getValor());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.DOWN), transacao.getValorTaxa());
    }

    @Test
    final void executeCreateTransacaoSaque() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        this.defaultCreateTransacao.execute(cliente.getId(), createDeposito());

        Transacao transacao = this.defaultCreateTransacao.execute(cliente.getId(), createSaque());

        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.DOWN), transacao.getValor());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.DOWN), transacao.getValorTaxa());
        Assertions.assertFalse(cliente.getIsPlanoExclusive());
    }

    @Test
    final void executeCreateTransacaoSaque300() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        TransacaoRequestDto depositoDto = createDeposito();
        depositoDto.setValor(BigDecimal.valueOf(300.00));
        this.defaultCreateTransacao.execute(cliente.getId(), depositoDto);

        TransacaoRequestDto saqueDto = createSaque();
        saqueDto.setValor(BigDecimal.valueOf(300.00));
        Transacao transacao = this.defaultCreateTransacao.execute(cliente.getId(), saqueDto);

        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(BigDecimal.valueOf(300.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValor());
        Assertions.assertEquals(BigDecimal.valueOf(1.20).setScale(2, RoundingMode.HALF_DOWN), transacao.getValorTaxa());
        Assertions.assertEquals(BigDecimal.valueOf(298.80).setScale(2, RoundingMode.HALF_DOWN), transacao.getValorLiquido());
        Assertions.assertFalse(cliente.getIsPlanoExclusive());
    }

    @Test
    final void executeCreateTransacaoSaque500Isento() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        ClienteUpdateRequestDto clienteUpdateRequestDto = new ClienteUpdateRequestDto();
        clienteUpdateRequestDto.setIsPlanoExclusive(Boolean.TRUE);

        this.defaultUpdateCliente.execute(cliente.getId(), clienteUpdateRequestDto);

        TransacaoRequestDto depositoDto = createDeposito();
        depositoDto.setValor(BigDecimal.valueOf(500.00));
        this.defaultCreateTransacao.execute(cliente.getId(), depositoDto);

        TransacaoRequestDto saqueDto = createSaque();
        saqueDto.setValor(BigDecimal.valueOf(500.00));
        Transacao transacao = this.defaultCreateTransacao.execute(cliente.getId(), saqueDto);

        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValor());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN), transacao.getValorTaxa());
        Assertions.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValorLiquido());
        Assertions.assertTrue(cliente.getIsPlanoExclusive());
    }

    @Test
    final void executeCreateTransacaoSaque500() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());

        TransacaoRequestDto depositoDto = createDeposito();
        depositoDto.setValor(BigDecimal.valueOf(500.00));
        this.defaultCreateTransacao.execute(cliente.getId(), depositoDto);

        TransacaoRequestDto saqueDto = createSaque();
        saqueDto.setValor(BigDecimal.valueOf(500.00));
        Transacao transacao = this.defaultCreateTransacao.execute(cliente.getId(), saqueDto);

        Assertions.assertNotNull(transacao);
        Assertions.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValor());
        Assertions.assertEquals(BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValorTaxa());
        Assertions.assertEquals(BigDecimal.valueOf(495.00).setScale(2, RoundingMode.HALF_DOWN), transacao.getValorLiquido());
        Assertions.assertFalse(cliente.getIsPlanoExclusive());
    }

    @Test
    final void executeCreateTransacaoDepositoInvalido() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());
        TransacaoRequestDto dto = this.createDeposito();
        dto.setValor(BigDecimal.ZERO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.defaultCreateTransacao.execute(cliente.getId(), dto);
        });
        assertEquals("O valor de depósito deve ser maior que zero.", exception.getMessage());
    }

    @Test
    final void executeCreateTransacaoSaqueInvalido() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());
        TransacaoRequestDto dto = this.createSaque();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.defaultCreateTransacao.execute(cliente.getId(), dto);
        });
        assertEquals("Saldo insuficiente para realizar o saque.", exception.getMessage());
    }

    @Test
    final void executeCreateTransacaoSaqueInvalidoNegativo() {

        Cliente cliente = this.defaultCreateCliente.execute(createCliente());
        TransacaoRequestDto dto = this.createSaque();
        dto.setValor(BigDecimal.valueOf(-1L));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.defaultCreateTransacao.execute(cliente.getId(), dto);
        });
        assertEquals("O valor a ser sacado é inválido.", exception.getMessage());
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
