/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.fun;

import com.mclegoman.save.util.StringHelper;
import com.mclegoman.save.config.SaveConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.time.LocalDate;
import java.time.Month;

@ClientOnly
public class AprilFools {
	public static void init() {
	}
	public static boolean isAprilFools() {
		LocalDate date = LocalDate.now();
		return (date.getMonth() == Month.APRIL && date.getDayOfMonth() == 1) || SaveConfig.instance.forceAprilFools.value();
	}
	public static String getVersionString(String fallback) {
		return StringHelper.getFormattedString(isAprilFools() ? SaveConfig.instance.aprilFoolsName.value() : fallback);
	}
}
