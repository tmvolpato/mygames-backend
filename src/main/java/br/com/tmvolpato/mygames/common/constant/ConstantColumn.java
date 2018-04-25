package br.com.tmvolpato.mygames.common.constant;

/**
 * Colunas de tabelas do sistema.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
public final class ConstantColumn {

    public static final String ID = "id";
    public static final String DATE_TIME_INCLUSION = "date_time_inclusion";
    public static final String DATE_TIME_LAST_EDITION = "date_time_last_edition";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ENABLED = "enabled";
    public static final String TITLE = "title";
    public static final String PLATFORM = "platform";
    public static final String COMPANY = "company";
    public static final String GENRE = "genre";
    public static final String PRICE = "price";
    public static final String USER = "user";
    public static final String PLATFORM_ID = "platform_id";
    public static final String GENRE_ID = "genre_id";
    public static final String ROLE_ID = "role_id";
    public static final String PRIVILEGE_ID = "privilege_id";
    public static final String COMPANY_ID = "company_id";
    public static final String USER_ID = "user_id";

    private ConstantColumn() {
        throw new AssertionError();
    }
}
