package br.com.basis.madre.service.projection;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TriagemProjection{

    Long getId();
    ZonedDateTime getDataHoraDoAtendimento();
    ClassificacaoDeRisco getClassificacaoDeRisco();
    PacienteProjection getPaciente();

    static interface PacienteProjection {
        Long getId();
        String getNome();
        String getNomeSocial();
        GenitoresProjection getGenitores();

        static interface GenitoresProjection {
            String getNomeDaMae();
        }
    }


    default int compareTo(TriagemProjection o){
        if(this.getClassificacaoDeRisco().nivelEmergencia().compareTo(o.getClassificacaoDeRisco().nivelEmergencia()) == 0){
            ZonedDateTime thisHoraDeAtendimento = this.getDataHoraDoAtendimento() == null ? ZonedDateTime.now() : this.getDataHoraDoAtendimento();
            ZonedDateTime otherHoraDeAtendimento = o.getDataHoraDoAtendimento() == null ? ZonedDateTime.now() : o.getDataHoraDoAtendimento();
            return thisHoraDeAtendimento.compareTo(otherHoraDeAtendimento);
        }
        else{
            return this.getClassificacaoDeRisco().nivelEmergencia().compareTo(o.getClassificacaoDeRisco().nivelEmergencia());
        }
    }
}
