package com.liljeson.mattias.fries.shared;

import java.util.ArrayList;
import java.util.List;

public class Block {
	// static father?
	// variable declarations?

	public int m_blockNr = -1;
	// public Block m_parent = null; // Static father
	public int m_parent = -1; // Static father
	public List<Variable> m_symbolPairs = new ArrayList<>();
	public List<Token> m_tokens = new ArrayList<>();

	private int m_pc = 0;

	public void resetPc() {
		m_pc = 0;
	}

	public Token getToken(int p_idx) {
		return m_tokens.get(p_idx);
	}

	public Token getNextToken() {
		return m_tokens.get(m_pc++);
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

	public Variable getVariable(int p_id) {
		for (Variable var : m_symbolPairs) {
			if (var.m_sym.m_id == p_id) {
				return var;
			}
		}
		return null;
	}

	public Symbol getSym(int p_id) {
		Variable var = getVariable(p_id);
		if (var != null) {
			return var.m_sym;
		} else {
			return null;
		}
	}

	public boolean setSym(int p_id, int p_val) {
		Variable var = getVariable(p_id);
		if (var != null) {
			var.m_val = p_val;
			return true;
		} else {
			return false;
		}
	}

	public int getValue(int p_id) {
		Variable var = getVariable(p_id);
		if (var != null) {
			return var.m_val;
		} else {
			return -1;
		}
	}
}
