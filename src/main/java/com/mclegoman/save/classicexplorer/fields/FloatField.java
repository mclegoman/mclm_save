/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

package com.mclegoman.save.classicexplorer.fields;

import java.io.IOException;

import com.mclegoman.save.classicexplorer.io.Reader;

/** Field for a float primitive */
public class FloatField extends Field {
	float fieldValue;
	
	public FloatField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Float) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readFloat();
	}
	
	@Override
	public FloatField clone() {
		return new FloatField(fieldName);
	}
}
