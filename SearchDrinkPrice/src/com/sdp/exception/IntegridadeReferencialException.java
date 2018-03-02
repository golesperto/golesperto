package com.sdp.exception;

import com.sdp.util.Mensagens;

public class IntegridadeReferencialException extends BusinessException {
    
    public IntegridadeReferencialException(){
        super(Mensagens.getMensagem("exception.integridade.violada"));
    }
    
    public IntegridadeReferencialException(Throwable cause){
        super(cause, Mensagens.getMensagem("tj.exception.integridade.violada"));
    }
    
}
