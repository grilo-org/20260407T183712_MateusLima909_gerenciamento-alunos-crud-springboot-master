package br.com.gerenciamento.exception;

import java.io.Serial;

public class CriptoExistsException extends Exception {

    public CriptoExistsException(String message_error) {
        super(message_error);
    }

    @Serial
    private static final long serialVersionUID = 1L;

}
