/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.classicexplorer.fields.*;
import com.mclegoman.save.classicexplorer.io.Reader;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.level.SaveModLevel;
import com.mclegoman.save.nbt.*;
import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.util.SaveHelper;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
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
			select(minecraft, parent, worldName, input);
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static void select(C_5664496 minecraft, Screen parent, String worldName, File input) {
		minecraft.m_6408915(new ConfirmScreen(new ConvertWorldInfoScreen(parent, "Converting world...", worldName, input), "Converting world...", "What level format are you converting from?", 0, true, "Classic", false, "Indev"));
	}
	private static void convert(C_5664496 minecraft, Version version, Screen parent, String worldName, File input) {
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
				} else error(minecraft, parent, "Invalid block amount!");
				// TODO: Convert and save chunks, and calculate sizeOnDisk.
				createLevel(minecraft, parent, new File(SaveHelper.getSavesDir(), worldName), seed, spawnX, spawnY, spawnZ, time, 0, playerData[0]);
				done(minecraft, parent, worldName);
			}
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	private static @NotNull NbtList getInventory(ItemData[] items) {
		NbtList inventory = new NbtList();
		for (ItemData itemData : items) {
			NbtCompound item = new NbtCompound();
			short slot = (short)itemData.slot;
			short itemId = (short)itemData.id;
			byte count = itemData.count;
			item.putShort("Slot", slot);
			item.putShort("id", itemId);
			item.putByte("Count", count);
			item.putShort("Damage", (short) 0);
			if (((slot >= 0 && slot <= 36) || (slot >= 100 && slot <= 104)) && itemId > 0) inventory.add(item);
		}
		return inventory;
	}
	private static void convertIndev(C_5664496 minecraft, Screen parent, String worldName, boolean convertPlayerData, File input) {
		// TODO: Actually Convert.
		error(minecraft, parent, "Indev conversion is not yet ready!");
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
		minecraft.m_6408915(new SaveInfoScreen(parent, "Convert World", "Successfully converted world to '" + worldName + "'!", InfoScreen.Type.DIRT, true));
	}
	private static void error(C_5664496 minecraft, Screen parent, String error) {
		minecraft.m_6408915(new SaveInfoScreen(parent, "Error!", "Failed to convert world!" + ((error == null || error.isEmpty()) ? "" : " " + error), InfoScreen.Type.ERROR, true));
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
	public static class ItemData {
		private byte count;
		private int id;
		private int slot;
		public ItemData(byte count, int id, int slot) {
			this.count = count;
			this.id = id;
			this.slot = slot;
		}
		public void setCount(byte count) {
			this.count = count;
		}
		public void setId(int id) {
			this.id = id;
		}
		public void setSlot(int slot) {
			this.slot = slot;
		}
	}
	public static ItemData setItemData(ItemData itemData, ItemDataType type, int value) {
		if (itemData == null) itemData = new ItemData((byte) 0, 0, 0);
		if (type == ItemDataType.count) itemData.setCount((byte) value);
		else if (type == ItemDataType.id) itemData.setId(value);
		else if (type == ItemDataType.slot) itemData.setSlot(value);
		return itemData;
	}
	public enum ItemDataType {
		count,
		id,
		slot
	}
	private static void setOverlay(String title, String message) {
		SaveHelper.infoOverlay.setTitle(title);
		SaveHelper.infoOverlay.setDescription(message);
		SaveHelper.infoOverlay.setLoading(-1);
	}
}
