#!/bin/sh
echo Compilation du code sources
javac -cp ".:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar" -encoding ISO-8859-1  */*.java

echo Génération du javadoc
javadoc -classpath ".:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar" -encoding ISO-8859-1 */*.java -d doc

