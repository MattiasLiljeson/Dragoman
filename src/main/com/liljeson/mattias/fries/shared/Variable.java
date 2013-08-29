package com.liljeson.mattias.fries.shared;

public class Variable {
	public Symbol m_sym;
	public int m_val;

	public Variable(Symbol p_sym, int p_level) {
		m_sym = p_sym;
		m_val = p_level;
	}
}