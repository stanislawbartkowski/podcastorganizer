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
package com.mp3organizer.run;

import com.mp3organizer.directory.ConvertMp3File;
import com.mp3organizer.directory.DestDirectory;
import com.mp3organizer.directory.ITestInterrup;
import com.mp3organizer.directory.SourceDirectory;
import com.mp3organizer.exception.Mp3Exception;
import com.mp3organizer.param.GetParam;
import java.util.logging.Logger;

/**
 *
 * @author podcast
 */
public final class RunBox {

    private RunBox() {
    }

    /**
     * Main task.
     * @param p Parameters
     * @param log Logger
     * @param iTest Test interrupt (if not null)
     * @throws Mp3Exception if any error ocured.
     */
    public static void run(final GetParam p, final Logger log, final ITestInterrup iTest) throws Mp3Exception {

        DestDirectory dest = new DestDirectory(log);
        dest.readDirectory(p.getDestDirectory());
        ConvertMp3File mp3Conv = new ConvertMp3File(p.getMp3ToChange(),
                p.getmp3ChangedDir(), log);
        SourceDirectory sou = new SourceDirectory(log, mp3Conv, iTest);
        sou.createList(p.getSourceDirectory(), dest);
        sou.copyFiles(p,p.getDestDirectory(), iTest);
    }
}
