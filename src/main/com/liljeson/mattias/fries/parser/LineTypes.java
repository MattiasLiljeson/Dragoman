package com.liljeson.mattias.fries.parser;

public class LineTypes {
	public static final int BREAKPOINT = -3; // Special case of comment
	public static final int COMMENT = -2; // Can be any number

	public static final int NONE = 0;
	public static final int BLOCK_CNT = 1;
	public static final int CODE = 3;
	public static final int BLOCK_DECL = 5;
	public static final int VAR_DECL = 10;
	public static final int ARR_DECL = 12;

	public static int fromLineLength(int length) {
		switch (length) {
		case BLOCK_CNT:
			return BLOCK_CNT;
		case CODE:
			return CODE;
		case BLOCK_DECL:
			return BLOCK_DECL;
		case VAR_DECL:
			return VAR_DECL;
		case ARR_DECL:
			return ARR_DECL;
		default:
			return NONE;
		}
	}
}