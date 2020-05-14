package br.com.basis.madre.farmacia.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Document(indexName = "prescricao")
public class Prescricao implements Serializable {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String descricao;

    @NotNull
    @Field(type = FieldType.Text)
    private String nome;

    @Field(type = FieldType.Text)
    private String farmacia;

    @Field(type = FieldType.Text)
    private String unidade;

    @Field(type = FieldType.Date)
    private LocalDate dataInicio;

    @Field(type = FieldType.Date)
    private LocalDateTime dataFim;

    @Field(type = FieldType.Text)
    private String local;

    public Prescricao(String nome, LocalDate dataInicio, LocalDateTime dataFim, String local) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
    }

    public Prescricao() {
    }

//    public Prescricao(String descricao, String nome, String farmacia, String unidade) {
//        this.descricao = descricao;
//        this.nome = nome;
//        this.farmacia = farmacia;
//        this.unidade = unidade;
//    }


    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescricao that = (Prescricao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
