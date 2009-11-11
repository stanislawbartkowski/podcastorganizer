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

/**
 *
 * @author sb
 */
final class Mp3Util {

    private Mp3Util() {
    }
    
    /**
     * Breaks file name into parts
     * @param sou Full file name
     * @return array of strings
     */
    static String[] decodePath(final String sou) {
        String[] p = sou.split("['//']");
        return p;        
    }
    
    /**
     * Add path to file name.
     * @param p1 Directory name.
     * @param p2 File name
     * @return Directory/file name
     */
    static String addPath(final String p1,final String p2) {
        return p1 + File.separatorChar + p2;
    }

}
