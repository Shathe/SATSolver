/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmiapractica1;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author shathe
 */
public class Test {

    public static void main(String[] args) {
System.out.println("No se tiene en cuenta el tiempo de generación de pruebas ni de lectura de la ecuación, solo de resolución");
        for (int numClausulas = 2; numClausulas < 300000; numClausulas *= 2) {
            int numLiteralesxClausula = (int) Math.pow(numClausulas,0.35);
            int numLiteralesDiferentes = (int) Math.pow(numClausulas,0.35);
            ejecutarPrueba(numClausulas, numLiteralesxClausula, numLiteralesDiferentes);
        	System.out.println();
        	System.gc();

        }

    }

    public static void ejecutarPrueba(int numClausulas, int numLiteralesxClausula, int numLiteralesDiferentes) {
        ArrayList<ClausulaMultiple> listaORs = new ArrayList(numClausulas);
        System.out.println("Ecuacion test de " + numClausulas + " numero de cláusulas, " + numLiteralesxClausula
                + " numero de literales por clausulas y " + numLiteralesDiferentes + " Literales diferentes utilizados");
       
        Random a = new Random();
        //creas ecuaciones de tamaño 500 AND con clausulas de 50 ors
        System.out.println("Generando datos de prueba");
        for (int i = 0; i < numClausulas; i++) {
            ArrayList<Literal> literales = new ArrayList(numLiteralesxClausula);
            for (int j = 0; j < numLiteralesxClausula; j++) {
                String literal = String.valueOf(new Integer(a.nextInt(numLiteralesDiferentes)));
                boolean valor = a.nextBoolean();
                Literal nuevo = new Literal(literal, valor);
                literales.add(nuevo);
            }
            ClausulaMultiple clausula = new ClausulaMultiple(literales);
            listaORs.add(clausula);
        }
        System.out.println("Datos generados");

        Ecuacion ecuacion = new Ecuacion(listaORs);
        SATsolver.resolverSAT(ecuacion);
    }
    public static void ejecutarPruebaIrresoluble(int numClausulas, int numLiteralesxClausula, int numLiteralesDiferentes) {
        ArrayList<ClausulaMultiple> listaORs = new ArrayList(numClausulas);
        System.out.println("Ecuacion test de " + numClausulas + " numero de cláusulas, " + numLiteralesxClausula
                + " numero de literales por clausulas y " + numLiteralesDiferentes + " Literales diferentes utilizados");
       
        Random a = new Random();
        //creas ecuaciones de tamaño 500 AND con clausulas de 50 ors
        System.out.println("Generando datos de prueba");
        for (int i = 0; i < numClausulas; i++) {
            ArrayList<Literal> literales = new ArrayList(numLiteralesxClausula);
            for (int j = 0; j < numLiteralesxClausula; j++) {
                String literal = String.valueOf(new Integer(a.nextInt(numLiteralesDiferentes)));
                boolean valor = a.nextBoolean();
                Literal nuevo = new Literal(literal, valor);
                literales.add(nuevo);
            }
            
            ClausulaMultiple clausula = new ClausulaMultiple(literales);
            listaORs.add(clausula);
        }
        System.out.println("Datos generados");
        ArrayList<Literal> literalesA = new ArrayList(1);
        ArrayList<Literal> literalesAN = new ArrayList(1);
        literalesA.add(new Literal("a",true));
        literalesAN.add(new Literal("a",false));
        ClausulaMultiple clausulaA = new ClausulaMultiple(literalesA);
        ClausulaMultiple clausulaAN = new ClausulaMultiple(literalesAN);
        listaORs.add(clausulaA);
        listaORs.add(clausulaAN);
        
        Ecuacion ecuacion = new Ecuacion(listaORs);
        SATsolver.resolverSAT(ecuacion);
    }
}
