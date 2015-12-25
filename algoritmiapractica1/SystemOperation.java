/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmiapractica1;

import static algoritmiapractica1.SATsolver.esta;
import static algoritmiapractica1.SATsolver.getValue;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author shathe
 */
public class SystemOperation {
    
    public static Ecuacion leerEcuacion(String ecuacion) {
        ArrayList<ClausulaMultiple> listaORs = new ArrayList();
        Ecuacion ecuacionFichero = new Ecuacion(listaORs);
        try {
            
            /*
             Construye la ecuacion desde la entrada de texto
             */
            String[] ORs = ecuacion.split("\\*");

            for (int i = 0; i < ORs.length; i++) {
                boolean insertar = true;
                String[] variables = ORs[i].split("\\+");
                ArrayList<Literal> listaLiterals = new ArrayList();
                for (int j = 0; j < variables.length; j++) {
                    variables[j] = variables[j].replace("(", "").replace(")", "");
                    if (variables[j].contains("-")) {
                        variables[j] = variables[j].replace("-", "");

                        if (esta(listaLiterals, variables[j])) {
                            //la variable ya estaba insertada, si tiene el mismo valor, no hacer nada
                            //si tiene diferente valor, esta clausula siempre sera verdadera
                            if (getValue(listaLiterals, variables[j])) {
                                //esta clausula siempre valdra true
                                insertar = false;
                            }
                        } else {
                            listaLiterals.add(new Literal(variables[j], false));
                        }
                    } else {
                        if (esta(listaLiterals, variables[j])) {
                            //la variable ya estaba insertada, si tiene el mismo valor, no hacer nada
                            //si tiene diferente valor, esta clausula siempre sera verdadera
                            if (!getValue(listaLiterals, variables[j])) {
                                //esta clausula siempre valdra true
                                insertar = false;

                            }
                        } else {
                            listaLiterals.add(new Literal(variables[j], true));
                        }
                    }
                }
                if (insertar) {
                    listaORs.add(new ClausulaMultiple(listaLiterals));
                }
            }
            ecuacionFichero = new Ecuacion(listaORs);
        } catch (Exception a) {
            String e = a.toString();

        }
        return ecuacionFichero;
    }
}
