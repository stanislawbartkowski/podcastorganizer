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
import com.mp3organizer.param.GetParam;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author sb
 */
public class SourceDirectory {

	private Directory dirlist;
	private final Logger log;
	private final ConvertMp3File mp3Conv;
	private final ITestInterrup iTest;

	/**
	 * Constructor
	 * 
	 * @param log
	 *            Logger
	 * @param p
	 *            Converter (not used now)
	 * @param iTest
	 *            Testing interrupt (null if not active)
	 */
	public SourceDirectory(final Logger log, final ConvertMp3File p,
			final ITestInterrup iTest) {
		dirlist = new Directory(log);
		this.log = log;
		this.mp3Conv = p;
		this.iTest = iTest;
	}

	/**
	 * Get base name
	 * 
	 * @param pName
	 *            File path name
	 * @return Base file name
	 */
	private String getLName(final String pName) {
		String[] v = pName.split("/");
		return v[v.length - 1];
	}

	/**
	 * Read and create source and dest list.
	 * 
	 * @param sourcedir
	 *            Source directory
	 * @param destdir
	 *            Destination directory
	 * @throws Mp3Exception
	 *             if any error occured
	 */
	public final void createList(final String sourcedir,
			final DestDirectory destdir) throws Mp3Exception {

		Directory dirDir = new Directory(log);
		dirDir.readDirectory(sourcedir, true, true);
		boolean firstFound = true;
		for (FileDescr fd : dirDir.getFileList()) {
			String pathName = fd.fileName;
			String dName = getLName(pathName);
			Directory souDir = new Directory(log);
			souDir.readDirectory(pathName, false, true);
			if (souDir.getSize() == 0) {
				log.info(pathName + " empty, skip this directory");
				continue;
			}
			for (FileDescr fmp3 : souDir.getFileList()) {
				String fileName = fmp3.baseFileName;
				fmp3.destbaseFileName = dName + "-" + fileName;
				if (destdir.isFile(fmp3.destbaseFileName)) {
					log.info(fmp3.destbaseFileName
							+ " already at destination, skip this file");
					if (firstFound) {
						dirlist.getFileList().clear();
						log.info(pathName + " do not copy all files before");
					}
					firstFound = false;
					continue;
				} else {
					log.info(fileName + " add for copying");
					FileDescr nmp3 = mp3Conv.Mp3ChangeSouFileName(fmp3);
					dirlist.addFile(nmp3);
				}
			}
		}
	}

	/**
	 * Main task: Copy files from sou to dest.
	 * 
	 * @param destdir
	 *            Source directory
	 * @param destPath
	 *            Dest directory
	 * @param iTest
	 *            Interrupter (null if not disabled).
	 * @throws Mp3Exception
	 *             If any error occured
	 */
	public final void copyFiles(final GetParam p, final String destPath,
			final ITestInterrup iTest) throws Mp3Exception {
		log.info("Start copying." + dirlist.noFiles() + " files, "
				+ dirlist.getSize() / 1000000 + " MB");

		for (FileDescr fd : dirlist.getFileList()) {
			String sou = fd.fileName;
			String dest = fd.destbaseFileName;
			boolean copyFile = true;
			while (copyFile) {
				try {
					log.info("Copying from " + sou + " ==> dir:" + destPath
							+ " file:" + dest);
					CopyFile.copyFile(sou, destPath, dest, iTest);
					copyFile = false;
				} catch (IOException ex) {
					log.info("Exception while copying, try remove file from dest");
					DestDirectory destdir = new DestDirectory(log);
					destdir.readDirectory(p.getDestDirectory());
					FileDescr remFile = destdir.getFileAndRemove();
					if (remFile == null) {
						String mess = "No more files to delete ";
						log.log(Level.SEVERE, mess);
						throw new Mp3Exception(mess);
					}
					log.info(remFile.fileName + " remove file");
					CopyFile.removeFile(remFile.getFileName());
				}
			}
		}

	}
}
