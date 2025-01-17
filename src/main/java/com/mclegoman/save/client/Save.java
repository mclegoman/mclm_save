/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.client;

import com.mclegoman.save.api.util.StringHelper;
import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.api.data.Resources;
import com.mclegoman.save.api.entrypoint.SaveModInit;
import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.client.april_fools.AprilFools;
import com.mclegoman.save.client.gui.screen.SaveInfoScreen;
import com.mclegoman.save.common.data.Data;
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
		if (Data.getVersion().isDevelopmentBuild()) bootMessages.add("This is a developer build of [save]. Expect Bugs!");
		String useLegacyMergeSort = System.getProperty("java.util.Arrays.useLegacyMergeSort");
		if (useLegacyMergeSort == null || useLegacyMergeSort.equalsIgnoreCase("false")) bootMessages.add("Please enable \"java.util.Arrays.useLegacyMergeSort\" for stability.");
		if (!bootMessages.isEmpty()) {
			if (Resources.minecraft != null) Resources.minecraft.m_6408915(new SaveInfoScreen(Resources.minecraft.f_0723335, "[save]", bootMessages, InfoScreen.Type.DIRT, true));
			else for (String message : bootMessages) Data.getVersion().sendToLog(LogType.INFO, message);
		}
		TickEvents.register(TickEvents.Tick.END, Data.getVersion().getID(), (client) -> {
		});
	}
}