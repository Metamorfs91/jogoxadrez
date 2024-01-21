package aplicacao;

import xadrez.XadrezPartida;

public class App {
    public static void main(String[] args) {
        XadrezPartida xadrezPartida = new XadrezPartida();
        UI.printTabuleiro(xadrezPartida.getPecas());
    }

}
