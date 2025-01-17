package com.mclegoman.save.common.util;

import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;

public class SaveHelper {
	public static File getMinecraftDir() {
		return QuiltLoader.getGameDir().toFile();
	}
	public static File getSavesDir() {
		return new File(QuiltLoader.getGameDir().toFile(),"saves");
	}
}
