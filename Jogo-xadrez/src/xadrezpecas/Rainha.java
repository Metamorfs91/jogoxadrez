package xadrezpecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Rainha extends XadrezPeca {

    public Rainha(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);

    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // para cima
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // para esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // para direita
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;

            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // para baixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // noroeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() - 1);
            ;
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // nordeste
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() + 1);
            ;
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // sudeste
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;

            p.setValores(p.getLinha() + 1, p.getColuna() + 1);
            ;
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        // para baixo
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
            ;
        }
        if (getTabuleiro().posicaoExistente(p) && haPecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}