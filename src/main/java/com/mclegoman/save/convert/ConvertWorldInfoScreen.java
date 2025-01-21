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
import com.mclegoman.save.util.StringHelper;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;

public class ConvertWorldInfoScreen extends SaveInfoScreen {
	private final Convert.Version version;
	private final Screen parent;
	private final String worldName;
	private final File file;
	public boolean render;
	public ConvertWorldInfoScreen(Screen parent, String message) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = null;
		this.file = null;
	}
	public ConvertWorldInfoScreen(Convert.Version version, Screen parent, String message, String worldName, File input) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = version;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
	}
	public ConvertWorldInfoScreen(Screen parent, String message, String worldName, File input) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
	}
	public void save$confirmResult(boolean confirm, int i) {
		Convert.result(Data.Resources.minecraft, i == 0 ? (confirm ? Convert.Version.classic : Convert.Version.indev) : this.version, this.parent, this.worldName, this.file, i, confirm);
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
