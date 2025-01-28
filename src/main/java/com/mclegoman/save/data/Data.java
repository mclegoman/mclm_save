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
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Optional;

@ClientOnly
public class Data {
	public static String getMcVersion() {
		String mcVersion = "UNKNOWN";
		Optional<ModContainer> mc = QuiltLoader.getModContainer("minecraft");
		if (mc.isPresent()) {
			try {
				String version = mc.get().metadata().version().raw();
				version = version.substring(version.indexOf("inf-"));
				version = version.substring(4);
				version = version.substring(0, version.indexOf("-"));
				mcVersion = "inf-" + version;
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.WARN, "Could not obtain minecraft version: " + error);
			}
		}
		return mcVersion;
	}
	private static Version version;
	public static Version getVersion() {
		if (version == null) QuiltLoader.getModContainer("save").ifPresent(container -> version = Version.parse(container.metadata()));
		if (version != null) return version;
		else throw new NullPointerException("save mod can't find it's own version. send help!");
	}
	public static void exit(int status) {
		version.sendToLog(LogType.INFO, "Halting with status code: " + status + "!");
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
