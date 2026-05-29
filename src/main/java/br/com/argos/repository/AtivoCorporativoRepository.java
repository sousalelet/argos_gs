package br.com.argos.repository;

import br.com.argos.ativo.AtivoCorporativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtivoCorporativoRepository extends JpaRepository<AtivoCorporativo, Long> {
    Page<AtivoCorporativo> findAllByAtivoTrue(Pageable pageable);
    Optional<AtivoCorporativo> findByIdAndAtivoTrue(Long id);
    boolean existsByNomeAndAtivoTrue(String nome);
    boolean existsByNomeAndAtivoTrueAndIdNot(String nome, Long id);
}
