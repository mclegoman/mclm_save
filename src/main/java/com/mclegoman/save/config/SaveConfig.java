/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.config;

import com.mclegoman.save.data.Data;
import com.mclegoman.save.util.SaveHelper;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;
import org.quiltmc.loader.api.config.v2.QuiltConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class SaveConfig extends ReflectiveConfig {
	public static final SaveConfig instance = QuiltConfig.create(Data.getVersion().getID(), Data.getVersion().getID(), SaveConfig.class);
	@Comment("When enabled, holding down a keyboard key will repeat the event.")
	public final TrackedValue<Boolean> allowKeyboardRepeatEvents = this.value(true);
	@Comment("When set to true, the game will think it's always april fools'.")
	public final TrackedValue<Boolean> forceAprilFools = this.value(false);
	@Comment("When set to true, and B is held down, an overlay is rendered to the screen.")
	public final TrackedValue<Boolean> allowBossMode = this.value(true);
	@Comment("When set to true, the Save and Load level buttons will open the dialog directly.")
	public final TrackedValue<String> aprilFoolsName = this.value("Terraria 3");
	@Comment("This sets the theme of the save/load dialog")
	public final TrackedValue<Theme> dialogTheme = this.value(Theme.system);
	public final TrackedValue<Boolean> starterItems = this.value(true);
	@Comment("Enables development debug.")
	public final TrackedValue<Boolean> debug = this.value(false);
	@Comment("Fixes flower generation to prevent flowers from spawning on blocks they can't survive on.")
	public final TrackedValue<Boolean> fixFlowerGen = this.value(true);
	@Comment("Disables ItemEntity with flower item stack. (This is meant as an alternative to fixFlowerGen)")
	public final TrackedValue<Boolean> shouldDisableFlowerItems = this.value(false);
	public final TrackedValue<ValueList<String>> flowerItems = this.list("", "37", "38");
	@Comment("When enabled, replaces populateChunk with inf-327's populateChunk which removes caves.")
	public final TrackedValue<Boolean> disableCaves = this.value(true);
	@Comment("Sets how often the game is automatically saved in ticks. (20 ticks = 1 second).")
	public final TrackedValue<Long> autoSaveTicks = this.value(1200L);
	@Comment("This sets where the save/load dialog opens")
	public final TrackedValue<String> dialogDir = this.value(SaveHelper.getSavesDir().toString());
	@Comment("This sets the load dialog filter.")
	public final TrackedValue<Filter> convertDialogFilter = this.value(Filter.minecraft);
	@Comment("Sets the proxy server. (Don't put http(s)://!)")
	public final TrackedValue<String> proxyUrl = this.value("betacraft.uk");
	@Comment("Sets the proxy server port.")
	public final TrackedValue<Integer> proxyPort = this.value(0);
	@Comment("When enabled, a converted world will load after conversion.")
	public final TrackedValue<Boolean> shouldLoadAfterConvert = this.value(true);
	@Comment("Settings for Classic/Indev world conversion. These are considered to be advanced settings. Only adjust if you know what you're doing, doing so is at your own risk.")
	public final ConvertSettings conversionSettings = new ConvertSettings();
	public static class ConvertSettings extends Section {
		@Comment("This sets the default spawnX of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> spawnX = this.value(128);
		@Comment("This sets the default spawnY of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> spawnY = this.value(36);
		@Comment("This sets the default spawnZ of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> spawnZ = this.value(128);
		@Comment("This sets the default time of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> time = this.value(0);
		@Comment("This sets the default height of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> height = this.value(64);
		@Comment("This sets the default length of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> length = this.value(256);
		@Comment("This sets the default width of a classic/indev world if it can't be found in the save.")
		public final TrackedValue<Integer> width = this.value(256);
		@Comment("This sets the block id of whats placed below the converted world if a y offset is applied, and the block id of replaceBedrock.")
		public final TrackedValue<Integer> offsetBlockId = this.value(0);
		@Comment("When enabled, ALL converted bedrock blocks will be replaced with offsetBlockId.")
		public final TrackedValue<Boolean> replaceBedrock = this.value(false);
	}
}
