package xadrez;

import tabuleirojogo.Tabuleiro;
import xadrezpecas.Rei;
import xadrezpecas.Torre;

public class XadrezPartida {
    private Tabuleiro tabuleiro;

    public XadrezPartida() {
        tabuleiro = new Tabuleiro(8, 8);
        configuracaoInicial();
    }

    public XadrezPeca[][] getPecas() {
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }

        }
        return mat;
    }

    private void novaPecaLugar(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.pecaLugar(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }

    private void configuracaoInicial() {
        novaPecaLugar('b', 6, new Torre(tabuleiro, Color.BRANCO));
        novaPecaLugar('e', 8, new Rei(tabuleiro, Color.PRETO));
        novaPecaLugar('e', 1, new Rei(tabuleiro, Color.BRANCO));

    }
}
