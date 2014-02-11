package com.liljeson.mattias.fries.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Block {
	public int m_blockId = -1;
	public int m_parentBlockId = -1; // Static father
	public Map<Integer, Symbol> m_symbols = new HashMap<>();
	public List<Token> m_tokens = new ArrayList<>();

	public Token getToken(int p_idx) {
		if (0 <= p_idx && p_idx < m_tokens.size()) {
			return m_tokens.get(p_idx);
		} else {
			return null;
		}
	}

	public void prepForUse() {
		addBeginIfNeeded();
		addEndIfNeeded();
	}

	private void addEndIfNeeded() {
		Token last = m_tokens.get(m_tokens.size() - 1);
		if (!last.equals(Token.END)) {
			Token token = new Token(TokenTypes.ID, Keywords.ID_END,
					"endAddedByPrepForUse");
			m_tokens.add(token);
		}
	}

	private void addBeginIfNeeded() {
		Token first = m_tokens.get(0);
		if (!first.equals(Token.BEGIN)) {
			Token token = new Token(TokenTypes.ID, Keywords.ID_BEGIN,
					"beginAddedByPrepForUse");
			m_tokens.add(0, token);
		}
	}

	public void setSymbol(Symbol p_sym) {
		m_symbols.put(p_sym.m_id, p_sym);
	}

	public Symbol getSymbol(int p_id) {
		return m_symbols.get(p_id);
	}

	public int getParentBlockId() {
		return m_parentBlockId;
	}

	// public void setParent(int m_parent) {
	// this.m_parent = m_parent;
	// }
}
