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

    private ArrayList<Accio> possiblesMoviments(Tauler t, int color) {
        ArrayList<Accio> accions = new ArrayList<>();
        //ArrayList<Tauler> accions = new ArrayList<>();
        for (int c = 0; c < t.getMida(); c++) {
            if (t.movpossible(c)) {
                Tauler child = new Tauler(t);
                child.afegeix(c, color);
                if (child.solucio(c, color)) {
                    Accio accio = new Accio(child, child.solucio(c, color), color);
                    accions.add(accio);
                } else {
                    Accio accio = new Accio(child, false);
                    accions.add(accio);
                }

            }
        }
        return accions;
    }

    private int getHeuristica(Tauler t) {
        int current, other = 0;

        // comprovem horitzontals
        for (int f = 0; f < t.getMida(); f++) {
            for (int c = 0; c < t.getMida(); c++) {
                int prevColor = t.getColor(f, c);
                int currColor = t.getColor(f, c+1);
                }
            }
        }

        for (int i = 0; i < t.getMida(); i++) {
            for (int j = 0; j < t.getMida(); j++) {

            }
        }

    }

    private int minmax(Accio a, int depth, boolean maximitzar) {

        if (depth == 0 || a.isTerminal()) {
            if (a.getGuanyador() == 1) {
                return Integer.MAX_VALUE;
            } else if (a.getGuanyador() == -1) {
                return Integer.MIN_VALUE;
            }
            return getHeuristica(a.getT());

        }
        if (maximitzar) {
            ArrayList<Accio> childs = new ArrayList<>();
            childs = possiblesMoviments(a.getT(), 1);
            int valor = Integer.MIN_VALUE;
            for (Accio child : childs) {
                valor = Math.max(valor, minmax(child, depth - 1, false));
            }
        } else {
            ArrayList<Accio> childs = new ArrayList<>();
            childs = possiblesMoviments(a.getT(), -1);
            int valor = Integer.MAX_VALUE;
            for (Accio child : childs) {
                valor = Math.max(valor, minmax(child, depth - 1, true));
            }
        }
        return -1;
    }

    private class Accio {

        private Tauler t;
        private boolean terminal;
        private int guanyador;

        public Accio(Tauler t, boolean terminal) {
            this.t = t;
            this.terminal = terminal;
            guanyador = -1;
        }

        public Accio(Tauler t, boolean terminal, int guanyador) {
            this.t = t;
            this.terminal = terminal;
            this.guanyador = guanyador;
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

        public int getGuanyador() {
            return guanyador;
        }

        public void setGuanyador(int guanyador) {
            this.guanyador = guanyador;
        }

    }
}
