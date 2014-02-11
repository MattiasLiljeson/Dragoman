package com.liljeson.mattias.fries.interpretator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.SymbolKinds;
import com.liljeson.mattias.fries.shared.Token;

class StackFrame {
	StackFrame m_dynamicFather;
	Block m_block;
	Variable m_frameVar = null;
	Map<Integer, Variable> m_variables = new HashMap<>();
	int m_pc = 0;

	public void resetPc() {
		m_pc = 0;
	}

	public Token look() {
		return m_block.getToken(m_pc);
	}

	public Token getNextToken() {
		return m_block.getToken(m_pc++);
	}

	/**
	 * 
	 * @param p_dynamicFather
	 * @param p_block
	 * @param p_frameVar
	 *            can be null
	 * @param p_params
	 *            can be null
	 */

	// TODO: change param to param list if many params are to be supported
	// TODO: use id to identify correct parameters
	StackFrame(StackFrame p_dynamicFather, Block p_block, Variable p_param) {
		m_block = p_block;
		m_dynamicFather = p_dynamicFather;
		setParameter(p_param);

		// initAllVars();
	}

	// public void initAllVars() {
	// Iterator<Map.Entry<Integer, Symbol>> it;
	// it = m_block.m_symbols.entrySet().iterator();
	// while (it.hasNext()) {
	// Map.Entry<Integer, Symbol> pair;
	// pair = (Map.Entry<Integer, Symbol>) it.next();
	// Symbol sym = pair.getValue();
	// Variable var = new Variable(sym);
	// int symId = pair.getKey();
	// var.setId(symId);
	// setVar(var);
	// }
	// }

	// FIXME: HACK: Replaces the first var that isn't funcval as if its a
	// parameter. This will work in all example P1's. Need to be changed if the
	// is going to be proper support for multiple params though
	private void setParameter(Variable p_param) {
		if (p_param != null) {
			Iterator<Map.Entry<Integer, Symbol>> it;
			it = m_block.m_symbols.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Symbol> pair;
				pair = (Map.Entry<Integer, Symbol>) it.next();
				Symbol sym = pair.getValue();
				if (sym.m_kind.m_kind != SymbolKinds.FUNC_VAL) {
					int symId = pair.getKey();
					p_param.setId(symId);
					setVar(p_param);
					break;
				}
			}
		}
	}

	// void setVariables(List<Variable> p_params) {
	// if (p_params != null) {
	// for (Variable param : p_params) {
	// if (symbolIsDefinedForThisBlock(param.getId())) {
	// m_variables.put(param.getId(), param);
	// } else {
	// // TODO: throw exception?
	// }
	// }
	// }
	// }

	Variable getVar(int p_id) {
		Variable var = m_variables.get(p_id);
		if (var == null && m_dynamicFather != null) {
			var = m_dynamicFather.getVar(p_id);
		}
		return var;
	}

	void setVar(Variable p_var) {
		if (symbolIsDeclaredForThisBlock(p_var.getId())) {
			if (p_var.getSymbol().m_kind.m_kind == SymbolKinds.FUNC_VAL) {
				m_frameVar = p_var;
			}
			m_variables.put(p_var.getId(), p_var);
		} else if (m_dynamicFather != null) {
			m_dynamicFather.setVar(p_var);
		} else {
			// TODO: throw exception?
		}
	}

	Symbol getSymbol(int p_id) {
		Symbol sym = m_block.getSymbol(p_id);
		if (sym == null && m_dynamicFather != null) {
			sym = m_dynamicFather.getSymbol(p_id);
		}
		return sym;
	}

	// void setSymbol(Symbol p_sym) {
	// if (symbolIsDefinedForThisBlock(p_sym.m_id)) {
	// m_block.m_symbols.put(p_sym.m_id, p_sym);
	// } else if (m_dynamicFather != null) {
	// m_dynamicFather.setSym(p_sym);
	// } else {
	// // TODO: throw exception?
	// }
	// }

	public boolean symbolIsDeclaredForThisBlock(int p_id) {
		return m_block.getSymbol(p_id) != null;
	}

	public Variable getFrameVar() {
		return m_frameVar;
	}

	void setFrameVar(Variable m_frameVar) {
		if (m_frameVar != null)
			this.m_frameVar = m_frameVar;
	}

	public int getParentBlockId() {
		return m_block.getParentBlockId();
	}

	public boolean varExistsInThisFrame(int p_symId) {
		Variable var = m_variables.get(p_symId);
		if (var != null) {
			return true;
		} else {
			return false;
		}
	}

}