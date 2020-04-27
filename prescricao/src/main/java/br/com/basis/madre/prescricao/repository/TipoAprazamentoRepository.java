package br.com.basis.madre.prescricao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.basis.madre.prescricao.domain.TipoAprazamento;

@Repository
public interface TipoAprazamentoRepository extends JpaRepository<TipoAprazamento, Long>{

}
