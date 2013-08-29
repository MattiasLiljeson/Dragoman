package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

public class Cradle {

	LogLady m_log = new LogLady(false, LogLevels.ERROR);
	CompLogger m_compLog = new CompLogger(m_log);
	// Tokenizer m_tokenizer = new Tokenizer("1+2");
	// char m_look = '_';
	// int[] table = new int['z'];

	// Replace with proper stack later
	// Map<Integer, Symbol> symbols = new HashMap<>();

	Program m_program = null;
	Block m_currBlock = null;
	Token m_look;
	boolean m_abort = false;

	public Cradle(final LogLady p_log, final CompLogger p_compLog) {
		if (p_compLog != null) {
			m_compLog = p_compLog;
		}
		if (p_log != null) {
			m_log = p_log;
		}
	}

	public void run(final Program p_program) {
		// m_tokenizer = new Tokenizer(p_program);
		// init();
		m_program = p_program;
		m_program.prepForUse();
		m_currBlock = p_program.getMain();
		m_look = m_currBlock.getNextToken();
	}

	public void interpret() {
		do {
			// m_log.write( new Integer( expression() ).toString() );
			switch (m_look.m_type) {
			// case '?':
			// input();
			// break;
			// case '!':
			// output();
			// break;
			default:
				assignment();
				break;
			}
			newLine();
		} while (m_look.equals(Token.END));
	}

	void init() {
		// getChar();
		// initTable();
	}

	// void initTable() {
	// for (int i = 0; i < table.length; i++) {
	// table[i] = 0;
	// }
	// }

	void abort(final String p_msg) {
		m_log.log(LogLevels.SEVERE, p_msg);
		m_abort = true;
		try {
			throw new Exception();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	void error(final String p_msg) {
		m_log.log(LogLevels.ERROR, p_msg);
		m_abort = true;
		try {
			throw new Exception();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	void expected(final String p_msg) {
		abort(p_msg + " Expected");
	}

	void getNextToken() {
		m_look = read();
		m_log.log(LogLevels.NONE,
				"; next token read to look: " + m_look.toString());
	}

	// void input() {
	// m_compLog.push();
	// match('?');
	// table[getName()] = m_log.read();
	// m_compLog.pop();
	// }
	//
	// void output() {
	// m_compLog.push();
	// match('!');
	// m_log.WriteLn(table[getName()]);
	// m_compLog.pop();
	// }

	void match(final Token p_token) {
		if (m_look.equals(p_token)) {
			getNextToken();
		} else {
			expected("\"" + p_token + "\"");
		}
	}

	void newLine() {
		if (m_look.isNewline()) {
			getNextToken();
		}
	}

	// boolean isAddop(final char p_char) {
	// return p_char == '+' || p_char == '-';
	// }

	// boolean isAlpha(final char p_char) {
	// return Character.isLetter(p_char);
	// }
	//
	// boolean isDigit(final char p_char) {
	// return Character.isDigit(p_char);
	// }

	// char getName() {
	// // % returned if look isn't a digit
	// char name = '%';
	// if (!isAlpha(m_look)) {
	// expected("Name");
	// } else {
	// name = Character.toUpperCase(m_look);
	// getNextToken();
	// }
	// return name;
	// }

	// int getNum() {
	// int num = 0;
	// if (!isDigit(m_look)) {
	// expected("Integer");
	// }
	// while (isDigit(m_look)) {
	// num = num * 10 + Integer.parseInt((String.valueOf(m_look)));
	// getChar();
	// }
	// return num;
	// }

	int getSymbol() {
		// null returned if look isn't a digit
		int sym = -1;
		if (!m_look.isSymbol()) {
			expected("Name");
		} else {
			sym = getCode();
		}
		return sym;
	}

	int getNum() {
		int num = 0;
		if (!m_look.isDigit()) {
			expected("Integer");
		} else {
			num = getCode();
		}
		return num;
	}

	int getCode() {
		int code = m_look.m_code;
		getNextToken();
		return code;
	}

	void emit(final String p_msg) {
		m_log.write("\t" + p_msg);
	}

	void emitLn(final String p_msg) {

		emitLn(p_msg, CompLogger.getCallerName(3));
	}

	void emitLn(final String p_msg, final String p_caller) {
		emit(p_msg + "\t\t; " + p_caller);
		m_log.writeLn();
	}

	Token read() {
		// return m_tokenizer.getNextToken().m_token;
		return m_currBlock.getNextToken();
	}

	int term() {
		m_compLog.push();
		int value = factor();
		while (m_look.isMulOp()) {
			if (m_look.equals(Token.TIMES)) {
				match(Token.TIMES);
				value *= factor();
			} else if (m_look.equals(Token.DIV)) {
				match(Token.DIV);
				value /= factor();
			}
		}
		m_compLog.pop();
		return value;
	}

	int expression() {
		m_compLog.push();
		int value;
		if (m_look.isAddOp()) {
			value = 0;
		} else {
			value = term();
		}
		while (m_look.isAddOp()) {
			if (m_look.equals(Token.PLUS)) {
				match(Token.PLUS);
				value += term();
			} else if (m_look.equals(Token.MINUS)) {
				match(Token.MINUS);
				value -= term();
			}
		}
		m_compLog.pop();
		return value;
	}

	void assignment() {
		m_compLog.push();
		// final char name = getName();
		int symId = getSymbol();
		match(Token.BECOME);
		// table[name] = expression();
		int val = expression();
		m_currBlock.setSym(symId, val);

		m_compLog.pop();
	}

	// void add() {
	// m_compLog.push();
	// match('+');
	// term();
	// emitLn("ADD  (SP)+, D0");
	// m_compLog.pop();
	// }
	//
	// void subtract() {
	// m_compLog.push();
	// match('-');
	// term();
	// emitLn("SUB  (SP)+, D0");
	// emitLn("NEG  D0");
	// m_compLog.pop();
	// }

	int factor() {
		int value;
		m_compLog.push();
		if (m_look.equals(Token.RPAR)) {
			match(Token.RPAR);
			value = expression();
			match(Token.LPAR);
		} else if (m_look.isSymbol()) {
			int symId = getSymbol();
			value = m_currBlock.getValue(symId);
			// value = table[getName()]; // ident();
		} else {
			value = getNum();
		}
		m_compLog.pop();
		return value;
	}

	// void ident() {
	// m_compLog.push();
	// final char name = getName();
	// if (m_look == '(') {
	// match('(');
	// match(')');
	// emitLn("BSR " + name);
	// } else {
	// emitLn("MOVE " + name + "(PC), D0");
	// }
	// m_compLog.pop();
	// }

	// void multiply() {
	// m_compLog.push();
	// match('*');
	// factor();
	// emitLn("MULS (SP)+, D0");
	// m_compLog.pop();
	// }
	//
	// void divide() {
	// m_compLog.push();
	// match('/');
	// factor();
	// emitLn("MOVE (SP)+, D1");
	// emitLn("DIVS D1, D0");
	// m_compLog.pop();
	// }
	// {--------------------------------------------------------------}
	// { Main Program }
	//
	// begin
	// Init;
	// end.
	// {--------------------------------------------------------------}
}