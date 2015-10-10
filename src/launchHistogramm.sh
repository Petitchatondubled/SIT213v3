#!/bin/sh
java -cp ".:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar"  test/histogramm $1 $2 $3 $4 \&& java -cp ".:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar" test/NormalDistribution
