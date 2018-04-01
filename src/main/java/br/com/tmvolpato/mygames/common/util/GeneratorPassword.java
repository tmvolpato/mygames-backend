package br.com.tmvolpato.mygames.common.util;

import br.com.tmvolpato.mygames.service.security.BSPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que encoda a senha.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
public class GeneratorPassword {

    public static void main(String[] args) {
        String idForEncode = "bcrypt";
        final BCryptPasswordEncoder defaultEncoder = new BCryptPasswordEncoder();


        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, defaultEncoder);
        //encoders.put("bcrypt", new BCryptPasswordEncoder());
        //encoders.put("scrypt", new SCryptPasswordEncoder());

        final DelegatingPasswordEncoder rv = new DelegatingPasswordEncoder(idForEncode, encoders);
        //rv.setDefaultPasswordEncoderForMatches(new BSPasswordEncoder());

        System.out.print(rv.encode("admin123"));
    }
}
