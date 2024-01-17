package Tabuleirojogo;

public class Tabuleiro {
    private int linha;
    private int coluna;
    private Peca[][] pecas;

    public Tabuleiro(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        peca = new Peca[linha][coluna];
    }

}
