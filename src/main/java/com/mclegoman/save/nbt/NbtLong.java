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

public final class NbtLong extends NbtElement {
	public long tag;

	public NbtLong() {
	}

	public NbtLong(long l) {
		this.tag = l;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeLong(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.tag = dataInput.readLong();
	}

	public byte getType() {
		return 4;
	}

	public String toString() {
		return String.valueOf(this.tag);
	}
}

