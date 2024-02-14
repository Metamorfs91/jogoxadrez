package xadrez;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
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

    public XadrezPosicao getXadrezPosicao() {
        return XadrezPosicao.fromPosicao(posicao);
    }

    protected boolean haPecaAdversaria(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p != null && p.getColor() != color;
    }
}
