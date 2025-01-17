/*
    mclm_save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.config;

import com.mclegoman.save.common.data.Data;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.config.v2.QuiltConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class SaveConfig extends ReflectiveConfig {
	public static final SaveConfig instance = QuiltConfig.create(Data.getVersion().getID(), Data.getVersion().getID(), SaveConfig.class);
	@Comment("When set to true, the Save and Load level buttons will open the dialog directly.")
	public final TrackedValue<Boolean> skipSaveLoadScreen = this.value(true);
	@Comment("When set to true, the game will think it's always april fools'.")
	public final TrackedValue<Boolean> forceAprilFools = this.value(false);
	@Comment("When set to true, the Save and Load level buttons will open the dialog directly.")
	public final TrackedValue<String> aprilFoolsName = this.value("Terraria 3");
	@Comment("This sets the theme of the save/load dialog")
	public final TrackedValue<Theme> dialogTheme = this.value(Theme.system);
	@Comment("This sets where the save/load dialog opens")
	public final TrackedValue<String> dialogDir = this.value(QuiltLoader.getGameDir().toString());
	@Comment("This sets the load dialog filter.")
	public final TrackedValue<Filter> loadDialogFilter = this.value(Filter.minecraft);
	@Comment("This sets the save dialog filter.")
	public final TrackedValue<Filter> saveDialogFilter = this.value(Filter.indev);
	@Comment("This sets weather or not the player should be converted from a classic save.")
	public final TrackedValue<Boolean> convertClassicPlayer = this.value(true);
	@Comment("This sets the default name of a classic world if it can't be found in the save.")
	public final TrackedValue<String> convertClassicDefaultName = this.value("A Nice World");
	@Comment("This sets the default creator of a classic world if it can't be found in the save.")
	public final TrackedValue<String> convertClassicDefaultCreator = this.value("Player");
	@Comment("This sets the default cloud color of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultCloudColor = this.value(0xFFFFFF);
	@Comment("This sets the default cloud height of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultCloudHeight = this.value(66);
	@Comment("This sets the default fog color of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultFogColor = this.value(0xFFFFFF);
	@Comment("This sets the default sky brightness of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSkyBrightness = this.value(100);
	@Comment("This sets the default sky color of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSkyColor = this.value(10079487);
	@Comment("This sets the default surrounding ground height of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSurroundingGroundHeight = this.value(23);
	@Comment("This sets the default surrounding ground type of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSurroundingGroundType = this.value(2);
	@Comment("This sets the default surrounding water height of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSurroundingWaterHeight = this.value(32);
	@Comment("This sets the default surrounding water type of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSurroundingWaterType = this.value(8);
	@Comment("This sets the default spawnX of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSpawnX = this.value(128);
	@Comment("This sets the default spawnY of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSpawnY = this.value(36);
	@Comment("This sets the default spawnZ of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultSpawnZ = this.value(128);
	@Comment("This sets the default height of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultHeight = this.value(64);
	@Comment("This sets the default length of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultLength = this.value(256);
	@Comment("This sets the default width of a classic world if it can't be found in the save.")
	public final TrackedValue<Integer> convertClassicDefaultWidth = this.value(256);
	@Comment("When set to true, the current world will be saved to %rundir%/level.mclevel")
	public final TrackedValue<Boolean> saveWorldOnExit = this.value(false);
	@Comment("When enabled, entities will be teleported to a air block.")
	public final TrackedValue<Boolean> blockPosFix = this.value(false);
	@Comment("Enables development debug.")
	public final TrackedValue<Boolean> debug = this.value(false);
	@Comment("Enables error catching logs.")
	public final TrackedValue<Boolean> logErrorCatching = this.value(false);
	@Comment("Sets how often the game is automatically saved in ticks. (20 ticks = 1 second).")
	public final TrackedValue<Long> autoSaveTicks = this.value(1200L);
	@Comment("Sets the proxy server. (Don't put http(s)://!)")
	public final TrackedValue<String> proxyUrl = this.value("betacraft.uk");
	@Comment("Sets the proxy server port.")
	public final TrackedValue<Integer> proxyPort = this.value(0);
}
