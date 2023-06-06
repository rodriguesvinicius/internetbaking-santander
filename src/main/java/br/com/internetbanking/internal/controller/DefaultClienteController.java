package br.com.internetbanking.internal.controller;

import br.com.internetbanking.api.controller.ClienteController;
import br.com.internetbanking.api.dto.ClienteRequestDto;
import br.com.internetbanking.api.dto.ClienteUpdateRequestDto;
import br.com.internetbanking.api.dto.TransacaoRequestDto;
import br.com.internetbanking.api.model.Cliente;
import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import br.com.internetbanking.api.usecase.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class DefaultClienteController implements ClienteController {

    private final RetrieveCliente retrieveCliente;

    private final CreateCliente createCliente;

    private final UpdateCliente updateCliente;

    private final DeleteCliente deleteCliente;

    private final RetrieveTransacao retrieveTransacao;

    private final CreateTransacao createTransacao;

    @Override
    public ResponseEntity<Page<Cliente>> getAll(Pageable pageable) {
        return new ResponseEntity<>(this.retrieveCliente.execute(pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Cliente> getById(Long idCliente) {
        return new ResponseEntity<>(this.retrieveCliente.execute(idCliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<Transacao>> getByFilters(Long idCliente, LocalDate dataCriacao, TipoTransacao tipoTransacao, Pageable pageable) {
        return new ResponseEntity<>(this.retrieveTransacao.execute(idCliente, dataCriacao, tipoTransacao, pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Cliente> save(ClienteRequestDto clienteRequestDto) {
        return new ResponseEntity<>(this.createCliente.execute(clienteRequestDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Transacao> withdraw(Long idCliente, TransacaoRequestDto transacaoRequestDto) {
        return new ResponseEntity<>(this.createTransacao.execute(idCliente,transacaoRequestDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Transacao> deposit(Long idCliente, TransacaoRequestDto transacaoRequestDto) {
        return new ResponseEntity<>(this.createTransacao.execute(idCliente,transacaoRequestDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Cliente> update(Long idCliente, ClienteUpdateRequestDto clienteUpdateRequestDto) {
        return new ResponseEntity<>(this.updateCliente.execute(idCliente, clienteUpdateRequestDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long idCliente) {
        this.deleteCliente.execute(idCliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
