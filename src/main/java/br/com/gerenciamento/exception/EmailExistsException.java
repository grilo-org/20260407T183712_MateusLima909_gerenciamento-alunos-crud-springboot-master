package br.com.gerenciamento.exception;

import java.io.Serial;

public class EmailExistsException extends Exception {


    public EmailExistsException(String message_error) {
        super(message_error);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
