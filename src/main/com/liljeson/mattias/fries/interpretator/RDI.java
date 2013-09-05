package com.liljeson.mattias.fries.interpretator;

import java.util.HashMap;
import java.util.Map;

import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

/**
 * RDI - Recursive Descent Interpreter
 * 
 * @author Mattias Liljeson
 * 
 */

public class RDI {

	LogLady m_log = new LogLady(false, LogLevels.ERROR);
	CompLogger m_compLog = new CompLogger(m_log);

	// Replace with proper stack later
	Map<Integer, Integer> m_vars = new HashMap<>();

	Program m_program = null;
	Block m_currBlock = null;
	Token m_look;
	boolean m_abort = false;

	public RDI(final LogLady p_log, final CompLogger p_compLog) {
		if (p_compLog != null) {
			m_compLog = p_compLog;
		}
		if (p_log != null) {
			m_log = p_log;
		}
	}

	public void run(final Program p_program, boolean p_useBreakpoints) {
		m_program = p_program;
		m_program.prepForUse();
		m_currBlock = p_program.getMain();
		m_log.log(LogLevels.INFO, "\n######################\nRunning program: "
				+ m_program.m_name);
		interpret(p_useBreakpoints);
	}

	public void interpret(boolean p_useBreakpoints) {
		m_log.log(LogLevels.INFO, "Starting interpretation of program: "
				+ m_program.m_name);
		do {
			getNextToken();
			if (m_look.isSymbol()) {
				assignment();
			} else if (p_useBreakpoints && m_look.isBreakpoint()) {
				m_log.log(LogLevels.INFO,
						"Hit breakpoint #" + String.valueOf(m_look.m_code));
				// To continue executing, just call interpret again
				return;
			}
			// newLine();
		} while (!m_look.equals(Token.END));
	}

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
		// m_currBlock.setSym(symId, val);
		m_vars.put(symId, val);

		m_compLog.pop();
	}

	int factor() {
		int value;
		m_compLog.push();
		if (m_look.equals(Token.RPAR)) {
			match(Token.RPAR);
			value = expression();
			match(Token.LPAR);
		} else if (m_look.isSymbol()) {
			int symId = getSymbol();
			// value = m_currBlock.getValue(symId);
			// value = table[getName()]; // ident();
			value = m_vars.get(symId);
		} else {
			value = getNum();
		}
		m_compLog.pop();
		return value;
	}
}