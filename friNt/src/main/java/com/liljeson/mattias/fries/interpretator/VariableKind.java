package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.shared.SymbolKinds;

public enum VariableKind {
	NONE, SIMPLE, ARRAY;

	public static VariableKind fromInt(int p_typeCode) {
		switch (p_typeCode) {
		case SymbolKinds.NONE:
			return NONE;
		case SymbolKinds.FUNC_VAL:
		case SymbolKinds.SIMPLE:
			return SIMPLE;
		case SymbolKinds.ARRAY:
			return ARRAY;
		default:
			return null;
		}
	}
}