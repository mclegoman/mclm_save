/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.fun;

import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModContributor;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AprilFools {
	public static List<String> playerNames = new ArrayList<>();
	public static int nameChance;
	public static int name;
	public static int attack;
	public static int defence;
	public static int speed;
	public static void init() {
		Optional<ModContainer> modContainer = Data.getVersion().getModContainer();
		if (modContainer.isPresent()) for (ModContributor contributor : modContainer.get().metadata().contributors()) playerNames.add(contributor.name());
		else playerNames.add("PLAYER NAME");
	}
	public static String getPlayerName() {
		if (nameChance == 1200) return "Herobrine";
		else return playerNames.get(name);
	}
	public static boolean isAprilFools() {
		LocalDate date = LocalDate.now();
		return (date.getMonth() == Month.APRIL && date.getDayOfMonth() == 1) || SaveConfig.instance.forceAprilFools.value();
	}
	public static String getVersionString(String fallback) {
		return isAprilFools() ? "Terraria 3" : fallback;
	}
}
