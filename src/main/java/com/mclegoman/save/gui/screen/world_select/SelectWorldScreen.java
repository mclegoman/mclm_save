/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui.screen.world_select;

import com.mclegoman.save.level.SaveModMinecraft;
import com.mclegoman.save.level.SaveModWorld;
import com.mclegoman.save.nbt.NbtCompound;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.util.SaveHelper;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.awt.*;
import java.io.File;

public class SelectWorldScreen extends Screen {
	public Screen parent;
	public String title = "Select world";
	public SelectWorldScreen(Screen screen) {
		this.parent = screen;
	}
	public void init() {
		this.buttons.clear();
		File file = SaveHelper.getMinecraftDir();
		for(int var2 = 0; var2 < 5; ++var2) {
			NbtCompound var3;
			if ((var3 = SaveModWorld.get(file, "World" + (var2 + 1))) == null) {
				ButtonWidget world = new net.minecraft.client.gui.widget.ButtonWidget(var2, this.width / 2 - 100, this.height / 6 + var2 * 24, "- empty -");
				world.active = isWorldButtonsActive();
				this.buttons.add(world);
			} else {
				String var4 = "World " + (var2 + 1);
				long var5 = var3.getLong("SizeOnDisk");
				var4 = var4 + " (" + (float)(var5 / 1024L * 100L / 1024L) / 100.0F + " MB)";
				ButtonWidget world = new net.minecraft.client.gui.widget.ButtonWidget(var2, this.width / 2 - 100, this.height / 6 + var2 * 24, var4);
				if (this.minecraft.f_5854988 != null && ((SaveModWorld)this.minecraft.f_5854988).name.equals("World" + (var2 + 1))) world.active = isLoadedWorldActive();
				this.buttons.add(world);
			}
		}
		this.m_7555169();
	}
	public boolean isWorldButtonsActive() {
		return true;
	}
	public boolean isLoadedWorldActive() {
		return false;
	}
	public String getWorldName(int i) {
		return SaveModWorld.get(SaveHelper.getMinecraftDir(), "World" + i) != null ? "World" + i : null;
	}
	public void m_7555169() {
		this.buttons.add(new ButtonWidget(5, this.width / 2 - 100, this.height / 6 + 120 + 12, 98, 20, "Delete world..."));
		ButtonWidget convert = new ButtonWidget(6, this.width / 2 + 2, this.height / 6 + 120 + 12, 98, 20, "Convert...");
		//convert.active = false;
		this.buttons.add(convert);
		this.buttons.add(new ButtonWidget(7, this.width / 2 + 102, this.height / 6 + 120 + 12, 20, 20, "..."));
		this.buttons.add(new ButtonWidget(8, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}
	public void buttonClicked(ButtonWidget buttonWidget) {
		if (buttonWidget.active) {
			if (buttonWidget.id < 5) {
				int world = buttonWidget.id + 1;
				this.buttons.clear();
				this.loadWorld(world);
			} else if (buttonWidget.id == 5) {
				this.minecraft.m_6408915(new DeleteWorldScreen(this));
			} else if (buttonWidget.id == 6) {
				this.minecraft.m_6408915(new ConvertWorldScreen(this));
			} else if (buttonWidget.id == 7) {
				try {
					Desktop.getDesktop().open(SaveHelper.getSavesDir());
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, error.getLocalizedMessage());
					buttonWidget.active = false;
				}
			} else if (buttonWidget.id == 8) {
				this.minecraft.m_6408915(this.parent);
			}
		}
	}
	public void loadWorld(int i) {
		((SaveModMinecraft)this.minecraft).save$set("World" + i);
		this.minecraft.m_6408915(null);
	}
	public void render(int i, int j, float f) {
		this.drawBackgroundTexture();
		drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 16777215);
		super.render(i, j, f);
	}
}
