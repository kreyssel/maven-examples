package org.kreyssel.mavenexamples.importscope.moduleb;

import org.kreyssel.mavenexamples.importscope.modulea.MyImpl;

public class MyModuleADependentImpl {

	public void echo() {
		new MyImpl().echo();
	}
}
