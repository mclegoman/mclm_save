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

import java.io.File;

public class ConvertWorldInfoScreen extends SaveInfoScreen {
	private final Convert.Version version;
	private final Screen parent;
	private final String worldName;
	private final File file;
	public ConvertWorldInfoScreen(Screen parent, String message) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = null;
		this.parent = parent;
		this.worldName = null;
		this.file = null;
	}
	public ConvertWorldInfoScreen(Convert.Version version, Screen parent, String message, String worldName, File input) {
		super(parent, "Convert World", message, Type.DIRT, false);
		this.version = version;
		this.parent = parent;
		this.worldName = worldName;
		this.file = input;
	}
	public void save$confirmResult(boolean confirm, int i) {
		Convert.result(this.minecraft, this.version, this.parent, this.worldName, this.file, i, confirm);
	}
}
