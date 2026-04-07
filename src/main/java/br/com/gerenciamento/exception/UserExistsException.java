package br.com.gerenciamento.exception;

import java.io.Serial;

public class UserExistsException extends Exception{
    
    public UserExistsException(String message_error) {
        super(message_error);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
