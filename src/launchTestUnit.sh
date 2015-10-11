#!/bin/sh
java -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar:../junit-4.10.jar:.."  org.junit.runner.JUnitCore test.TestSimulateur
java -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar:../junit-4.10.jar:.."  org.junit.runner.JUnitCore test.TestTransmetteurBruite


