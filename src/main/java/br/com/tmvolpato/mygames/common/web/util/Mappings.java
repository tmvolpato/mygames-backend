package br.com.tmvolpato.mygames.common.web.util;

import br.com.tmvolpato.mygames.common.constant.ConstantWeb;

/**
 * Mapeamentos de recursos.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class Mappings {

    public static final String DOCUMENTATION_API = ConstantWeb.SLASH + "documentation-api";
    public static final String USERS = ConstantWeb.API + "users";
    public static final String GAMES = ConstantWeb.API + "games";



    private Mappings() {
        throw new AssertionError();
    }
}
