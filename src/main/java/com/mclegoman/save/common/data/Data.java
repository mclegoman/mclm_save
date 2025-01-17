/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.common.data;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.rtu.versioning.Version;
import com.mclegoman.save.config.SaveConfig;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class Data {
	public static String mcVersion = "inf-20100325-2";
	private static Version version;
	public static Version getVersion() {
		if (version == null) QuiltLoader.getModContainer("save").ifPresent(container -> version = Version.parse(container.metadata()));
		if (version != null) return version;
		else throw new NullPointerException("save mod can't find it's own version. send help!");
	}
	public static void exit(int status) {
		version.sendToLog(LogType.INFO, "Halting with status code: " + status + "!");
		if (SaveConfig.instance.saveWorldOnExit.value()) {
			try {
				//new World().save(ClientData.minecraft.f_5854988, new File(QuiltLoader.getGameDir() + (QuiltLoader.getGameDir().endsWith("/") ? "" : "/") + "level.mclevel"));
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.WARN, "Failed to save world on exit: " + error);
			}
		}
		Thread.currentThread().interrupt();
		if (Mouse.isCreated()) Mouse.destroy();
		if (Keyboard.isCreated()) Keyboard.destroy();
		if (AL.isCreated()) AL.destroy();
		Runtime.getRuntime().halt(status);
	}
}
