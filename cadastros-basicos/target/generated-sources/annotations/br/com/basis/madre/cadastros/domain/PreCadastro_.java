package br.com.basis.madre.cadastros.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PreCadastro.class)
public abstract class PreCadastro_ {

	public static volatile SingularAttribute<PreCadastro, Boolean> ativo;
	public static volatile SingularAttribute<PreCadastro, String> numCartaoSus;
	public static volatile SingularAttribute<PreCadastro, Long> id;
	public static volatile SingularAttribute<PreCadastro, String> nomeSocial;
	public static volatile SingularAttribute<PreCadastro, String> nomeDaMae;
	public static volatile SingularAttribute<PreCadastro, LocalDate> dataDeNascimento;
	public static volatile SingularAttribute<PreCadastro, String> nomeDoPaciente;

}

