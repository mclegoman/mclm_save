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

	public final WorldChunk getChunk(int i, int j) {
		int var3 = i & 31 | (j & 31) << 5;
		if (!this.hasChunk(i, j)) {
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_1033437();
				this.saveChunk(this.chunks[var3]);
			}

			WorldChunk var4;
			if ((var4 = this.loadChunk(i, j)) == null) {
				var4 = this.generator.getChunk(i, j);
				this.saveChunk(var4);
			}

			this.chunks[var3] = var4;
			if (this.chunks[var3] != null) {
				this.chunks[var3].m_6736297();
			}

			if (this.hasChunk(i + 1, j + 1) && this.hasChunk(i, j + 1) && this.hasChunk(i + 1, j)) {
				this.populateChunk(this, i, j);
			}

			if (this.hasChunk(i - 1, j + 1) && this.hasChunk(i, j + 1) && this.hasChunk(i - 1, j)) {
				this.populateChunk(this, i - 1, j);
			}

			if (this.hasChunk(i + 1, j - 1) && this.hasChunk(i, j - 1) && this.hasChunk(i + 1, j)) {
				this.populateChunk(this, i, j - 1);
			}

			if (this.hasChunk(i - 1, j - 1) && this.hasChunk(i, j - 1) && this.hasChunk(i - 1, j)) {
				this.populateChunk(this, i - 1, j - 1);
			}
		}

		return this.chunks[var3];
	}

	private File getChunkFile(int i, int j) {
		// What is wrong with this?
		// Why is i/j set to really high values?
		// TODO: Fix this.
		String chunk = "c." + Integer.toString(i, 36) + "." + Integer.toString(j, 36) + ".dat";
		String x = Integer.toString(i & 63, 36);
		String y = Integer.toString(j & 63, 36);
		File file = new File(new File(this.file, x), y);
		file.mkdirs();
		return new File(file, chunk);
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
		WorldChunk worldChunk = new WorldChunk(world, blocks, nbtCompound.getInt("xPos"), nbtCompound.getInt("zPos"));
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
			// TODO: Add save$dirty boolean to chunk and make chunk dirty when edited.
			if (chunk != null) {// && chunk.dirty) {
				this.saveChunk(chunk);
				//this.chunks[var3].dirty = false;
				index++;
				if (index == 10 && !bl) return;
			}
		}
	}
}
