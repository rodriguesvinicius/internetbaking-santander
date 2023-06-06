package br.com.internetbanking.api.controller;

import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.internal.configuration.InternetBankingConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequestMapping(InternetBankingConstants.API_VERSION_V1 + "/clientes")
@Api(tags = "Cliente")
public interface ClienteController {

    @GetMapping
    @ApiOperation("Listar de forma paginada todos os clientes")
    ResponseEntity<Page<Cliente>> getAll(Pageable pageable);

    @GetMapping("/{idCliente}")
    @ApiOperation("Buscar um cliente por um determinado id")
    ResponseEntity<Cliente> getById(@PathVariable Long idCliente);

    @GetMapping("/{idCliente}/transacoes")
    @ApiOperation("Buscar todas as transações por filtro")
    ResponseEntity<Page<Transacao>> getByFilters(@PathVariable Long idCliente,
                                                 @RequestParam(value = "dataCriacao", required = false) LocalDate dataCriacao,
                                                 @RequestParam(value = "tipoTransacao", required = false) TipoTransacao tipoTransacao,
                                                 Pageable pageable);
    @PostMapping
    @ApiOperation("Criar um cliente")
    ResponseEntity<Cliente> save(@Valid @RequestBody ClienteRequestDto clienteRequestDto);

    @PostMapping("/{idCliente}/sacar")
    @ApiOperation("Realizar saque")
    ResponseEntity<Transacao> withdraw(@PathVariable Long idCliente, @Valid @RequestBody TransacaoRequestDto transacaoRequestDto);

    @PostMapping("/{idCliente}/depositar")
    @ApiOperation("Realizar deposíto")
    ResponseEntity<Transacao> deposit(@PathVariable Long idCliente, @Valid @RequestBody TransacaoRequestDto transacaoRequestDto);

    @PutMapping("/{idCliente}")
    @ApiOperation("Atualizar cliente")
    ResponseEntity<Cliente> update(@PathVariable Long idCliente, @Valid @RequestBody ClienteUpdateRequestDto clienteUpdateRequestDto);

    @DeleteMapping("/{idCliente}")
    @ApiOperation("Deletar um cliente")
    ResponseEntity<Void> delete(@PathVariable Long idCliente);
}
