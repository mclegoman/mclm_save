/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.util;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SaveHelper {
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
}
