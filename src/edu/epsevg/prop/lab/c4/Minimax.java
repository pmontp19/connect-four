/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.epsevg.prop.lab.c4;

import java.util.ArrayList;

/**
 *
 * @author pere
 */
public class Minimax implements Jugador, IAuto {

    private String nom;

    public Minimax() {
        nom = "Minmax AI";
    }

    // si moviment vol dir torn jugador nostre, volem valor MAX
    public int moviment(Tauler t, int color) {
        int col = (int) (8.0D * Math.random());
        while (!t.movpossible(col)) {
            col = (int) (8.0D * Math.random());
        }
        return col;
    }

    public String nom() {
        return nom;
    }

    private ArrayList<Tauler> possiblesMoviments(Tauler t, int color) {
        ArrayList<Accio> accions = new ArrayList<>();
        //ArrayList<Tauler> accions = new ArrayList<>();
        for (int c = 0; c < t.getMida(); c++) {
            if (t.movpossible(c)) {
                Tauler child = new Tauler(t);
                child.afegeix(c, color);
                child.solucio(c, c)
                Accio accio = new Accio(child, true)
                accions.add(child);
            }
        }
        return accions;
    }
    
    private int getHeuristica(Tauler t) {
        int liniesCurrent, liniesOther = 0;
        
        
    }

    private int minmax(Tauler t, int depth, boolean maximitzar) {

        if (depth == 0 || !t.espotmoure() ){
            return getHeuristica(Tauler t);
        }
        if (maximitzar) {
            ArrayList<Tauler> childs = new ArrayList<>();
            childs = possiblesMoviments(t, 1);
            int valor = Integer.MIN_VALUE;
            for (Tauler child : childs) {
                valor = Math.max(valor, minmax(child, depth - 1, false));
            }
        } else {
            ArrayList<Tauler> childs = new ArrayList<>();
            childs = possiblesMoviments(t, -1);
            int valor = Integer.MAX_VALUE;
            for (Tauler child : childs) {
                valor = Math.max(valor, minmax(child, depth - 1, false));
            }
        }

    }
    
    private class Accio {
        private Tauler t;
        private boolean terminal;   

        public Accio(Tauler t, boolean terminal) {
            this.t = t;
            this.terminal = terminal;
        }

        public Tauler getT() {
            return t;
        }

        public void setT(Tauler t) {
            this.t = t;
        }

        public boolean isTerminal() {
            return terminal;
        }

        public void setTerminal(boolean terminal) {
            this.terminal = terminal;
        }
        
        
    }
}
