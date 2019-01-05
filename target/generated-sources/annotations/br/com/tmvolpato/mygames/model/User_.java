package br.com.tmvolpato.mygames.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends br.com.tmvolpato.mygames.model.AbstractPersistable_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SetAttribute<User, Game> games;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> enabled;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String GAMES = "games";
	public static final String EMAIL = "email";
	public static final String ENABLED = "enabled";

}

