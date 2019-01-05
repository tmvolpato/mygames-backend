package br.com.tmvolpato.mygames.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Game.class)
public abstract class Game_ extends br.com.tmvolpato.mygames.model.AbstractPersistable_ {

	public static volatile SingularAttribute<Game, BigDecimal> price;
	public static volatile SingularAttribute<Game, Genre> genre;
	public static volatile SingularAttribute<Game, Company> company;
	public static volatile SingularAttribute<Game, String> title;
	public static volatile SingularAttribute<Game, User> user;
	public static volatile SingularAttribute<Game, Platform> platform;

	public static final String PRICE = "price";
	public static final String GENRE = "genre";
	public static final String COMPANY = "company";
	public static final String TITLE = "title";
	public static final String USER = "user";
	public static final String PLATFORM = "platform";

}

