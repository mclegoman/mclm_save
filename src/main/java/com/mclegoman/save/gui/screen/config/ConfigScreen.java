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
		this.buttons.add(new ButtonWidget(2, this.width / 2 - 150, getY(1), 148, 20, "Allow Boss Mode: " + SaveConfig.instance.allowBossMode.value()));
		this.buttons.add(new ButtonWidget(3, this.width / 2 + 2, getY(1), 148, 20, "Starter Items: " + SaveConfig.instance.starterItems.value()));
		this.buttons.add(new ButtonWidget(4, this.width / 2 - 150, getY(2), 300, 20, "Load After Convert: " + SaveConfig.instance.shouldLoadAfterConvert.value()));
		// TODO: Fix Credits/Attributions Screen.
		ButtonWidget credits = new ButtonWidget(10, this.width / 2 - 100, this.height / 6 + 144, 200, 20, "Credits and Attribution");
		credits.active = false;
		this.buttons.add(credits);
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
				if (button.id == 2) SaveConfig.instance.allowBossMode.setValue(!SaveConfig.instance.allowBossMode.value());
				if (button.id == 3) SaveConfig.instance.starterItems.setValue(!SaveConfig.instance.starterItems.value());
				if (button.id == 4) SaveConfig.instance.shouldLoadAfterConvert.setValue(!SaveConfig.instance.shouldLoadAfterConvert.value());
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
		super.render(i, j, f);
	}
	public void keyPressed(char chr, int key) {
		if (key == 1) {
			SaveConfig.instance.save();
			if (Data.Resources.minecraft != null) Data.Resources.minecraft.m_6408915(this.parent);
		}
	}
}
