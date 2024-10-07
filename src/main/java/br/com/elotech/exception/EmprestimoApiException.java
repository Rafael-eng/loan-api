package br.com.elotech.exception;

public class EmprestimoApiException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmprestimoApiException(String message) {
        super(message);
    }

    public EmprestimoApiException(String message, Exception e) {
        super(message, e);
    }

    public EmprestimoApiException(Exception e) {
        super(e);
    }

    public EmprestimoApiException(String message, Throwable throwable) {
    }


}

