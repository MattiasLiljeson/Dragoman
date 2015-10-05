package com.liljeson.mattias.fries.parser;

import java.util.List;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.SymbolKind;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.shared.TokenTypes;

public class P1Reader {
	static Program readProgram(List<String> p_program) {
		String[] progAsArr = p_program.toArray(new String[0]);
		return readProgram(progAsArr);
	}

	public static Program readProgram(String[] p_program) {
		Program prog = new Program();
		int breakPointCnt = 0;
		for (String line : p_program) {
			breakPointCnt = readLine(line, prog, breakPointCnt);
		}
		return prog;
	}

	static int readLine(String p_line, Program p_program,
			int p_breakPointCnt) {

		String[] lineAsArr = split(p_line);
		int type = getLineType(lineAsArr);
		if (type != LineTypes.COMMENT) {
			switch (type) {
			case LineTypes.VAR_DECL: {
				Symbol sym = parseVarDecl(lineAsArr);
				p_program.m_blocks.top().setSymbol(sym);
				break;
			}
			case LineTypes.ARR_DECL: {
				Symbol sym = parseArrDecl(lineAsArr);
				p_program.m_blocks.top().setSymbol(sym);
				break;
			}
			case LineTypes.BLOCK_DECL:
				Block block = parseBlockDecl(lineAsArr);
				p_program.m_blocks.push(block);
				break;
			case LineTypes.CODE:
				Token token = parseToken(lineAsArr);
				p_program.m_blocks.top().m_tokens.add(token);
				break;
			case LineTypes.BREAKPOINT:
				Token breakPoint = createBreakPointToken(lineAsArr);
				p_program.m_blocks.top().m_tokens.add(breakPoint);

			case LineTypes.BLOCK_CNT:
				// Verify that read block cnt correspond to this
				break;
			case LineTypes.COMMENT:
				// Ignore
				break;
			case LineTypes.NONE:
				// cast exception?
				break;
			}
		}
		return p_breakPointCnt;
	}

	private static Token createBreakPointToken(String[] p_line) {
		String text = "BreakpointTokenAddedByP1";
		int symId = Integer.parseInt(p_line[2]);
		int arrIdx = Integer.parseInt(p_line[3]);
		if (p_line.length > 4) {
			text = p_line[4];
		}
		return new Token(TokenTypes.BREAKPOINT, symId, arrIdx, text);
	}

	static Symbol parseArrDecl(String[] p_line) {
		Symbol sym = parseSymbol(p_line);
		sym.m_index = Integer.parseInt(p_line[8]);
		sym.m_lowLimit = Integer.parseInt(p_line[9]);
		sym.m_hiLimit = Integer.parseInt(p_line[10]);
		sym.m_name = p_line[11];
		return sym;
	}

	static Symbol parseVarDecl(String[] p_line) {
		Symbol sym = parseSymbol(p_line);
		// Line 9 always contains # as this is not an array
		sym.m_name = p_line[9];
		return sym;
	}

	/** General symbol parser. Used both by parseArr and parseVar */
	static Symbol parseSymbol(String[] p_line) {
		Symbol sym = new Symbol();
		sym.m_id = Integer.parseInt(p_line[0]);
		sym.m_type = Integer.parseInt(p_line[1]);
		sym.m_kind = new SymbolKind(Integer.parseInt(p_line[2]),
				Integer.parseInt(p_line[3]), Integer.parseInt(p_line[4]),
				Integer.parseInt(p_line[5]));
		sym.m_relAdr = Integer.parseInt(p_line[6]);
		sym.m_extName = p_line[7];
		return sym;
	}

	static Block parseBlockDecl(String[] p_line) {
		Block b = new Block();
		b.m_blockId = Integer.parseInt(p_line[0]);
		b.m_parentBlockId = Integer.parseInt(p_line[1]);
		// Ignore the rest
		return b;
	}

	static Token parseToken(String[] p_line) {
		int code = Integer.parseInt(p_line[0]);
		int type = Integer.parseInt(p_line[1]);
		String text = p_line[2];
		return new Token(type, code, text);
	}

	static int getLineType(String[] p_line) {
		if (p_line[0].startsWith("#")) {
			if (p_line.length > 1 && p_line[1].equals("BREAKPOINT")) {
				return LineTypes.BREAKPOINT;
			} else {
				return LineTypes.COMMENT;
			}
		} else {
			return LineTypes.fromLineLength(p_line.length);
		}
	}

	static String[] split(String p_line) {
		String trimmedLine = p_line.trim();
		return trimmedLine.split("\\s+");
	}
}
