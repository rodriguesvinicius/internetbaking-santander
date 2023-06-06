package br.com.internetbanking.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "transacao")
@Getter
@Setter
public class Transacao implements Serializable {

    @Serial
    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @NotNull
    @Column(name = "tipo_transacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "valor_taxa", nullable = false)
    private BigDecimal valorTaxa;

    @NotNull
    @Column(name = "valor_liquido", nullable = false)
    private BigDecimal valorLiquido;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Cliente cliente;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transacao transacao = (Transacao) o;
        return getId() != null && Objects.equals(getId(), transacao.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
