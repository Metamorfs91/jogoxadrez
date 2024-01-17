package Tabuleirojogo;

public class Peca {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

}
