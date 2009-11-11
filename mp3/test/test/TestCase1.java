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
package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.junit.Test;

import com.mp3organizer.directory.CopyFile;
import com.mp3organizer.directory.DestDirectory;
import com.mp3organizer.directory.FileDescr;
import com.mp3organizer.directory.ITestInterrup;
import com.mp3organizer.exception.Mp3Exception;
import com.mp3organizer.param.GetParam;
import com.mp3organizer.run.RunBox;

public class TestCase1 {

    private Logger log = Logger.getLogger("mp3.reader");

    private void removeDir(final String dir) throws Mp3Exception {
        DestDirectory deste = new DestDirectory(log);
        deste.readDirectory(dir);
        for (FileDescr fd = deste.getFileAndRemove(); fd != null; fd = deste.getFileAndRemove()) {
            CopyFile.removeFile(fd.getFileName());
        }

    }

    private String getSouDir(final String rdir) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL u = classLoader.getResource(rdir);
        String pa = u.getPath();
        return pa;
    }

    /**
     * Test that directory is read properly
     * Step1: read test directory.
     * Verification 1: file1.mp3 is recognized
     * Verification 2: file3.mp3 is recognized
     * Verification 3: file3.txt is NOT recognized
     * Verification 4: ooooo (random) is NOT recognized
     * Verification 5: file4.mp3 is recognized
     *
     * @throws Mp3Exception
     */
    @Test
	public final void Test1() throws Mp3Exception {
        String dir = getSouDir("testresource");
        System.out.println("Read file " + dir);
        // Step 1
        DestDirectory dest = new DestDirectory(log);
        dest.readDirectory(dir);
        // Verification 1
        assertTrue(dest.isFile("file1.mp3"));
        // Verification 2
        assertTrue(dest.isFile("file2.mp3"));
        // Verification 3
        assertFalse(dest.isFile("file3.txt"));
        // Verification 4
        assertFalse(dest.isFile("ooooooo"));
        // Verification 5
        assertTrue(dest.isFile("file4.mp3"));
    }

    private void copyS(final String dir, final String file, final String destdir,
            final String destprefix) throws IOException {
        String p = dir + File.separator + file;
        String de = file;
        if (destprefix != null) {
            de = destprefix + file;
        }
        CopyFile.copyFile(p, destdir, de, null);
    }

    /**
     * Test that file from source to dest are copied properly
     * Step1: Remove all file is dest
     * Step2: Copy files: file1.mp3 and file4.mp3 
     * Verification 1: test that file1.mp3 and file4.mp3 is read in dest directory
     * Verifiaction 2: test that file2.mp3 is NOT in the dest directory
     * @throws Mp3Exception
     * @throws IOException
     */
    @Test
	public final void Test2() throws Mp3Exception, IOException {
        String dir = getSouDir("testresource");
        String dest = getSouDir("testoutdir");
        removeDir(dest);
        DestDirectory deste = new DestDirectory(log);
        deste.readDirectory(dest);
        copyS(dir, "file1.mp3", dest,null);
        copyS(dir, "file4.mp3", dest,null);
        deste = new DestDirectory(log);
        deste.readDirectory(dest);
        assertTrue(deste.isFile("file1.mp3"));
        assertTrue(deste.isFile("file4.mp3"));
        assertFalse(deste.isFile("file2.mp3"));
    }

    private class TestI implements ITestInterrup {

        private int no = 0;

        @Override
        public void copyI(final String sourceFile, final String destFile)
                throws IOException {
            no++;
            if (no == 2) {
                throw new IOException();
            }
        }
    }

    /**
     * Test one copying with one file removal.
     * Step 1: clear dest directory
     * Step 2: create 2 files: file1.mp3 and file2.mp3
     * Important: file2.mp3 should have later modification date than file1.mp3 !
     * Step 3; start copy and emulate that after copying second dest is full
     * Verification 1: file1.mp3 should be remove at dest
     * Verification 2: all others files should be copied
     * @throws Mp3Exception
     * @throws IOException
     */

    @Test
	public final void Test3() throws Mp3Exception, IOException {
        String dir = getSouDir("testmp3source" + File.separator + "2009-10-03");
        String dest = getSouDir("testoutdir");
        // Step 1
        removeDir(dest);
        DestDirectory deste = new DestDirectory(log);
        deste.readDirectory(dest);
        // Step 2
        copyS(dir, "file1.mp3", dest,"2009-10-03-");
        copyS(dir, "file2.mp3", dest,"2009-10-03-");
        GetParam pa = new GetParam();
        dir = getSouDir("testmp3source");
        pa.setSourceDirectory(dir);
        pa.setDestDirectory(dest);
        // Step 3
        RunBox.run(pa, log, new TestI());

        deste = new DestDirectory(log);
        deste.readDirectory(dest);
        // Verification 1
        assertFalse(deste.isFile("2009-10-03-file1.mp3"));
        // Verification 2
        assertTrue(deste.isFile("2009-10-03-file2.mp3"));
        assertTrue(deste.isFile("2009-10-03-file4.mp3"));
        assertTrue(deste.isFile("2009-10-03-file5.mp3"));
    }
}
