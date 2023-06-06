package br.com.internetbanking.api.dto;

import br.com.internetbanking.api.model.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ClienteRequestDto {

    private String nome;

    private Boolean isPlanoExclusive;

    private String numeroConta;

    private LocalDate dataNascimento;

    public static Cliente fromDto(ClienteRequestDto clienteRequestDto) {
        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.ZERO);
        BeanUtils.copyProperties(clienteRequestDto, cliente);
        return cliente;
    }
}
