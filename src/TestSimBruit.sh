#!/bin/bash

echo Test avec Source Aleatoire, NRZ form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits avec snr = 5"
java test/Simulateur -s -form NRZ -snr 5
echo -e "\n\n\n"

echo Test avec Source Aleatoire, NRZT form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits avec snr = 5"
java test/Simulateur -s -form NRZT -snr 5
echo -e "\n\n\n"

echo Test avec Source Aleatoire, RZ form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits avec snr = 5"
java test/Simulateur -s -form RZ -snr 5
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, NRZ form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits avec snr = -8"
java test/Simulateur -s -mess 000200 -form NRZ -nbEch 60 -snr -8
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, NRZT form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits avec SNR = -3"
java test/Simulateur -s -mess 000200 -form NRZ -nbEch  60 -snr -8
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, RZ form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits avec SNR = -8"
java test/Simulateur -s -mess 000200 -form RZ -nbEch  60 -snr -8
echo -e "\n\n\n"



echo End TestSimAnalogique


