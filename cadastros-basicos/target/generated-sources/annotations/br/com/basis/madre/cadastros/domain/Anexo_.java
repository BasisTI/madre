package br.com.basis.madre.cadastros.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Anexo.class)
public abstract class Anexo_ {

	public static volatile SingularAttribute<Anexo, Float> tamanhoArquivo;
	public static volatile SingularAttribute<Anexo, String> nomeArquivo;
	public static volatile SingularAttribute<Anexo, byte[]> arquivoAnexo;
	public static volatile SingularAttribute<Anexo, String> arquivoAnexoContentType;
	public static volatile SingularAttribute<Anexo, Long> id;
	public static volatile SingularAttribute<Anexo, LocalDate> dataCriacao;

}

