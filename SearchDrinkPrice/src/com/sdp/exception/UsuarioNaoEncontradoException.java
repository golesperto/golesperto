package com.sdp.exception;

import com.sdp.util.Mensagens;


public class UsuarioNaoEncontradoException extends BusinessException {
    
    private static String mensagem = Mensagens.getMensagem("exception.usuario.nao.encontrado");

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
