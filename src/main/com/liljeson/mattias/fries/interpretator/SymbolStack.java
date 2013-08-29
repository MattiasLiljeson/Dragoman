package com.liljeson.mattias.fries.interpretator;

import java.util.List;

import com.liljeson.mattias.fries.shared.Variable;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.utils.DeluxeArray;

public class SymbolStack {

	DeluxeArray<Variable> m_symbols;

	void addSymbol(Symbol p_sym, int p_level) {
		m_symbols.push(new Variable(p_sym, p_level));
	}

	void popLevel(int p_level) {
		List<Variable> pairs = m_symbols.arr;
		for (int i = pairs.size() - 1; i >= 0; i--) {
			if (pairs.get(i).m_val >= p_level) {
				pairs.remove(i);
			}
		}
	}
}
