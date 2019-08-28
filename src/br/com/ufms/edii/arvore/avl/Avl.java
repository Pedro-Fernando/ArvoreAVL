package br.com.ufms.edii.arvore.avl;

public class Avl<T extends Comparable<T>> {

    private No<T> raiz;

    public void inserir(T valor) {
        if (isArvoreVazia()) {
            this.raiz = new No<T>(valor);
        } else {
            inserirAvl(this.raiz, valor);
        }
    }

    private void inserirAvl(No<T> no, T valor) {
        if (no.getValor() == null) {
            inserirNo(no, valor);
            return;
        } else if (no.getValor().compareTo(valor) < 0) {
            inserirAvl(no.getDireito(), valor);
        } else if (no.getValor().compareTo(valor) > 0) {
            inserirAvl(no.getEsquerdo(), valor);
        } else {
            return;
        }

        atualizarAltura(no);
        decidirTipoDaRotacao(no);
    }

    private void inserirNo(No<T> no, T valor) {
        no.setValor(valor);
        no.setAltura(1);
        no.setDireito(new No<T>());
        no.setEsquerdo(new No<T>());

    }

    private void decidirTipoDaRotacao(No<T> no) {
        int fatorDeBalanceamento = calcularFatorDeBalanceamento(no);

        if (isNoBalanceado(fatorDeBalanceamento)) {
            return;
        }

        if (fatorDeBalanceamento > 1) {
            if (calcularFatorDeBalanceamento(no.getDireito()) < 0) {
                rotacaoDuplaEsquerda(no);
            } else {
                rotacaoSimplesEsquerda(no);
            }
        } else {
            if (calcularFatorDeBalanceamento(no.getEsquerdo()) > 0) {
                rotacaoDuplaDireita(no);
            } else {
                rotacaoSimplesDireita(no);
            }
        }
    }

    private boolean isNoBalanceado(int fatorDeBalanceamento) {
        return fatorDeBalanceamento >= -1 && fatorDeBalanceamento <= 1;
    }

    private void rotacaoSimplesEsquerda(No<T> a) {
        No<T> b = a.getDireito();
        No<T> c = b.getEsquerdo();

        trocarValores(a, b);

        b.setEsquerdo(a.getEsquerdo());

        a.setEsquerdo(b);
        a.setDireito(b.getDireito());

        b.setDireito(c);

        atualizarAltura(b);
        atualizarAltura(a);
    }

    private void rotacaoSimplesDireita(No<T> a) {
        No<T> b = a.getEsquerdo();
        No<T> c = b.getDireito();

        trocarValores(a, b);

        b.setDireito(a.getDireito());

        a.setDireito(b);
        a.setEsquerdo(b.getEsquerdo());

        b.setEsquerdo(c);

        atualizarAltura(b);
        atualizarAltura(a);
    }

    private void rotacaoDuplaDireita(No<T> a) {
        rotacaoSimplesEsquerda(a.getEsquerdo());
        rotacaoSimplesDireita(a);
    }

    private void rotacaoDuplaEsquerda(No<T> a) {
        rotacaoSimplesDireita(a.getDireito());//rotação simples a direita na sub-arvore da direita;
        rotacaoSimplesEsquerda(a);
    }

    private void atualizarAltura(No<T> no) {
        no.setAltura(altura(no));
    }

    private void trocarValores(No<T> a, No<T> b) {
        T valorAntigoDeA = a.getValor();
        a.setValor(b.getValor());
        b.setValor(valorAntigoDeA);
    }

    private boolean isArvoreVazia() {
        return this.raiz == null || this.raiz.getValor() == null;
    }

    private int calcularFatorDeBalanceamento(No<T> no) {
        return no.getDireito().getAltura() - no.getEsquerdo().getAltura();
    }

    private int altura(No<T> no) {
        if (no.getValor() == null) {
            return 0;
        } else {
            int alturaSubArvoreEsquerda = no.getEsquerdo().getAltura();
            int alturaSubArvoreDireita = no.getDireito().getAltura();

            if (alturaSubArvoreEsquerda < alturaSubArvoreDireita) {
                return alturaSubArvoreDireita + 1;
            } else {
                return alturaSubArvoreEsquerda + 1;
            }
        }
    }

    public void imprimir() {
        System.out.println();
        imprimeEmOrdem(this.raiz);
    }

    private void imprimeEmOrdem(No<T> no) {
        if (no.getEsquerdo() == null) return;
        if (no.getDireito() == null) return;

        imprimeEmOrdem(no.getEsquerdo());
        System.out.printf("[%d] Filhos : E=%d, D=%d\n", no.getValor(), no.getEsquerdo().getValor(), no.getDireito().getValor());
        imprimeEmOrdem(no.getDireito());
    }

    public void remover(T valor) {
        No<T> resultadoDaBusca = buscarNo(valor);

        if (resultadoDaBusca == null || resultadoDaBusca.getValor() == null) {
            return;
        }

        switch (numeroDeFilhos(resultadoDaBusca)) {
            case 0:
                mataNo(resultadoDaBusca);
                break;

            case -1:
                resultadoDaBusca.setValor(resultadoDaBusca.getEsquerdo().getValor());
                resultadoDaBusca.setDireito(resultadoDaBusca.getEsquerdo().getDireito());
                resultadoDaBusca.setEsquerdo(resultadoDaBusca.getEsquerdo().getEsquerdo());
                break;

            case 1:
                resultadoDaBusca.setValor(resultadoDaBusca.getDireito().getValor());
                resultadoDaBusca.setEsquerdo(resultadoDaBusca.getDireito().getEsquerdo());
                resultadoDaBusca.setDireito(resultadoDaBusca.getDireito().getDireito());
                break;

            case 2:
                No<T> subArvoreEsquerda = buscarMaiorNoSubArvoreEsquerda(resultadoDaBusca);
                T removido = subArvoreEsquerda.getValor();
                remover(subArvoreEsquerda.getValor());
                resultadoDaBusca.setValor(removido);
                atualizarAltura(resultadoDaBusca);
                if (!isNoBalanceado(calcularFatorDeBalanceamento(resultadoDaBusca))){
                    decidirTipoDaRotacao(resultadoDaBusca);
                }
                break;
        }

    }

    private int numeroDeFilhos(No<T> ponteiro) {
        int aux = 0;
        if (ponteiro.getEsquerdo().getValor() != null) {
            aux = -1;
        }
        if (ponteiro.getDireito().getValor() != null) {
            aux = aux * (-1) + 1;
        }
        return aux;
    }

    private No<T> buscarMenorNoSubArvoreDireita(No<T> no) {
        no = no.getDireito();

        while (no.getEsquerdo().getValor() != null) {
            no = no.getEsquerdo();
        }

        return no;
    }

    private No<T> buscarMaiorNoSubArvoreEsquerda(No<T> no) {
        no = no.getEsquerdo();
        while (no.getDireito().getValor() != null) {
            no = no.getDireito();
        }

        return no;
    }

    private void mataNo(No<T> ponteiro) {
        ponteiro.setValor(null);
        ponteiro.setDireito(null);
        ponteiro.setEsquerdo(null);
        ponteiro.setAltura(0);
    }

    private No<T> buscarNo(T valor) {
        if (isArvoreVazia()) {
            return null;
        }

        No<T> ponteiro = this.raiz;

        while (valorNaoEncontrado(ponteiro, valor)) {
            if (valorBuscadoMaiorQueValorDoPonteiro(ponteiro, valor)) {
                ponteiro = ponteiro.getDireito();
            } else if (valorBuscadoMenorQueValorDoPonteiro(ponteiro, valor)) {
                ponteiro = ponteiro.getEsquerdo();
            }
        }

        return ponteiro;
    }

    private boolean valorBuscadoMenorQueValorDoPonteiro(No<T> ponteiro, T valor) {
        return valor.compareTo(ponteiro.getValor()) < 0;
    }

    private boolean valorBuscadoMaiorQueValorDoPonteiro(No<T> ponteiro, T valor) {
        return valor.compareTo(ponteiro.getValor()) > 0;
    }

    private boolean valorNaoEncontrado(No<T> ponteiro, T valor) {
        return ponteiro.getValor() != null && !ponteiro.getValor().equals(valor);
    }

}

