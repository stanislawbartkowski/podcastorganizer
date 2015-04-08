## Description ##

Simple solution for automated podcast downloading and saving to mp3 player.

Can be used on any linux box.

Solution is divided into two parts.

  1. Script (bashpodder) to download your mp3 podcasts as background task (crontab).
  1. Utility to copy new podcasts to attached mp3 player. Automatically deletes old files if there is no free space.

## Prerequisites ##

Java 1.5 or later.

## Additional info ##

The whole space on mp3 player is used for keeping downloaded podcasts. Utility removes outdated podcasts without warning.

## How to use it ##

### Create environment ###
http://code.google.com/p/podcastorganizer/wiki/HowToCreate

Important: is should be done only once. Later all downloading is performed as background task

### Copy new podcast to mp3 player ###

  1. Attach player.
  1. Launch ./copy.sh utility.
  1. Remove mp3 player (when all file are saved) and it is done.