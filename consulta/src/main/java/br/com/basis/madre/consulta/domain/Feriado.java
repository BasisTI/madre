package br.com.basis.madre.consulta.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TB_FERIADO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Feriado implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ID_EMBALAGEM = "ID_EMBALAGEM";
    private static final String DATA_FERIADO = "DATA_FERIADO";
    private static final String TURNO = "TURNO";
    private static final String SITUACAO = "SITUACAO";
    private static final String DIA_SEMANA = "DIA_SEMANA";

    @Id
    @Column(name = ID_EMBALAGEM)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FERIADO")
    @SequenceGenerator(name = "SQ_FERIADO", sequenceName = "SQ_FERIADO", allocationSize = 1)
    private Long id;

    @Column(name = DATA_FERIADO, nullable = false)
    private LocalDateTime data;

    @Column(name = TURNO, nullable = false)
    private String turno;

    @Column(name = DIA_SEMANA, nullable = false)
    private String diaSemana;

    @Column(name = SITUACAO, nullable = false)
    private Boolean situacao;
}
