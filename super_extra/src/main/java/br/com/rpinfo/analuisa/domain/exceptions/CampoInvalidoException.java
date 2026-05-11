package br.com.rpinfo.analuisa.domain.exceptions;

public class CampoInvalidoException extends RuntimeException {
    public CampoInvalidoException(String message) {
        super(message);
    }
}
