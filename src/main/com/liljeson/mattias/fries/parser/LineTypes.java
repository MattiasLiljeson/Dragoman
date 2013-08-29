package com.liljeson.mattias.fries.parser;

public class LineTypes {
	public static final int COMMENT = -1; // Can be any number
	public static final int NONE = 0;
	public static final int BLOCK_CNT = 1;
	public static final int CODE = 3;
	public static final int BLOCK_DECL = 5;
	public static final int VAR_DECL = 10;
	public static final int ARR_DECL = 12;

	public static int fromLineLength(int length) {
		// FIXME: check for correct length
		return length;
	}
}