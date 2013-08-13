/*
 * Copyright 2013 stanislawbartkowski@gmail.com
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
import java.util.logging.Logger;

/**
 *
 * @author sb
 */
public class DestDirectory {
    
	/** Directory stru. */
    private Directory dir;
    
    /**
     * Constructor.
     * @param log Logger
     */
    public DestDirectory(final Logger log) {
        dir = new Directory(log);
    }
       
    /**
     * Reads file from directory.
     * @param destDirectory Directory name to read from.
     * @throws Mp3Exception If any error occured.
     */
    public final void readDirectory(final String destDirectory) throws Mp3Exception {
        dir.readDirectory(destDirectory,false,true);
    }
    
    /**
     * Test if file exists.
     * @param fileName File name to test.
     * @return true: file exists, false: otherwise,
     */
    public final boolean isFile(final String fileName) {
        return dir.isFile(fileName);
    }
    
    /**
     * Get filestru of the first file and removes from list
     * Important: do not remove file, only removes from list.
     * @return FileDescr of the first file or null if list empty
     */
    public final FileDescr getFileAndRemove() {
        return dir.getFileAndRemove();
    }

}
