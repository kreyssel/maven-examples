package org.kreyssel.mavenexamples.importscope.moduleb;


public class MyModuleBTest {

	public void testMyModuleADependentImpl() {
		new MyModuleADependentImpl().echo();
	}
		
	public void testMyModuleADependentOnOptionalImpl() {
		new MyModuleADependentOnOptionalImpl().echo();
	}
}
