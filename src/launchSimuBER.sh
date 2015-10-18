#!/bin/sh
echo "lancement de la simulation du BER en fonction du SNR"
java -cp ".:../jcommon-1.0.23.jar:../jfreechart-1.0.19.jar" test.courbeTEB $1 $2 $3 $4 $5 $6 $7 $8
