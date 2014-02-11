package com.liljeson.mattias.fries.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class LogLady {
	public enum LogLevels {
		TRACE, DEBUG, INFO, WARNING, ERROR, SEVERE, OFF
	}

	public enum Details {
		NONE, LEVEL, LEVEL_AND_DATE
	}

	LogLevels m_printLevel = LogLevels.ERROR;
	Details m_details = Details.LEVEL_AND_DATE;

	public LogLady() {
		m_details = Details.LEVEL_AND_DATE;
	}

	public LogLady(final Details p_details, final LogLevels p_printLevel) {
		m_details = p_details;
		m_printLevel = p_printLevel;
	}

	public void log(final LogLevels p_level, final String p_msg) {
		if (p_level.compareTo(m_printLevel) >= 0) {
			String msg = p_msg;
			if (m_details.compareTo(Details.LEVEL_AND_DATE) >= 0) {
				System.out.println(new Date().toString() + ", "
						+ p_level.toString() + ": ");
			}
			if (m_details.compareTo(Details.LEVEL) >= 0) {
				msg = p_level.name() + ":\t " + p_msg;
			}
			System.out.println(msg);
		}
	}

	public void write(final String p_msg) {
		System.out.print(p_msg);
	}

	public void write(final int p_i) {
		System.out.print(String.valueOf(p_i));
	}

	public void writeLn() {
		System.out.println("");
	}

	public void writeLn(final int p_i) {
		System.out.println(String.valueOf(p_i));
	}

	public int read() {
		int input = -1;
		try {
			final BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			input = Integer.parseInt(String.valueOf(bufferRead.readLine()));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	// public char read() {
	// return '_';
	// }
}
