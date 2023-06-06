package br.com.internetbanking.api.dto;

import br.com.internetbanking.api.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteUpdateRequestDto {

    private String nome;

    private Boolean isPlanoExclusive;

    private String numeroConta;

    private LocalDate dataNascimento;

    public static void prepareEntityToUpdate(Cliente entity, ClienteUpdateRequestDto clientUpdateRequestDto) {
        entity.setNome(clientUpdateRequestDto.getNome() == null ? entity.getNome() : clientUpdateRequestDto.getNome());
        entity.setNumeroConta(clientUpdateRequestDto.getNumeroConta() == null ? entity.getNumeroConta() : clientUpdateRequestDto.getNumeroConta());
        entity.setIsPlanoExclusive(clientUpdateRequestDto.getIsPlanoExclusive() == null ? entity.getIsPlanoExclusive() : clientUpdateRequestDto.getIsPlanoExclusive());
        entity.setDataNascimento(clientUpdateRequestDto.getDataNascimento() == null ? entity.getDataNascimento() : clientUpdateRequestDto.getDataNascimento());
    }
}
