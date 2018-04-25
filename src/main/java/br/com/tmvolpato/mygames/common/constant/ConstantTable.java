package br.com.tmvolpato.mygames.common.constant;

/**
 * Tabelas do sistema.
 * 
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ConstantTable {

    public static final String USERS = "users";
    public static final String GAME = "game";
    public static final String PLATFORM = "platform";
    public static final String GENRE = "genre";
    public static final String COMPANY = "company";
    public static final String ROLE = "role";
    public static final String PRIVILEGE = "privilege";
    public static final String USER_HAS_ROLE = "user_has_role";
    public static final String ROLE_HAS_PRIVILEGE = "role_has_privilege";

    private ConstantTable() {
        throw new AssertionError();
    }
}
