package com.liljeson.mattias.fries.parser;

import java.util.List;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Kind;
import com.liljeson.mattias.fries.shared.Variable;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.Token;

public class P1Reader {
	static Program readProgram(List<String> p_program) {
		String[] progAsArr = p_program.toArray(new String[0]);
		return readProgram(progAsArr);
	}

	static Program readProgram(String[] p_program) {
		Program prog = new Program();
		for (String line : p_program) {
			readLine(line, prog);
		}
		return prog;
	}

	static void readLine(String p_line, Program p_program) {

		String[] lineAsArr = split(p_line);
		int type = getLineType(lineAsArr);
		if (type != LineTypes.COMMENT) {
			switch (type) {
			case LineTypes.VAR_DECL: {
				Symbol sym = parseVarDecl(lineAsArr);
				p_program.m_blocks.top().m_symbolPairs.add(new Variable(sym, 0));
				break;
			}
			case LineTypes.ARR_DECL: {
				Symbol sym = parseArrDecl(lineAsArr);
				p_program.m_blocks.top().m_symbolPairs.add(new Variable(sym, 0));
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
		sym.m_kind = new Kind(Integer.parseInt(p_line[2]),
				Integer.parseInt(p_line[3]), Integer.parseInt(p_line[4]),
				Integer.parseInt(p_line[5]));
		sym.m_relAdr = Integer.parseInt(p_line[6]);
		sym.m_extName = p_line[7];
		return sym;
	}

	static Block parseBlockDecl(String[] p_line) {
		Block b = new Block();
		b.m_blockNr = Integer.parseInt(p_line[0]);
		b.m_parent = Integer.parseInt(p_line[1]);
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
			return LineTypes.COMMENT;
		} else {
			return LineTypes.fromLineLength(p_line.length);
		}
	}

	static String[] split(String p_line) {
		String trimmedLine = p_line.trim();
		return trimmedLine.split("\\s+");
	}
}
