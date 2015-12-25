/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmiapractica1;

import java.util.ArrayList;

/**
 *
 * @author shathe
 */
public class OperacionesHornSAT {

    /*
     Devuelve tre si y solo si hay clausulas de un literal, y cuyos nombres coincidan
     y valores sean opuestos
     */
    public static boolean hayClausulasOpuestas(ArrayList<ClausulaMultiple> lista) {
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(i).variables.size() == 1 && lista.get(j).variables.size() == 1) {
                    //tamaño coincide y es 1
                    if (lista.get(i).variables.get(0).nombre.equals(lista.get(j).variables.get(0).nombre)
                            && lista.get(i).variables.get(0).valor != lista.get(j).variables.get(0).valor) {
                        //se ha encontrado
                        encontrado = true;
                    }
                }
            }
        }
        return encontrado;
    }
    /*
     Devuelve una clausula cuyo tamaño sea uno, sino existe ninguna, devuelve nulo
     */

    public static ClausulaMultiple SiguienteClausulaUnitaria(ArrayList<ClausulaMultiple> lista, ArrayList<ClausulaMultiple> vistas) {
        ClausulaMultiple siguiente = null;

        for (int i = 0; siguiente == null && i < lista.size(); i++) {
            if (lista.get(i).variables.size() == 1) {
                //Encontrada una clausula unitaria, ahora hay que ver que esa encontrada no se ha visto ya 
                boolean encontrada = false;
                for (int j = 0; !encontrada && (j < vistas.size()); j++) {
                    //miras si ya la habias encontrado anteriormente
                    if (lista.get(i) == vistas.get(j)) {
                        encontrada = true;
                    }
                }
                //no se habia visto antes
                if (!encontrada) {
                    siguiente = lista.get(i);
                }
            }
        }

        return siguiente;
    }
    /*
    Devuelve una lista de Clausulas simplificando una lista dada y una clausula unitaria
    */
    public static ArrayList<ClausulaMultiple> SimplificarClausulas(ArrayList<ClausulaMultiple> lista, ClausulaMultiple ClausulaUnitaria) {

        for (int i = 0; i < lista.size(); i++) {
            boolean eliminarClausula = false;

            for (int j = 0; j < lista.get(i).variables.size(); j++) {
                boolean eliminarLiteral = false;
                if (lista.get(i) != ClausulaUnitaria
                        && lista.get(i).variables.get(j).nombre.equals(ClausulaUnitaria.variables.get(0).nombre)) {
                    //la variable se llama igual, asi que ahora hay que ver el valor
                    //si son de valor contrario, hay que eliminar el literal contrario
                    //si es del mismo valor, lo que hay que eliminar es la clausula
                    if (lista.get(i).variables.get(j).valor == ClausulaUnitaria.variables.get(0).valor) {
                        //eliminar clausula
                        eliminarClausula = true;
                    } else {
                        //eliminar literal
                        eliminarLiteral = true;
                    }
                }
                if (eliminarLiteral) {
                    lista.get(i).variables.remove(j);
                    j--;
                }
            }
            if (eliminarClausula){
                lista.remove(i);
                i--;
            }

        }

        return lista;
    }
}
