// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-10-31 13:11:31

package net.java.treaty.script.generated;

import java.util.List;
import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TreatyLexer extends Lexer {
    public static final int NameAttribute=9;
    public static final int Hash=29;
    public static final int LineComment=44;
    public static final int Newline=4;
    public static final int AnnotationValue=6;
    public static final int Uri=18;
    public static final int DecimalDigit=50;
    public static final int Percent=31;
    public static final int AnnotationKey=5;
    public static final int IDLetter=41;
    public static final int EOF=-1;
    public static final int HexDigit=52;
    public static final int Identifier=8;
    public static final int Amper=19;
    public static final int T__55=55;
    public static final int BlockComment=43;
    public static final int T__56=56;
    public static final int XOr=13;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int UriCharacter=45;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int UriUnescaped=47;
    public static final int String=38;
    public static final int Or=12;
    public static final int IDDigit=42;
    public static final int Whitespace=37;
    public static final int ResourceReferenceAttribute=11;
    public static final int Dollar=25;
    public static final int NamespaceDelimiter=40;
    public static final int And=14;
    public static final int Tilde=36;
    public static final int Asterisk=21;
    public static final int RParen=17;
    public static final int At=22;
    public static final int Exclamation=28;
    public static final int LParen=16;
    public static final int Trigger=7;
    public static final int Colon=23;
    public static final int Question=33;
    public static final int UriMark=51;
    public static final int Plus=32;
    public static final int Minus=30;
    public static final int Semi=34;
    public static final int UriReserved=46;
    public static final int Not=15;
    public static final int UriAlpha=49;
    public static final int Dot=26;
    public static final int UriEscaped=48;
    public static final int Annotation=39;
    public static final int Equals=27;
    public static final int Comma=24;
    public static final int Apostrophe=20;
    public static final int Slash=35;
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

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
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
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
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
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
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
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
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
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
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
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
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
    // $ANTLR end "T__58"

    // $ANTLR start "And"
    public final void mAnd() throws RecognitionException {
        try {
            int _type = And;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:210:13: ( 'and' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:210:15: 'and'
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

    // $ANTLR start "Not"
    public final void mNot() throws RecognitionException {
        try {
            int _type = Not;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:13: ( 'not' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:15: 'not'
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

    // $ANTLR start "Or"
    public final void mOr() throws RecognitionException {
        try {
            int _type = Or;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:212:13: ( 'or' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:212:15: 'or'
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

    // $ANTLR start "XOr"
    public final void mXOr() throws RecognitionException {
        try {
            int _type = XOr;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:213:13: ( 'xor' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:213:15: 'xor'
            {
            match("xor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "XOr"

    // $ANTLR start "LParen"
    public final void mLParen() throws RecognitionException {
        try {
            int _type = LParen;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:13: ( '(' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:15: '('
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:216:13: ( ')' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:216:15: ')'
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

    // $ANTLR start "Amper"
    public final void mAmper() throws RecognitionException {
        try {
            int _type = Amper;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:218:13: ( '&' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:218:15: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Amper"

    // $ANTLR start "Apostrophe"
    public final void mApostrophe() throws RecognitionException {
        try {
            int _type = Apostrophe;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:219:13: ( '\\'' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:219:15: '\\''
            {
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Apostrophe"

    // $ANTLR start "Asterisk"
    public final void mAsterisk() throws RecognitionException {
        try {
            int _type = Asterisk;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:220:13: ( '*' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:220:15: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Asterisk"

    // $ANTLR start "At"
    public final void mAt() throws RecognitionException {
        try {
            int _type = At;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:221:13: ( '@' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:221:15: '@'
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

    // $ANTLR start "Colon"
    public final void mColon() throws RecognitionException {
        try {
            int _type = Colon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:13: ( ':' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:15: ':'
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

    // $ANTLR start "Comma"
    public final void mComma() throws RecognitionException {
        try {
            int _type = Comma;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:223:13: ( ',' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:223:15: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Comma"

    // $ANTLR start "Dollar"
    public final void mDollar() throws RecognitionException {
        try {
            int _type = Dollar;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:224:13: ( '$' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:224:15: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Dollar"

    // $ANTLR start "Dot"
    public final void mDot() throws RecognitionException {
        try {
            int _type = Dot;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:225:13: ( '.' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:225:15: '.'
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

    // $ANTLR start "Equals"
    public final void mEquals() throws RecognitionException {
        try {
            int _type = Equals;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:226:13: ( '=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:226:15: '='
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

    // $ANTLR start "Exclamation"
    public final void mExclamation() throws RecognitionException {
        try {
            int _type = Exclamation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:227:13: ( '!' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:227:15: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Exclamation"

    // $ANTLR start "Hash"
    public final void mHash() throws RecognitionException {
        try {
            int _type = Hash;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:228:13: ( '#' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:228:15: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Hash"

    // $ANTLR start "Minus"
    public final void mMinus() throws RecognitionException {
        try {
            int _type = Minus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:229:13: ( '-' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:229:15: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Minus"

    // $ANTLR start "Percent"
    public final void mPercent() throws RecognitionException {
        try {
            int _type = Percent;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:13: ( '%' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:15: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Percent"

    // $ANTLR start "Plus"
    public final void mPlus() throws RecognitionException {
        try {
            int _type = Plus;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:231:13: ( '+' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:231:15: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Plus"

    // $ANTLR start "Question"
    public final void mQuestion() throws RecognitionException {
        try {
            int _type = Question;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:232:13: ( '?' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:232:15: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Question"

    // $ANTLR start "Semi"
    public final void mSemi() throws RecognitionException {
        try {
            int _type = Semi;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:233:13: ( ';' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:233:15: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Semi"

    // $ANTLR start "Slash"
    public final void mSlash() throws RecognitionException {
        try {
            int _type = Slash;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:13: ( '/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:15: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Slash"

    // $ANTLR start "Tilde"
    public final void mTilde() throws RecognitionException {
        try {
            int _type = Tilde;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:235:13: ( '~' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:235:15: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Tilde"

    // $ANTLR start "Trigger"
    public final void mTrigger() throws RecognitionException {
        try {
            int _type = Trigger;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String1=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:238:5: ( 'on' Whitespace String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:238:9: 'on' Whitespace String
            {
            match("on"); 

            mWhitespace(); 
            int String1Start465 = getCharIndex();
            mString(); 
            String1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String1Start465, getCharIndex()-1);

                        emit(new CommonToken(Trigger, (String1!=null?String1.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Trigger"

    // $ANTLR start "NameAttribute"
    public final void mNameAttribute() throws RecognitionException {
        try {
            int _type = NameAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String2=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:245:5: ( 'name' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:245:9: 'name' Equals String
            {
            match("name"); 

            mEquals(); 
            int String2Start499 = getCharIndex();
            mString(); 
            String2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String2Start499, getCharIndex()-1);

                        emit(new CommonToken(NameAttribute, (String2!=null?String2.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NameAttribute"

    // $ANTLR start "ResourceTypeAttribute"
    public final void mResourceTypeAttribute() throws RecognitionException {
        try {
            int _type = ResourceTypeAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String3=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:252:5: ( 'type' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:252:9: 'type' Equals String
            {
            match("type"); 

            mEquals(); 
            int String3Start533 = getCharIndex();
            mString(); 
            String3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String3Start533, getCharIndex()-1);

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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:259:5: ( 'ref' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:259:9: 'ref' Equals String
            {
            match("ref"); 

            mEquals(); 
            int String4Start567 = getCharIndex();
            mString(); 
            String4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String4Start567, getCharIndex()-1);

                        emit(new CommonToken(ResourceReferenceAttribute, (String4!=null?String4.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ResourceReferenceAttribute"

    // $ANTLR start "String"
    public final void mString() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:267:5: ( (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:267:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:267:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='\b')||LA1_0=='\u000B'||(LA1_0>='\u000E' && LA1_0<='\u001F')||(LA1_0>='!' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:267:9: ~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
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
            	    break loop1;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "String"

    // $ANTLR start "Annotation"
    public final void mAnnotation() throws RecognitionException {
        try {
            int _type = Annotation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token key=null;
            Token value=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:5: ( At key= AnnotationKey Equals value= AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:9: At key= AnnotationKey Equals value= AnnotationValue
            {
            mAt(); 
            int keyStart635 = getCharIndex();
            mAnnotationKey(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart635, getCharIndex()-1);
            mEquals(); 
            int valueStart641 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart641, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:281:5: ( Identifier ( NamespaceDelimiter Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:281:9: Identifier ( NamespaceDelimiter Identifier )*
            {
            mIdentifier(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:281:20: ( NamespaceDelimiter Identifier )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.'||LA2_0==':') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:281:21: NamespaceDelimiter Identifier
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:286:5: ( Colon | Dot )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:5: ( ( options {greedy=false; } : . )* Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:9: ( options {greedy=false; } : . )* Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:9: ( options {greedy=false; } : . )*
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
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:292:39: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            int Newline5Start753 = getCharIndex();
            mNewline(); 
            Newline5 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Newline5Start753, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:303:5: ( IDLetter ( IDLetter | IDDigit )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:303:9: IDLetter ( IDLetter | IDDigit )*
            {
            mIDLetter(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:303:18: ( IDLetter | IDDigit )*
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:308:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:315:5: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:315:9: '0' .. '9'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:5: ( ( '\\r' )? '\\n' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:9: ( '\\r' )? '\\n'
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:9: ( '\\r' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:9: '\\r'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:329:5: ( ( ' ' | '\\t' )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:329:9: ( ' ' | '\\t' )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:329:9: ( ' ' | '\\t' )+
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:334:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:334:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:334:14: ( options {greedy=false; } : . )*
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
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:334:44: .
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:5: ({...}? => ( Whitespace )? '//' (~ ( '\\n' ) )* | {...}? => Whitespace '//' (~ ( '\\n' ) )* )
            int alt11=2;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:9: {...}? => ( Whitespace )? '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() == 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() == 0 ");
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:46: ( Whitespace )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\t'||LA8_0==' ') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:46: Whitespace
                            {
                            mWhitespace(); 

                            }
                            break;

                    }

                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:63: (~ ( '\\n' ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='\u0000' && LA9_0<='\t')||(LA9_0>='\u000B' && LA9_0<='\uFFFF')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:343:63: ~ ( '\\n' )
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
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:344:9: {...}? => Whitespace '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() >= 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() >= 0 ");
                    }
                    mWhitespace(); 
                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:344:62: (~ ( '\\n' ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\uFFFF')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:344:62: ~ ( '\\n' )
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
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LineComment"

    // $ANTLR start "Uri"
    public final void mUri() throws RecognitionException {
        try {
            int _type = Uri;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:351:2: ( ( UriCharacter )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:351:6: ( UriCharacter )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:351:6: ( UriCharacter )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0=='!'||(LA12_0>='#' && LA12_0<='\'')||(LA12_0>='*' && LA12_0<=';')||LA12_0=='='||(LA12_0>='?' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='z')||LA12_0=='~') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:351:6: UriCharacter
            	    {
            	    mUriCharacter(); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Uri"

    // $ANTLR start "UriCharacter"
    public final void mUriCharacter() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:356:5: ( UriReserved | UriUnescaped | UriEscaped )
            int alt13=3;
            switch ( input.LA(1) ) {
            case '#':
            case '$':
            case '&':
            case '+':
            case ',':
            case '/':
            case ':':
            case ';':
            case '=':
            case '?':
            case '@':
                {
                alt13=1;
                }
                break;
            case '!':
            case '\'':
            case '*':
            case '-':
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '_':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '~':
                {
                alt13=2;
                }
                break;
            case '%':
                {
                alt13=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:356:9: UriReserved
                    {
                    mUriReserved(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:357:6: UriUnescaped
                    {
                    mUriUnescaped(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:358:6: UriEscaped
                    {
                    mUriEscaped(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "UriCharacter"

    // $ANTLR start "UriReserved"
    public final void mUriReserved() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:363:5: ( Semi | Slash | Question | Colon | At | Amper | Equals | Plus | Dollar | Comma | Hash )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            {
            if ( (input.LA(1)>='#' && input.LA(1)<='$')||input.LA(1)=='&'||(input.LA(1)>='+' && input.LA(1)<=',')||input.LA(1)=='/'||(input.LA(1)>=':' && input.LA(1)<=';')||input.LA(1)=='='||(input.LA(1)>='?' && input.LA(1)<='@') ) {
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
    // $ANTLR end "UriReserved"

    // $ANTLR start "UriUnescaped"
    public final void mUriUnescaped() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:368:5: ( UriAlpha | DecimalDigit | UriMark )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '_':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                {
                alt14=1;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                {
                alt14=2;
                }
                break;
            case '!':
            case '\'':
            case '*':
            case '-':
            case '.':
            case '~':
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:368:9: UriAlpha
                    {
                    mUriAlpha(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:369:6: DecimalDigit
                    {
                    mDecimalDigit(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:370:6: UriMark
                    {
                    mUriMark(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "UriUnescaped"

    // $ANTLR start "UriAlpha"
    public final void mUriAlpha() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:375:5: ( Identifier )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:375:9: Identifier
            {
            mIdentifier(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UriAlpha"

    // $ANTLR start "UriMark"
    public final void mUriMark() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:380:5: ( Minus | Dot | Exclamation | Tilde | Asterisk | Apostrophe )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
            {
            if ( input.LA(1)=='!'||input.LA(1)=='\''||input.LA(1)=='*'||(input.LA(1)>='-' && input.LA(1)<='.')||input.LA(1)=='~' ) {
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
    // $ANTLR end "UriMark"

    // $ANTLR start "UriEscaped"
    public final void mUriEscaped() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:385:5: ( Percent HexDigit HexDigit )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:385:8: Percent HexDigit HexDigit
            {
            mPercent(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UriEscaped"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:390:2: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:390:4: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
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
    // $ANTLR end "HexDigit"

    // $ANTLR start "DecimalDigit"
    public final void mDecimalDigit() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:2: ( ( '0' .. '9' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:6: ( '0' .. '9' )
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:6: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:7: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DecimalDigit"

    public void mTokens() throws RecognitionException {
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:8: ( T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equals | Exclamation | Hash | Minus | Percent | Plus | Question | Semi | Slash | Tilde | Trigger | NameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri )
        int alt15=41;
        alt15 = dfa15.predict(input);
        switch (alt15) {
            case 1 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:10: T__53
                {
                mT__53(); 

                }
                break;
            case 2 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:16: T__54
                {
                mT__54(); 

                }
                break;
            case 3 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:22: T__55
                {
                mT__55(); 

                }
                break;
            case 4 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:28: T__56
                {
                mT__56(); 

                }
                break;
            case 5 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:34: T__57
                {
                mT__57(); 

                }
                break;
            case 6 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:40: T__58
                {
                mT__58(); 

                }
                break;
            case 7 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:46: And
                {
                mAnd(); 

                }
                break;
            case 8 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:50: Not
                {
                mNot(); 

                }
                break;
            case 9 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:54: Or
                {
                mOr(); 

                }
                break;
            case 10 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:57: XOr
                {
                mXOr(); 

                }
                break;
            case 11 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:61: LParen
                {
                mLParen(); 

                }
                break;
            case 12 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:68: RParen
                {
                mRParen(); 

                }
                break;
            case 13 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:75: Amper
                {
                mAmper(); 

                }
                break;
            case 14 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:81: Apostrophe
                {
                mApostrophe(); 

                }
                break;
            case 15 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:92: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 16 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:101: At
                {
                mAt(); 

                }
                break;
            case 17 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:104: Colon
                {
                mColon(); 

                }
                break;
            case 18 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:110: Comma
                {
                mComma(); 

                }
                break;
            case 19 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:116: Dollar
                {
                mDollar(); 

                }
                break;
            case 20 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:123: Dot
                {
                mDot(); 

                }
                break;
            case 21 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:127: Equals
                {
                mEquals(); 

                }
                break;
            case 22 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:134: Exclamation
                {
                mExclamation(); 

                }
                break;
            case 23 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:146: Hash
                {
                mHash(); 

                }
                break;
            case 24 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:151: Minus
                {
                mMinus(); 

                }
                break;
            case 25 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:157: Percent
                {
                mPercent(); 

                }
                break;
            case 26 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:165: Plus
                {
                mPlus(); 

                }
                break;
            case 27 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:170: Question
                {
                mQuestion(); 

                }
                break;
            case 28 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:179: Semi
                {
                mSemi(); 

                }
                break;
            case 29 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:184: Slash
                {
                mSlash(); 

                }
                break;
            case 30 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:190: Tilde
                {
                mTilde(); 

                }
                break;
            case 31 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:196: Trigger
                {
                mTrigger(); 

                }
                break;
            case 32 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:204: NameAttribute
                {
                mNameAttribute(); 

                }
                break;
            case 33 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:218: ResourceTypeAttribute
                {
                mResourceTypeAttribute(); 

                }
                break;
            case 34 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:240: ResourceReferenceAttribute
                {
                mResourceReferenceAttribute(); 

                }
                break;
            case 35 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:267: Annotation
                {
                mAnnotation(); 

                }
                break;
            case 36 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:278: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 37 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:289: Newline
                {
                mNewline(); 

                }
                break;
            case 38 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:297: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 39 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:308: BlockComment
                {
                mBlockComment(); 

                }
                break;
            case 40 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:321: LineComment
                {
                mLineComment(); 

                }
                break;
            case 41 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:333: Uri
                {
                mUri(); 

                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA11_eotS =
        "\6\uffff";
    static final String DFA11_eofS =
        "\6\uffff";
    static final String DFA11_minS =
        "\2\11\1\uffff\1\57\1\0\1\uffff";
    static final String DFA11_maxS =
        "\2\57\1\uffff\1\57\1\0\1\uffff";
    static final String DFA11_acceptS =
        "\2\uffff\1\1\2\uffff\1\2";
    static final String DFA11_specialS =
        "\1\3\1\2\1\uffff\1\1\1\0\1\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\1\26\uffff\1\1\16\uffff\1\2",
            "\1\1\26\uffff\1\1\16\uffff\1\3",
            "",
            "\1\4",
            "\1\uffff",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "341:1: LineComment : ({...}? => ( Whitespace )? '//' (~ ( '\\n' ) )* | {...}? => Whitespace '//' (~ ( '\\n' ) )* );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_4 = input.LA(1);

                         
                        int index11_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 2;}

                        else if ( (( getCharPositionInLine() >= 0 )) ) {s = 5;}

                         
                        input.seek(index11_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA11_3 = input.LA(1);

                         
                        int index11_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_3=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 4;}

                         
                        input.seek(index11_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA11_1 = input.LA(1);

                         
                        int index11_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_1=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 3;}

                        else if ( (LA11_1=='\t'||LA11_1==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                         
                        input.seek(index11_1);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA11_0 = input.LA(1);

                         
                        int index11_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_0=='\t'||LA11_0==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                        else if ( (LA11_0=='/') && (( getCharPositionInLine() == 0 ))) {s = 2;}

                         
                        input.seek(index11_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA15_eotS =
        "\1\uffff\7\43\2\uffff\1\56\1\57\1\60\1\61\1\63\1\64\1\65\1\66\1"+
        "\67\1\70\1\71\1\72\1\73\1\74\1\75\1\76\1\101\1\102\3\43\1\uffff"+
        "\1\105\1\uffff\1\43\1\uffff\5\43\1\115\4\43\4\uffff\1\41\14\uffff"+
        "\1\41\1\136\2\uffff\2\43\2\uffff\5\43\2\uffff\1\154\1\155\1\43\1"+
        "\157\11\41\2\uffff\1\41\1\uffff\4\136\1\144\1\uffff\7\43\2\uffff"+
        "\1\43\1\uffff\5\41\2\uffff\2\41\1\134\1\uffff\2\136\1\144\1\43\1"+
        "\u008f\6\43\1\u009b\4\41\1\uffff\1\41\1\136\1\u00a2\1\uffff\5\u008f"+
        "\6\43\1\uffff\5\u009b\1\41\1\uffff\5\u00a2\3\u008f\6\43\3\u009b"+
        "\3\u00a2\1\u008f\6\43\1\u009b\1\u00a2\1\41\1\43\1\41\1\u00c9\1\u00ca"+
        "\1\u00cb\1\41\1\u00cd\1\41\3\uffff\1\41\1\uffff\13\41\1\u00db\1"+
        "\u00dc\2\uffff";
    static final String DFA15_eofS =
        "\u00dd\uffff";
    static final String DFA15_minS =
        "\1\11\7\41\2\uffff\14\41\1\60\10\41\1\uffff\1\11\1\uffff\1\41\1"+
        "\uffff\4\41\1\11\5\41\4\uffff\1\56\14\uffff\2\0\2\uffff\2\41\2\uffff"+
        "\5\41\2\uffff\4\41\1\56\1\101\1\0\1\101\1\56\5\0\1\uffff\6\0\1\60"+
        "\1\uffff\7\41\2\uffff\1\41\1\uffff\1\56\5\0\1\uffff\2\0\1\41\3\0"+
        "\1\60\11\41\2\56\5\0\1\41\1\uffff\4\41\1\60\6\41\1\uffff\4\41\1"+
        "\60\1\0\1\uffff\4\41\1\60\2\41\1\60\10\41\1\60\2\41\1\60\11\41\1"+
        "\162\1\41\1\162\3\41\1\145\1\41\1\145\3\uffff\1\163\1\uffff\1\163"+
        "\2\157\2\165\2\162\2\143\2\145\2\41\2\uffff";
    static final String DFA15_maxS =
        "\10\176\2\uffff\14\176\1\146\10\176\1\uffff\1\57\1\uffff\1\176\1"+
        "\uffff\12\176\4\uffff\1\172\14\uffff\2\uffff\2\uffff\2\176\2\uffff"+
        "\5\176\2\uffff\4\176\2\172\1\uffff\2\172\5\uffff\1\uffff\1\uffff"+
        "\1\0\4\uffff\1\146\1\uffff\7\176\2\uffff\1\176\1\uffff\1\172\5\uffff"+
        "\1\uffff\2\uffff\1\176\3\uffff\1\146\11\176\2\172\5\uffff\1\176"+
        "\1\uffff\4\176\1\146\6\176\1\uffff\4\176\1\146\1\uffff\1\uffff\4"+
        "\176\1\146\2\176\1\146\10\176\1\146\2\176\1\146\11\176\1\162\1\176"+
        "\1\162\3\176\1\145\1\176\1\145\3\uffff\1\163\1\uffff\1\163\2\157"+
        "\2\165\2\162\2\143\2\145\2\176\2\uffff";
    static final String DFA15_acceptS =
        "\10\uffff\1\13\1\14\25\uffff\1\45\1\uffff\1\51\1\uffff\1\44\12\uffff"+
        "\1\15\1\16\1\17\1\20\1\uffff\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
        "\1\30\1\31\1\32\1\33\1\34\2\uffff\1\35\1\36\2\uffff\1\46\1\50\5"+
        "\uffff\1\37\1\11\16\uffff\1\47\7\uffff\1\50\7\uffff\1\7\1\10\1\uffff"+
        "\1\12\6\uffff\1\43\30\uffff\1\42\13\uffff\1\40\6\uffff\1\41\46\uffff"+
        "\1\4\1\5\1\6\1\uffff\1\3\15\uffff\1\1\1\2";
    static final String DFA15_specialS =
        "\40\uffff\1\17\36\uffff\1\14\1\32\23\uffff\1\22\2\uffff\1\5\1\0"+
        "\1\25\1\30\1\36\1\uffff\1\26\1\35\1\16\1\11\1\6\1\12\1\1\15\uffff"+
        "\1\21\1\3\1\15\1\20\1\10\1\uffff\1\7\1\24\1\uffff\1\31\1\33\1\2"+
        "\1\23\13\uffff\1\37\1\34\1\40\1\27\1\4\23\uffff\1\13\73\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\40\1\37\2\uffff\1\37\22\uffff\1\40\1\23\1\uffff\1\24\1\20"+
            "\1\26\1\12\1\13\1\10\1\11\1\14\1\27\1\17\1\25\1\21\1\32\12\41"+
            "\1\16\1\31\1\uffff\1\22\1\uffff\1\30\1\15\32\36\4\uffff\1\36"+
            "\1\uffff\1\5\1\36\1\1\11\36\1\3\1\6\1\4\2\36\1\35\1\2\1\34\3"+
            "\36\1\7\2\36\3\uffff\1\33",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\16\44\1\42\13\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\24\44\1\46\5\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\24\44\1\47\5\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\15\44\1\50\3\44\1\51\10\44"+
            "\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\15\44\1\52\14\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\1\54\15\44\1\53\13\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\16\44\1\55\13\44\3\uffff\1"+
            "\41",
            "",
            "",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\2\41"+
            "\32\62\4\uffff\1\62\1\uffff\32\62\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\12\41\7\uffff\6\41\32\uffff\6\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\1\77\4\41\1\100\14\41\1\uffff\1"+
            "\41\1\uffff\34\41\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\30\44\1\103\1\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\104\25\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "",
            "\1\40\26\uffff\1\40\16\uffff\1\106",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\15\44\1\107\14\44\3\uffff\1"+
            "\41",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\17\44\1\110\12\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\22\44\1\111\7\44\3\uffff\1"+
            "\41",
            "\1\114\26\uffff\1\114\1\41\1\uffff\5\41\2\uffff\6\41\12\45"+
            "\2\41\1\uffff\1\41\1\uffff\2\41\32\44\4\uffff\1\44\1\uffff\5"+
            "\44\1\112\14\44\1\113\7\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\3\44\1\116\26\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\23\44\1\117\6\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\14\44\1\120\15\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\21\44\1\121\10\44\3\uffff\1"+
            "\41",
            "",
            "",
            "",
            "",
            "\1\125\1\uffff\12\126\1\123\2\uffff\1\124\3\uffff\32\122\4"+
            "\uffff\1\122\1\uffff\32\122",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\141\2\137\1\144\1\137\1\144"+
            "\2\137\32\140\4\144\1\140\1\144\32\140\3\144\1\142\uff81\144",
            "",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\17\44\1\145\12\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\5\44\1\146\24\44\3\uffff\1"+
            "\41",
            "",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\22\44\1\147\7\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\17\44\1\150\12\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\23\44\1\151\6\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\1\152\31\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\24\44\1\153\5\44\3\uffff\1"+
            "\41",
            "",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\156\25\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\125\1\uffff\12\126\1\123\2\uffff\1\124\3\uffff\32\122\4"+
            "\uffff\1\122\1\uffff\32\122",
            "\32\160\4\uffff\1\160\1\uffff\32\160",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\163\2\161\1\166\1\161\1\166\2\161\32\162\4\166"+
            "\1\162\1\166\32\162\3\166\1\164\uff81\166",
            "\32\160\4\uffff\1\160\1\uffff\32\160",
            "\1\125\1\uffff\12\126\1\123\2\uffff\1\124\3\uffff\32\122\4"+
            "\uffff\1\122\1\uffff\32\122",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\170\2\127\1\134\1\127\1\134\2\127\32\167\4\134"+
            "\1\167\1\134\32\167\3\134\1\135\uff81\134",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\171\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\60\134\12\172\7\134\6\172\32\134\6\172\uff99\134",
            "",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\1\uffff",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\141\2\137\1\144\1\137\1\144"+
            "\2\137\32\140\4\144\1\140\1\144\32\140\3\144\1\142\uff81\144",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\174\2\137\1\144\1\137\1\144"+
            "\2\137\32\173\4\144\1\173\1\144\32\173\3\144\1\142\uff81\144",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\141\2\137\1\144\1\137\1\144"+
            "\2\137\32\140\4\144\1\140\1\144\32\140\3\144\1\142\uff81\144",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\141\2\137\1\144\1\137\1\144"+
            "\2\137\32\140\4\144\1\140\1\144\32\140\3\144\1\142\uff81\144",
            "\12\175\7\uffff\6\175\32\uffff\6\175",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\176\25\44\3\uffff\1"+
            "\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\177\1"+
            "\uffff\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\23\44\1\u0081\1\u0080\5\44"+
            "\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\13\44\1\u0082\16\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\u0083\25\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\10\44\1\u0084\21\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\2\44\1\u0085\27\44\3\uffff"+
            "\1\41",
            "",
            "",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\u0086"+
            "\1\uffff\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "",
            "\1\125\1\uffff\12\u0088\1\123\2\uffff\1\124\3\uffff\32\u0087"+
            "\4\uffff\1\u0087\1\uffff\32\u0087",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\163\2\161\1\166\1\161\1\166\2\161\32\162\4\166"+
            "\1\162\1\166\32\162\3\166\1\164\uff81\166",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\u008a\2\161\1\166\1\161\1\166\2\161\32\u0089"+
            "\4\166\1\u0089\1\166\32\u0089\3\166\1\164\uff81\166",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\163\2\161\1\166\1\161\1\166\2\161\32\162\4\166"+
            "\1\162\1\166\32\162\3\166\1\164\uff81\166",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\163\2\161\1\166\1\161\1\166\2\161\32\162\4\166"+
            "\1\162\1\166\32\162\3\166\1\164\uff81\166",
            "\60\166\12\u008b\7\166\6\u008b\32\166\6\u008b\uff99\166",
            "",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\170\2\127\1\134\1\127\1\134\2\127\32\167\4\134"+
            "\1\167\1\134\32\167\3\134\1\135\uff81\134",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\170\2\127\1\134\1\127\1\134\2\127\32\167\4\134"+
            "\1\167\1\134\32\167\3\134\1\135\uff81\134",
            "\1\135\1\uffff\2\127\1\133\1\127\1\135\2\uffff\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\uffff\1\127\1\uffff\2\127\32\130"+
            "\4\uffff\1\130\1\uffff\32\130\3\uffff\1\135",
            "\60\134\12\u008c\7\134\6\u008c\32\134\6\u008c\uff99\134",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\174\2\137\1\144\1\137\1\144"+
            "\2\137\32\173\4\144\1\173\1\144\32\173\3\144\1\142\uff81\144",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\174\2\137\1\144\1\137\1\144"+
            "\2\137\32\173\4\144\1\173\1\144\32\173\3\144\1\142\uff81\144",
            "\12\u008d\7\uffff\6\u008d\32\uffff\6\u008d",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\u008e"+
            "\1\uffff\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u0092\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u0091\4\uffff\1\u0091\1\uffff\32\u0091\3"+
            "\uffff\1\u0093",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\14\44\1\u0095\15\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\21\44\1\u0096\10\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\10\44\1\u0097\21\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\27\44\1\u0098\2\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\13\44\1\u0099\16\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\2\44\1\u009a\27\44\3\uffff"+
            "\1\41",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u009d\4\uffff\1\u009d\1\uffff\32\u009d\3"+
            "\uffff\1\u009f",
            "\1\125\1\uffff\12\u0088\1\123\2\uffff\1\124\3\uffff\32\u0087"+
            "\4\uffff\1\u0087\1\uffff\32\u0087",
            "\1\125\1\uffff\12\u0088\1\123\2\uffff\1\124\3\uffff\32\u0087"+
            "\4\uffff\1\u0087\1\uffff\32\u0087",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\u008a\2\161\1\166\1\161\1\166\2\161\32\u0089"+
            "\4\166\1\u0089\1\166\32\u0089\3\166\1\164\uff81\166",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\u008a\2\161\1\166\1\161\1\166\2\161\32\u0089"+
            "\4\166\1\u0089\1\166\32\u0089\3\166\1\164\uff81\166",
            "\60\166\12\u00a1\7\166\6\u00a1\32\166\6\u00a1\uff99\166",
            "\41\134\1\135\1\134\2\127\1\133\1\127\1\135\2\134\1\132\2\127"+
            "\2\135\1\127\12\131\2\127\1\134\1\127\1\134\2\127\32\130\4\134"+
            "\1\130\1\134\32\130\3\134\1\135\uff81\134",
            "\12\144\1\uffff\26\144\1\142\1\144\2\137\1\143\1\137\1\142"+
            "\2\144\1\142\2\137\2\142\1\137\12\141\2\137\1\144\1\137\1\144"+
            "\2\137\32\140\4\144\1\140\1\144\32\140\3\144\1\142\uff81\144",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00a5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00a4\4\uffff\1\u00a4\1\uffff\32\u00a4\3"+
            "\uffff\1\u00a6",
            "",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u0092\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u0091\4\uffff\1\u0091\1\uffff\32\u0091\3"+
            "\uffff\1\u0093",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u00a9\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u00a8\4\uffff\1\u00a8\1\uffff\32\u00a8\3"+
            "\uffff\1\u0093",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u0092\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u0091\4\uffff\1\u0091\1\uffff\32\u0091\3"+
            "\uffff\1\u0093",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u0092\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u0091\4\uffff\1\u0091\1\uffff\32\u0091\3"+
            "\uffff\1\u0093",
            "\12\u00aa\7\uffff\6\u00aa\32\uffff\6\u00aa",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\u00ab\25\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\1\u00ac\31\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\u00ad\25\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\10\44\1\u00ae\21\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\24\44\1\u00af\5\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\u00b0\25\44\3\uffff"+
            "\1\41",
            "",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u009d\4\uffff\1\u009d\1\uffff\32\u009d\3"+
            "\uffff\1\u009f",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u00b2\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u00b1\4\uffff\1\u00b1\1\uffff\32\u00b1\3"+
            "\uffff\1\u009f",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u009d\4\uffff\1\u009d\1\uffff\32\u009d\3"+
            "\uffff\1\u009f",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u009d\4\uffff\1\u009d\1\uffff\32\u009d\3"+
            "\uffff\1\u009f",
            "\12\u00b3\7\uffff\6\u00b3\32\uffff\6\u00b3",
            "\41\166\1\164\1\166\2\161\1\165\1\161\1\164\2\166\1\164\2\161"+
            "\2\164\1\161\12\163\2\161\1\166\1\161\1\166\2\161\32\162\4\166"+
            "\1\162\1\166\32\162\3\166\1\164\uff81\166",
            "",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00a5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00a4\4\uffff\1\u00a4\1\uffff\32\u00a4\3"+
            "\uffff\1\u00a6",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00b5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00b4\4\uffff\1\u00b4\1\uffff\32\u00b4\3"+
            "\uffff\1\u00a6",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00a5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00a4\4\uffff\1\u00a4\1\uffff\32\u00a4\3"+
            "\uffff\1\u00a6",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00a5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00a4\4\uffff\1\u00a4\1\uffff\32\u00a4\3"+
            "\uffff\1\u00a6",
            "\12\u00b6\7\uffff\6\u00b6\32\uffff\6\u00b6",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u00a9\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u00a8\4\uffff\1\u00a8\1\uffff\32\u00a8\3"+
            "\uffff\1\u0093",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u00a9\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u00a8\4\uffff\1\u00a8\1\uffff\32\u00a8\3"+
            "\uffff\1\u0093",
            "\12\u00b7\7\uffff\6\u00b7\32\uffff\6\u00b7",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\21\44\1\u00b8\10\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\10\44\1\u00b9\21\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\21\44\1\u00ba\10\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\22\44\1\u00bb\7\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\21\44\1\u00bc\10\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\22\44\1\u00bd\7\44\3\uffff"+
            "\1\41",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u00b2\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u00b1\4\uffff\1\u00b1\1\uffff\32\u00b1\3"+
            "\uffff\1\u009f",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u00b2\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u00b1\4\uffff\1\u00b1\1\uffff\32\u00b1\3"+
            "\uffff\1\u009f",
            "\12\u00be\7\uffff\6\u00be\32\uffff\6\u00be",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00b5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00b4\4\uffff\1\u00b4\1\uffff\32\u00b4\3"+
            "\uffff\1\u00a6",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00b5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00b4\4\uffff\1\u00b4\1\uffff\32\u00b4\3"+
            "\uffff\1\u00a6",
            "\12\u00bf\7\uffff\6\u00bf\32\uffff\6\u00bf",
            "\1\u0093\1\uffff\2\u0090\1\u0094\1\u0090\1\u0093\2\uffff\1"+
            "\u0093\2\u0090\2\u0093\1\u0090\12\u0092\2\u0090\1\uffff\1\u0090"+
            "\1\uffff\2\u0090\32\u0091\4\uffff\1\u0091\1\uffff\32\u0091\3"+
            "\uffff\1\u0093",
            "\1\41\1\uffff\5\41\2\uffff\3\41\1\u00c0\2\41\12\45\2\41\1\uffff"+
            "\1\41\1\uffff\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\15\44\1\u00c1\14\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\3\41\1\u00c2\2\41\12\45\2\41\1\uffff"+
            "\1\41\1\uffff\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\23\44\1\u00c3\6\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\4\44\1\u00c4\25\44\3\uffff"+
            "\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\22\44\1\u00c5\7\44\3\uffff"+
            "\1\41",
            "\1\u009f\1\uffff\2\u009c\1\u00a0\1\u009c\1\u009f\2\uffff\1"+
            "\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\uffff\1\u009c"+
            "\1\uffff\2\u009c\32\u009d\4\uffff\1\u009d\1\uffff\32\u009d\3"+
            "\uffff\1\u009f",
            "\1\u00a6\1\uffff\2\u00a3\1\u00a7\1\u00a3\1\u00a6\2\uffff\1"+
            "\u00a6\2\u00a3\2\u00a6\1\u00a3\12\u00a5\2\u00a3\1\uffff\1\u00a3"+
            "\1\uffff\2\u00a3\32\u00a4\4\uffff\1\u00a4\1\uffff\32\u00a4\3"+
            "\uffff\1\u00a6",
            "\1\u00c6",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\23\44\1\u00c7\6\44\3\uffff"+
            "\1\41",
            "\1\u00c8",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\u00cc",
            "\1\41\1\uffff\5\41\2\uffff\6\41\12\45\2\41\1\uffff\1\41\1\uffff"+
            "\2\41\32\44\4\uffff\1\44\1\uffff\32\44\3\uffff\1\41",
            "\1\u00ce",
            "",
            "",
            "",
            "\1\u00cf",
            "",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "\1\41\1\uffff\5\41\2\uffff\22\41\1\uffff\1\41\1\uffff\34\41"+
            "\4\uffff\1\41\1\uffff\32\41\3\uffff\1\41",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equals | Exclamation | Hash | Minus | Percent | Plus | Question | Semi | Slash | Tilde | Trigger | NameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA15_88 = input.LA(1);

                        s = -1;
                        if ( ((LA15_88>='A' && LA15_88<='Z')||LA15_88=='_'||(LA15_88>='a' && LA15_88<='z')) ) {s = 119;}

                        else if ( ((LA15_88>='#' && LA15_88<='$')||LA15_88=='&'||(LA15_88>='+' && LA15_88<=',')||LA15_88=='/'||(LA15_88>=':' && LA15_88<=';')||LA15_88=='='||(LA15_88>='?' && LA15_88<='@')) ) {s = 87;}

                        else if ( ((LA15_88>='0' && LA15_88<='9')) ) {s = 120;}

                        else if ( (LA15_88=='*') ) {s = 90;}

                        else if ( (LA15_88=='%') ) {s = 91;}

                        else if ( (LA15_88=='!'||LA15_88=='\''||(LA15_88>='-' && LA15_88<='.')||LA15_88=='~') ) {s = 93;}

                        else if ( ((LA15_88>='\u0000' && LA15_88<=' ')||LA15_88=='\"'||(LA15_88>='(' && LA15_88<=')')||LA15_88=='<'||LA15_88=='>'||(LA15_88>='[' && LA15_88<='^')||LA15_88=='`'||(LA15_88>='{' && LA15_88<='}')||(LA15_88>='\u007F' && LA15_88<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA15_99 = input.LA(1);

                         
                        int index15_99 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_99>='0' && LA15_99<='9')||(LA15_99>='A' && LA15_99<='F')||(LA15_99>='a' && LA15_99<='f')) ) {s = 125;}

                        else s = 100;

                         
                        input.seek(index15_99);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA15_124 = input.LA(1);

                         
                        int index15_124 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_124>='#' && LA15_124<='$')||LA15_124=='&'||(LA15_124>='+' && LA15_124<=',')||LA15_124=='/'||(LA15_124>=':' && LA15_124<=';')||LA15_124=='='||(LA15_124>='?' && LA15_124<='@')) ) {s = 95;}

                        else if ( ((LA15_124>='A' && LA15_124<='Z')||LA15_124=='_'||(LA15_124>='a' && LA15_124<='z')) ) {s = 123;}

                        else if ( ((LA15_124>='0' && LA15_124<='9')) ) {s = 124;}

                        else if ( (LA15_124=='!'||LA15_124=='\''||LA15_124=='*'||(LA15_124>='-' && LA15_124<='.')||LA15_124=='~') ) {s = 98;}

                        else if ( (LA15_124=='%') ) {s = 99;}

                        else if ( ((LA15_124>='\u0000' && LA15_124<='\t')||(LA15_124>='\u000B' && LA15_124<=' ')||LA15_124=='\"'||(LA15_124>='(' && LA15_124<=')')||LA15_124=='<'||LA15_124=='>'||(LA15_124>='[' && LA15_124<='^')||LA15_124=='`'||(LA15_124>='{' && LA15_124<='}')||(LA15_124>='\u007F' && LA15_124<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_124);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA15_114 = input.LA(1);

                        s = -1;
                        if ( ((LA15_114>='\u0000' && LA15_114<=' ')||LA15_114=='\"'||(LA15_114>='(' && LA15_114<=')')||LA15_114=='<'||LA15_114=='>'||(LA15_114>='[' && LA15_114<='^')||LA15_114=='`'||(LA15_114>='{' && LA15_114<='}')||(LA15_114>='\u007F' && LA15_114<='\uFFFF')) ) {s = 118;}

                        else if ( ((LA15_114>='A' && LA15_114<='Z')||LA15_114=='_'||(LA15_114>='a' && LA15_114<='z')) ) {s = 137;}

                        else if ( ((LA15_114>='#' && LA15_114<='$')||LA15_114=='&'||(LA15_114>='+' && LA15_114<=',')||LA15_114=='/'||(LA15_114>=':' && LA15_114<=';')||LA15_114=='='||(LA15_114>='?' && LA15_114<='@')) ) {s = 113;}

                        else if ( (LA15_114=='!'||LA15_114=='\''||LA15_114=='*'||(LA15_114>='-' && LA15_114<='.')||LA15_114=='~') ) {s = 116;}

                        else if ( ((LA15_114>='0' && LA15_114<='9')) ) {s = 138;}

                        else if ( (LA15_114=='%') ) {s = 117;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA15_141 = input.LA(1);

                         
                        int index15_141 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_141>='#' && LA15_141<='$')||LA15_141=='&'||(LA15_141>='+' && LA15_141<=',')||LA15_141=='/'||(LA15_141>=':' && LA15_141<=';')||LA15_141=='='||(LA15_141>='?' && LA15_141<='@')) ) {s = 95;}

                        else if ( ((LA15_141>='A' && LA15_141<='Z')||LA15_141=='_'||(LA15_141>='a' && LA15_141<='z')) ) {s = 96;}

                        else if ( ((LA15_141>='0' && LA15_141<='9')) ) {s = 97;}

                        else if ( (LA15_141=='!'||LA15_141=='\''||LA15_141=='*'||(LA15_141>='-' && LA15_141<='.')||LA15_141=='~') ) {s = 98;}

                        else if ( (LA15_141=='%') ) {s = 99;}

                        else if ( ((LA15_141>='\u0000' && LA15_141<='\t')||(LA15_141>='\u000B' && LA15_141<=' ')||LA15_141=='\"'||(LA15_141>='(' && LA15_141<=')')||LA15_141=='<'||LA15_141=='>'||(LA15_141>='[' && LA15_141<='^')||LA15_141=='`'||(LA15_141>='{' && LA15_141<='}')||(LA15_141>='\u007F' && LA15_141<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_141);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA15_87 = input.LA(1);

                        s = -1;
                        if ( ((LA15_87>='#' && LA15_87<='$')||LA15_87=='&'||(LA15_87>='+' && LA15_87<=',')||LA15_87=='/'||(LA15_87>=':' && LA15_87<=';')||LA15_87=='='||(LA15_87>='?' && LA15_87<='@')) ) {s = 87;}

                        else if ( ((LA15_87>='A' && LA15_87<='Z')||LA15_87=='_'||(LA15_87>='a' && LA15_87<='z')) ) {s = 88;}

                        else if ( ((LA15_87>='0' && LA15_87<='9')) ) {s = 89;}

                        else if ( (LA15_87=='*') ) {s = 90;}

                        else if ( (LA15_87=='%') ) {s = 91;}

                        else if ( (LA15_87=='!'||LA15_87=='\''||(LA15_87>='-' && LA15_87<='.')||LA15_87=='~') ) {s = 93;}

                        else if ( ((LA15_87>='\u0000' && LA15_87<=' ')||LA15_87=='\"'||(LA15_87>='(' && LA15_87<=')')||LA15_87=='<'||LA15_87=='>'||(LA15_87>='[' && LA15_87<='^')||LA15_87=='`'||(LA15_87>='{' && LA15_87<='}')||(LA15_87>='\u007F' && LA15_87<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA15_97 = input.LA(1);

                         
                        int index15_97 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_97>='#' && LA15_97<='$')||LA15_97=='&'||(LA15_97>='+' && LA15_97<=',')||LA15_97=='/'||(LA15_97>=':' && LA15_97<=';')||LA15_97=='='||(LA15_97>='?' && LA15_97<='@')) ) {s = 95;}

                        else if ( ((LA15_97>='A' && LA15_97<='Z')||LA15_97=='_'||(LA15_97>='a' && LA15_97<='z')) ) {s = 96;}

                        else if ( ((LA15_97>='0' && LA15_97<='9')) ) {s = 97;}

                        else if ( (LA15_97=='!'||LA15_97=='\''||LA15_97=='*'||(LA15_97>='-' && LA15_97<='.')||LA15_97=='~') ) {s = 98;}

                        else if ( (LA15_97=='%') ) {s = 99;}

                        else if ( ((LA15_97>='\u0000' && LA15_97<='\t')||(LA15_97>='\u000B' && LA15_97<=' ')||LA15_97=='\"'||(LA15_97>='(' && LA15_97<=')')||LA15_97=='<'||LA15_97=='>'||(LA15_97>='[' && LA15_97<='^')||LA15_97=='`'||(LA15_97>='{' && LA15_97<='}')||(LA15_97>='\u007F' && LA15_97<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_97);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA15_119 = input.LA(1);

                        s = -1;
                        if ( ((LA15_119>='#' && LA15_119<='$')||LA15_119=='&'||(LA15_119>='+' && LA15_119<=',')||LA15_119=='/'||(LA15_119>=':' && LA15_119<=';')||LA15_119=='='||(LA15_119>='?' && LA15_119<='@')) ) {s = 87;}

                        else if ( ((LA15_119>='A' && LA15_119<='Z')||LA15_119=='_'||(LA15_119>='a' && LA15_119<='z')) ) {s = 119;}

                        else if ( ((LA15_119>='0' && LA15_119<='9')) ) {s = 120;}

                        else if ( (LA15_119=='*') ) {s = 90;}

                        else if ( (LA15_119=='%') ) {s = 91;}

                        else if ( (LA15_119=='!'||LA15_119=='\''||(LA15_119>='-' && LA15_119<='.')||LA15_119=='~') ) {s = 93;}

                        else if ( ((LA15_119>='\u0000' && LA15_119<=' ')||LA15_119=='\"'||(LA15_119>='(' && LA15_119<=')')||LA15_119=='<'||LA15_119=='>'||(LA15_119>='[' && LA15_119<='^')||LA15_119=='`'||(LA15_119>='{' && LA15_119<='}')||(LA15_119>='\u007F' && LA15_119<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA15_117 = input.LA(1);

                        s = -1;
                        if ( ((LA15_117>='0' && LA15_117<='9')||(LA15_117>='A' && LA15_117<='F')||(LA15_117>='a' && LA15_117<='f')) ) {s = 139;}

                        else if ( ((LA15_117>='\u0000' && LA15_117<='/')||(LA15_117>=':' && LA15_117<='@')||(LA15_117>='G' && LA15_117<='`')||(LA15_117>='g' && LA15_117<='\uFFFF')) ) {s = 118;}

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA15_96 = input.LA(1);

                         
                        int index15_96 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_96>='A' && LA15_96<='Z')||LA15_96=='_'||(LA15_96>='a' && LA15_96<='z')) ) {s = 123;}

                        else if ( ((LA15_96>='#' && LA15_96<='$')||LA15_96=='&'||(LA15_96>='+' && LA15_96<=',')||LA15_96=='/'||(LA15_96>=':' && LA15_96<=';')||LA15_96=='='||(LA15_96>='?' && LA15_96<='@')) ) {s = 95;}

                        else if ( (LA15_96=='!'||LA15_96=='\''||LA15_96=='*'||(LA15_96>='-' && LA15_96<='.')||LA15_96=='~') ) {s = 98;}

                        else if ( ((LA15_96>='0' && LA15_96<='9')) ) {s = 124;}

                        else if ( (LA15_96=='%') ) {s = 99;}

                        else if ( ((LA15_96>='\u0000' && LA15_96<='\t')||(LA15_96>='\u000B' && LA15_96<=' ')||LA15_96=='\"'||(LA15_96>='(' && LA15_96<=')')||LA15_96=='<'||LA15_96=='>'||(LA15_96>='[' && LA15_96<='^')||LA15_96=='`'||(LA15_96>='{' && LA15_96<='}')||(LA15_96>='\u007F' && LA15_96<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_96);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA15_98 = input.LA(1);

                         
                        int index15_98 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_98>='#' && LA15_98<='$')||LA15_98=='&'||(LA15_98>='+' && LA15_98<=',')||LA15_98=='/'||(LA15_98>=':' && LA15_98<=';')||LA15_98=='='||(LA15_98>='?' && LA15_98<='@')) ) {s = 95;}

                        else if ( ((LA15_98>='A' && LA15_98<='Z')||LA15_98=='_'||(LA15_98>='a' && LA15_98<='z')) ) {s = 96;}

                        else if ( ((LA15_98>='0' && LA15_98<='9')) ) {s = 97;}

                        else if ( (LA15_98=='!'||LA15_98=='\''||LA15_98=='*'||(LA15_98>='-' && LA15_98<='.')||LA15_98=='~') ) {s = 98;}

                        else if ( (LA15_98=='%') ) {s = 99;}

                        else if ( ((LA15_98>='\u0000' && LA15_98<='\t')||(LA15_98>='\u000B' && LA15_98<=' ')||LA15_98=='\"'||(LA15_98>='(' && LA15_98<=')')||LA15_98=='<'||LA15_98=='>'||(LA15_98>='[' && LA15_98<='^')||LA15_98=='`'||(LA15_98>='{' && LA15_98<='}')||(LA15_98>='\u007F' && LA15_98<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_98);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA15_161 = input.LA(1);

                        s = -1;
                        if ( ((LA15_161>='\u0000' && LA15_161<=' ')||LA15_161=='\"'||(LA15_161>='(' && LA15_161<=')')||LA15_161=='<'||LA15_161=='>'||(LA15_161>='[' && LA15_161<='^')||LA15_161=='`'||(LA15_161>='{' && LA15_161<='}')||(LA15_161>='\u007F' && LA15_161<='\uFFFF')) ) {s = 118;}

                        else if ( ((LA15_161>='#' && LA15_161<='$')||LA15_161=='&'||(LA15_161>='+' && LA15_161<=',')||LA15_161=='/'||(LA15_161>=':' && LA15_161<=';')||LA15_161=='='||(LA15_161>='?' && LA15_161<='@')) ) {s = 113;}

                        else if ( ((LA15_161>='A' && LA15_161<='Z')||LA15_161=='_'||(LA15_161>='a' && LA15_161<='z')) ) {s = 114;}

                        else if ( ((LA15_161>='0' && LA15_161<='9')) ) {s = 115;}

                        else if ( (LA15_161=='!'||LA15_161=='\''||LA15_161=='*'||(LA15_161>='-' && LA15_161<='.')||LA15_161=='~') ) {s = 116;}

                        else if ( (LA15_161=='%') ) {s = 117;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA15_63 = input.LA(1);

                        s = -1;
                        if ( ((LA15_63>='#' && LA15_63<='$')||LA15_63=='&'||(LA15_63>='+' && LA15_63<=',')||LA15_63=='/'||(LA15_63>=':' && LA15_63<=';')||LA15_63=='='||(LA15_63>='?' && LA15_63<='@')) ) {s = 87;}

                        else if ( ((LA15_63>='A' && LA15_63<='Z')||LA15_63=='_'||(LA15_63>='a' && LA15_63<='z')) ) {s = 88;}

                        else if ( ((LA15_63>='0' && LA15_63<='9')) ) {s = 89;}

                        else if ( (LA15_63=='*') ) {s = 90;}

                        else if ( (LA15_63=='%') ) {s = 91;}

                        else if ( ((LA15_63>='\u0000' && LA15_63<=' ')||LA15_63=='\"'||(LA15_63>='(' && LA15_63<=')')||LA15_63=='<'||LA15_63=='>'||(LA15_63>='[' && LA15_63<='^')||LA15_63=='`'||(LA15_63>='{' && LA15_63<='}')||(LA15_63>='\u007F' && LA15_63<='\uFFFF')) ) {s = 92;}

                        else if ( (LA15_63=='!'||LA15_63=='\''||(LA15_63>='-' && LA15_63<='.')||LA15_63=='~') ) {s = 93;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA15_115 = input.LA(1);

                        s = -1;
                        if ( ((LA15_115>='\u0000' && LA15_115<=' ')||LA15_115=='\"'||(LA15_115>='(' && LA15_115<=')')||LA15_115=='<'||LA15_115=='>'||(LA15_115>='[' && LA15_115<='^')||LA15_115=='`'||(LA15_115>='{' && LA15_115<='}')||(LA15_115>='\u007F' && LA15_115<='\uFFFF')) ) {s = 118;}

                        else if ( ((LA15_115>='#' && LA15_115<='$')||LA15_115=='&'||(LA15_115>='+' && LA15_115<=',')||LA15_115=='/'||(LA15_115>=':' && LA15_115<=';')||LA15_115=='='||(LA15_115>='?' && LA15_115<='@')) ) {s = 113;}

                        else if ( ((LA15_115>='A' && LA15_115<='Z')||LA15_115=='_'||(LA15_115>='a' && LA15_115<='z')) ) {s = 114;}

                        else if ( ((LA15_115>='0' && LA15_115<='9')) ) {s = 115;}

                        else if ( (LA15_115=='!'||LA15_115=='\''||LA15_115=='*'||(LA15_115>='-' && LA15_115<='.')||LA15_115=='~') ) {s = 116;}

                        else if ( (LA15_115=='%') ) {s = 117;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA15_95 = input.LA(1);

                         
                        int index15_95 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_95>='#' && LA15_95<='$')||LA15_95=='&'||(LA15_95>='+' && LA15_95<=',')||LA15_95=='/'||(LA15_95>=':' && LA15_95<=';')||LA15_95=='='||(LA15_95>='?' && LA15_95<='@')) ) {s = 95;}

                        else if ( ((LA15_95>='A' && LA15_95<='Z')||LA15_95=='_'||(LA15_95>='a' && LA15_95<='z')) ) {s = 96;}

                        else if ( ((LA15_95>='0' && LA15_95<='9')) ) {s = 97;}

                        else if ( (LA15_95=='!'||LA15_95=='\''||LA15_95=='*'||(LA15_95>='-' && LA15_95<='.')||LA15_95=='~') ) {s = 98;}

                        else if ( (LA15_95=='%') ) {s = 99;}

                        else if ( ((LA15_95>='\u0000' && LA15_95<='\t')||(LA15_95>='\u000B' && LA15_95<=' ')||LA15_95=='\"'||(LA15_95>='(' && LA15_95<=')')||LA15_95=='<'||LA15_95=='>'||(LA15_95>='[' && LA15_95<='^')||LA15_95=='`'||(LA15_95>='{' && LA15_95<='}')||(LA15_95>='\u007F' && LA15_95<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_95);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA15_32 = input.LA(1);

                         
                        int index15_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA15_32=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 70;}

                        else if ( (LA15_32=='\t'||LA15_32==' ') ) {s = 32;}

                        else s = 69;

                         
                        input.seek(index15_32);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA15_116 = input.LA(1);

                        s = -1;
                        if ( ((LA15_116>='#' && LA15_116<='$')||LA15_116=='&'||(LA15_116>='+' && LA15_116<=',')||LA15_116=='/'||(LA15_116>=':' && LA15_116<=';')||LA15_116=='='||(LA15_116>='?' && LA15_116<='@')) ) {s = 113;}

                        else if ( ((LA15_116>='A' && LA15_116<='Z')||LA15_116=='_'||(LA15_116>='a' && LA15_116<='z')) ) {s = 114;}

                        else if ( ((LA15_116>='0' && LA15_116<='9')) ) {s = 115;}

                        else if ( (LA15_116=='!'||LA15_116=='\''||LA15_116=='*'||(LA15_116>='-' && LA15_116<='.')||LA15_116=='~') ) {s = 116;}

                        else if ( (LA15_116=='%') ) {s = 117;}

                        else if ( ((LA15_116>='\u0000' && LA15_116<=' ')||LA15_116=='\"'||(LA15_116>='(' && LA15_116<=')')||LA15_116=='<'||LA15_116=='>'||(LA15_116>='[' && LA15_116<='^')||LA15_116=='`'||(LA15_116>='{' && LA15_116<='}')||(LA15_116>='\u007F' && LA15_116<='\uFFFF')) ) {s = 118;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA15_113 = input.LA(1);

                        s = -1;
                        if ( ((LA15_113>='#' && LA15_113<='$')||LA15_113=='&'||(LA15_113>='+' && LA15_113<=',')||LA15_113=='/'||(LA15_113>=':' && LA15_113<=';')||LA15_113=='='||(LA15_113>='?' && LA15_113<='@')) ) {s = 113;}

                        else if ( ((LA15_113>='A' && LA15_113<='Z')||LA15_113=='_'||(LA15_113>='a' && LA15_113<='z')) ) {s = 114;}

                        else if ( ((LA15_113>='0' && LA15_113<='9')) ) {s = 115;}

                        else if ( (LA15_113=='!'||LA15_113=='\''||LA15_113=='*'||(LA15_113>='-' && LA15_113<='.')||LA15_113=='~') ) {s = 116;}

                        else if ( (LA15_113=='%') ) {s = 117;}

                        else if ( ((LA15_113>='\u0000' && LA15_113<=' ')||LA15_113=='\"'||(LA15_113>='(' && LA15_113<=')')||LA15_113=='<'||LA15_113=='>'||(LA15_113>='[' && LA15_113<='^')||LA15_113=='`'||(LA15_113>='{' && LA15_113<='}')||(LA15_113>='\u007F' && LA15_113<='\uFFFF')) ) {s = 118;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA15_84 = input.LA(1);

                        s = -1;
                        if ( ((LA15_84>='#' && LA15_84<='$')||LA15_84=='&'||(LA15_84>='+' && LA15_84<=',')||LA15_84=='/'||(LA15_84>=':' && LA15_84<=';')||LA15_84=='='||(LA15_84>='?' && LA15_84<='@')) ) {s = 113;}

                        else if ( ((LA15_84>='A' && LA15_84<='Z')||LA15_84=='_'||(LA15_84>='a' && LA15_84<='z')) ) {s = 114;}

                        else if ( ((LA15_84>='0' && LA15_84<='9')) ) {s = 115;}

                        else if ( (LA15_84=='!'||LA15_84=='\''||LA15_84=='*'||(LA15_84>='-' && LA15_84<='.')||LA15_84=='~') ) {s = 116;}

                        else if ( (LA15_84=='%') ) {s = 117;}

                        else if ( ((LA15_84>='\u0000' && LA15_84<=' ')||LA15_84=='\"'||(LA15_84>='(' && LA15_84<=')')||LA15_84=='<'||LA15_84=='>'||(LA15_84>='[' && LA15_84<='^')||LA15_84=='`'||(LA15_84>='{' && LA15_84<='}')||(LA15_84>='\u007F' && LA15_84<='\uFFFF')) ) {s = 118;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA15_125 = input.LA(1);

                         
                        int index15_125 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_125>='0' && LA15_125<='9')||(LA15_125>='A' && LA15_125<='F')||(LA15_125>='a' && LA15_125<='f')) ) {s = 141;}

                        else s = 100;

                         
                        input.seek(index15_125);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA15_120 = input.LA(1);

                        s = -1;
                        if ( ((LA15_120>='#' && LA15_120<='$')||LA15_120=='&'||(LA15_120>='+' && LA15_120<=',')||LA15_120=='/'||(LA15_120>=':' && LA15_120<=';')||LA15_120=='='||(LA15_120>='?' && LA15_120<='@')) ) {s = 87;}

                        else if ( ((LA15_120>='A' && LA15_120<='Z')||LA15_120=='_'||(LA15_120>='a' && LA15_120<='z')) ) {s = 119;}

                        else if ( ((LA15_120>='0' && LA15_120<='9')) ) {s = 120;}

                        else if ( (LA15_120=='*') ) {s = 90;}

                        else if ( (LA15_120=='%') ) {s = 91;}

                        else if ( (LA15_120=='!'||LA15_120=='\''||(LA15_120>='-' && LA15_120<='.')||LA15_120=='~') ) {s = 93;}

                        else if ( ((LA15_120>='\u0000' && LA15_120<=' ')||LA15_120=='\"'||(LA15_120>='(' && LA15_120<=')')||LA15_120=='<'||LA15_120=='>'||(LA15_120>='[' && LA15_120<='^')||LA15_120=='`'||(LA15_120>='{' && LA15_120<='}')||(LA15_120>='\u007F' && LA15_120<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA15_89 = input.LA(1);

                        s = -1;
                        if ( ((LA15_89>='#' && LA15_89<='$')||LA15_89=='&'||(LA15_89>='+' && LA15_89<=',')||LA15_89=='/'||(LA15_89>=':' && LA15_89<=';')||LA15_89=='='||(LA15_89>='?' && LA15_89<='@')) ) {s = 87;}

                        else if ( ((LA15_89>='A' && LA15_89<='Z')||LA15_89=='_'||(LA15_89>='a' && LA15_89<='z')) ) {s = 88;}

                        else if ( ((LA15_89>='0' && LA15_89<='9')) ) {s = 89;}

                        else if ( (LA15_89=='*') ) {s = 90;}

                        else if ( (LA15_89=='%') ) {s = 91;}

                        else if ( (LA15_89=='!'||LA15_89=='\''||(LA15_89>='-' && LA15_89<='.')||LA15_89=='~') ) {s = 93;}

                        else if ( ((LA15_89>='\u0000' && LA15_89<=' ')||LA15_89=='\"'||(LA15_89>='(' && LA15_89<=')')||LA15_89=='<'||LA15_89=='>'||(LA15_89>='[' && LA15_89<='^')||LA15_89=='`'||(LA15_89>='{' && LA15_89<='}')||(LA15_89>='\u007F' && LA15_89<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA15_93 = input.LA(1);

                        s = -1;
                        if ( ((LA15_93>='#' && LA15_93<='$')||LA15_93=='&'||(LA15_93>='+' && LA15_93<=',')||LA15_93=='/'||(LA15_93>=':' && LA15_93<=';')||LA15_93=='='||(LA15_93>='?' && LA15_93<='@')) ) {s = 87;}

                        else if ( ((LA15_93>='A' && LA15_93<='Z')||LA15_93=='_'||(LA15_93>='a' && LA15_93<='z')) ) {s = 88;}

                        else if ( ((LA15_93>='0' && LA15_93<='9')) ) {s = 89;}

                        else if ( (LA15_93=='*') ) {s = 90;}

                        else if ( (LA15_93=='%') ) {s = 91;}

                        else if ( (LA15_93=='!'||LA15_93=='\''||(LA15_93>='-' && LA15_93<='.')||LA15_93=='~') ) {s = 93;}

                        else if ( ((LA15_93>='\u0000' && LA15_93<=' ')||LA15_93=='\"'||(LA15_93>='(' && LA15_93<=')')||LA15_93=='<'||LA15_93=='>'||(LA15_93>='[' && LA15_93<='^')||LA15_93=='`'||(LA15_93>='{' && LA15_93<='}')||(LA15_93>='\u007F' && LA15_93<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA15_140 = input.LA(1);

                        s = -1;
                        if ( ((LA15_140>='#' && LA15_140<='$')||LA15_140=='&'||(LA15_140>='+' && LA15_140<=',')||LA15_140=='/'||(LA15_140>=':' && LA15_140<=';')||LA15_140=='='||(LA15_140>='?' && LA15_140<='@')) ) {s = 87;}

                        else if ( ((LA15_140>='A' && LA15_140<='Z')||LA15_140=='_'||(LA15_140>='a' && LA15_140<='z')) ) {s = 88;}

                        else if ( ((LA15_140>='0' && LA15_140<='9')) ) {s = 89;}

                        else if ( (LA15_140=='*') ) {s = 90;}

                        else if ( (LA15_140=='%') ) {s = 91;}

                        else if ( (LA15_140=='!'||LA15_140=='\''||(LA15_140>='-' && LA15_140<='.')||LA15_140=='~') ) {s = 93;}

                        else if ( ((LA15_140>='\u0000' && LA15_140<=' ')||LA15_140=='\"'||(LA15_140>='(' && LA15_140<=')')||LA15_140=='<'||LA15_140=='>'||(LA15_140>='[' && LA15_140<='^')||LA15_140=='`'||(LA15_140>='{' && LA15_140<='}')||(LA15_140>='\u007F' && LA15_140<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA15_90 = input.LA(1);

                        s = -1;
                        if ( (LA15_90=='/') ) {s = 121;}

                        else if ( ((LA15_90>='#' && LA15_90<='$')||LA15_90=='&'||(LA15_90>='+' && LA15_90<=',')||(LA15_90>=':' && LA15_90<=';')||LA15_90=='='||(LA15_90>='?' && LA15_90<='@')) ) {s = 87;}

                        else if ( ((LA15_90>='A' && LA15_90<='Z')||LA15_90=='_'||(LA15_90>='a' && LA15_90<='z')) ) {s = 88;}

                        else if ( ((LA15_90>='0' && LA15_90<='9')) ) {s = 89;}

                        else if ( (LA15_90=='*') ) {s = 90;}

                        else if ( (LA15_90=='%') ) {s = 91;}

                        else if ( (LA15_90=='!'||LA15_90=='\''||(LA15_90>='-' && LA15_90<='.')||LA15_90=='~') ) {s = 93;}

                        else if ( ((LA15_90>='\u0000' && LA15_90<=' ')||LA15_90=='\"'||(LA15_90>='(' && LA15_90<=')')||LA15_90=='<'||LA15_90=='>'||(LA15_90>='[' && LA15_90<='^')||LA15_90=='`'||(LA15_90>='{' && LA15_90<='}')||(LA15_90>='\u007F' && LA15_90<='\uFFFF')) ) {s = 92;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA15_122 = input.LA(1);

                        s = -1;
                        if ( ((LA15_122>='0' && LA15_122<='9')||(LA15_122>='A' && LA15_122<='F')||(LA15_122>='a' && LA15_122<='f')) ) {s = 140;}

                        else if ( ((LA15_122>='\u0000' && LA15_122<='/')||(LA15_122>=':' && LA15_122<='@')||(LA15_122>='G' && LA15_122<='`')||(LA15_122>='g' && LA15_122<='\uFFFF')) ) {s = 92;}

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA15_64 = input.LA(1);

                         
                        int index15_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_64>='#' && LA15_64<='$')||LA15_64=='&'||(LA15_64>='+' && LA15_64<=',')||LA15_64=='/'||(LA15_64>=':' && LA15_64<=';')||LA15_64=='='||(LA15_64>='?' && LA15_64<='@')) ) {s = 95;}

                        else if ( ((LA15_64>='A' && LA15_64<='Z')||LA15_64=='_'||(LA15_64>='a' && LA15_64<='z')) ) {s = 96;}

                        else if ( ((LA15_64>='0' && LA15_64<='9')) ) {s = 97;}

                        else if ( (LA15_64=='!'||LA15_64=='\''||LA15_64=='*'||(LA15_64>='-' && LA15_64<='.')||LA15_64=='~') ) {s = 98;}

                        else if ( (LA15_64=='%') ) {s = 99;}

                        else if ( ((LA15_64>='\u0000' && LA15_64<='\t')||(LA15_64>='\u000B' && LA15_64<=' ')||LA15_64=='\"'||(LA15_64>='(' && LA15_64<=')')||LA15_64=='<'||LA15_64=='>'||(LA15_64>='[' && LA15_64<='^')||LA15_64=='`'||(LA15_64>='{' && LA15_64<='}')||(LA15_64>='\u007F' && LA15_64<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_64);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA15_123 = input.LA(1);

                         
                        int index15_123 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_123>='#' && LA15_123<='$')||LA15_123=='&'||(LA15_123>='+' && LA15_123<=',')||LA15_123=='/'||(LA15_123>=':' && LA15_123<=';')||LA15_123=='='||(LA15_123>='?' && LA15_123<='@')) ) {s = 95;}

                        else if ( ((LA15_123>='A' && LA15_123<='Z')||LA15_123=='_'||(LA15_123>='a' && LA15_123<='z')) ) {s = 123;}

                        else if ( ((LA15_123>='0' && LA15_123<='9')) ) {s = 124;}

                        else if ( (LA15_123=='!'||LA15_123=='\''||LA15_123=='*'||(LA15_123>='-' && LA15_123<='.')||LA15_123=='~') ) {s = 98;}

                        else if ( (LA15_123=='%') ) {s = 99;}

                        else if ( ((LA15_123>='\u0000' && LA15_123<='\t')||(LA15_123>='\u000B' && LA15_123<=' ')||LA15_123=='\"'||(LA15_123>='(' && LA15_123<=')')||LA15_123=='<'||LA15_123=='>'||(LA15_123>='[' && LA15_123<='^')||LA15_123=='`'||(LA15_123>='{' && LA15_123<='}')||(LA15_123>='\u007F' && LA15_123<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 100;}

                        else s = 94;

                         
                        input.seek(index15_123);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA15_138 = input.LA(1);

                        s = -1;
                        if ( ((LA15_138>='#' && LA15_138<='$')||LA15_138=='&'||(LA15_138>='+' && LA15_138<=',')||LA15_138=='/'||(LA15_138>=':' && LA15_138<=';')||LA15_138=='='||(LA15_138>='?' && LA15_138<='@')) ) {s = 113;}

                        else if ( ((LA15_138>='A' && LA15_138<='Z')||LA15_138=='_'||(LA15_138>='a' && LA15_138<='z')) ) {s = 137;}

                        else if ( ((LA15_138>='0' && LA15_138<='9')) ) {s = 138;}

                        else if ( (LA15_138=='!'||LA15_138=='\''||LA15_138=='*'||(LA15_138>='-' && LA15_138<='.')||LA15_138=='~') ) {s = 116;}

                        else if ( (LA15_138=='%') ) {s = 117;}

                        else if ( ((LA15_138>='\u0000' && LA15_138<=' ')||LA15_138=='\"'||(LA15_138>='(' && LA15_138<=')')||LA15_138=='<'||LA15_138=='>'||(LA15_138>='[' && LA15_138<='^')||LA15_138=='`'||(LA15_138>='{' && LA15_138<='}')||(LA15_138>='\u007F' && LA15_138<='\uFFFF')) ) {s = 118;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA15_94 = input.LA(1);

                         
                        int index15_94 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 100;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index15_94);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA15_91 = input.LA(1);

                        s = -1;
                        if ( ((LA15_91>='0' && LA15_91<='9')||(LA15_91>='A' && LA15_91<='F')||(LA15_91>='a' && LA15_91<='f')) ) {s = 122;}

                        else if ( ((LA15_91>='\u0000' && LA15_91<='/')||(LA15_91>=':' && LA15_91<='@')||(LA15_91>='G' && LA15_91<='`')||(LA15_91>='g' && LA15_91<='\uFFFF')) ) {s = 92;}

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA15_137 = input.LA(1);

                        s = -1;
                        if ( ((LA15_137>='#' && LA15_137<='$')||LA15_137=='&'||(LA15_137>='+' && LA15_137<=',')||LA15_137=='/'||(LA15_137>=':' && LA15_137<=';')||LA15_137=='='||(LA15_137>='?' && LA15_137<='@')) ) {s = 113;}

                        else if ( ((LA15_137>='A' && LA15_137<='Z')||LA15_137=='_'||(LA15_137>='a' && LA15_137<='z')) ) {s = 137;}

                        else if ( ((LA15_137>='0' && LA15_137<='9')) ) {s = 138;}

                        else if ( (LA15_137=='!'||LA15_137=='\''||LA15_137=='*'||(LA15_137>='-' && LA15_137<='.')||LA15_137=='~') ) {s = 116;}

                        else if ( (LA15_137=='%') ) {s = 117;}

                        else if ( ((LA15_137>='\u0000' && LA15_137<=' ')||LA15_137=='\"'||(LA15_137>='(' && LA15_137<=')')||LA15_137=='<'||LA15_137=='>'||(LA15_137>='[' && LA15_137<='^')||LA15_137=='`'||(LA15_137>='{' && LA15_137<='}')||(LA15_137>='\u007F' && LA15_137<='\uFFFF')) ) {s = 118;}

                        else s = 33;

                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA15_139 = input.LA(1);

                        s = -1;
                        if ( ((LA15_139>='\u0000' && LA15_139<='/')||(LA15_139>=':' && LA15_139<='@')||(LA15_139>='G' && LA15_139<='`')||(LA15_139>='g' && LA15_139<='\uFFFF')) ) {s = 118;}

                        else if ( ((LA15_139>='0' && LA15_139<='9')||(LA15_139>='A' && LA15_139<='F')||(LA15_139>='a' && LA15_139<='f')) ) {s = 161;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 15, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}