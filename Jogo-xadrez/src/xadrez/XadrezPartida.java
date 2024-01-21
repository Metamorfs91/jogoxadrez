package xadrez;

import tabuleirojogo.Posicao;
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
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinha()][tabuleiro.getColuna()];
        for (int i = 0; i < tabuleiro.getLinha(); i++) {
            for (int j = 0; j < tabuleiro.getColuna(); j++) {
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }

        }
        return mat;
    }

    private void configuracaoInicial() {
        tabuleiro.pecaLugar(new Torre(tabuleiro, Color.BRANCO), new Posicao(2, 1));
        tabuleiro.pecaLugar(new Rei(tabuleiro, Color.PRETO), new Posicao(0, 4));
        tabuleiro.pecaLugar(new Rei(tabuleiro, Color.BRANCO), new Posicao(7, 4));

    }
}
