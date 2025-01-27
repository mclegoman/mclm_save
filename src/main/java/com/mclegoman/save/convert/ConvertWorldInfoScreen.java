/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.util.StringHelper;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;

public class ConvertWorldInfoScreen extends SaveInfoScreen {
	private final Convert.Version version;
	private final Screen parent;
	private final String worldName;
	private final File file;
	public boolean render;
	private short width;
	private short length;
	private short height;
	private NbtCompound nbtCompound;
	private NbtCompound player;
	private Convert.WorldData worldData;
	public ConvertWorldInfoScreen(Screen parent, String message) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = null;
		this.file = null;
		this.width = 0;
		this.length = 0;
		this.height = 0;
		this.nbtCompound = null;
		this.player = null;
		this.worldData = null;
	}
	public ConvertWorldInfoScreen(Convert.Version version, Screen parent, String message, String worldName, File input) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = version;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
		this.width = 0;
		this.length = 0;
		this.height = 0;
		this.nbtCompound = null;
		this.player = null;
		this.worldData = null;
	}
	public ConvertWorldInfoScreen(Screen parent, String message, String worldName, File input) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
		this.width = 0;
		this.length = 0;
		this.height = 0;
		this.nbtCompound = null;
		this.player = null;
		this.worldData = null;
	}
	public ConvertWorldInfoScreen(Screen parent, String message, String worldName, File input, short width, short length, short height, NbtCompound nbtCompound, NbtCompound player, Convert.WorldData worldData) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
		this.width = width;
		this.length = length;
		this.height = height;
		this.nbtCompound = nbtCompound;
		this.player = player;
		this.worldData = worldData;
	}
	public void save$confirmResult(boolean confirm, int i) {
		Convert.result(Data.Resources.minecraft, i == 0 ? (confirm ? Convert.Version.classic : Convert.Version.indev) : this.version, this.parent, this.worldName, this.file, i, confirm);
	}
	public void save$confirmResult(int value, int i) {
		Convert.result(Data.Resources.minecraft, this.parent, this.worldName, i, value, this.width, this.height, this.length, this.nbtCompound, this.player, this.worldData);
	}
	public void render(int i, int j, float f) {
		super.render(i, j, f);
		renderModInfo(this.width, this.height);
		this.render = true;
	}
	public void renderModInfo(int width, int height) {
		if (Data.getVersion().isDevelopmentBuild() || SaveConfig.instance.debug.value()) {
			Data.Resources.minecraft.f_0426313.drawWithShadow(StringHelper.getFormattedString("[save] [save_version] ([minecraft_version])"), 2, height - (Data.getVersion().isDevelopmentBuild() ? 23 : 12), 16777215);
			if (Data.getVersion().isDevelopmentBuild()) Data.Resources.minecraft.f_0426313.drawWithShadow(StringHelper.getFormattedString("Development Build"), 2, height - 12, 0xFFAA00);
		}
	}
}
