/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.config;

import com.google.gson.internal.JavaVersion;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public enum Theme {
	light("IntelliJ (Light)"),
	dark("Darcula (Dark)"),
	metal("Metal (Java)"),
	system(JavaVersion.getMajorJavaVersion() >= 11 ? "Auto" : "System"); // Java 21 prevents win11's system theme from working, jSystemThemeDetector requires java 11 or higher.
	final String name;
	Theme(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
