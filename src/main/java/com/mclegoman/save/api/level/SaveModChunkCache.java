package com.mclegoman.save.api.level;

import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtList;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkNibbleStorage;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.chunk.WorldChunk;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class SaveModChunkCache implements ChunkSource {
	private final ChunkSource generator;
	private final WorldChunk[] chunks = new WorldChunk[1024];
	private final File file;
	private final World world;

	public SaveModChunkCache(World world, File file, ChunkSource chunkSource) {
		this.world = world;
		this.generator = chunkSource;
		this.file = file;
	}

	public final boolean hasChunk(int i, int j) {
		int index = i & 31 | (j & 31) << 5;
		if (this.chunks[index] != null) {
			WorldChunk chunk = this.chunks[index];
			return i == chunk.chunkX && j == chunk.chunkZ;
		}
		return false;
	}

	public final WorldChunk getChunk(int x, int y) {
		int var3 = x & 31 | (y & 31) << 5;
		if (!this.hasChunk(x, y)) {
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_1033437();
				this.saveChunk(this.chunks[var3]);
			}

			WorldChunk var4;
			if ((var4 = this.loadChunk(x, y)) == null) {
				var4 = this.generator.getChunk(x, y);
				this.saveChunk(var4);
			}

			this.chunks[var3] = var4;
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_6736297();
			}

			if (this.hasChunk(x + 1, y + 1) && this.hasChunk(x, y + 1) && this.hasChunk(x + 1, y)) {
				this.populateChunk(this, x, y);
			}

			if (this.hasChunk(x - 1, y + 1) && this.hasChunk(x, y + 1) && this.hasChunk(x - 1, y)) {
				this.populateChunk(this, x - 1, y);
			}

			if (this.hasChunk(x + 1, y - 1) && this.hasChunk(x, y - 1) && this.hasChunk(x + 1, y)) {
				this.populateChunk(this, x, y - 1);
			}

			if (this.hasChunk(x - 1, y - 1) && this.hasChunk(x, y - 1) && this.hasChunk(x - 1, y)) {
				this.populateChunk(this, x - 1, y - 1);
			}
		}

		return this.chunks[var3];
	}
	private static String toBase36(int i, boolean folder) {
		int value = getUnsignedValue((byte) i);
		return folder ? Integer.toString(value % 64, 36) : (i < 0 ? "-" : "") + Integer.toString(value, 36);
	}

	private static int getUnsignedValue(byte value) {
		return value & 0xFF;
	}

	private File getChunkFile(int x, int y) {
		int convertedX = SaveModWorld.convertChunkCoord(x, true);
		int convertedY = SaveModWorld.convertChunkCoord(y, true);
		File folder = new File(new File(this.file, toBase36(convertedX, true)), toBase36(convertedY, true));
		folder.mkdirs();
		return new File(folder, "c." + ((convertedX < 0) ? "-" : "") + toBase36((convertedX < 0) ? (convertedX * -1) : convertedX, false) + "."  + ((convertedY < 0) ? "-" : "") + toBase36((convertedY < 0) ? (convertedY * -1) : convertedY, false) + ".dat");
	}

	private WorldChunk loadChunk(int i, int j) {
		if (this.getChunkFile(i, j).exists()) {
			try {
				NbtCompound worldChunk = SaveModLevel.load(Files.newInputStream(this.getChunkFile(i, j).toPath()));
				return readChunk(this.world, worldChunk.getCompound("Level"));
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
			}
		}

		return null;
	}
	public static WorldChunk readChunk(World world, NbtCompound nbtCompound) {
		byte[] blocks = nbtCompound.getByteArray("Blocks");
		WorldChunk worldChunk = new WorldChunk(world, blocks, SaveModWorld.convertChunkCoord(nbtCompound.getInt("xPos"), false), SaveModWorld.convertChunkCoord(nbtCompound.getInt("zPos"), false));
		worldChunk.blockMetadata = new ChunkNibbleStorage(blocks.length);
		worldChunk.blockMetadata.data = nbtCompound.getByteArray("Data");
		worldChunk.skyLight = new ChunkNibbleStorage(blocks.length);
		worldChunk.skyLight.data = nbtCompound.getByteArray("SkyLight");
		worldChunk.blockLight = new ChunkNibbleStorage(blocks.length);
		worldChunk.blockLight.data = nbtCompound.getByteArray("BlockLight");
		worldChunk.heightMap = nbtCompound.getByteArray("HeightMap");
		if (worldChunk.blockMetadata.data == null) {
			worldChunk.blockMetadata = new ChunkNibbleStorage(worldChunk.blockIds.length);
		}
		if (worldChunk.heightMap == null || worldChunk.skyLight.data == null) {
			worldChunk.heightMap = new byte[256];
			worldChunk.skyLight = new ChunkNibbleStorage(worldChunk.blockIds.length);
			worldChunk.populateSkylight();
		}
		if (worldChunk.blockLight == null) {
			worldChunk.blockLight = new ChunkNibbleStorage(worldChunk.blockIds.length);
		}
		NbtList tileEntities = nbtCompound.getList("TileEntities");
		if (tileEntities != null) {
			for (int i = 0; i < tileEntities.size(); i++) {
				BlockEntity blockEntity = SaveModWorld.getBlockEntity((NbtCompound) tileEntities.get(i));
				if (blockEntity != null) worldChunk.setBlockEntityAt(blockEntity.x - (worldChunk.chunkX << 4), blockEntity.y, blockEntity.z - (worldChunk.chunkZ << 4), blockEntity);
			}
		}

		return worldChunk;
	}
	private void saveChunk(WorldChunk worldChunk) {
		File chunk = this.getChunkFile(worldChunk.chunkX, worldChunk.chunkZ);
		if (chunk.exists()) {
			((SaveModWorld)this.world).sizeOnDisk -= chunk.length();
		}
		try {
			FileOutputStream var3 = new FileOutputStream(chunk);
			NbtCompound var4 = new NbtCompound();
			NbtCompound var5 = new NbtCompound();
			var4.put("Level", var5);
			SaveModWorld.saveChunk(worldChunk, var5);
			SaveModLevel.save(var4, var3);
			((SaveModWorld)this.world).sizeOnDisk += chunk.length();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
	}

	public final void populateChunk(ChunkSource chunkSource, int i, int j) {
		this.generator.populateChunk(chunkSource, i, j);
	}

	public final void save(boolean bl) {
		int index = 0;
		for (WorldChunk chunk : this.chunks) {
			if (chunk != null && ((SaveModWorldChunk)chunk).save$getDirty()) {
				this.saveChunk(chunk);
				((SaveModWorldChunk)this.chunks[index]).save$setDirty(false);
				index++;
				if (index == 10 && !bl) return;
			}
		}
	}
}
