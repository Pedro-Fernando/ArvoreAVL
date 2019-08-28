package br.com.ufms.edii.arvore.main;

import br.com.ufms.edii.arvore.avl.Avl;

public class Main {

    public static void main(String[] args) {

        Avl<Integer> arvore = new Avl<Integer>();


        arvore.inserir(100);
        arvore.inserir(80);
        arvore.inserir(200);
        arvore.inserir(60);
        arvore.inserir(90);
        arvore.inserir(50);
        arvore.inserir(95);
        arvore.inserir(300);
        arvore.inserir(97);
        arvore.inserir(55);
        arvore.inserir(400);
        arvore.inserir(240);
        arvore.inserir(250);
        arvore.imprimir();

        arvore.remover(300);
        arvore.remover(80);
        arvore.remover(240);
        arvore.remover(50);
        arvore.remover(100);
        arvore.imprimir();


    }
}
