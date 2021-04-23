package br.com.basis.madre.service.projection;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.Map;

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

    Map<ClassificacaoDeRisco, Integer> niveisEmergencia = ClassificacaoDeRisco.getNiveisEmergencia();

    default int compareTo(TriagemProjection o){
        if(niveisEmergencia.get(this.getClassificacaoDeRisco()) < niveisEmergencia.get(o.getClassificacaoDeRisco())){
            return -1;
        }
        else if(niveisEmergencia.get(this.getClassificacaoDeRisco()) > niveisEmergencia.get(o.getClassificacaoDeRisco())){
            return 1;
        }
        else{
            if(this.getDataHoraDoAtendimento() != null && o.getDataHoraDoAtendimento() != null){
                return this.getDataHoraDoAtendimento().compareTo(o.getDataHoraDoAtendimento());
            }
            return 0;
        }
    }
}
