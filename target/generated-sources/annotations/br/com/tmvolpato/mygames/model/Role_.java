package br.com.tmvolpato.mygames.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends br.com.tmvolpato.mygames.model.AbstractPersistable_ {

	public static volatile SetAttribute<Role, Privilege> privileges;
	public static volatile SingularAttribute<Role, String> name;

}

