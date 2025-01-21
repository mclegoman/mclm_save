/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.convert;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.config.Filter;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.config.Theme;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.screen.Screen;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

public class ConvertDialog extends Thread {
	private final C_5664496 minecraft;
	private final Screen parent;
	private final int slot;
	public ConvertDialog(C_5664496 minecraft, Screen parent, int slot) {
		this.minecraft = minecraft;
		this.parent = parent;
		this.slot = slot;
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
				Data.getVersion().sendToLog(LogType.WARN, "Error setting convert dialog theme: " + error.getLocalizedMessage());
			}
			FileNameExtensionFilter[] filters = new FileNameExtensionFilter[3];
			filters[0] = new FileNameExtensionFilter("Minecraft level (.mclevel, .mine, .dat)", "mclevel", "mine", "dat");
			filters[1] = new FileNameExtensionFilter("Indev Minecraft level (.mclevel)", "mclevel");
			filters[2] = new FileNameExtensionFilter("Classic Minecraft level (.mine, .dat)", "mine", "dat");
			for (FileNameExtensionFilter filter : filters) fileChooser.addChoosableFileFilter(filter);
			if (SaveConfig.instance.convertDialogFilter.value() == Filter.minecraft) {
				fileChooser.setFileFilter(filters[0]);
			} else if (SaveConfig.instance.convertDialogFilter.value() == Filter.indev) {
				fileChooser.setFileFilter(filters[1]);
			} else if (SaveConfig.instance.convertDialogFilter.value() == Filter.classic) {
				fileChooser.setFileFilter(filters[2]);
			}
			int dialog = fileChooser.showOpenDialog(Data.Resources.minecraft.f_0769488);
			if (dialog == JFileChooser.APPROVE_OPTION) {
				SaveConfig.instance.dialogDir.setValue(String.valueOf(fileChooser.getSelectedFile().getParent()));
				if (fileChooser.getFileFilter() == filters[0]) {
					SaveConfig.instance.convertDialogFilter.setValue(Filter.minecraft);
				} else if (fileChooser.getFileFilter() == filters[1]) {
					SaveConfig.instance.convertDialogFilter.setValue(Filter.indev);
				} else if (fileChooser.getFileFilter() == filters[2]) {
					SaveConfig.instance.convertDialogFilter.setValue(Filter.classic);
				} else {
					SaveConfig.instance.convertDialogFilter.setValue(Filter.all);
				}
				SaveConfig.instance.save();
				Convert.process(this.minecraft, this.parent, this.slot, fileChooser.getSelectedFile());
			} else if (dialog == JFileChooser.CANCEL_OPTION) {
				SaveConfig.instance.save();
				Data.getVersion().sendToLog(LogType.INFO, "World conversion was cancelled.");
				this.minecraft.m_6408915(this.parent);
			} else {
				this.minecraft.m_6408915(new SaveInfoScreen(this.parent, "Error!", "An error occurred.", InfoScreen.Type.ERROR, true));
			}
			interrupt();
		} else {
			this.minecraft.m_6408915(new SaveInfoScreen(null, "Error!", "Canvas doesn't seem to exist.", InfoScreen.Type.ERROR, true));
		}
	}
}
