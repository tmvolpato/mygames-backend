package br.com.tmvolpato.mygames.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Generator password.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public class GeneratorPassword {

    /**
     * Encode ID.
     */
    private static final String ENCODE_ID = "bcrypt";

    /**FO
     * Encode password.
     *
     * @param password
     * @return
     */
    public static String encode(final String password) {
        final String idForEncode = ENCODE_ID;
        final BCryptPasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, defaultEncoder);
        final DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return delegatingPasswordEncoder.encode(password);
    }
}
