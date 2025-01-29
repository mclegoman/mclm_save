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

public final class NbtFloat extends NbtElement {
	public float tag;

	public NbtFloat() {
	}

	public NbtFloat(float f) {
		this.tag = f;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeFloat(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readFloat();
	}

	public byte getType() {
		return 5;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}
