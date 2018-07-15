package br.com.tmvolpato.mygames.common.constant;

/**
 * Constant regular expressions.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ConstantRegexp {

    /**
     * Email validate.
     */
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Alphabet or numeric.
     */
    public static final String ALFABETIC_AND_NUMERIC_PATTERN = "^[a-z][a-zA-Z0-9]*$";

    private ConstantRegexp() {
        throw new AssertionError();
    }
}
