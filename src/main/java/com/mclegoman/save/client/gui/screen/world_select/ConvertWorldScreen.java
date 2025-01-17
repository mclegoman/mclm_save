package com.mclegoman.save.client.gui.screen.world_select;

import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.client.gui.screen.SaveInfoScreen;
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
		} else save$confirmResult(true, i);
	}

	public void save$confirmResult(boolean confirm, int i) {
		if (confirm) {
			if (this.getWorldName(i) != null) {
				//World.m_9583644(this.minecraft.m_7340082(), this.getWorldName(i));
			}
			// Ask for input file, convert the world, and output to selected slot.
			this.minecraft.m_6408915(new SaveInfoScreen(this.parent, "Convert World", "Successfully converted world to slot " + i + "!", InfoScreen.Type.DIRT, true));
		} else this.minecraft.m_6408915(this.parent);
	}
}
