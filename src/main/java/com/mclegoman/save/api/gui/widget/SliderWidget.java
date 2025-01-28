/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.gui.widget;

import com.mclegoman.save.util.StringHelper;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.opengl.GL11;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class SliderWidget extends ButtonWidget {
	public boolean focused;
	protected double value;
	protected double valueMultiplier;
	protected String string;
	public SliderWidget(int i, int j, int k, int l, int m, String string, double value, double valueMultiplier) {
		super(i, j, k, l, m, string);
		this.string = string;
		this.valueMultiplier = valueMultiplier;
	}
	public SliderWidget(int i, int j, int k, String string, double value, double valueMultiplier) {
		super(i, j, k, string);
		this.string = string;
		this.valueMultiplier = valueMultiplier;
	}
	public void setValueFromMouse(double mouseX) {
		this.setValue((mouseX - (double)(this.x + 4)) / (double)(this.width - 8));
	}
	public void setValue(double value) {
		this.value = value < 0.0 ? 0.0 : Math.min(value, 1.0);
	}
	public double getValue(boolean raw) {
		return raw ? this.value : (int)(this.value * this.valueMultiplier);
	}
	public double getValueMultiplier() {
		return this.valueMultiplier;
	}
	public void m_9498802(C_5664496 minecraft, int i, int j) {
		this.message = StringHelper.getFormattedString(this.string + ": " + (int)getValue(false));
		if (this.visible) {
			GL11.glBindTexture(3553, minecraft.f_9413506.load("/gui/gui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexture(this.x, this.y, 0, 46, this.width / 2, this.height);
			this.drawTexture(this.x + this.width / 2, this.y, 200 - this.width / 2, 46, this.width / 2, this.height);
			GL11.glBindTexture(3553, minecraft.f_9413506.load("/assets/save/gui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexture(Math.min(this.x + (this.width - 8), (int) (this.x + (this.value * this.width))), this.y, 0, ((i >= this.x && j >= this.y && i < this.x + this.width && j < this.y + this.height) || this.focused) ? 20 : 0, this.width / 2, this.height);
			drawCenteredString(minecraft.f_0426313, this.message, this.x + this.width / 2, this.y + (this.height - 8) / 2, 14737632);
		}
	}
}
