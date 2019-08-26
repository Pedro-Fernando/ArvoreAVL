package br.com.ufms.edii.arvore.main;

import br.com.ufms.edii.arvore.avl.Avl;

public class Main {

    public static void main(String[] args) {

        Avl<Integer> arvore = new Avl<Integer>();

        arvore.inserir(10);
        arvore.inserir(5);
        arvore.inserir(20);
        arvore.inserir(15);
        arvore.inserir(18);
        arvore.inserir(21);
        arvore.imprimir();

        arvore.remover(18);
        arvore.imprimir();

        arvore.inserir(4);
        arvore.imprimir();

    }
}
