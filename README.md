# GLP-Spatial

Ce projet est réalisé par Alice MABILLE, Lucas LENAERT et Benjamin PAUMARD, étudiants en deuxième année de licence
informatique à CY Cergy Paris Université. Ce projet met en application les connaissances acquises dans l'UE Génie
Logiciel.

## Introduction

Cette application a pour but de simuler (de manière simplifiée) le lancement d'une charge utile en orbite terrestre ou à destination d'une
planète du système solaire.

## Installation

> Cette application requiert [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) ou une version supérieure.

Deux version sont disponibles [ici](https://github.com/benjaminpmd/GLP-Spatial/releases) :
* Une version JAR
* Une version contenant le code source

Afin d’exécuter le code source, certaines librairies doivent être installées :

* [JFreechart](https://www.jfree.org/jfreechart/download.html)
* [JUnit](https://github.com/junit-team/junit4/wiki/Download-and-Install)
* [log4J](https://logging.apache.org/log4j/2.x/download.html)

## Ajout de destinations et de centres de lancement

### Ajout de destination

Il est possible d'ajouter des destinations, pour ceci, il suffit d'ajouter une ligne comme ci-dessous dans le fichier 
suivant `/assets/csv/celestialObjects.csv` :

> Nom de l’objet;Rayon de l’objet;Masse de l’objet ;Distance de la terre;0

Toutes les informations doivent être en unité SI (distances en m, masses en kg).

Vous pouvez aussi ajouter une image PNG de l’objet dans le dossier `/assets/img/`. Attention, le nom de l’image doit 
être identique à celui indiqué dans le fichier CSV.

## Ajout de centre

Il est possible d’ajouter un centre de lancement, pour ceci, il suffit d’ajouter une ligne comme ci-dessous dans le 
fichier suivant `/assets/csv/centers.csv` :

> Nom du centre;Degrés par rapport au point de départ

Le point de départ est situé à 0 PI.
