/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui;

import com.mclegoman.save.api.gui.InfoOverlay;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.util.StringHelper;

import java.awt.*;

public class SaveInfoOverlay extends InfoOverlay {
	public int getProgressBarColor(int v) {
		return Color.getHSBColor(v / 100.0F, 1.0F, 1.0F).getRGB();
	}
	public void renderModInfo(int width, int height) {
		if (Data.getVersion().isDevelopmentBuild() || SaveConfig.instance.debug.value()) {
			Data.Resources.minecraft.f_0426313.drawWithShadow(StringHelper.getFormattedString("[save] [save_version] ([save:minecraft])"), 2, height - (Data.getVersion().isDevelopmentBuild() ? 23 : 12), 16777215);
			if (Data.getVersion().isDevelopmentBuild()) Data.Resources.minecraft.f_0426313.drawWithShadow(StringHelper.getFormattedString("Development Build"), 2, height - 12, 0xFFAA00);
		}
	}
}
