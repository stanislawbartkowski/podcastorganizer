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
package com.mp3organizer;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.mp3organizer.exception.Mp3Exception;
import com.mp3organizer.param.GetParam;
import com.mp3organizer.run.RunBox;

/**
 *
 * @author sb
 */
public final class Main {
	
	private Main() {
		
	}

	/**
	 * Print line.
	 * @param s the string to print
	 */
    private static void pL(final String s) {
        System.out.println(s);
    }

    /**
     * Prints help to standard output.
     */
    private static void drawHelp() {
    	pL("Version: 2014/03/09");
        pL("Parameters:");
        pL(" /soudir/ /destdir/");
        pL("Example:");
        pL("/home/podcast/mp3 /media/disk");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        if (args.length != 2) {
            drawHelp();
            return;
        }
        Logger log = Logger.getLogger("mp3.reader");
        GetParam p = new GetParam();
        p.setSourceDirectory(args[0]);
        p.setDestDirectory(args[1]);
        try {
            RunBox.run(p, log, null);
        } catch (Mp3Exception ex) {
            log.log(Level.SEVERE, null, ex);
        }

    }
}
