package tabuleirojogo;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new TabuleiroExcecao("Erro ao Criar Tabuleiro, é necessário que haja pelo menos 1 linha e 1 coluna");
        }

        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if (!posicaoExistente(linha, coluna)) {
            throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {

        if (!posicaoExistente(posicao)) {
            throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];

    }

    public void pecaLugar(Peca peca, Posicao posicao) {
        if (existePeca(posicao)) {
            throw new TabuleiroExcecao("Existe uma peça nesta posicao " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    private boolean posicaoExistente(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoExistente(Posicao posicao) {
        return posicaoExistente(posicao.getLinha(), posicao.getColuna());
    }

    public boolean existePeca(Posicao posicao) {
        if (!posicaoExistente(posicao)) {
            throw new TabuleiroExcecao("Posicao não existe no tabuleiro");
        }
        return peca(posicao) != null;

    }
}
