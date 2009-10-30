// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-10-30 20:34:36

package net.java.treaty.script.generated;

import java.util.List;
import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TreatyLexer extends Lexer {
    public static final int RParen=16;
    public static final int At=20;
    public static final int LParen=15;
    public static final int Trigger=7;
    public static final int LineComment=29;
    public static final int Newline=4;
    public static final int Colon=19;
    public static final int AnnotationValue=6;
    public static final int EOF=-1;
    public static final int IDLetter=26;
    public static final int AnnotationKey=5;
    public static final int Identifier=8;
    public static final int ClassName=23;
    public static final int BlockComment=28;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int ClassNameAttribute=9;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int Not=14;
    public static final int Dot=21;
    public static final int String=17;
    public static final int Or=12;
    public static final int Annotation=24;
    public static final int IDDigit=27;
    public static final int Whitespace=22;
    public static final int ResourceReferenceAttribute=11;
    public static final int Equals=18;
    public static final int NamespaceDelimiter=25;
    public static final int And=13;
    public static final int ResourceTypeAttribute=10;

    private int implicitLineJoiningLevel = 0;

    // Workaround until getText(), and setText() work as expected within lexer fragments
    private String lastAnnotationValue = null;

    private List<Token> tokens = new LinkedList<Token>();

    public void emit(Token token) {
        state.token = token;
        tokens.add(token);
    }

    public Token nextToken() {
        super.nextToken();
        
        if (tokens.size() == 0) {
            return Token.EOF_TOKEN;
        }
        
        return tokens.remove(0);
    }


    // delegates
    // delegators

    public TreatyLexer() {;} 
    public TreatyLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TreatyLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g"; }

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:35:7: ( 'consumer-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:35:9: 'consumer-resource'
            {
            match("consumer-resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:36:7: ( 'supplier-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:36:9: 'supplier-resource'
            {
            match("supplier-resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:37:7: ( 'constraint' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:37:9: 'constraint'
            {
            match("constraint"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:38:7: ( 'mustexist' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:38:9: 'mustexist'
            {
            match("mustexist"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:39:7: ( 'onfailure' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:39:9: 'onfailure'
            {
            match("onfailure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:7: ( 'onsuccess' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:9: 'onsuccess'
            {
            match("onsuccess"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "And"
    public final void mAnd() throws RecognitionException {
        try {
            int _type = And;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:202:13: ( 'and' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:202:15: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "And"

    // $ANTLR start "Or"
    public final void mOr() throws RecognitionException {
        try {
            int _type = Or;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:203:13: ( 'or' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:203:15: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Or"

    // $ANTLR start "Not"
    public final void mNot() throws RecognitionException {
        try {
            int _type = Not;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:204:13: ( 'not' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:204:15: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Not"

    // $ANTLR start "LParen"
    public final void mLParen() throws RecognitionException {
        try {
            int _type = LParen;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:206:13: ( '(' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:206:15: '('
            {
            match('('); 
             implicitLineJoiningLevel += 1; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LParen"

    // $ANTLR start "RParen"
    public final void mRParen() throws RecognitionException {
        try {
            int _type = RParen;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:13: ( ')' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:15: ')'
            {
            match(')'); 
             implicitLineJoiningLevel -= 1; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RParen"

    // $ANTLR start "Equals"
    public final void mEquals() throws RecognitionException {
        try {
            int _type = Equals;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:209:13: ( '=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:209:15: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Equals"

    // $ANTLR start "Colon"
    public final void mColon() throws RecognitionException {
        try {
            int _type = Colon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:210:13: ( ':' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:210:15: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Colon"

    // $ANTLR start "At"
    public final void mAt() throws RecognitionException {
        try {
            int _type = At;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:13: ( '@' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:15: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "At"

    // $ANTLR start "Dot"
    public final void mDot() throws RecognitionException {
        try {
            int _type = Dot;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:212:13: ( '.' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:212:15: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Dot"

    // $ANTLR start "Trigger"
    public final void mTrigger() throws RecognitionException {
        try {
            int _type = Trigger;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String1=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:5: ( 'on' Whitespace String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:9: 'on' Whitespace String
            {
            match("on"); 

            mWhitespace(); 
            int String1Start236 = getCharIndex();
            mString(); 
            String1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String1Start236, getCharIndex()-1);

                        emit(new CommonToken(Trigger, (String1!=null?String1.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Trigger"

    // $ANTLR start "ClassNameAttribute"
    public final void mClassNameAttribute() throws RecognitionException {
        try {
            int _type = ClassNameAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token ClassName2=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:5: ( 'name' Equals ClassName )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:9: 'name' Equals ClassName
            {
            match("name"); 

            mEquals(); 
            int ClassName2Start270 = getCharIndex();
            mClassName(); 
            ClassName2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, ClassName2Start270, getCharIndex()-1);

                        emit(new CommonToken(ClassNameAttribute, (ClassName2!=null?ClassName2.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ClassNameAttribute"

    // $ANTLR start "ClassName"
    public final void mClassName() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:5: ( Identifier ( Dot Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:9: Identifier ( Dot Identifier )*
            {
            mIdentifier(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:20: ( Dot Identifier )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='.') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:21: Dot Identifier
            	    {
            	    mDot(); 
            	    mIdentifier(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "ClassName"

    // $ANTLR start "ResourceTypeAttribute"
    public final void mResourceTypeAttribute() throws RecognitionException {
        try {
            int _type = ResourceTypeAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String3=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:5: ( 'type' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:9: 'type' Equals String
            {
            match("type"); 

            mEquals(); 
            int String3Start333 = getCharIndex();
            mString(); 
            String3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String3Start333, getCharIndex()-1);

                        emit(new CommonToken(ResourceTypeAttribute, (String3!=null?String3.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ResourceTypeAttribute"

    // $ANTLR start "ResourceReferenceAttribute"
    public final void mResourceReferenceAttribute() throws RecognitionException {
        try {
            int _type = ResourceReferenceAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String4=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:241:5: ( 'ref' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:241:9: 'ref' Equals String
            {
            match("ref"); 

            mEquals(); 
            int String4Start367 = getCharIndex();
            mString(); 
            String4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String4Start367, getCharIndex()-1);

                        emit(new CommonToken(ResourceReferenceAttribute, (String4!=null?String4.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ResourceReferenceAttribute"

    // $ANTLR start "Annotation"
    public final void mAnnotation() throws RecognitionException {
        try {
            int _type = Annotation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token key=null;
            Token value=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:248:5: ( At key= AnnotationKey Equals value= AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:248:9: At key= AnnotationKey Equals value= AnnotationValue
            {
            mAt(); 
            int keyStart401 = getCharIndex();
            mAnnotationKey(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart401, getCharIndex()-1);
            mEquals(); 
            int valueStart407 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart407, getCharIndex()-1);

                        emit(new CommonToken(AnnotationKey, (key!=null?key.getText():null)));
                        emit(new CommonToken(AnnotationValue, lastAnnotationValue));
                        emit(new CommonToken(Newline, "\n"));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Annotation"

    // $ANTLR start "AnnotationKey"
    public final void mAnnotationKey() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:258:5: ( Identifier ( NamespaceDelimiter Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:258:9: Identifier ( NamespaceDelimiter Identifier )*
            {
            mIdentifier(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:258:20: ( NamespaceDelimiter Identifier )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.'||LA2_0==':') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:258:21: NamespaceDelimiter Identifier
            	    {
            	    mNamespaceDelimiter(); 
            	    mIdentifier(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "AnnotationKey"

    // $ANTLR start "NamespaceDelimiter"
    public final void mNamespaceDelimiter() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:263:5: ( Colon | Dot )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            {
            if ( input.LA(1)=='.'||input.LA(1)==':' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NamespaceDelimiter"

    // $ANTLR start "AnnotationValue"
    public final void mAnnotationValue() throws RecognitionException {
        try {
            Token Newline5=null;

             int startIndex = getCharIndex(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:269:5: ( ( options {greedy=false; } : . )* Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:269:9: ( options {greedy=false; } : . )* Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:269:9: ( options {greedy=false; } : . )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\r') ) {
                    alt3=2;
                }
                else if ( (LA3_0=='\n') ) {
                    alt3=2;
                }
                else if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:269:39: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            int Newline5Start519 = getCharIndex();
            mNewline(); 
            Newline5 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Newline5Start519, getCharIndex()-1);

                        int endIndex = getCharIndex() - 1;
                        int whitespace = (Newline5!=null?Newline5.getText():null).length();
                        
                        // Workaround until getText(), and setText() work as expected within lexer fragments
                        lastAnnotationValue = input.substring(startIndex, endIndex - whitespace).trim();
                    

            }

        }
        finally {
        }
    }
    // $ANTLR end "AnnotationValue"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:280:5: ( IDLetter ( IDLetter | IDDigit )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:280:9: IDLetter ( IDLetter | IDDigit )*
            {
            mIDLetter(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:280:18: ( IDLetter | IDDigit )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "IDLetter"
    public final void mIDLetter() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:285:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IDLetter"

    // $ANTLR start "IDDigit"
    public final void mIDDigit() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:5: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "IDDigit"

    // $ANTLR start "Newline"
    public final void mNewline() throws RecognitionException {
        try {
            int _type = Newline;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:5: ( ( '\\r' )? '\\n' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:9: ( '\\r' )? '\\n'
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:9: ( '\\r' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

                        if (implicitLineJoiningLevel > 0) {
                            _channel=HIDDEN;
                        }
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Newline"

    // $ANTLR start "Whitespace"
    public final void mWhitespace() throws RecognitionException {
        try {
            int _type = Whitespace;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:306:5: ( ( ' ' | '\\t' )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:306:9: ( ' ' | '\\t' )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:306:9: ( ' ' | '\\t' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\t'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Whitespace"

    // $ANTLR start "BlockComment"
    public final void mBlockComment() throws RecognitionException {
        try {
            int _type = BlockComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:311:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:311:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:311:14: ( options {greedy=false; } : . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='/') ) {
                        alt7=2;
                    }
                    else if ( ((LA7_1>='\u0000' && LA7_1<='.')||(LA7_1>='0' && LA7_1<='\uFFFF')) ) {
                        alt7=1;
                    }


                }
                else if ( ((LA7_0>='\u0000' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:311:44: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BlockComment"

    // $ANTLR start "LineComment"
    public final void mLineComment() throws RecognitionException {
        try {
            int _type = LineComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:316:5: ( '//' (~ ( '\\n' ) )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:316:9: '//' (~ ( '\\n' ) )*
            {
            match("//"); 

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:316:14: (~ ( '\\n' ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='\t')||(LA8_0>='\u000B' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:316:14: ~ ( '\\n' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LineComment"

    // $ANTLR start "String"
    public final void mString() throws RecognitionException {
        try {
            int _type = String;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:320:5: ( (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:320:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:320:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\u0000' && LA9_0<='\b')||LA9_0=='\u000B'||(LA9_0>='\u000E' && LA9_0<='\u001F')||(LA9_0>='!' && LA9_0<='\uFFFF')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:320:9: ~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\b')||input.LA(1)=='\u000B'||(input.LA(1)>='\u000E' && input.LA(1)<='\u001F')||(input.LA(1)>='!' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "String"

    public void mTokens() throws RecognitionException {
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:8: ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | And | Or | Not | LParen | RParen | Equals | Colon | At | Dot | Trigger | ClassNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | String )
        int alt10=26;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:10: T__30
                {
                mT__30(); 

                }
                break;
            case 2 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:16: T__31
                {
                mT__31(); 

                }
                break;
            case 3 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:22: T__32
                {
                mT__32(); 

                }
                break;
            case 4 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:28: T__33
                {
                mT__33(); 

                }
                break;
            case 5 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:34: T__34
                {
                mT__34(); 

                }
                break;
            case 6 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:40: T__35
                {
                mT__35(); 

                }
                break;
            case 7 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:46: And
                {
                mAnd(); 

                }
                break;
            case 8 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:50: Or
                {
                mOr(); 

                }
                break;
            case 9 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:53: Not
                {
                mNot(); 

                }
                break;
            case 10 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:57: LParen
                {
                mLParen(); 

                }
                break;
            case 11 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:64: RParen
                {
                mRParen(); 

                }
                break;
            case 12 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:71: Equals
                {
                mEquals(); 

                }
                break;
            case 13 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:78: Colon
                {
                mColon(); 

                }
                break;
            case 14 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:84: At
                {
                mAt(); 

                }
                break;
            case 15 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:87: Dot
                {
                mDot(); 

                }
                break;
            case 16 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:91: Trigger
                {
                mTrigger(); 

                }
                break;
            case 17 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:99: ClassNameAttribute
                {
                mClassNameAttribute(); 

                }
                break;
            case 18 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:118: ResourceTypeAttribute
                {
                mResourceTypeAttribute(); 

                }
                break;
            case 19 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:140: ResourceReferenceAttribute
                {
                mResourceReferenceAttribute(); 

                }
                break;
            case 20 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:167: Annotation
                {
                mAnnotation(); 

                }
                break;
            case 21 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:178: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 22 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:189: Newline
                {
                mNewline(); 

                }
                break;
            case 23 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:197: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 24 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:208: BlockComment
                {
                mBlockComment(); 

                }
                break;
            case 25 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:221: LineComment
                {
                mLineComment(); 

                }
                break;
            case 26 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:233: String
                {
                mString(); 

                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\23\6\25\1\36\1\37\1\40\1\41\1\42\1\44\3\25\2\uffff\1\23\1\uffff"+
        "\1\25\1\uffff\4\25\1\57\3\25\5\uffff\1\23\1\uffff\2\25\1\23\1\73"+
        "\5\25\2\uffff\1\102\1\103\1\25\3\23\2\25\1\23\1\uffff\1\23\1\uffff"+
        "\1\73\5\25\2\uffff\1\25\1\23\1\uffff\1\23\1\25\1\124\1\71\6\25\2"+
        "\23\1\135\1\uffff\1\124\6\25\1\145\1\uffff\1\135\6\25\1\uffff\1"+
        "\145\1\23\6\25\1\145\1\23\1\25\1\23\1\171\1\172\1\173\1\145\1\23"+
        "\1\175\1\23\3\uffff\1\23\1\uffff\13\23\1\u008b\1\u008c\2\uffff";
    static final String DFA10_eofS =
        "\u008d\uffff";
    static final String DFA10_minS =
        "\1\11\17\0\2\uffff\1\52\1\uffff\1\0\1\uffff\10\0\5\uffff\1\56\1"+
        "\uffff\11\0\2\uffff\3\0\1\56\1\101\4\0\1\uffff\1\0\1\uffff\6\0\2"+
        "\uffff\1\0\1\56\1\uffff\12\0\1\101\1\56\1\0\1\uffff\10\0\1\uffff"+
        "\7\0\1\uffff\1\0\1\101\7\0\1\162\1\0\1\162\4\0\1\145\1\0\1\145\3"+
        "\uffff\1\163\1\uffff\1\163\2\157\2\165\2\162\2\143\2\145\2\0\2\uffff";
    static final String DFA10_maxS =
        "\1\172\17\uffff\2\uffff\1\57\1\uffff\1\uffff\1\uffff\10\uffff\5"+
        "\uffff\1\172\1\uffff\11\uffff\2\uffff\3\uffff\2\172\4\uffff\1\uffff"+
        "\1\uffff\1\uffff\6\uffff\2\uffff\1\uffff\1\172\1\uffff\12\uffff"+
        "\2\172\1\uffff\1\uffff\10\uffff\1\uffff\7\uffff\1\uffff\1\uffff"+
        "\1\172\7\uffff\1\162\1\uffff\1\162\4\uffff\1\145\1\uffff\1\145\3"+
        "\uffff\1\163\1\uffff\1\163\2\157\2\165\2\162\2\143\2\145\2\uffff"+
        "\2\uffff";
    static final String DFA10_acceptS =
        "\20\uffff\1\26\1\27\1\uffff\1\32\1\uffff\1\25\10\uffff\1\12\1\13"+
        "\1\14\1\15\1\16\1\uffff\1\17\11\uffff\1\20\1\10\11\uffff\1\30\1"+
        "\uffff\1\31\6\uffff\1\7\1\11\2\uffff\1\24\15\uffff\1\23\10\uffff"+
        "\1\22\7\uffff\1\21\23\uffff\1\4\1\5\1\6\1\uffff\1\3\15\uffff\1\1"+
        "\1\2";
    static final String DFA10_specialS =
        "\1\uffff\1\124\1\5\1\102\1\12\1\117\1\113\1\45\1\32\1\7\1\10\1\125"+
        "\1\127\1\73\1\21\1\16\4\uffff\1\122\1\uffff\1\11\1\43\1\57\1\131"+
        "\1\33\1\101\1\115\1\4\7\uffff\1\1\1\14\1\70\1\61\1\120\1\37\1\60"+
        "\1\77\1\104\2\uffff\1\24\1\25\1\3\2\uffff\1\36\1\2\1\67\1\22\1\uffff"+
        "\1\6\1\uffff\1\0\1\123\1\40\1\55\1\76\1\106\2\uffff\1\105\2\uffff"+
        "\1\130\1\52\1\116\1\15\1\26\1\47\1\41\1\56\1\75\1\107\2\uffff\1"+
        "\44\1\uffff\1\13\1\27\1\46\1\42\1\53\1\74\1\110\1\100\1\uffff\1"+
        "\23\1\30\1\65\1\34\1\54\1\72\1\111\1\uffff\1\103\1\uffff\1\31\1"+
        "\66\1\35\1\51\1\71\1\112\1\64\1\uffff\1\62\1\uffff\1\50\1\17\1\20"+
        "\1\114\1\uffff\1\63\21\uffff\1\121\1\126\2\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\21\1\20\2\uffff\1\20\22\uffff\1\21\7\uffff\1\7\1\10\4\uffff"+
            "\1\14\1\22\12\uffff\1\12\2\uffff\1\11\2\uffff\1\13\32\17\4\uffff"+
            "\1\17\1\uffff\1\5\1\17\1\1\11\17\1\3\1\6\1\4\2\17\1\16\1\2\1"+
            "\15\6\17",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\16\26\1\24\13\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\24\26\1\27\5\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\24\26\1\30\5\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\15\26\1\31\3\26\1\32\10\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\15\26\1\33\14\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\1\35\15\26\1\34\13\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\40\23\32\43\4\23"+
            "\1\43\1\23\32\43\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\30\26\1\45\1\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\46\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "",
            "",
            "\1\47\4\uffff\1\50",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\15\26\1\51\14\26\uff85\23",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\17\26\1\52\12\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\22\26\1\53\7\26\uff85\23",
            "\11\23\1\56\1\uffff\1\23\2\uffff\22\23\1\56\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\5\26\1\54\14\26\1\55\7\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\3\26\1\60\26\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\23\26\1\61\6\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\14\26\1\62\15\26\uff85\23",
            "",
            "",
            "",
            "",
            "",
            "\1\64\1\uffff\12\63\1\64\2\uffff\1\65\3\uffff\32\63\4\uffff"+
            "\1\63\1\uffff\32\63",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\17\26\1\66\12\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\5\26\1\67\24\26\uff85\23",
            "\11\72\2\71\1\72\2\71\22\72\1\71\11\72\1\70\uffd5\72",
            "\11\74\2\uffff\1\74\2\uffff\22\74\1\uffff\uffdf\74",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\22\26\1\75\7\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\17\26\1\76\12\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\23\26\1\77\6\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\1\100\31\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\24\26\1\101\5\26\uff85\23",
            "",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\104\25\26\uff85\23",
            "\1\64\1\uffff\12\63\1\64\2\uffff\1\65\3\uffff\32\63\4\uffff"+
            "\1\63\1\uffff\32\63",
            "\32\105\4\uffff\1\105\1\uffff\32\105",
            "\11\107\2\106\1\107\2\106\22\107\1\106\uffdf\107",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\110\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\3\23"+
            "\1\111\3\23\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\72\2\71\1\72\2\71\22\72\1\71\11\72\1\70\4\72\1\112\uffd0"+
            "\72",
            "",
            "\11\72\2\71\1\72\2\71\22\72\1\71\11\72\1\70\uffd5\72",
            "",
            "\11\74\2\uffff\1\74\2\uffff\22\74\1\uffff\uffdf\74",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\23\26\1\114\1\113\5\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\13\26\1\115\16\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\116\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\10\26\1\117\21\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\2\26\1\120\27\26\uff85\23",
            "",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\3\23"+
            "\1\121\3\23\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\1\64\1\uffff\12\122\1\64\2\uffff\1\65\3\uffff\32\122\4\uffff"+
            "\1\122\1\uffff\32\122",
            "",
            "\11\107\2\106\1\107\2\106\22\107\1\106\uffdf\107",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\3\23"+
            "\1\123\3\23\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\125\2\uffff\1\125\2\uffff\22\125\1\uffff\uffdf\125",
            "\11\72\2\uffff\1\72\2\uffff\22\72\1\uffff\11\72\1\70\uffd5"+
            "\72",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\14\26\1\126\15\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\21\26\1\127\10\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\10\26\1\130\21\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\27\26\1\131\2\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\13\26\1\132\16\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\2\26\1\133\27\26\uff85\23",
            "\32\134\4\uffff\1\134\1\uffff\32\134",
            "\1\64\1\uffff\12\122\1\64\2\uffff\1\65\3\uffff\32\122\4\uffff"+
            "\1\122\1\uffff\32\122",
            "\11\136\2\uffff\1\136\2\uffff\22\136\1\uffff\uffdf\136",
            "",
            "\11\125\2\uffff\1\125\2\uffff\22\125\1\uffff\uffdf\125",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\137\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\1\140\31\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\141\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\10\26\1\142\21\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\24\26\1\143\5\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\144\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\15\23\1\147\1\23"+
            "\12\146\7\23\32\146\4\23\1\146\1\23\32\146\uff85\23",
            "",
            "\11\136\2\uffff\1\136\2\uffff\22\136\1\uffff\uffdf\136",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\21\26\1\150\10\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\10\26\1\151\21\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\21\26\1\152\10\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\22\26\1\153\7\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\21\26\1\154\10\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\22\26\1\155\7\26\uff85\23",
            "",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\15\23\1\147\1\23"+
            "\12\146\7\23\32\146\4\23\1\146\1\23\32\146\uff85\23",
            "\32\156\4\uffff\1\156\1\uffff\32\156",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\14\23\1\157\2\23"+
            "\12\26\7\23\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\15\26\1\160\14\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\14\23\1\161\2\23"+
            "\12\26\7\23\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\23\26\1\162\6\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\4\26\1\163\25\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\22\26\1\164\7\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\15\23\1\147\1\23"+
            "\12\165\7\23\32\165\4\23\1\165\1\23\32\165\uff85\23",
            "\1\166",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\23\26\1\167\6\26\uff85\23",
            "\1\170",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\15\23\1\147\1\23"+
            "\12\165\7\23\32\165\4\23\1\165\1\23\32\165\uff85\23",
            "\1\174",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\17\23\12\26\7\23"+
            "\32\26\4\23\1\26\1\23\32\26\uff85\23",
            "\1\176",
            "",
            "",
            "",
            "\1\177",
            "",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "\11\23\2\uffff\1\23\2\uffff\22\23\1\uffff\uffdf\23",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | And | Or | Not | LParen | RParen | Equals | Colon | At | Dot | Trigger | ClassNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | String );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_60 = input.LA(1);

                        s = -1;
                        if ( ((LA10_60>='\u0000' && LA10_60<='\b')||LA10_60=='\u000B'||(LA10_60>='\u000E' && LA10_60<='\u001F')||(LA10_60>='!' && LA10_60<='\uFFFF')) ) {s = 60;}

                        else s = 59;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_37 = input.LA(1);

                        s = -1;
                        if ( (LA10_37=='p') ) {s = 54;}

                        else if ( ((LA10_37>='0' && LA10_37<='9')||(LA10_37>='A' && LA10_37<='Z')||LA10_37=='_'||(LA10_37>='a' && LA10_37<='o')||(LA10_37>='q' && LA10_37<='z')) ) {s = 22;}

                        else if ( ((LA10_37>='\u0000' && LA10_37<='\b')||LA10_37=='\u000B'||(LA10_37>='\u000E' && LA10_37<='\u001F')||(LA10_37>='!' && LA10_37<='/')||(LA10_37>=':' && LA10_37<='@')||(LA10_37>='[' && LA10_37<='^')||LA10_37=='`'||(LA10_37>='{' && LA10_37<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA10_54 = input.LA(1);

                        s = -1;
                        if ( (LA10_54=='e') ) {s = 72;}

                        else if ( ((LA10_54>='0' && LA10_54<='9')||(LA10_54>='A' && LA10_54<='Z')||LA10_54=='_'||(LA10_54>='a' && LA10_54<='d')||(LA10_54>='f' && LA10_54<='z')) ) {s = 22;}

                        else if ( ((LA10_54>='\u0000' && LA10_54<='\b')||LA10_54=='\u000B'||(LA10_54>='\u000E' && LA10_54<='\u001F')||(LA10_54>='!' && LA10_54<='/')||(LA10_54>=':' && LA10_54<='@')||(LA10_54>='[' && LA10_54<='^')||LA10_54=='`'||(LA10_54>='{' && LA10_54<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA10_50 = input.LA(1);

                        s = -1;
                        if ( (LA10_50=='e') ) {s = 68;}

                        else if ( ((LA10_50>='0' && LA10_50<='9')||(LA10_50>='A' && LA10_50<='Z')||LA10_50=='_'||(LA10_50>='a' && LA10_50<='d')||(LA10_50>='f' && LA10_50<='z')) ) {s = 22;}

                        else if ( ((LA10_50>='\u0000' && LA10_50<='\b')||LA10_50=='\u000B'||(LA10_50>='\u000E' && LA10_50<='\u001F')||(LA10_50>='!' && LA10_50<='/')||(LA10_50>=':' && LA10_50<='@')||(LA10_50>='[' && LA10_50<='^')||LA10_50=='`'||(LA10_50>='{' && LA10_50<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA10_29 = input.LA(1);

                        s = -1;
                        if ( (LA10_29=='m') ) {s = 50;}

                        else if ( ((LA10_29>='0' && LA10_29<='9')||(LA10_29>='A' && LA10_29<='Z')||LA10_29=='_'||(LA10_29>='a' && LA10_29<='l')||(LA10_29>='n' && LA10_29<='z')) ) {s = 22;}

                        else if ( ((LA10_29>='\u0000' && LA10_29<='\b')||LA10_29=='\u000B'||(LA10_29>='\u000E' && LA10_29<='\u001F')||(LA10_29>='!' && LA10_29<='/')||(LA10_29>=':' && LA10_29<='@')||(LA10_29>='[' && LA10_29<='^')||LA10_29=='`'||(LA10_29>='{' && LA10_29<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA10_2 = input.LA(1);

                        s = -1;
                        if ( (LA10_2=='u') ) {s = 23;}

                        else if ( ((LA10_2>='0' && LA10_2<='9')||(LA10_2>='A' && LA10_2<='Z')||LA10_2=='_'||(LA10_2>='a' && LA10_2<='t')||(LA10_2>='v' && LA10_2<='z')) ) {s = 22;}

                        else if ( ((LA10_2>='\u0000' && LA10_2<='\b')||LA10_2=='\u000B'||(LA10_2>='\u000E' && LA10_2<='\u001F')||(LA10_2>='!' && LA10_2<='/')||(LA10_2>=':' && LA10_2<='@')||(LA10_2>='[' && LA10_2<='^')||LA10_2=='`'||(LA10_2>='{' && LA10_2<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA10_58 = input.LA(1);

                        s = -1;
                        if ( (LA10_58=='*') ) {s = 56;}

                        else if ( ((LA10_58>='\u0000' && LA10_58<='\b')||LA10_58=='\u000B'||(LA10_58>='\u000E' && LA10_58<='\u001F')||(LA10_58>='!' && LA10_58<=')')||(LA10_58>='+' && LA10_58<='\uFFFF')) ) {s = 58;}

                        else if ( ((LA10_58>='\t' && LA10_58<='\n')||(LA10_58>='\f' && LA10_58<='\r')||LA10_58==' ') ) {s = 57;}

                        else s = 19;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA10_9 = input.LA(1);

                        s = -1;
                        if ( ((LA10_9>='\u0000' && LA10_9<='\b')||LA10_9=='\u000B'||(LA10_9>='\u000E' && LA10_9<='\u001F')||(LA10_9>='!' && LA10_9<='\uFFFF')) ) {s = 19;}

                        else s = 32;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA10_10 = input.LA(1);

                        s = -1;
                        if ( ((LA10_10>='\u0000' && LA10_10<='\b')||LA10_10=='\u000B'||(LA10_10>='\u000E' && LA10_10<='\u001F')||(LA10_10>='!' && LA10_10<='\uFFFF')) ) {s = 19;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA10_22 = input.LA(1);

                        s = -1;
                        if ( ((LA10_22>='0' && LA10_22<='9')||(LA10_22>='A' && LA10_22<='Z')||LA10_22=='_'||(LA10_22>='a' && LA10_22<='z')) ) {s = 22;}

                        else if ( ((LA10_22>='\u0000' && LA10_22<='\b')||LA10_22=='\u000B'||(LA10_22>='\u000E' && LA10_22<='\u001F')||(LA10_22>='!' && LA10_22<='/')||(LA10_22>=':' && LA10_22<='@')||(LA10_22>='[' && LA10_22<='^')||LA10_22=='`'||(LA10_22>='{' && LA10_22<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA10_4 = input.LA(1);

                        s = -1;
                        if ( (LA10_4=='n') ) {s = 25;}

                        else if ( (LA10_4=='r') ) {s = 26;}

                        else if ( ((LA10_4>='0' && LA10_4<='9')||(LA10_4>='A' && LA10_4<='Z')||LA10_4=='_'||(LA10_4>='a' && LA10_4<='m')||(LA10_4>='o' && LA10_4<='q')||(LA10_4>='s' && LA10_4<='z')) ) {s = 22;}

                        else if ( ((LA10_4>='\u0000' && LA10_4<='\b')||LA10_4=='\u000B'||(LA10_4>='\u000E' && LA10_4<='\u001F')||(LA10_4>='!' && LA10_4<='/')||(LA10_4>=':' && LA10_4<='@')||(LA10_4>='[' && LA10_4<='^')||LA10_4=='`'||(LA10_4>='{' && LA10_4<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA10_85 = input.LA(1);

                        s = -1;
                        if ( ((LA10_85>='\u0000' && LA10_85<='\b')||LA10_85=='\u000B'||(LA10_85>='\u000E' && LA10_85<='\u001F')||(LA10_85>='!' && LA10_85<='\uFFFF')) ) {s = 85;}

                        else s = 84;

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA10_38 = input.LA(1);

                        s = -1;
                        if ( (LA10_38=='f') ) {s = 55;}

                        else if ( ((LA10_38>='0' && LA10_38<='9')||(LA10_38>='A' && LA10_38<='Z')||LA10_38=='_'||(LA10_38>='a' && LA10_38<='e')||(LA10_38>='g' && LA10_38<='z')) ) {s = 22;}

                        else if ( ((LA10_38>='\u0000' && LA10_38<='\b')||LA10_38=='\u000B'||(LA10_38>='\u000E' && LA10_38<='\u001F')||(LA10_38>='!' && LA10_38<='/')||(LA10_38>=':' && LA10_38<='@')||(LA10_38>='[' && LA10_38<='^')||LA10_38=='`'||(LA10_38>='{' && LA10_38<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA10_74 = input.LA(1);

                        s = -1;
                        if ( (LA10_74=='*') ) {s = 56;}

                        else if ( ((LA10_74>='\u0000' && LA10_74<='\b')||LA10_74=='\u000B'||(LA10_74>='\u000E' && LA10_74<='\u001F')||(LA10_74>='!' && LA10_74<=')')||(LA10_74>='+' && LA10_74<='\uFFFF')) ) {s = 58;}

                        else s = 57;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA10_15 = input.LA(1);

                        s = -1;
                        if ( ((LA10_15>='0' && LA10_15<='9')||(LA10_15>='A' && LA10_15<='Z')||LA10_15=='_'||(LA10_15>='a' && LA10_15<='z')) ) {s = 22;}

                        else if ( ((LA10_15>='\u0000' && LA10_15<='\b')||LA10_15=='\u000B'||(LA10_15>='\u000E' && LA10_15<='\u001F')||(LA10_15>='!' && LA10_15<='/')||(LA10_15>=':' && LA10_15<='@')||(LA10_15>='[' && LA10_15<='^')||LA10_15=='`'||(LA10_15>='{' && LA10_15<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA10_115 = input.LA(1);

                        s = -1;
                        if ( ((LA10_115>='0' && LA10_115<='9')||(LA10_115>='A' && LA10_115<='Z')||LA10_115=='_'||(LA10_115>='a' && LA10_115<='z')) ) {s = 22;}

                        else if ( ((LA10_115>='\u0000' && LA10_115<='\b')||LA10_115=='\u000B'||(LA10_115>='\u000E' && LA10_115<='\u001F')||(LA10_115>='!' && LA10_115<='/')||(LA10_115>=':' && LA10_115<='@')||(LA10_115>='[' && LA10_115<='^')||LA10_115=='`'||(LA10_115>='{' && LA10_115<='\uFFFF')) ) {s = 19;}

                        else s = 122;

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA10_116 = input.LA(1);

                        s = -1;
                        if ( ((LA10_116>='0' && LA10_116<='9')||(LA10_116>='A' && LA10_116<='Z')||LA10_116=='_'||(LA10_116>='a' && LA10_116<='z')) ) {s = 22;}

                        else if ( ((LA10_116>='\u0000' && LA10_116<='\b')||LA10_116=='\u000B'||(LA10_116>='\u000E' && LA10_116<='\u001F')||(LA10_116>='!' && LA10_116<='/')||(LA10_116>=':' && LA10_116<='@')||(LA10_116>='[' && LA10_116<='^')||LA10_116=='`'||(LA10_116>='{' && LA10_116<='\uFFFF')) ) {s = 19;}

                        else s = 123;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA10_14 = input.LA(1);

                        s = -1;
                        if ( (LA10_14=='e') ) {s = 38;}

                        else if ( ((LA10_14>='0' && LA10_14<='9')||(LA10_14>='A' && LA10_14<='Z')||LA10_14=='_'||(LA10_14>='a' && LA10_14<='d')||(LA10_14>='f' && LA10_14<='z')) ) {s = 22;}

                        else if ( ((LA10_14>='\u0000' && LA10_14<='\b')||LA10_14=='\u000B'||(LA10_14>='\u000E' && LA10_14<='\u001F')||(LA10_14>='!' && LA10_14<='/')||(LA10_14>=':' && LA10_14<='@')||(LA10_14>='[' && LA10_14<='^')||LA10_14=='`'||(LA10_14>='{' && LA10_14<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA10_56 = input.LA(1);

                        s = -1;
                        if ( (LA10_56=='/') ) {s = 74;}

                        else if ( (LA10_56=='*') ) {s = 56;}

                        else if ( ((LA10_56>='\u0000' && LA10_56<='\b')||LA10_56=='\u000B'||(LA10_56>='\u000E' && LA10_56<='\u001F')||(LA10_56>='!' && LA10_56<=')')||(LA10_56>='+' && LA10_56<='.')||(LA10_56>='0' && LA10_56<='\uFFFF')) ) {s = 58;}

                        else if ( ((LA10_56>='\t' && LA10_56<='\n')||(LA10_56>='\f' && LA10_56<='\r')||LA10_56==' ') ) {s = 57;}

                        else s = 19;

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA10_94 = input.LA(1);

                        s = -1;
                        if ( ((LA10_94>='\u0000' && LA10_94<='\b')||LA10_94=='\u000B'||(LA10_94>='\u000E' && LA10_94<='\u001F')||(LA10_94>='!' && LA10_94<='\uFFFF')) ) {s = 94;}

                        else s = 93;

                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA10_48 = input.LA(1);

                        s = -1;
                        if ( ((LA10_48>='0' && LA10_48<='9')||(LA10_48>='A' && LA10_48<='Z')||LA10_48=='_'||(LA10_48>='a' && LA10_48<='z')) ) {s = 22;}

                        else if ( ((LA10_48>='\u0000' && LA10_48<='\b')||LA10_48=='\u000B'||(LA10_48>='\u000E' && LA10_48<='\u001F')||(LA10_48>='!' && LA10_48<='/')||(LA10_48>=':' && LA10_48<='@')||(LA10_48>='[' && LA10_48<='^')||LA10_48=='`'||(LA10_48>='{' && LA10_48<='\uFFFF')) ) {s = 19;}

                        else s = 66;

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA10_49 = input.LA(1);

                        s = -1;
                        if ( ((LA10_49>='0' && LA10_49<='9')||(LA10_49>='A' && LA10_49<='Z')||LA10_49=='_'||(LA10_49>='a' && LA10_49<='z')) ) {s = 22;}

                        else if ( ((LA10_49>='\u0000' && LA10_49<='\b')||LA10_49=='\u000B'||(LA10_49>='\u000E' && LA10_49<='\u001F')||(LA10_49>='!' && LA10_49<='/')||(LA10_49>=':' && LA10_49<='@')||(LA10_49>='[' && LA10_49<='^')||LA10_49=='`'||(LA10_49>='{' && LA10_49<='\uFFFF')) ) {s = 19;}

                        else s = 67;

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA10_75 = input.LA(1);

                        s = -1;
                        if ( (LA10_75=='m') ) {s = 86;}

                        else if ( ((LA10_75>='0' && LA10_75<='9')||(LA10_75>='A' && LA10_75<='Z')||LA10_75=='_'||(LA10_75>='a' && LA10_75<='l')||(LA10_75>='n' && LA10_75<='z')) ) {s = 22;}

                        else if ( ((LA10_75>='\u0000' && LA10_75<='\b')||LA10_75=='\u000B'||(LA10_75>='\u000E' && LA10_75<='\u001F')||(LA10_75>='!' && LA10_75<='/')||(LA10_75>=':' && LA10_75<='@')||(LA10_75>='[' && LA10_75<='^')||LA10_75=='`'||(LA10_75>='{' && LA10_75<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA10_86 = input.LA(1);

                        s = -1;
                        if ( (LA10_86=='e') ) {s = 95;}

                        else if ( ((LA10_86>='0' && LA10_86<='9')||(LA10_86>='A' && LA10_86<='Z')||LA10_86=='_'||(LA10_86>='a' && LA10_86<='d')||(LA10_86>='f' && LA10_86<='z')) ) {s = 22;}

                        else if ( ((LA10_86>='\u0000' && LA10_86<='\b')||LA10_86=='\u000B'||(LA10_86>='\u000E' && LA10_86<='\u001F')||(LA10_86>='!' && LA10_86<='/')||(LA10_86>=':' && LA10_86<='@')||(LA10_86>='[' && LA10_86<='^')||LA10_86=='`'||(LA10_86>='{' && LA10_86<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA10_95 = input.LA(1);

                        s = -1;
                        if ( (LA10_95=='r') ) {s = 104;}

                        else if ( ((LA10_95>='0' && LA10_95<='9')||(LA10_95>='A' && LA10_95<='Z')||LA10_95=='_'||(LA10_95>='a' && LA10_95<='q')||(LA10_95>='s' && LA10_95<='z')) ) {s = 22;}

                        else if ( ((LA10_95>='\u0000' && LA10_95<='\b')||LA10_95=='\u000B'||(LA10_95>='\u000E' && LA10_95<='\u001F')||(LA10_95>='!' && LA10_95<='/')||(LA10_95>=':' && LA10_95<='@')||(LA10_95>='[' && LA10_95<='^')||LA10_95=='`'||(LA10_95>='{' && LA10_95<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA10_104 = input.LA(1);

                        s = -1;
                        if ( (LA10_104=='-') ) {s = 111;}

                        else if ( ((LA10_104>='0' && LA10_104<='9')||(LA10_104>='A' && LA10_104<='Z')||LA10_104=='_'||(LA10_104>='a' && LA10_104<='z')) ) {s = 22;}

                        else if ( ((LA10_104>='\u0000' && LA10_104<='\b')||LA10_104=='\u000B'||(LA10_104>='\u000E' && LA10_104<='\u001F')||(LA10_104>='!' && LA10_104<=',')||(LA10_104>='.' && LA10_104<='/')||(LA10_104>=':' && LA10_104<='@')||(LA10_104>='[' && LA10_104<='^')||LA10_104=='`'||(LA10_104>='{' && LA10_104<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA10_8 = input.LA(1);

                        s = -1;
                        if ( ((LA10_8>='\u0000' && LA10_8<='\b')||LA10_8=='\u000B'||(LA10_8>='\u000E' && LA10_8<='\u001F')||(LA10_8>='!' && LA10_8<='\uFFFF')) ) {s = 19;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA10_26 = input.LA(1);

                        s = -1;
                        if ( ((LA10_26>='0' && LA10_26<='9')||(LA10_26>='A' && LA10_26<='Z')||LA10_26=='_'||(LA10_26>='a' && LA10_26<='z')) ) {s = 22;}

                        else if ( ((LA10_26>='\u0000' && LA10_26<='\b')||LA10_26=='\u000B'||(LA10_26>='\u000E' && LA10_26<='\u001F')||(LA10_26>='!' && LA10_26<='/')||(LA10_26>=':' && LA10_26<='@')||(LA10_26>='[' && LA10_26<='^')||LA10_26=='`'||(LA10_26>='{' && LA10_26<='\uFFFF')) ) {s = 19;}

                        else s = 47;

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA10_97 = input.LA(1);

                        s = -1;
                        if ( (LA10_97=='r') ) {s = 106;}

                        else if ( ((LA10_97>='0' && LA10_97<='9')||(LA10_97>='A' && LA10_97<='Z')||LA10_97=='_'||(LA10_97>='a' && LA10_97<='q')||(LA10_97>='s' && LA10_97<='z')) ) {s = 22;}

                        else if ( ((LA10_97>='\u0000' && LA10_97<='\b')||LA10_97=='\u000B'||(LA10_97>='\u000E' && LA10_97<='\u001F')||(LA10_97>='!' && LA10_97<='/')||(LA10_97>=':' && LA10_97<='@')||(LA10_97>='[' && LA10_97<='^')||LA10_97=='`'||(LA10_97>='{' && LA10_97<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA10_106 = input.LA(1);

                        s = -1;
                        if ( (LA10_106=='-') ) {s = 113;}

                        else if ( ((LA10_106>='0' && LA10_106<='9')||(LA10_106>='A' && LA10_106<='Z')||LA10_106=='_'||(LA10_106>='a' && LA10_106<='z')) ) {s = 22;}

                        else if ( ((LA10_106>='\u0000' && LA10_106<='\b')||LA10_106=='\u000B'||(LA10_106>='\u000E' && LA10_106<='\u001F')||(LA10_106>='!' && LA10_106<=',')||(LA10_106>='.' && LA10_106<='/')||(LA10_106>=':' && LA10_106<='@')||(LA10_106>='[' && LA10_106<='^')||LA10_106=='`'||(LA10_106>='{' && LA10_106<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA10_53 = input.LA(1);

                        s = -1;
                        if ( ((LA10_53>='\t' && LA10_53<='\n')||(LA10_53>='\f' && LA10_53<='\r')||LA10_53==' ') ) {s = 70;}

                        else if ( ((LA10_53>='\u0000' && LA10_53<='\b')||LA10_53=='\u000B'||(LA10_53>='\u000E' && LA10_53<='\u001F')||(LA10_53>='!' && LA10_53<='\uFFFF')) ) {s = 71;}

                        else s = 19;

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA10_42 = input.LA(1);

                        s = -1;
                        if ( (LA10_42=='p') ) {s = 62;}

                        else if ( ((LA10_42>='0' && LA10_42<='9')||(LA10_42>='A' && LA10_42<='Z')||LA10_42=='_'||(LA10_42>='a' && LA10_42<='o')||(LA10_42>='q' && LA10_42<='z')) ) {s = 22;}

                        else if ( ((LA10_42>='\u0000' && LA10_42<='\b')||LA10_42=='\u000B'||(LA10_42>='\u000E' && LA10_42<='\u001F')||(LA10_42>='!' && LA10_42<='/')||(LA10_42>=':' && LA10_42<='@')||(LA10_42>='[' && LA10_42<='^')||LA10_42=='`'||(LA10_42>='{' && LA10_42<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA10_62 = input.LA(1);

                        s = -1;
                        if ( (LA10_62=='l') ) {s = 77;}

                        else if ( ((LA10_62>='0' && LA10_62<='9')||(LA10_62>='A' && LA10_62<='Z')||LA10_62=='_'||(LA10_62>='a' && LA10_62<='k')||(LA10_62>='m' && LA10_62<='z')) ) {s = 22;}

                        else if ( ((LA10_62>='\u0000' && LA10_62<='\b')||LA10_62=='\u000B'||(LA10_62>='\u000E' && LA10_62<='\u001F')||(LA10_62>='!' && LA10_62<='/')||(LA10_62>=':' && LA10_62<='@')||(LA10_62>='[' && LA10_62<='^')||LA10_62=='`'||(LA10_62>='{' && LA10_62<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA10_77 = input.LA(1);

                        s = -1;
                        if ( (LA10_77=='i') ) {s = 88;}

                        else if ( ((LA10_77>='0' && LA10_77<='9')||(LA10_77>='A' && LA10_77<='Z')||LA10_77=='_'||(LA10_77>='a' && LA10_77<='h')||(LA10_77>='j' && LA10_77<='z')) ) {s = 22;}

                        else if ( ((LA10_77>='\u0000' && LA10_77<='\b')||LA10_77=='\u000B'||(LA10_77>='\u000E' && LA10_77<='\u001F')||(LA10_77>='!' && LA10_77<='/')||(LA10_77>=':' && LA10_77<='@')||(LA10_77>='[' && LA10_77<='^')||LA10_77=='`'||(LA10_77>='{' && LA10_77<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA10_88 = input.LA(1);

                        s = -1;
                        if ( (LA10_88=='e') ) {s = 97;}

                        else if ( ((LA10_88>='0' && LA10_88<='9')||(LA10_88>='A' && LA10_88<='Z')||LA10_88=='_'||(LA10_88>='a' && LA10_88<='d')||(LA10_88>='f' && LA10_88<='z')) ) {s = 22;}

                        else if ( ((LA10_88>='\u0000' && LA10_88<='\b')||LA10_88=='\u000B'||(LA10_88>='\u000E' && LA10_88<='\u001F')||(LA10_88>='!' && LA10_88<='/')||(LA10_88>=':' && LA10_88<='@')||(LA10_88>='[' && LA10_88<='^')||LA10_88=='`'||(LA10_88>='{' && LA10_88<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA10_23 = input.LA(1);

                        s = -1;
                        if ( (LA10_23=='p') ) {s = 42;}

                        else if ( ((LA10_23>='0' && LA10_23<='9')||(LA10_23>='A' && LA10_23<='Z')||LA10_23=='_'||(LA10_23>='a' && LA10_23<='o')||(LA10_23>='q' && LA10_23<='z')) ) {s = 22;}

                        else if ( ((LA10_23>='\u0000' && LA10_23<='\b')||LA10_23=='\u000B'||(LA10_23>='\u000E' && LA10_23<='\u001F')||(LA10_23>='!' && LA10_23<='/')||(LA10_23>=':' && LA10_23<='@')||(LA10_23>='[' && LA10_23<='^')||LA10_23=='`'||(LA10_23>='{' && LA10_23<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA10_83 = input.LA(1);

                        s = -1;
                        if ( ((LA10_83>='\u0000' && LA10_83<='\b')||LA10_83=='\u000B'||(LA10_83>='\u000E' && LA10_83<='\u001F')||(LA10_83>='!' && LA10_83<='\uFFFF')) ) {s = 94;}

                        else s = 93;

                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA10_7 = input.LA(1);

                        s = -1;
                        if ( ((LA10_7>='\u0000' && LA10_7<='\b')||LA10_7=='\u000B'||(LA10_7>='\u000E' && LA10_7<='\u001F')||(LA10_7>='!' && LA10_7<='\uFFFF')) ) {s = 19;}

                        else s = 30;

                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA10_87 = input.LA(1);

                        s = -1;
                        if ( (LA10_87=='a') ) {s = 96;}

                        else if ( ((LA10_87>='0' && LA10_87<='9')||(LA10_87>='A' && LA10_87<='Z')||LA10_87=='_'||(LA10_87>='b' && LA10_87<='z')) ) {s = 22;}

                        else if ( ((LA10_87>='\u0000' && LA10_87<='\b')||LA10_87=='\u000B'||(LA10_87>='\u000E' && LA10_87<='\u001F')||(LA10_87>='!' && LA10_87<='/')||(LA10_87>=':' && LA10_87<='@')||(LA10_87>='[' && LA10_87<='^')||LA10_87=='`'||(LA10_87>='{' && LA10_87<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA10_76 = input.LA(1);

                        s = -1;
                        if ( (LA10_76=='r') ) {s = 87;}

                        else if ( ((LA10_76>='0' && LA10_76<='9')||(LA10_76>='A' && LA10_76<='Z')||LA10_76=='_'||(LA10_76>='a' && LA10_76<='q')||(LA10_76>='s' && LA10_76<='z')) ) {s = 22;}

                        else if ( ((LA10_76>='\u0000' && LA10_76<='\b')||LA10_76=='\u000B'||(LA10_76>='\u000E' && LA10_76<='\u001F')||(LA10_76>='!' && LA10_76<='/')||(LA10_76>=':' && LA10_76<='@')||(LA10_76>='[' && LA10_76<='^')||LA10_76=='`'||(LA10_76>='{' && LA10_76<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA10_114 = input.LA(1);

                        s = -1;
                        if ( ((LA10_114>='0' && LA10_114<='9')||(LA10_114>='A' && LA10_114<='Z')||LA10_114=='_'||(LA10_114>='a' && LA10_114<='z')) ) {s = 22;}

                        else if ( ((LA10_114>='\u0000' && LA10_114<='\b')||LA10_114=='\u000B'||(LA10_114>='\u000E' && LA10_114<='\u001F')||(LA10_114>='!' && LA10_114<='/')||(LA10_114>=':' && LA10_114<='@')||(LA10_114>='[' && LA10_114<='^')||LA10_114=='`'||(LA10_114>='{' && LA10_114<='\uFFFF')) ) {s = 19;}

                        else s = 121;

                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA10_107 = input.LA(1);

                        s = -1;
                        if ( (LA10_107=='t') ) {s = 114;}

                        else if ( ((LA10_107>='0' && LA10_107<='9')||(LA10_107>='A' && LA10_107<='Z')||LA10_107=='_'||(LA10_107>='a' && LA10_107<='s')||(LA10_107>='u' && LA10_107<='z')) ) {s = 22;}

                        else if ( ((LA10_107>='\u0000' && LA10_107<='\b')||LA10_107=='\u000B'||(LA10_107>='\u000E' && LA10_107<='\u001F')||(LA10_107>='!' && LA10_107<='/')||(LA10_107>=':' && LA10_107<='@')||(LA10_107>='[' && LA10_107<='^')||LA10_107=='`'||(LA10_107>='{' && LA10_107<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA10_72 = input.LA(1);

                        s = -1;
                        if ( ((LA10_72>='0' && LA10_72<='9')||(LA10_72>='A' && LA10_72<='Z')||LA10_72=='_'||(LA10_72>='a' && LA10_72<='z')) ) {s = 22;}

                        else if ( (LA10_72=='=') ) {s = 83;}

                        else if ( ((LA10_72>='\u0000' && LA10_72<='\b')||LA10_72=='\u000B'||(LA10_72>='\u000E' && LA10_72<='\u001F')||(LA10_72>='!' && LA10_72<='/')||(LA10_72>=':' && LA10_72<='<')||(LA10_72>='>' && LA10_72<='@')||(LA10_72>='[' && LA10_72<='^')||LA10_72=='`'||(LA10_72>='{' && LA10_72<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA10_89 = input.LA(1);

                        s = -1;
                        if ( (LA10_89=='i') ) {s = 98;}

                        else if ( ((LA10_89>='0' && LA10_89<='9')||(LA10_89>='A' && LA10_89<='Z')||LA10_89=='_'||(LA10_89>='a' && LA10_89<='h')||(LA10_89>='j' && LA10_89<='z')) ) {s = 22;}

                        else if ( ((LA10_89>='\u0000' && LA10_89<='\b')||LA10_89=='\u000B'||(LA10_89>='\u000E' && LA10_89<='\u001F')||(LA10_89>='!' && LA10_89<='/')||(LA10_89>=':' && LA10_89<='@')||(LA10_89>='[' && LA10_89<='^')||LA10_89=='`'||(LA10_89>='{' && LA10_89<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA10_98 = input.LA(1);

                        s = -1;
                        if ( (LA10_98=='s') ) {s = 107;}

                        else if ( ((LA10_98>='0' && LA10_98<='9')||(LA10_98>='A' && LA10_98<='Z')||LA10_98=='_'||(LA10_98>='a' && LA10_98<='r')||(LA10_98>='t' && LA10_98<='z')) ) {s = 22;}

                        else if ( ((LA10_98>='\u0000' && LA10_98<='\b')||LA10_98=='\u000B'||(LA10_98>='\u000E' && LA10_98<='\u001F')||(LA10_98>='!' && LA10_98<='/')||(LA10_98>=':' && LA10_98<='@')||(LA10_98>='[' && LA10_98<='^')||LA10_98=='`'||(LA10_98>='{' && LA10_98<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA10_63 = input.LA(1);

                        s = -1;
                        if ( (LA10_63=='e') ) {s = 78;}

                        else if ( ((LA10_63>='0' && LA10_63<='9')||(LA10_63>='A' && LA10_63<='Z')||LA10_63=='_'||(LA10_63>='a' && LA10_63<='d')||(LA10_63>='f' && LA10_63<='z')) ) {s = 22;}

                        else if ( ((LA10_63>='\u0000' && LA10_63<='\b')||LA10_63=='\u000B'||(LA10_63>='\u000E' && LA10_63<='\u001F')||(LA10_63>='!' && LA10_63<='/')||(LA10_63>=':' && LA10_63<='@')||(LA10_63>='[' && LA10_63<='^')||LA10_63=='`'||(LA10_63>='{' && LA10_63<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA10_78 = input.LA(1);

                        s = -1;
                        if ( (LA10_78=='x') ) {s = 89;}

                        else if ( ((LA10_78>='0' && LA10_78<='9')||(LA10_78>='A' && LA10_78<='Z')||LA10_78=='_'||(LA10_78>='a' && LA10_78<='w')||(LA10_78>='y' && LA10_78<='z')) ) {s = 22;}

                        else if ( ((LA10_78>='\u0000' && LA10_78<='\b')||LA10_78=='\u000B'||(LA10_78>='\u000E' && LA10_78<='\u001F')||(LA10_78>='!' && LA10_78<='/')||(LA10_78>=':' && LA10_78<='@')||(LA10_78>='[' && LA10_78<='^')||LA10_78=='`'||(LA10_78>='{' && LA10_78<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA10_24 = input.LA(1);

                        s = -1;
                        if ( (LA10_24=='s') ) {s = 43;}

                        else if ( ((LA10_24>='0' && LA10_24<='9')||(LA10_24>='A' && LA10_24<='Z')||LA10_24=='_'||(LA10_24>='a' && LA10_24<='r')||(LA10_24>='t' && LA10_24<='z')) ) {s = 22;}

                        else if ( ((LA10_24>='\u0000' && LA10_24<='\b')||LA10_24=='\u000B'||(LA10_24>='\u000E' && LA10_24<='\u001F')||(LA10_24>='!' && LA10_24<='/')||(LA10_24>=':' && LA10_24<='@')||(LA10_24>='[' && LA10_24<='^')||LA10_24=='`'||(LA10_24>='{' && LA10_24<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA10_43 = input.LA(1);

                        s = -1;
                        if ( (LA10_43=='t') ) {s = 63;}

                        else if ( ((LA10_43>='0' && LA10_43<='9')||(LA10_43>='A' && LA10_43<='Z')||LA10_43=='_'||(LA10_43>='a' && LA10_43<='s')||(LA10_43>='u' && LA10_43<='z')) ) {s = 22;}

                        else if ( ((LA10_43>='\u0000' && LA10_43<='\b')||LA10_43=='\u000B'||(LA10_43>='\u000E' && LA10_43<='\u001F')||(LA10_43>='!' && LA10_43<='/')||(LA10_43>=':' && LA10_43<='@')||(LA10_43>='[' && LA10_43<='^')||LA10_43=='`'||(LA10_43>='{' && LA10_43<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA10_40 = input.LA(1);

                        s = -1;
                        if ( ((LA10_40>='\u0000' && LA10_40<='\b')||LA10_40=='\u000B'||(LA10_40>='\u000E' && LA10_40<='\u001F')||(LA10_40>='!' && LA10_40<='\uFFFF')) ) {s = 60;}

                        else s = 59;

                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA10_112 = input.LA(1);

                        s = -1;
                        if ( (LA10_112=='t') ) {s = 119;}

                        else if ( ((LA10_112>='0' && LA10_112<='9')||(LA10_112>='A' && LA10_112<='Z')||LA10_112=='_'||(LA10_112>='a' && LA10_112<='s')||(LA10_112>='u' && LA10_112<='z')) ) {s = 22;}

                        else if ( ((LA10_112>='\u0000' && LA10_112<='\b')||LA10_112=='\u000B'||(LA10_112>='\u000E' && LA10_112<='\u001F')||(LA10_112>='!' && LA10_112<='/')||(LA10_112>=':' && LA10_112<='@')||(LA10_112>='[' && LA10_112<='^')||LA10_112=='`'||(LA10_112>='{' && LA10_112<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA10_119 = input.LA(1);

                        s = -1;
                        if ( ((LA10_119>='0' && LA10_119<='9')||(LA10_119>='A' && LA10_119<='Z')||LA10_119=='_'||(LA10_119>='a' && LA10_119<='z')) ) {s = 22;}

                        else if ( ((LA10_119>='\u0000' && LA10_119<='\b')||LA10_119=='\u000B'||(LA10_119>='\u000E' && LA10_119<='\u001F')||(LA10_119>='!' && LA10_119<='/')||(LA10_119>=':' && LA10_119<='@')||(LA10_119>='[' && LA10_119<='^')||LA10_119=='`'||(LA10_119>='{' && LA10_119<='\uFFFF')) ) {s = 19;}

                        else s = 125;

                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA10_110 = input.LA(1);

                        s = -1;
                        if ( ((LA10_110>='0' && LA10_110<='9')||(LA10_110>='A' && LA10_110<='Z')||LA10_110=='_'||(LA10_110>='a' && LA10_110<='z')) ) {s = 117;}

                        else if ( (LA10_110=='.') ) {s = 103;}

                        else if ( ((LA10_110>='\u0000' && LA10_110<='\b')||LA10_110=='\u000B'||(LA10_110>='\u000E' && LA10_110<='\u001F')||(LA10_110>='!' && LA10_110<='-')||LA10_110=='/'||(LA10_110>=':' && LA10_110<='@')||(LA10_110>='[' && LA10_110<='^')||LA10_110=='`'||(LA10_110>='{' && LA10_110<='\uFFFF')) ) {s = 19;}

                        else s = 101;

                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA10_96 = input.LA(1);

                        s = -1;
                        if ( (LA10_96=='i') ) {s = 105;}

                        else if ( ((LA10_96>='0' && LA10_96<='9')||(LA10_96>='A' && LA10_96<='Z')||LA10_96=='_'||(LA10_96>='a' && LA10_96<='h')||(LA10_96>='j' && LA10_96<='z')) ) {s = 22;}

                        else if ( ((LA10_96>='\u0000' && LA10_96<='\b')||LA10_96=='\u000B'||(LA10_96>='\u000E' && LA10_96<='\u001F')||(LA10_96>='!' && LA10_96<='/')||(LA10_96>=':' && LA10_96<='@')||(LA10_96>='[' && LA10_96<='^')||LA10_96=='`'||(LA10_96>='{' && LA10_96<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA10_105 = input.LA(1);

                        s = -1;
                        if ( (LA10_105=='n') ) {s = 112;}

                        else if ( ((LA10_105>='0' && LA10_105<='9')||(LA10_105>='A' && LA10_105<='Z')||LA10_105=='_'||(LA10_105>='a' && LA10_105<='m')||(LA10_105>='o' && LA10_105<='z')) ) {s = 22;}

                        else if ( ((LA10_105>='\u0000' && LA10_105<='\b')||LA10_105=='\u000B'||(LA10_105>='\u000E' && LA10_105<='\u001F')||(LA10_105>='!' && LA10_105<='/')||(LA10_105>=':' && LA10_105<='@')||(LA10_105>='[' && LA10_105<='^')||LA10_105=='`'||(LA10_105>='{' && LA10_105<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA10_55 = input.LA(1);

                        s = -1;
                        if ( ((LA10_55>='0' && LA10_55<='9')||(LA10_55>='A' && LA10_55<='Z')||LA10_55=='_'||(LA10_55>='a' && LA10_55<='z')) ) {s = 22;}

                        else if ( (LA10_55=='=') ) {s = 73;}

                        else if ( ((LA10_55>='\u0000' && LA10_55<='\b')||LA10_55=='\u000B'||(LA10_55>='\u000E' && LA10_55<='\u001F')||(LA10_55>='!' && LA10_55<='/')||(LA10_55>=':' && LA10_55<='<')||(LA10_55>='>' && LA10_55<='@')||(LA10_55>='[' && LA10_55<='^')||LA10_55=='`'||(LA10_55>='{' && LA10_55<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA10_39 = input.LA(1);

                        s = -1;
                        if ( (LA10_39=='*') ) {s = 56;}

                        else if ( ((LA10_39>='\t' && LA10_39<='\n')||(LA10_39>='\f' && LA10_39<='\r')||LA10_39==' ') ) {s = 57;}

                        else if ( ((LA10_39>='\u0000' && LA10_39<='\b')||LA10_39=='\u000B'||(LA10_39>='\u000E' && LA10_39<='\u001F')||(LA10_39>='!' && LA10_39<=')')||(LA10_39>='+' && LA10_39<='\uFFFF')) ) {s = 58;}

                        else s = 19;

                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA10_108 = input.LA(1);

                        s = -1;
                        if ( (LA10_108=='e') ) {s = 115;}

                        else if ( ((LA10_108>='0' && LA10_108<='9')||(LA10_108>='A' && LA10_108<='Z')||LA10_108=='_'||(LA10_108>='a' && LA10_108<='d')||(LA10_108>='f' && LA10_108<='z')) ) {s = 22;}

                        else if ( ((LA10_108>='\u0000' && LA10_108<='\b')||LA10_108=='\u000B'||(LA10_108>='\u000E' && LA10_108<='\u001F')||(LA10_108>='!' && LA10_108<='/')||(LA10_108>=':' && LA10_108<='@')||(LA10_108>='[' && LA10_108<='^')||LA10_108=='`'||(LA10_108>='{' && LA10_108<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA10_99 = input.LA(1);

                        s = -1;
                        if ( (LA10_99=='r') ) {s = 108;}

                        else if ( ((LA10_99>='0' && LA10_99<='9')||(LA10_99>='A' && LA10_99<='Z')||LA10_99=='_'||(LA10_99>='a' && LA10_99<='q')||(LA10_99>='s' && LA10_99<='z')) ) {s = 22;}

                        else if ( ((LA10_99>='\u0000' && LA10_99<='\b')||LA10_99=='\u000B'||(LA10_99>='\u000E' && LA10_99<='\u001F')||(LA10_99>='!' && LA10_99<='/')||(LA10_99>=':' && LA10_99<='@')||(LA10_99>='[' && LA10_99<='^')||LA10_99=='`'||(LA10_99>='{' && LA10_99<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 59 : 
                        int LA10_13 = input.LA(1);

                        s = -1;
                        if ( (LA10_13=='y') ) {s = 37;}

                        else if ( ((LA10_13>='0' && LA10_13<='9')||(LA10_13>='A' && LA10_13<='Z')||LA10_13=='_'||(LA10_13>='a' && LA10_13<='x')||LA10_13=='z') ) {s = 22;}

                        else if ( ((LA10_13>='\u0000' && LA10_13<='\b')||LA10_13=='\u000B'||(LA10_13>='\u000E' && LA10_13<='\u001F')||(LA10_13>='!' && LA10_13<='/')||(LA10_13>=':' && LA10_13<='@')||(LA10_13>='[' && LA10_13<='^')||LA10_13=='`'||(LA10_13>='{' && LA10_13<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 60 : 
                        int LA10_90 = input.LA(1);

                        s = -1;
                        if ( (LA10_90=='u') ) {s = 99;}

                        else if ( ((LA10_90>='0' && LA10_90<='9')||(LA10_90>='A' && LA10_90<='Z')||LA10_90=='_'||(LA10_90>='a' && LA10_90<='t')||(LA10_90>='v' && LA10_90<='z')) ) {s = 22;}

                        else if ( ((LA10_90>='\u0000' && LA10_90<='\b')||LA10_90=='\u000B'||(LA10_90>='\u000E' && LA10_90<='\u001F')||(LA10_90>='!' && LA10_90<='/')||(LA10_90>=':' && LA10_90<='@')||(LA10_90>='[' && LA10_90<='^')||LA10_90=='`'||(LA10_90>='{' && LA10_90<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 61 : 
                        int LA10_79 = input.LA(1);

                        s = -1;
                        if ( (LA10_79=='l') ) {s = 90;}

                        else if ( ((LA10_79>='0' && LA10_79<='9')||(LA10_79>='A' && LA10_79<='Z')||LA10_79=='_'||(LA10_79>='a' && LA10_79<='k')||(LA10_79>='m' && LA10_79<='z')) ) {s = 22;}

                        else if ( ((LA10_79>='\u0000' && LA10_79<='\b')||LA10_79=='\u000B'||(LA10_79>='\u000E' && LA10_79<='\u001F')||(LA10_79>='!' && LA10_79<='/')||(LA10_79>=':' && LA10_79<='@')||(LA10_79>='[' && LA10_79<='^')||LA10_79=='`'||(LA10_79>='{' && LA10_79<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 62 : 
                        int LA10_64 = input.LA(1);

                        s = -1;
                        if ( (LA10_64=='i') ) {s = 79;}

                        else if ( ((LA10_64>='0' && LA10_64<='9')||(LA10_64>='A' && LA10_64<='Z')||LA10_64=='_'||(LA10_64>='a' && LA10_64<='h')||(LA10_64>='j' && LA10_64<='z')) ) {s = 22;}

                        else if ( ((LA10_64>='\u0000' && LA10_64<='\b')||LA10_64=='\u000B'||(LA10_64>='\u000E' && LA10_64<='\u001F')||(LA10_64>='!' && LA10_64<='/')||(LA10_64>=':' && LA10_64<='@')||(LA10_64>='[' && LA10_64<='^')||LA10_64=='`'||(LA10_64>='{' && LA10_64<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 63 : 
                        int LA10_44 = input.LA(1);

                        s = -1;
                        if ( (LA10_44=='a') ) {s = 64;}

                        else if ( ((LA10_44>='0' && LA10_44<='9')||(LA10_44>='A' && LA10_44<='Z')||LA10_44=='_'||(LA10_44>='b' && LA10_44<='z')) ) {s = 22;}

                        else if ( ((LA10_44>='\u0000' && LA10_44<='\b')||LA10_44=='\u000B'||(LA10_44>='\u000E' && LA10_44<='\u001F')||(LA10_44>='!' && LA10_44<='/')||(LA10_44>=':' && LA10_44<='@')||(LA10_44>='[' && LA10_44<='^')||LA10_44=='`'||(LA10_44>='{' && LA10_44<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 64 : 
                        int LA10_92 = input.LA(1);

                        s = -1;
                        if ( ((LA10_92>='0' && LA10_92<='9')||(LA10_92>='A' && LA10_92<='Z')||LA10_92=='_'||(LA10_92>='a' && LA10_92<='z')) ) {s = 102;}

                        else if ( (LA10_92=='.') ) {s = 103;}

                        else if ( ((LA10_92>='\u0000' && LA10_92<='\b')||LA10_92=='\u000B'||(LA10_92>='\u000E' && LA10_92<='\u001F')||(LA10_92>='!' && LA10_92<='-')||LA10_92=='/'||(LA10_92>=':' && LA10_92<='@')||(LA10_92>='[' && LA10_92<='^')||LA10_92=='`'||(LA10_92>='{' && LA10_92<='\uFFFF')) ) {s = 19;}

                        else s = 101;

                        if ( s>=0 ) return s;
                        break;
                    case 65 : 
                        int LA10_27 = input.LA(1);

                        s = -1;
                        if ( (LA10_27=='d') ) {s = 48;}

                        else if ( ((LA10_27>='0' && LA10_27<='9')||(LA10_27>='A' && LA10_27<='Z')||LA10_27=='_'||(LA10_27>='a' && LA10_27<='c')||(LA10_27>='e' && LA10_27<='z')) ) {s = 22;}

                        else if ( ((LA10_27>='\u0000' && LA10_27<='\b')||LA10_27=='\u000B'||(LA10_27>='\u000E' && LA10_27<='\u001F')||(LA10_27>='!' && LA10_27<='/')||(LA10_27>=':' && LA10_27<='@')||(LA10_27>='[' && LA10_27<='^')||LA10_27=='`'||(LA10_27>='{' && LA10_27<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 66 : 
                        int LA10_3 = input.LA(1);

                        s = -1;
                        if ( (LA10_3=='u') ) {s = 24;}

                        else if ( ((LA10_3>='0' && LA10_3<='9')||(LA10_3>='A' && LA10_3<='Z')||LA10_3=='_'||(LA10_3>='a' && LA10_3<='t')||(LA10_3>='v' && LA10_3<='z')) ) {s = 22;}

                        else if ( ((LA10_3>='\u0000' && LA10_3<='\b')||LA10_3=='\u000B'||(LA10_3>='\u000E' && LA10_3<='\u001F')||(LA10_3>='!' && LA10_3<='/')||(LA10_3>=':' && LA10_3<='@')||(LA10_3>='[' && LA10_3<='^')||LA10_3=='`'||(LA10_3>='{' && LA10_3<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 67 : 
                        int LA10_102 = input.LA(1);

                        s = -1;
                        if ( (LA10_102=='.') ) {s = 103;}

                        else if ( ((LA10_102>='0' && LA10_102<='9')||(LA10_102>='A' && LA10_102<='Z')||LA10_102=='_'||(LA10_102>='a' && LA10_102<='z')) ) {s = 102;}

                        else if ( ((LA10_102>='\u0000' && LA10_102<='\b')||LA10_102=='\u000B'||(LA10_102>='\u000E' && LA10_102<='\u001F')||(LA10_102>='!' && LA10_102<='-')||LA10_102=='/'||(LA10_102>=':' && LA10_102<='@')||(LA10_102>='[' && LA10_102<='^')||LA10_102=='`'||(LA10_102>='{' && LA10_102<='\uFFFF')) ) {s = 19;}

                        else s = 101;

                        if ( s>=0 ) return s;
                        break;
                    case 68 : 
                        int LA10_45 = input.LA(1);

                        s = -1;
                        if ( (LA10_45=='u') ) {s = 65;}

                        else if ( ((LA10_45>='0' && LA10_45<='9')||(LA10_45>='A' && LA10_45<='Z')||LA10_45=='_'||(LA10_45>='a' && LA10_45<='t')||(LA10_45>='v' && LA10_45<='z')) ) {s = 22;}

                        else if ( ((LA10_45>='\u0000' && LA10_45<='\b')||LA10_45=='\u000B'||(LA10_45>='\u000E' && LA10_45<='\u001F')||(LA10_45>='!' && LA10_45<='/')||(LA10_45>=':' && LA10_45<='@')||(LA10_45>='[' && LA10_45<='^')||LA10_45=='`'||(LA10_45>='{' && LA10_45<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 69 : 
                        int LA10_68 = input.LA(1);

                        s = -1;
                        if ( ((LA10_68>='0' && LA10_68<='9')||(LA10_68>='A' && LA10_68<='Z')||LA10_68=='_'||(LA10_68>='a' && LA10_68<='z')) ) {s = 22;}

                        else if ( (LA10_68=='=') ) {s = 81;}

                        else if ( ((LA10_68>='\u0000' && LA10_68<='\b')||LA10_68=='\u000B'||(LA10_68>='\u000E' && LA10_68<='\u001F')||(LA10_68>='!' && LA10_68<='/')||(LA10_68>=':' && LA10_68<='<')||(LA10_68>='>' && LA10_68<='@')||(LA10_68>='[' && LA10_68<='^')||LA10_68=='`'||(LA10_68>='{' && LA10_68<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 70 : 
                        int LA10_65 = input.LA(1);

                        s = -1;
                        if ( (LA10_65=='c') ) {s = 80;}

                        else if ( ((LA10_65>='0' && LA10_65<='9')||(LA10_65>='A' && LA10_65<='Z')||LA10_65=='_'||(LA10_65>='a' && LA10_65<='b')||(LA10_65>='d' && LA10_65<='z')) ) {s = 22;}

                        else if ( ((LA10_65>='\u0000' && LA10_65<='\b')||LA10_65=='\u000B'||(LA10_65>='\u000E' && LA10_65<='\u001F')||(LA10_65>='!' && LA10_65<='/')||(LA10_65>=':' && LA10_65<='@')||(LA10_65>='[' && LA10_65<='^')||LA10_65=='`'||(LA10_65>='{' && LA10_65<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 71 : 
                        int LA10_80 = input.LA(1);

                        s = -1;
                        if ( (LA10_80=='c') ) {s = 91;}

                        else if ( ((LA10_80>='0' && LA10_80<='9')||(LA10_80>='A' && LA10_80<='Z')||LA10_80=='_'||(LA10_80>='a' && LA10_80<='b')||(LA10_80>='d' && LA10_80<='z')) ) {s = 22;}

                        else if ( ((LA10_80>='\u0000' && LA10_80<='\b')||LA10_80=='\u000B'||(LA10_80>='\u000E' && LA10_80<='\u001F')||(LA10_80>='!' && LA10_80<='/')||(LA10_80>=':' && LA10_80<='@')||(LA10_80>='[' && LA10_80<='^')||LA10_80=='`'||(LA10_80>='{' && LA10_80<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 72 : 
                        int LA10_91 = input.LA(1);

                        s = -1;
                        if ( (LA10_91=='e') ) {s = 100;}

                        else if ( ((LA10_91>='0' && LA10_91<='9')||(LA10_91>='A' && LA10_91<='Z')||LA10_91=='_'||(LA10_91>='a' && LA10_91<='d')||(LA10_91>='f' && LA10_91<='z')) ) {s = 22;}

                        else if ( ((LA10_91>='\u0000' && LA10_91<='\b')||LA10_91=='\u000B'||(LA10_91>='\u000E' && LA10_91<='\u001F')||(LA10_91>='!' && LA10_91<='/')||(LA10_91>=':' && LA10_91<='@')||(LA10_91>='[' && LA10_91<='^')||LA10_91=='`'||(LA10_91>='{' && LA10_91<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 73 : 
                        int LA10_100 = input.LA(1);

                        s = -1;
                        if ( (LA10_100=='s') ) {s = 109;}

                        else if ( ((LA10_100>='0' && LA10_100<='9')||(LA10_100>='A' && LA10_100<='Z')||LA10_100=='_'||(LA10_100>='a' && LA10_100<='r')||(LA10_100>='t' && LA10_100<='z')) ) {s = 22;}

                        else if ( ((LA10_100>='\u0000' && LA10_100<='\b')||LA10_100=='\u000B'||(LA10_100>='\u000E' && LA10_100<='\u001F')||(LA10_100>='!' && LA10_100<='/')||(LA10_100>=':' && LA10_100<='@')||(LA10_100>='[' && LA10_100<='^')||LA10_100=='`'||(LA10_100>='{' && LA10_100<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 74 : 
                        int LA10_109 = input.LA(1);

                        s = -1;
                        if ( (LA10_109=='s') ) {s = 116;}

                        else if ( ((LA10_109>='0' && LA10_109<='9')||(LA10_109>='A' && LA10_109<='Z')||LA10_109=='_'||(LA10_109>='a' && LA10_109<='r')||(LA10_109>='t' && LA10_109<='z')) ) {s = 22;}

                        else if ( ((LA10_109>='\u0000' && LA10_109<='\b')||LA10_109=='\u000B'||(LA10_109>='\u000E' && LA10_109<='\u001F')||(LA10_109>='!' && LA10_109<='/')||(LA10_109>=':' && LA10_109<='@')||(LA10_109>='[' && LA10_109<='^')||LA10_109=='`'||(LA10_109>='{' && LA10_109<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 75 : 
                        int LA10_6 = input.LA(1);

                        s = -1;
                        if ( (LA10_6=='o') ) {s = 28;}

                        else if ( (LA10_6=='a') ) {s = 29;}

                        else if ( ((LA10_6>='0' && LA10_6<='9')||(LA10_6>='A' && LA10_6<='Z')||LA10_6=='_'||(LA10_6>='b' && LA10_6<='n')||(LA10_6>='p' && LA10_6<='z')) ) {s = 22;}

                        else if ( ((LA10_6>='\u0000' && LA10_6<='\b')||LA10_6=='\u000B'||(LA10_6>='\u000E' && LA10_6<='\u001F')||(LA10_6>='!' && LA10_6<='/')||(LA10_6>=':' && LA10_6<='@')||(LA10_6>='[' && LA10_6<='^')||LA10_6=='`'||(LA10_6>='{' && LA10_6<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 76 : 
                        int LA10_117 = input.LA(1);

                        s = -1;
                        if ( (LA10_117=='.') ) {s = 103;}

                        else if ( ((LA10_117>='0' && LA10_117<='9')||(LA10_117>='A' && LA10_117<='Z')||LA10_117=='_'||(LA10_117>='a' && LA10_117<='z')) ) {s = 117;}

                        else if ( ((LA10_117>='\u0000' && LA10_117<='\b')||LA10_117=='\u000B'||(LA10_117>='\u000E' && LA10_117<='\u001F')||(LA10_117>='!' && LA10_117<='-')||LA10_117=='/'||(LA10_117>=':' && LA10_117<='@')||(LA10_117>='[' && LA10_117<='^')||LA10_117=='`'||(LA10_117>='{' && LA10_117<='\uFFFF')) ) {s = 19;}

                        else s = 101;

                        if ( s>=0 ) return s;
                        break;
                    case 77 : 
                        int LA10_28 = input.LA(1);

                        s = -1;
                        if ( (LA10_28=='t') ) {s = 49;}

                        else if ( ((LA10_28>='0' && LA10_28<='9')||(LA10_28>='A' && LA10_28<='Z')||LA10_28=='_'||(LA10_28>='a' && LA10_28<='s')||(LA10_28>='u' && LA10_28<='z')) ) {s = 22;}

                        else if ( ((LA10_28>='\u0000' && LA10_28<='\b')||LA10_28=='\u000B'||(LA10_28>='\u000E' && LA10_28<='\u001F')||(LA10_28>='!' && LA10_28<='/')||(LA10_28>=':' && LA10_28<='@')||(LA10_28>='[' && LA10_28<='^')||LA10_28=='`'||(LA10_28>='{' && LA10_28<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 78 : 
                        int LA10_73 = input.LA(1);

                        s = -1;
                        if ( ((LA10_73>='\u0000' && LA10_73<='\b')||LA10_73=='\u000B'||(LA10_73>='\u000E' && LA10_73<='\u001F')||(LA10_73>='!' && LA10_73<='\uFFFF')) ) {s = 85;}

                        else s = 84;

                        if ( s>=0 ) return s;
                        break;
                    case 79 : 
                        int LA10_5 = input.LA(1);

                        s = -1;
                        if ( (LA10_5=='n') ) {s = 27;}

                        else if ( ((LA10_5>='0' && LA10_5<='9')||(LA10_5>='A' && LA10_5<='Z')||LA10_5=='_'||(LA10_5>='a' && LA10_5<='m')||(LA10_5>='o' && LA10_5<='z')) ) {s = 22;}

                        else if ( ((LA10_5>='\u0000' && LA10_5<='\b')||LA10_5=='\u000B'||(LA10_5>='\u000E' && LA10_5<='\u001F')||(LA10_5>='!' && LA10_5<='/')||(LA10_5>=':' && LA10_5<='@')||(LA10_5>='[' && LA10_5<='^')||LA10_5=='`'||(LA10_5>='{' && LA10_5<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 80 : 
                        int LA10_41 = input.LA(1);

                        s = -1;
                        if ( (LA10_41=='s') ) {s = 61;}

                        else if ( ((LA10_41>='0' && LA10_41<='9')||(LA10_41>='A' && LA10_41<='Z')||LA10_41=='_'||(LA10_41>='a' && LA10_41<='r')||(LA10_41>='t' && LA10_41<='z')) ) {s = 22;}

                        else if ( ((LA10_41>='\u0000' && LA10_41<='\b')||LA10_41=='\u000B'||(LA10_41>='\u000E' && LA10_41<='\u001F')||(LA10_41>='!' && LA10_41<='/')||(LA10_41>=':' && LA10_41<='@')||(LA10_41>='[' && LA10_41<='^')||LA10_41=='`'||(LA10_41>='{' && LA10_41<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 81 : 
                        int LA10_137 = input.LA(1);

                        s = -1;
                        if ( ((LA10_137>='\u0000' && LA10_137<='\b')||LA10_137=='\u000B'||(LA10_137>='\u000E' && LA10_137<='\u001F')||(LA10_137>='!' && LA10_137<='\uFFFF')) ) {s = 19;}

                        else s = 139;

                        if ( s>=0 ) return s;
                        break;
                    case 82 : 
                        int LA10_20 = input.LA(1);

                        s = -1;
                        if ( (LA10_20=='n') ) {s = 41;}

                        else if ( ((LA10_20>='0' && LA10_20<='9')||(LA10_20>='A' && LA10_20<='Z')||LA10_20=='_'||(LA10_20>='a' && LA10_20<='m')||(LA10_20>='o' && LA10_20<='z')) ) {s = 22;}

                        else if ( ((LA10_20>='\u0000' && LA10_20<='\b')||LA10_20=='\u000B'||(LA10_20>='\u000E' && LA10_20<='\u001F')||(LA10_20>='!' && LA10_20<='/')||(LA10_20>=':' && LA10_20<='@')||(LA10_20>='[' && LA10_20<='^')||LA10_20=='`'||(LA10_20>='{' && LA10_20<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 83 : 
                        int LA10_61 = input.LA(1);

                        s = -1;
                        if ( (LA10_61=='u') ) {s = 75;}

                        else if ( (LA10_61=='t') ) {s = 76;}

                        else if ( ((LA10_61>='0' && LA10_61<='9')||(LA10_61>='A' && LA10_61<='Z')||LA10_61=='_'||(LA10_61>='a' && LA10_61<='s')||(LA10_61>='v' && LA10_61<='z')) ) {s = 22;}

                        else if ( ((LA10_61>='\u0000' && LA10_61<='\b')||LA10_61=='\u000B'||(LA10_61>='\u000E' && LA10_61<='\u001F')||(LA10_61>='!' && LA10_61<='/')||(LA10_61>=':' && LA10_61<='@')||(LA10_61>='[' && LA10_61<='^')||LA10_61=='`'||(LA10_61>='{' && LA10_61<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 84 : 
                        int LA10_1 = input.LA(1);

                        s = -1;
                        if ( (LA10_1=='o') ) {s = 20;}

                        else if ( ((LA10_1>='0' && LA10_1<='9')||(LA10_1>='A' && LA10_1<='Z')||LA10_1=='_'||(LA10_1>='a' && LA10_1<='n')||(LA10_1>='p' && LA10_1<='z')) ) {s = 22;}

                        else if ( ((LA10_1>='\u0000' && LA10_1<='\b')||LA10_1=='\u000B'||(LA10_1>='\u000E' && LA10_1<='\u001F')||(LA10_1>='!' && LA10_1<='/')||(LA10_1>=':' && LA10_1<='@')||(LA10_1>='[' && LA10_1<='^')||LA10_1=='`'||(LA10_1>='{' && LA10_1<='\uFFFF')) ) {s = 19;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 85 : 
                        int LA10_11 = input.LA(1);

                        s = -1;
                        if ( ((LA10_11>='A' && LA10_11<='Z')||LA10_11=='_'||(LA10_11>='a' && LA10_11<='z')) ) {s = 35;}

                        else if ( ((LA10_11>='\u0000' && LA10_11<='\b')||LA10_11=='\u000B'||(LA10_11>='\u000E' && LA10_11<='\u001F')||(LA10_11>='!' && LA10_11<='@')||(LA10_11>='[' && LA10_11<='^')||LA10_11=='`'||(LA10_11>='{' && LA10_11<='\uFFFF')) ) {s = 19;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 86 : 
                        int LA10_138 = input.LA(1);

                        s = -1;
                        if ( ((LA10_138>='\u0000' && LA10_138<='\b')||LA10_138=='\u000B'||(LA10_138>='\u000E' && LA10_138<='\u001F')||(LA10_138>='!' && LA10_138<='\uFFFF')) ) {s = 19;}

                        else s = 140;

                        if ( s>=0 ) return s;
                        break;
                    case 87 : 
                        int LA10_12 = input.LA(1);

                        s = -1;
                        if ( ((LA10_12>='\u0000' && LA10_12<='\b')||LA10_12=='\u000B'||(LA10_12>='\u000E' && LA10_12<='\u001F')||(LA10_12>='!' && LA10_12<='\uFFFF')) ) {s = 19;}

                        else s = 36;

                        if ( s>=0 ) return s;
                        break;
                    case 88 : 
                        int LA10_71 = input.LA(1);

                        s = -1;
                        if ( ((LA10_71>='\u0000' && LA10_71<='\b')||LA10_71=='\u000B'||(LA10_71>='\u000E' && LA10_71<='\u001F')||(LA10_71>='!' && LA10_71<='\uFFFF')) ) {s = 71;}

                        else if ( ((LA10_71>='\t' && LA10_71<='\n')||(LA10_71>='\f' && LA10_71<='\r')||LA10_71==' ') ) {s = 70;}

                        else s = 19;

                        if ( s>=0 ) return s;
                        break;
                    case 89 : 
                        int LA10_25 = input.LA(1);

                        s = -1;
                        if ( (LA10_25=='f') ) {s = 44;}

                        else if ( (LA10_25=='s') ) {s = 45;}

                        else if ( ((LA10_25>='0' && LA10_25<='9')||(LA10_25>='A' && LA10_25<='Z')||LA10_25=='_'||(LA10_25>='a' && LA10_25<='e')||(LA10_25>='g' && LA10_25<='r')||(LA10_25>='t' && LA10_25<='z')) ) {s = 22;}

                        else if ( ((LA10_25>='\u0000' && LA10_25<='\b')||LA10_25=='\u000B'||(LA10_25>='\u000E' && LA10_25<='\u001F')||(LA10_25>='!' && LA10_25<='/')||(LA10_25>=':' && LA10_25<='@')||(LA10_25>='[' && LA10_25<='^')||LA10_25=='`'||(LA10_25>='{' && LA10_25<='\uFFFF')) ) {s = 19;}

                        else if ( (LA10_25=='\t'||LA10_25==' ') ) {s = 46;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}