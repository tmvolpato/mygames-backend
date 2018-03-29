package br.com.tmvolpato.mygames.common.constant;

/**
 * Mensagens de validação.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ConstantMessageValidation {

    public static final String TITLE_NOT_BLANK = "title.not.blank";
    public static final String NAME_NOT_BLANK = "name.not.blank";
    public static final String EMAIL_NOT_BLANK = "email.not.blank";
    public static final String EMAIL_VALIDATE = "email.validate";
    public static final String PASSWORD_NOT_BLANK = "password.not.blank";
    public static final String PLATFORM_NOT_NULL = "platform.not.null";
    public static final String COMPANY_NOT_NULL = "company.not.null";
    public static final String GENRE_NOT_NULL = "genre.not.null";
    public static final String ROLE_NOT_NULL = "role.not.null";
    public static final String PRICE_NOT_NULL = "price.not.null";
    public static final String USER_NOT_NULL = "user.not.null";

    private ConstantMessageValidation() {
        throw new AssertionError();
    }
}
