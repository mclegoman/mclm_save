/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.config.Filter;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.config.Theme;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.level.LevelFile;
import com.mclegoman.save.rtu.util.LogType;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

public class SaveLoadScreen extends Thread {
	private final boolean isLoad;
	public SaveLoadScreen(boolean isLoad) {
		this.isLoad = isLoad;
	}
	public void run() {
		if (Data.Resources.minecraft.f_0769488 != null) {
			File openInPath = new File(SaveConfig.instance.dialogDir.value());
			if (!openInPath.exists() || !openInPath.isDirectory()) openInPath = new File(SaveConfig.instance.dialogDir.getDefaultValue());
			JFileChooser fileChooser = new JFileChooser(openInPath);
			try {
				Theme theme = SaveConfig.instance.dialogTheme.value();
				if (theme.equals(Theme.system)) {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} else if (theme.equals(Theme.light)) {
					UIManager.setLookAndFeel(new FlatIntelliJLaf());
				}
				else if (theme.equals(Theme.dark)) {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
				}
				else if (theme.equals(Theme.metal)) {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				}
				fileChooser.updateUI();
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.WARN, "Error setting Save/Load dialog theme: " + error.getLocalizedMessage());
			}
			FileNameExtensionFilter[] filters = new FileNameExtensionFilter[3];
			if (isLoad) {
				filters[0] = new FileNameExtensionFilter("Minecraft level (.mclevel, .mine, .dat)", "mclevel", "mine", "dat");
				filters[1] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
				filters[2] = new FileNameExtensionFilter("Classic Minecraft level (.mine, .dat)", "mine", "dat");
			} else {
				filters[0] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
			}
			for (FileNameExtensionFilter filter : filters) fileChooser.addChoosableFileFilter(filter);
			if (isLoad) {
				if (SaveConfig.instance.loadDialogFilter.value() == Filter.minecraft) {
					fileChooser.setFileFilter(filters[0]);
				} else if (SaveConfig.instance.loadDialogFilter.value() == Filter.indev) {
					fileChooser.setFileFilter(filters[1]);
				} else if (SaveConfig.instance.loadDialogFilter.value() == Filter.classic) {
					fileChooser.setFileFilter(filters[2]);
				}
			} else {
				if (SaveConfig.instance.saveDialogFilter.value() == Filter.indev) {
					fileChooser.setFileFilter(filters[0]);
				}
			}
			Data.Resources.minecraft.m_6408915(new SaveInfoScreen(Data.Resources.minecraft.f_0723335, isLoad ? "Loading World" : "Saving World", isLoad ? "Select a world": "Select directory", InfoScreen.Type.DIRT, false));
			if ((isLoad ? fileChooser.showOpenDialog(Data.Resources.minecraft.f_0769488) : fileChooser.showSaveDialog(Data.Resources.minecraft.f_0769488)) == 0) {
				Data.Resources.minecraft.m_6408915(new SaveInfoScreen(Data.Resources.minecraft.f_0723335, isLoad ? "Loading World" : "Saving World", "Please wait...", InfoScreen.Type.DIRT, false));
				LevelFile.file = fileChooser.getSelectedFile();
				LevelFile.isLoad = isLoad;
				LevelFile.shouldProcess = true;
			} else {
				Data.Resources.minecraft.m_6408915(null);
			}
			if (LevelFile.file != null) {
				SaveConfig.instance.dialogDir.setValue(String.valueOf(LevelFile.file.toPath().getParent().toFile()));
				if (isLoad) {
					if (fileChooser.getFileFilter() == filters[0]) {
						SaveConfig.instance.loadDialogFilter.setValue(Filter.minecraft);
					} else if (fileChooser.getFileFilter() == filters[1]) {
						SaveConfig.instance.loadDialogFilter.setValue(Filter.indev);
					} else if (fileChooser.getFileFilter() == filters[2]) {
						SaveConfig.instance.loadDialogFilter.setValue(Filter.classic);
					} else {
						SaveConfig.instance.loadDialogFilter.setValue(Filter.all);
					}
				} else {
					if (fileChooser.getFileFilter() == filters[0]) {
						SaveConfig.instance.saveDialogFilter.setValue(Filter.indev);
					} else {
						SaveConfig.instance.saveDialogFilter.setValue(Filter.all);
					}
				}
			}
			interrupt();
		} else {
			Data.Resources.minecraft.m_6408915(new InfoScreen(Data.Resources.minecraft.f_0723335, "Saving/Loading World", "Canvas doesn't seem to exist.", InfoScreen.Type.ERROR, true));
		}
	}
}
