package com.liljeson.mattias.fries.shared;

public class Token {
	private static final String MSG = "FromStaticFieldsInClassToken";
	public static final Token BEGIN = new Token(TokenTypes.ID,
			Keywords.ID_BEGIN, MSG);
	public static final Token END = new Token(TokenTypes.ID, Keywords.ID_END,
			MSG);
	public static final Token BECOME = new Token(TokenTypes.OP,
			Keywords.TC_BECOME, MSG);
	public static final Token MINUS = new Token(TokenTypes.OP,
			Keywords.TC_MINUS, MSG);
	public static final Token PLUS = new Token(TokenTypes.OP, Keywords.TC_PLUS,
			MSG);
	public static final Token TIMES = new Token(TokenTypes.OP, Keywords.TC_MUL,
			MSG);
	public static final Token DIV = new Token(TokenTypes.OP, Keywords.TC_DIV,
			MSG);
	public static final Token LPAR = new Token(TokenTypes.OP, Keywords.TC_LPAR,
			MSG);
	public static final Token RPAR = new Token(TokenTypes.OP, Keywords.TC_RPAR,
			MSG);

	public int m_type;
	public int m_code;
	public String m_text;

	public Token(int p_type, int p_code, String p_text) {
		m_type = p_type;
		m_code = p_code;
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
