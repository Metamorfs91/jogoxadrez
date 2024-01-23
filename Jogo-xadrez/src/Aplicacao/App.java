package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.XadrezExcecao;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();

        while (true) {
            try {
                UI.limparTela();
                UI.printTabuleiro(xadrezPartida.getPecas());
                System.out.println();
                System.out.print("Origem ");
                XadrezPosicao origem = UI.readXadrezPosicao(sc);

                System.out.println();
                System.out.print("Alvo ");
                XadrezPosicao alvo = UI.readXadrezPosicao(sc);
                XadrezPeca pecaCapturada = xadrezPartida.movimentoPecaXadrez(origem, alvo);
            } catch (XadrezExcecao e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

    }

}
