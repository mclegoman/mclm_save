/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.level;

import com.mclegoman.save.api.level.SaveModWorldChunk;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ClientOnly
@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin implements SaveModWorldChunk {
	@Inject(method = "populateSkylight", at = @At("TAIL"))
	private void save$populateSkylight(CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "checkSkylight", at = @At("TAIL"))
	private void save$checkSkylight(CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "resetLightAt", at = @At("TAIL"))
	private void save$resetLightAt(CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "m_4332228", at = @At("TAIL"))
	private void save$m_4332228(int j, int k, int l, int par4, CallbackInfoReturnable<Boolean> cir) {
		save$setDirty(true);
	}
	@Inject(method = "m_3875116", at = @At("HEAD"))
	private void save$m_3875116(int j, int k, int l, int par4, CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "m_3334061", at = @At("HEAD"))
	private void save$m_3334061(LightType i, int j, int k, int l, int par5, CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "setBlockEntityAt", at = @At("HEAD"))
	private void save$setBlockEntityAt(int j, int k, int blockEntity, BlockEntity par4, CallbackInfo ci) {
		save$setDirty(true);
	}
	@Inject(method = "m_3088960", at = @At("HEAD"))
	private void save$m_3088960(int j, int k, int par3, CallbackInfo ci) {
		save$setDirty(true);
	}
	public boolean save$getDirty() {
		return this.save$dirty;
	}
	public void save$setDirty(boolean dirty) {
		this.save$dirty = dirty;
	}
	@Unique
	private boolean save$dirty;
}
