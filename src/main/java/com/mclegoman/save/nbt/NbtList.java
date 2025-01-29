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
import java.util.ArrayList;
import java.util.List;

public final class NbtList extends NbtElement {
	private List<NbtElement> nbtElements = new ArrayList<>();
	private byte f_1918247;

	public NbtList() {
	}

	void serialize(DataOutput dataOutput) throws IOException {
		if (this.nbtElements.size() > 0) {
			this.f_1918247 = this.nbtElements.get(0).getType();
		} else {
			this.f_1918247 = 1;
		}

		dataOutput.writeByte(this.f_1918247);
		dataOutput.writeInt(this.nbtElements.size());

		for(int var2 = 0; var2 < this.nbtElements.size(); ++var2) {
			this.nbtElements.get(var2).serialize(dataOutput);
		}

	}

	void deserialize(DataInput dataInput) throws IOException {
		this.f_1918247 = dataInput.readByte();
		int var2 = dataInput.readInt();
		this.nbtElements = new ArrayList<>();

		for(int var3 = 0; var3 < var2; ++var3) {
			NbtElement var4 = NbtElement.get(this.f_1918247);
			var4.deserialize(dataInput);
			this.nbtElements.add(var4);
		}

	}

	public byte getType() {
		return 9;
	}

	public String toString() {
		StringBuilder var10000 = (new StringBuilder()).append(this.nbtElements.size()).append(" entries of type ");
		String var10001;
		switch (this.f_1918247) {
			case 0:
				var10001 = "TAG_End";
				break;
			case 1:
				var10001 = "TAG_Byte";
				break;
			case 2:
				var10001 = "TAG_Short";
				break;
			case 3:
				var10001 = "TAG_Int";
				break;
			case 4:
				var10001 = "TAG_Long";
				break;
			case 5:
				var10001 = "TAG_Float";
				break;
			case 6:
				var10001 = "TAG_Double";
				break;
			case 7:
				var10001 = "TAG_Byte_Array";
				break;
			case 8:
				var10001 = "TAG_String";
				break;
			case 9:
				var10001 = "TAG_List";
				break;
			case 10:
				var10001 = "TAG_Compound";
				break;
			default:
				var10001 = "UNKNOWN";
		}

		return var10000.append(var10001).toString();
	}

	public void addNbt(NbtElement nbtElement) {
		this.f_1918247 = nbtElement.getType();
		this.nbtElements.add(nbtElement);
	}

	public NbtElement getNbt(int i) {
		return this.nbtElements.get(i);
	}

	public int index() {
		return this.nbtElements.size();
	}
}
