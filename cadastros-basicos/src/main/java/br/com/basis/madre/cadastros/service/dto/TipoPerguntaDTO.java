package br.com.basis.madre.cadastros.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoPergunta entity.
 */
public class TipoPerguntaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String enunciadoPergunta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciadoPergunta() {
        return enunciadoPergunta;
    }

    public void setEnunciadoPergunta(String enunciadoPergunta) {
        this.enunciadoPergunta = enunciadoPergunta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoPerguntaDTO tipoPerguntaDTO = (TipoPerguntaDTO) o;
        if(tipoPerguntaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoPerguntaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoPerguntaDTO{" +
            "id=" + getId() +
            ", enunciadoPergunta='" + getEnunciadoPergunta() + "'" +
            "}";
    }
}
