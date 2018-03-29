package br.com.tmvolpato.mygames.common.web.exceptionhandler;

import lombok.Getter;

/**
 * Classe mensagem de erro do sistema para usuário e desenvolvedor.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public class MessageError {

    @Getter
    private String messageUser;

    @Getter
    private String messageDev;

    public MessageError(final String messageUser, final String messageDev) {
        this.messageUser = messageUser;
        this.messageDev = messageDev;
    }
}
