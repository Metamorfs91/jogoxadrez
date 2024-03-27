package xadrezpecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {
    private XadrezPartida xadrezPartida;

    public Rei(Tabuleiro tabuleiro, Color color, XadrezPartida xadrezPartida) {
        super(tabuleiro, color);
        this.xadrezPartida = xadrezPartida;
    }

    @Override
    public String toString() {
        return "R";
    }

    private boolean podeMover(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    private boolean testeTorreRodizio(Posicao posicao) {
        XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContadorMovimento() == 0;
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // para cima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // para baixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // para esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // para direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // noroest
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // #movimento especial de Rodizio

        if (getContadorMovimento() == 0 && !xadrezPartida.getCheck()) {
            // movimento especial Rodizio Rei torre
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeTorreRodizio(posT1)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeTorreRodizio(posT2)) {
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null
                        && getTabuleiro().peca(p3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }
        return mat;
    }
}
