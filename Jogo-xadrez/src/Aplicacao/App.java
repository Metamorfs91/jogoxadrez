package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.XadrezExcecao;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();
        List<XadrezPeca> capturada = new ArrayList<>();

        while (!xadrezPartida.getCheckMate()) {
            try {
                UI.limparTela();
                UI.mostrarPartida(xadrezPartida, capturada);
                System.out.println();
                System.out.print("Origem: ");
                XadrezPosicao origem = UI.readXadrezPosicao(sc);

                boolean[][] possiveisMovimentos = xadrezPartida.possiveisMovimentos(origem);
                UI.limparTela();
                UI.printTabuleiro(xadrezPartida.getPecas(), possiveisMovimentos);

                System.out.println();
                System.out.print("Alvo: ");
                XadrezPosicao alvo = UI.readXadrezPosicao(sc);

                XadrezPeca pecaCapturada = xadrezPartida.movimentoPecaXadrez(origem, alvo);

                if (pecaCapturada != null) {
                    capturada.add(pecaCapturada);
                }
                if (xadrezPartida.getPromocao() != null) {
                    System.out.print("Digite a peca para promocao (B/C/T/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
                        System.out.print("valor invalido !  Digite a peca para promocao (B/C/T/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    xadrezPartida.substituirPecaPromovida(type);
                }

            } catch (XadrezExcecao e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limparTela();
        UI.mostrarPartida(xadrezPartida, capturada);
    }

}
