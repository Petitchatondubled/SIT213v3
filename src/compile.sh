#!/bin/sh
echo Compilation du code sources
javac -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar:../junit-4.10.jar" -encoding ISO-8859-1  */*.java
javac -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar:../junit-4.10.jar" -encoding ISO-8859-1  ../test/*.java


echo Génération du javadoc
javadoc -classpath ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar:../junit-4.10.jar" -encoding ISO-8859-1 */*.java -d doc

