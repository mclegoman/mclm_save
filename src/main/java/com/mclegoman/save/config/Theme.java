/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.config;

public enum Theme {
	light("IntelliJ (Light)"),
	dark("Darcula (Dark)"),
	metal("Metal (Java)"),
	system("System");
	final String name;
	Theme(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
