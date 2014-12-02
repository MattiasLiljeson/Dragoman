package com.liljeson.mattias.fries.interpretator;

import com.liljeson.mattias.fries.interpretator.Variable.WrongTypeException;
import com.liljeson.mattias.fries.shared.Block;
import com.liljeson.mattias.fries.shared.Keywords;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.shared.Symbol;
import com.liljeson.mattias.fries.shared.SymbolKinds;
import com.liljeson.mattias.fries.shared.SymbolTypes;
import com.liljeson.mattias.fries.shared.Token;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.DeluxeArray;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.Details;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

/**
 * RDI - Recursive Descent Interpreter
 * 
 * @author Mattias Liljeson
 * 
 */

public class RDI {

	LogLady m_log = new LogLady( Details.LEVEL, LogLevels.ERROR );
	CompLogger m_compLog = new CompLogger( m_log );

	SymbolStack m_vars = new SymbolStack();

	Program m_program = null;
	boolean m_abort = false;
	DeluxeArray< Integer > m_checks = new DeluxeArray<>();
	DeluxeArray< Integer > m_input = new DeluxeArray<>();

	boolean m_useCheckpoints;
	boolean m_interactive = true;
	Variable m_lastBlockVar = null;

	public RDI( final LogLady p_log, final CompLogger p_compLog ) {
		if( p_compLog != null ) {
			m_compLog = p_compLog;
		}
		if( p_log != null ) {
			m_log = p_log;
		}
	}

	void abort( final String p_msg ) {
		m_log.log( LogLevels.SEVERE, p_msg );
		m_abort = true;
		try {
			throw new Exception();
		} catch( final Exception e ) {
			e.printStackTrace();
		}
	}

	void error( final String p_msg ) {
		m_log.log( LogLevels.ERROR, p_msg );
		m_abort = true;
		try {
			throw new Exception();
		} catch( final Exception e ) {
			e.printStackTrace();
		}
	}

	void expected( final String p_msg ) {
		abort( "Expected: \"" + p_msg + "\". Was: \"" + look().m_text + "\"" );
	}

	Token getNextToken() {
		Token tok = m_vars.getNextToken();
		if( look() != null ) {
			m_log.log( LogLevels.DEBUG, "; next token read to look: "
					+ look().m_text );
		} else {
			m_log.log( LogLevels.DEBUG, "; next token read to look: null" );
		}
		return tok;
	}

	void match( final Token p_token ) {
		if( look().equals( p_token ) ) {
			getNextToken();
		} else {
			expected( p_token.m_text );
		}
	}

	int getSymbolId() {
		// null returned if look isn't a digit
		int sym = -1;
		if( !look().isSymbol() ) {
			expected( "Name" );
		} else {
			sym = getCode();
		}
		return sym;
	}

	int getNum() {
		int num = 0;
		if( !look().isDigit() ) {
			expected( "Integer" );
		} else {
			num = getCode();
		}
		return num;
	}

	int getCode() {
		int code = look().m_code;
		getNextToken();
		return code;
	}

	void emitLn( final String p_msg ) {
		emitLn( p_msg, CompLogger.getCallerName( 3 ) );
	}

	void emitLn( final String p_msg, final String p_caller ) {
		emit( p_msg + "\t\t; " + p_caller );
		m_log.writeLn();
	}

	void emit( final String p_msg ) {
		m_log.write( "\t" + p_msg );
	}

	public void run( final Program p_program, boolean p_useBreakpoints,
			boolean p_interactive ) {
		m_interactive = p_interactive;
		m_useCheckpoints = p_useBreakpoints;
		m_program = p_program;
		m_program.prepForUse();
		m_log.log( LogLevels.INFO, "Running program: " + m_program.m_name );
		start();
	}

	public void start() {
		m_log.log( LogLevels.INFO, "Starting interpretation of program: "
				+ m_program.m_name );
		m_abort = false;
		interpretBlock( m_program.getMain(), null );
	}

	// TODO: change param to param list if many params are to be supported
	void interpretBlock( Block p_block, Variable p_param ) {
		m_compLog.push();
		m_vars.push( p_block, p_param );
		match( Token.BEGIN );
		statements();
		match( Token.END );
		m_lastBlockVar = m_vars.pop().getFrameVar();
		m_compLog.pop();
	}

	Token look() {
		return m_vars.look();
	}

	private void statements() {
		m_compLog.push();
		while( ifKeepExecBlock() ) {
			statement();
			getNextToken();
		}
		m_compLog.pop();
	}

	boolean ifKeepExecBlock() {
		return !m_abort && look() != null && !look().equals( Token.END )
				&& !look().equals( Token.ELSE );
	}

	void statement() {
		m_compLog.push();
		if( look().isBreakpoint() ) {
			checkpoint();
		} else if( look().isCall() ) {
			callBlock();
		} else if( look().isSymbol() ) {
			// assignment();
			symbol();
		} else if( look().equals( Token.IF ) ) {
			ifStmt();
		} else if( look().isNewline() ) {
			m_log.log( LogLevels.DEBUG, "End of line: " + look().m_code );
		}
		m_compLog.pop();
	}

	private void ifStmt() {
		m_compLog.push();
		match( Token.IF );
		boolean branch = condition();
		match( Token.THEN );
		m_log.log( LogLevels.TRACE, "; Condition returned: " + branch );
		if( branch ) {
			m_log.log( LogLevels.INFO, "; doing if" );
			execBody();
			skipNewLine();
			match( Token.ELSE );
			skipNewLine();
			if( look().isCall() ) {
				getNextToken();
			} else {
				skipUntil( Token.SEMICOLON );
			}
		} else {
			m_log.log( LogLevels.INFO, "; doing else" );
			skipUntil( Token.ELSE );
			match( Token.ELSE );
			execBody();
		}
		m_compLog.pop();
	}

	private boolean condition() {
		m_compLog.push();
		int lhs = expression();
		Token op = getNextToken();
		int rhs = expression();
		m_compLog.pop();
		return relation( lhs, op, rhs );
	}

	private boolean relation( int lhs, Token op, int rhs ) {
		m_compLog.push();
		m_compLog.pop();
		if( !op.isOp() ) {
			m_abort = true;
			error( "Is not operator!" );
		} else {
			if( op.m_code == Keywords.TC_EQUAL ) {
				return lhs == rhs;
			} else if( op.m_code == Keywords.TC_NEQ ) {
				return lhs != rhs;
			} else if( op.m_code == Keywords.TC_LESS ) {
				return lhs < rhs;
			} else if( op.m_code == Keywords.TC_LE ) {
				return lhs <= rhs;
			} else if( op.m_code == Keywords.TC_GT ) {
				return lhs > rhs;
			} else if( op.m_code == Keywords.TC_GE ) {
				return lhs >= rhs;
			} else {
				m_abort = true;
				error( "Is not relation operator!" );
			}
		}

		return false;
	}

	void execBody() {
		m_compLog.push();
		statement();
		m_compLog.pop();
	}

	private void skipNewLine() {
		m_compLog.push();
		while( look().isNewline() ) {
			getNextToken();
		}
		m_compLog.pop();
	}

	private void skipUntil( Token p_until ) {
		m_compLog.push();
		while( !look().equals( p_until ) ) {
			getNextToken();
		}
		m_compLog.pop();
	}

	private void checkpoint() {
		m_compLog.push();
		if( m_useCheckpoints ) {
			int val = -1;
			try {
				val = m_vars.getVar( look().m_code ).getInt( look().m_extra );
			} catch( WrongTypeException e ) {
				e.printStackTrace();
			}
			m_checks.push( val );
			String msg = "Hit checkpoint for symbol #"
					+ String.valueOf( look().m_code ) + ": " + look().m_text
					+ " = " + val;
			m_log.log( LogLevels.INFO, msg );
		}
		m_compLog.pop();
	}

	private void callBlock() {
		m_compLog.push();
		int blockId = look().m_code;
		Block block = m_program.getBlock( blockId );
		interpretBlock( block, null );
		m_compLog.pop();
	}

	int expression() {
		m_compLog.push();
		int value;
		if( look().isAddOp() ) {
			value = 0;
		} else {
			value = term();
		}
		while( look().isAddOp() ) {
			if( look().equals( Token.PLUS ) ) {
				match( Token.PLUS );
				value += term();
			} else if( look().equals( Token.MINUS ) ) {
				match( Token.MINUS );
				value -= term();
			}
		}
		m_compLog.pop();
		return value;
	}

	int term() {
		m_compLog.push();
		int value = factor();
		while( look().isMulOp() ) {
			if( look().equals( Token.TIMES ) ) {
				match( Token.TIMES );
				value *= factor();
			} else if( look().equals( Token.DIV ) ) {
				match( Token.DIV );
				value /= factor();
			}
		}
		m_compLog.pop();
		return value;
	}

	int factor() {
		m_compLog.push();
		int value = -1;
		if( look().equals( Token.RPAR ) ) {
			value = getParenthesisExpr();
		} else if( look().isSymbol() ) {
			value = symbol();
		} else {
			value = getNum();
		}
		m_compLog.pop();
		return value;
	}

	int symbol() {
		m_compLog.push();
		String text = look().m_text;
		int symId = getSymbolId();
		Symbol sym = m_vars.getSymbol( symId );
		int value;
		if( sym != null ) {
			value = userDefinedSymbol( symId, sym );
		} else {
			value = rtsSymbol( text );
		}
		m_compLog.pop();
		return value;
	}

	private int rtsSymbol( String p_text ) {
		m_compLog.push();
		int val = -1;
		if( p_text.equals( "write" ) ) {
			int expr = getParenthesisExpr();
			m_log.write( expr );
			m_checks.push( expr );
		} else if( p_text.equals( "writln" ) ) {
			m_log.writeLn();
		} else if( p_text.equals( "readint" ) ) {
			if( m_interactive ) {
				val = m_log.read();
				m_log.log( LogLevels.INFO, "read input from user: " + val );
			} else {
				if( m_input.size() > 0 ) {
					val = m_input.pop();
					m_log.log( LogLevels.INFO, "read input from input buffer: "
							+ val );
				} else {
					val = -2;
					m_log.log( LogLevels.INFO, "input buffer empty. used: "
							+ val );
				}
			}
		}
		m_compLog.pop();
		return val;
	}

	int userDefinedSymbol( int symId, Symbol sym ) {
		m_compLog.push();
		try {
			switch( sym.m_kind.m_kind ) {
			case SymbolKinds.SIMPLE:
				return variableSym( symId, sym );
			case SymbolKinds.ARRAY:
				return arraySym( symId, sym );
			case SymbolKinds.FUNC:
				return functionSym( symId, sym );
			case SymbolKinds.FUNC_VAL:
				return recursiveFunctionSym( symId, sym );
			default:
				unsupportedSym( sym );
			}
		} catch( WrongTypeException e ) {
			m_abort = true;
			e.printStackTrace();
		}
		m_compLog.pop();
		return -1;
	}

	void unsupportedSym( Symbol sym ) {
		m_compLog.push();
		m_abort = true;
		error( "; unrecognized symbol format \"" + sym.m_name + "\"" );
		m_compLog.pop();
	}

	int variableSym( int symId, Symbol sym ) throws WrongTypeException {
		m_compLog.push();
		int value = -1;
		if( look().equals( Token.BECOME ) ) {
			assignment( symId, sym, Variable.NO_ARRAY_IDX );
		} else {
			value = m_vars.getVar( symId ).getInt( Variable.NO_ARRAY_IDX );
			m_log.log( LogLevels.INFO, "; variable \"" + sym.m_name
					+ "\" returned: " + value );
		}
		m_compLog.pop();
		return value;
	}

	int arraySym( int symId, Symbol sym ) throws WrongTypeException {
		m_compLog.push();
		int value = -1;
		int arrIdx = getParenthesisExpr();
		if( look().equals( Token.BECOME ) ) {
			assignment( symId, sym, arrIdx );
		} else {
			Variable var = m_vars.getVar( symId );
			value = var.getInt( arrIdx );
			m_log.log( LogLevels.INFO, "; array \"" + sym.m_name + "[" + arrIdx
					+ "]\"  returned: " + value );
		}
		m_compLog.pop();
		return value;
	}

	int functionSym( int symId, Symbol sym ) {
		m_compLog.push();
		int value;
		m_log.log( LogLevels.INFO, "; Entering function: " + sym.m_name );
		Symbol funcSym = m_vars.getSymbol( symId );
		value = callFunc( funcSym );
		m_log.log( LogLevels.INFO, "; function \"" + sym.m_name
				+ "\" returned: " + value );
		m_compLog.pop();
		return value;
	}

	int recursiveFunctionSym( int symId, Symbol sym ) {
		m_compLog.push();
		int value = -1;
		if( look().equals( Token.BECOME ) ) {
			assignment( symId, sym, Variable.NO_ARRAY_IDX );
		} else {
			m_log.log( LogLevels.INFO, "; Entering recursive function: "
					+ sym.m_name );
			// TODO: Is it always true that the parent block has the correct
			// symbol for recursive function calls?
			int parentId = m_vars.top().getParentBlockId();
			Block parent = m_program.getBlock( parentId );
			Symbol funcSym = parent.getSymbol( symId );
			value = callFunc( funcSym );
			m_log.log( LogLevels.INFO, "; recursive function \"" + sym.m_name
					+ "\" returned: " + value );
		}
		m_compLog.pop();
		return value;
	}

	int callFunc( Symbol p_funcSym ) {
		m_compLog.push();
		int value = -1;
		int blockId = p_funcSym.m_kind.m_info2;

		try {
			match( Token.LPAR );
			value = expression();
			Symbol sym = new Symbol( SymbolTypes.INT, SymbolKinds.SIMPLE );
			Variable param = new Variable( sym );
			param.setInt( value, Variable.NO_ARRAY_IDX );
			match( Token.RPAR );

			Block block = m_program.getBlock( blockId );
			interpretBlock( block, param );
			value = m_lastBlockVar.getInt( Variable.NO_ARRAY_IDX );
		} catch( WrongTypeException e ) {
			e.printStackTrace();
		}

		m_compLog.pop();
		return value;
	}

	void assignment( int symId, Symbol sym, int p_arrIdx ) {
		m_compLog.push();
		match( Token.BECOME );
		int value = expression();
		Variable var = null;
		if( m_vars.top().varExistsInThisFrame( symId ) ) {
			var = m_vars.getVar( symId );
		} else {
			var = new Variable( sym );
		}

		try {
			var.setInt( value, p_arrIdx );
		} catch( WrongTypeException e ) {
			m_abort = true;
			e.printStackTrace();
		}
		m_vars.setVar( var );

		if( sym.m_kind.m_kind == SymbolKinds.ARRAY ) {
			m_log.log( LogLevels.INFO, "; array \"" + sym.m_name + "["
					+ p_arrIdx + "]\"  set to: " + value );
		} else {
			m_log.log( LogLevels.INFO, "; variable \"" + sym.m_name
					+ "\" set to: " + value );
		}

		m_compLog.pop();
	}

	int getParenthesisExpr() {
		int expressionValue;
		match( Token.LPAR );
		expressionValue = expression();
		match( Token.RPAR );
		return expressionValue;
	}
}