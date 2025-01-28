/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.util;

import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.gui.SaveInfoOverlay;
import com.mclegoman.save.level.SaveModWorld;
import com.mclegoman.save.nbt.NbtDouble;
import com.mclegoman.save.nbt.NbtFloat;
import com.mclegoman.save.nbt.NbtList;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import org.lwjgl.input.Keyboard;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SaveHelper {
	public static SaveInfoOverlay infoOverlay = new SaveInfoOverlay();
	private static final Map<String, Class<?>> blockEntityIdToType = new HashMap<>();
	private static final Map<Class<?>, String> blockEntityTypeToId = new HashMap<>();
	public static File getMinecraftDir() {
		return QuiltLoader.getGameDir().toFile();
	}
	public static File getSavesDir() {
		return new File(QuiltLoader.getGameDir().toFile(),"saves");
	}
	public static void register(Class<?> classObj, String id) {
		blockEntityIdToType.put(id, classObj);
		blockEntityTypeToId.put(classObj, id);
	}
	public static void init() {
		if (SaveConfig.instance.allowKeyboardRepeatEvents.value()) Keyboard.enableRepeatEvents(true);
		register(FurnaceBlockEntity.class, "Furnace");
		register(ChestBlockEntity.class, "Chest");
	}
	public static String getBlockEntityIdFromType(Class<?> classObj) {
		return blockEntityTypeToId.get(classObj);
	}
	public static Class<?> getBlockEntityTypeFromId(String id) {
		return blockEntityIdToType.get(id);
	}
	public static NbtList toNbtList(double... ds) {
		NbtList nbtList = new NbtList();
		for (double value : ds) nbtList.add(new NbtDouble(value));
		return nbtList;
	}
	public static NbtList toNbtList(float... fs) {
		NbtList nbtList = new NbtList();
		for (float value : fs) nbtList.add(new NbtFloat(value));
		return nbtList;
	}
	public static String toBase36(int i, boolean folder) {
		int value = getUnsignedValue((byte) i);
		return folder ? Integer.toString(value % 64, 36) : (i < 0 ? "-" : "") + Integer.toString(value, 36);
	}
	public static int getUnsignedValue(byte value) {
		return value & 0xFF;
	}
	public static File getChunkFile(File file, int x, int z) {
		int convertedX = SaveModWorld.convertChunkCoord(x, true);
		int convertedY = SaveModWorld.convertChunkCoord(z, true);
		File folder = new File(new File(file, toBase36(convertedX, true)), toBase36(convertedY, true));
		folder.mkdirs();
		return new File(folder, "c." + ((convertedX < 0) ? "-" : "") + toBase36((convertedX < 0) ? (convertedX * -1) : convertedX, false) + "."  + ((convertedY < 0) ? "-" : "") + toBase36((convertedY < 0) ? (convertedY * -1) : convertedY, false) + ".dat");
	}
}
