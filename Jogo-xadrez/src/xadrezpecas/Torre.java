package xadrezpecas;

import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Torre extends XadrezPeca {

    public Torre(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);

    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        return mat;
    }
}