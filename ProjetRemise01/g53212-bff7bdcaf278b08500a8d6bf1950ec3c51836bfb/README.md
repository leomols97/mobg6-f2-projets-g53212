# Projet MOBG5

Ce dépôt contient les sources du projet "Le Ménestrel".

## Description

Une application informative qui listerait des événements passés sous forme de liste et des événements futurs sous forme de liste également.
Il listerait également les bières proposées à chaque événement et les brasseries associées.

Menu Hamburger dans lequel il y aura :

A. Accueil
B. Événements
E. Bières
F. Artistes
G. Recherche (À mettre au dessus)

A. Qu’est-ce que notre projet
B. Liste des événements passés ainsi que la possibilité de cliquer sur chaque événement pour en afficher plus d’infos (avec possibilité de retrouver les playlists) (avec une séparation visuelle entre les passés et les futurs) (faire des modals (genres de popup, mais en plus grand, qui invite à l’action) pour les infos supplémentaires)
E. Liste des bières promues ainsi que les brasseries associées
F. Liste des artistes promus ainsi qu’un lien vers leur page Instagram ainsi que la possibilité de voir leur interview qui a lieu après leur live
G. Possibilité de rechercher un artiste / brasserie / bière




## Persistance des données

L'application conserve un historique des recherches effectuées. Cet historique est persisté dans la base de données locale de l'application si nécessaire et mise à jour lors d'une interaction de l'utilisateur

Les données relatives aux comptes utilisateurs, aux notes et aux critiques sont persistées via firebase ou Heroku : <url du projet firebase>

## Backend

L'application mobile appelle via des services web le backend <nom du backend>. Les sources sont disponibles sur le dépôt <url du dépôt du backend>.

## Service rest

Pour collecter les données relatives aux films, des appels au service rest suivant sont effectués : https://www.themoviedb.org/documentation/api

## Auteur

**Léopold Mols** g53212
