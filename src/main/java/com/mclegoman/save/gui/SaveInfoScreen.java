/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui;

import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.util.StringHelper;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.config.SaveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.List;

@ClientOnly
public class SaveInfoScreen extends InfoScreen {
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		super(parentScreen, title, status, type, canBeClosedMessage, extraInfoMessage);
	}
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, String canBeClosedMessage, String extraInfoMessage) {
		super(parentScreen, title, status, type, canBeClosedMessage, extraInfoMessage);
	}
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, boolean canBeClosed, String extraInfoMessage) {
		super(parentScreen, title, status, type, canBeClosed, extraInfoMessage);
	}
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, List<String> status, Type type, boolean canBeClosed) {
		super(parentScreen, title, status, type, canBeClosed);
	}
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, boolean canBeClosed, String extraInfoMessage) {
		super(parentScreen, title, status, type, canBeClosed, extraInfoMessage);
	}
	public SaveInfoScreen(@Nullable Screen parentScreen, String title, String status, Type type, boolean canBeClosed) {
		super(parentScreen, title, status, type, canBeClosed);
	}
	@Override
	public void renderModInfo() {
		if (Data.getVersion().isDevelopmentBuild() || SaveConfig.instance.debug.value()) {
			textRenderer.drawWithShadow(StringHelper.getFormattedString("[save] [save_version] ([save:minecraft])"), 2, this.height - (Data.getVersion().isDevelopmentBuild() ? 23 : 12), 16777215);
			if (Data.getVersion().isDevelopmentBuild()) textRenderer.drawWithShadow(StringHelper.getFormattedString("Development Build"), 2, this.height - 12, 0xFFAA00);
		}
	}
	@Override
	public void renderLogo() {
		GL11.glBindTexture(3553, this.minecraft.f_9413506.load("/assets/save/logo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexture((this.width - 256) / 2, 22, 0, 0, 256, 64);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2 + 32), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float scale;
		GL11.glScalef(scale = (1.8F - Math.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0F * 3.1415927F * 2.0F) * 0.1F)) * 100.0F / (float)(this.textRenderer.getWidth(StringHelper.getFormattedString(title)) + 32), scale, scale);
		drawCenteredString(this.textRenderer, StringHelper.getFormattedString(title), 0, -8, 0xFFAA00);
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldRenderTitle() {
		return false;
	}
}
