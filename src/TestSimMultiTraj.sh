#!/bin/bash
echo Test avec Source Fixe, NRZ form avec un seul trajet dt=30 at=0.5
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form NRZ -ti 1 30 0.5 
echo -e "\n\n\n"

echo Test avec Source Fixe, NRZT form avec un seul trajet dt=30 at=0.6
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form NRZT -ti 1 30 0.6
echo -e "\n\n\n"


echo Test avec Source Fixe, RZ form avec un seul trajet dt=30 at=0.7
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001 -form RZ -ti 1 30 0.7
echo -e "\n\n\n"


echo Test avec Source Fixe avec erreur en  argument, attenuation sup à 1
echo --------------------------
echo -e "Envoie de 10101111"
java test/Simulateur -s -mess 10101111 -form NRZZ -ti 1 30 1.2
echo -e "\n\n\n"

echo Test avec Source Aleatoire, NRZ form avec 2 trajets
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZ -ti 1 30 0.5 -ti 2 60 0.2
echo -e "\n\n\n"

echo Test avec Source Aleatoire, NRZT form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZT -ti 1 30 0.5 -ti 2 60 0.2
echo -e "\n\n\n"

echo Test avec Source Aleatoire, RZ form
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form RZ -ti 1 30 0.5 -ti 2 60 0.2
echo -e "\n\n\n"

echo Test avec Source Aleatoire,NRZ form un trajet avec bruit gaussien
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZ -ti 1 30 0.5 -ti 2 60 0.2 -snr -1
echo -e "\n\n\n"


echo Test avec Source Aleatoire,NRZT form un trajet avec bruit gaussien
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZT -ti 1 30 0.5 -ti 2 60 0.2 -snr -1
echo -e "\n\n\n"


echo Test avec Source Aleatoire,RZ form un trajet avec bruit gaussien
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form RZ -ti 1 30 0.5 -ti 2 60 0.2 -snr -1
echo -e "\n\n\n"

echo Test avec Source Aleatoire,NRZ form un trajet avec bruit gaussien
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s -form NRZ -ti 1 30 0.5 -ti 2 60 0.2 -snr -500
echo -e "\n\n\n"

echo End TestSimMultiTraj

