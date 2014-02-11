package com.liljeson.mattias.fries.interpretator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.liljeson.mattias.fries.interpretator.Variable.WrongTypeException;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.SymbolKinds;
import com.liljeson.mattias.fries.shared.SymbolTypes;

public class VariableTest {

	// @Before
	// public void setUp() throws Exception {
	// }

	@Test
	public void testSimple() throws WrongTypeException {
		// fail("Not yet implemented");
		Symbol sym = new Symbol(SymbolTypes.INT, SymbolKinds.SIMPLE);
		Variable var = new Variable(sym);
		var.setInt(42, Variable.NO_ARRAY_IDX);
		assertEquals("", 42, var.getInt(Variable.NO_ARRAY_IDX));
	}

	@Test
	public void testArr() throws WrongTypeException {
		// fail("Not yet implemented");
		Symbol sym = new Symbol(SymbolTypes.INT, SymbolKinds.ARRAY);
		sym.m_lowLimit = 1;
		sym.m_index = 1;
		sym.m_hiLimit = 4;
		Variable var = new Variable(sym);
		var.setInt(42, 1);
		assertEquals("", 42, var.getInt(1));
		var.setInt(43, 2);
		assertEquals("", 43, var.getInt(2));
		var.setInt(44, 3);
		assertEquals("", 44, var.getInt(3));

		var.setInt(32, 1);
		var.setInt(33, 2);
		var.setInt(34, 3);
		assertEquals("", 32, var.getInt(1));
		assertEquals("", 33, var.getInt(2));
		assertEquals("", 34, var.getInt(3));

		var.setInt(24, 3);
		var.setInt(23, 2);
		var.setInt(22, 1);
		assertEquals("", 24, var.getInt(3));
		assertEquals("", 23, var.getInt(2));
		assertEquals("", 22, var.getInt(1));
	}
}
