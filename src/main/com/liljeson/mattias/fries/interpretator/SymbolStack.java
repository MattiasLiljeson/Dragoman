package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.utils.DeluxeArray;

public class SymbolStack {

	DeluxeArray<StackFrame> m_frames = new DeluxeArray<>();

	// TODO: change param to param list if many params are to be supported
	void push(Block p_block, Variable p_param) {
		m_frames.push(new StackFrame(m_frames.top(), p_block, p_param));
	}

	StackFrame pop() {
		return m_frames.pop();
	}

	StackFrame top() {
		return m_frames.top();
	}

	void setVar(Variable p_var) {
		m_frames.top().setVar(p_var);
	}

	Variable getVar(int p_id) {
		return m_frames.top().getVar(p_id);
	}

	Symbol getSymbol(int p_id) {
		return m_frames.top().getSymbol(p_id);
	}

	public void resetPc() {
		m_frames.top().resetPc();
	}

	public Token look() {
		if (m_frames.top() != null) {
			return m_frames.top().look();
		} else {
			return null;
		}
	}

	public Token getNextToken() {
		if (m_frames.top() != null) {
			return m_frames.top().getNextToken();
		} else {
			return null;
		}
	}
}
