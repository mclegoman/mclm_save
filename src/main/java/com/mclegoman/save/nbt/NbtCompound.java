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
import java.util.HashMap;
import java.util.Map;

public final class NbtCompound extends NbtElement {
	private final Map<String, Object> elements = new HashMap<>();

	public Map<String, Object> getElements() {
		return this.elements;
	}

	public NbtCompound() {
	}

	void serialize(DataOutput dataOutput) throws IOException {

		for (Object tag : this.elements.values()) {
			NbtElement.serialize((NbtElement) tag, dataOutput);
		}

		dataOutput.writeByte(0);
	}

	void deserialize(DataInput dataInput) throws IOException {
		this.elements.clear();

		NbtElement nbtElement;
		while((nbtElement = NbtElement.input(dataInput)).getType() != 0) {
			this.elements.put(nbtElement.getName(), nbtElement);
		}

	}

	public byte getType() {
		return 10;
	}

	public void addNbt(String string, NbtElement nbtElement) {
		this.elements.put(string, nbtElement.setName(string));
	}

	public void m_9599287(String string, byte b) {
		this.elements.put(string, (new NbtByte(b)).setName(string));
	}

	public void m_4087969(String string, short s) {
		this.elements.put(string, (new NbtShort(s)).setName(string));
	}

	public void m_2604386(String string, int i) {
		this.elements.put(string, (new NbtInt(i)).setName(string));
	}

	public void m_7606620(String string, long l) {
		this.elements.put(string, (new NbtLong(l)).setName(string));
	}

	public void m_0744548(String string, float f) {
		this.elements.put(string, (new NbtFloat(f)).setName(string));
	}

	public void m_3881827(String string, String string2) {
		this.elements.put(string, (new NbtString(string2)).setName(string));
	}

	public void m_2915076(String string, byte[] bs) {
		this.elements.put(string, (new NbtByteArray(bs)).setName(string));
	}

	public void m_0738578(String string, NbtCompound nbtCompound) {
		this.elements.put(string, nbtCompound.setName(string));
	}

	public byte getByte(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtByte)this.elements.get(string)).tag;
	}

	public short getShort(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtShort)this.elements.get(string)).tag;
	}

	public int getInt(String string) {
		return !this.elements.containsKey(string) ? 0 : ((NbtInt)this.elements.get(string)).tag;
	}

	public long m_6044735(String string) {
		return !this.elements.containsKey(string) ? 0L : ((NbtLong)this.elements.get(string)).tag;
	}

	public float m_0000382(String string) {
		return !this.elements.containsKey(string) ? 0.0F : ((NbtFloat)this.elements.get(string)).tag;
	}

	public String getString(String string) {
		return !this.elements.containsKey(string) ? "" : ((NbtString)this.elements.get(string)).tag;
	}

	public byte[] m_5601145(String string) {
		return !this.elements.containsKey(string) ? new byte[0] : ((NbtByteArray)this.elements.get(string)).tag;
	}

	public NbtCompound getNbt(String string) {
		return !this.elements.containsKey(string) ? new NbtCompound() : (NbtCompound)this.elements.get(string);
	}

	public NbtList getList(String string) {
		return !this.elements.containsKey(string) ? new NbtList() : (NbtList)this.elements.get(string);
	}

	public String toString() {
		return this.elements.size() + " entries";
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
}
