package com.liljeson.mattias.fries.shared;

public class Symbol {
	public int m_id;
	public int m_type;
	public SymbolKind m_kind = null;
	public int m_relAdr;
	public String m_extName;
	public int m_index = -1;
	public int m_lowLimit = -1;
	public int m_hiLimit = -1;
	public String m_name;

	public Symbol() {
		m_kind = new SymbolKind(SymbolKind.NO_INFO, SymbolKind.NO_INFO,
				SymbolKind.NO_INFO,
				SymbolKind.NO_INFO);
	}

	public Symbol(int p_type, int p_kind) {
		this();
		m_type = p_type;
		m_kind.m_kind = p_kind;
	}
	// public Symbol(Kind p_kind) {
	// m_kind = p_kind;
	// }
}
