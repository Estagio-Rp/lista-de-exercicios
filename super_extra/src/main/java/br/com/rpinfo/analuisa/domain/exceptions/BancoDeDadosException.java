package br.com.rpinfo.analuisa.domain.exceptions;

public class BancoDeDadosException extends RuntimeException {
    public BancoDeDadosException(String message) {
        super(message);
    }

    public BancoDeDadosException(String message, Throwable causa) {
        super(message, causa);
    }
}
