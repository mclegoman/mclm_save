/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client.gui;

import com.mclegoman.mclm_save.client.util.StringHelper;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ButtonWidget extends net.minecraft.client.gui.widget.ButtonWidget {
	public ButtonWidget(int id, int x, int y, String message) {
		super(id, x, y, StringHelper.getFormattedString(message));
	}
	public ButtonWidget(int id, int x, int y, int width, int height, String message) {
		super(id, x, y, width, height, StringHelper.getFormattedString(message));
	}
}
