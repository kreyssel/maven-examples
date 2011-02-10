package org.kreyssel.mavenexamples.importscope.modulea;

import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;

public class MyOptionalImpl {

	public void echo(){
		try {
			System.out.println("MyOptionalImpl "+ FileSystemUtils.freeSpaceKb());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
