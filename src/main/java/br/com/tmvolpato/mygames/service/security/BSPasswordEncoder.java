package br.com.tmvolpato.mygames.service.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.stream.Collectors.joining;

/**
 * Codificação da senha, usado somente para desenvolvimento.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
public class BSPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence charSequence) {
        return charSequence.chars()
                .map(Character::toUpperCase)
                .mapToObj(c -> Character.toString((char) (Character.isLetter(c) ? ('A' + (c - 26) % 26) : c)))
                .collect(joining());
    }

    @Override
    public boolean matches(final CharSequence charSequence, final String encodedPassword) {
        return encode(charSequence).equalsIgnoreCase(encodedPassword);
    }
}
