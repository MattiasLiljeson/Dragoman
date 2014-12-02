package com.liljeson.mattias.fries.ui

import com.liljeson.mattias.fries.interpretator.RDI;
import com.liljeson.mattias.fries.parser.P1Reader;
import com.liljeson.mattias.fries.shared.Program;
import com.liljeson.mattias.fries.utils.CompLogger;
import com.liljeson.mattias.fries.utils.LogLady;
import com.liljeson.mattias.fries.utils.LogLady.Details;
import com.liljeson.mattias.fries.utils.LogLady.LogLevels;

class Cli {
	static main( args ){
		
		def cli = new CliBuilder(usage: 'friesUi.groovy -[hdlaci] <path to p1 file>')
		// Create the list of options.
		cli.with {
			h longOpt: 'help', 'Show usage information'
			d longOpt: 'log-details', args: 1, argName: 'log-details', 'Details of log output. Can be NONE, [LEVEL] and LEVEL_AND_DATE.'
			l longOpt: 'log-level', args: 1, argName: 'log-level', 'Log level. Can be TRACE, DEBUG, [INFO], WARNING, ERROR, SEVERE, OFF.'
			a longOpt: 'alias', args: 1, argName: 'alias', 'Program alias. Alias for the program, used for program output. If not specified, the program filename is used.'
			c longOpt: 'checkpoints', argName: 'checkpoints', 'Use checkpoints. If not specified, checkpoints are disabled.'
			i longOpt: 'interactive', argName: 'interactive', 'Fetch input from user. If not specified, input is read from a buffer if existing, otherwise a default value is used for all input.'
		}
		
		def options = cli.parse( args )
		if( !options ){
			println "Error when parsing the options! Quitting..."
			return
		}
		
		if( options.h ) {
			// Show usage text when -h or --help option is used.
			cli.usage()
			return
		}
		
		// Handle all non-option arguments.
		String prgFilePath = "prog.p1"
		def extraArguments = options.arguments()
		if( extraArguments ){
			prgFilePath = extraArguments[0]
			if( extraArguments.size() > 1 ){
				println "Too many arguments. Skipping all but the first"
			}
		} else {
			println "You must specify a program to interpret! Quitting..."
			return
		}
		
		def details = Details.LEVEL // Default.
		if ( options.d ) {
			details = Details.valueOf( options.d )
		}
		def level = LogLevels.INFO // Default.
		if ( options.l ) {
			level = LogLevels.valueOf( options.l )
		}
		def alias = prgFilePath // Default.
		if ( options.a ) {
			alias = options.a
		}
		def checkpoints = false // Default.
		if ( options.c ) {
			checkpoints = options.c
		}
		def interactive = false // Default.
		if ( options.i ) {
			interactive = options.i
		}

		LogLady logLady = new LogLady( details, level )
		CompLogger compLady = new CompLogger( logLady )
		RDI rdi = new RDI( logLady, compLady )
		
		String[] programLines
		try{
			programLines = readProgram( prgFilePath )
		} catch( FileNotFoundException e ){
			println "Couldn't find the p1 file! Quitting..."
			return
		} catch( Exception e ){
			println "CHAOS! Quitting..."
			return
		}
		Program program = P1Reader.readProgram( programLines )
		program.m_name = alias;
		rdi.run( program, checkpoints, interactive );
	}
	
	static String[] readProgram( String filepath ){
		return new File(filepath).readLines().toArray(new String[1])
	}
}
