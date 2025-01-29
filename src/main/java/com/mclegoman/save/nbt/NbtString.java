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
import java.nio.charset.StandardCharsets;

public final class NbtString extends NbtElement {
	public String tag;

	public NbtString() {
	}

	public NbtString(String string) {
		this.tag = string;
	}

	void serialize(DataOutput dataOutput) throws IOException {
		byte[] var2 = this.tag.getBytes(StandardCharsets.UTF_8);
		dataOutput.writeShort(var2.length);
		dataOutput.write(var2);
	}

	void deserialize(DataInput dataInput) throws IOException {
		byte[] var2 = new byte[dataInput.readShort()];
		dataInput.readFully(var2);
		this.tag = new String(var2, StandardCharsets.UTF_8);
	}

	public byte getType() {
		return 8;
	}

	public String toString() {
		return this.tag;
	}
}
