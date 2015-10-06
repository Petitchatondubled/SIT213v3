#!/bin/sh
echo Compilation du code source
javac -encoding ISO-8859-1 */*.java


echo Génération du javadoc
javadoc -encoding ISO-8859-1 */*.java -d doc

