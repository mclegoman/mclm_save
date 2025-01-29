/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NbtDouble extends NbtElement {
	private double tag;

	public NbtDouble() {
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeDouble(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readDouble();
	}

	public byte getType() {
		return 6;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
