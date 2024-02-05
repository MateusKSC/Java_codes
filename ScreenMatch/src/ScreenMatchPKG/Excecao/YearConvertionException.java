package ScreenMatchPKG.Excecao;

public class YearConvertionException extends RuntimeException {
    private String mensagem;
    public YearConvertionException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }
}
