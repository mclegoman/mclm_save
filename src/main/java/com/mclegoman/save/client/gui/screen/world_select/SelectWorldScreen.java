package com.mclegoman.save.client.gui.screen.world_select;

import com.mclegoman.save.api.level.SaveModMinecraft;
import com.mclegoman.save.api.level.SaveModWorld;
import com.mclegoman.save.api.nbt.NbtCompound;
import com.mclegoman.save.common.util.SaveHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

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
				this.buttons.add(new net.minecraft.client.gui.widget.ButtonWidget(var2, this.width / 2 - 100, this.height / 6 + var2 * 24, var4));
			}
		}

		this.m_7555169();
	}

	public boolean isWorldButtonsActive() {
		return true;
	}
	
	public String getWorldName(int i) {
		return SaveModWorld.get(SaveHelper.getMinecraftDir(), "World" + i) != null ? "World" + i : null;
	}

	public void m_7555169() {
		this.buttons.add(new com.mclegoman.save.api.gui.widget.ButtonWidget(5, this.width / 2 - 100, this.height / 6 + 120 + 12, 98, 20, "Delete world..."));
		ButtonWidget convert = new com.mclegoman.save.api.gui.widget.ButtonWidget(6, this.width / 2 + 2, this.height / 6 + 120 + 12, 98, 20, "Convert...");
		convert.active = false;
		this.buttons.add(convert);
		this.buttons.add(new ButtonWidget(7, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

	public void buttonClicked(ButtonWidget buttonWidget) {
		if (buttonWidget.active) {
			if (buttonWidget.id < 5) {
				this.loadWorld(buttonWidget.id + 1);
			} else if (buttonWidget.id == 5) {
				this.minecraft.m_6408915(new DeleteWorldScreen(this));
			} else if (buttonWidget.id == 6) {
				this.minecraft.m_6408915(new ConvertWorldScreen(this));
			} else if (buttonWidget.id == 7) {
				this.minecraft.m_6408915(this.parent);
			}
		}
	}
	public void loadWorld(int i) {
		while (!this.buttons.isEmpty()) this.buttons.clear();
		((SaveModMinecraft)this.minecraft).save$set("World" + i);
		this.minecraft.m_6408915(null);
	}

	public void render(int i, int j, float f) {
		this.drawBackgroundTexture();
		drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 16777215);
		super.render(i, j, f);
	}
}
