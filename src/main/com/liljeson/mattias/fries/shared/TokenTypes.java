package com.liljeson.mattias.fries.shared;

public class TokenTypes {
	public static final int NONE = 0;
	public static final int ID = 1;
	public static final int INT = 2;
	public static final int REAL = 3;
	public static final int TEXT = 4;
	public static final int OP = 5;
	public static final int FAIL = 6;
	public static final int LINE = 7;
	public static final int CALL = 10;

	public static int fromInt(int p_type) {
		return p_type;
	}
}
