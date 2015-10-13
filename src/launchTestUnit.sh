#!/bin/sh
javac test/TestSimulateur.java test/
javac test/TestTransmetteurBruite.java test/
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestSimulateur
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestTransmetteurBruite


