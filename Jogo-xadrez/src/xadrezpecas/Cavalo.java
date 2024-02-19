package xadrezpecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Cavalo extends XadrezPeca {

    public Cavalo(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);

    }

    @Override
    public String toString() {
        return "C";
    }

    private boolean podeMover(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
