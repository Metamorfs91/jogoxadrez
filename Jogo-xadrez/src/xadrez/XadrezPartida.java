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
    private XadrezPeca enPassantVunerable;
    private XadrezPeca promocao;

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

    public XadrezPeca getEnpassantVunarable() {
        return enPassantVunerable;
    }

    public XadrezPeca getPromocao() {
        return promocao;
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

        XadrezPeca moverdPeca = (XadrezPeca) tabuleiro.peca(alvo);

        // movimento especial promocao
        promocao = null;
        if (moverdPeca instanceof Peao) {
            if (moverdPeca.getColor() == Color.WHITE && alvo.getLinha() == 0
                    || (moverdPeca.getColor() == Color.BLACK && alvo.getLinha() == 7)) {
                promocao = (XadrezPeca) tabuleiro.peca(alvo);
                promocao = substituirPecaPromovida("Q");
            }
        }

        check = (testeCheck(opponent(atualJogador))) ? true : false;

        if (testeCheckMate(opponent(atualJogador))) {
            checkMate = true;
        } else {
            proximoTurno();
        }

        // #especialMovimento enPassant

        if (moverdPeca instanceof Peao
                && (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)) {
            enPassantVunerable = moverdPeca;

        } else {
            enPassantVunerable = null;
        }
        // movimento Especial

        return (XadrezPeca) pecaCapturada;
    }

    public XadrezPeca substituirPecaPromovida(String type) {
        if (promocao == null) {
            throw new IllegalStateException("Essa peca não pode ser promovida ");
        }
        if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")) {
            return promocao;
        }
        Posicao pos = promocao.getXadrezPosicao().toPosicao();
        Peca p = tabuleiro.removerPeca(pos);

        pecasNoTabuleiro.remove(p);

        XadrezPeca novaPeca = novaPeca(type, promocao.getColor());
        tabuleiro.pecaLugar(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);
        return novaPeca;

    }

    private XadrezPeca novaPeca(String type, Color color) {
        if (type.equals("B"))
            return new Bispo(tabuleiro, color);
        if (type.equals("C"))
            return new Cavalo(tabuleiro, color);
        if (type.equals("Q"))
            return new Rainha(tabuleiro, color);
        return new Torre(tabuleiro, color);

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
        // movimento especial en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != alvo.getColuna() && pecaCapturada == null) {
                Posicao peaoPosicao;
                if (p.getColor() == Color.WHITE) {
                    peaoPosicao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
                } else {
                    peaoPosicao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(peaoPosicao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
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
        // especialmovimento en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != alvo.getColuna() && pecaCapturada == enPassantVunerable) {

                XadrezPeca peao = (XadrezPeca) tabuleiro.removerPeca(alvo);
                Posicao peaPosicao;
                if (p.getColor() == Color.WHITE) {
                    peaPosicao = new Posicao(3, alvo.getColuna());
                } else {
                    peaPosicao = new Posicao(4, alvo.getColuna());
                }
                tabuleiro.pecaLugar(peao, peaPosicao);

            }
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

        novaPecaLugar('a', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('b', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('c', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('d', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('e', 2, new Peao(tabuleiro, Color.WHITE, this));

        novaPecaLugar('f', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('g', 2, new Peao(tabuleiro, Color.WHITE, this));
        novaPecaLugar('h', 2, new Peao(tabuleiro, Color.WHITE, this));

        novaPecaLugar('a', 8, new Torre(tabuleiro, Color.BLACK));
        novaPecaLugar('b', 8, new Cavalo(tabuleiro, Color.BLACK));
        novaPecaLugar('c', 8, new Bispo(tabuleiro, Color.BLACK));
        novaPecaLugar('d', 8, new Rainha(tabuleiro, Color.BLACK));
        novaPecaLugar('e', 8, new Rei(tabuleiro, Color.BLACK, this));
        novaPecaLugar('f', 8, new Bispo(tabuleiro, Color.BLACK));
        novaPecaLugar('g', 8, new Cavalo(tabuleiro, Color.BLACK));
        novaPecaLugar('h', 8, new Torre(tabuleiro, Color.BLACK));

        novaPecaLugar('a', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('b', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('c', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('d', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('e', 7, new Peao(tabuleiro, Color.BLACK, this));

        novaPecaLugar('f', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('g', 7, new Peao(tabuleiro, Color.BLACK, this));
        novaPecaLugar('h', 7, new Peao(tabuleiro, Color.BLACK, this));

    }
}
