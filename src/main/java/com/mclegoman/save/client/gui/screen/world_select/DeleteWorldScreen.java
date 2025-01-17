package com.mclegoman.save.client.gui.screen.world_select;

import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.api.level.SaveModWorld;
import com.mclegoman.save.common.util.SaveHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class DeleteWorldScreen extends SelectWorldScreen {
	public DeleteWorldScreen(Screen screen) {
		super(screen);
		this.title = "Delete world";
	}

	public void m_7555169() {
		this.buttons.add(new ButtonWidget(7, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}
	public boolean isWorldButtonsActive() {
		return false;
	}
	public void loadWorld(int i) {
		if (this.getWorldName(i) != null) {
			this.minecraft.m_6408915(new ConfirmScreen(this, "Are you sure you want to delete this world?", "'" + this.getWorldName(i) + "' will be lost forever!", i));
		} else System.out.println(":DarknessWhat: " + i);
	}

	public void save$confirmResult(boolean value, int i) {
		if (value) SaveModWorld.delete(SaveHelper.getSavesDir(), this.getWorldName(i));
		this.minecraft.m_6408915(this.parent);
	}
}
