/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

package com.mclegoman.save.classicexplorer.fields;

import com.mclegoman.save.classicexplorer.io.Reader;

import java.io.IOException;

/** Field for a long primitive */
public class LongField extends Field {
	long fieldValue;
	
	public LongField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Long) fieldValue;
	}

	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readLong();
	}

	@Override
	public LongField clone() {
		return new LongField(fieldName);
	}
}
