mkdir bin 2>/dev/null
javac -d ./bin/ -cp ./bin/ ./algoritmiapractica1/*.java
java -cp ./bin/ algoritmiapractica1.SATsolver $1 $2