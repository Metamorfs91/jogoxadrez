package xadrez;

import tabuleirojogo.Peca;
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
        XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
            }

        }
        return mat;
    }

    public boolean[][] possiveisMovimentos(XadrezPosicao origemPosicao) {
        Posicao posicao = origemPosicao.toPosicao();
        validarPosicaoInicial(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public XadrezPeca movimentoPecaXadrez(XadrezPosicao origemPosicao, XadrezPosicao alvoPosicao) {
        Posicao origem = origemPosicao.toPosicao();
        Posicao alvo = alvoPosicao.toPosicao();
        validarPosicaoInicial(origem);
        validarPosicaoAlvo(origem, alvo);
        Peca pecaCapturada = fazerMovimento(origem, alvo);
        return (XadrezPeca) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao alvo) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(alvo);
        tabuleiro.pecaLugar(p, alvo);
        return pecaCapturada;
    }

    private void validarPosicaoInicial(Posicao posicao) {
        if (!tabuleiro.existePeca(posicao)) {
            throw new XadrezExcecao("nao existe peca na posicao de origem");
        }

        if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
            throw new XadrezExcecao("Nao existe movimentos possiveis para a peca escolhida");
        }
    }

    private void validarPosicaoAlvo(Posicao origem, Posicao alvo) {
        if (!tabuleiro.peca(origem).possiveisMovimentos(alvo)) {
            throw new XadrezExcecao("A peca escolhida nao pode mover para posicao ecolhida");
        }
    }

    private void novaPecaLugar(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.pecaLugar(peca, new XadrezPosicao(coluna, linha).toPosicao());
    }

    private void configuracaoInicial() {
        novaPecaLugar('d', 8, new Rei(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 8, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 1, new Torre(tabuleiro, Color.WHITE));

        novaPecaLugar('c', 1, new Torre(tabuleiro, Color.WHITE));
        novaPecaLugar('c', 2, new Torre(tabuleiro, Color.WHITE));
        novaPecaLugar('d', 2, new Torre(tabuleiro, Color.WHITE));
        novaPecaLugar('e', 2, new Torre(tabuleiro, Color.WHITE));
        novaPecaLugar('d', 1, new Rei(tabuleiro, Color.WHITE));

        novaPecaLugar('c', 7, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('c', 8, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('d', 7, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 7, new Torre(tabuleiro, Color.BLACK));
    }
}
