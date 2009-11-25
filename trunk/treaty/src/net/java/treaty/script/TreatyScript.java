package net.java.treaty.script;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import net.java.treaty.Contract;
import net.java.treaty.ContractVocabulary;
import net.java.treaty.TreatyException;
import net.java.treaty.script.generated.TreatyLexer;
import net.java.treaty.script.generated.TreatyParser;

public class TreatyScript {
	
	static public Contract fromString(String script, ContractVocabulary vocabulary) throws TreatyException {
		return TreatyScript.fromCharStream(new ANTLRStringStream(script), vocabulary);
	}
	
	static public Contract fromInputStream(InputStream scriptStream, ContractVocabulary vocabulary) throws TreatyException {	
		try {
			return TreatyScript.fromCharStream(new ANTLRInputStream(scriptStream), vocabulary);
		} catch (IOException e) {
			throw new TreatyException(e);
		}
	}
	
	static private Contract fromCharStream(CharStream scriptStream, ContractVocabulary vocabulary) throws TreatyException {
		try {
			TreatyLexer lexer = new TreatyLexer(scriptStream);
			TreatyParser parser = new TreatyParser(new CommonTokenStream(lexer));
		
			return parser.contract(vocabulary);
		} catch (RecognitionException e) {
			throw new TreatyException(e);
		}
	}
}
