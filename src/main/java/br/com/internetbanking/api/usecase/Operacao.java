package br.com.internetbanking.api.usecase;

import java.math.BigDecimal;

public interface Operacao {

    default BigDecimal calcularTaxa(BigDecimal valor, boolean planoExclusive) {
        if (planoExclusive) {
            return BigDecimal.ZERO;
        } else if (valor.compareTo(BigDecimal.valueOf(100)) <= 0) {
            return BigDecimal.ZERO;
        } else if (valor.compareTo(BigDecimal.valueOf(300)) <= 0) {
            return valor.multiply(BigDecimal.valueOf(0.004));
        } else {
            return valor.multiply(BigDecimal.valueOf(0.01));
        }
    }
}
