package br.com.internetbanking.api.dto;

import br.com.internetbanking.api.model.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequestDto {

    private BigDecimal valor;

    private TipoTransacao tipoTransacao;
}
