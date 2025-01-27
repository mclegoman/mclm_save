/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui.screen.config;

import com.mclegoman.save.data.Data;
import com.mclegoman.save.util.StringHelper;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.config.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.input.Keyboard;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ConfigScreen extends Screen {
	private final Screen parent;
	public ConfigScreen(Screen screen) {
		this.parent = screen;
	}
	public int getY(int index) {
		return (this.height / 6) + (index * 24);
	}
	public final void init() {
		this.buttons.clear();
		this.buttons.add(new ButtonWidget(0, this.width / 2 - 150, getY(0), 148, 20, "Force April Fools: " + SaveConfig.instance.forceAprilFools.value()));
		this.buttons.add(new ButtonWidget(1, this.width / 2 + 2, getY(0), 148, 20, "Dialog Theme: " + SaveConfig.instance.dialogTheme.value().getName()));
		this.buttons.add(new ButtonWidget(6, this.width / 2 - 150, getY(1), 300, 20, "Disable Cave Generation: " + SaveConfig.instance.disableCaves.value()));
		this.buttons.add(new ButtonWidget(2, this.width / 2 - 150, getY(2), 148, 20, "Fix Flower Generation: " + SaveConfig.instance.fixFlowerGen.value()));
		this.buttons.add(new ButtonWidget(3, this.width / 2 + 2, getY(2), 148, 20, "Prevent Flower Drop: " + SaveConfig.instance.shouldDisableFlowerItems.value()));
		this.buttons.add(new ButtonWidget(4, this.width / 2 - 150, getY(3), 300, 20, "Starter Items: " + SaveConfig.instance.starterItems.value()));
		this.buttons.add(new ButtonWidget(5, this.width / 2 - 150, getY(4), 300, 20, "Load After Convert: " + SaveConfig.instance.shouldLoadAfterConvert.value()));
		this.buttons.add(new ButtonWidget(10, this.width / 2 - 100, this.height / 6 + 144, 200, 20, "Credits and Attribution"));
		this.buttons.add(new ButtonWidget(11, this.width / 2 - 100, this.height / 6 + 168, 98, 20, "Reset to Default"));
		this.buttons.add(new ButtonWidget(12, this.width / 2 + 2, this.height / 6 + 168, 98, 20, "Done"));
	}

	protected void buttonClicked(net.minecraft.client.gui.widget.ButtonWidget button) {
		if (button.active) {
			if (button.id == 12) {
				SaveConfig.instance.save();
				if (Data.Resources.minecraft != null) Data.Resources.minecraft.m_6408915(this.parent);
			} else {
				if (button.id == 0) SaveConfig.instance.forceAprilFools.setValue(!SaveConfig.instance.forceAprilFools.value());
				if (button.id == 1) {
					Theme theme = SaveConfig.instance.dialogTheme.value();
					if (theme.equals(Theme.system)) {
						if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) SaveConfig.instance.dialogTheme.setValue(Theme.metal);
						else SaveConfig.instance.dialogTheme.setValue(Theme.light);
					} else if (theme.equals(Theme.light)) {
						if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) SaveConfig.instance.dialogTheme.setValue(Theme.system);
						else SaveConfig.instance.dialogTheme.setValue(Theme.dark);
					} else if (theme.equals(Theme.dark)) {
						if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) SaveConfig.instance.dialogTheme.setValue(Theme.light);
						else SaveConfig.instance.dialogTheme.setValue(Theme.metal);
					} else if (theme.equals(Theme.metal)) {
						if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) SaveConfig.instance.dialogTheme.setValue(Theme.dark);
						else SaveConfig.instance.dialogTheme.setValue(Theme.system);
					}
				}
				if (button.id == 2) SaveConfig.instance.fixFlowerGen.setValue(!SaveConfig.instance.fixFlowerGen.value());
				if (button.id == 3) SaveConfig.instance.shouldDisableFlowerItems.setValue(!SaveConfig.instance.shouldDisableFlowerItems.value());
				if (button.id == 4) SaveConfig.instance.starterItems.setValue(!SaveConfig.instance.starterItems.value());
				if (button.id == 5) SaveConfig.instance.shouldLoadAfterConvert.setValue(!SaveConfig.instance.shouldLoadAfterConvert.value());
				if (button.id == 6) SaveConfig.instance.disableCaves.setValue(!SaveConfig.instance.disableCaves.value());
				if (button.id == 10 && Data.Resources.minecraft != null) Data.Resources.minecraft.m_6408915(new CreditsScreen(new ConfigScreen(this.parent)));
				if (button.id == 11) reset();
				init();
			}
		}
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void reset() {
		for (TrackedValue value : SaveConfig.instance.values()) value.setValue(value.getDefaultValue(), false);
	}
	public final void render(int i, int j, float f) {
		this.drawBackgroundTexture();
		drawCenteredString(this.textRenderer, StringHelper.getFormattedString("[save] Config"), this.width / 2, 20, 16777215);
		int warningY = 2;
		if (SaveConfig.instance.fixFlowerGen.value()) {
			if (SaveConfig.instance.shouldDisableFlowerItems.value()) {
				drawCenteredString(this.textRenderer, StringHelper.getFormattedString("Note: Prevent Flower Drop is not required with Fix Flower Generation."), this.width / 2, warningY, 0xFF5555);
				warningY += 11;
			}
		} else {
			if (SaveConfig.instance.shouldDisableFlowerItems.value()) drawCenteredString(this.textRenderer, StringHelper.getFormattedString("Note: We recommend using Fix Flower Generation instead of Prevent Flower Drop."), this.width / 2, warningY, 0xFF5555);
			else drawCenteredString(this.textRenderer, StringHelper.getFormattedString("WARNING: Fix Flower Generation is recommended for performance purposes."), this.width / 2, warningY, 0xFF5555);
			warningY += 11;
		}
		if (!SaveConfig.instance.disableCaves.value()) drawCenteredString(this.textRenderer, StringHelper.getFormattedString("WARNING: Caves will regenerate everytime the chunk is loaded."), this.width / 2, warningY, 0xFF5555);
		super.render(i, j, f);
	}
	public void keyPressed(char chr, int key) {
		if (key == 1) {
			SaveConfig.instance.save();
			if (Data.Resources.minecraft != null) Data.Resources.minecraft.m_6408915(this.parent);
		}
	}
}
