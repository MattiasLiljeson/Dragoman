package com.liljeson.mattias.fries.interpretator;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.liljeson.mattias.fries.interpretator.Variable.WrongTypeException;
import com.liljeson.mattias.fries.parser.P1Reader;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.Details;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

public class RDITest {

	final LogLady m_logLady = new LogLady( Details.LEVEL, LogLevels.INFO );
	final CompLogger m_compLady = new CompLogger( m_logLady );
	final RDI m_rdi = new RDI( m_logLady, m_compLady );
	Program m_program = null;

	@Test
	public void testRunTst0() {
		m_program = P1Reader.readProgram( RDITestData.TST0 );
		m_program.m_name = "tst0";
		m_rdi.run( m_program, false, false );
	}

	@Test
	public void testRunTst1() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST1 );
		m_program.m_name = "tst1";
		m_rdi.run( m_program, true, false );
		assertEquals( "", new Integer( 2 ), m_rdi.m_checks.get( 0 ) );
	}

	@Test
	public void testRunTst2() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST2 );
		m_program.m_name = "tst2 with breakpoints";
		m_rdi.run( m_program, true, false );
		assertEquals( "I", new Integer( 2 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "K", new Integer( 3 ), m_rdi.m_checks.get( 1 ) );
		assertEquals( "new I", new Integer( 5 ), m_rdi.m_checks.get( 2 ) );
	}

	@Test
	public void testRunTst3() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST3 );
		m_program.m_name = "tst3";
		m_rdi.run( m_program, true, false );
		assertEquals( "I", new Integer( 2 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "K", new Integer( 3 ), m_rdi.m_checks.get( 1 ) );
		assertEquals( "new I", new Integer( 5 ), m_rdi.m_checks.get( 2 ) );
	}

	@Test
	public void testRunTst4() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST4 );
		m_program.m_name = "tst4";
		m_rdi.run( m_program, true, false );

		assertEquals( "I", new Integer( 2 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "K", new Integer( 3 ), m_rdi.m_checks.get( 1 ) );

		assertEquals( "local I", new Integer( 4 ), m_rdi.m_checks.get( 2 ) );
		assertEquals( "new K", new Integer( 7 ), m_rdi.m_checks.get( 3 ) );

		assertEquals( "final I", new Integer( 14 ), m_rdi.m_checks.get( 4 ) );
		assertEquals( "final K", new Integer( 7 ), m_rdi.m_checks.get( 5 ) );

	}

	@Test
	public void testRunTst5() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST5 );
		m_program.m_name = "tst5";
		m_rdi.run( m_program, true, false );
		assertEquals( "I", new Integer( 3 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "F, funcval", new Integer( 3 ), m_rdi.m_checks.get( 1 ) );
		assertEquals( "F, funcval", new Integer( 4 ), m_rdi.m_checks.get( 2 ) );
		assertEquals( "final I", new Integer( 16 ), m_rdi.m_checks.get( 3 ) );
	}

	@Test
	public void testRunTst6() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST6 );
		m_program.m_name = "tst6";
		m_rdi.run( m_program, true, false );
		assertEquals( "final I", new Integer( 18 ), m_rdi.m_checks.get( 0 ) );
	}

	@Test
	public void testRunTst7() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST7 );
		m_program.m_name = "tst7";
		m_rdi.run( m_program, true, false );
		assertEquals( "final I", new Integer( 1049 ), m_rdi.m_checks.get( 0 ) );
	}

	@Test
	public void testRunTst8() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST8 );
		m_program.m_name = "tst8";
		m_rdi.run( m_program, true, false );
		assertEquals( "a[3]", new Integer( 5 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "a[4]", new Integer( 9 ), m_rdi.m_checks.get( 1 ) );
		assertEquals( "a[5]", new Integer( 45 ), m_rdi.m_checks.get( 2 ) );
	}

	@Test
	public void testRunTst9() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.TST9 );
		m_program.m_name = "tst9";
		m_rdi.m_input.push( 5 );
		m_rdi.m_input.push( 3 );
		m_rdi.run( m_program, true, false );
		assertEquals( "output: ", new Integer( 5 ), m_rdi.m_checks.get( 0 ) );
		assertEquals( "output: ", new Integer( 3 ), m_rdi.m_checks.get( 1 ) );
		assertEquals( "output: ", new Integer( 6 ), m_rdi.m_checks.get( 2 ) );
	}

	@Ignore
	@Test
	public void testRunDeluxe() throws WrongTypeException {
		m_program = P1Reader.readProgram( RDITestData.DELUXE );
		m_program.m_name = "DELUXE";
		m_rdi.run( m_program, true, false );
	}

}
