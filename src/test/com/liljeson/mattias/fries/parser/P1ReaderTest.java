package com.liljeson.mattias.fries.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.SymbolKinds;
import com.liljeson.mattias.fries.shared.SymbolTypes;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.shared.TokenTypes;

public class P1ReaderTest {

	static final String[] BREAKPOINT_LINE = { "#", "BREAKPOINT" };
	static final String[] COMMENT_LINE = { "###PROGRAM###" };
	static final String[] ERRANOUS_LINE = { "0", "-1HARVARDETFEL" };
	static final String[] BLOCK_CNT_LINE = { "0" };
	static final String[] BLOCK_DECL_LINE = { "0", "-1", "2", "2", "48" };
	static final String[] TOKEN_LINE = { "2", "7", "Line#" };
	static final String[] VAR_LINE = { "19", "1", "0", "0", "0", "0", "0",
			"**", "#", "I" };
	static final String[] ARR_LINE = { "20", "1", "1", "1", "0", "0", "1",
			"**", "1", "2", "6", "A" };
	static final String[] P1 = {
			"###PROGRAM###",
			"1",
			"##BLOCK##",
			"  0 -1  1  1   10",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    2    2    2",
			"    1    5    ;",
			"    6    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testReadProgram() {
		Program prog = P1Reader.readProgram(P1);
		assertEquals("Block cnt", 1, prog.m_blocks.size());
		Block b = prog.m_blocks.get(0);
		assertEquals("First token type", TokenTypes.LINE,
				b.m_tokens.get(0).m_type);
		assertEquals("Last token type", TokenTypes.ID,
				b.m_tokens.get(9).m_type);
		assertEquals("Variable ID", 19,
				b.m_symbolPairs.get(0).m_sym.m_id);
		assertEquals("Variable type", SymbolTypes.INT,
				b.m_symbolPairs.get(0).m_sym.m_type);
		assertEquals("Variable kind", SymbolKinds.SIMPLE,
				b.m_symbolPairs.get(0).m_sym.m_kind.m_kind);
		assertEquals("Variable name", "I",
				b.m_symbolPairs.get(0).m_sym.m_name);
	}

	@Test
	public void testReadLine() {
		// fail("Not yet implemented");
	}

	@Test
	public void testParseBlockDecl() {

		Block b = P1Reader.parseBlockDecl(BLOCK_DECL_LINE);
		assertEquals("BlockNr", 0, b.m_blockNr);
		assertEquals("Parent", -1, b.m_parent);
	}

	@Test
	public void testParseToken() {
		Token t = P1Reader.parseToken(TOKEN_LINE);
		assertEquals("Type", TokenTypes.LINE, t.m_type);
		assertEquals("Code", 2, t.m_code);
		assertEquals("Text", "Line#", t.m_text);
	}

	@Test
	public void testParseArrDecl() {

		Symbol s = P1Reader.parseArrDecl(ARR_LINE);
		assertEquals("Idx", 1, s.m_index);
		assertEquals("Low", 2, s.m_lowLimit);
		assertEquals("Hi", 6, s.m_hiLimit);
		assertEquals("Name", "A", s.m_name);
	}

	@Test
	public void testParseVarDecl() {

		Symbol s = P1Reader.parseVarDecl(VAR_LINE);
		assertEquals("Name", "I", s.m_name);
	}

	@Test
	public void testParseSymbol() {

		Symbol s = P1Reader.parseSymbol(ARR_LINE);
		assertEquals("Id", 20, s.m_id);
		assertEquals("Type", SymbolTypes.INT, s.m_type);
		assertEquals("Kind", SymbolKinds.ARRAY, s.m_kind.m_kind);
		assertEquals("Kind info1", 1, s.m_kind.m_info1);
		assertEquals("Kind info1", 0, s.m_kind.m_info2);
		assertEquals("Kind info1", 0, s.m_kind.m_info2);
		assertEquals("Relative adress", 1, s.m_relAdr);
		assertEquals("Ext name", "**", s.m_extName);
	}

	@Test
	public void testGetLineType() {
		assertEquals("breakpoint", LineTypes.BREAKPOINT,
				P1Reader.getLineType(BREAKPOINT_LINE));

		assertEquals("comment", LineTypes.COMMENT,
				P1Reader.getLineType(COMMENT_LINE));

		assertEquals("none / erranous", LineTypes.NONE,
				P1Reader.getLineType(ERRANOUS_LINE));

		assertEquals("block count", LineTypes.BLOCK_CNT,
				P1Reader.getLineType(BLOCK_CNT_LINE));

		assertEquals("code / tokens", LineTypes.CODE,
				P1Reader.getLineType(TOKEN_LINE));

		assertEquals("Block declaration", LineTypes.BLOCK_DECL,
				P1Reader.getLineType(BLOCK_DECL_LINE));

		assertEquals("Variable declaration", LineTypes.VAR_DECL,
				P1Reader.getLineType(VAR_LINE));

		assertEquals("Array declaration", LineTypes.ARR_DECL,
				P1Reader.getLineType(ARR_LINE));
	}

	@Test
	public void testSplit() {
		String[] line = P1Reader.split(P1[3]);
		assertEquals("Token 1", "0", line[0]);
	}
}
