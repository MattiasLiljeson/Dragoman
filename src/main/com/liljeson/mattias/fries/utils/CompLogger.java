package com.liljeson.mattias.fries.utils;

import java.util.Stack;

import com.liljeson.mattias.fries.utils.LogLady.Details;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

public class CompLogger {

	Stack<String> m_callStack = new Stack<>();
	LogLady m_log = new LogLady(Details.NONE, LogLevels.ERROR);

	public CompLogger(final LogLady p_lady) {
		if (p_lady != null) {
			m_log = p_lady;
		}
	}

	public void push() {
		push(getCallerName(3));
	}

	public void push(String p_funcName) {
		// int funcNameLen = 40;
		// if (p_funcName.length() > funcNameLen) {
		// int startChop = p_funcName.length() - funcNameLen;
		// int stopChop = p_funcName.length();
		// p_funcName = p_funcName.substring(startChop, stopChop);
		// }
		m_callStack.push(p_funcName);
		printTop("> ");
	}

	public void pop() {
		printTop("< ");
		m_callStack.pop();
	}

	void printTop(final String p_sym) {
		final String out = ";" + stackDepth() + p_sym + m_callStack.peek();
		m_log.log(LogLevels.DEBUG, out);
	}

	public static String getCallerName(final int p_depth) {
		final String raw = Thread.currentThread().getStackTrace()[p_depth]
				.toString();
		String stringToMatch = ".RDI";
		int startOfFunctionName = raw.lastIndexOf(stringToMatch)
				+ stringToMatch.length();
		String niceified = raw.substring(startOfFunctionName, raw.length());
		return niceified;
	}

	String stackDepth() {
		String depthMarker = "";
		for (int i = 0; i < m_callStack.size(); i++) {
			depthMarker += "-";
		}
		return depthMarker;
	}
}
