#!/bin/bash
echo Test avec Source Fixe
echo --------------------------
echo "Envoie de 10011001"
java test/Simulateur -s -mess 10011001
echo -e "\n\n\n"

echo Test avec Source Fixe avec erreur en  argument
echo --------------------------
echo -e "Envoie de 1010120111"
java test/Simulateur -s -mess 1010120111
echo -e "\n\n\n"

echo Test avec Source Aleatoire n°1
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 100 bits"
java test/Simulateur -s
echo -e "\n\n\n"

echo Test Source Aleatoire n°2
echo --------------------------
echo -e "Envoie d'un signal aléatoire de 200 bits"
java test/Simulateur -s -mess 000200
echo -e "\n\n\n"

echo Test avec Source Aleatoire n°3
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 300 bits"
java test/Simulateur -s -mess 000300
echo -e "\n\n\n"

echo Test avec Source Aleatoire avec erreur en argument
echo --------------------------
echo -e "Envoie d'un signal aleatoire de 1212102  bits"
java test/Simulateur -s -mess 1212102
echo -e "\n\n\n"


echo End TestSim
