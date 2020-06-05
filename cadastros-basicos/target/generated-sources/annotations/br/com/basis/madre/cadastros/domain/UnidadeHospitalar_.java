package br.com.basis.madre.cadastros.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UnidadeHospitalar.class)
public abstract class UnidadeHospitalar_ {

	public static volatile SingularAttribute<UnidadeHospitalar, Boolean> ativo;
	public static volatile SingularAttribute<UnidadeHospitalar, String> sigla;
	public static volatile SingularAttribute<UnidadeHospitalar, String> endereco;
	public static volatile SingularAttribute<UnidadeHospitalar, byte[]> logo;
	public static volatile SingularAttribute<UnidadeHospitalar, String> logoContentType;
	public static volatile SingularAttribute<UnidadeHospitalar, String> nome;
	public static volatile SingularAttribute<UnidadeHospitalar, Long> id;
	public static volatile SingularAttribute<UnidadeHospitalar, String> cnpj;
	public static volatile ListAttribute<UnidadeHospitalar, Usuario> usuarios;

}

