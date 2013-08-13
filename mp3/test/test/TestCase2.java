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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import com.mp3organizer.exception.Mp3Exception;
import com.mp3organizer.param.GetParam;
import com.mp3organizer.run.RunBox;

// unfortunately, directory order does not reflect the creating order.
// test without sense

public class TestCase2 extends TestHelper {

	@Test
	public void test1() throws Mp3Exception, IOException {

		createSubDir(getSouDir("testsou1"), "2009-10-01");
		pause(1);
		createSubDir(getSouDir("testsou1"), "2009-10-03");
		pause(1);
		createSubDir(getSouDir("testsou1"), "2009-10-05");
		String dir = getSouDir("testsou1" + File.separator + "2009-10-05");
		removeDir(dir);
		createSubDir(getSouDir("testsou1"), "2009-10-07");
		dir = getSouDir("testsou1" + File.separator + "2009-10-07");
		removeDir(dir);

		dir = getSouDir("testsou1" + File.separator + "2009-10-01");
		removeDir(dir);
		for (int i = 10; i < 15; i++) {
			String filename = "file-" + i + ".mp3";
			File f = new File(dir, filename);
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write("Hello");
			writer.close();
			pause(1);
		}

		dir = getSouDir("testsou1" + File.separator + "2009-10-03");
		removeDir(dir);
		for (int i = 0; i < 5; i++) {
			String filename = "file-" + i + ".mp3";
			File f = new File(dir, filename);
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write("Hello");
			writer.close();
			pause(1);
		}

		String desttemp = getSouDir("testoutdir");
		removeDir(desttemp);

		GetParam pa = new GetParam();
		dir = getSouDir("testsou1");
		pa.setSourceDirectory(dir);
		pa.setDestDirectory(desttemp);
		// Step 3
		RunBox.run(pa, log, null);

		File file = new File(desttemp);

		// Reading directory contents
		File[] files = file.listFiles();
		assertEquals(10, files.length);

		// unfortunately, directory order does not reflect the creating order.
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}

	@Test
	public void test2() throws Mp3Exception, IOException {
		test1();
		createSubDir(getSouDir("testsou1"), "2009-10-05");
		String dir = getSouDir("testsou1" + File.separator + "2009-10-05");
		removeDir(dir);
		for (int i = 20; i < 25; i++) {
			String filename = "file-" + i + ".mp3";
			File f = new File(dir, filename);
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write("Hello");
			writer.close();
			pause(1);
		}
		GetParam pa = new GetParam();
		dir = getSouDir("testsou1");
		pa.setSourceDirectory(dir);
		String desttemp = getSouDir("testoutdir");
		pa.setDestDirectory(desttemp);
		// Step 3
		RunBox.run(pa, log, null);

		File file = new File(desttemp);

		// Reading directory contents
		File[] files = file.listFiles();
		assertEquals(15, files.length);

		// unfortunately, directory order does not reflect the creating order.
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}

	@Test
	public void test3() throws Mp3Exception, IOException {
		test2();
		String desttemp = getSouDir("testoutdir");
		File file = new File(desttemp);
		File[] files = file.listFiles();
		assertEquals(15, files.length);
		for (File f : files) {
			if (f.getAbsolutePath().contains("2009-10-01"))
				f.delete();
		}
		
		createSubDir(getSouDir("testsou1"), "2009-10-07");
		String dir = getSouDir("testsou1" + File.separator + "2009-10-07");
		removeDir(dir);
		for (int i = 30; i < 40; i++) {
			String filename = "file-" + i + ".mp3";
			File f = new File(dir, filename);
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write("Hello");
			writer.close();
			pause(1);
		}
		
		GetParam pa = new GetParam();
		dir = getSouDir("testsou1");
		pa.setSourceDirectory(dir);
		pa.setDestDirectory(desttemp);
		// Step 3
		RunBox.run(pa, log, null);

		file = new File(desttemp);

		// Reading directory contents
		files = file.listFiles();
		assertEquals(20, files.length);

		// unfortunately, directory order does not reflect the creating order.
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}


		
		
	}

}
