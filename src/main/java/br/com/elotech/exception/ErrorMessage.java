package br.com.elotech.exception;

public class ErrorMessage {

    private String campo;

    private String mensagem;

    public ErrorMessage() {
    }

    public ErrorMessage(String campo, String msg) {
        this.campo = campo;
        this.mensagem = msg;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
