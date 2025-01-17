package com.mclegoman.save.api.level;

import com.mclegoman.save.api.entity.SaveModEntity;
import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.world.World;

import java.io.*;
import java.nio.file.Files;

public class SaveModWorld extends World {
	private File dir;
	private NbtCompound f_4300305;
	private long seed = 0L;
	public long ticks = 0L;
	public long sizeOnDisk = 0L;
	public SaveModWorld(File file, String string) {
		file.mkdirs();
		this.dir = new File(file, string);
		this.dir.mkdirs();
		//TODO: Load the world.
		File levelFile = new File(this.dir, "level.dat");
		if (levelFile.exists()) {
			try {
				NbtCompound nbtCompound = SaveModLevel.save$load(Files.newInputStream(levelFile.toPath())).getCompound("Data");
				this.seed = nbtCompound.getLong("RandomSeed");
				this.spawnpointX = nbtCompound.getInt("SpawnX");
				this.spawnpointY = nbtCompound.getInt("SpawnY");
				this.spawnpointZ = nbtCompound.getInt("SpawnZ");
				this.ticks = nbtCompound.getLong("Time");
				this.sizeOnDisk = nbtCompound.getLong("SizeOnDisk");
				this.f_4300305 = nbtCompound.getCompound("Player");
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
			}
		}
		while (this.seed == 0L) {
			this.seed = this.random.nextLong();
			this.spawnpointX = 0;
			this.spawnpointY = 64;
			this.spawnpointZ = 0;
		}

		//this.chunkSource = new ChunkCache(this, this.dir, new OverworldChunkGenerator(this, this.seed));
		//this.save(false);
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
			delete(file.listFiles());
			file.delete();
		}
	}
	private static void delete(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) delete(file.listFiles());
			file.delete();
		}
	}
	public final void addPlayer() {
		if (this.f_4300305 != null) {
			((SaveModEntity)this.f_6053391).save$readEntityNbt(this.f_4300305);
			this.f_4300305 = null;
		}
	}
}
