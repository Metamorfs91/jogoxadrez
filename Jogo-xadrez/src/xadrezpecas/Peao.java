package xadrezpecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca {
    private XadrezPartida xadrezPartida;

    public Peao(Tabuleiro tabuleiro, Color color, XadrezPartida xadrezPartida) {
        super(tabuleiro, color);
        this.xadrezPartida = xadrezPartida;

    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (getColor() == Color.WHITE) {
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;

            }

            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)
                    && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().existePeca(p)
                    && getContadorMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;

            }

            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            // #especial movimento en passant white
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && haPecaAdversaria(esquerda)
                        && getTabuleiro().peca(esquerda) == xadrezPartida.getEnpassantVunarable()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && haPecaAdversaria(direita)
                        && getTabuleiro().peca(direita) == xadrezPartida.getEnpassantVunarable()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }

        }

        else {
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;

            }
            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)
                    && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().existePeca(p)
                    && getContadorMovimento() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;

            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // #especial movimento en passant black
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && haPecaAdversaria(esquerda)
                        && getTabuleiro().peca(esquerda) == xadrezPartida.getEnpassantVunarable()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && haPecaAdversaria(direita)
                        && getTabuleiro().peca(direita) == xadrezPartida.getEnpassantVunarable()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }

        }

        return mat;

    }

    @Override
    public String toString() {
        return "P";

    }

}