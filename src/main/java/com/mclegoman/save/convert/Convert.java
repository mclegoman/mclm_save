/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.mclegoman.save.api.gui.screen.ConfirmScreen;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.gui.screen.convert.ConvertWorldInfoScreen;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;

import java.io.File;

public class Convert {
	public static void start(C_5664496 minecraft, Screen parent, int slot) {
		Data.Resources.minecraft.m_6408915(new ConvertWorldInfoScreen(parent, "Select world file to convert."));
		ConvertDialog convertDialog = new ConvertDialog(minecraft, parent, slot);
		convertDialog.start();
	}
	public static void process(C_5664496 minecraft, Screen parent, int slot, File input) {
		Data.getVersion().sendToLog(LogType.INFO, "Converting '" + input.toString() + "' to Alpha save format!");
		try {
			String worldName = "World" + slot;
			// TODO: detect whether the inputted world is classic, or indev.
			convert(minecraft, Version.classic, parent, worldName, input);
		} catch (Exception error) {
			error(minecraft, parent, error.getLocalizedMessage());
		}
	}
	public static void convert(C_5664496 minecraft, Version version, Screen parent, String worldName, File input) {
		if (version == Version.classic) {
			minecraft.m_6408915(new ConvertWorldInfoScreen(parent, "Converting classic world...", worldName, input));
			minecraft.m_6408915(new ConfirmScreen(minecraft.f_0723335, "Do you want to keep your player data?", "This includes your inventory, and location!", 1));
		} else if (version == Version.indev) {
			minecraft.m_6408915(new ConvertWorldInfoScreen(parent, "Converting indev world...", worldName, input));
			minecraft.m_6408915(new ConfirmScreen(minecraft.f_0723335, "Do you want to keep your player data?", "This includes your inventory, and location!", 2));
		} else {
			// This should never actually be run unless modified.
			error(minecraft, parent, "Invalid version type!");
		}
	}
	public static void result(C_5664496 minecraft, Screen parent, String worldName, File input, int id, boolean value) {
		// 1: Convert Classic Player Data
		// 2: Convert Indev Player Data
		if (id == 1) convertClassic(minecraft, parent, worldName, value, input);
		else if (id == 2) convertIndev(minecraft, parent, worldName, value, input);
	}
	public static void convertClassic(C_5664496 minecraft, Screen parent, String worldName, boolean convertPlayerData, File input) {
		// TODO: Actually Convert.
		done(minecraft, parent, worldName);
	}
	public static void convertIndev(C_5664496 minecraft, Screen parent, String worldName, boolean convertPlayerData, File input) {
		// TODO: Actually Convert.
		done(minecraft, parent, worldName);
	}
	public static void done(C_5664496 minecraft, Screen parent, String worldName) {
		minecraft.m_6408915(new SaveInfoScreen(parent, "Convert World", "Successfully converted world to '" + worldName + "'!", InfoScreen.Type.DIRT, true));
	}
	public static void error(C_5664496 minecraft, Screen parent, String error) {
		minecraft.m_6408915(new SaveInfoScreen(parent, "Error!", "Failed to convert world!" + (error.isEmpty() ? "" : " " + error), InfoScreen.Type.ERROR, true));
	}
	public enum Version {
		classic,
		indev
	}
}
