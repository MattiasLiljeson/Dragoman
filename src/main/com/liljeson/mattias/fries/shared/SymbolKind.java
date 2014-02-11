package com.liljeson.mattias.fries.shared;

public class SymbolKind {
	public static final int NO_INFO = -1;

	public int m_kind;
	public int m_info1;
	public int m_info2;
	public int m_info3;

	// public Kind(int p_kind) {
	// m_kind = p_kind;
	// m_info1 = NO_INFO;
	// m_info2 = NO_INFO;
	// m_info3 = NO_INFO;
	// }

	public SymbolKind(int p_kind, int p_info1, int p_info2, int p_info3) {
		m_kind = p_kind;
		m_info1 = p_info1;
		m_info2 = p_info2;
		m_info3 = p_info3;
	}
}
