package algoritmiapractica1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Iñigo Alonso Ruiz y Alejandro Dieste Cortés
 */
public class SATsolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            //se pone menu explicativo y se lee de teclado la formula
            System.out.println("Introduzca la ecuación SAT que desee saber si es satisfacctible,"
                    + " SAT es una ecuación de ANDs de ORs, ejemplo: (a or not b) and (c or a). Pero para facilitar"
                    + " el reconocimiento de la fórmula, and='*', or='+', y se nieg un literal anteponiendo el símbolo"
                    + " '-'. El mismo ejemplo quedaría:(a + -b) * (c + a):");
            Scanner scan = new Scanner(System.in);
            String ecuacion = scan.nextLine();
            Ecuacion ecuacionFichero = SystemOperation.leerEcuacion(ecuacion);
            resolverSAT(ecuacionFichero);
        } else if (args[0].equals("-f")) {
            String ficheroSAT = args[1];
            File file = new File(ficheroSAT);
            try {
                Scanner fichero = new Scanner(file);
                String ecuacion = "";
                while (fichero.hasNext()) {
                    ecuacion += fichero.next();
                }
                Ecuacion ecuacionFichero = SystemOperation.leerEcuacion(ecuacion);
                resolverSAT(ecuacionFichero);
            } catch (Exception a) {
            }
        } else {
            System.out.println("La forma de ejecutar este programa es sin argumentos o,"
                    + "pasanadole un fichero donde esta la ecuación a resolver de esta forma:"
                    + " ./programa -f ficheroEcuacion.txt ");
        }
        //lees el fichero

    }
    /*
     Resuelve HornSAT, devuelve true si es satisfactible
     */

    public static boolean resolverHornSAT(ArrayList<ClausulaMultiple> lista) {
        boolean satisfactible = false;
        boolean solucionEncontrada = false;
        ArrayList<ClausulaMultiple> vistas = new ArrayList();
        while (!solucionEncontrada) {
            if (OperacionesHornSAT.hayClausulasOpuestas(lista)) {
                solucionEncontrada = true;
                //no es satisfactible
            } else {
                ClausulaMultiple siguienteClausulaUnitaria = OperacionesHornSAT.SiguienteClausulaUnitaria(lista, vistas);
                if (siguienteClausulaUnitaria == null) {
                    //no hay mas clausulas unitarias-> es satifactible
                    satisfactible = true;
                    solucionEncontrada = true;
                } else {
                    //
                    lista = OperacionesHornSAT.SimplificarClausulas(lista, siguienteClausulaUnitaria);
                    vistas.add(siguienteClausulaUnitaria);
                }
            }
        }

        return satisfactible;
    }
    /*
     Devuelves si la ista de Ors es 2SAT
     */

    public static boolean esOR2SAT(ClausulaMultiple listaOrs) {
        int variablesDiferentes = 0;
        ArrayList<String> nombresVistos = new ArrayList<String>();
        for (Literal variable : listaOrs.variables) {
            if (!nombresVistos.contains(variable.nombre)) {
                nombresVistos.add(variable.nombre);
                variablesDiferentes++;
            } else {
                //es una variable repetida, 
            }
        }

        return variablesDiferentes <= 2;
    }
    /*
     Devuelve si la clausa es HornSAT
     */

    public static boolean esHornSAT(ClausulaMultiple listaOrs) {
        int positivas = 0;
        for (Literal variable : listaOrs.variables) {

            if (variable.valor) {
                positivas++;
            }

        }

        return positivas <= 1;
    }
    /*
     Devuelve el valor de un literal cuyo nombre coincida con varaible y este en la lista
     */

    public static boolean getValue(ArrayList<Literal> lista, String variable) {
        boolean valor = false;
        for (int i = 0; i < lista.size(); i++) {
            if (variable.equals(lista.get(i).nombre)) {
                return lista.get(i).valor;
            }
        }
        return valor;

    }
    /*si está un literal en la lista cuyo nombre coincida con varaible 
     */

    public static boolean esta(ArrayList<Literal> lista, String nombre) {
        boolean encontrado = false;
        for (int i = 0; !encontrado && i < lista.size(); i++) {
            encontrado = lista.get(i).nombre.equals(nombre);
        }
        return encontrado;

    }
    /*
     Resuelve SAT mostrando por pantalla el resultado dada una ecuacion
     */

    public static void resolverSAT(Ecuacion ecuacionFichero) {
        /*
         primero se mira si es 2SAT
         */
        ArrayList<ClausulaMultiple> listaORs2SAT = new ArrayList();

        boolean es2SAT = true;
        for (int i = 0; es2SAT && (i < ecuacionFichero.listaORs.size()); i++) {
            if (esOR2SAT(ecuacionFichero.listaORs.get(i))) {
                listaORs2SAT.add(ecuacionFichero.listaORs.get(i));
            } else {
                es2SAT = false;
            }
        }
        if (es2SAT) {
            //Resolucion 2SAT
            double desde = System.currentTimeMillis();
            ArrayList<Clause> formula = new ArrayList();
            System.out.println(listaORs2SAT.size() + " número de Cláusulas 2SAT");
            for (int i = 0; i < listaORs2SAT.size(); i++) {
                if (listaORs2SAT.get(i).variables.size() == 2) {
                    listaORs2SAT.get(i).variables.get(0);
                    Literal a = new Literal(listaORs2SAT.get(i).variables.get(0).nombre, listaORs2SAT.get(i).variables.get(0).valor);
                    Literal b = new Literal(listaORs2SAT.get(i).variables.get(1).nombre, listaORs2SAT.get(i).variables.get(1).valor);
                    Clause siguiente = new Clause(a, b);
                    formula.add(siguiente);

                } else if (listaORs2SAT.get(i).variables.size() == 1) {
                    listaORs2SAT.get(i).variables.get(0);
                    Literal a = new Literal(listaORs2SAT.get(i).variables.get(0).nombre, listaORs2SAT.get(i).variables.get(0).valor);
                    Clause siguiente = new Clause(a);
                    formula.add(siguiente);
                }
            }
            TwoSat s = new TwoSat(formula);
            boolean satisfactible2sat = s.isSatisfiable2SAT();

            if (satisfactible2sat) {
                System.out.println("2SAT es resoluble");
            } else {
                System.out.println("2SAT es irresoluble");

            }
            double hasta = System.currentTimeMillis();
            System.out.println("se ha tardado en resolverlo (ms) " + (hasta - desde));

        } else {
            //No es 2SAT
            /*
             Ahora se mira si es Horn-SAT
             */
            boolean HornSAT = true;
            ArrayList<ClausulaMultiple> listaHorn = new ArrayList<ClausulaMultiple>();

            for (int i = 0; HornSAT && (i < ecuacionFichero.listaORs.size()); i++) {
                if (esHornSAT(ecuacionFichero.listaORs.get(i))) {
                    listaHorn.add(ecuacionFichero.listaORs.get(i));
                } else {
                    HornSAT = false;
                }
            }
            if (HornSAT) {
                //es HornSAT
                double desde = System.currentTimeMillis();

                System.out.println(listaHorn.size() + " número de Cláusulas HornSAT");
                boolean satisfactible = resolverHornSAT(listaHorn);
                if (satisfactible) {
                    System.out.println("HornSAT es resoluble");
                } else {
                    System.out.println("HornSAT es irresoluble");

                }
                double hasta = System.currentTimeMillis();
                System.out.println("se ha tardado en resolverlo (ms) " + (hasta - desde));

            } else {
                //No es ni 2SAT ni HornSAT así que hay que hacer una heurística

                //Primero hay que saber cuantos literales difernetes tenemos
                //Asi habrá 2^n posibles combinaciones
                ArrayList<Literal> listaVariables = new ArrayList();
                System.out.println("Extrayendo todos los diferentes literales");

                //creas los valores iniciales (valor=true) de todas las varaibles diferentes
                for (int i = 0; i < ecuacionFichero.listaORs.size(); i++) {
                    for (int j = 0; j < ecuacionFichero.listaORs.get(i).variables.size(); j++) {
                        if (!listaVariables.contains(new Literal(ecuacionFichero.listaORs.get(i).variables.get(j).nombre, true))) {
                            listaVariables.add(new Literal(ecuacionFichero.listaORs.get(i).variables.get(j).nombre, true));
                        }
                    }
                }
                System.out.println("Literales extraidos");

                System.out.println("SAT con "+ecuacionFichero.listaORs.size()+" cláusulas y "+listaVariables.size()+" Literales diferentes");
                double desde = System.currentTimeMillis();
                System.out.println("Generando estados heuristicos");

                double numeroMejoresEstados = Math.pow(listaVariables.size(), 0.28);
                numeroMejoresEstados += 15;
                ArrayList<HashMap<String, Boolean>> mejoresEstados = WalkSAT.NmejoresEstados(ecuacionFichero.listaORs,
                        listaVariables, (int) numeroMejoresEstados);
                //valor de la solucion correcta (perfecto de la heuristica)
                int heuristica = 0;
                int ValorPerfecto = ecuacionFichero.listaORs.size();
                //pruebas con los estados de la heuristica
                int acertado = 0;
                System.out.println("Estados heuristicos generados");

                for (int i = 0; ValorPerfecto != heuristica && i < mejoresEstados.size(); i++) {
                    HashMap<String, Boolean> estado = mejoresEstados.get(mejoresEstados.size() - 1 - i);
                    heuristica = WalkSAT.numeroClausulasCiertas(ecuacionFichero.listaORs, estado);
                    acertado = i;
                }

                if (ValorPerfecto == heuristica) {
                    System.out.println("SAT es resoluble");
                    double hasta = System.currentTimeMillis();
                    System.out.println("se ha tardado en resolverlo (ms) " + (hasta - desde));
                    System.out.println("se ha acertado con la heurística al intento " + (acertado + 1));

                } else {
                    //si no se ha dado con un esado final satisfactible con la heuristica, probar con otro tamaño  de heuristica
                    numeroMejoresEstados = Math.pow(2, Math.sqrt(listaVariables.size()));
                    numeroMejoresEstados += 50;
                    mejoresEstados = WalkSAT.NmejoresEstados(ecuacionFichero.listaORs,
                            listaVariables, (int) numeroMejoresEstados);
                    //valor de la solucion correcta (perfecto de la heuristica)
                    heuristica = 0;
                    ValorPerfecto = ecuacionFichero.listaORs.size();
                    //pruebas con los estados de la heuristica
                    acertado = 0;
                    for (int i = 0; ValorPerfecto != heuristica && i < mejoresEstados.size(); i++) {
                        HashMap<String, Boolean> estado = mejoresEstados.get(mejoresEstados.size() - 1 - i);
                        heuristica = WalkSAT.numeroClausulasCiertas(ecuacionFichero.listaORs, estado);
                        acertado = i;
                    }

                    if (ValorPerfecto == heuristica) {
                        System.out.println("SAT es resoluble");
                        double hasta = System.currentTimeMillis();
                        System.out.println("se ha tardado en resolverlo (ms) " + (hasta - desde));
                        System.out.println("se ha acertado con la heurística al intento " + (acertado + 1));

                    } else {

                        //fuerza bruta
                        System.out.println("Generando estados fuerza bruta");

                        ArrayList<HashMap<String, Boolean>> posiblesEstados = WalkSAT.posiblesEstados(listaVariables);
                        System.out.println("estados generados");

                        for (int i = 0; ValorPerfecto != heuristica && i < posiblesEstados.size(); i++) {
                            HashMap<String, Boolean> estado = posiblesEstados.get(posiblesEstados.size() - 1 - i);
                            heuristica = WalkSAT.numeroClausulasCiertas(ecuacionFichero.listaORs, estado);
                        }
                        if (ValorPerfecto == heuristica) {
                            System.out.println("SAT es resoluble");

                        } else {

                            System.out.println("SAT es irresoluble");

                        }
                        double hasta = System.currentTimeMillis();
                        System.out.println("se ha tardado en resolverlo (ms) " + (hasta - desde));

                    }
                }

            }
        }
    }
}
