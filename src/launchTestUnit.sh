#!/bin/sh
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestSimulateur
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestTransmetteurBruite
java -cp ".:../junit-4.10.jar:../test"  org.junit.runner.JUnitCore TestTransmetteurBruiteMultiTrajet


