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
	private final boolean trueButtonActive;
	private final String trueButtonText;
	private final boolean falseButtonActive;
	private final String falseButtonText;
	public ConfirmScreen(Screen screen, String string, String string2, int i) {
		this(screen, string, string2, i, "Yes", "No");
	}
	public ConfirmScreen(Screen screen, String string, String string2, int i, String trueButtonText, String falseButtonText) {
		this(screen, string, string2, i, true, trueButtonText, true, falseButtonText);
	}
	public ConfirmScreen(Screen screen, String string, String string2, int i, boolean trueButtonActive, String trueButtonText, boolean falseButtonActive, String falseButtonText) {
		this.parent = screen;
		this.title = string;
		this.description = string2;
		this.id = i;
		this.trueButtonActive = trueButtonActive;
		this.trueButtonText = trueButtonText;
		this.falseButtonActive = falseButtonActive;
		this.falseButtonText = falseButtonText;
	}
	public final void init() {
		this.buttons.clear();
		ButtonWidget trueButton = new OptionButtonWidget(0, this.width / 2 - 155, this.height / 6 + 96, this.trueButtonText);
		trueButton.active = this.trueButtonActive;
		this.buttons.add(trueButton);
		ButtonWidget falseButton = new OptionButtonWidget(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.falseButtonText);
		falseButton.active = this.falseButtonActive;
		this.buttons.add(falseButton);
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

