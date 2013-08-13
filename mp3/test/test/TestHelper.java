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
package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Logger;

import com.mp3organizer.directory.CopyFile;
import com.mp3organizer.directory.DestDirectory;
import com.mp3organizer.directory.FileDescr;
import com.mp3organizer.directory.ITestInterrup;
import com.mp3organizer.exception.Mp3Exception;

abstract class TestHelper {

	protected Logger log = Logger.getLogger("mp3.reader");

	protected void removeDir(final String dir) throws Mp3Exception {
		DestDirectory deste = new DestDirectory(log);
		deste.readDirectory(dir);
		for (FileDescr fd = deste.getFileAndRemove(); fd != null; fd = deste
				.getFileAndRemove()) {
			CopyFile.removeFile(fd.getFileName());
		}

	}

	protected static void pause(int seconds) {
		Date start = new Date();
		Date end = new Date();
		while (end.getTime() - start.getTime() < seconds * 1000) {
			end = new Date();
		}
	}

	protected String getSouDir(final String rdir) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL u = classLoader.getResource(rdir);
		String pa = u.getPath();
		return pa;
	}

	protected class TestI implements ITestInterrup {

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

	protected void copyS(final String dir, final String file,
			final String destdir, final String destprefix) throws IOException {
		String p = dir + File.separator + file;
		String de = file;
		if (destprefix != null) {
			de = destprefix + file;
		}
		CopyFile.copyFile(p, destdir, de, null);
	}

	protected void createSubDir(String dir, String subdir) {
		File d = new File(dir, subdir);
		if (!d.exists())
			d.mkdir();
	}

}
