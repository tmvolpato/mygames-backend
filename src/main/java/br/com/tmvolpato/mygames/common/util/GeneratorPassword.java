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
 * @version 1.0.0
 * @sice 2017
 */
public class GeneratorPassword {

    /**
     * Encode ID.
     */
    private static final String ENCODE_ID = "bcrypt";

    /**
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
