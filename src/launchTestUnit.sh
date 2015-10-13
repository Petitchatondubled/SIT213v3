#!/bin/sh
javac ../test/TestSimulateur ../test/
javac ../test/TestTransmetteurBruite ../test/
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestSimulateur
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestTransmetteurBruite


