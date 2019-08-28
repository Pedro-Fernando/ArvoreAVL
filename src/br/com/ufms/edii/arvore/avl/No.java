package br.com.ufms.edii.arvore.avl;


public class No<T extends Comparable<T>> {
    private T valor;
    private No<T> esquerdo;
    private No<T> direito;
    private Integer altura;


    public No() {
        this.valor = null;
        this.esquerdo = null;
        this.direito = null;
        this.altura = 0;
    }

    public No(T valor) {
        this.valor = valor;
        this.direito = new No<T>();
        this.esquerdo = new No<T>();
        this.altura = 1;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public No<T> getEsquerdo() {
        return esquerdo;
    }

    public void setEsquerdo(No<T> esquerdo) {
        this.esquerdo = esquerdo;
    }

    public No<T> getDireito() {
        return direito;
    }

    public void setDireito(No<T> direito) {
        this.direito = direito;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }
}