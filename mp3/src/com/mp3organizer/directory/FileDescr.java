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

/**
 *
 * @author sb
 */
public class FileDescr {

	/**
	 * Gettoer for file name
	 * @return the file name
	 */
    public final String getFileName() {
		return fileName;
	}

	String fileName;
    String baseFileName;
    Long lastModified;
    long size;
    String destbaseFileName;
    
    /**
     * Empty constructor
     */
    FileDescr() {        
    }
    
    FileDescr(final FileDescr sou) {
        fileName = sou.fileName;
        baseFileName = sou.baseFileName;
        lastModified = sou.lastModified;
        size = sou.size;
        destbaseFileName = sou.destbaseFileName;
    }

    /**
     * Comparator by modification date.
     * @param f2 The file to compare woith
     * @return: -1: less, 0 equal 1: later
     */
    final int compare(final FileDescr f2) {
        if (lastModified < f2.lastModified) {
            return -1;
        }
        if (lastModified == f2.lastModified) {
            return 0;
        }
        return 1;
    }
}
