package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.shared.SymbolTypes;

public enum VariableType {
	VOID, INT, REAL, CHAR, STRING;

	public static VariableType fromInt(int p_typeCode) {
		switch (p_typeCode) {
		case SymbolTypes.VOID:
			return VOID;
		case SymbolTypes.INT:
			return INT;
		case SymbolTypes.REAL:
			return REAL;
		case SymbolTypes.CHAR:
			return CHAR;
		case SymbolTypes.STRING:
			return STRING;
		default:
			return null;
		}
	}
}