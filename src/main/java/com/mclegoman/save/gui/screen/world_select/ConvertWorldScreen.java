/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui.screen.world_select;

import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.convert.Convert;
import com.mclegoman.save.level.SaveModWorld;
import com.mclegoman.save.util.SaveHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ConvertWorldScreen extends SelectWorldScreen {
	public ConvertWorldScreen(Screen screen) {
		super(screen);
		this.title = "Select output for converted classic/indev world";
	}
	public void m_7555169() {
		this.buttons.add(new ButtonWidget(8, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}
	public void loadWorld(int i) {
		String worldName = this.getWorldName(i);
		if (worldName != null) {
			this.minecraft.m_6408915(new ConfirmScreen(this, "Are you sure you want to use this slot?", "'" + worldName + "' will be lost forever!", i));
		} else this.convert(true, i);
	}
	public void convert(boolean confirm, int i) {
		if (confirm) {
			if (this.getWorldName(i) != null) SaveModWorld.delete(SaveHelper.getSavesDir(), this.getWorldName(i));
			Convert.start(this.minecraft, this.parent, i);
		} else this.minecraft.m_6408915(this.parent);
	}
	public void save$confirmResult(boolean confirm, int i) {
		convert(confirm, i);
	}
}
