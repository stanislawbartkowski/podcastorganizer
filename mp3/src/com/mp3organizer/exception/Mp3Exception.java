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
package com.mp3organizer.exception;

/**
 *
 * @author sb
 */
public class Mp3Exception extends Exception {

    public Mp3Exception() {
    }

    public Mp3Exception(final String mess) {
        super(mess);
    }

    public Mp3Exception(final Throwable e) {
        super(e);
    }
}
