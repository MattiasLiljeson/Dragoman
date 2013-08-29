package com.liljeson.mattias.fries.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TokenTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHashCode() {
		Token a = new Token(1, 2, "a");
		int aHash = a.hashCode();
		Token b = new Token(1, 2, "b");
		int bHash = b.hashCode();
		Token c = new Token(1, 3, "c");
		int cHash = c.hashCode();

		assertEquals("Equals", aHash, bHash);
		assertNotEquals("", aHash, cHash);
	}

	@Test
	public void testToken() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsBegin() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsEnd() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsSymbol() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsDigit() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsNewline() {
		// fail("Not yet implemented");
	}

	@Test
	public void testIsAddOp() {
		Token minus = new Token(TokenTypes.OP, Keywords.TC_MINUS, "minus");
		assertTrue("minus isAddOp", minus.isAddOp());
		Token plus = new Token(TokenTypes.OP, Keywords.TC_PLUS, "plus");
		assertTrue("plus isAddOp", plus.isAddOp());
		Token fail = new Token(-1, Keywords.TC_PLUS, "fail");
		assertFalse("fail isAddOp", fail.isAddOp());
	}

	@Test
	public void testIsMulOp() {
		Token mul = new Token(TokenTypes.OP, Keywords.TC_MUL, "mul");
		assertTrue("mul isMulOp", mul.isMulOp());
		Token div = new Token(TokenTypes.OP, Keywords.TC_DIV, "div");
		assertTrue("div isMulOp", div.isMulOp());
		Token fail = new Token(-1, Keywords.TC_DIV, "fail");
		assertFalse("fail isAddOp", fail.isMulOp());
	}

	@Test
	public void testIsOp() {
		Token t = new Token(TokenTypes.OP, -1, "t");
		assertTrue("isOp", t.isOp());
	}

	@Test
	public void testEqualsObject() {
		Token a = new Token(1, 2, "a");
		Token b = new Token(1, 2, "b");
		Token c = new Token(1, 3, "c");

		assertEquals("Equals", a, b);
		assertNotEquals("", a, c);
	}

}
