package com.liljeson.mattias.fries.interpretator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.liljeson.mattias.fries.parser.P1Reader;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

public class RDITest {

	final LogLady m_logLady = new LogLady(false, LogLevels.INFO);
	final CompLogger m_compLady = new CompLogger(m_logLady);
	final RDI m_rdi = new RDI(m_logLady, m_compLady);
	Program m_program = null;

	// @Before
	// public void setUp() throws Exception {
	// }

	@Test
	public void testRunTst0() {
		m_program = P1Reader.readProgram(RDITestData.TST0);
		m_program.m_name = "tst0";
		m_rdi.run(m_program, false);
	}

	@Test
	public void testRunTst1() {
		m_program = P1Reader.readProgram(RDITestData.TST1);
		m_program.m_name = "tst1";
		m_rdi.run(m_program, false);
		assertEquals("", Integer.valueOf(2), m_rdi.m_vars.get(19));
	}

	@Test
	public void testRunTst2() {
		m_program = P1Reader.readProgram(RDITestData.TST2);
		m_program.m_name = "tst2";
		m_rdi.run(m_program, false);
		assertEquals("", Integer.valueOf(5), m_rdi.m_vars.get(19));
		assertEquals("", Integer.valueOf(3), m_rdi.m_vars.get(20));
	}

	@Test
	public void testRunTst2WithBreakPoints() {
		m_program = P1Reader.readProgram(RDITestData.TST2);
		m_program.m_name = "tst2 with breakpoints";
		m_rdi.run(m_program, true);
		assertEquals("I", Integer.valueOf(2), m_rdi.m_vars.get(19));
		m_rdi.interpret(true);
		assertEquals("K", Integer.valueOf(3), m_rdi.m_vars.get(20));
		m_rdi.interpret(true);
		assertEquals("new I", Integer.valueOf(5), m_rdi.m_vars.get(19));
	}

	@Test
	public void testRunTst3WithBreakPoints() {
		m_program = P1Reader.readProgram(RDITestData.TST3);
		m_program.m_name = "tst3";
		m_rdi.run(m_program, true);
		assertEquals("I", Integer.valueOf(2), m_rdi.m_vars.get(19));
		assertEquals("K", Integer.valueOf(3), m_rdi.m_vars.get(20));
		m_rdi.interpret(true);
		assertEquals("new I", Integer.valueOf(5), m_rdi.m_vars.get(19));
	}
	// @Test
	// public void testRun() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testInterpret() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testInit() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testAbort() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testError() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testExpected() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetNextToken() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testMatch() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testNewLine() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetSymbol() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetNum() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testGetCode() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testEmit() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testEmitLnString() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testEmitLnStringString() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testRead() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testTerm() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testExpression() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testAssignment() {
	// fail("Not yet implemented");
	// }
	//
	// @Test
	// public void testFactor() {
	// fail("Not yet implemented");
	// }

}
