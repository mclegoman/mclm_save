/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NbtCompoundStream {
	public static NbtCompound toNbtCompound(InputStream inputStream) throws IOException {
		DataInputStream inputStream1 = new DataInputStream(new GZIPInputStream(inputStream));

		NbtCompound var5;
		try {
			NbtElement var1;
			if (!((var1 = NbtElement.input(inputStream1)) instanceof NbtCompound)) {
				throw new IOException("Root tag must be a named compound tag");
			}

			var5 = (NbtCompound)var1;
		} finally {
			inputStream1.close();
		}

		return var5;
	}
}
