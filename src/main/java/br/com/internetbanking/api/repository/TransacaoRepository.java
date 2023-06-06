package br.com.internetbanking.api.repository;

import br.com.internetbanking.api.model.TipoTransacao;
import br.com.internetbanking.api.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    @Query("SELECT t FROM Transacao t " +
            "WHERE (:id IS NULL OR t.cliente.id = :id) " +
            "AND (:dataCriacao IS NULL OR t.dataCriacao = :dataCriacao) " +
            "AND (:tipoTransacao IS NULL OR t.tipoTransacao = :tipoTransacao)")
    Page<Transacao> findAll(
            @Param("id") Long id,
            @Param("dataCriacao") LocalDate dataCriacao,
            @Param("tipoTransacao") TipoTransacao tipoTransacao,
            Pageable pageable);
}
