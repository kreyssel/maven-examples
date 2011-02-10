package org.kreyssel.mavenexamples.importscope.moduleb;

import org.kreyssel.mavenexamples.importscope.modulea.MyOptionalImpl;

public class MyModuleADependentOnOptionalImpl {

	public void echo() {
		new MyOptionalImpl().echo();
	}
}
