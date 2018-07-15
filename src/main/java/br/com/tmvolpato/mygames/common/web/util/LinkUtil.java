package br.com.tmvolpato.mygames.common.web.util;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;

/**
 * Build link(s) and add in header.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class LinkUtil {

    public static final String REL_COLLECTION = "collection";
    public static final String REL_NEXT = "next";
    public static final String REL_PREV = "prev";
    public static final String REL_FIRST = "first";
    public static final String REL_LAST = "last";

    public static String createLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    public static String gatherLinkHeaders(final String... uris) {
        final StringBuilder linkHeaderValue = new StringBuilder();
        for (final String uri: uris) {
            linkHeaderValue.append(uri);
            linkHeaderValue.append(", ");
        }

        return linkHeaderValue.substring(ConstantNumeric.ZERO, linkHeaderValue.length() - ConstantNumeric.TWO);

    }

    private LinkUtil() {
        throw new AssertionError();
    }
}
