/*
 * Copyright 2009 stanislawbartkowski@gmail.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mp3organizer.directory;

import com.mp3organizer.exception.Mp3Exception;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sb
 */
class Directory {

	/** Extension for mp3 files. */
    static final String MP3EXT = ".mp3";

    /**
     * Comparator for file sorting by name.
     */
    private class fCompare implements Comparator<FileDescr> {

        public int compare(final FileDescr f1, final FileDescr f2) {
            return f1.compare(f2);
        }
    }

    /**
     * Comparator for file sorting by modification date.
     */
    private class fCompare1 implements Comparator<FileDescr> {

        public int compare(final FileDescr f1, final FileDescr f2) {
            String f1Name = f1.baseFileName;
            String f2Name = f2.baseFileName;
            return f2Name.compareTo(f1Name);
        }
    }

    /** list of files. */
    private ArrayList<FileDescr> fileList;
    /** Logger. */
    private final Logger log;

    /**
     * Get file list.
     * @return List file.
     */
    Collection<FileDescr> getFileList() {
        return fileList;
    }

    /**
     * Constructor.
     * @param log Logger.
     */
    Directory(final Logger log) {
        this.log = log;
        fileList = new ArrayList<FileDescr>();
    }
    
    /**
     * If file exists on the list.
     * @param filename the file name
     * @return true: if on the list, false: otherwise
     */
    boolean isFile(final String filename) {
        for (FileDescr fd : fileList) {
            if (fd.baseFileName.equalsIgnoreCase(filename)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add file to list.
     * @param fd file to add
     */
    void addFile(final FileDescr fd) {
        fileList.add(fd);
    }

    /**
     * Size sum of all files.
     * @return  bytes of all files.
     */
    long getSize() {
        long size = 0;
        for (FileDescr fd : fileList) {
            size += fd.size;
        }
        return size;
    }
    
    /**
     * The same as getSize but in MB
     * @return number of MBs
     */
    long getSizeMB() {
        return getSize() / 1000000;
    }
    
    /**
     * Remove file from the list.
     * @return file descr of the first file to remove or null if list empty.
     */
    FileDescr getFileAndRemove() {
        if (fileList.size() == 0) {
            return null;
        }
        FileDescr fd = fileList.get(0);
        fileList.remove(fd);
        return fd;        
    }
    
    /**
     * Numnber of file on the list
     * @return number of files.
     */
    
    long noFiles() {
        return fileList.size();
    }


    /**
     * Read directory.
     * @param destDirectory the diretory name,
     * @param onlyDirectory true: only directory names, false: .mp3 files in the directory
     * @param orderLater true: sort by modification date: false: by name
     * @throws Mp3Exception if any error occured
     */
    void readDirectory(final String destDirectory, final boolean onlyDirectory, final boolean orderLater) throws Mp3Exception {
        File dir = new File(destDirectory);
        if (onlyDirectory) {
           log.info("Read directories from " + destDirectory);
        }
        else {
            log.info("Read " + MP3EXT + " files from " + destDirectory + " directory");
        }
        if (dir == null) {
            String mess = destDirectory + " does not exist or is not a directory";
            Mp3Exception e =  new Mp3Exception(mess);
            log.log(Level.SEVERE, mess,e);
            throw e;
        }
        File[] list = dir.listFiles();
        for (File fi : list) {
            FileDescr fd = new FileDescr();
            fd.fileName = fi.getAbsolutePath();
            fd.lastModified = fi.lastModified();
            fd.size = fi.length();
            fd.baseFileName = fi.getName();
            if (onlyDirectory) {
                if (!fi.isDirectory()) {
                    continue;
                }
            } else {
                if (!fd.baseFileName.endsWith(MP3EXT)) {
                    continue;
                }
            }
            addFile(fd);
        }
        if (orderLater) {
            Collections.sort(fileList, new fCompare());
        } else {
            Collections.sort(fileList, new fCompare1());
        }
        
        if (onlyDirectory) {
           log.info(""+fileList.size() + " directories read.");
        }
        else {
            log.info("" + fileList.size() + " files read, " + getSizeMB() + " MB");
        }
        
    }
}