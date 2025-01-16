/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.client;

import com.mclegoman.mclm_save.client.util.StringHelper;
import com.mclegoman.mclm_save.rtu.util.LogType;
import com.mclegoman.mclm_save.api.data.Resources;
import com.mclegoman.mclm_save.api.entrypoint.SaveModInit;
import com.mclegoman.mclm_save.api.event.TickEvents;
import com.mclegoman.mclm_save.api.gui.InfoScreen;
import com.mclegoman.mclm_save.client.april_fools.AprilFools;
import com.mclegoman.mclm_save.client.gui.SaveInfoScreen;
import com.mclegoman.mclm_save.common.data.Data;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.List;

@ClientOnly
public class Save implements SaveModInit {
	public void init(ModContainer mod) {
		StringHelper.init();
		AprilFools.init();
		List<String> bootMessages = new ArrayList<>();
		if (Data.version.isDevelopmentBuild()) bootMessages.add("This is a developer build of Save. Expect Bugs!");
		String useLegacyMergeSort = System.getProperty("java.util.Arrays.useLegacyMergeSort");
		if (useLegacyMergeSort == null || useLegacyMergeSort.equalsIgnoreCase("false")) bootMessages.add("Please enable \"java.util.Arrays.useLegacyMergeSort\" for stability.");
		if (!bootMessages.isEmpty()) {
			if (Resources.minecraft != null) Resources.minecraft.m_6408915(new SaveInfoScreen(Resources.minecraft.f_0723335, Data.version.getName(), bootMessages, InfoScreen.Type.DIRT, true));
			else for (String message : bootMessages) Data.version.sendToLog(LogType.INFO, message);
		}
		TickEvents.register(TickEvents.Tick.END, () -> {
		});
	}
}