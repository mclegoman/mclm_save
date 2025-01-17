package com.mclegoman.save.api.level;

import com.mclegoman.save.api.entity.SaveModBlockEntity;
import com.mclegoman.save.api.entity.SaveModEntity;
import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.api.nbt.NbtList;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.common.util.SaveHelper;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.io.*;
import java.nio.file.Files;

public class SaveModWorld extends World {
	private File dir;
	private NbtCompound f_4300305;
	private long seed = 0L;
	public long sizeOnDisk = 0L;
	private boolean isNewWorld;
	public SaveModWorld(File file, String string) {
		file.mkdirs();
		this.dir = new File(file, string);
		this.dir.mkdirs();
		File levelFile = new File(this.dir, "level.dat");
		this.isNewWorld = !levelFile.exists() && SaveConfig.instance.starterItems.value();
		if (levelFile.exists()) {
			try {
				NbtCompound nbtCompound = SaveModLevel.load(Files.newInputStream(levelFile.toPath())).getCompound("Data");
				this.seed = nbtCompound.getLong("RandomSeed");
				this.spawnpointX = nbtCompound.getInt("SpawnX");
				this.spawnpointY = nbtCompound.getInt("SpawnY");
				this.spawnpointZ = nbtCompound.getInt("SpawnZ");
				this.ticks = (int)nbtCompound.getLong("Time");
				this.sizeOnDisk = nbtCompound.getLong("SizeOnDisk");
				if (nbtCompound.containsKey("Player")) this.f_4300305 = nbtCompound.getCompound("Player");
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

		this.chunkSource = new SaveModChunkCache(this, this.dir, new SaveModOverworldChunkGenerator(this, this.seed));
		this.save(false);
	}
	public static NbtCompound get(File file, String string) {
		file = new File(file, "saves");
		if ((file = new File(file, string)).exists()) {
			if ((file = new File(file, "level.dat")).exists()) {
				try {
					return SaveModLevel.load(Files.newInputStream(file.toPath())).getCompound("Data");
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
		if (this.isNewWorld) {
			// When generating a world using vanilla, these items would be added to your hotbar. I couldn't find the function that added them, so i made it myself.
			Integer[] itemIds = new Integer[]{
					Item.DIAMOND_AXE.id,
					Item.DIAMOND_SHOVEL.id,
					Item.DIAMOND_PICKAXE.id,
					Block.TORCH.id,
					Item.FLINT_AND_STEEL.id,
					Block.TNT.id,
					Block.GLASS.id,
					Item.BOW.id,
					Item.ARROW.id
			};
			int index = 0;
			for (Integer id : itemIds) {
				((PlayerEntity) this.f_6053391).inventory.inventorySlots[index] = new ItemStack(id, 64);
				index++;
			}
			this.isNewWorld = false;
		}
	}
	public final void removePlayer() {
		this.entities.remove(this.f_6053391);
	}
	private void save(boolean bl) {
		File level = new File(this.dir, "level.dat");
		NbtCompound data = new NbtCompound();
		data.putLong("RandomSeed", this.seed);
		data.putInt("SpawnX", (int) this.spawnpointX);
		data.putInt("SpawnY", (int) this.spawnpointY);
		data.putInt("SpawnZ", (int) this.spawnpointZ);
		data.putLong("Time", this.ticks);
		data.putLong("SizeOnDisk", this.sizeOnDisk);
		data.putLong("LastPlayed", System.currentTimeMillis());
		NbtCompound player;
		if (this.f_6053391 != null) {
			player = new NbtCompound();
			((SaveModEntity)this.f_6053391).save$writeEntityNbt(player);
			data.putCompound("Player", player);
		}
		NbtCompound output = new NbtCompound();
		output.put("Data", data);
		try {
			SaveModLevel.save(output, Files.newOutputStream(level.toPath()));
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
		((SaveModChunkCache)this.chunkSource).save(bl);
	}
	public static ItemStack readItem(NbtCompound nbtCompound) {
		ItemStack stack = new ItemStack(nbtCompound.getShort("id"), nbtCompound.getByte("Count"));
		stack.metadata = nbtCompound.getShort("Damage");
		return stack;
	}
	public static void saveItem(ItemStack item, NbtCompound nbtCompound) {
		nbtCompound.putShort("id", (short)item.itemId);
		nbtCompound.putByte("Count", (byte)item.size);
		nbtCompound.putShort("Damage", (short)item.metadata);
	}
	public static void saveChunk(WorldChunk worldChunk, NbtCompound nbtCompound) {
		nbtCompound.putInt("xPos", worldChunk.chunkX);
		nbtCompound.putInt("zPos", worldChunk.chunkZ);
		nbtCompound.putLong("LastUpdate", worldChunk.world.ticks);
		nbtCompound.putByteArray("Blocks", worldChunk.blockIds);
		nbtCompound.putByteArray("Data", worldChunk.blockMetadata.data);
		nbtCompound.putByteArray("SkyLight", worldChunk.skyLight.data);
		nbtCompound.putByteArray("BlockLight", worldChunk.blockLight.data);
		nbtCompound.putByteArray("HeightMap", worldChunk.heightMap);
		NbtList tileEntities = new NbtList();

		for (Object blockEntityObject : worldChunk.blockEntities.values()) {
			if (blockEntityObject instanceof BlockEntity) {
				NbtCompound data = new NbtCompound();
				((SaveModBlockEntity)blockEntityObject).save$writeNbt(data);
				tileEntities.add(data);
			}
		}

		nbtCompound.put("TileEntities", tileEntities);
	}
	public static BlockEntity getBlockEntity(NbtCompound nbtCompound) {
		BlockEntity blockEntity = null;
		try {
			Class<?> classObj = SaveHelper.getBlockEntityTypeFromId(nbtCompound.getString("id"));
			if (classObj != null) blockEntity = (BlockEntity)classObj.newInstance();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
		}
		if (blockEntity != null) ((SaveModBlockEntity)blockEntity).save$readNbt(nbtCompound);
		else Data.getVersion().sendToLog(LogType.WARN, "Skipping TileEntity with id " + nbtCompound.getString("id"));
		return blockEntity;
	}
	public void waitIfSaving() {
		this.save(true);
	}
}
