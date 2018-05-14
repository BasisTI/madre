package br.com.basis.madre.cadastros.service.dto;

import br.com.basis.dynamicexports.pojo.ReportObject;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import  br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
public class UnidadeHospitalarDTO implements ReportObject, Serializable {

        private Long id;



        @NotNull
        @Size(min = 1, max = 10)
        private String sigla;

        @NotNull
        @Size(min = 1, max = 100)
        private String nome;

        @NotNull
        @CNPJ
        @Size(min = 14, max = 14)
        private String cnpj;

        @NotNull
        @Size(min = 1, max = 200)
        private String endereco;

        @NotNull
        private Boolean ativo;

        // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        public String getSigla() {
            return sigla;
        }

        public void setSigla(String sigla) {
            this.sigla = sigla;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public Boolean isAtivo() {
            return ativo;
        }

        public void setAtivo(Boolean ativo) {
            this.ativo = ativo;
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
            UnidadeHospitalar unidadeHospitalar = (br.com.basis.madre.cadastros.domain.UnidadeHospitalar) o;
            if (unidadeHospitalar.getId() == null || getId() == null) {
                return false;
            }
            return Objects.equals(getId(), unidadeHospitalar.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(getId());
        }

        @Override
        public String toString() {
            return "UnidadeHospitalar{" +
                "id=" + getId() +
                ", sigla='" + getSigla() + "'" +
                ", nome='" + getNome() + "'" +
                ", cnpj='" + getCnpj() + "'" +
                ", endereco='" + getEndereco() + "'" +
                ", ativo='" + isAtivo() + "'" +
                "}";
        }
}


