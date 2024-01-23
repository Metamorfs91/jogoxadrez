package aplicacao;

import java.util.Scanner;

import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        XadrezPartida xadrezPartida = new XadrezPartida();

        while (true) {
            UI.printTabuleiro(xadrezPartida.getPecas());
            System.out.println();
            System.out.print("Origem ");
            XadrezPosicao origem = UI.readXadrezPosicao(sc);

            System.out.println();
            System.out.print("Alvo ");
            XadrezPosicao alvo = UI.readXadrezPosicao(sc);
            XadrezPeca pecaCapturada = xadrezPartida.movimentoPecaXadrez(origem, alvo);
        }
    }

}
