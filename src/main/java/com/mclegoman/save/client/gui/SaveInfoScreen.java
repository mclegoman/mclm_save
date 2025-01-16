/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.client.gui;

import com.mclegoman.save.api.gui.InfoScreen;
import com.mclegoman.save.api.util.StringHelper;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.config.SaveConfig;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.List;

@ClientOnly
public final class SaveInfoScreen extends InfoScreen {
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
		if (Data.version.isDevelopmentBuild() || SaveConfig.instance.debug.value()) {
			textRenderer.drawWithShadow(StringHelper.getFormattedString("[save] [save_version] ([minecraft_version])"), 2, this.height - (Data.version.isDevelopmentBuild() ? 23 : 12), 16777215);
			if (Data.version.isDevelopmentBuild()) textRenderer.drawWithShadow(StringHelper.getFormattedString("Development Build"), 2, this.height - 12, 0xFFAA00);
		}
	}
}
