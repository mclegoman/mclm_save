/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save;

import com.mclegoman.save.fun.BossMode;
import com.mclegoman.save.util.StringHelper;
import com.mclegoman.save.util.SaveHelper;
import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.api.entrypoint.SaveModInit;
import com.mclegoman.save.api.gui.screen.InfoScreen;
import com.mclegoman.save.fun.AprilFools;
import com.mclegoman.save.gui.screen.SaveInfoScreen;
import com.mclegoman.save.data.Data;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.List;

@ClientOnly
public class Save implements SaveModInit {
	public void init(ModContainer mod) {
		SaveHelper.init();
		StringHelper.init();
		AprilFools.init();
		BossMode.init();
		List<String> bootMessages = new ArrayList<>();
		if (Data.getVersion().isDevelopmentBuild()) bootMessages.add("This is a developer build of [save]. Expect Bugs!");
		String useLegacyMergeSort = System.getProperty("java.util.Arrays.useLegacyMergeSort");
		if (useLegacyMergeSort == null || useLegacyMergeSort.equalsIgnoreCase("false")) bootMessages.add("Please enable \"-Djava.util.Arrays.useLegacyMergeSort\" for stability.");
		if (!bootMessages.isEmpty()) {
			if (Data.Resources.minecraft != null) Data.Resources.minecraft.m_6408915(new SaveInfoScreen(Data.Resources.minecraft.f_0723335, "[save]", bootMessages, InfoScreen.Type.DIRT, true));
			else for (String message : bootMessages) Data.getVersion().sendToLog(LogType.INFO, message);
		}
	}
}