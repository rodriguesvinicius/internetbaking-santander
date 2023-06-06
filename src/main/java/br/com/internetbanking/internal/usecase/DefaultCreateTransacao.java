package br.com.internetbanking.internal.usecase;

import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.api.repository.TransacaoRepository;
import br.com.internetbanking.api.usecase.CreateTransacao;
import br.com.internetbanking.api.usecase.Operacao;
import br.com.internetbanking.api.usecase.RetrieveCliente;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@ConditionalOnSingleCandidate(CreateTransacao.class)
@Slf4j
public class DefaultCreateTransacao implements CreateTransacao, Operacao {

    private final TransacaoRepository transacaoRepository;
    private final RetrieveCliente retrieveCliente;

    @Override
    @Transactional
    public Transacao execute(Long idCliente, TransacaoRequestDto transacaoRequestDto) {
        Cliente cliente = this.retrieveCliente.execute(idCliente);
        if (transacaoRequestDto.getTipoTransacao().equals(TipoTransacao.SAQUE)) {
            return this.sacar(cliente, transacaoRequestDto);
        } else {
            return this.depositar(cliente, transacaoRequestDto);
        }
    }

    private Transacao sacar(Cliente cliente, TransacaoRequestDto transacaoRequestDto) {
        BigDecimal taxa = calcularTaxa(transacaoRequestDto.getValor(), cliente.getIsPlanoExclusive());
        BigDecimal valorLiquido = transacaoRequestDto.getValor().subtract(taxa);

        validarValorSaque(valorLiquido, cliente.getSaldo());
        cliente.setSaldo(cliente.getSaldo().subtract(valorLiquido));
        return salvarTransacao(transacaoRequestDto, TipoTransacao.SAQUE, cliente, taxa, valorLiquido);
    }

    private Transacao depositar(Cliente cliente, TransacaoRequestDto transacaoRequestDto) {
        validarValorDeposito(transacaoRequestDto.getValor());
        cliente.setSaldo(cliente.getSaldo().add(transacaoRequestDto.getValor()));
        return salvarTransacao(transacaoRequestDto, TipoTransacao.DEPOSITO, cliente, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private void validarValorSaque(BigDecimal valorLiquido, BigDecimal saldo) {
        if (valorLiquido.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor a ser sacado é inválido.");
        }

        if (saldo.compareTo(valorLiquido) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
    }

    private void validarValorDeposito(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor de depósito deve ser maior que zero.");
        }
    }

    private Transacao salvarTransacao(TransacaoRequestDto transacaoRequestDto, TipoTransacao tipoTransacao,
                                      Cliente cliente, BigDecimal taxa, BigDecimal valorLiquido) {
        Transacao transacao = new Transacao();
        transacao.setTipoTransacao(tipoTransacao);
        transacao.setDataCriacao(LocalDate.now());
        transacao.setCliente(cliente);
        transacao.setValor(transacaoRequestDto.getValor().setScale(2, RoundingMode.DOWN));
        transacao.setValorTaxa(taxa.setScale(2, RoundingMode.DOWN));
        transacao.setValorLiquido(valorLiquido.setScale(2, RoundingMode.DOWN));

        log.info("[DEFAULT-CREATE-TRANSACAO] {} realizado com sucesso para o cliente: {}", tipoTransacao, cliente.getId());

        return this.transacaoRepository.save(transacao);
    }
}
