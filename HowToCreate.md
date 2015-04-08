Description: how to create environment for automated downloading.

# Introduction #

Below is a description how to create environment. Bad news is that it seems complicated a little bit but good news is that after doing it there is no need to bother any more. Everything will be running in the background.

# Details #

## Decide ##

It is recommended to create additional user (for instance podcast) to keep all stuff. It is more safe and the solution does not mingle with your main activities. This way it is quite safe and easy to remove this solution or migrate it to another box.

## Download ##

Download two file: mp3.jar and resource.zip. Unzip resource.zip. Warning: before unzipping make sure that you do not overwrite any other files.

## Create environment ##
Create subdirectory, for instance podcast.

Copy all files from unzipped resource.zip files and mp3.jar to podcast directory.

Following directory structure should be created (! - file needs customization)

```
podcast
  copy.sh !
  mp3.jar 
  pget.sh !
  crontab !
  podcast.sh !
  podcast -
    bashpodder.shell
    bp.conf !
    bp.conf1 !
    bp.conf2 !
    bp.conf3 !
    bp.conf4 !
    bp.conf5 !
    parse_enclosure.xsl.xml
```

Customization details:

### copy.sh ###
> This file is used to copy downloaded podcasts to mp3 player.

> !Customize Modify parameters: path to mp3.jar, podcast directory and media destination directory.

### pget.sh ###
> This file is used to launch background downloading as cron task. Important: should contain full path names.

> !Customize Modify full path name to podcast.sh script file.

### crontab ###
> Example crontab file

> !Customize As needed. Default is to launch downloading once a day.

### podcast.sh ###
> Starts downloading. Is launched as cron background task and may be started manually.

> !Customize As needed.

### podcast/ ###
> ### bp.conf ###
> ### bp.conf1 ###
> ### bp.conf2 ###
> ### bp.conf3 ###
> ### bp.conf4 ###

> Addresses of pages to be downloaded.

> !Customize Examples reflect my personal favorites. Should be modified as needed.