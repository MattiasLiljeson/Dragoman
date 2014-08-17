package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.shared.Symbol;

public class Variable {
	public static final int NO_ARRAY_IDX = -1;

	public class WrongTypeException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	private VariableType m_type = VariableType.VOID;
	private VariableKind m_kind = VariableKind.NONE;
	private Symbol m_sym;

	// For simple
	int m_val;

	// For Array
	int[] m_vals;
	int m_startIdx;

	public Variable(Symbol p_sym) {
		setSymbol(p_sym);
		setType(VariableType.fromInt(p_sym.m_type));
		setKind(VariableKind.fromInt(p_sym.m_kind.m_kind));

		if (m_kind == VariableKind.ARRAY) {
			int arrSize = p_sym.m_hiLimit - p_sym.m_lowLimit;
			m_vals = new int[arrSize];
			m_startIdx = p_sym.m_lowLimit;
		}
	}

	private void setKind(VariableKind p_kind) {
		m_kind = p_kind;
	}

	public int getId() {
		return getSymbol().m_id;
	}

	public void setId(int m_id) {
		this.getSymbol().m_id = m_id;
	}

	public VariableType getType() {
		return m_type;
	}

	public void setType(VariableType p_type) {
		if (p_type != null) {

			m_type = p_type;
		} else {
			int breakHere = 1;
		}
	}

	public void setInt(int p_val, int p_arrIdx) throws WrongTypeException {
		if (m_type == VariableType.INT) {
			if (m_kind == VariableKind.SIMPLE) {
				m_val = p_val;
			} else if (m_kind == VariableKind.ARRAY) {
				m_vals[p_arrIdx - m_startIdx] = p_val;
			}
		} else {
			throw new WrongTypeException();
		}
	}

	public int getInt(int p_arrIdx) throws WrongTypeException {
		if (m_type == VariableType.INT) {

			if (m_kind == VariableKind.SIMPLE) {
				return m_val;
			} else if (m_kind == VariableKind.ARRAY) {
				return m_vals[p_arrIdx - m_startIdx];
			} else {
				// FIXME: throw exception?
				return -1;
			}
		} else {
			throw new WrongTypeException();
		}
	}

	public Symbol getSymbol() {
		return m_sym;
	}

	public void setSymbol(Symbol m_sym) {
		this.m_sym = m_sym;
	}

}