package com.sdp.exception;

import com.sdp.util.Mensagens;


public class UsuarioBloqueadoException extends BusinessException{
    
    private String mensagem = Mensagens.getMensagem("exception.usuario.bloqueado");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
