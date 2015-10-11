#!/bin/sh
java -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar"  test/histogramm $1 $2 $3 $4 \&& java -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar" test/NormalDistribution
