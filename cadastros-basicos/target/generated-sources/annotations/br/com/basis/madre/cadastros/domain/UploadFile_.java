package br.com.basis.madre.cadastros.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UploadFile.class)
public abstract class UploadFile_ {

	public static volatile SingularAttribute<UploadFile, String> originalName;
	public static volatile SingularAttribute<UploadFile, String> filename;
	public static volatile SingularAttribute<UploadFile, Date> dateOf;
	public static volatile SingularAttribute<UploadFile, Integer> sizeOf;
	public static volatile SingularAttribute<UploadFile, Long> id;
	public static volatile SingularAttribute<UploadFile, Integer> processType;

}

