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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author sb
 */
public final class CopyFile {

    private CopyFile() {
    }
    
    /**
     * Copy file from sou to dest
     * @param src Source file name
     * @param dst Dest fle name
     * @throws IOException
     */
    private static void copy(final File src, final File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    /**
     * Copy file (full dressed) 
     * @param sourceFile the source file name
     * @param destDir the dest directory
     * @param destFile the file name in dest directory
     * @param iTest testing interrupt (if not null)
     * @throws IOException if any problem occured
     */
    
    public static void copyFile(final String sourceFile,final String destDir, final String destFile,
            final ITestInterrup iTest) throws IOException {
        File in = new File(sourceFile);
        String dFile = destDir + File.separatorChar + destFile;
        if (iTest != null) {
            iTest.copyI(sourceFile, dFile);
        }
        File dest = new File(dFile);
        copy(in,dest);
        long modifiedTime = in.lastModified();
        dest.setLastModified(modifiedTime);
    }
    
    /**
     * Removes file.
     * @param file the file name to be removed.
     */
    public static void removeFile(final String file) {
        File f = new File(file);
        f.delete();
    }
    
}