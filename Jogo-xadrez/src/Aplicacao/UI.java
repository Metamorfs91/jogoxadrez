package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Color;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static XadrezPosicao readXadrezPosicao(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new XadrezPosicao(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler posicoes do Xadrez");
        }

    }

    public static void mostrarPartida(XadrezPartida xadrezPartida, List<XadrezPeca> capturada) {
        printTabuleiro(xadrezPartida.getPecas());
        System.out.println();
        mostrarPecaCapturada(capturada);
        System.out.println();
        System.out.println("Turno: " + xadrezPartida.getTurno());
        if (!xadrezPartida.getCheckMate()) {

            System.out.println("Aguardando Player: " + xadrezPartida.getatualJogador());
            if (xadrezPartida.getCheck()) {
                System.out.println("CHECK!");
            }
        } else {
            System.out.println("CHACKMATE!");
            System.out.println("Vencedor: " + xadrezPartida.getatualJogador());
        }
    }

    public static void printTabuleiro(XadrezPeca[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                printPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printTabuleiro(XadrezPeca[][] pecas, boolean[][] possiveisMovimentos) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                printPeca(pecas[i][j], possiveisMovimentos[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPeca(XadrezPeca peca, boolean background) {

        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }

        if (peca == null) {
            System.out.print("-" + ANSI_RESET);

        } else {
            if (peca.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void mostrarPecaCapturada(List<XadrezPeca> pecaCapturada) {
        List<XadrezPeca> white = pecaCapturada.stream().filter(x -> x.getColor() == Color.WHITE)
                .collect(Collectors.toList());

        List<XadrezPeca> black = pecaCapturada.stream().filter(x -> x.getColor() == Color.BLACK)
                .collect(Collectors.toList());

        System.out.println("Pecas Capturadas: ");

        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);

        System.out.println(Arrays.toString(white.toArray()));

        System.out.print(ANSI_RESET);

        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);

        System.out.println(Arrays.toString(black.toArray()));

        System.out.print(ANSI_RESET);
    }
}
