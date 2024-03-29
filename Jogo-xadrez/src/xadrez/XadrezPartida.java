package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrezpecas.Bispo;
import xadrezpecas.Cavalo;
import xadrezpecas.Peao;
import xadrezpecas.Rainha;
import xadrezpecas.Rei;
import xadrezpecas.Torre;

public class XadrezPartida {

    private int turno;
    private Color atualJogador;

    private Tabuleiro tabuleiro;
    private boolean check;

    private boolean checkMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public XadrezPartida() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        atualJogador = Color.WHITE;
        check = false;
        configuracaoInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Color getatualJogador() {
        return atualJogador;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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
        Posicao posicao = origemPosicao
                .toPosicao();
        validarPosicaoInicial(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public XadrezPeca movimentoPecaXadrez(XadrezPosicao origemPosicao, XadrezPosicao alvoPosicao) {
        Posicao origem = origemPosicao.toPosicao();
        Posicao alvo = alvoPosicao.toPosicao();
        validarPosicaoInicial(origem);
        validarPosicaoAlvo(origem, alvo);
        Peca pecaCapturada = fazerMovimento(origem, alvo);
        if (testeCheck(atualJogador)) {
            desfazerMovimento(origem, alvo, pecaCapturada);
            throw new XadrezExcecao("Voçê não pode fazer este movimento !");
        }
        check = (testeCheck(opponent(atualJogador))) ? true : false;

        if (testeCheckMate(opponent(atualJogador))) {
            checkMate = true;
        } else {
            proximoTurno();
        }
        // movimento Especial

        return (XadrezPeca) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao alvo) {

        XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(origem);
        p.aumentarContador();

        Peca pecaCapturada = tabuleiro.removerPeca(alvo);
        tabuleiro.pecaLugar(p, alvo);

        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        // movimento especialrook pequeno
        if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(origemT);
            tabuleiro.pecaLugar(torre, alvoT);
            torre.aumentarContador();
        }
        // movimento especial rook grande
        if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(origemT);
            tabuleiro.pecaLugar(torre, alvoT);
            torre.aumentarContador();
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao alvo, Peca pecaCapturada) {
        XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(alvo);
        p.diminuirContadorMovimento();
        tabuleiro.pecaLugar(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.pecaLugar(pecaCapturada, alvo);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
        // movimento especialrook pequeno
        if (p instanceof Rei && alvo.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(alvoT);
            tabuleiro.pecaLugar(torre, origemT);
            torre.diminuirContadorMovimento();
        }
        // movimento especial rook grande
        if (p instanceof Rei && alvo.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            XadrezPeca torre = (XadrezPeca) tabuleiro.removerPeca(alvoT);
            tabuleiro.pecaLugar(torre, origemT);
            torre.diminuirContadorMovimento();
        }
    }

    private void validarPosicaoInicial(Posicao posicao) {
        if (!tabuleiro.existePeca(posicao)) {
            throw new XadrezExcecao("nao existe peca na posicao de origem");
        }
        if (atualJogador != ((XadrezPeca) tabuleiro.peca(posicao)).getColor()) {
            throw new XadrezExcecao("A Peca escolhida não e sua ");
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

    private void proximoTurno() {
        turno++;
        atualJogador = (atualJogador == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private XadrezPeca rei(Color color) {
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == color)
                .collect(Collectors.toList());
        for (Peca p : list) {
            if (p instanceof Rei) {
                return (XadrezPeca) p;
            }
        }
        throw new IllegalStateException("Não á um rei" + color + "no tabuleiro");
    }

    private boolean testeCheck(Color color) {
        Posicao reiPosicao = rei(color).getXadrezPosicao().toPosicao();
        List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == opponent(color))
                .collect(Collectors.toList());
        for (Peca p : oponentePecas) {
            boolean[][] mat = p.possiveisMovimentos();
            if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Color color) {
        if (!testeCheck(color)) {
            return false;
        }
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == color)
                .collect(Collectors.toList());
        for (Peca p : list) {
            boolean[][] mat = p.possiveisMovimentos();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((XadrezPeca) p).getXadrezPosicao().toPosicao();
                        Posicao alvo = new Posicao(i, j);
                        Peca capturadaPeca = fazerMovimento(origem, alvo);
                        boolean testeCheck = testeCheck(color);
                        desfazerMovimento(origem, alvo, capturadaPeca);

                        if (!testeCheck) {
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    private void novaPecaLugar(char coluna, int linha, XadrezPeca peca) {
        tabuleiro.pecaLugar(peca, new XadrezPosicao(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void configuracaoInicial() {
        novaPecaLugar('a', 1, new Torre(tabuleiro, Color.WHITE));
        novaPecaLugar('b', 1, new Cavalo(tabuleiro, Color.WHITE));
        novaPecaLugar('c', 1, new Bispo(tabuleiro, Color.WHITE));
        novaPecaLugar('d', 1, new Rainha(tabuleiro, Color.WHITE));
        novaPecaLugar('e', 1, new Rei(tabuleiro, Color.WHITE, this));
        novaPecaLugar('f', 1, new Bispo(tabuleiro, Color.WHITE));
        novaPecaLugar('g', 1, new Cavalo(tabuleiro, Color.WHITE));
        novaPecaLugar('h', 1, new Torre(tabuleiro, Color.WHITE));

        novaPecaLugar('a', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('b', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('c', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('d', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('e', 2, new Peao(tabuleiro, Color.WHITE));

        novaPecaLugar('f', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('g', 2, new Peao(tabuleiro, Color.WHITE));
        novaPecaLugar('h', 2, new Peao(tabuleiro, Color.WHITE));

        novaPecaLugar('a', 8, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('b', 8, new Cavalo(tabuleiro, Color.BLACK));
        novaPecaLugar('c', 8, new Bispo(tabuleiro, Color.BLACK));
        novaPecaLugar('d', 8, new Rainha(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 8, new Rei(tabuleiro, Color.BLACK, this));
        novaPecaLugar('f', 8, new Bispo(tabuleiro, Color.BLACK));
        novaPecaLugar('g', 8, new Cavalo(tabuleiro, Color.BLACK));
        novaPecaLugar('h', 8, new Torre(tabuleiro, Color.BLACK));

        novaPecaLugar('a', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('b', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('c', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('d', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 7, new Peao(tabuleiro, Color.BLACK));

        novaPecaLugar('f', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('g', 7, new Peao(tabuleiro, Color.BLACK));
        novaPecaLugar('h', 7, new Peao(tabuleiro, Color.BLACK));

    }
}
