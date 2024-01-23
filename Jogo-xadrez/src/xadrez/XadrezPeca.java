package xadrez;

import tabuleirojogo.Peca;
import tabuleirojogo.Tabuleiro;

public abstract class XadrezPeca extends Peca {
    private Color color;

    public XadrezPeca(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
