/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui.screen.convert;

import com.mclegoman.save.convert.Convert;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import net.minecraft.client.gui.screen.Screen;

public class ConvertWorldInfoScreen extends SaveInfoScreen {
	private final Screen parent;
	private final String worldName;
	public ConvertWorldInfoScreen(Screen parent, String message) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.parent = parent;
		this.worldName = null;
	}
	public ConvertWorldInfoScreen(Screen parent, String message, String worldName) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.parent = parent;
		this.worldName = worldName;
	}
	public void save$confirmResult(boolean confirm, int i) {
		Convert.result(this.minecraft, this.parent, this.worldName, i, confirm);
	}
}
