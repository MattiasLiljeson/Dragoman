package com.liljeson.mattias.fries.shared;

public class Token {
	private static final String MSG = "Comparison Token; ";
	public static final Token BEGIN = new Token(TokenTypes.ID,
			Keywords.ID_BEGIN, MSG + "BEGIN");
	public static final Token END = new Token(TokenTypes.ID, Keywords.ID_END,
			MSG + "END");
	public static final Token IF = new Token(TokenTypes.ID, Keywords.ID_IF,
			MSG + "IF");
	public static final Token THEN = new Token(TokenTypes.ID, Keywords.ID_THEN,
			MSG + "THEN");
	public static final Token ELSE = new Token(TokenTypes.ID, Keywords.ID_ELSE,
			MSG + "ELSE");
	public static final Token SEMICOLON = new Token(TokenTypes.OP,
			Keywords.TC_SEMI, MSG + "SEMICOLON, ;");
	public static final Token BECOME = new Token(TokenTypes.OP,
			Keywords.TC_BECOME, MSG + "BECOMES, :=");
	public static final Token MINUS = new Token(TokenTypes.OP,
			Keywords.TC_MINUS, MSG + "MINS, -");
	public static final Token PLUS = new Token(TokenTypes.OP, Keywords.TC_PLUS,
			MSG + "PLUS, +");
	public static final Token TIMES = new Token(TokenTypes.OP, Keywords.TC_MUL,
			MSG + "TIMES, *");
	public static final Token DIV = new Token(TokenTypes.OP, Keywords.TC_DIV,
			MSG + "DIV, /");
	public static final Token LPAR = new Token(TokenTypes.OP, Keywords.TC_LPAR,
			MSG + "LEFT PARANTHESIS, (");
	public static final Token RPAR = new Token(TokenTypes.OP, Keywords.TC_RPAR,
			MSG + "RIGHT PARANTHESIS, )");

	public int m_type;
	public int m_code;
	public int m_extra; // used by breakpoint to store arr idx
	public String m_text;

	public Token(int p_type, int p_code, int p_extra, String p_text) {
		m_type = p_type;
		m_code = p_code;
		m_extra = p_extra;
		m_text = p_text;

	}

	public Token(int p_type, int p_code, String p_text) {
		m_type = p_type;
		m_code = p_code;
		m_extra = -1;
		m_text = p_text;

	}

	public boolean isSymbol() {
		return m_type == TokenTypes.ID && m_code > Keywords.ID_CNT;
	}

	public boolean isDigit() {
		return m_type == TokenTypes.INT;
	}

	public boolean isNewline() {
		return m_type == TokenTypes.LINE;
	}

	public boolean isBreakpoint() {
		return m_type == TokenTypes.BREAKPOINT;
	}

	public boolean isCall() {
		return m_type == TokenTypes.CALL;
	}

	public boolean isOp() {
		return m_type == TokenTypes.OP;
	}

	public boolean isAddOp() {
		return this.equals(MINUS) || this.equals(PLUS);
	}

	public boolean isMulOp() {
		return this.equals(DIV) || this.equals(TIMES);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + m_code;
		result = prime * result + m_type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (m_code != other.m_code)
			return false;
		if (m_type != other.m_type)
			return false;
		return true;
	}
}
