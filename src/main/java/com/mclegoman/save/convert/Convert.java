/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.mclegoman.save.api.exception.ConvertFailException;
import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.classicexplorer.fields.*;
import com.mclegoman.save.classicexplorer.io.Reader;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.level.SaveModLevel;
import com.mclegoman.save.level.SaveModMinecraft;
import com.mclegoman.save.nbt.*;
import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.util.SaveHelper;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

public class Convert {
	public static void start(C_5664496 minecraft, Screen parent, int slot) {
		Data.Resources.minecraft.m_6408915(new ConvertWorldInfoScreen(parent, "Select world file to convert."));
		ConvertDialog convertDialog = new ConvertDialog(minecraft, parent, slot);
		convertDialog.start();
	}
	protected static void process(C_5664496 minecraft, Screen parent, int slot, File input) {
		Data.getVersion().sendToLog(LogType.INFO, "Converting '" + input.getName() + "' to Alpha save format!");
		try {
			String worldName = "World" + slot;
			Version version = (input.getName().endsWith(".mine") || input.getName().endsWith(".dat")) ? Version.classic : (input.getName().endsWith(".mclevel") ? Version.indev : null);
			if (version != null) convert(minecraft, version, parent, worldName, input);
			else select(minecraft, parent, worldName, input);
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static void select(C_5664496 minecraft, Screen parent, String worldName, File input) {
		// This function is run when we can't work out what level format we're converting.
		minecraft.m_6408915(new ConfirmScreen(new ConvertWorldInfoScreen(parent, "Converting world...", worldName, input), "Converting world...", "What level format are you converting from?", 0, "Classic", "Indev"));
	}
	private static void convert(C_5664496 minecraft, Version version, Screen parent, String worldName, File input) {
		// This function starts the conversion process by asking the user whether they want player data to be converted.
		minecraft.m_6408915(new ConfirmScreen(new ConvertWorldInfoScreen(version, parent, "Converting " + version.getName() + " world...", worldName, input), "Do you want to keep your player data?", "This includes your inventory, and location!", 1));
	}
	protected static void result(C_5664496 minecraft, Version version, Screen parent, String worldName, File input, int id, boolean value) {
		// 0: Version Type
		if (id == 0) convert(minecraft, version, parent, worldName, input);
		// 1: Convert Player Data
		if (id == 1) {
			setOverlay("Converting level", "Converting from " + version.getName() + " to alpha format!");
			if (version == Version.classic) convertClassic(minecraft, parent, worldName, value, input);
			else if (version == Version.indev) convertIndev(minecraft, parent, worldName, value, input);
				// This won't be executed unless the Version enum has been modified.
			else error(minecraft, parent, "Invalid version type!");
		}
	}
	private static void convertClassic(C_5664496 minecraft, Screen parent, String worldName, boolean convertPlayerData, File input) {
		try {
			setOverlay("Converting level", "Reading data...");
			long seed = new Random().nextLong();
			int spawnX = SaveConfig.instance.conversionSettings.spawnX.value();
			int spawnY = SaveConfig.instance.conversionSettings.spawnY.value();
			int spawnZ = SaveConfig.instance.conversionSettings.spawnZ.value();
			int time = SaveConfig.instance.conversionSettings.time.value();
			byte[] blocks = null;
			ClassField blockMap = null;
			short height = SaveConfig.instance.conversionSettings.height.value().shortValue();
			short length = SaveConfig.instance.conversionSettings.length.value().shortValue();
			short width = SaveConfig.instance.conversionSettings.width.value().shortValue();
			final NbtCompound[] playerData = new NbtCompound[]{null};
			for (Field field : Reader.read(input).getFields()) {
				if (field.getFieldName().equals("createTime")) {
					seed = (long) field.getField();
				} else if (field.getFieldName().equals("xSpawn")) {
					spawnX = (int) field.getField();
				} else if (field.getFieldName().equals("ySpawn")) {
					spawnY = (int) field.getField();
				} else if (field.getFieldName().equals("zSpawn")) {
					spawnZ = (int) field.getField();
				} else if (field.getFieldName().equals("tickCount")) {
					time = (int) field.getField();
				} else if (field.getFieldName().equals("blocks")) {
					blocks = ((BlocksField)field).getBlocks();
				} else if (field.getFieldName().equals("blockMap")) {
					blockMap = ((ClassField) field);
				} else if (field.getFieldName().equals("width")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					width = Short.parseShort(String.valueOf(field.getField()));
				} else if (field.getFieldName().equals("height")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					length = Short.parseShort(String.valueOf(field.getField())); // Was changed from "height" to "length" in Indev.
				} else if (field.getFieldName().equals("depth")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					height = Short.parseShort(String.valueOf(field.getField())); // Was changed from "depth" to "height" in Indev.
				}
			}
			if (blocks == null) error(minecraft, parent, "No blocks found!");
			else {
				if (blocks.length == (width * height * length)) {
					if (convertPlayerData) {
						if (blockMap != null) {
							blockMap.getClassField().getFields().forEach((field) -> {
								if (field.getFieldName().equals("all")) {
									((ClassField)field).getArrayList().forEach(entityData -> {
										if (entityData.getName().equals("com.mojang.minecraft.player.Player")) {
											NbtCompound data = new NbtCompound();
											data.putString("id", "LocalPlayer");
											entityData.getFields().forEach(player -> {
												if (player.getFieldName().equals("inventory")) {
													NbtList inventory = new NbtList();
													ArrayList<Field> inventory1 = ((ClassField)player).getClassField().getFields();
													inventory1.forEach(invField -> {
														if (invField.getFieldName().equals("count")) {
															for (Field field2 : ((ArrayField)invField).getArray()) {
																NbtCompound itemData = new NbtCompound();
																byte count = ((Integer)field2.getField()).byteValue();
																itemData.putByte("Count", count);
																if (count != 0) inventory.add(itemData);
															}
														} else if (invField.getFieldName().equals("slots")) {
															int index = 0;
															int slot = 0;
															for (Field field2 : ((ArrayField)invField).getArray()) {
																short id = ((Integer)field2.getField()).shortValue();
																if (id >= 0) {
																	NbtCompound itemData = (NbtCompound) inventory.get(index);
																	itemData.putShort("id", id);
																	itemData.putByte("Slot", ((Integer)slot).byteValue());
																	index++;
																}
																slot++;
															}
														}
													});
													data.put("Inventory", inventory);
												}
												if (player.getFieldName().equals("score")) {
													data.putInt("Score", (int) player.getField());
												}
											});
											NbtList motion = SaveHelper.toNbtList(0.0D, 0.0D, 0.0D);
											NbtList pos = new NbtList();
											NbtList rotation = SaveHelper.toNbtList(0.0F, 0.0F);
											entityData.getSuperClass().getSuperClass().getFields().forEach(entity -> {
												if (entity.getFieldName().equals("x") || entity.getFieldName().equals("y") || entity.getFieldName().equals("z")) {
													pos.add(new NbtDouble((float) entity.getField()));
												}
												if (entity.getFieldName().equals("fallDistance")) {
													data.putFloat("FallDistance", (float) entity.getField());
												}
											});
											data.put("Motion", motion);
											data.put("Pos", pos);
											data.put("Rotation", rotation);
											data.putShort("Air", (short) 300);
											data.putShort("AttackTime", (short) 0);
											data.putShort("DeathTime", (short) 0);
											data.putShort("HurtTime", (short) 0);
											data.putShort("Health", (short) 20);
											data.putShort("Fire", (short) -20);
											playerData[0] = data;
										}
									});
								}
							});
						}
					}
				} else throw new ConvertFailException("Invalid block amount!");
				File file = new File(SaveHelper.getSavesDir(), worldName);
				convertBlocks(file, width, height, length, blocks, null, time);
				createLevel(minecraft, parent, file, seed, spawnX, spawnY, spawnZ, time, calculateSizeOnDisk(file, width, length), playerData[0]);
				done(minecraft, parent, worldName);
			}
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static void convertIndev(C_5664496 minecraft, Screen parent, String worldName, boolean convertPlayerData, File input) {
		try {
			NbtCompound nbtCompound = SaveModLevel.load(Files.newInputStream(input.toPath()));
			long seed = nbtCompound.getCompound("About").getLong("CreatedOn");
			NbtCompound map = nbtCompound.getCompound("Map");
			short spawnX = ((NbtShort) map.getList("Spawn").get(0)).value;
			short spawnY = ((NbtShort) map.getList("Spawn").get(1)).value;
			short spawnZ = ((NbtShort) map.getList("Spawn").get(2)).value;
			long time = nbtCompound.getCompound("Map").getShort("TimeOfDay");
			NbtCompound player = null;
			if (convertPlayerData) {
				for (int i = 0; i < nbtCompound.getList("Entities").size(); i++) {
					NbtCompound entity = (NbtCompound) nbtCompound.getList("Entities").get(i);
					if (entity.containsKey("id") && entity.getString("id").equals("LocalPlayer")) {
						player = entity;
						break;
					}
				}
				// The only difference between indev player data and infdev player data is
				// that infdev uses double instead of float for motion and pos.
				if (player != null) {
					NbtList motion = player.getList("Motion");
					NbtList newMotion = new NbtList();
					for (int i = 0; i < motion.size(); i++) newMotion.add(new NbtDouble(((NbtFloat)motion.get(i)).value));
					player.put("Motion", newMotion);
					NbtList pos = player.getList("Pos");
					NbtList newPos = new NbtList();
					for (int i = 0; i < pos.size(); i++) newPos.add(new NbtDouble(((NbtFloat)pos.get(i)).value));
					player.put("Pos", newPos);
				}
			}
			short width = map.getShort("Width");
			short height = map.getShort("Height");
			short length = map.getShort("Length");

			File file = new File(SaveHelper.getSavesDir(), worldName);
			convertBlocks(file, width, height, length, map.getByteArray("Blocks"), map.containsKey("Data") ? map.getByteArray("Data") : null, time);
			// If we were to convert entities, they would be converted here.
			// Note: we don't convert currently, as the next version of infdev, doesn't save/load entities.
			convertTileEntities(file, nbtCompound.getList("TileEntities"));
			createLevel(minecraft, parent, file, seed, spawnX, spawnY, spawnZ, time, calculateSizeOnDisk(file, width, length), player);
			done(minecraft, parent, worldName);
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static void convertTileEntities(File dir, NbtList tileEntities) throws IOException {
		for (int i = 0; i < tileEntities.size(); i++) {
			setOverlay("Converting level", "Converting tile entities... (" + i + "/" + tileEntities.size() + ")");
			NbtElement tileEntity = tileEntities.get(i);
			if (tileEntity.getType() == 10) {
				NbtCompound tile = (NbtCompound)tileEntity;
				if (tile.containsKey("Pos")) {
					int pos = tile.getInt("Pos");
					// https://minecraft.wiki/w/Java_Edition_Indev_level_format
					int x = pos % 1024;
					int y = (pos >> 10) % 1024;
					int z = (pos >> 20) % 1024;
					tile.remove("Pos");
					tile.putInt("x", x);
					tile.putInt("y", y);
					tile.putInt("z", z);
					int chunkX = x / 16;
					int chunkZ = z / 16;
					if (x >= chunkX * 16 && x < (chunkX + 1) * 16 && z >= chunkZ * 16 && z < (chunkZ + 1) * 16) {
						if (tile.getString("id").equals("Chest")) {
							File file = SaveHelper.getChunkFile(dir, chunkX, chunkZ);
							NbtCompound chunk = SaveModLevel.load(Files.newInputStream(file.toPath())).getCompound("Level");
							NbtList chunkTileEntities = chunk.getList("TileEntities");
							chunkTileEntities.add(tile);
							NbtCompound level = new NbtCompound();
							level.put("Level", chunk);
							SaveModLevel.save(level, Files.newOutputStream(file.toPath()));
						}
					}
				}
			}
		}
	}
	private static long calculateSizeOnDisk(File dir, short width, short length) {
		long sizeOnDisk = 0L;
		int total = ((width / 16) * (length / 16));
		for (int chunk = 0; chunk < total; chunk++) sizeOnDisk += SaveHelper.getChunkFile(dir, chunk % (width / 16), chunk / (width / 16)).length();
		return sizeOnDisk;
	}
	private static void convertBlocks(File dir, short width, short height, short length, byte[] blocks, byte[] blocksData, long ticks) throws ConvertFailException, IOException {
		// inf-20100227 changed the world height from 256, to 127.
		// https://minecraft.wiki/w/Java_Edition_Infdev_20100227-1414
		if (width % 16 != 0) throw new ConvertFailException("Width was " + width + ", expecting value divisible by 16!");
		if (height <= 0 || height > 128) throw new ConvertFailException("Height was " + height + ", expecting value between 1 and 127!");
		if (length % 16 != 0) throw new ConvertFailException("Length was " + length + ", expecting value divisible by 16!");
		if (blocks.length == width * height * length) {
			int total = ((width / 16) * (length / 16));
			for (int chunk = 0; chunk < total; chunk++) {
				setOverlay("Converting level", "Writing chunk data... (" + chunk + "/" + total + ")");
				int x = chunk % (width / 16);
				int z = chunk / (width / 16);
				File chunkFile = SaveHelper.getChunkFile(dir, x, z);
				NbtCompound chunkData = new NbtCompound();
				NbtCompound level = new NbtCompound();
				level.putInt("xPos", x);
				level.putInt("zPos", z);
				level.putLong("LastUpdate", ticks);
				level.putByteArray("Blocks", getBlocksForChunk(x, z, width, height, length, blocks));
				level.putByteArray("Data", getBlockDataForChunk(x, z, width, height, length, blocksData, false));
				level.putByteArray("SkyLight", new byte[16 * 16 * 128]);
				level.putByteArray("BlockLight", getBlockDataForChunk(x, z, width, height, length, blocksData, true));
				level.putByteArray("HeightMap", calcHeightMap());
				level.put("TileEntities", new NbtList());
				chunkData.put("Level", level);
				SaveModLevel.save(chunkData, Files.newOutputStream(chunkFile.toPath()));
			}
		} else throw new ConvertFailException("Invalid block amount!");
	}
	private static byte[] calcHeightMap() {
		// It's not perfect (e.g transparent blocks probably wouldn't be counted), but the game should fix this when saving anyway.
		byte[] heightMap = new byte[16 * 16];
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					int currentHeight = 127 - y;
					heightMap[x * 16 + z] = (byte) (currentHeight + 1);
				}
			}
		}
		return heightMap;
	}
	private static byte[] getBlocksForChunk(int x, int z, int width, short height, int length, byte[] blocks) {
		byte[] chunk = new byte[16 * 16 * 128];
		int index = 0;
		for (int xChunk = 0; xChunk < 16; xChunk++) {
			for (int zChunk = 0; zChunk < 16; zChunk++) {
				for (int y = 0; y < height; y++) {
					chunk[index] = blocks[(((y * length + (z * 16 + zChunk)) * width) + (x * 16 + xChunk))];
					index++;
				}
				index += (128 - height);
			}
		}
		return chunk;
	}
	private static byte[] getBlockDataForChunk(int x, int z, int width, short height, int length, byte[] blockData, boolean isLight) {
		byte[] output = new byte[16 * 16 * 64];
		int i = 0;
		for (int xIndex = x * 16; xIndex < x * 16 + 16; xIndex++) {
			for (int zIndex = z * 16; zIndex < z * 16 + 16; zIndex++) {
				for (int yIndex = 0; yIndex < height; yIndex += 2) {
					byte a = blockData[(yIndex * length + zIndex) * width + xIndex];
					byte b = blockData[((yIndex + 1) * length + zIndex) * width + xIndex];
					if (isLight) {
						byte lightByte = (byte) ((a & 15) * 16 + b & 15);
						if (lightByte > 127) lightByte -= 256;
						output[i] = lightByte;
					} else {
						byte dataByte = (byte) ((a >> 4) * 16 + b >> 4);
						if (dataByte > 127) dataByte -= 256;
						output[i] = dataByte;
					}
					i += 1;
				}
				i += ((128 - height) / 2);
			}
		}
		return output;
	}
	private static void createLevel(C_5664496 minecraft, Screen parent, File dir, long seed, int spawnX, int spawnY, int spawnZ, long time, long sizeOnDisk, @Nullable NbtCompound player) {
		try {
			setOverlay("Converting level", "Writing level data...");
			dir.mkdirs();
			File level = new File(dir, "level.dat");
			NbtCompound data = new NbtCompound();
			data.putLong("RandomSeed", seed);
			data.putInt("SpawnX", spawnX);
			data.putInt("SpawnY", spawnY);
			data.putInt("SpawnZ", spawnZ);
			data.putLong("Time", time);
			data.putLong("SizeOnDisk", sizeOnDisk);
			data.putLong("LastPlayed", System.currentTimeMillis());
			if (player != null) data.putCompound("Player", player);
			NbtCompound output = new NbtCompound();
			output.put("Data", data);
			SaveModLevel.save(output, Files.newOutputStream(level.toPath()));
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static void done(C_5664496 minecraft, Screen parent, String worldName) {
		if (SaveConfig.instance.shouldLoadAfterConvert.value()) {
			((SaveModMinecraft)minecraft).save$set(worldName);
			minecraft.m_6408915(null);
		} else minecraft.m_6408915(new SaveInfoScreen(parent, "Convert World", "Successfully converted world to '" + worldName + "'!", InfoScreen.Type.DIRT, true));
	}
	private static void error(C_5664496 minecraft, Screen parent, String error) {
		minecraft.m_6408915(new SaveInfoScreen(parent, "Error!", ((error == null || error.isEmpty()) ? "Failed to convert world!" : error), InfoScreen.Type.ERROR, true));
	}
	public enum Version {
		classic("classic"),
		indev("indev");
		private String name;
		Version(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}
	private static void setOverlay(String title, String message) {
		setOverlay(title, message, -1);
	}
	private static void setOverlay(String title, String message, int value) {
		SaveHelper.infoOverlay.setTitle(title);
		SaveHelper.infoOverlay.setDescription(message);
		SaveHelper.infoOverlay.setLoading(value);
	}
}