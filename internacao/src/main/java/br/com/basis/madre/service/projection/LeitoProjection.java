package br.com.basis.madre.service.projection;

public interface LeitoProjection {
    Long getId();
    String getNome();
    Integer getAndar();
    Integer getAla();
    SituacaoDeLeitoProjection getSituacao();
    UnidadeFuncionalProjection getUnidadeFuncional();

    static interface SituacaoDeLeitoProjection {
        Long getId();
        String getNome();
    }

    static interface UnidadeFuncionalProjection {
        Long getId();
        String getNome();
    }
}
