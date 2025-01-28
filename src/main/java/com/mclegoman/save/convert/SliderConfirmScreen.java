/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.mclegoman.save.api.gui.screen.SaveModScreen;
import com.mclegoman.save.api.gui.widget.SliderWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import org.lwjgl.input.Mouse;

public class SliderConfirmScreen extends Screen {
	private final Screen parent;
	private final String title;
	private final String description;
	private final int id;
	private final String sliderText;
	private final int sliderMultiplier;
	private final String confirmText;
	private double value;
	public SliderConfirmScreen(Screen screen, String string, String string2, int i, String sliderText, int sliderMultiplier, String confirmText) {
		this.parent = screen;
		this.title = string;
		this.description = string2;
		this.id = i;
		this.sliderText = sliderText;
		this.sliderMultiplier = sliderMultiplier;
		this.confirmText = confirmText;
	}
	public final void init() {
		this.buttons.clear();
		this.buttons.add(new SliderWidget(0, this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.sliderText, this.value, this.sliderMultiplier));
		this.buttons.add(new OptionButtonWidget(1, this.width / 2 - 155 + 160, this.height / 6 + 96, this.confirmText));
	}
	protected final void buttonClicked(ButtonWidget buttonWidget) {
		if (buttonWidget.active) {
			if (buttonWidget.id == 0) {
				((SliderWidget)buttonWidget).focused = true;
				this.value = ((SliderWidget)buttonWidget).getValue(true);
				((SliderWidget)buttonWidget).setValueFromMouse((double) (Mouse.getEventX() * this.width) / this.minecraft.f_0545414);
			} else ((SliderWidget)this.buttons.get(0)).focused = false;
			if (buttonWidget.id == 1) ((SaveModScreen)this.parent).save$confirmResult((int)((SliderWidget)this.buttons.get(0)).getValue(false), this.id);
		}
	}
	protected void keyPressed(char c, int i) {
		if (((SliderWidget)this.buttons.get(0)).focused) {
			double value = (((SliderWidget) this.buttons.get(0)).getValue(false));
			if (i == 200 || i == 203) {
				// DOWN
				value = Math.max(0, value - 1) / ((SliderWidget) this.buttons.get(0)).getValueMultiplier();
			} else if (i == 205 || i == 208) {
				// UP
				value = Math.min(100, value + 1) / ((SliderWidget) this.buttons.get(0)).getValueMultiplier();
			}
			((SliderWidget)this.buttons.get(0)).setValue(value);
			this.value = ((SliderWidget)this.buttons.get(0)).getValue(true);
		}
		super.keyPressed(c, i);
	}
	public final void render(int i, int j, float f) {
		this.drawBackgroundTexture();
		drawCenteredString(this.textRenderer, this.title, this.width / 2, 70, 16777215);
		drawCenteredString(this.textRenderer, this.description, this.width / 2, 90, 16777215);
		super.render(i, j, f);
	}
}

