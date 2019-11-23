/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.epsevg.prop.lab.c4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pere
 */
public class Betaalpha implements Jugador, IAuto {

    private String nom;
    private int depth;

    public Betaalpha() {
        nom = "BetaAlpha AI";
        depth = 2;
    }

    // si moviment vol dir torn jugador nostre, volem valor MAX
    public int moviment(Tauler t, int color) {
        int valor, millorCol=0;
        int maxValor = Integer.MIN_VALUE;
        for (int i=0; i<t.getMida(); i++) {
            Tauler child = new Tauler(t);
            child.afegeix(i, color);
            valor = minValor(t, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, color);
            System.out.println("columna "+i+" valor="+valor);
            if (valor > maxValor) {
                maxValor = valor;
                millorCol = i;
            }
        }
        System.out.println(millorCol);
        return millorCol;
    }

    public String nom() {
        return nom;
    }

    /**
     * Minimitzar valor
     *
     * @param estat
     * @param alfa
     * @param beta
     * @param depth
     * @param player
     * @return beta
     */
    private int minValor(Tauler estat, int alfa, int beta, int depth, int player) {
        if (Terminal(estat) || depth == 0) {
            return eval(estat, player);
        }
        for (int i = 0; i < estat.getMida(); i++) {
            if (estat.movpossible(i)) {
                Tauler child = new Tauler(estat);
                child.afegeix(i, -player);
                if (child.solucio(i, -player)) {
                    return Integer.MIN_VALUE;
                }
                int temp = maxValor(child, alfa, beta, depth - 1, player);
                beta = Math.min(beta, temp);
                if (beta <= alfa) {
                    return beta;
                }
            }
        }
        return beta;
    }

    private int maxValor(Tauler estat, int alfa, int beta, int depth, int player) {
        if (Terminal(estat) || depth == 0) {
            return eval(estat, player);
        }
        for (int i = 0; i < estat.getMida(); i++) {
            if (estat.movpossible(i)) {
                Tauler child = new Tauler(estat);
                child.afegeix(i, player);
                if (child.solucio(i, player)) {
                    return Integer.MAX_VALUE;
                }
                int temp = minValor(child, alfa, beta, depth - 1, player);
                alfa = Math.max(alfa, temp);
                if (beta <= alfa) {
                    return alfa;
                }
            }
        }
        return alfa;
    }

    private boolean Terminal(Tauler t) {
        return !t.espotmoure();
    }

    /**
     * Funcio per evaluar totes les posicions del tauler i donar una heuristica
     *
     * @param estat
     * @param player
     * @return heuristica calculada per l'estat del tauler donat
     */
    private int eval(Tauler estat, int player) {
        int heuristica = 0;
        for (int i = 0; i < estat.getMida(); i++) {
            for (int j = 0; j < estat.getMida(); j++) {
                heuristica += evalPos(estat, i, j, player);
            }
        }
        return heuristica;
    }

    /**
     * Mirem al voltant de la casella per saber si es una bona posicio Comemcem
     * per la pos fila,col i mirem només 3 més enlla com a max Sense sortir del
     * tauler
     *
     * @param estat
     * @param fila
     * @param col
     * @param player
     * @return
     */
    private int evalPos(Tauler estat, int fila, int col, int player) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 100);
        map.put(3, 1000);
        int heuristica = 0;
        int limit = estat.getMida();
        int comptador = 1;
        int enBlanc = 0;
        int posPlayer = estat.getColor(fila, col);

        // horitzontals
        for (int i = col + 1; i < estat.getMida() && i <= col + 3; i++) {
            if (estat.getColor(fila, i) == posPlayer) {
                comptador++;
            } else if (estat.getColor(fila, i) == 0) {
                enBlanc = espaisBlanc(estat, i, col);
            }
        }
        if (posPlayer == player) {
            heuristica += mapValue(comptador, enBlanc);
        } else {
            heuristica -= mapValue(comptador, enBlanc);
        }

        // verticals
        comptador = 1;
        for (int i = fila + 1; i < estat.getMida() && i <= fila + 3; i++) {
            if (estat.getColor(i, col) == posPlayer) {
                comptador++;
            } else break;
        }
        if (posPlayer == player) {
            heuristica += mapValue(comptador);
        } else {
            heuristica -= mapValue(comptador);
        }

        // diagonals
        // TODO
        return heuristica;
    }
    
    private int espaisBlanc (Tauler estat, int fila, int col) {
        int espais = 0;
        for (int i=0; i<4 && fila >= 0; i++)   {
            if (estat.getColor(fila, col) == 0) {
                espais++;
            } else break;
            fila -= i;
        }
        return espais;
    }
    
    private int mapValue (int k) {
        if (k==1) return 1;
        if (k==2) return 100;
        if (k==3) return 1000;
        return 10000;
    }
    
    private int mapValue (int k, int falten) {
        int valor = 4 - falten;
        if (k==1) return 1*valor;
        if (k==2) return 100*valor;
        if (k==3) return 1000*valor;
        return 10000;
    }

    private class Valor {

        private int minimax;
        private Integer moviment;

        public Valor(int minimax, Integer moviment) {
            this.minimax = minimax;
            this.moviment = moviment;
        }

        public int getMinimax() {
            return minimax;
        }

        public void setMinimax(int minimax) {
            this.minimax = minimax;
        }

        public Integer getMoviment() {
            return moviment;
        }

        public void setMoviment(int moviment) {
            this.moviment = moviment;
        }
    }
}
