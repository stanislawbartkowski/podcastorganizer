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
package com.mp3organizer.param;

/**
 *
 * @author sb
 */
public class GetParam {

	private String destDirectory;
	private String sourceDirectory;
	
	public final void setDestDirectory(final String destDirectory) {
		this.destDirectory = destDirectory;
	}

	public final void setSourceDirectory(final String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}
	
    public GetParam() {
    	destDirectory = "/media/disk";
    	sourceDirectory = "/home/podcast/podcast";
    }

    public final String getDestDirectory() {
//        return "/media/SMFS1.0";
//        return "/media/disk";
    	return destDirectory;
    }

    public final String getSourceDirectory() {
//        return "/home/podcast/podcast";
    	return sourceDirectory;
    }

    public final String getMp3ToChange() {
//        return "ta.*|doc.*|foo.*|ker.*|\\d\\d\\d.*";
        return "xxxx";
    }

    public final String getmp3ChangedDir() {
        return "oooo";
    }
}
