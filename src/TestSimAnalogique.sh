#!/bin/bash
echo Test avec Source Fixe, NRZ form
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form NRZ 
echo -e "\n\n\n"

echo Test avec Source Fixe, NRZT form
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form NRZT
echo -e "\n\n\n"


echo Test avec Source Fixe, RZ form
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form RZ
echo -e "\n\n\n"


echo Test avec Source Fixe avec erreur en  argument, forme inconnue
echo --------------------------
echo -e "Envoie de 10101111"
java test/Simulateur -s -mess 10101111 -form NRZZ
echo -e "\n\n\n"

echo Test avec Source Aleatoire, NRZ form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZ
echo -e "\n\n\n"

echo Test avec Source Aleatoire, NRZT form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZT
echo -e "\n\n\n"

echo Test avec Source Aleatoire, RZ form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form RZ
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, NRZ form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits"
java test/Simulateur -s -mess 000200 -form NRZ -nbEch 60
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, NRZT form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits"
java test/Simulateur -s -mess 000200 -form NRZ -nbEch  60
echo -e "\n\n\n"


echo Test avec Source Aleatoire, 60 echantillon, RZ form
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 200 bits"
java test/Simulateur -s -mess 000200 -form RZ -nbEch  60
echo -e "\n\n\n"



echo Test avec Source Aleatoire avec erreur en argument, echantillon inferieur a 3
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 100 bits"
java test/Simulateur -s  -form NRZT -nbEch 2
echo -e "\n\n\n"

echo End TestSimAnalogique


