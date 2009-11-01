// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-11-02 11:40:07

package net.java.treaty.script.generated;

import java.util.List;
import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TreatyLexer extends Lexer {
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
    public static final int T__57=57;
    public static final int XOr=13;
    public static final int T__58=58;
    public static final int UriCharacter=45;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int ResourceNameAttribute=9;
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

    static private Token NEWLINE_TOKEN = new CommonToken(Newline, "\n");

    // Workaround until getText(), and setText() work as expected within lexer fragments
    private String lastAnnotationValue = null;

    private int implicitLineJoiningLevel = 0;

    private List<Token> tokens = new LinkedList<Token>();

    public void emit(Token token) {
        state.token = token;
        tokens.add(token);
    }

    public Token nextToken() {
        super.nextToken();
        
        // eliminate some edge cases by always ending on a new line
        if (tokens.size() == 0) {
            tokens.add(NEWLINE_TOKEN);
            tokens.add(Token.EOF_TOKEN);
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:39:7: ( 'consumer-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:39:9: 'consumer-resource'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:7: ( 'supplier-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:9: 'supplier-resource'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:41:7: ( 'external-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:41:9: 'external-resource'
            {
            match("external-resource"); 


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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:42:7: ( 'constraint' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:42:9: 'constraint'
            {
            match("constraint"); 


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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:43:7: ( 'mustexist' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:43:9: 'mustexist'
            {
            match("mustexist"); 


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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:44:7: ( 'onfailure' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:44:9: 'onfailure'
            {
            match("onfailure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:45:7: ( 'onsuccess' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:45:9: 'onsuccess'
            {
            match("onsuccess"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "And"
    public final void mAnd() throws RecognitionException {
        try {
            int _type = And;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:214:13: ( 'and' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:214:15: 'and'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:13: ( 'not' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:215:15: 'not'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:216:13: ( 'or' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:216:15: 'or'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:217:13: ( 'xor' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:217:15: 'xor'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:219:13: ( '(' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:219:15: '('
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:220:13: ( ')' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:220:15: ')'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:13: ( '&' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:222:15: '&'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:223:13: ( '\\'' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:223:15: '\\''
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:224:13: ( '*' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:224:15: '*'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:225:13: ( '@' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:225:15: '@'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:226:13: ( ':' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:226:15: ':'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:227:13: ( ',' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:227:15: ','
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:228:13: ( '$' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:228:15: '$'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:229:13: ( '.' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:229:15: '.'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:13: ( '=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:230:15: '='
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:231:13: ( '!' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:231:15: '!'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:232:13: ( '#' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:232:15: '#'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:233:13: ( '-' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:233:15: '-'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:13: ( '%' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:234:15: '%'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:235:13: ( '+' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:235:15: '+'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:236:13: ( '?' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:236:15: '?'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:237:13: ( ';' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:237:15: ';'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:238:13: ( '/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:238:15: '/'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:239:13: ( '~' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:239:15: '~'
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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:242:5: ( 'on' Whitespace String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:242:9: 'on' Whitespace String
            {
            match("on"); 

            mWhitespace(); 
            int String1Start482 = getCharIndex();
            mString(); 
            String1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String1Start482, getCharIndex()-1);

                        emit(new CommonToken(Trigger, (String1!=null?String1.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Trigger"

    // $ANTLR start "ResourceNameAttribute"
    public final void mResourceNameAttribute() throws RecognitionException {
        try {
            int _type = ResourceNameAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String2=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:249:5: ( 'name' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:249:9: 'name' Equals String
            {
            match("name");

            mEquals(); 
            int String2Start516 = getCharIndex();
            mString(); 
            String2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String2Start516, getCharIndex()-1);

                        emit(new CommonToken(ResourceNameAttribute, (String2!=null?String2.getText():null)));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ResourceNameAttribute"

    // $ANTLR start "ResourceTypeAttribute"
    public final void mResourceTypeAttribute() throws RecognitionException {
        try {
            int _type = ResourceTypeAttribute;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String3=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:256:5: ( 'type' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:256:9: 'type' Equals String
            {
            match("type"); 

            mEquals(); 
            int String3Start550 = getCharIndex();
            mString(); 
            String3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String3Start550, getCharIndex()-1);

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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:263:5: ( 'ref' Equals String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:263:9: 'ref' Equals String
            {
            match("ref"); 

            mEquals(); 
            int String4Start584 = getCharIndex();
            mString(); 
            String4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String4Start584, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:5: ( (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='\b')||LA1_0=='\u000B'||(LA1_0>='\u000E' && LA1_0<='\u001F')||(LA1_0>='!' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:271:9: ~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:275:5: ( At key= AnnotationKey Equals value= AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:275:9: At key= AnnotationKey Equals value= AnnotationValue
            {
            mAt(); 
            int keyStart652 = getCharIndex();
            mAnnotationKey(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart652, getCharIndex()-1);
            mEquals(); 
            int valueStart658 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart658, getCharIndex()-1);

                        emit(new CommonToken(AnnotationKey, (key!=null?key.getText():null)));
                        emit(new CommonToken(AnnotationValue, lastAnnotationValue));
                        emit(NEWLINE_TOKEN);
                    

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:285:5: ( Identifier ( NamespaceDelimiter Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:285:9: Identifier ( NamespaceDelimiter Identifier )*
            {
            mIdentifier(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:285:20: ( NamespaceDelimiter Identifier )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='.'||LA2_0==':') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:285:21: NamespaceDelimiter Identifier
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:290:5: ( Colon | Dot )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:5: ( ( options {greedy=false; } : . )* Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:9: ( options {greedy=false; } : . )* Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:9: ( options {greedy=false; } : . )*
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
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:296:39: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            int Newline5Start770 = getCharIndex();
            mNewline(); 
            Newline5 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Newline5Start770, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:307:5: ( IDLetter ( IDLetter | IDDigit )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:307:9: IDLetter ( IDLetter | IDDigit )*
            {
            mIDLetter(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:307:18: ( IDLetter | IDDigit )*
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:312:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:5: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:319:9: '0' .. '9'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:323:5: ( ( '\\r' )? '\\n' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:323:9: ( '\\r' )? '\\n'
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:323:9: ( '\\r' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\r') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:323:9: '\\r'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:333:5: ( ( ' ' | '\\t' )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:333:9: ( ' ' | '\\t' )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:333:9: ( ' ' | '\\t' )+
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:338:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:338:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:338:14: ( options {greedy=false; } : . )*
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
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:338:44: .
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:5: ({...}? => ( Whitespace )? '//' (~ ( '\\n' ) )* | {...}? => Whitespace '//' (~ ( '\\n' ) )* )
            int alt11=2;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:9: {...}? => ( Whitespace )? '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() == 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() == 0 ");
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:46: ( Whitespace )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\t'||LA8_0==' ') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:46: Whitespace
                            {
                            mWhitespace(); 

                            }
                            break;

                    }

                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:63: (~ ( '\\n' ) )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='\u0000' && LA9_0<='\t')||(LA9_0>='\u000B' && LA9_0<='\uFFFF')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:347:63: ~ ( '\\n' )
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:348:9: {...}? => Whitespace '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() >= 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() >= 0 ");
                    }
                    mWhitespace(); 
                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:348:62: (~ ( '\\n' ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\uFFFF')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:348:62: ~ ( '\\n' )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:355:5: ( ( UriCharacter )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:355:9: ( UriCharacter )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:355:9: ( UriCharacter )+
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
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:355:9: UriCharacter
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:360:5: ( UriReserved | UriUnescaped | UriEscaped )
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:360:9: UriReserved
                    {
                    mUriReserved(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:361:9: UriUnescaped
                    {
                    mUriUnescaped(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:362:9: UriEscaped
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:367:5: ( Semi | Slash | Question | Colon | At | Amper | Equals | Plus | Dollar | Comma | Hash )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:372:5: ( UriAlpha | DecimalDigit | UriMark )
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:372:9: UriAlpha
                    {
                    mUriAlpha(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:373:9: DecimalDigit
                    {
                    mDecimalDigit(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:374:9: UriMark
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:379:5: ( Identifier )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:379:9: Identifier
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:384:5: ( Minus | Dot | Exclamation | Tilde | Asterisk | Apostrophe )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:389:5: ( Percent HexDigit HexDigit )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:389:8: Percent HexDigit HexDigit
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:394:5: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:394:9: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:5: ( ( '0' .. '9' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:9: ( '0' .. '9' )
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:9: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:10: '0' .. '9'
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
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:8: ( T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equals | Exclamation | Hash | Minus | Percent | Plus | Question | Semi | Slash | Tilde | Trigger | ResourceNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri )
        int alt15=42;
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
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:46: T__59
                {
                mT__59(); 

                }
                break;
            case 8 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:52: And
                {
                mAnd(); 

                }
                break;
            case 9 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:56: Not
                {
                mNot(); 

                }
                break;
            case 10 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:60: Or
                {
                mOr(); 

                }
                break;
            case 11 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:63: XOr
                {
                mXOr(); 

                }
                break;
            case 12 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:67: LParen
                {
                mLParen(); 

                }
                break;
            case 13 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:74: RParen
                {
                mRParen(); 

                }
                break;
            case 14 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:81: Amper
                {
                mAmper(); 

                }
                break;
            case 15 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:87: Apostrophe
                {
                mApostrophe(); 

                }
                break;
            case 16 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:98: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 17 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:107: At
                {
                mAt(); 

                }
                break;
            case 18 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:110: Colon
                {
                mColon(); 

                }
                break;
            case 19 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:116: Comma
                {
                mComma(); 

                }
                break;
            case 20 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:122: Dollar
                {
                mDollar(); 

                }
                break;
            case 21 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:129: Dot
                {
                mDot(); 

                }
                break;
            case 22 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:133: Equals
                {
                mEquals(); 

                }
                break;
            case 23 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:140: Exclamation
                {
                mExclamation(); 

                }
                break;
            case 24 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:152: Hash
                {
                mHash(); 

                }
                break;
            case 25 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:157: Minus
                {
                mMinus(); 

                }
                break;
            case 26 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:163: Percent
                {
                mPercent(); 

                }
                break;
            case 27 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:171: Plus
                {
                mPlus(); 

                }
                break;
            case 28 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:176: Question
                {
                mQuestion(); 

                }
                break;
            case 29 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:185: Semi
                {
                mSemi(); 

                }
                break;
            case 30 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:190: Slash
                {
                mSlash(); 

                }
                break;
            case 31 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:196: Tilde
                {
                mTilde(); 

                }
                break;
            case 32 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:202: Trigger
                {
                mTrigger(); 

                }
                break;
            case 33 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:210: ResourceNameAttribute
                {
                mResourceNameAttribute(); 

                }
                break;
            case 34 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:232: ResourceTypeAttribute
                {
                mResourceTypeAttribute(); 

                }
                break;
            case 35 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:254: ResourceReferenceAttribute
                {
                mResourceReferenceAttribute(); 

                }
                break;
            case 36 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:281: Annotation
                {
                mAnnotation(); 

                }
                break;
            case 37 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:292: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 38 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:303: Newline
                {
                mNewline(); 

                }
                break;
            case 39 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:311: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 40 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:322: BlockComment
                {
                mBlockComment(); 

                }
                break;
            case 41 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:335: LineComment
                {
                mLineComment(); 

                }
                break;
            case 42 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:347: Uri
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
        "\1\0\1\3\1\uffff\1\1\1\2\1\uffff}>";
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
                        int LA11_0 = input.LA(1);

                         
                        int index11_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_0=='\t'||LA11_0==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                        else if ( (LA11_0=='/') && (( getCharPositionInLine() == 0 ))) {s = 2;}

                         
                        input.seek(index11_0);
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
                        int LA11_4 = input.LA(1);

                         
                        int index11_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 2;}

                        else if ( (( getCharPositionInLine() >= 0 )) ) {s = 5;}

                         
                        input.seek(index11_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA11_1 = input.LA(1);

                         
                        int index11_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA11_1=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 3;}

                        else if ( (LA11_1=='\t'||LA11_1==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                         
                        input.seek(index11_1);
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
        "\1\uffff\10\44\2\uffff\1\60\1\61\1\62\1\63\1\65\1\66\1\67\1\70\1"+
        "\71\1\72\1\73\1\74\1\75\1\76\1\77\1\100\1\103\1\104\3\44\1\uffff"+
        "\1\107\1\uffff\1\44\1\uffff\6\44\1\120\4\44\4\uffff\1\42\14\uffff"+
        "\1\42\1\142\2\uffff\2\44\2\uffff\6\44\2\uffff\1\160\1\161\1\44\1"+
        "\163\11\42\2\uffff\1\42\1\142\1\uffff\3\142\1\147\1\uffff\10\44"+
        "\2\uffff\1\44\1\uffff\1\42\1\uffff\4\42\1\uffff\2\42\1\137\1\uffff"+
        "\2\142\1\147\1\44\1\u0094\7\44\1\u00a1\4\42\1\uffff\1\42\1\142\1"+
        "\u00a8\1\uffff\5\u0094\7\44\1\uffff\5\u00a1\1\42\1\uffff\5\u00a8"+
        "\3\u0094\7\44\3\u00a1\3\u00a8\1\u0094\7\44\1\u00a1\1\u00a8\1\42"+
        "\1\44\2\42\1\u00d3\1\u00d4\1\u00d5\1\42\1\u00d7\2\42\3\uffff\1\42"+
        "\1\uffff\21\42\1\u00ec\1\u00ed\1\u00ee\3\uffff";
    static final String DFA15_eofS =
        "\u00ef\uffff";
    static final String DFA15_minS =
        "\1\11\10\41\2\uffff\14\41\1\60\10\41\1\uffff\1\11\1\uffff\1\41\1"+
        "\uffff\5\41\1\11\5\41\4\uffff\1\56\14\uffff\2\0\2\uffff\2\41\2\uffff"+
        "\6\41\2\uffff\4\41\1\56\1\101\1\0\1\101\1\56\5\0\1\uffff\6\0\1\60"+
        "\1\uffff\10\41\2\uffff\1\41\1\uffff\1\56\1\uffff\7\0\1\41\3\0\1"+
        "\60\12\41\2\56\5\0\1\41\1\uffff\4\41\1\60\7\41\1\uffff\4\41\1\60"+
        "\1\0\1\uffff\4\41\1\60\2\41\1\60\11\41\1\60\2\41\1\60\12\41\1\162"+
        "\1\41\2\162\3\41\1\145\1\41\2\145\3\uffff\1\163\1\uffff\2\163\3"+
        "\157\3\165\3\162\3\143\3\145\3\41\3\uffff";
    static final String DFA15_maxS =
        "\11\176\2\uffff\14\176\1\146\10\176\1\uffff\1\57\1\uffff\1\176\1"+
        "\uffff\13\176\4\uffff\1\172\14\uffff\2\uffff\2\uffff\2\176\2\uffff"+
        "\6\176\2\uffff\4\176\2\172\1\uffff\2\172\5\uffff\1\uffff\2\uffff"+
        "\1\0\3\uffff\1\146\1\uffff\10\176\2\uffff\1\176\1\uffff\1\172\1"+
        "\uffff\7\uffff\1\176\3\uffff\1\146\12\176\2\172\5\uffff\1\176\1"+
        "\uffff\4\176\1\146\7\176\1\uffff\4\176\1\146\1\uffff\1\uffff\4\176"+
        "\1\146\2\176\1\146\11\176\1\146\2\176\1\146\12\176\1\162\1\176\2"+
        "\162\3\176\1\145\1\176\2\145\3\uffff\1\163\1\uffff\2\163\3\157\3"+
        "\165\3\162\3\143\3\145\3\176\3\uffff";
    static final String DFA15_acceptS =
        "\11\uffff\1\14\1\15\25\uffff\1\46\1\uffff\1\52\1\uffff\1\45\13\uffff"+
        "\1\16\1\17\1\20\1\21\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1\30"+
        "\1\31\1\32\1\33\1\34\1\35\2\uffff\1\36\1\37\2\uffff\1\47\1\51\6"+
        "\uffff\1\40\1\12\16\uffff\1\50\7\uffff\1\51\10\uffff\1\10\1\11\1"+
        "\uffff\1\13\1\uffff\1\44\36\uffff\1\43\14\uffff\1\41\6\uffff\1\42"+
        "\52\uffff\1\5\1\6\1\7\1\uffff\1\4\24\uffff\1\1\1\2\1\3";
    static final String DFA15_specialS =
        "\41\uffff\1\12\37\uffff\1\26\1\24\24\uffff\1\5\2\uffff\1\14\1\31"+
        "\1\30\1\21\1\25\1\uffff\1\35\1\23\1\36\1\13\1\1\1\0\1\37\17\uffff"+
        "\1\32\1\20\1\11\1\6\1\40\1\17\1\10\1\uffff\1\4\1\15\1\34\1\3\14"+
        "\uffff\1\16\1\33\1\22\1\27\1\7\24\uffff\1\2\107\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\41\1\40\2\uffff\1\40\22\uffff\1\41\1\24\1\uffff\1\25\1\21"+
            "\1\27\1\13\1\14\1\11\1\12\1\15\1\30\1\20\1\26\1\22\1\33\12\42"+
            "\1\17\1\32\1\uffff\1\23\1\uffff\1\31\1\16\32\37\4\uffff\1\37"+
            "\1\uffff\1\6\1\37\1\1\1\37\1\3\7\37\1\4\1\7\1\5\2\37\1\36\1"+
            "\2\1\35\3\37\1\10\2\37\3\uffff\1\34",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\16\45\1\43\13\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\24\45\1\47\5\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\27\45\1\50\2\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\24\45\1\51\5\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\15\45\1\52\3\45\1\53\10\45"+
            "\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\15\45\1\54\14\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\1\56\15\45\1\55\13\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\16\45\1\57\13\45\3\uffff\1"+
            "\42",
            "",
            "",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\2\42"+
            "\32\64\4\uffff\1\64\1\uffff\32\64\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\12\42\7\uffff\6\42\32\uffff\6\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\1\101\4\42\1\102\14\42\1\uffff\1"+
            "\42\1\uffff\34\42\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\30\45\1\105\1\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\106\25\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "",
            "\1\41\26\uffff\1\41\16\uffff\1\110",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\15\45\1\111\14\45\3\uffff\1"+
            "\42",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\17\45\1\112\12\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\113\6\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\22\45\1\114\7\45\3\uffff\1"+
            "\42",
            "\1\117\26\uffff\1\117\1\42\1\uffff\5\42\2\uffff\6\42\12\46"+
            "\2\42\1\uffff\1\42\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\5"+
            "\45\1\115\14\45\1\116\7\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\3\45\1\121\26\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\122\6\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\14\45\1\123\15\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\124\10\45\3\uffff\1"+
            "\42",
            "",
            "",
            "",
            "",
            "\1\130\1\uffff\12\131\1\126\2\uffff\1\127\3\uffff\32\125\4"+
            "\uffff\1\125\1\uffff\32\125",
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
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\144\2\141\1\147\1\141\1\147"+
            "\2\141\32\143\4\147\1\143\1\147\32\143\3\147\1\145\uff81\147",
            "",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\17\45\1\150\12\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\5\45\1\151\24\45\3\uffff\1"+
            "\42",
            "",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\22\45\1\152\7\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\17\45\1\153\12\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\154\25\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\155\6\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\1\156\31\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\24\45\1\157\5\45\3\uffff\1"+
            "\42",
            "",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\162\25\45\3\uffff\1"+
            "\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\130\1\uffff\12\131\1\126\2\uffff\1\127\3\uffff\32\125\4"+
            "\uffff\1\125\1\uffff\32\125",
            "\32\164\4\uffff\1\164\1\uffff\32\164",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\170\2\166\1\165\1\166\1\165\2\166\32\167\4\165"+
            "\1\167\1\165\32\167\3\165\1\171\uff81\165",
            "\32\164\4\uffff\1\164\1\uffff\32\164",
            "\1\130\1\uffff\12\131\1\126\2\uffff\1\127\3\uffff\32\125\4"+
            "\uffff\1\125\1\uffff\32\125",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\174\2\132\1\137\1\132\1\137\2\132\32\173\4\137"+
            "\1\173\1\137\32\173\3\137\1\140\uff81\137",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\175\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\60\137\12\176\7\137\6\176\32\137\6\176\uff99\137",
            "",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\144\2\141\1\147\1\141\1\147"+
            "\2\141\32\143\4\147\1\143\1\147\32\143\3\147\1\145\uff81\147",
            "\1\uffff",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\u0080\2\141\1\147\1\141\1"+
            "\147\2\141\32\177\4\147\1\177\1\147\32\177\3\147\1\145\uff81"+
            "\147",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\144\2\141\1\147\1\141\1\147"+
            "\2\141\32\143\4\147\1\143\1\147\32\143\3\147\1\145\uff81\147",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\144\2\141\1\147\1\141\1\147"+
            "\2\141\32\143\4\147\1\143\1\147\32\143\3\147\1\145\uff81\147",
            "\12\u0081\7\uffff\6\u0081\32\uffff\6\u0081",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u0082\25\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\u0083"+
            "\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\u0085\1\u0084\5\45"+
            "\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\13\45\1\u0086\16\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\u0087\10\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u0088\25\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\10\45\1\u0089\21\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\2\45\1\u008a\27\45\3\uffff"+
            "\1\42",
            "",
            "",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\u008b"+
            "\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "",
            "\1\130\1\uffff\12\u008d\1\126\2\uffff\1\127\3\uffff\32\u008c"+
            "\4\uffff\1\u008c\1\uffff\32\u008c",
            "",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\170\2\166\1\165\1\166\1\165\2\166\32\167\4\165"+
            "\1\167\1\165\32\167\3\165\1\171\uff81\165",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\u008f\2\166\1\165\1\166\1\165\2\166\32\u008e"+
            "\4\165\1\u008e\1\165\32\u008e\3\165\1\171\uff81\165",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\170\2\166\1\165\1\166\1\165\2\166\32\167\4\165"+
            "\1\167\1\165\32\167\3\165\1\171\uff81\165",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\170\2\166\1\165\1\166\1\165\2\166\32\167\4\165"+
            "\1\167\1\165\32\167\3\165\1\171\uff81\165",
            "\60\165\12\u0090\7\165\6\u0090\32\165\6\u0090\uff99\165",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\174\2\132\1\137\1\132\1\137\2\132\32\173\4\137"+
            "\1\173\1\137\32\173\3\137\1\140\uff81\137",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\174\2\132\1\137\1\132\1\137\2\132\32\173\4\137"+
            "\1\173\1\137\32\173\3\137\1\140\uff81\137",
            "\1\140\1\uffff\2\132\1\136\1\132\1\140\2\uffff\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\uffff\1\132\1\uffff\2\132\32\133"+
            "\4\uffff\1\133\1\uffff\32\133\3\uffff\1\140",
            "\60\137\12\u0091\7\137\6\u0091\32\137\6\u0091\uff99\137",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\u0080\2\141\1\147\1\141\1"+
            "\147\2\141\32\177\4\147\1\177\1\147\32\177\3\147\1\145\uff81"+
            "\147",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\u0080\2\141\1\147\1\141\1"+
            "\147\2\141\32\177\4\147\1\177\1\147\32\177\3\147\1\145\uff81"+
            "\147",
            "\12\u0092\7\uffff\6\u0092\32\uffff\6\u0092",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\u0093"+
            "\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u0097\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u0096\4\uffff\1\u0096\1\uffff\32\u0096\3"+
            "\uffff\1\u0098",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\14\45\1\u009a\15\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\u009b\10\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\10\45\1\u009c\21\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\15\45\1\u009d\14\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\27\45\1\u009e\2\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\13\45\1\u009f\16\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\2\45\1\u00a0\27\45\3\uffff"+
            "\1\42",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00a4\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00a3\4\uffff\1\u00a3\1\uffff\32\u00a3\3"+
            "\uffff\1\u00a5",
            "\1\130\1\uffff\12\u008d\1\126\2\uffff\1\127\3\uffff\32\u008c"+
            "\4\uffff\1\u008c\1\uffff\32\u008c",
            "\1\130\1\uffff\12\u008d\1\126\2\uffff\1\127\3\uffff\32\u008c"+
            "\4\uffff\1\u008c\1\uffff\32\u008c",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\u008f\2\166\1\165\1\166\1\165\2\166\32\u008e"+
            "\4\165\1\u008e\1\165\32\u008e\3\165\1\171\uff81\165",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\u008f\2\166\1\165\1\166\1\165\2\166\32\u008e"+
            "\4\165\1\u008e\1\165\32\u008e\3\165\1\171\uff81\165",
            "\60\165\12\u00a7\7\165\6\u00a7\32\165\6\u00a7\uff99\165",
            "\41\137\1\140\1\137\2\132\1\136\1\132\1\140\2\137\1\135\2\132"+
            "\2\140\1\132\12\134\2\132\1\137\1\132\1\137\2\132\32\133\4\137"+
            "\1\133\1\137\32\133\3\137\1\140\uff81\137",
            "\12\147\1\uffff\26\147\1\145\1\147\2\141\1\146\1\141\1\145"+
            "\2\147\1\145\2\141\2\145\1\141\12\144\2\141\1\147\1\141\1\147"+
            "\2\141\32\143\4\147\1\143\1\147\32\143\3\147\1\145\uff81\147",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00ab\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00aa\4\uffff\1\u00aa\1\uffff\32\u00aa\3"+
            "\uffff\1\u00ac",
            "",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u0097\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u0096\4\uffff\1\u0096\1\uffff\32\u0096\3"+
            "\uffff\1\u0098",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u00af\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u00ae\4\uffff\1\u00ae\1\uffff\32\u00ae\3"+
            "\uffff\1\u0098",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u0097\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u0096\4\uffff\1\u0096\1\uffff\32\u0096\3"+
            "\uffff\1\u0098",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u0097\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u0096\4\uffff\1\u0096\1\uffff\32\u0096\3"+
            "\uffff\1\u0098",
            "\12\u00b0\7\uffff\6\u00b0\32\uffff\6\u00b0",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u00b1\25\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\1\u00b2\31\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u00b3\25\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\1\u00b4\31\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\10\45\1\u00b5\21\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\24\45\1\u00b6\5\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u00b7\25\45\3\uffff"+
            "\1\42",
            "",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00a4\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00a3\4\uffff\1\u00a3\1\uffff\32\u00a3\3"+
            "\uffff\1\u00a5",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00b9\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00b8\4\uffff\1\u00b8\1\uffff\32\u00b8\3"+
            "\uffff\1\u00a5",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00a4\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00a3\4\uffff\1\u00a3\1\uffff\32\u00a3\3"+
            "\uffff\1\u00a5",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00a4\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00a3\4\uffff\1\u00a3\1\uffff\32\u00a3\3"+
            "\uffff\1\u00a5",
            "\12\u00ba\7\uffff\6\u00ba\32\uffff\6\u00ba",
            "\41\165\1\171\1\165\2\166\1\172\1\166\1\171\2\165\1\171\2\166"+
            "\2\171\1\166\12\170\2\166\1\165\1\166\1\165\2\166\32\167\4\165"+
            "\1\167\1\165\32\167\3\165\1\171\uff81\165",
            "",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00ab\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00aa\4\uffff\1\u00aa\1\uffff\32\u00aa\3"+
            "\uffff\1\u00ac",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00bc\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00bb\4\uffff\1\u00bb\1\uffff\32\u00bb\3"+
            "\uffff\1\u00ac",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00ab\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00aa\4\uffff\1\u00aa\1\uffff\32\u00aa\3"+
            "\uffff\1\u00ac",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00ab\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00aa\4\uffff\1\u00aa\1\uffff\32\u00aa\3"+
            "\uffff\1\u00ac",
            "\12\u00bd\7\uffff\6\u00bd\32\uffff\6\u00bd",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u00af\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u00ae\4\uffff\1\u00ae\1\uffff\32\u00ae\3"+
            "\uffff\1\u0098",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u00af\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u00ae\4\uffff\1\u00ae\1\uffff\32\u00ae\3"+
            "\uffff\1\u0098",
            "\12\u00be\7\uffff\6\u00be\32\uffff\6\u00be",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\u00bf\10\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\10\45\1\u00c0\21\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\u00c1\10\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\13\45\1\u00c2\16\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\22\45\1\u00c3\7\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\21\45\1\u00c4\10\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\22\45\1\u00c5\7\45\3\uffff"+
            "\1\42",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00b9\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00b8\4\uffff\1\u00b8\1\uffff\32\u00b8\3"+
            "\uffff\1\u00a5",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00b9\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00b8\4\uffff\1\u00b8\1\uffff\32\u00b8\3"+
            "\uffff\1\u00a5",
            "\12\u00c6\7\uffff\6\u00c6\32\uffff\6\u00c6",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00bc\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00bb\4\uffff\1\u00bb\1\uffff\32\u00bb\3"+
            "\uffff\1\u00ac",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00bc\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00bb\4\uffff\1\u00bb\1\uffff\32\u00bb\3"+
            "\uffff\1\u00ac",
            "\12\u00c7\7\uffff\6\u00c7\32\uffff\6\u00c7",
            "\1\u0098\1\uffff\2\u0095\1\u0099\1\u0095\1\u0098\2\uffff\1"+
            "\u0098\2\u0095\2\u0098\1\u0095\12\u0097\2\u0095\1\uffff\1\u0095"+
            "\1\uffff\2\u0095\32\u0096\4\uffff\1\u0096\1\uffff\32\u0096\3"+
            "\uffff\1\u0098",
            "\1\42\1\uffff\5\42\2\uffff\3\42\1\u00c8\2\42\12\46\2\42\1\uffff"+
            "\1\42\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\15\45\1\u00c9\14\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\3\42\1\u00ca\2\42\12\46\2\42\1\uffff"+
            "\1\42\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\3\42\1\u00cb\2\42\12\46\2\42\1\uffff"+
            "\1\42\1\uffff\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\u00cc\6\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\4\45\1\u00cd\25\45\3\uffff"+
            "\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\22\45\1\u00ce\7\45\3\uffff"+
            "\1\42",
            "\1\u00a5\1\uffff\2\u00a2\1\u00a6\1\u00a2\1\u00a5\2\uffff\1"+
            "\u00a5\2\u00a2\2\u00a5\1\u00a2\12\u00a4\2\u00a2\1\uffff\1\u00a2"+
            "\1\uffff\2\u00a2\32\u00a3\4\uffff\1\u00a3\1\uffff\32\u00a3\3"+
            "\uffff\1\u00a5",
            "\1\u00ac\1\uffff\2\u00a9\1\u00ad\1\u00a9\1\u00ac\2\uffff\1"+
            "\u00ac\2\u00a9\2\u00ac\1\u00a9\12\u00ab\2\u00a9\1\uffff\1\u00a9"+
            "\1\uffff\2\u00a9\32\u00aa\4\uffff\1\u00aa\1\uffff\32\u00aa\3"+
            "\uffff\1\u00ac",
            "\1\u00cf",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\23\45\1\u00d0\6\45\3\uffff"+
            "\1\42",
            "\1\u00d1",
            "\1\u00d2",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\u00d6",
            "\1\42\1\uffff\5\42\2\uffff\6\42\12\46\2\42\1\uffff\1\42\1\uffff"+
            "\2\42\32\45\4\uffff\1\45\1\uffff\32\45\3\uffff\1\42",
            "\1\u00d8",
            "\1\u00d9",
            "",
            "",
            "",
            "\1\u00da",
            "",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\u00e7",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "\1\42\1\uffff\5\42\2\uffff\22\42\1\uffff\1\42\1\uffff\34\42"+
            "\4\uffff\1\42\1\uffff\32\42\3\uffff\1\42",
            "",
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
            return "1:1: Tokens : ( T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equals | Exclamation | Hash | Minus | Percent | Plus | Question | Semi | Slash | Tilde | Trigger | ResourceNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA15_101 = input.LA(1);

                         
                        int index15_101 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_101>='#' && LA15_101<='$')||LA15_101=='&'||(LA15_101>='+' && LA15_101<=',')||LA15_101=='/'||(LA15_101>=':' && LA15_101<=';')||LA15_101=='='||(LA15_101>='?' && LA15_101<='@')) ) {s = 97;}

                        else if ( ((LA15_101>='A' && LA15_101<='Z')||LA15_101=='_'||(LA15_101>='a' && LA15_101<='z')) ) {s = 99;}

                        else if ( ((LA15_101>='0' && LA15_101<='9')) ) {s = 100;}

                        else if ( (LA15_101=='!'||LA15_101=='\''||LA15_101=='*'||(LA15_101>='-' && LA15_101<='.')||LA15_101=='~') ) {s = 101;}

                        else if ( (LA15_101=='%') ) {s = 102;}

                        else if ( ((LA15_101>='\u0000' && LA15_101<='\t')||(LA15_101>='\u000B' && LA15_101<=' ')||LA15_101=='\"'||(LA15_101>='(' && LA15_101<=')')||LA15_101=='<'||LA15_101=='>'||(LA15_101>='[' && LA15_101<='^')||LA15_101=='`'||(LA15_101>='{' && LA15_101<='}')||(LA15_101>='\u007F' && LA15_101<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_101);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA15_100 = input.LA(1);

                         
                        int index15_100 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_100>='#' && LA15_100<='$')||LA15_100=='&'||(LA15_100>='+' && LA15_100<=',')||LA15_100=='/'||(LA15_100>=':' && LA15_100<=';')||LA15_100=='='||(LA15_100>='?' && LA15_100<='@')) ) {s = 97;}

                        else if ( ((LA15_100>='A' && LA15_100<='Z')||LA15_100=='_'||(LA15_100>='a' && LA15_100<='z')) ) {s = 99;}

                        else if ( ((LA15_100>='0' && LA15_100<='9')) ) {s = 100;}

                        else if ( (LA15_100=='!'||LA15_100=='\''||LA15_100=='*'||(LA15_100>='-' && LA15_100<='.')||LA15_100=='~') ) {s = 101;}

                        else if ( (LA15_100=='%') ) {s = 102;}

                        else if ( ((LA15_100>='\u0000' && LA15_100<='\t')||(LA15_100>='\u000B' && LA15_100<=' ')||LA15_100=='\"'||(LA15_100>='(' && LA15_100<=')')||LA15_100=='<'||LA15_100=='>'||(LA15_100>='[' && LA15_100<='^')||LA15_100=='`'||(LA15_100>='{' && LA15_100<='}')||(LA15_100>='\u007F' && LA15_100<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_100);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA15_167 = input.LA(1);

                        s = -1;
                        if ( ((LA15_167>='#' && LA15_167<='$')||LA15_167=='&'||(LA15_167>='+' && LA15_167<=',')||LA15_167=='/'||(LA15_167>=':' && LA15_167<=';')||LA15_167=='='||(LA15_167>='?' && LA15_167<='@')) ) {s = 118;}

                        else if ( ((LA15_167>='A' && LA15_167<='Z')||LA15_167=='_'||(LA15_167>='a' && LA15_167<='z')) ) {s = 119;}

                        else if ( ((LA15_167>='0' && LA15_167<='9')) ) {s = 120;}

                        else if ( (LA15_167=='!'||LA15_167=='\''||LA15_167=='*'||(LA15_167>='-' && LA15_167<='.')||LA15_167=='~') ) {s = 121;}

                        else if ( (LA15_167=='%') ) {s = 122;}

                        else if ( ((LA15_167>='\u0000' && LA15_167<=' ')||LA15_167=='\"'||(LA15_167>='(' && LA15_167<=')')||LA15_167=='<'||LA15_167=='>'||(LA15_167>='[' && LA15_167<='^')||LA15_167=='`'||(LA15_167>='{' && LA15_167<='}')||(LA15_167>='\u007F' && LA15_167<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA15_129 = input.LA(1);

                         
                        int index15_129 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_129>='0' && LA15_129<='9')||(LA15_129>='A' && LA15_129<='F')||(LA15_129>='a' && LA15_129<='f')) ) {s = 146;}

                        else s = 103;

                         
                        input.seek(index15_129);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA15_126 = input.LA(1);

                        s = -1;
                        if ( ((LA15_126>='0' && LA15_126<='9')||(LA15_126>='A' && LA15_126<='F')||(LA15_126>='a' && LA15_126<='f')) ) {s = 145;}

                        else if ( ((LA15_126>='\u0000' && LA15_126<='/')||(LA15_126>=':' && LA15_126<='@')||(LA15_126>='G' && LA15_126<='`')||(LA15_126>='g' && LA15_126<='\uFFFF')) ) {s = 95;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA15_87 = input.LA(1);

                        s = -1;
                        if ( ((LA15_87>='\u0000' && LA15_87<=' ')||LA15_87=='\"'||(LA15_87>='(' && LA15_87<=')')||LA15_87=='<'||LA15_87=='>'||(LA15_87>='[' && LA15_87<='^')||LA15_87=='`'||(LA15_87>='{' && LA15_87<='}')||(LA15_87>='\u007F' && LA15_87<='\uFFFF')) ) {s = 117;}

                        else if ( ((LA15_87>='#' && LA15_87<='$')||LA15_87=='&'||(LA15_87>='+' && LA15_87<=',')||LA15_87=='/'||(LA15_87>=':' && LA15_87<=';')||LA15_87=='='||(LA15_87>='?' && LA15_87<='@')) ) {s = 118;}

                        else if ( ((LA15_87>='A' && LA15_87<='Z')||LA15_87=='_'||(LA15_87>='a' && LA15_87<='z')) ) {s = 119;}

                        else if ( ((LA15_87>='0' && LA15_87<='9')) ) {s = 120;}

                        else if ( (LA15_87=='!'||LA15_87=='\''||LA15_87=='*'||(LA15_87>='-' && LA15_87<='.')||LA15_87=='~') ) {s = 121;}

                        else if ( (LA15_87=='%') ) {s = 122;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA15_121 = input.LA(1);

                        s = -1;
                        if ( ((LA15_121>='#' && LA15_121<='$')||LA15_121=='&'||(LA15_121>='+' && LA15_121<=',')||LA15_121=='/'||(LA15_121>=':' && LA15_121<=';')||LA15_121=='='||(LA15_121>='?' && LA15_121<='@')) ) {s = 118;}

                        else if ( ((LA15_121>='A' && LA15_121<='Z')||LA15_121=='_'||(LA15_121>='a' && LA15_121<='z')) ) {s = 119;}

                        else if ( ((LA15_121>='0' && LA15_121<='9')) ) {s = 120;}

                        else if ( (LA15_121=='!'||LA15_121=='\''||LA15_121=='*'||(LA15_121>='-' && LA15_121<='.')||LA15_121=='~') ) {s = 121;}

                        else if ( (LA15_121=='%') ) {s = 122;}

                        else if ( ((LA15_121>='\u0000' && LA15_121<=' ')||LA15_121=='\"'||(LA15_121>='(' && LA15_121<=')')||LA15_121=='<'||LA15_121=='>'||(LA15_121>='[' && LA15_121<='^')||LA15_121=='`'||(LA15_121>='{' && LA15_121<='}')||(LA15_121>='\u007F' && LA15_121<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA15_146 = input.LA(1);

                         
                        int index15_146 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_146>='#' && LA15_146<='$')||LA15_146=='&'||(LA15_146>='+' && LA15_146<=',')||LA15_146=='/'||(LA15_146>=':' && LA15_146<=';')||LA15_146=='='||(LA15_146>='?' && LA15_146<='@')) ) {s = 97;}

                        else if ( ((LA15_146>='A' && LA15_146<='Z')||LA15_146=='_'||(LA15_146>='a' && LA15_146<='z')) ) {s = 99;}

                        else if ( ((LA15_146>='0' && LA15_146<='9')) ) {s = 100;}

                        else if ( (LA15_146=='!'||LA15_146=='\''||LA15_146=='*'||(LA15_146>='-' && LA15_146<='.')||LA15_146=='~') ) {s = 101;}

                        else if ( (LA15_146=='%') ) {s = 102;}

                        else if ( ((LA15_146>='\u0000' && LA15_146<='\t')||(LA15_146>='\u000B' && LA15_146<=' ')||LA15_146=='\"'||(LA15_146>='(' && LA15_146<=')')||LA15_146=='<'||LA15_146=='>'||(LA15_146>='[' && LA15_146<='^')||LA15_146=='`'||(LA15_146>='{' && LA15_146<='}')||(LA15_146>='\u007F' && LA15_146<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_146);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA15_124 = input.LA(1);

                        s = -1;
                        if ( ((LA15_124>='#' && LA15_124<='$')||LA15_124=='&'||(LA15_124>='+' && LA15_124<=',')||LA15_124=='/'||(LA15_124>=':' && LA15_124<=';')||LA15_124=='='||(LA15_124>='?' && LA15_124<='@')) ) {s = 90;}

                        else if ( ((LA15_124>='A' && LA15_124<='Z')||LA15_124=='_'||(LA15_124>='a' && LA15_124<='z')) ) {s = 123;}

                        else if ( ((LA15_124>='0' && LA15_124<='9')) ) {s = 124;}

                        else if ( (LA15_124=='*') ) {s = 93;}

                        else if ( (LA15_124=='%') ) {s = 94;}

                        else if ( (LA15_124=='!'||LA15_124=='\''||(LA15_124>='-' && LA15_124<='.')||LA15_124=='~') ) {s = 96;}

                        else if ( ((LA15_124>='\u0000' && LA15_124<=' ')||LA15_124=='\"'||(LA15_124>='(' && LA15_124<=')')||LA15_124=='<'||LA15_124=='>'||(LA15_124>='[' && LA15_124<='^')||LA15_124=='`'||(LA15_124>='{' && LA15_124<='}')||(LA15_124>='\u007F' && LA15_124<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA15_120 = input.LA(1);

                        s = -1;
                        if ( ((LA15_120>='#' && LA15_120<='$')||LA15_120=='&'||(LA15_120>='+' && LA15_120<=',')||LA15_120=='/'||(LA15_120>=':' && LA15_120<=';')||LA15_120=='='||(LA15_120>='?' && LA15_120<='@')) ) {s = 118;}

                        else if ( ((LA15_120>='A' && LA15_120<='Z')||LA15_120=='_'||(LA15_120>='a' && LA15_120<='z')) ) {s = 119;}

                        else if ( ((LA15_120>='0' && LA15_120<='9')) ) {s = 120;}

                        else if ( (LA15_120=='!'||LA15_120=='\''||LA15_120=='*'||(LA15_120>='-' && LA15_120<='.')||LA15_120=='~') ) {s = 121;}

                        else if ( (LA15_120=='%') ) {s = 122;}

                        else if ( ((LA15_120>='\u0000' && LA15_120<=' ')||LA15_120=='\"'||(LA15_120>='(' && LA15_120<=')')||LA15_120=='<'||LA15_120=='>'||(LA15_120>='[' && LA15_120<='^')||LA15_120=='`'||(LA15_120>='{' && LA15_120<='}')||(LA15_120>='\u007F' && LA15_120<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA15_33 = input.LA(1);

                         
                        int index15_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA15_33=='\t'||LA15_33==' ') ) {s = 33;}

                        else if ( (LA15_33=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 72;}

                        else s = 71;

                         
                        input.seek(index15_33);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA15_99 = input.LA(1);

                         
                        int index15_99 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_99>='A' && LA15_99<='Z')||LA15_99=='_'||(LA15_99>='a' && LA15_99<='z')) ) {s = 127;}

                        else if ( ((LA15_99>='#' && LA15_99<='$')||LA15_99=='&'||(LA15_99>='+' && LA15_99<=',')||LA15_99=='/'||(LA15_99>=':' && LA15_99<=';')||LA15_99=='='||(LA15_99>='?' && LA15_99<='@')) ) {s = 97;}

                        else if ( (LA15_99=='!'||LA15_99=='\''||LA15_99=='*'||(LA15_99>='-' && LA15_99<='.')||LA15_99=='~') ) {s = 101;}

                        else if ( ((LA15_99>='0' && LA15_99<='9')) ) {s = 128;}

                        else if ( (LA15_99=='%') ) {s = 102;}

                        else if ( ((LA15_99>='\u0000' && LA15_99<='\t')||(LA15_99>='\u000B' && LA15_99<=' ')||LA15_99=='\"'||(LA15_99>='(' && LA15_99<=')')||LA15_99=='<'||LA15_99=='>'||(LA15_99>='[' && LA15_99<='^')||LA15_99=='`'||(LA15_99>='{' && LA15_99<='}')||(LA15_99>='\u007F' && LA15_99<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_99);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA15_90 = input.LA(1);

                        s = -1;
                        if ( ((LA15_90>='#' && LA15_90<='$')||LA15_90=='&'||(LA15_90>='+' && LA15_90<=',')||LA15_90=='/'||(LA15_90>=':' && LA15_90<=';')||LA15_90=='='||(LA15_90>='?' && LA15_90<='@')) ) {s = 90;}

                        else if ( ((LA15_90>='A' && LA15_90<='Z')||LA15_90=='_'||(LA15_90>='a' && LA15_90<='z')) ) {s = 91;}

                        else if ( ((LA15_90>='0' && LA15_90<='9')) ) {s = 92;}

                        else if ( (LA15_90=='*') ) {s = 93;}

                        else if ( (LA15_90=='%') ) {s = 94;}

                        else if ( (LA15_90=='!'||LA15_90=='\''||(LA15_90>='-' && LA15_90<='.')||LA15_90=='~') ) {s = 96;}

                        else if ( ((LA15_90>='\u0000' && LA15_90<=' ')||LA15_90=='\"'||(LA15_90>='(' && LA15_90<=')')||LA15_90=='<'||LA15_90=='>'||(LA15_90>='[' && LA15_90<='^')||LA15_90=='`'||(LA15_90>='{' && LA15_90<='}')||(LA15_90>='\u007F' && LA15_90<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA15_127 = input.LA(1);

                         
                        int index15_127 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_127>='#' && LA15_127<='$')||LA15_127=='&'||(LA15_127>='+' && LA15_127<=',')||LA15_127=='/'||(LA15_127>=':' && LA15_127<=';')||LA15_127=='='||(LA15_127>='?' && LA15_127<='@')) ) {s = 97;}

                        else if ( ((LA15_127>='A' && LA15_127<='Z')||LA15_127=='_'||(LA15_127>='a' && LA15_127<='z')) ) {s = 127;}

                        else if ( ((LA15_127>='0' && LA15_127<='9')) ) {s = 128;}

                        else if ( (LA15_127=='!'||LA15_127=='\''||LA15_127=='*'||(LA15_127>='-' && LA15_127<='.')||LA15_127=='~') ) {s = 101;}

                        else if ( (LA15_127=='%') ) {s = 102;}

                        else if ( ((LA15_127>='\u0000' && LA15_127<='\t')||(LA15_127>='\u000B' && LA15_127<=' ')||LA15_127=='\"'||(LA15_127>='(' && LA15_127<=')')||LA15_127=='<'||LA15_127=='>'||(LA15_127>='[' && LA15_127<='^')||LA15_127=='`'||(LA15_127>='{' && LA15_127<='}')||(LA15_127>='\u007F' && LA15_127<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_127);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA15_142 = input.LA(1);

                        s = -1;
                        if ( ((LA15_142>='#' && LA15_142<='$')||LA15_142=='&'||(LA15_142>='+' && LA15_142<=',')||LA15_142=='/'||(LA15_142>=':' && LA15_142<=';')||LA15_142=='='||(LA15_142>='?' && LA15_142<='@')) ) {s = 118;}

                        else if ( ((LA15_142>='A' && LA15_142<='Z')||LA15_142=='_'||(LA15_142>='a' && LA15_142<='z')) ) {s = 142;}

                        else if ( ((LA15_142>='0' && LA15_142<='9')) ) {s = 143;}

                        else if ( (LA15_142=='!'||LA15_142=='\''||LA15_142=='*'||(LA15_142>='-' && LA15_142<='.')||LA15_142=='~') ) {s = 121;}

                        else if ( (LA15_142=='%') ) {s = 122;}

                        else if ( ((LA15_142>='\u0000' && LA15_142<=' ')||LA15_142=='\"'||(LA15_142>='(' && LA15_142<=')')||LA15_142=='<'||LA15_142=='>'||(LA15_142>='[' && LA15_142<='^')||LA15_142=='`'||(LA15_142>='{' && LA15_142<='}')||(LA15_142>='\u007F' && LA15_142<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA15_123 = input.LA(1);

                        s = -1;
                        if ( ((LA15_123>='#' && LA15_123<='$')||LA15_123=='&'||(LA15_123>='+' && LA15_123<=',')||LA15_123=='/'||(LA15_123>=':' && LA15_123<=';')||LA15_123=='='||(LA15_123>='?' && LA15_123<='@')) ) {s = 90;}

                        else if ( ((LA15_123>='A' && LA15_123<='Z')||LA15_123=='_'||(LA15_123>='a' && LA15_123<='z')) ) {s = 123;}

                        else if ( ((LA15_123>='0' && LA15_123<='9')) ) {s = 124;}

                        else if ( (LA15_123=='*') ) {s = 93;}

                        else if ( (LA15_123=='%') ) {s = 94;}

                        else if ( (LA15_123=='!'||LA15_123=='\''||(LA15_123>='-' && LA15_123<='.')||LA15_123=='~') ) {s = 96;}

                        else if ( ((LA15_123>='\u0000' && LA15_123<=' ')||LA15_123=='\"'||(LA15_123>='(' && LA15_123<=')')||LA15_123=='<'||LA15_123=='>'||(LA15_123>='[' && LA15_123<='^')||LA15_123=='`'||(LA15_123>='{' && LA15_123<='}')||(LA15_123>='\u007F' && LA15_123<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA15_119 = input.LA(1);

                        s = -1;
                        if ( ((LA15_119>='A' && LA15_119<='Z')||LA15_119=='_'||(LA15_119>='a' && LA15_119<='z')) ) {s = 142;}

                        else if ( ((LA15_119>='#' && LA15_119<='$')||LA15_119=='&'||(LA15_119>='+' && LA15_119<=',')||LA15_119=='/'||(LA15_119>=':' && LA15_119<=';')||LA15_119=='='||(LA15_119>='?' && LA15_119<='@')) ) {s = 118;}

                        else if ( ((LA15_119>='0' && LA15_119<='9')) ) {s = 143;}

                        else if ( (LA15_119=='!'||LA15_119=='\''||LA15_119=='*'||(LA15_119>='-' && LA15_119<='.')||LA15_119=='~') ) {s = 121;}

                        else if ( (LA15_119=='%') ) {s = 122;}

                        else if ( ((LA15_119>='\u0000' && LA15_119<=' ')||LA15_119=='\"'||(LA15_119>='(' && LA15_119<=')')||LA15_119=='<'||LA15_119=='>'||(LA15_119>='[' && LA15_119<='^')||LA15_119=='`'||(LA15_119>='{' && LA15_119<='}')||(LA15_119>='\u007F' && LA15_119<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA15_93 = input.LA(1);

                        s = -1;
                        if ( (LA15_93=='/') ) {s = 125;}

                        else if ( ((LA15_93>='#' && LA15_93<='$')||LA15_93=='&'||(LA15_93>='+' && LA15_93<=',')||(LA15_93>=':' && LA15_93<=';')||LA15_93=='='||(LA15_93>='?' && LA15_93<='@')) ) {s = 90;}

                        else if ( ((LA15_93>='A' && LA15_93<='Z')||LA15_93=='_'||(LA15_93>='a' && LA15_93<='z')) ) {s = 91;}

                        else if ( ((LA15_93>='0' && LA15_93<='9')) ) {s = 92;}

                        else if ( (LA15_93=='*') ) {s = 93;}

                        else if ( (LA15_93=='%') ) {s = 94;}

                        else if ( (LA15_93=='!'||LA15_93=='\''||(LA15_93>='-' && LA15_93<='.')||LA15_93=='~') ) {s = 96;}

                        else if ( ((LA15_93>='\u0000' && LA15_93<=' ')||LA15_93=='\"'||(LA15_93>='(' && LA15_93<=')')||LA15_93=='<'||LA15_93=='>'||(LA15_93>='[' && LA15_93<='^')||LA15_93=='`'||(LA15_93>='{' && LA15_93<='}')||(LA15_93>='\u007F' && LA15_93<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA15_144 = input.LA(1);

                        s = -1;
                        if ( ((LA15_144>='0' && LA15_144<='9')||(LA15_144>='A' && LA15_144<='F')||(LA15_144>='a' && LA15_144<='f')) ) {s = 167;}

                        else if ( ((LA15_144>='\u0000' && LA15_144<='/')||(LA15_144>=':' && LA15_144<='@')||(LA15_144>='G' && LA15_144<='`')||(LA15_144>='g' && LA15_144<='\uFFFF')) ) {s = 117;}

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA15_97 = input.LA(1);

                         
                        int index15_97 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_97>='#' && LA15_97<='$')||LA15_97=='&'||(LA15_97>='+' && LA15_97<=',')||LA15_97=='/'||(LA15_97>=':' && LA15_97<=';')||LA15_97=='='||(LA15_97>='?' && LA15_97<='@')) ) {s = 97;}

                        else if ( ((LA15_97>='A' && LA15_97<='Z')||LA15_97=='_'||(LA15_97>='a' && LA15_97<='z')) ) {s = 99;}

                        else if ( ((LA15_97>='0' && LA15_97<='9')) ) {s = 100;}

                        else if ( (LA15_97=='!'||LA15_97=='\''||LA15_97=='*'||(LA15_97>='-' && LA15_97<='.')||LA15_97=='~') ) {s = 101;}

                        else if ( (LA15_97=='%') ) {s = 102;}

                        else if ( ((LA15_97>='\u0000' && LA15_97<='\t')||(LA15_97>='\u000B' && LA15_97<=' ')||LA15_97=='\"'||(LA15_97>='(' && LA15_97<=')')||LA15_97=='<'||LA15_97=='>'||(LA15_97>='[' && LA15_97<='^')||LA15_97=='`'||(LA15_97>='{' && LA15_97<='}')||(LA15_97>='\u007F' && LA15_97<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_97);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA15_66 = input.LA(1);

                         
                        int index15_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_66>='#' && LA15_66<='$')||LA15_66=='&'||(LA15_66>='+' && LA15_66<=',')||LA15_66=='/'||(LA15_66>=':' && LA15_66<=';')||LA15_66=='='||(LA15_66>='?' && LA15_66<='@')) ) {s = 97;}

                        else if ( ((LA15_66>='A' && LA15_66<='Z')||LA15_66=='_'||(LA15_66>='a' && LA15_66<='z')) ) {s = 99;}

                        else if ( ((LA15_66>='0' && LA15_66<='9')) ) {s = 100;}

                        else if ( (LA15_66=='!'||LA15_66=='\''||LA15_66=='*'||(LA15_66>='-' && LA15_66<='.')||LA15_66=='~') ) {s = 101;}

                        else if ( (LA15_66=='%') ) {s = 102;}

                        else if ( ((LA15_66>='\u0000' && LA15_66<='\t')||(LA15_66>='\u000B' && LA15_66<=' ')||LA15_66=='\"'||(LA15_66>='(' && LA15_66<=')')||LA15_66=='<'||LA15_66=='>'||(LA15_66>='[' && LA15_66<='^')||LA15_66=='`'||(LA15_66>='{' && LA15_66<='}')||(LA15_66>='\u007F' && LA15_66<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_66);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA15_94 = input.LA(1);

                        s = -1;
                        if ( ((LA15_94>='\u0000' && LA15_94<='/')||(LA15_94>=':' && LA15_94<='@')||(LA15_94>='G' && LA15_94<='`')||(LA15_94>='g' && LA15_94<='\uFFFF')) ) {s = 95;}

                        else if ( ((LA15_94>='0' && LA15_94<='9')||(LA15_94>='A' && LA15_94<='F')||(LA15_94>='a' && LA15_94<='f')) ) {s = 126;}

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA15_65 = input.LA(1);

                        s = -1;
                        if ( ((LA15_65>='#' && LA15_65<='$')||LA15_65=='&'||(LA15_65>='+' && LA15_65<=',')||LA15_65=='/'||(LA15_65>=':' && LA15_65<=';')||LA15_65=='='||(LA15_65>='?' && LA15_65<='@')) ) {s = 90;}

                        else if ( ((LA15_65>='A' && LA15_65<='Z')||LA15_65=='_'||(LA15_65>='a' && LA15_65<='z')) ) {s = 91;}

                        else if ( ((LA15_65>='0' && LA15_65<='9')) ) {s = 92;}

                        else if ( (LA15_65=='*') ) {s = 93;}

                        else if ( (LA15_65=='%') ) {s = 94;}

                        else if ( ((LA15_65>='\u0000' && LA15_65<=' ')||LA15_65=='\"'||(LA15_65>='(' && LA15_65<=')')||LA15_65=='<'||LA15_65=='>'||(LA15_65>='[' && LA15_65<='^')||LA15_65=='`'||(LA15_65>='{' && LA15_65<='}')||(LA15_65>='\u007F' && LA15_65<='\uFFFF')) ) {s = 95;}

                        else if ( (LA15_65=='!'||LA15_65=='\''||(LA15_65>='-' && LA15_65<='.')||LA15_65=='~') ) {s = 96;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA15_145 = input.LA(1);

                        s = -1;
                        if ( ((LA15_145>='#' && LA15_145<='$')||LA15_145=='&'||(LA15_145>='+' && LA15_145<=',')||LA15_145=='/'||(LA15_145>=':' && LA15_145<=';')||LA15_145=='='||(LA15_145>='?' && LA15_145<='@')) ) {s = 90;}

                        else if ( ((LA15_145>='A' && LA15_145<='Z')||LA15_145=='_'||(LA15_145>='a' && LA15_145<='z')) ) {s = 91;}

                        else if ( ((LA15_145>='0' && LA15_145<='9')) ) {s = 92;}

                        else if ( (LA15_145=='*') ) {s = 93;}

                        else if ( (LA15_145=='%') ) {s = 94;}

                        else if ( (LA15_145=='!'||LA15_145=='\''||(LA15_145>='-' && LA15_145<='.')||LA15_145=='~') ) {s = 96;}

                        else if ( ((LA15_145>='\u0000' && LA15_145<=' ')||LA15_145=='\"'||(LA15_145>='(' && LA15_145<=')')||LA15_145=='<'||LA15_145=='>'||(LA15_145>='[' && LA15_145<='^')||LA15_145=='`'||(LA15_145>='{' && LA15_145<='}')||(LA15_145>='\u007F' && LA15_145<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA15_92 = input.LA(1);

                        s = -1;
                        if ( ((LA15_92>='#' && LA15_92<='$')||LA15_92=='&'||(LA15_92>='+' && LA15_92<=',')||LA15_92=='/'||(LA15_92>=':' && LA15_92<=';')||LA15_92=='='||(LA15_92>='?' && LA15_92<='@')) ) {s = 90;}

                        else if ( ((LA15_92>='A' && LA15_92<='Z')||LA15_92=='_'||(LA15_92>='a' && LA15_92<='z')) ) {s = 91;}

                        else if ( ((LA15_92>='0' && LA15_92<='9')) ) {s = 92;}

                        else if ( (LA15_92=='*') ) {s = 93;}

                        else if ( (LA15_92=='%') ) {s = 94;}

                        else if ( (LA15_92=='!'||LA15_92=='\''||(LA15_92>='-' && LA15_92<='.')||LA15_92=='~') ) {s = 96;}

                        else if ( ((LA15_92>='\u0000' && LA15_92<=' ')||LA15_92=='\"'||(LA15_92>='(' && LA15_92<=')')||LA15_92=='<'||LA15_92=='>'||(LA15_92>='[' && LA15_92<='^')||LA15_92=='`'||(LA15_92>='{' && LA15_92<='}')||(LA15_92>='\u007F' && LA15_92<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA15_91 = input.LA(1);

                        s = -1;
                        if ( ((LA15_91>='A' && LA15_91<='Z')||LA15_91=='_'||(LA15_91>='a' && LA15_91<='z')) ) {s = 123;}

                        else if ( ((LA15_91>='#' && LA15_91<='$')||LA15_91=='&'||(LA15_91>='+' && LA15_91<=',')||LA15_91=='/'||(LA15_91>=':' && LA15_91<=';')||LA15_91=='='||(LA15_91>='?' && LA15_91<='@')) ) {s = 90;}

                        else if ( ((LA15_91>='0' && LA15_91<='9')) ) {s = 124;}

                        else if ( (LA15_91=='*') ) {s = 93;}

                        else if ( (LA15_91=='%') ) {s = 94;}

                        else if ( (LA15_91=='!'||LA15_91=='\''||(LA15_91>='-' && LA15_91<='.')||LA15_91=='~') ) {s = 96;}

                        else if ( ((LA15_91>='\u0000' && LA15_91<=' ')||LA15_91=='\"'||(LA15_91>='(' && LA15_91<=')')||LA15_91=='<'||LA15_91=='>'||(LA15_91>='[' && LA15_91<='^')||LA15_91=='`'||(LA15_91>='{' && LA15_91<='}')||(LA15_91>='\u007F' && LA15_91<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA15_118 = input.LA(1);

                        s = -1;
                        if ( ((LA15_118>='#' && LA15_118<='$')||LA15_118=='&'||(LA15_118>='+' && LA15_118<=',')||LA15_118=='/'||(LA15_118>=':' && LA15_118<=';')||LA15_118=='='||(LA15_118>='?' && LA15_118<='@')) ) {s = 118;}

                        else if ( ((LA15_118>='A' && LA15_118<='Z')||LA15_118=='_'||(LA15_118>='a' && LA15_118<='z')) ) {s = 119;}

                        else if ( ((LA15_118>='0' && LA15_118<='9')) ) {s = 120;}

                        else if ( (LA15_118=='!'||LA15_118=='\''||LA15_118=='*'||(LA15_118>='-' && LA15_118<='.')||LA15_118=='~') ) {s = 121;}

                        else if ( (LA15_118=='%') ) {s = 122;}

                        else if ( ((LA15_118>='\u0000' && LA15_118<=' ')||LA15_118=='\"'||(LA15_118>='(' && LA15_118<=')')||LA15_118=='<'||LA15_118=='>'||(LA15_118>='[' && LA15_118<='^')||LA15_118=='`'||(LA15_118>='{' && LA15_118<='}')||(LA15_118>='\u007F' && LA15_118<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA15_143 = input.LA(1);

                        s = -1;
                        if ( ((LA15_143>='#' && LA15_143<='$')||LA15_143=='&'||(LA15_143>='+' && LA15_143<=',')||LA15_143=='/'||(LA15_143>=':' && LA15_143<=';')||LA15_143=='='||(LA15_143>='?' && LA15_143<='@')) ) {s = 118;}

                        else if ( ((LA15_143>='A' && LA15_143<='Z')||LA15_143=='_'||(LA15_143>='a' && LA15_143<='z')) ) {s = 142;}

                        else if ( ((LA15_143>='0' && LA15_143<='9')) ) {s = 143;}

                        else if ( (LA15_143=='!'||LA15_143=='\''||LA15_143=='*'||(LA15_143>='-' && LA15_143<='.')||LA15_143=='~') ) {s = 121;}

                        else if ( (LA15_143=='%') ) {s = 122;}

                        else if ( ((LA15_143>='\u0000' && LA15_143<=' ')||LA15_143=='\"'||(LA15_143>='(' && LA15_143<=')')||LA15_143=='<'||LA15_143=='>'||(LA15_143>='[' && LA15_143<='^')||LA15_143=='`'||(LA15_143>='{' && LA15_143<='}')||(LA15_143>='\u007F' && LA15_143<='\uFFFF')) ) {s = 117;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA15_128 = input.LA(1);

                         
                        int index15_128 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_128>='#' && LA15_128<='$')||LA15_128=='&'||(LA15_128>='+' && LA15_128<=',')||LA15_128=='/'||(LA15_128>=':' && LA15_128<=';')||LA15_128=='='||(LA15_128>='?' && LA15_128<='@')) ) {s = 97;}

                        else if ( ((LA15_128>='A' && LA15_128<='Z')||LA15_128=='_'||(LA15_128>='a' && LA15_128<='z')) ) {s = 127;}

                        else if ( ((LA15_128>='0' && LA15_128<='9')) ) {s = 128;}

                        else if ( (LA15_128=='!'||LA15_128=='\''||LA15_128=='*'||(LA15_128>='-' && LA15_128<='.')||LA15_128=='~') ) {s = 101;}

                        else if ( (LA15_128=='%') ) {s = 102;}

                        else if ( ((LA15_128>='\u0000' && LA15_128<='\t')||(LA15_128>='\u000B' && LA15_128<=' ')||LA15_128=='\"'||(LA15_128>='(' && LA15_128<=')')||LA15_128=='<'||LA15_128=='>'||(LA15_128>='[' && LA15_128<='^')||LA15_128=='`'||(LA15_128>='{' && LA15_128<='}')||(LA15_128>='\u007F' && LA15_128<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 103;}

                        else s = 98;

                         
                        input.seek(index15_128);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA15_96 = input.LA(1);

                        s = -1;
                        if ( ((LA15_96>='#' && LA15_96<='$')||LA15_96=='&'||(LA15_96>='+' && LA15_96<=',')||LA15_96=='/'||(LA15_96>=':' && LA15_96<=';')||LA15_96=='='||(LA15_96>='?' && LA15_96<='@')) ) {s = 90;}

                        else if ( ((LA15_96>='A' && LA15_96<='Z')||LA15_96=='_'||(LA15_96>='a' && LA15_96<='z')) ) {s = 91;}

                        else if ( ((LA15_96>='0' && LA15_96<='9')) ) {s = 92;}

                        else if ( (LA15_96=='*') ) {s = 93;}

                        else if ( (LA15_96=='%') ) {s = 94;}

                        else if ( (LA15_96=='!'||LA15_96=='\''||(LA15_96>='-' && LA15_96<='.')||LA15_96=='~') ) {s = 96;}

                        else if ( ((LA15_96>='\u0000' && LA15_96<=' ')||LA15_96=='\"'||(LA15_96>='(' && LA15_96<=')')||LA15_96=='<'||LA15_96=='>'||(LA15_96>='[' && LA15_96<='^')||LA15_96=='`'||(LA15_96>='{' && LA15_96<='}')||(LA15_96>='\u007F' && LA15_96<='\uFFFF')) ) {s = 95;}

                        else s = 34;

                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA15_98 = input.LA(1);

                         
                        int index15_98 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 103;}

                        else if ( (true) ) {s = 34;}

                         
                        input.seek(index15_98);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA15_102 = input.LA(1);

                         
                        int index15_102 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA15_102>='0' && LA15_102<='9')||(LA15_102>='A' && LA15_102<='F')||(LA15_102>='a' && LA15_102<='f')) ) {s = 129;}

                        else s = 103;

                         
                        input.seek(index15_102);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA15_122 = input.LA(1);

                        s = -1;
                        if ( ((LA15_122>='0' && LA15_122<='9')||(LA15_122>='A' && LA15_122<='F')||(LA15_122>='a' && LA15_122<='f')) ) {s = 144;}

                        else if ( ((LA15_122>='\u0000' && LA15_122<='/')||(LA15_122>=':' && LA15_122<='@')||(LA15_122>='G' && LA15_122<='`')||(LA15_122>='g' && LA15_122<='\uFFFF')) ) {s = 117;}

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