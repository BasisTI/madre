package br.com.basis.suprimentos.domain.projection;

import java.util.Set;

public interface Estoque {
    Long getId();

    Boolean getAtivo();

    Boolean getEstocavel();

    Long getQuantidadeBloqueada();

    Long getQuantidadeComProblema();

    Long getQuantidadeDisponivel();

    _Fornecedor getFornecedor();

    _Material getMaterial();

    _Almoxarifado getAlmoxarifado();

    Set<_Lote> getLotes();

    interface _Lote {
        Long getId();

        Long getQuantidadeBloqueada();

        Long getQuantidadeDisponivel();

        Long getQuantidadeProblema();
    }

    interface _Almoxarifado {
        Long getId();

        String getDescricao();
    }

    interface _Fornecedor {
        Long getId();

        String getNomeFantasia();
    }

    interface _Material {
        Long getId();
        String getNome();
        _GrupoMaterial getGrupo();
        _UnidadeMedida getUnidadeMedida();

        interface _GrupoMaterial {
            Long getId();
            String getDescricao();
        }

        interface _UnidadeMedida {
            Long getId();
            String getDescricao();
        }
    }
}
