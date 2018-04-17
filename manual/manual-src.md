

# FFAbrams

## Logiciel d'encodage en Java

### Nicolas Leroy(LERN13029600)
### Francis Manzanilla(MANF24049403)
### Guillaume Ryckaert(RYCG06079402)


## 1. Lancement

Nous vous conseillons de lancer le Logiciel via le fichier **.jar** fourni.
En effet, notre Logiciel a besoin d'extraire la libraire **ffmpeg** dans un dossier temporaire afin de pouvoir fonctionner, cette librairie n'étant pas compressée dans le projet eclipse, il vaut mieux lancer le jar avant de démarrer ce dernier.

## 2. Fonctionalités
######  Conteneurs supportés
Le Logiciel supporte les formats contenurs suivants:
  - *avi*
  - *mp4*
  - *3gp*
  - *3g2*
  - *mkv*
  - *m4a*
  - *mp3*
  - *ogg*
  - *oga*
  - *aac*
  - *mka*

###### Codecs video
Il est possible d'exporter vers les codecs audio suivants:
- *h264*
- *hevc*
- *Xvid*

###### Definition de la qualité vidéo:
La qualité vidéo dépend de plusieurs facteurs, majoritairement:
  - La qualité de la vidéo de base,
  - Le codec video utilisé,
  - La résolution choisie,
  - Le débit de donnés,

Il y a deux façons de déterminer ce dernier:

- Via le bitrate video
      Le bitrate video représente le taux de donnés lues a chaque seconde pour pouvoir
      décoder la vidéo. Plus il est élevé, plus le risque de voir des artefacts apparaitre
      est faible.

- Via le CRF (Constant Rate factor)
      C'est un nombre compris entre 0 et 51, qui fait determine la qualité de la vidéo.
      Le bitrate ne sera pas constant, mais variable en fonction des besoins.
      0 représente la meilleure qualité possible, 51 la pire.

> NB: Un bitrate/CRF trop élevé peut entrainer des erreurs de lecture, car la quantité de données a lire est trop importante pour être traitée en temps réel.

Les résolutions supportés sont des ensembles de deux entiers pairs, supérieurs a 64.

> NB: Une résolution trop grande peut résulter en une erreur de l'encodeur, qui doit allouer trop de memoire afin de pouvoir continuer.

###### Définiton de la qulité audio:

La qualité audio peut être modifiée a l'aide du choix du codec, ainsi que du bitrate audio.

###### Ajout d'une seconde piste audio:

Il est possible d'ajouter une seconde piste audio au fichier, a l'aide du bouton "Add audio file" Cette piste sera encodée avec le codec et bitrate audio chosi.

###### Création de sous-titres:

Il est possible de créer des sous titres via l'outil fourni. Ces derniers seront exportés sous la forme d'un fichier **.srt** qui sera automatiquement inclus a l'encodage.

Il est égalemet possible d'ajouter un autre fichier de sous-titres via le browser.

>NB: Les sous-titres sont encodés via la convention mov_text, ce qui permet a la plupart des lecteurs de les activer/désactiver a loisir. Il n'est pas possible d'inclure des sous-titres de cette façon en utilisant le contenur avi, qui ne supporte pas cette convention.

###### 3. Si ça tourne mal...

Fermer la barre de progression ira tuer le process d'encodage.
