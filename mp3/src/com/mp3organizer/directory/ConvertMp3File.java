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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author sb
 */
public class ConvertMp3File {

	private final String mp3Pattern;
	private final String mp3ChangedDir;
	private final Logger log;

	public ConvertMp3File(final String p1, final String p2, final Logger p3) {
		mp3Pattern = p1;
		mp3ChangedDir = p2;
		this.log = p3;
	}

	private class ReadC extends Thread {

		final InputStream i;

		ReadC(final InputStream i) {
			this.i = i;
		}

		@Override
		public void run() {

			byte[] b = new byte[100];

			while (true) {
				try {
					int no = i.read(b);
					for (int ii = 0; ii < no; ii++) {
						char ch = (char) b[ii];
						System.out.print(ch);
					}
				} catch (IOException ex) {
					Logger.getLogger(ConvertMp3File.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}

		}

	}

	public final FileDescr Mp3ChangeSouFileName(final FileDescr sou) throws Mp3Exception {
		try {
			String bfileName = sou.baseFileName;
			FileDescr dest = new FileDescr(sou);
			if (!bfileName.matches(mp3Pattern)) {
				return dest;
			}
			String[] p = Mp3Util.decodePath(sou.fileName);
			String nsou = p[0];
			int i;
			for (i = 1; i < p.length - 1; i++) {
				nsou = Mp3Util.addPath(nsou, p[i]);
			}
			nsou = Mp3Util.addPath(nsou, mp3ChangedDir);
			String convDir = nsou;
			File co = new File(nsou);
			if (!co.exists()) {
				co.mkdir();
			}
			nsou = Mp3Util.addPath(nsou, bfileName);
			dest.fileName = nsou;
			File f = new File(nsou);
			if (f.exists()) {
				log.info("Converted file " + nsou + " already exists.");
				return dest;
			}
			// java.lang.String cmdLine = "a2mp3 -v -d " + convDir + " " +
			// sou.fileName;
			// String cmdLine = "lame " + convDir + " " + sou.fileName;
			String cmdLine = "lame " + sou.fileName + " " + dest.fileName;
			log.info(cmdLine);
			Process pr = Runtime.getRuntime().exec(cmdLine);
			InputStream o = pr.getInputStream();
			InputStream e = pr.getErrorStream();
			ReadC re = new ReadC(e);
			re.start();
			pr.waitFor();
			re.stop();
			return dest;
		} catch (InterruptedException ex) {
			throw new Mp3Exception(ex);
		} catch (IOException ex) {
			throw new Mp3Exception(ex);
		}
	}
}
