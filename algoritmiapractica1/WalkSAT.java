/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmiapractica1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author shathe
 */
public class WalkSAT {
    /*
     Funcion heurística, devuelve el número de clausulas ciertas dado unos valores
     */
    public static int numeroClausulasCiertas(ArrayList<ClausulaMultiple> lista, HashMap<String, Boolean> valores) {
        int numero = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (esCierta(lista.get(i), valores)) {
                numero++;
            }
        }

        return numero;
    }
    /*
     Devuelve si una clausula es cierta dado unos valores de los literales
     */

    public static boolean esCierta(ClausulaMultiple clausula, HashMap<String, Boolean> valores) {
        boolean cierta = false;
        //para que una clausula sea cierta, necesita que algun valor fianl de un literal sea cierto
        for (int i = 0; !cierta && i < clausula.variables.size(); i++) {

            boolean valorVariable = valores.get(clausula.variables.get(i).nombre);
            /*Si en la clausula es true, significa que es una variable "a"
             y si es false, es que era un literal "-a", para que esto sea un valor final
             true, deben de coincidir (true-true) o (false-false)
             */
            cierta = (valorVariable == clausula.variables.get(i).valor);
        }

        return cierta;
    }
    
    /*
    devuelve todos los estados posibles dada una lista de Literales
    */
    public static ArrayList<HashMap<String, Boolean>> posiblesEstados(ArrayList<Literal> listaVariables) {
        ArrayList<HashMap<String, Boolean>> estados = nuevosEstadosDesde(listaVariables, 0);
        anadirEstado(estados, listaVariables);

        return estados;
    }
    
    /*
    Devuelve todas las combinaciones de estados cambiando desde la posicion [desde]
    hasta el fin de la lista
    */
    public static ArrayList<HashMap<String, Boolean>> nuevosEstadosDesde(ArrayList<Literal> listaVariables, int desde) {
        ArrayList<HashMap<String, Boolean>> estados = new ArrayList();

        for (int i = desde; i < listaVariables.size(); i++) {
            if (listaVariables.get(i).valor == true) {
                ArrayList<Literal> nueva = (ArrayList<Literal>) listaVariables.clone();

                nueva.set(i, new Literal(nueva.get(i).nombre, !nueva.get(i).valor));
                anadirEstado(estados, nueva);
                ArrayList<HashMap<String, Boolean>> nuevosEstados = nuevosEstadosDesde(nueva, i + 1);
                estados = juntarEstados(estados, nuevosEstados);
            }

        }
        return estados;

    }
    /*
    Añade un estado a la lista de estados
    */
    public static void anadirEstado(ArrayList<HashMap<String, Boolean>> estados, ArrayList<Literal> listaVariables) {
        HashMap<String, Boolean> estado = new HashMap();
        for (int i = 0; i < listaVariables.size(); i++) {
            estado.put(listaVariables.get(i).nombre, listaVariables.get(i).valor);
        }
        estados.add(estado);
    }
    /*
    Unifica dos listas de estados en una
    */
    public static ArrayList<HashMap<String, Boolean>> juntarEstados(ArrayList<HashMap<String, Boolean>> a, ArrayList<HashMap<String, Boolean>> b) {

        for (int i = 0; i < b.size(); i++) {
            a.add(b.get(i));
        }
        return a;
    }
    /*
    Devuelve una lista clave-valor, con el número de veces que aparace cada literal
    en la lista (ecuacion) en forma de literal positivo (a)
    */
    public static HashMap<String, Integer> vecesPositivo(ArrayList<ClausulaMultiple> lista) {
        HashMap<String, Integer> positivo = new HashMap();
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).variables.size(); j++) {
                if (lista.get(i).variables.get(j).valor && !positivo.containsKey(lista.get(i).variables.get(j).nombre)) {
                    positivo.put(lista.get(i).variables.get(j).nombre, 1);
                } else if (lista.get(i).variables.get(j).valor && positivo.containsKey(lista.get(i).variables.get(j).nombre)) {
                    positivo.replace(lista.get(i).variables.get(j).nombre, positivo.get(lista.get(i).variables.get(j).nombre) + 1);
                }
            }
        }
        return positivo;
    }
    
    /*
     Devuelve una lista clave-valor, con el número de veces que aparace cada literal
    en la lista (ecuacion) en forma de literal negativo (-a)
    */
    public static HashMap<String, Integer> vecesNegativo(ArrayList<ClausulaMultiple> lista) {
        HashMap<String, Integer> negativo = new HashMap();
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.get(i).variables.size(); j++) {
                if (!lista.get(i).variables.get(j).valor && !negativo.containsKey(lista.get(i).variables.get(j).nombre)) {
                    negativo.put(lista.get(i).variables.get(j).nombre, 1);
                } else if (!lista.get(i).variables.get(j).valor && negativo.containsKey(lista.get(i).variables.get(j).nombre)) {
                    negativo.replace(lista.get(i).variables.get(j).nombre, negativo.get(lista.get(i).variables.get(j).nombre) + 1);
                }
            }
        }

        return negativo;
    }
/*
    El coste de una heuristica buena costaría mas que recorrer todos los estados
    */
    public static ArrayList<HashMap<String, Boolean>> NmejoresEstados(ArrayList<ClausulaMultiple> lista,ArrayList<Literal> listaVariables,int n ) {
        HashMap<String, Integer> vecesNegativo=vecesNegativo( lista);
        HashMap<String, Integer> vecesPositivo=vecesPositivo( lista);
        ArrayList<HashMap<String, Boolean>> mejores=new ArrayList();
        for (int i=0;i<n;i++){
            HashMap<String, Boolean> siguiente= new HashMap();
            for(int j=0;j<listaVariables.size();j++){
                Integer positivo=vecesPositivo.get(listaVariables.get(j).nombre);
                Integer negativo=vecesNegativo.get(listaVariables.get(j).nombre);
                if(positivo==null){
                    positivo=0;
                }
                if(negativo==null){
                    negativo=0;
                }
                siguiente.put(listaVariables.get(j).nombre, valorMejorPseudoAleatorio(positivo,negativo));
            }
            mejores.add(siguiente);
        }
        

        return mejores;
    }
    
    /*
    Dado el numero de veces positibvo y negativo, devuelve un booleano, el cual,
    tendrá mas posibilidades de ser true cuanta mas diferencia haya entre positivo-negativo
    */
    public static boolean valorMejorPseudoAleatorio(int positivo,int negativo){
        double positivod=positivo;
        if(positivod==0)positivod=0.3;
        double negativod=negativo;
        if(negativod==0)negativod=0.3;
        Random a=new Random();
        double P_N=positivod/negativod;
        double aleatorio=a.nextDouble();
        if(P_N>1){
            //hay mas literales positivos (l)
            P_N*=P_N;
            P_N*=aleatorio;
        }else{
            //hay mas negados
            P_N*=P_N;
            P_N/=aleatorio;

        }
        
        return P_N>1;
    }
}
