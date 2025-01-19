/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;

public class ConfirmScreen extends Screen {
	private final Screen parent;
	private final String title;
	private final String description;
	private final int id;

	public ConfirmScreen(Screen screen, String string, String string2, int i) {
		this.parent = screen;
		this.title = string;
		this.description = string2;
		this.id = i;
	}

	public final void init() {
		this.buttons.add(new OptionButtonWidget(0, this.width / 2 - 155, this.height / 6 + 96, "Yes"));
		this.buttons.add(new OptionButtonWidget(1, this.width / 2 - 155 + 160, this.height / 6 + 96, "No"));
	}

	protected final void buttonClicked(ButtonWidget buttonWidget) {
		((SaveModScreen)this.parent).save$confirmResult(buttonWidget.id == 0, this.id);
	}

	public final void render(int i, int j, float f) {
		this.drawBackgroundTexture();
		drawCenteredString(this.textRenderer, this.title, this.width / 2, 70, 16777215);
		drawCenteredString(this.textRenderer, this.description, this.width / 2, 90, 16777215);
		super.render(i, j, f);
	}
}

