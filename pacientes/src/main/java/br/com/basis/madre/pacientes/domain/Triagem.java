package br.com.basis.madre.pacientes.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Triagem.
 */
@Entity
@Table(name = "triagem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "triagem")
public class Triagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_atendimento")
    private LocalDate dataAtendimento;

    @Max(value = 15)
    @Column(name = "pressao_arterial")
    private Integer pressaoArterial;

    @Max(value = 15)
    @Column(name = "frequencia_respiratoria")
    private Integer frequenciaRespiratoria;

    @Max(value = 3)
    @Column(name = "temperatura")
    private Integer temperatura;

    @Max(value = 3)
    @Column(name = "peso")
    private Integer peso;

    @Column(name = "hora_atendimento")
    private ZonedDateTime horaAtendimento;

    @Max(value = 10)
    @Column(name = "frequencia_cardiaca")
    private Integer frequenciaCardiaca;

    @Max(value = 5)
    @Column(name = "glicemia")
    private Integer glicemia;

    @Max(value = 10)
    @Column(name = "saturacao")
    private Integer saturacao;

    @DecimalMax(value = "4")
    @Column(name = "altura")
    private Float altura;

    @Size(max = 1000)
    @Column(name = "medicacao_continua", length = 1000)
    private String medicacaoContinua;

    @Size(max = 1000)
    @Column(name = "ferida_lesao", length = 1000)
    private String feridaLesao;

    @Size(max = 1000)
    @Column(name = "alergias", length = 1000)
    private String alergias;

    @Size(max = 20)
    @Column(name = "estado_paciente", length = 20)
    private String estadoPaciente;

    @Size(max = 30)
    @Column(name = "sintoma_alerta", length = 30)
    private String sintomaAlerta;

    @Size(max = 30)
    @Column(name = "historico_saude", length = 30)
    private String historicoSaude;

    @Size(max = 30)
    @Column(name = "estado_geral", length = 30)
    private String estadoGeral;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public Triagem dataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
        return this;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Integer getPressaoArterial() {
        return pressaoArterial;
    }

    public Triagem pressaoArterial(Integer pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
        return this;
    }

    public void setPressaoArterial(Integer pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public Integer getFrequenciaRespiratoria() {
        return frequenciaRespiratoria;
    }

    public Triagem frequenciaRespiratoria(Integer frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
        return this;
    }

    public void setFrequenciaRespiratoria(Integer frequenciaRespiratoria) {
        this.frequenciaRespiratoria = frequenciaRespiratoria;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public Triagem temperatura(Integer temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getPeso() {
        return peso;
    }

    public Triagem peso(Integer peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public ZonedDateTime getHoraAtendimento() {
        return horaAtendimento;
    }

    public Triagem horaAtendimento(ZonedDateTime horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
        return this;
    }

    public void setHoraAtendimento(ZonedDateTime horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public Integer getFrequenciaCardiaca() {
        return frequenciaCardiaca;
    }

    public Triagem frequenciaCardiaca(Integer frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
        return this;
    }

    public void setFrequenciaCardiaca(Integer frequenciaCardiaca) {
        this.frequenciaCardiaca = frequenciaCardiaca;
    }

    public Integer getGlicemia() {
        return glicemia;
    }

    public Triagem glicemia(Integer glicemia) {
        this.glicemia = glicemia;
        return this;
    }

    public void setGlicemia(Integer glicemia) {
        this.glicemia = glicemia;
    }

    public Integer getSaturacao() {
        return saturacao;
    }

    public Triagem saturacao(Integer saturacao) {
        this.saturacao = saturacao;
        return this;
    }

    public void setSaturacao(Integer saturacao) {
        this.saturacao = saturacao;
    }

    public Float getAltura() {
        return altura;
    }

    public Triagem altura(Float altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getMedicacaoContinua() {
        return medicacaoContinua;
    }

    public Triagem medicacaoContinua(String medicacaoContinua) {
        this.medicacaoContinua = medicacaoContinua;
        return this;
    }

    public void setMedicacaoContinua(String medicacaoContinua) {
        this.medicacaoContinua = medicacaoContinua;
    }

    public String getFeridaLesao() {
        return feridaLesao;
    }

    public Triagem feridaLesao(String feridaLesao) {
        this.feridaLesao = feridaLesao;
        return this;
    }

    public void setFeridaLesao(String feridaLesao) {
        this.feridaLesao = feridaLesao;
    }

    public String getAlergias() {
        return alergias;
    }

    public Triagem alergias(String alergias) {
        this.alergias = alergias;
        return this;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getEstadoPaciente() {
        return estadoPaciente;
    }

    public Triagem estadoPaciente(String estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
        return this;
    }

    public void setEstadoPaciente(String estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
    }

    public String getSintomaAlerta() {
        return sintomaAlerta;
    }

    public Triagem sintomaAlerta(String sintomaAlerta) {
        this.sintomaAlerta = sintomaAlerta;
        return this;
    }

    public void setSintomaAlerta(String sintomaAlerta) {
        this.sintomaAlerta = sintomaAlerta;
    }

    public String getHistoricoSaude() {
        return historicoSaude;
    }

    public Triagem historicoSaude(String historicoSaude) {
        this.historicoSaude = historicoSaude;
        return this;
    }

    public void setHistoricoSaude(String historicoSaude) {
        this.historicoSaude = historicoSaude;
    }

    public String getEstadoGeral() {
        return estadoGeral;
    }

    public Triagem estadoGeral(String estadoGeral) {
        this.estadoGeral = estadoGeral;
        return this;
    }

    public void setEstadoGeral(String estadoGeral) {
        this.estadoGeral = estadoGeral;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triagem triagem = (Triagem) o;
        if (triagem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), triagem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Triagem{" +
            "id=" + getId() +
            ", dataAtendimento='" + getDataAtendimento() + "'" +
            ", pressaoArterial=" + getPressaoArterial() +
            ", frequenciaRespiratoria=" + getFrequenciaRespiratoria() +
            ", temperatura=" + getTemperatura() +
            ", peso=" + getPeso() +
            ", horaAtendimento='" + getHoraAtendimento() + "'" +
            ", frequenciaCardiaca=" + getFrequenciaCardiaca() +
            ", glicemia=" + getGlicemia() +
            ", saturacao=" + getSaturacao() +
            ", altura=" + getAltura() +
            ", medicacaoContinua='" + getMedicacaoContinua() + "'" +
            ", feridaLesao='" + getFeridaLesao() + "'" +
            ", alergias='" + getAlergias() + "'" +
            ", estadoPaciente='" + getEstadoPaciente() + "'" +
            ", sintomaAlerta='" + getSintomaAlerta() + "'" +
            ", historicoSaude='" + getHistoricoSaude() + "'" +
            ", estadoGeral='" + getEstadoGeral() + "'" +
            "}";
    }
}
