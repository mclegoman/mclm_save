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

public final class NbtByteArray extends NbtElement {
	public byte[] tag;

	public NbtByteArray() {
	}

	public NbtByteArray(byte[] bs) {
		this.tag = bs;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		dataOutput.writeInt(this.tag.length);
		dataOutput.write(this.tag);
	}

	void deserialize(DataInput dataInput) throws IOException {
		int var2 = dataInput.readInt();
		this.tag = new byte[var2];
		dataInput.readFully(this.tag);
	}

	public byte getType() {
		return 7;
	}

	public String toString() {
		return "[" + this.tag.length + " bytes]";
	}
}
