Alonso Ruiz, Iñigo 	 665959@unizar.es 	 a665959
Dieste Cortés, Alejandro 	541892@unizar.es 	a541892

Repato del trabajo:

Se ha intentando que ambos trabajasemos lo mismo, más o menos ha estado 40-60% ambos.
Iñigo Alonso se ha encargado de la heurística y fuerza Bruta y también parte del HornSAT
Alejandro Dieste se ha encargado del 2SAt y las pruebas y parte del HornSAT


Para ejecutar el programa:

ejecutarPrograma.sh: se ejecuta ./ejecutarPrograma.sh  y te preguntará que introduzcas por teclado la ecuación
ejecutarPrograma.sh: se ejecuta ./ejecutarProgramaFichero -f Ficheros_Ecuaciones_SAT_Simples/SAT1.txt
es decir, se le pasa por parametro el fichero que contiene la ecuación
ejecutarPruebas.sh:  se ejecuta ./ejecutarPruebas.sh y se ejecutará pruebas aleatorias de menor a mayor carga 


Explicación del programa por pasos:

El programa principal lee de teclado o de fichero la ecuación.
(No se leen variables repetidas, es decir, (a+a) se lee como (a))
Primero se comprueba si es 2SAT
ES 2SAT:
Se utilizan las clases propias de 2SAT (Clause, TwoSAT, Kosaraju, DirectedGraph). La idea es simplificar por la propiedad transitiva (a or b) and (not b or c) = (a or c).
Se contruye el grafo de implicaciones. Para cada cláusula (A or B), se añade:  (~A -> B) y (~B -> A). Y con el algoritmo de Kosaraju se comprueba si cualquiera de los literales
y su negado no esté fuertemente ligado, sino, es satisfacible, en cambio, si sí que hay, entonces no lo es.

No es 2SAT:
	Se comprueba si es HornSAT
	Es HornSAT:
	Paso1. Miras si hay clausulas unitarias(de un solo literal) opuestas  (ejemplo:   (a)*(-a) si esto pasa, no es satisfactible.
	Paso 2. Buscas una clausula unitaria (sin repetir ninguna que hayas visto ya), si no encuentras, es satisfactible, y si la encuentras, al paso 3
	Paso 3. Simplificas dada esa clausula unitaria, las demás, si tienen ese literal, se elimina la cláusula, si tienen el literal negado, se elimina ese literal de la cláusula.
	ejemplo: (a) *(-a+b)*(a+c)*(d+b) --> (a)*(b)*(d+b)
	Vuelta al paso 1.
	
	No es HornSAT:
	Se cogen unos pocos (bastante pocos) estados posibles. Los mejores según una heurística*.
	Se evalúan y se mira si es satisfactible. Si no lo es, se vuelve hacer lo mismo pero cogiendo un número más considerado de estados con esta hurística.
	Si no sales que es satisfactible ya se hace una fuerza bruta completa y se muestra por pantalla si es o no satisfactible.

Heurística:
Se lee de cada distinto literal de la ecuación, el número de veces que aparece negado y sin negar.
El valor de esa variabe, tiene más posibilidades de ser true, cuanta más diferencia entre positivo/negativo haya. 
Es decir, si un literal en una ecuación está 7 veces positivo (a), y 2 veces negado  (-a). hay muchas más posibilidades de que en los mejores estados posibles, esta variable sea verdad.
Existe un punto de aleatoriedad.
La idea de esta heurística es que para que en una cláusula sea cierta, que es lo que se busca, es que los literales que haya dentro de ella sean true.
Es decir, los literales (-a) hay que darles el valor false y los literales (a) hay que darles valor positivo.

El programa de pruebas (Test):

Ejecuta una ecuación generada aleatoria aumentando el número de clausulas, de literales utilizados y de literales dentro de las clausulas.
Restricciones:
empieza con número de Clausulas = 2 y como máximo alcanza 300000
en cada iteración: e  numero Clausulas se dobla
El número de literales es bastante más bajo que el número de cláusulas, pero directamente proporcional a esta


Fuentes de conocimiento en las cuales nos hemos basado para comprender qué y cómo implementar.
2SAT:  http://www.keithschwarz.com/interesting/code/2sat/
HornSAT: https://en.wikipedia.org/wiki/Horn-satisfiability
https://en.wikipedia.org/wiki/2-satisfiability

Se ha utlizado código ageno para la realización del 2SAT. Clases: Literal, Clause, TwoSAT, Kosaraju, DirectedGraph
Fuente: http://www.keithschwarz.com/interesting/code/2sat/
Se ha modificado y adaptado a nuestras necesidades Literal, Clause