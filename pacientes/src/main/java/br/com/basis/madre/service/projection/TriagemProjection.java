package br.com.basis.madre.service.projection;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.HashMap;
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

    default int compareTo(TriagemProjection o){
        Map<ClassificacaoDeRisco, Integer> associativo = new HashMap<>();
        associativo.put(ClassificacaoDeRisco.EMERGENCIA, 10);
        associativo.put(ClassificacaoDeRisco.MUITO_URGENTE, 20);
        associativo.put(ClassificacaoDeRisco.URGENTE, 30);
        associativo.put(ClassificacaoDeRisco.POUCO_URGENTE, 40);
        associativo.put(ClassificacaoDeRisco.NAO_URGENTE, 50);

        if(associativo.get(this.getClassificacaoDeRisco()) < associativo.get(o.getClassificacaoDeRisco())){
            return -1;
        }
        else if(associativo.get(this.getClassificacaoDeRisco()) > associativo.get(o.getClassificacaoDeRisco())){
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
