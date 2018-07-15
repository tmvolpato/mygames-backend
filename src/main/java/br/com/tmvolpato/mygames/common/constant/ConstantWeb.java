package br.com.tmvolpato.mygames.common.constant;

/**
 * Constant of configuration base url of endpoint.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ConstantWeb {

    public static final String SLASH = "/";
    public static final String API = "api" + SLASH;
    public static final String PUBLIC = API + "public" + SLASH;
    public static final String RESTRICT = API + "restrict" + SLASH;


    private ConstantWeb() {
        throw new AssertionError();
    }
}
