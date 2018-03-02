package com.sdp.exception;

import com.sdp.util.Mensagens;

@SuppressWarnings("serial")
public class UsuarioEmailExistenteException extends BusinessException {
    public UsuarioEmailExistenteException() {
        super(Mensagens.getMensagem("exception.usuario.email.existente"));
    }

    public UsuarioEmailExistenteException(
            Throwable cause) {
        super(cause, Mensagens.getMensagem("exception.usuario.email.existente"));
    }

    public UsuarioEmailExistenteException(
            Throwable cause, String mensagem) {
        super(cause, mensagem);
    }
}
