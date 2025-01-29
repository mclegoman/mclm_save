/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.data;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.rtu.versioning.Version;
import net.minecraft.client.C_5664496;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class Data {
	public static String getMcVersion() {
		return "in-20100110";
	}
	private static Version version;
	public static Version getVersion() {
		if (version == null) QuiltLoader.getModContainer("save").ifPresent(container -> version = Version.parse(container.metadata()));
		if (version != null) return version;
		else throw new NullPointerException("save mod can't find it's own version. send help!");
	}
	public static void exit(int status) {
		getVersion().sendToLog(LogType.INFO, "Halting with status code: " + status + "!");
		Thread.currentThread().interrupt();
		if (Mouse.isCreated()) Mouse.destroy();
		if (Keyboard.isCreated()) Keyboard.destroy();
		if (AL.isCreated()) AL.destroy();
		Runtime.getRuntime().halt(status);
	}
	public static class Resources {
		public static C_5664496 minecraft;
	}
}
