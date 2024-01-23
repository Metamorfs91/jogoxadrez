package xadrezpecas;

import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

    public Rei(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);

    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        return mat;
    }
}
