package com.mclegoman.save.api.minecraft;

import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.world.World;

import java.io.*;
import java.nio.file.Files;

public class SaveModWorld extends World {
	private File dir;
	public SaveModWorld(File file, String string) {
		file.mkdirs();
		this.dir = new File(file, string);
		this.dir.mkdirs();
	}
	public static NbtCompound get(File file, String string) {
		file = new File(file, "saves");
		if ((file = new File(file, string)).exists()) {
			if ((file = new File(file, "level.dat")).exists()) {
				try {
					return SaveModLevel.save$load(Files.newInputStream(file.toPath())).getCompound("Data");
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
				}
			}
		}
		return null;
	}
	public static void delete(File file, String string) {
		if ((file = new File(file, string)).exists()) {
			m_4805602(file.listFiles());
			file.delete();
		}
	}
	private static void m_4805602(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) m_4805602(file.listFiles());
			file.delete();
		}
	}
}
