/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.api.gui;

import com.mclegoman.mclm_save.api.util.StringHelper;
import com.mclegoman.mclm_save.rtu.util.LogType;
import com.mclegoman.mclm_save.api.data.Resources;
import com.mclegoman.mclm_save.common.data.Data;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.List;

@ClientOnly
public class InfoScreen extends Screen {
	public String title;
	public List<String> status;
	public Type type;
	public String canBeClosedMessage;
	public String extraInfoMessage;
	public Screen parentScreen;
	public InfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		this.title = title;
		this.status = status;
		this.type = type;
		this.canBeClosedMessage = canBeClosedMessage;
		this.extraInfoMessage = extraInfoMessage;
		this.parentScreen = parentScreen;
		for (String message : status) Data.version.sendToLog(this.type.equals(Type.ERROR) ? LogType.WARN : LogType.INFO, StringHelper.getFormattedString(message));
	}
	public InfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		this.title = title;
		List<String> messages = new ArrayList<>();
		if (status.length() <= 64) {
			messages.add(status);
		} else {
			int index = 0;
			while (index < status.length()) {
				int newIndex = Math.min(index + 64, status.length());
				messages.add(status.substring(index, newIndex));
				index = newIndex;
			}
		}
		this.status = messages;
		this.type = type;
		this.canBeClosedMessage = canBeClosedMessage;
		this.extraInfoMessage = extraInfoMessage;
		this.parentScreen = parentScreen;
		Data.version.sendToLog(this.type.equals(Type.ERROR) ? LogType.WARN : LogType.INFO, StringHelper.getFormattedString(status));
	}
	public InfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, boolean canBeClosed, String extraInfoMessage) {
		this(parentScreen, title, status, type, canBeClosed ? "Press ESC to return to the game" : "", extraInfoMessage);
	}
	public InfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, boolean canBeClosed) {
		this(parentScreen, title, status, type, canBeClosed ? "Press ESC to return to the game" : "", "");
	}
	public InfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, boolean canBeClosed, String extraInfoMessage) {
		this(parentScreen, title, status, type, canBeClosed ? "Press ESC to return to the game" : "", extraInfoMessage);
	}
	public InfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, boolean canBeClosed) {
		this(parentScreen, title, status, type, canBeClosed ? "Press ESC to return to the game" : "", "");
	}
	public void render(int i, int j, float f) {
		if (this.type == Type.DIRT) {
			if (Resources.minecraft != null) {
				BufferBuilder var4 = BufferBuilder.INSTANCE;
				int var5 = Resources.minecraft.f_9413506.load("/dirt.png");
				int var8 = Resources.minecraft.f_0545414 * 240 / Resources.minecraft.f_5990000;
				int var3 = Resources.minecraft.f_5990000 * 240 / Resources.minecraft.f_5990000;
				GL11.glBindTexture(3553, var5);
				var4.start();
				var4.color(4210752);
				var4.vertex(0.0F, (float) var3, 0.0F, 0.0F, (float) var3 / 32.0F);
				var4.vertex((float) var8, (float) var3, 0.0F, (float) var8 / 32.0F, (float) var3 / 32.0F);
				var4.vertex((float) var8, 0.0F, 0.0F, (float) var8 / 32.0F, 0.0F);
				var4.vertex(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
				var4.end();
			} else {
				fillGradient(0, 0, this.width, this.height, -12574688, -11530224);
			}
		}
		if (this.type == Type.ERROR) {
			fillGradient(0, 0, this.width, this.height, -12574688, -11530224);
		}
		int y = Math.max((this.height / 2) - ((this.status.size() * 11) / 2), 22);
		drawCenteredString(this.textRenderer, StringHelper.getFormattedString(title), this.width / 2, y - 20, 0xFFAA00);
		for (String string : this.status) {
			drawCenteredString(this.textRenderer, StringHelper.getFormattedString(string), this.width / 2, y, 16777215);
			y += 11;
		}
		if (this.canBeClosedMessage != null && !this.canBeClosedMessage.equals("")) drawCenteredString(this.textRenderer, StringHelper.getFormattedString(this.canBeClosedMessage), this.width / 2, this.height - 20, 16777215);
		if (this.extraInfoMessage != null && !this.extraInfoMessage.equals("")) drawCenteredString(this.textRenderer, StringHelper.getFormattedString(this.extraInfoMessage), this.width / 2, this.height - 31, 16777215);
		renderModInfo();
		super.render(i, j,f );
	}
	public void renderModInfo() {
	}
	public void keyPressed(char chr, int key) {
		if (this.canBeClosedMessage != null && !this.canBeClosedMessage.equals("")) {
			if (key == 1) {
				Resources.minecraft.m_6408915(this.parentScreen);
				Resources.minecraft.m_5690108();
			}
		}
	}
	public enum Type {
		DIRT,
		ERROR
	}
}
