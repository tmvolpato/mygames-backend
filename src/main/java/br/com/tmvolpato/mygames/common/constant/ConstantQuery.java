package br.com.tmvolpato.mygames.common.constant;

/**
 * Constant Query.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ConstantQuery {

    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_ORDER = "sortOrder";
    public static final String S_ORDER_ASC = "ASC";
    public static final String S_ORDER_DESC = "DESC";
    public static final String Q_PARAM = "q";
    public static final String NEGATION = "~";
    public static final String OP = "=";
    public static final String SEPARATOR = ",";
    public static final String SEPARATOR_AMPER = "&";
    public static final String PERCENTAGE = "%";

    private ConstantQuery() {
        throw new AssertionError();
    }

}
