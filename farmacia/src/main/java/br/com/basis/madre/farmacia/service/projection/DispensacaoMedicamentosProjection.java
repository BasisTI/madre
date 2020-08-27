package br.com.basis.madre.farmacia.service.projection;

import br.com.basis.madre.farmacia.domain.Apresentacao;
import br.com.basis.madre.farmacia.domain.TipoMedicamento;
import br.com.basis.madre.farmacia.domain.Unidade;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface DispensacaoMedicamentosProjection {


     Long getId();


     Long getIdFarmacia();


     Boolean getDispensado();


     Long getUsuarioQueDispensou();


     Boolean getEstornado();

    Medicamento getMedicamentos();

    interface Medicamento {
        Long getId();


        String getCodigo();


        String getNome();


        String getDescricao();


        String getConcentracao();


        Boolean getAtivo();


        TipoMedicamento getTipoMedicamento();

        interface TipoMedicamento {
            String getNome();
        }

        Apresentacao getApresentacao();

        interface Apresentacao {
            String getNome();
        }

        Unidade getUnidade();

        interface Unidade {
            String getNome();
        }
    }

//    Dispensacao getDispensacao();
//
//    interface Dispensacao {
//
//        Long getId();
//
//
//
//
//    }


}
