/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.util;

import com.mclegoman.save.gui.SaveInfoOverlay;
import com.mclegoman.save.nbt.NbtDouble;
import com.mclegoman.save.nbt.NbtFloat;
import com.mclegoman.save.nbt.NbtList;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
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
	}public static NbtList toNbtList(float... fs) {
		NbtList nbtList = new NbtList();
		for (float value : fs) nbtList.add(new NbtFloat(value));
		return nbtList;
	}
}
