package br.com.basis.madre.cadastros.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Acao.class)
public abstract class Acao_ {

	public static volatile SingularAttribute<Acao, Long> id;
	public static volatile SingularAttribute<Acao, String> nmAcao;
	public static volatile SingularAttribute<Acao, String> cdAcao;
	public static volatile SetAttribute<Acao, Funcionalidade> funcionalidades;

}

