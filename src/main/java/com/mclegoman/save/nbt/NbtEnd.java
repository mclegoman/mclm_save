/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public final class NbtEnd extends NbtElement {
	public NbtEnd() {
	}

	void deserialize(DataInput dataInput) {
	}

	void serialize(DataOutput dataOutput) {
	}

	public byte getType() {
		return 0;
	}

	public String toString() {
		return "END";
	}
}
