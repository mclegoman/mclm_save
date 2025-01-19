/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.entity;

import com.mclegoman.save.entity.SaveModEntity;
import net.minecraft.entity.living.mob.passive.animal.PigEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin implements SaveModEntity {
	public String save$id() {
		return "Pig";
	}
}
