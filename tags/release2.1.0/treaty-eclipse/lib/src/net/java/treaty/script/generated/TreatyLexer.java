// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-11-26 11:23:16

package net.java.treaty.script.generated;

import java.util.List;
import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TreatyLexer extends Lexer {
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__65=65;
    public static final int Gt=21;
    public static final int Hash=37;
    public static final int Exponent=45;
    public static final int LineComment=58;
    public static final int Newline=4;
    public static final int AnnotationValue=6;
    public static final int Uri=18;
    public static final int DecimalDigit=50;
    public static final int AnnotationKey=5;
    public static final int Percent=39;
    public static final int IDLetter=55;
    public static final int EOF=-1;
    public static final int HexDigit=49;
    public static final int Identifier=8;
    public static final int Amper=28;
    public static final int BlockComment=57;
    public static final int Lt=23;
    public static final int XOr=12;
    public static final int UriCharacter=59;
    public static final int ResourceNameAttribute=9;
    public static final int UriUnescaped=61;
    public static final int String=52;
    public static final int Or=13;
    public static final int IDDigit=56;
    public static final int Whitespace=51;
    public static final int ResourceReferenceAttribute=11;
    public static final int Dollar=34;
    public static final int NamespaceDelimiter=54;
    public static final int Tilde=44;
    public static final int And=14;
    public static final int Asterisk=30;
    public static final int RParen=17;
    public static final int Lte=24;
    public static final int Exclamation=36;
    public static final int At=31;
    public static final int LParen=16;
    public static final int Trigger=7;
    public static final int Colon=32;
    public static final int Gte=22;
    public static final int NotEqual=20;
    public static final int Question=41;
    public static final int Equal=19;
    public static final int UriMark=64;
    public static final int StringLiteral=27;
    public static final int Plus=40;
    public static final int Minus=38;
    public static final int T__71=71;
    public static final int Semi=42;
    public static final int UriReserved=60;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int UnicodeEscape=47;
    public static final int FloatingPointLiteral=26;
    public static final int Not=15;
    public static final int UriAlpha=63;
    public static final int Dot=35;
    public static final int UriEscaped=62;
    public static final int IntegerLiteral=25;
    public static final int Annotation=53;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int Comma=33;
    public static final int T__73=73;
    public static final int EscapeSequence=46;
    public static final int OctalEscape=48;
    public static final int Apostrophe=29;
    public static final int Slash=43;
    public static final int ResourceTypeAttribute=10;

    static private Token NEWLINE_TOKEN = new CommonToken(Newline, "\n");

    // Workaround until getText(), and setText() work as expected within lexer fragments.
    private String lastAnnotationValue = null;

    // Keeps track of the implicit line joining level, so statments between parentheses can can span multiple lines.
    private int implicitLineJoiningLevel = 0;

    private List<Token> tokens = new LinkedList<Token>();

    public void emit(Token token) {
        state.token = token;
        tokens.add(token);
    }

    public Token nextToken() {
        super.nextToken();
        
        if (tokens.size() == 0) {
            // Eliminates the edge case were a final statement may or may not end with a new line.
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

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:7: ( 'consumer-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:40:9: 'consumer-resource'
            {
            match("consumer-resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:41:7: ( 'supplier-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:41:9: 'supplier-resource'
            {
            match("supplier-resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:42:7: ( 'external-resource' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:42:9: 'external-resource'
            {
            match("external-resource"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:43:7: ( 'constraint' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:43:9: 'constraint'
            {
            match("constraint"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:44:7: ( 'mustexist' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:44:9: 'mustexist'
            {
            match("mustexist"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:45:7: ( 'matches' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:45:9: 'matches'
            {
            match("matches"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:46:7: ( 'in' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:46:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:47:7: ( 'true' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:47:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:48:7: ( 'false' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:48:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:49:7: ( 'onfailure' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:49:9: 'onfailure'
            {
            match("onfailure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:50:7: ( 'onsuccess' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:50:9: 'onsuccess'
            {
            match("onsuccess"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "And"
    public final void mAnd() throws RecognitionException {
        try {
            int _type = And;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:386:13: ( 'and' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:386:15: 'and'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:387:13: ( 'not' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:387:15: 'not'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:388:13: ( 'or' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:388:15: 'or'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:389:13: ( 'xor' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:389:15: 'xor'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:391:13: ( '(' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:391:15: '('
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:392:13: ( ')' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:392:15: ')'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:394:13: ( '&' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:394:15: '&'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:13: ( '\\'' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:395:15: '\\''
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:396:13: ( '*' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:396:15: '*'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:397:13: ( '@' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:397:15: '@'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:398:13: ( ':' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:398:15: ':'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:13: ( ',' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:399:15: ','
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:400:13: ( '$' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:400:15: '$'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:401:13: ( '.' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:401:15: '.'
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

    // $ANTLR start "Equal"
    public final void mEqual() throws RecognitionException {
        try {
            int _type = Equal;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:402:13: ( '=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:402:15: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Equal"

    // $ANTLR start "Exclamation"
    public final void mExclamation() throws RecognitionException {
        try {
            int _type = Exclamation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:403:13: ( '!' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:403:15: '!'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:404:13: ( '#' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:404:15: '#'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:405:13: ( '-' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:405:15: '-'
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

    // $ANTLR start "NotEqual"
    public final void mNotEqual() throws RecognitionException {
        try {
            int _type = NotEqual;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:406:13: ( '!=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:406:15: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NotEqual"

    // $ANTLR start "Percent"
    public final void mPercent() throws RecognitionException {
        try {
            int _type = Percent;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:407:13: ( '%' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:407:15: '%'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:408:13: ( '+' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:408:15: '+'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:409:13: ( '?' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:409:15: '?'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:410:13: ( ';' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:410:15: ';'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:411:13: ( '/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:411:15: '/'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:412:13: ( '~' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:412:15: '~'
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

    // $ANTLR start "Gt"
    public final void mGt() throws RecognitionException {
        try {
            int _type = Gt;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:414:13: ( '>' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:414:15: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Gt"

    // $ANTLR start "Lt"
    public final void mLt() throws RecognitionException {
        try {
            int _type = Lt;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:415:13: ( '<' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:415:15: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Lt"

    // $ANTLR start "Gte"
    public final void mGte() throws RecognitionException {
        try {
            int _type = Gte;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:416:13: ( '>=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:416:15: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Gte"

    // $ANTLR start "Lte"
    public final void mLte() throws RecognitionException {
        try {
            int _type = Lte;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:417:13: ( '<=' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:417:15: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Lte"

    // $ANTLR start "IntegerLiteral"
    public final void mIntegerLiteral() throws RecognitionException {
        try {
            int _type = IntegerLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:5: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:9: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:9: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='0') ) {
                alt2=1;
            }
            else if ( ((LA2_0>='1' && LA2_0<='9')) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:10: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:16: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:25: ( '0' .. '9' )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:420:25: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IntegerLiteral"

    // $ANTLR start "FloatingPointLiteral"
    public final void mFloatingPointLiteral() throws RecognitionException {
        try {
            int _type = FloatingPointLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:424:5: ( | ( '0' | '1' .. '9' ( '0' .. '9' )* ) '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent )
            int alt10=4;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:5: 
                    {
                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:9: ( '0' | '1' .. '9' ( '0' .. '9' )* ) '.' ( '0' .. '9' )* ( Exponent )?
                    {
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:9: ( '0' | '1' .. '9' ( '0' .. '9' )* )
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='0') ) {
                        alt4=1;
                    }
                    else if ( ((LA4_0>='1' && LA4_0<='9')) ) {
                        alt4=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 0, input);

                        throw nvae;
                    }
                    switch (alt4) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:10: '0'
                            {
                            match('0'); 

                            }
                            break;
                        case 2 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:16: '1' .. '9' ( '0' .. '9' )*
                            {
                            matchRange('1','9'); 
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:25: ( '0' .. '9' )*
                            loop3:
                            do {
                                int alt3=2;
                                int LA3_0 = input.LA(1);

                                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                                    alt3=1;
                                }


                                switch (alt3) {
                            	case 1 :
                            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:25: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); 

                            	    }
                            	    break;

                            	default :
                            	    break loop3;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match('.'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:40: ( '0' .. '9' )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:41: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:52: ( Exponent )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='E'||LA6_0=='e') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:425:52: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:426:9: '.' ( '0' .. '9' )+ ( Exponent )?
                    {
                    match('.'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:426:13: ( '0' .. '9' )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:426:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:426:25: ( Exponent )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:426:25: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:427:9: ( '0' .. '9' )+ Exponent
                    {
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:427:9: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:427:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    mExponent(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FloatingPointLiteral"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:432:5: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:432:9: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:432:19: ( '+' | '-' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='+'||LA11_0=='-') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:432:30: ( '0' .. '9' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:432:31: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:436:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:436:9: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:436:13: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\\') ) {
                    alt13=1;
                }
                else if ( ((LA13_0>='\u0000' && LA13_0<='!')||(LA13_0>='#' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:436:15: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:436:32: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match('\"'); 

                        String value = getText().substring(1, getText().length() - 1); // strip quotes
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:444:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UnicodeEscape | OctalEscape )
            int alt14=3;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt14=1;
                    }
                    break;
                case 'u':
                    {
                    alt14=2;
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
                    {
                    alt14=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:444:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:445:9: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:446:9: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt15=3;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\\') ) {
                int LA15_1 = input.LA(2);

                if ( ((LA15_1>='0' && LA15_1<='3')) ) {
                    int LA15_2 = input.LA(3);

                    if ( ((LA15_2>='0' && LA15_2<='7')) ) {
                        int LA15_5 = input.LA(4);

                        if ( ((LA15_5>='0' && LA15_5<='7')) ) {
                            alt15=1;
                        }
                        else {
                            alt15=2;}
                    }
                    else {
                        alt15=3;}
                }
                else if ( ((LA15_1>='4' && LA15_1<='7')) ) {
                    int LA15_3 = input.LA(3);

                    if ( ((LA15_3>='0' && LA15_3<='7')) ) {
                        alt15=2;
                    }
                    else {
                        alt15=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:14: ( '0' .. '3' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:25: ( '0' .. '7' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:36: ( '0' .. '7' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:451:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:452:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:452:14: ( '0' .. '7' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:452:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:452:25: ( '0' .. '7' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:452:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:453:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:453:14: ( '0' .. '7' )
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:453:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:458:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:458:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "DecimalDigit"
    public final void mDecimalDigit() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:463:5: ( ( '0' .. '9' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:463:9: ( '0' .. '9' )
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:463:9: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:463:10: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DecimalDigit"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:468:4: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:468:8: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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

    // $ANTLR start "Trigger"
    public final void mTrigger() throws RecognitionException {
        try {
            int _type = Trigger;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token String1=null;

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:472:5: ( 'on' Whitespace String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:472:9: 'on' Whitespace String
            {
            match("on"); 

            mWhitespace(); 
            int String1Start1043 = getCharIndex();
            mString(); 
            String1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String1Start1043, getCharIndex()-1);

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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:479:5: ( 'name' Equal String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:479:9: 'name' Equal String
            {
            match("name"); 

            mEqual(); 
            int String2Start1077 = getCharIndex();
            mString(); 
            String2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String2Start1077, getCharIndex()-1);

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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:486:5: ( 'type' Equal String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:486:9: 'type' Equal String
            {
            match("type"); 

            mEqual(); 
            int String3Start1111 = getCharIndex();
            mString(); 
            String3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String3Start1111, getCharIndex()-1);

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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:493:5: ( 'ref' Equal String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:493:9: 'ref' Equal String
            {
            match("ref"); 

            mEqual(); 
            int String4Start1145 = getCharIndex();
            mString(); 
            String4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, String4Start1145, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:501:5: ( (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:501:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:501:9: (~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\u0000' && LA16_0<='\b')||LA16_0=='\u000B'||(LA16_0>='\u000E' && LA16_0<='\u001F')||(LA16_0>='!' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:501:9: ~ ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
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
            	    break loop16;
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

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:505:5: ( At key= AnnotationKey Equal value= AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:505:9: At key= AnnotationKey Equal value= AnnotationValue
            {
            mAt(); 
            int keyStart1213 = getCharIndex();
            mAnnotationKey(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart1213, getCharIndex()-1);
            mEqual(); 
            int valueStart1219 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart1219, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:515:5: ( Identifier ( NamespaceDelimiter Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:515:9: Identifier ( NamespaceDelimiter Identifier )*
            {
            mIdentifier(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:515:20: ( NamespaceDelimiter Identifier )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='.'||LA17_0==':') ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:515:21: NamespaceDelimiter Identifier
            	    {
            	    mNamespaceDelimiter(); 
            	    mIdentifier(); 

            	    }
            	    break;

            	default :
            	    break loop17;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:520:5: ( Colon | Dot )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:526:5: ( ( options {greedy=false; } : . )* Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:526:9: ( options {greedy=false; } : . )* Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:526:9: ( options {greedy=false; } : . )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0=='\r') ) {
                    alt18=2;
                }
                else if ( (LA18_0=='\n') ) {
                    alt18=2;
                }
                else if ( ((LA18_0>='\u0000' && LA18_0<='\t')||(LA18_0>='\u000B' && LA18_0<='\f')||(LA18_0>='\u000E' && LA18_0<='\uFFFF')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:526:39: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            int Newline5Start1331 = getCharIndex();
            mNewline(); 
            Newline5 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Newline5Start1331, getCharIndex()-1);

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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:537:5: ( IDLetter ( IDLetter | IDDigit )* )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:537:9: IDLetter ( IDLetter | IDDigit )*
            {
            mIDLetter(); 
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:537:18: ( IDLetter | IDDigit )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='0' && LA19_0<='9')||(LA19_0>='A' && LA19_0<='Z')||LA19_0=='_'||(LA19_0>='a' && LA19_0<='z')) ) {
                    alt19=1;
                }


                switch (alt19) {
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
            	    break loop19;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:542:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:549:5: ( '0' .. '9' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:549:9: '0' .. '9'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:553:5: ( ( '\\r' )? '\\n' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:553:9: ( '\\r' )? '\\n'
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:553:9: ( '\\r' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\r') ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:553:9: '\\r'
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:563:5: ( ( ' ' | '\\t' )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:563:9: ( ' ' | '\\t' )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:563:9: ( ' ' | '\\t' )+
            int cnt21=0;
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='\t'||LA21_0==' ') ) {
                    alt21=1;
                }


                switch (alt21) {
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
            	    if ( cnt21 >= 1 ) break loop21;
                        EarlyExitException eee =
                            new EarlyExitException(21, input);
                        throw eee;
                }
                cnt21++;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:568:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:568:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:568:14: ( options {greedy=false; } : . )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0=='*') ) {
                    int LA22_1 = input.LA(2);

                    if ( (LA22_1=='/') ) {
                        alt22=2;
                    }
                    else if ( ((LA22_1>='\u0000' && LA22_1<='.')||(LA22_1>='0' && LA22_1<='\uFFFF')) ) {
                        alt22=1;
                    }


                }
                else if ( ((LA22_0>='\u0000' && LA22_0<=')')||(LA22_0>='+' && LA22_0<='\uFFFF')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:568:44: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop22;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:5: ({...}? => ( Whitespace )? '//' (~ ( '\\n' ) )* | {...}? => Whitespace '//' (~ ( '\\n' ) )* )
            int alt26=2;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:9: {...}? => ( Whitespace )? '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() == 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() == 0 ");
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:46: ( Whitespace )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='\t'||LA23_0==' ') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:46: Whitespace
                            {
                            mWhitespace(); 

                            }
                            break;

                    }

                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:63: (~ ( '\\n' ) )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( ((LA24_0>='\u0000' && LA24_0<='\t')||(LA24_0>='\u000B' && LA24_0<='\uFFFF')) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:577:63: ~ ( '\\n' )
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
                    	    break loop24;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:578:9: {...}? => Whitespace '//' (~ ( '\\n' ) )*
                    {
                    if ( !(( getCharPositionInLine() >= 0 )) ) {
                        throw new FailedPredicateException(input, "LineComment", " getCharPositionInLine() >= 0 ");
                    }
                    mWhitespace(); 
                    match("//"); 

                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:578:62: (~ ( '\\n' ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0>='\u0000' && LA25_0<='\t')||(LA25_0>='\u000B' && LA25_0<='\uFFFF')) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:578:62: ~ ( '\\n' )
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
                    	    break loop25;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:585:5: ( ( UriCharacter )+ )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:585:9: ( UriCharacter )+
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:585:9: ( UriCharacter )+
            int cnt27=0;
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0=='!'||(LA27_0>='#' && LA27_0<='\'')||(LA27_0>='*' && LA27_0<=';')||LA27_0=='='||(LA27_0>='?' && LA27_0<='Z')||LA27_0=='_'||(LA27_0>='a' && LA27_0<='z')||LA27_0=='~') ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:585:9: UriCharacter
            	    {
            	    mUriCharacter(); 

            	    }
            	    break;

            	default :
            	    if ( cnt27 >= 1 ) break loop27;
                        EarlyExitException eee =
                            new EarlyExitException(27, input);
                        throw eee;
                }
                cnt27++;
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:590:5: ( UriReserved | UriUnescaped | UriEscaped )
            int alt28=3;
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
                alt28=1;
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
                alt28=2;
                }
                break;
            case '%':
                {
                alt28=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:590:9: UriReserved
                    {
                    mUriReserved(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:591:9: UriUnescaped
                    {
                    mUriUnescaped(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:592:9: UriEscaped
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:597:5: ( Semi | Slash | Question | Colon | At | Amper | Equal | Plus | Dollar | Comma | Hash )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:602:5: ( UriAlpha | DecimalDigit | UriMark )
            int alt29=3;
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
                alt29=1;
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
                alt29=2;
                }
                break;
            case '!':
            case '\'':
            case '*':
            case '-':
            case '.':
            case '~':
                {
                alt29=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:602:9: UriAlpha
                    {
                    mUriAlpha(); 

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:603:9: DecimalDigit
                    {
                    mDecimalDigit(); 

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:604:9: UriMark
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:609:5: ( Identifier )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:609:9: Identifier
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:614:5: ( Minus | Dot | Exclamation | Tilde | Asterisk | Apostrophe )
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
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:619:5: ( Percent HexDigit HexDigit )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:619:8: Percent HexDigit HexDigit
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

    public void mTokens() throws RecognitionException {
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:8: ( T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equal | Exclamation | Hash | Minus | NotEqual | Percent | Plus | Question | Semi | Slash | Tilde | Gt | Lt | Gte | Lte | IntegerLiteral | FloatingPointLiteral | StringLiteral | Trigger | ResourceNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri )
        int alt30=54;
        alt30 = dfa30.predict(input);
        switch (alt30) {
            case 1 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:10: T__65
                {
                mT__65(); 

                }
                break;
            case 2 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:16: T__66
                {
                mT__66(); 

                }
                break;
            case 3 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:22: T__67
                {
                mT__67(); 

                }
                break;
            case 4 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:28: T__68
                {
                mT__68(); 

                }
                break;
            case 5 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:34: T__69
                {
                mT__69(); 

                }
                break;
            case 6 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:40: T__70
                {
                mT__70(); 

                }
                break;
            case 7 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:46: T__71
                {
                mT__71(); 

                }
                break;
            case 8 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:52: T__72
                {
                mT__72(); 

                }
                break;
            case 9 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:58: T__73
                {
                mT__73(); 

                }
                break;
            case 10 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:64: T__74
                {
                mT__74(); 

                }
                break;
            case 11 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:70: T__75
                {
                mT__75(); 

                }
                break;
            case 12 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:76: And
                {
                mAnd(); 

                }
                break;
            case 13 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:80: Not
                {
                mNot(); 

                }
                break;
            case 14 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:84: Or
                {
                mOr(); 

                }
                break;
            case 15 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:87: XOr
                {
                mXOr(); 

                }
                break;
            case 16 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:91: LParen
                {
                mLParen(); 

                }
                break;
            case 17 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:98: RParen
                {
                mRParen(); 

                }
                break;
            case 18 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:105: Amper
                {
                mAmper(); 

                }
                break;
            case 19 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:111: Apostrophe
                {
                mApostrophe(); 

                }
                break;
            case 20 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:122: Asterisk
                {
                mAsterisk(); 

                }
                break;
            case 21 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:131: At
                {
                mAt(); 

                }
                break;
            case 22 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:134: Colon
                {
                mColon(); 

                }
                break;
            case 23 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:140: Comma
                {
                mComma(); 

                }
                break;
            case 24 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:146: Dollar
                {
                mDollar(); 

                }
                break;
            case 25 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:153: Dot
                {
                mDot(); 

                }
                break;
            case 26 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:157: Equal
                {
                mEqual(); 

                }
                break;
            case 27 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:163: Exclamation
                {
                mExclamation(); 

                }
                break;
            case 28 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:175: Hash
                {
                mHash(); 

                }
                break;
            case 29 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:180: Minus
                {
                mMinus(); 

                }
                break;
            case 30 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:186: NotEqual
                {
                mNotEqual(); 

                }
                break;
            case 31 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:195: Percent
                {
                mPercent(); 

                }
                break;
            case 32 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:203: Plus
                {
                mPlus(); 

                }
                break;
            case 33 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:208: Question
                {
                mQuestion(); 

                }
                break;
            case 34 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:217: Semi
                {
                mSemi(); 

                }
                break;
            case 35 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:222: Slash
                {
                mSlash(); 

                }
                break;
            case 36 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:228: Tilde
                {
                mTilde(); 

                }
                break;
            case 37 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:234: Gt
                {
                mGt(); 

                }
                break;
            case 38 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:237: Lt
                {
                mLt(); 

                }
                break;
            case 39 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:240: Gte
                {
                mGte(); 

                }
                break;
            case 40 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:244: Lte
                {
                mLte(); 

                }
                break;
            case 41 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:248: IntegerLiteral
                {
                mIntegerLiteral(); 

                }
                break;
            case 42 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:263: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 43 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:284: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 44 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:298: Trigger
                {
                mTrigger(); 

                }
                break;
            case 45 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:306: ResourceNameAttribute
                {
                mResourceNameAttribute(); 

                }
                break;
            case 46 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:328: ResourceTypeAttribute
                {
                mResourceTypeAttribute(); 

                }
                break;
            case 47 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:350: ResourceReferenceAttribute
                {
                mResourceReferenceAttribute(); 

                }
                break;
            case 48 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:377: Annotation
                {
                mAnnotation(); 

                }
                break;
            case 49 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:388: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 50 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:399: Newline
                {
                mNewline(); 

                }
                break;
            case 51 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:407: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 52 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:418: BlockComment
                {
                mBlockComment(); 

                }
                break;
            case 53 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:431: LineComment
                {
                mLineComment(); 

                }
                break;
            case 54 :
                // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:1:443: Uri
                {
                mUri(); 

                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA30 dfa30 = new DFA30(this);
    static final String DFA10_eotS =
        "\1\1\7\uffff";
    static final String DFA10_eofS =
        "\10\uffff";
    static final String DFA10_minS =
        "\1\56\1\uffff\2\56\3\uffff\1\56";
    static final String DFA10_maxS =
        "\1\71\1\uffff\2\145\3\uffff\1\145";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\2\uffff\1\3\1\2\1\4\1\uffff";
    static final String DFA10_specialS =
        "\10\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\4\1\uffff\1\2\11\3",
            "",
            "\1\5\1\uffff\12\6\13\uffff\1\6\37\uffff\1\6",
            "\1\5\1\uffff\12\7\13\uffff\1\6\37\uffff\1\6",
            "",
            "",
            "",
            "\1\5\1\uffff\12\7\13\uffff\1\6\37\uffff\1\6"
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
            return "423:1: FloatingPointLiteral : ( | ( '0' | '1' .. '9' ( '0' .. '9' )* ) '.' ( '0' .. '9' )* ( Exponent )? | '.' ( '0' .. '9' )+ ( Exponent )? | ( '0' .. '9' )+ Exponent );";
        }
    }
    static final String DFA26_eotS =
        "\6\uffff";
    static final String DFA26_eofS =
        "\6\uffff";
    static final String DFA26_minS =
        "\2\11\1\uffff\1\57\1\0\1\uffff";
    static final String DFA26_maxS =
        "\2\57\1\uffff\1\57\1\0\1\uffff";
    static final String DFA26_acceptS =
        "\2\uffff\1\1\2\uffff\1\2";
    static final String DFA26_specialS =
        "\1\2\1\1\1\uffff\1\3\1\0\1\uffff}>";
    static final String[] DFA26_transitionS = {
            "\1\1\26\uffff\1\1\16\uffff\1\2",
            "\1\1\26\uffff\1\1\16\uffff\1\3",
            "",
            "\1\4",
            "\1\uffff",
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "571:1: LineComment : ({...}? => ( Whitespace )? '//' (~ ( '\\n' ) )* | {...}? => Whitespace '//' (~ ( '\\n' ) )* );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_4 = input.LA(1);

                         
                        int index26_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 2;}

                        else if ( (( getCharPositionInLine() >= 0 )) ) {s = 5;}

                         
                        input.seek(index26_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_1 = input.LA(1);

                         
                        int index26_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA26_1=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 3;}

                        else if ( (LA26_1=='\t'||LA26_1==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                         
                        input.seek(index26_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_0 = input.LA(1);

                         
                        int index26_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA26_0=='\t'||LA26_0==' ') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 1;}

                        else if ( (LA26_0=='/') && (( getCharPositionInLine() == 0 ))) {s = 2;}

                         
                        input.seek(index26_0);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_3 = input.LA(1);

                         
                        int index26_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA26_3=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 4;}

                         
                        input.seek(index26_3);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA30_eotS =
        "\1\44\13\53\2\uffff\1\75\1\76\1\77\1\100\1\102\1\103\1\104\1\105"+
        "\1\107\1\111\1\112\1\113\1\114\1\115\1\116\1\117\1\122\1\123\1\125"+
        "\1\127\2\130\2\uffff\2\53\1\uffff\1\136\1\53\1\uffff\1\53\1\uffff"+
        "\5\53\1\145\4\53\1\154\4\53\4\uffff\1\55\4\uffff\1\44\1\uffff\1"+
        "\167\7\uffff\1\55\1\u0080\7\uffff\2\55\1\44\1\130\1\53\2\uffff\5"+
        "\53\1\uffff\5\53\2\uffff\1\u0096\1\u0097\1\53\1\u0099\6\55\1\uffff"+
        "\4\55\2\uffff\1\55\1\u0080\1\uffff\3\u0080\1\u0085\1\uffff\1\55"+
        "\1\44\2\55\1\44\6\53\1\u00b6\4\53\2\uffff\1\53\1\uffff\1\55\1\uffff"+
        "\4\55\1\uffff\1\55\1\44\3\55\1\175\1\uffff\2\u0080\1\u0085\1\44"+
        "\1\55\1\44\1\55\1\u00c5\6\53\1\uffff\1\u00d1\1\u00d7\2\53\1\u00da"+
        "\4\55\1\uffff\1\44\1\55\1\u0080\1\44\1\uffff\5\u00c5\6\53\1\uffff"+
        "\5\u00d1\1\uffff\2\53\1\uffff\5\u00da\1\55\3\u00c5\5\53\1\u00f8"+
        "\3\u00d1\2\53\3\u00da\1\u00c5\5\53\1\uffff\1\u00d1\2\53\1\u00da"+
        "\1\55\1\53\2\55\1\u0108\1\u0109\1\u010a\1\55\1\u010c\2\55\3\uffff"+
        "\1\55\1\uffff\21\55\1\u0121\1\u0122\1\u0123\3\uffff";
    static final String DFA30_eofS =
        "\u0124\uffff";
    static final String DFA30_minS =
        "\1\11\13\41\2\uffff\14\41\1\60\5\41\2\75\2\41\2\uffff\2\41\1\uffff"+
        "\1\11\1\41\1\uffff\1\41\1\uffff\11\41\1\11\5\41\4\uffff\1\56\4\uffff"+
        "\1\41\1\uffff\1\41\7\uffff\2\0\7\uffff\1\53\1\60\3\41\2\uffff\5"+
        "\41\1\uffff\5\41\2\uffff\4\41\1\56\1\101\1\56\1\101\1\0\1\53\1\uffff"+
        "\5\0\1\uffff\6\0\1\60\1\uffff\1\60\1\41\1\60\1\53\14\41\2\uffff"+
        "\1\41\1\uffff\1\56\1\uffff\5\0\1\60\1\41\1\60\2\0\1\41\3\0\1\60"+
        "\1\41\1\60\1\41\1\60\7\41\1\uffff\5\41\2\56\3\0\1\41\2\0\1\41\1"+
        "\uffff\4\41\1\60\6\41\1\uffff\4\41\1\60\1\uffff\2\41\1\uffff\4\41"+
        "\1\60\1\0\2\41\1\60\10\41\1\60\4\41\1\60\6\41\1\uffff\4\41\1\162"+
        "\1\41\2\162\3\41\1\145\1\41\2\145\3\uffff\1\163\1\uffff\2\163\3"+
        "\157\3\165\3\162\3\143\3\145\3\41\3\uffff";
    static final String DFA30_maxS =
        "\14\176\2\uffff\14\176\1\146\5\176\2\75\2\176\2\uffff\2\176\1\uffff"+
        "\1\57\1\176\1\uffff\1\176\1\uffff\17\176\4\uffff\1\172\4\uffff\1"+
        "\176\1\uffff\1\176\7\uffff\2\uffff\7\uffff\1\71\1\145\3\176\2\uffff"+
        "\5\176\1\uffff\5\176\2\uffff\4\176\4\172\1\uffff\1\71\1\uffff\5"+
        "\uffff\1\uffff\2\uffff\1\0\3\uffff\1\146\1\uffff\1\71\1\176\2\71"+
        "\14\176\2\uffff\1\176\1\uffff\1\172\1\uffff\5\uffff\1\71\1\176\1"+
        "\71\2\uffff\1\176\3\uffff\1\146\1\176\1\71\1\176\1\71\7\176\1\uffff"+
        "\5\176\2\172\3\uffff\1\176\2\uffff\1\176\1\uffff\4\176\1\146\6\176"+
        "\1\uffff\4\176\1\146\1\uffff\2\176\1\uffff\4\176\1\146\1\uffff\2"+
        "\176\1\146\10\176\1\146\4\176\1\146\6\176\1\uffff\4\176\1\162\1"+
        "\176\2\162\3\176\1\145\1\176\2\145\3\uffff\1\163\1\uffff\2\163\3"+
        "\157\3\165\3\162\3\143\3\145\3\176\3\uffff";
    static final String DFA30_acceptS =
        "\14\uffff\1\20\1\21\26\uffff\1\52\1\53\2\uffff\1\62\2\uffff\1\61"+
        "\1\uffff\1\66\17\uffff\1\22\1\23\1\24\1\25\1\uffff\1\26\1\27\1\30"+
        "\1\31\1\uffff\1\32\1\uffff\1\33\1\34\1\35\1\37\1\40\1\41\1\42\2"+
        "\uffff\1\43\1\44\1\47\1\45\1\50\1\46\1\51\5\uffff\1\63\1\65\5\uffff"+
        "\1\7\5\uffff\1\54\1\16\12\uffff\1\36\5\uffff\1\64\7\uffff\1\65\20"+
        "\uffff\1\14\1\15\1\uffff\1\17\1\uffff\1\60\32\uffff\1\10\16\uffff"+
        "\1\57\13\uffff\1\56\5\uffff\1\11\2\uffff\1\55\35\uffff\1\6\17\uffff"+
        "\1\5\1\12\1\13\1\uffff\1\4\24\uffff\1\1\1\2\1\3";
    static final String DFA30_specialS =
        "\51\uffff\1\0\46\uffff\1\25\1\34\43\uffff\1\40\2\uffff\1\24\1\10"+
        "\1\30\1\5\1\2\1\uffff\1\11\1\6\1\13\1\37\1\35\1\20\1\27\27\uffff"+
        "\1\16\1\33\1\32\1\1\1\17\3\uffff\1\26\1\15\1\uffff\1\31\1\4\1\22"+
        "\1\12\23\uffff\1\21\1\14\1\36\1\uffff\1\3\1\23\34\uffff\1\7\103"+
        "\uffff}>";
    static final String[] DFA30_transitionS = {
            "\1\51\1\50\2\uffff\1\50\22\uffff\1\51\1\27\1\45\1\30\1\24\1"+
            "\32\1\16\1\17\1\14\1\15\1\20\1\33\1\23\1\31\1\25\1\36\1\42\11"+
            "\43\1\22\1\35\1\41\1\26\1\40\1\34\1\21\32\47\4\uffff\1\47\1"+
            "\uffff\1\11\1\47\1\1\1\47\1\3\1\7\2\47\1\5\3\47\1\4\1\12\1\10"+
            "\2\47\1\46\1\2\1\6\3\47\1\13\2\47\3\uffff\1\37",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\16\54\1\52\13\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\24\54\1\57\5\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\27\54\1\60\2\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\62\23\54\1\61\5\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\63\14\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\64\6\54\1\65\1\54\3"+
            "\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\66\31\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\67\3\54\1\70\10\54"+
            "\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\71\14\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\73\15\54\1\72\13\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\16\54\1\74\13\54\3\uffff\1"+
            "\55",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\2\55"+
            "\32\101\4\uffff\1\101\1\uffff\32\101\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\106\2\55\1\uffff\1\55\1"+
            "\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\110\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\12\55\7\uffff\6\55\32\uffff\6\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\1\120\4\55\1\121\14\55\1\uffff\1"+
            "\55\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\124",
            "\1\126",
            "\1\55\1\uffff\5\55\2\uffff\4\55\1\133\1\55\12\132\2\55\1\uffff"+
            "\1\55\1\uffff\6\55\1\131\25\55\4\uffff\1\55\1\uffff\4\55\1\131"+
            "\25\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\4\55\1\133\1\55\12\134\2\55\1\uffff"+
            "\1\55\1\uffff\6\55\1\131\25\55\4\uffff\1\55\1\uffff\4\55\1\131"+
            "\25\55\3\uffff\1\55",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\135\25\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "",
            "\1\51\26\uffff\1\51\16\uffff\1\137",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\140\14\54\3\uffff\1"+
            "\55",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\17\54\1\141\12\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\142\6\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\143\7\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\144\6\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\24\54\1\146\5\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\17\54\1\147\12\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\13\54\1\150\16\54\3\uffff\1"+
            "\55",
            "\1\153\26\uffff\1\153\1\55\1\uffff\5\55\2\uffff\6\55\12\56"+
            "\2\55\1\uffff\1\55\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\5"+
            "\54\1\151\14\54\1\152\7\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\3\54\1\155\26\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\156\6\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\14\54\1\157\15\54\3\uffff\1"+
            "\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\160\10\54\3\uffff\1"+
            "\55",
            "",
            "",
            "",
            "",
            "\1\164\1\uffff\12\163\1\162\2\uffff\1\165\3\uffff\32\161\4"+
            "\uffff\1\161\1\uffff\32\161",
            "",
            "",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\106\2\55\1\uffff\1\55\1"+
            "\uffff\6\55\1\166\25\55\4\uffff\1\55\1\uffff\4\55\1\166\25\55"+
            "\3\uffff\1\55",
            "",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4\175"+
            "\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u0082\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u0081\4\u0085\1\u0081\1"+
            "\u0085\32\u0081\3\u0085\1\u0083\uff81\u0085",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0086\1\uffff\1\u0088\2\uffff\12\u0087",
            "\12\132\13\uffff\1\131\37\uffff\1\131",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u008a\2\55\1\uffff\1\55"+
            "\1\uffff\6\55\1\u0089\25\55\4\uffff\1\55\1\uffff\4\55\1\u0089"+
            "\25\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\4\55\1\133\1\55\12\134\2\55\1\uffff"+
            "\1\55\1\uffff\6\55\1\131\25\55\4\uffff\1\55\1\uffff\4\55\1\131"+
            "\25\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\5\54\1\u008b\24\54\3\uffff"+
            "\1\55",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u008c\7\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\17\54\1\u008d\12\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u008e\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\u008f\6\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\2\54\1\u0090\27\54\3\uffff"+
            "\1\55",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u0091\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u0092\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u0093\7\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\u0094\31\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\24\54\1\u0095\5\54\3\uffff"+
            "\1\55",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u0098\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\164\1\uffff\12\163\1\162\2\uffff\1\165\3\uffff\32\161\4"+
            "\uffff\1\161\1\uffff\32\161",
            "\32\u009a\4\uffff\1\u009a\1\uffff\32\u009a",
            "\1\164\1\uffff\12\163\1\162\2\uffff\1\165\3\uffff\32\161\4"+
            "\uffff\1\161\1\uffff\32\161",
            "\32\u009a\4\uffff\1\u009a\1\uffff\32\u009a",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u009d\4\u009b\1\u009d\1\u009b\32"+
            "\u009d\3\u009b\1\u009f\uff81\u009b",
            "\1\u00a1\1\uffff\1\u00a3\2\uffff\12\u00a2",
            "",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4\175"+
            "\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\u00a5\2\170\1\175\1\170\1\175\2\170\32\u00a4"+
            "\4\175\1\u00a4\1\175\32\u00a4\3\175\1\176\uff81\175",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4\175"+
            "\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\u00a6\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4"+
            "\175\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\60\175\12\u00a7\7\175\6\u00a7\32\175\6\u00a7\uff99\175",
            "",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4\175"+
            "\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u0082\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u0081\4\u0085\1\u0081\1"+
            "\u0085\32\u0081\3\u0085\1\u0083\uff81\u0085",
            "\1\uffff",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u00a9\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u00a8\4\u0085\1\u00a8\1"+
            "\u0085\32\u00a8\3\u0085\1\u0083\uff81\u0085",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u0082\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u0081\4\u0085\1\u0081\1"+
            "\u0085\32\u0081\3\u0085\1\u0083\uff81\u0085",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u0082\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u0081\4\u0085\1\u0081\1"+
            "\u0085\32\u0081\3\u0085\1\u0083\uff81\u0085",
            "\12\u00aa\7\uffff\6\u00aa\32\uffff\6\u00aa",
            "",
            "\12\u00ab",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u0087\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\12\u00ab",
            "\1\u00ac\1\uffff\1\u00ae\2\uffff\12\u00ad",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u008a\2\55\1\uffff\1\55"+
            "\1\uffff\6\55\1\u0089\25\55\4\uffff\1\55\1\uffff\4\55\1\u0089"+
            "\25\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\u00af"+
            "\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\u00b1\1\u00b0\5\54"+
            "\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\13\54\1\u00b2\16\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\u00b3\10\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00b4\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\7\54\1\u00b5\22\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\u00b7"+
            "\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00b8\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\10\54\1\u00b9\21\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\2\54\1\u00ba\27\54\3\uffff"+
            "\1\55",
            "",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\u00bb"+
            "\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "",
            "\1\164\1\uffff\12\u00bd\1\162\2\uffff\1\165\3\uffff\32\u00bc"+
            "\4\uffff\1\u00bc\1\uffff\32\u00bc",
            "",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u009d\4\u009b\1\u009d\1\u009b\32"+
            "\u009d\3\u009b\1\u009f\uff81\u009b",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u00bf\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u00be\4\u009b\1\u00be\1\u009b\32"+
            "\u00be\3\u009b\1\u009f\uff81\u009b",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u009d\4\u009b\1\u009d\1\u009b\32"+
            "\u009d\3\u009b\1\u009f\uff81\u009b",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u009d\4\u009b\1\u009d\1\u009b\32"+
            "\u009d\3\u009b\1\u009f\uff81\u009b",
            "\60\u009b\12\u00c0\7\u009b\6\u00c0\32\u009b\6\u00c0\uff99\u009b",
            "\12\u00c1",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u00a2\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\12\u00c1",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\u00a5\2\170\1\175\1\170\1\175\2\170\32\u00a4"+
            "\4\175\1\u00a4\1\175\32\u00a4\3\175\1\176\uff81\175",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\u00a5\2\170\1\175\1\170\1\175\2\170\32\u00a4"+
            "\4\175\1\u00a4\1\175\32\u00a4\3\175\1\176\uff81\175",
            "\1\176\1\uffff\2\170\1\174\1\170\1\176\2\uffff\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\uffff\1\170\1\uffff\2\170\32\171"+
            "\4\uffff\1\171\1\uffff\32\171\3\uffff\1\176",
            "\60\175\12\u00c2\7\175\6\u00c2\32\175\6\u00c2\uff99\175",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u00a9\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u00a8\4\u0085\1\u00a8\1"+
            "\u0085\32\u00a8\3\u0085\1\u0083\uff81\u0085",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u00a9\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u00a8\4\u0085\1\u00a8\1"+
            "\u0085\32\u00a8\3\u0085\1\u0083\uff81\u0085",
            "\12\u00c3\7\uffff\6\u00c3\32\uffff\6\u00c3",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u00ab\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\12\u00c4",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u00ad\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\12\u00c4",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00c8\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00c7\4\uffff\1\u00c7\1\uffff\32\u00c7\3"+
            "\uffff\1\u00c9",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\14\54\1\u00cb\15\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\u00cc\10\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\10\54\1\u00cd\21\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\u00ce\14\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\27\54\1\u00cf\2\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00d0\25\54\3\uffff"+
            "\1\55",
            "",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00d4\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00d3\4\uffff\1\u00d3\1\uffff\32\u00d3\3"+
            "\uffff\1\u00d5",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\13\54\1\u00d8\16\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\2\54\1\u00d9\27\54\3\uffff"+
            "\1\55",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00dd\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00dc\4\uffff\1\u00dc\1\uffff\32\u00dc\3"+
            "\uffff\1\u00de",
            "\1\164\1\uffff\12\u00bd\1\162\2\uffff\1\165\3\uffff\32\u00bc"+
            "\4\uffff\1\u00bc\1\uffff\32\u00bc",
            "\1\164\1\uffff\12\u00bd\1\162\2\uffff\1\165\3\uffff\32\u00bc"+
            "\4\uffff\1\u00bc\1\uffff\32\u00bc",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u00bf\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u00be\4\u009b\1\u00be\1\u009b\32"+
            "\u00be\3\u009b\1\u009f\uff81\u009b",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u00bf\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u00be\4\u009b\1\u00be\1\u009b\32"+
            "\u00be\3\u009b\1\u009f\uff81\u009b",
            "\60\u009b\12\u00e0\7\u009b\6\u00e0\32\u009b\6\u00e0\uff99\u009b",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u00c1\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\41\175\1\176\1\175\2\170\1\174\1\170\1\176\2\175\1\173\2\170"+
            "\2\176\1\170\12\172\2\170\1\175\1\170\1\175\2\170\32\171\4\175"+
            "\1\171\1\175\32\171\3\175\1\176\uff81\175",
            "\12\u0085\1\uffff\26\u0085\1\u0083\1\u0085\2\177\1\u0084\1"+
            "\177\1\u0083\2\u0085\1\u0083\2\177\2\u0083\1\177\12\u0082\2"+
            "\177\1\u0085\1\177\1\u0085\2\177\32\u0081\4\u0085\1\u0081\1"+
            "\u0085\32\u0081\3\u0085\1\u0083\uff81\u0085",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\u00c4\2\55\1\uffff\1\55"+
            "\1\uffff\34\55\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00c8\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00c7\4\uffff\1\u00c7\1\uffff\32\u00c7\3"+
            "\uffff\1\u00c9",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00e2\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00e1\4\uffff\1\u00e1\1\uffff\32\u00e1\3"+
            "\uffff\1\u00c9",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00c8\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00c7\4\uffff\1\u00c7\1\uffff\32\u00c7\3"+
            "\uffff\1\u00c9",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00c8\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00c7\4\uffff\1\u00c7\1\uffff\32\u00c7\3"+
            "\uffff\1\u00c9",
            "\12\u00e3\7\uffff\6\u00e3\32\uffff\6\u00e3",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00e4\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\u00e5\31\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00e6\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\1\u00e7\31\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\10\54\1\u00e8\21\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u00e9\7\54\3\uffff"+
            "\1\55",
            "",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00d4\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00d3\4\uffff\1\u00d3\1\uffff\32\u00d3\3"+
            "\uffff\1\u00d5",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00eb\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00ea\4\uffff\1\u00ea\1\uffff\32\u00ea\3"+
            "\uffff\1\u00d5",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00d4\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00d3\4\uffff\1\u00d3\1\uffff\32\u00d3\3"+
            "\uffff\1\u00d5",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00d4\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00d3\4\uffff\1\u00d3\1\uffff\32\u00d3\3"+
            "\uffff\1\u00d5",
            "\12\u00ec\7\uffff\6\u00ec\32\uffff\6\u00ec",
            "",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\24\54\1\u00ed\5\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u00ee\25\54\3\uffff"+
            "\1\55",
            "",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00dd\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00dc\4\uffff\1\u00dc\1\uffff\32\u00dc\3"+
            "\uffff\1\u00de",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00f0\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00ef\4\uffff\1\u00ef\1\uffff\32\u00ef\3"+
            "\uffff\1\u00de",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00dd\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00dc\4\uffff\1\u00dc\1\uffff\32\u00dc\3"+
            "\uffff\1\u00de",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00dd\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00dc\4\uffff\1\u00dc\1\uffff\32\u00dc\3"+
            "\uffff\1\u00de",
            "\12\u00f1\7\uffff\6\u00f1\32\uffff\6\u00f1",
            "\41\u009b\1\u009f\1\u009b\2\u009c\1\u00a0\1\u009c\1\u009f\2"+
            "\u009b\1\u009f\2\u009c\2\u009f\1\u009c\12\u009e\2\u009c\1\u009b"+
            "\1\u009c\1\u009b\2\u009c\32\u009d\4\u009b\1\u009d\1\u009b\32"+
            "\u009d\3\u009b\1\u009f\uff81\u009b",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00e2\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00e1\4\uffff\1\u00e1\1\uffff\32\u00e1\3"+
            "\uffff\1\u00c9",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00e2\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00e1\4\uffff\1\u00e1\1\uffff\32\u00e1\3"+
            "\uffff\1\u00c9",
            "\12\u00f2\7\uffff\6\u00f2\32\uffff\6\u00f2",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\u00f3\10\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\10\54\1\u00f4\21\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\u00f5\10\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\13\54\1\u00f6\16\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u00f7\7\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00eb\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00ea\4\uffff\1\u00ea\1\uffff\32\u00ea\3"+
            "\uffff\1\u00d5",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00eb\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00ea\4\uffff\1\u00ea\1\uffff\32\u00ea\3"+
            "\uffff\1\u00d5",
            "\12\u00f9\7\uffff\6\u00f9\32\uffff\6\u00f9",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\21\54\1\u00fa\10\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u00fb\7\54\3\uffff"+
            "\1\55",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00f0\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00ef\4\uffff\1\u00ef\1\uffff\32\u00ef\3"+
            "\uffff\1\u00de",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00f0\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00ef\4\uffff\1\u00ef\1\uffff\32\u00ef\3"+
            "\uffff\1\u00de",
            "\12\u00fc\7\uffff\6\u00fc\32\uffff\6\u00fc",
            "\1\u00c9\1\uffff\2\u00c6\1\u00ca\1\u00c6\1\u00c9\2\uffff\1"+
            "\u00c9\2\u00c6\2\u00c9\1\u00c6\12\u00c8\2\u00c6\1\uffff\1\u00c6"+
            "\1\uffff\2\u00c6\32\u00c7\4\uffff\1\u00c7\1\uffff\32\u00c7\3"+
            "\uffff\1\u00c9",
            "\1\55\1\uffff\5\55\2\uffff\3\55\1\u00fd\2\55\12\56\2\55\1\uffff"+
            "\1\55\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\15\54\1\u00fe\14\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\3\55\1\u00ff\2\55\12\56\2\55\1\uffff"+
            "\1\55\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\3\55\1\u0100\2\55\12\56\2\55\1\uffff"+
            "\1\55\1\uffff\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\u0101\6\54\3\uffff"+
            "\1\55",
            "",
            "\1\u00d5\1\uffff\2\u00d2\1\u00d6\1\u00d2\1\u00d5\2\uffff\1"+
            "\u00d5\2\u00d2\2\u00d5\1\u00d2\12\u00d4\2\u00d2\1\uffff\1\u00d2"+
            "\1\uffff\2\u00d2\32\u00d3\4\uffff\1\u00d3\1\uffff\32\u00d3\3"+
            "\uffff\1\u00d5",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\4\54\1\u0102\25\54\3\uffff"+
            "\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\22\54\1\u0103\7\54\3\uffff"+
            "\1\55",
            "\1\u00de\1\uffff\2\u00db\1\u00df\1\u00db\1\u00de\2\uffff\1"+
            "\u00de\2\u00db\2\u00de\1\u00db\12\u00dd\2\u00db\1\uffff\1\u00db"+
            "\1\uffff\2\u00db\32\u00dc\4\uffff\1\u00dc\1\uffff\32\u00dc\3"+
            "\uffff\1\u00de",
            "\1\u0104",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\23\54\1\u0105\6\54\3\uffff"+
            "\1\55",
            "\1\u0106",
            "\1\u0107",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\u010b",
            "\1\55\1\uffff\5\55\2\uffff\6\55\12\56\2\55\1\uffff\1\55\1\uffff"+
            "\2\55\32\54\4\uffff\1\54\1\uffff\32\54\3\uffff\1\55",
            "\1\u010d",
            "\1\u010e",
            "",
            "",
            "",
            "\1\u010f",
            "",
            "\1\u0110",
            "\1\u0111",
            "\1\u0112",
            "\1\u0113",
            "\1\u0114",
            "\1\u0115",
            "\1\u0116",
            "\1\u0117",
            "\1\u0118",
            "\1\u0119",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "\1\55\1\uffff\5\55\2\uffff\22\55\1\uffff\1\55\1\uffff\34\55"+
            "\4\uffff\1\55\1\uffff\32\55\3\uffff\1\55",
            "",
            "",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | And | Not | Or | XOr | LParen | RParen | Amper | Apostrophe | Asterisk | At | Colon | Comma | Dollar | Dot | Equal | Exclamation | Hash | Minus | NotEqual | Percent | Plus | Question | Semi | Slash | Tilde | Gt | Lt | Gte | Lte | IntegerLiteral | FloatingPointLiteral | StringLiteral | Trigger | ResourceNameAttribute | ResourceTypeAttribute | ResourceReferenceAttribute | Annotation | Identifier | Newline | Whitespace | BlockComment | LineComment | Uri );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_41 = input.LA(1);

                         
                        int index30_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA30_41=='\t'||LA30_41==' ') ) {s = 41;}

                        else if ( (LA30_41=='/') && ((( getCharPositionInLine() == 0 )||( getCharPositionInLine() >= 0 )))) {s = 95;}

                        else s = 94;

                         
                        input.seek(index30_41);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_159 = input.LA(1);

                        s = -1;
                        if ( ((LA30_159>='#' && LA30_159<='$')||LA30_159=='&'||(LA30_159>='+' && LA30_159<=',')||LA30_159=='/'||(LA30_159>=':' && LA30_159<=';')||LA30_159=='='||(LA30_159>='?' && LA30_159<='@')) ) {s = 156;}

                        else if ( ((LA30_159>='A' && LA30_159<='Z')||LA30_159=='_'||(LA30_159>='a' && LA30_159<='z')) ) {s = 157;}

                        else if ( ((LA30_159>='0' && LA30_159<='9')) ) {s = 158;}

                        else if ( (LA30_159=='!'||LA30_159=='\''||LA30_159=='*'||(LA30_159>='-' && LA30_159<='.')||LA30_159=='~') ) {s = 159;}

                        else if ( (LA30_159=='%') ) {s = 160;}

                        else if ( ((LA30_159>='\u0000' && LA30_159<=' ')||LA30_159=='\"'||(LA30_159>='(' && LA30_159<=')')||LA30_159=='<'||LA30_159=='>'||(LA30_159>='[' && LA30_159<='^')||LA30_159=='`'||(LA30_159>='{' && LA30_159<='}')||(LA30_159>='\u007F' && LA30_159<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA30_124 = input.LA(1);

                        s = -1;
                        if ( ((LA30_124>='0' && LA30_124<='9')||(LA30_124>='A' && LA30_124<='F')||(LA30_124>='a' && LA30_124<='f')) ) {s = 167;}

                        else if ( ((LA30_124>='\u0000' && LA30_124<='/')||(LA30_124>=':' && LA30_124<='@')||(LA30_124>='G' && LA30_124<='`')||(LA30_124>='g' && LA30_124<='\uFFFF')) ) {s = 125;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA30_194 = input.LA(1);

                        s = -1;
                        if ( ((LA30_194>='#' && LA30_194<='$')||LA30_194=='&'||(LA30_194>='+' && LA30_194<=',')||LA30_194=='/'||(LA30_194>=':' && LA30_194<=';')||LA30_194=='='||(LA30_194>='?' && LA30_194<='@')) ) {s = 120;}

                        else if ( ((LA30_194>='A' && LA30_194<='Z')||LA30_194=='_'||(LA30_194>='a' && LA30_194<='z')) ) {s = 121;}

                        else if ( ((LA30_194>='0' && LA30_194<='9')) ) {s = 122;}

                        else if ( (LA30_194=='*') ) {s = 123;}

                        else if ( (LA30_194=='%') ) {s = 124;}

                        else if ( (LA30_194=='!'||LA30_194=='\''||(LA30_194>='-' && LA30_194<='.')||LA30_194=='~') ) {s = 126;}

                        else if ( ((LA30_194>='\u0000' && LA30_194<=' ')||LA30_194=='\"'||(LA30_194>='(' && LA30_194<=')')||LA30_194=='<'||LA30_194=='>'||(LA30_194>='[' && LA30_194<='^')||LA30_194=='`'||(LA30_194>='{' && LA30_194<='}')||(LA30_194>='\u007F' && LA30_194<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA30_168 = input.LA(1);

                         
                        int index30_168 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_168>='A' && LA30_168<='Z')||LA30_168=='_'||(LA30_168>='a' && LA30_168<='z')) ) {s = 168;}

                        else if ( ((LA30_168>='#' && LA30_168<='$')||LA30_168=='&'||(LA30_168>='+' && LA30_168<=',')||LA30_168=='/'||(LA30_168>=':' && LA30_168<=';')||LA30_168=='='||(LA30_168>='?' && LA30_168<='@')) ) {s = 127;}

                        else if ( (LA30_168=='!'||LA30_168=='\''||LA30_168=='*'||(LA30_168>='-' && LA30_168<='.')||LA30_168=='~') ) {s = 131;}

                        else if ( ((LA30_168>='0' && LA30_168<='9')) ) {s = 169;}

                        else if ( (LA30_168=='%') ) {s = 132;}

                        else if ( ((LA30_168>='\u0000' && LA30_168<='\t')||(LA30_168>='\u000B' && LA30_168<=' ')||LA30_168=='\"'||(LA30_168>='(' && LA30_168<=')')||LA30_168=='<'||LA30_168=='>'||(LA30_168>='[' && LA30_168<='^')||LA30_168=='`'||(LA30_168>='{' && LA30_168<='}')||(LA30_168>='\u007F' && LA30_168<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_168);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA30_123 = input.LA(1);

                        s = -1;
                        if ( (LA30_123=='/') ) {s = 166;}

                        else if ( ((LA30_123>='#' && LA30_123<='$')||LA30_123=='&'||(LA30_123>='+' && LA30_123<=',')||(LA30_123>=':' && LA30_123<=';')||LA30_123=='='||(LA30_123>='?' && LA30_123<='@')) ) {s = 120;}

                        else if ( ((LA30_123>='A' && LA30_123<='Z')||LA30_123=='_'||(LA30_123>='a' && LA30_123<='z')) ) {s = 121;}

                        else if ( ((LA30_123>='0' && LA30_123<='9')) ) {s = 122;}

                        else if ( (LA30_123=='*') ) {s = 123;}

                        else if ( (LA30_123=='%') ) {s = 124;}

                        else if ( (LA30_123=='!'||LA30_123=='\''||(LA30_123>='-' && LA30_123<='.')||LA30_123=='~') ) {s = 126;}

                        else if ( ((LA30_123>='\u0000' && LA30_123<=' ')||LA30_123=='\"'||(LA30_123>='(' && LA30_123<=')')||LA30_123=='<'||LA30_123=='>'||(LA30_123>='[' && LA30_123<='^')||LA30_123=='`'||(LA30_123>='{' && LA30_123<='}')||(LA30_123>='\u007F' && LA30_123<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA30_127 = input.LA(1);

                         
                        int index30_127 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_127>='#' && LA30_127<='$')||LA30_127=='&'||(LA30_127>='+' && LA30_127<=',')||LA30_127=='/'||(LA30_127>=':' && LA30_127<=';')||LA30_127=='='||(LA30_127>='?' && LA30_127<='@')) ) {s = 127;}

                        else if ( ((LA30_127>='A' && LA30_127<='Z')||LA30_127=='_'||(LA30_127>='a' && LA30_127<='z')) ) {s = 129;}

                        else if ( ((LA30_127>='0' && LA30_127<='9')) ) {s = 130;}

                        else if ( (LA30_127=='!'||LA30_127=='\''||LA30_127=='*'||(LA30_127>='-' && LA30_127<='.')||LA30_127=='~') ) {s = 131;}

                        else if ( (LA30_127=='%') ) {s = 132;}

                        else if ( ((LA30_127>='\u0000' && LA30_127<='\t')||(LA30_127>='\u000B' && LA30_127<=' ')||LA30_127=='\"'||(LA30_127>='(' && LA30_127<=')')||LA30_127=='<'||LA30_127=='>'||(LA30_127>='[' && LA30_127<='^')||LA30_127=='`'||(LA30_127>='{' && LA30_127<='}')||(LA30_127>='\u007F' && LA30_127<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_127);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA30_224 = input.LA(1);

                        s = -1;
                        if ( ((LA30_224>='#' && LA30_224<='$')||LA30_224=='&'||(LA30_224>='+' && LA30_224<=',')||LA30_224=='/'||(LA30_224>=':' && LA30_224<=';')||LA30_224=='='||(LA30_224>='?' && LA30_224<='@')) ) {s = 156;}

                        else if ( ((LA30_224>='A' && LA30_224<='Z')||LA30_224=='_'||(LA30_224>='a' && LA30_224<='z')) ) {s = 157;}

                        else if ( ((LA30_224>='0' && LA30_224<='9')) ) {s = 158;}

                        else if ( (LA30_224=='!'||LA30_224=='\''||LA30_224=='*'||(LA30_224>='-' && LA30_224<='.')||LA30_224=='~') ) {s = 159;}

                        else if ( (LA30_224=='%') ) {s = 160;}

                        else if ( ((LA30_224>='\u0000' && LA30_224<=' ')||LA30_224=='\"'||(LA30_224>='(' && LA30_224<=')')||LA30_224=='<'||LA30_224=='>'||(LA30_224>='[' && LA30_224<='^')||LA30_224=='`'||(LA30_224>='{' && LA30_224<='}')||(LA30_224>='\u007F' && LA30_224<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA30_121 = input.LA(1);

                        s = -1;
                        if ( (LA30_121=='*') ) {s = 123;}

                        else if ( ((LA30_121>='A' && LA30_121<='Z')||LA30_121=='_'||(LA30_121>='a' && LA30_121<='z')) ) {s = 164;}

                        else if ( ((LA30_121>='#' && LA30_121<='$')||LA30_121=='&'||(LA30_121>='+' && LA30_121<=',')||LA30_121=='/'||(LA30_121>=':' && LA30_121<=';')||LA30_121=='='||(LA30_121>='?' && LA30_121<='@')) ) {s = 120;}

                        else if ( (LA30_121=='!'||LA30_121=='\''||(LA30_121>='-' && LA30_121<='.')||LA30_121=='~') ) {s = 126;}

                        else if ( ((LA30_121>='0' && LA30_121<='9')) ) {s = 165;}

                        else if ( (LA30_121=='%') ) {s = 124;}

                        else if ( ((LA30_121>='\u0000' && LA30_121<=' ')||LA30_121=='\"'||(LA30_121>='(' && LA30_121<=')')||LA30_121=='<'||LA30_121=='>'||(LA30_121>='[' && LA30_121<='^')||LA30_121=='`'||(LA30_121>='{' && LA30_121<='}')||(LA30_121>='\u007F' && LA30_121<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA30_126 = input.LA(1);

                        s = -1;
                        if ( ((LA30_126>='#' && LA30_126<='$')||LA30_126=='&'||(LA30_126>='+' && LA30_126<=',')||LA30_126=='/'||(LA30_126>=':' && LA30_126<=';')||LA30_126=='='||(LA30_126>='?' && LA30_126<='@')) ) {s = 120;}

                        else if ( ((LA30_126>='A' && LA30_126<='Z')||LA30_126=='_'||(LA30_126>='a' && LA30_126<='z')) ) {s = 121;}

                        else if ( ((LA30_126>='0' && LA30_126<='9')) ) {s = 122;}

                        else if ( (LA30_126=='*') ) {s = 123;}

                        else if ( (LA30_126=='%') ) {s = 124;}

                        else if ( (LA30_126=='!'||LA30_126=='\''||(LA30_126>='-' && LA30_126<='.')||LA30_126=='~') ) {s = 126;}

                        else if ( ((LA30_126>='\u0000' && LA30_126<=' ')||LA30_126=='\"'||(LA30_126>='(' && LA30_126<=')')||LA30_126=='<'||LA30_126=='>'||(LA30_126>='[' && LA30_126<='^')||LA30_126=='`'||(LA30_126>='{' && LA30_126<='}')||(LA30_126>='\u007F' && LA30_126<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA30_170 = input.LA(1);

                         
                        int index30_170 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_170>='0' && LA30_170<='9')||(LA30_170>='A' && LA30_170<='F')||(LA30_170>='a' && LA30_170<='f')) ) {s = 195;}

                        else s = 133;

                         
                        input.seek(index30_170);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA30_128 = input.LA(1);

                         
                        int index30_128 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (( getCharPositionInLine() == 0 )) ) {s = 133;}

                        else if ( (true) ) {s = 45;}

                         
                        input.seek(index30_128);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA30_191 = input.LA(1);

                        s = -1;
                        if ( ((LA30_191>='#' && LA30_191<='$')||LA30_191=='&'||(LA30_191>='+' && LA30_191<=',')||LA30_191=='/'||(LA30_191>=':' && LA30_191<=';')||LA30_191=='='||(LA30_191>='?' && LA30_191<='@')) ) {s = 156;}

                        else if ( ((LA30_191>='A' && LA30_191<='Z')||LA30_191=='_'||(LA30_191>='a' && LA30_191<='z')) ) {s = 190;}

                        else if ( ((LA30_191>='0' && LA30_191<='9')) ) {s = 191;}

                        else if ( (LA30_191=='!'||LA30_191=='\''||LA30_191=='*'||(LA30_191>='-' && LA30_191<='.')||LA30_191=='~') ) {s = 159;}

                        else if ( (LA30_191=='%') ) {s = 160;}

                        else if ( ((LA30_191>='\u0000' && LA30_191<=' ')||LA30_191=='\"'||(LA30_191>='(' && LA30_191<=')')||LA30_191=='<'||LA30_191=='>'||(LA30_191>='[' && LA30_191<='^')||LA30_191=='`'||(LA30_191>='{' && LA30_191<='}')||(LA30_191>='\u007F' && LA30_191<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA30_165 = input.LA(1);

                        s = -1;
                        if ( ((LA30_165>='#' && LA30_165<='$')||LA30_165=='&'||(LA30_165>='+' && LA30_165<=',')||LA30_165=='/'||(LA30_165>=':' && LA30_165<=';')||LA30_165=='='||(LA30_165>='?' && LA30_165<='@')) ) {s = 120;}

                        else if ( ((LA30_165>='A' && LA30_165<='Z')||LA30_165=='_'||(LA30_165>='a' && LA30_165<='z')) ) {s = 164;}

                        else if ( ((LA30_165>='0' && LA30_165<='9')) ) {s = 165;}

                        else if ( (LA30_165=='*') ) {s = 123;}

                        else if ( (LA30_165=='%') ) {s = 124;}

                        else if ( (LA30_165=='!'||LA30_165=='\''||(LA30_165>='-' && LA30_165<='.')||LA30_165=='~') ) {s = 126;}

                        else if ( ((LA30_165>='\u0000' && LA30_165<=' ')||LA30_165=='\"'||(LA30_165>='(' && LA30_165<=')')||LA30_165=='<'||LA30_165=='>'||(LA30_165>='[' && LA30_165<='^')||LA30_165=='`'||(LA30_165>='{' && LA30_165<='}')||(LA30_165>='\u007F' && LA30_165<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA30_156 = input.LA(1);

                        s = -1;
                        if ( ((LA30_156>='#' && LA30_156<='$')||LA30_156=='&'||(LA30_156>='+' && LA30_156<=',')||LA30_156=='/'||(LA30_156>=':' && LA30_156<=';')||LA30_156=='='||(LA30_156>='?' && LA30_156<='@')) ) {s = 156;}

                        else if ( ((LA30_156>='A' && LA30_156<='Z')||LA30_156=='_'||(LA30_156>='a' && LA30_156<='z')) ) {s = 157;}

                        else if ( ((LA30_156>='0' && LA30_156<='9')) ) {s = 158;}

                        else if ( (LA30_156=='!'||LA30_156=='\''||LA30_156=='*'||(LA30_156>='-' && LA30_156<='.')||LA30_156=='~') ) {s = 159;}

                        else if ( (LA30_156=='%') ) {s = 160;}

                        else if ( ((LA30_156>='\u0000' && LA30_156<=' ')||LA30_156=='\"'||(LA30_156>='(' && LA30_156<=')')||LA30_156=='<'||LA30_156=='>'||(LA30_156>='[' && LA30_156<='^')||LA30_156=='`'||(LA30_156>='{' && LA30_156<='}')||(LA30_156>='\u007F' && LA30_156<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA30_160 = input.LA(1);

                        s = -1;
                        if ( ((LA30_160>='0' && LA30_160<='9')||(LA30_160>='A' && LA30_160<='F')||(LA30_160>='a' && LA30_160<='f')) ) {s = 192;}

                        else if ( ((LA30_160>='\u0000' && LA30_160<='/')||(LA30_160>=':' && LA30_160<='@')||(LA30_160>='G' && LA30_160<='`')||(LA30_160>='g' && LA30_160<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA30_131 = input.LA(1);

                         
                        int index30_131 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_131>='#' && LA30_131<='$')||LA30_131=='&'||(LA30_131>='+' && LA30_131<=',')||LA30_131=='/'||(LA30_131>=':' && LA30_131<=';')||LA30_131=='='||(LA30_131>='?' && LA30_131<='@')) ) {s = 127;}

                        else if ( ((LA30_131>='A' && LA30_131<='Z')||LA30_131=='_'||(LA30_131>='a' && LA30_131<='z')) ) {s = 129;}

                        else if ( ((LA30_131>='0' && LA30_131<='9')) ) {s = 130;}

                        else if ( (LA30_131=='!'||LA30_131=='\''||LA30_131=='*'||(LA30_131>='-' && LA30_131<='.')||LA30_131=='~') ) {s = 131;}

                        else if ( (LA30_131=='%') ) {s = 132;}

                        else if ( ((LA30_131>='\u0000' && LA30_131<='\t')||(LA30_131>='\u000B' && LA30_131<=' ')||LA30_131=='\"'||(LA30_131>='(' && LA30_131<=')')||LA30_131=='<'||LA30_131=='>'||(LA30_131>='[' && LA30_131<='^')||LA30_131=='`'||(LA30_131>='{' && LA30_131<='}')||(LA30_131>='\u007F' && LA30_131<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_131);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA30_190 = input.LA(1);

                        s = -1;
                        if ( ((LA30_190>='A' && LA30_190<='Z')||LA30_190=='_'||(LA30_190>='a' && LA30_190<='z')) ) {s = 190;}

                        else if ( ((LA30_190>='#' && LA30_190<='$')||LA30_190=='&'||(LA30_190>='+' && LA30_190<=',')||LA30_190=='/'||(LA30_190>=':' && LA30_190<=';')||LA30_190=='='||(LA30_190>='?' && LA30_190<='@')) ) {s = 156;}

                        else if ( ((LA30_190>='0' && LA30_190<='9')) ) {s = 191;}

                        else if ( (LA30_190=='!'||LA30_190=='\''||LA30_190=='*'||(LA30_190>='-' && LA30_190<='.')||LA30_190=='~') ) {s = 159;}

                        else if ( (LA30_190=='%') ) {s = 160;}

                        else if ( ((LA30_190>='\u0000' && LA30_190<=' ')||LA30_190=='\"'||(LA30_190>='(' && LA30_190<=')')||LA30_190=='<'||LA30_190=='>'||(LA30_190>='[' && LA30_190<='^')||LA30_190=='`'||(LA30_190>='{' && LA30_190<='}')||(LA30_190>='\u007F' && LA30_190<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA30_169 = input.LA(1);

                         
                        int index30_169 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_169>='#' && LA30_169<='$')||LA30_169=='&'||(LA30_169>='+' && LA30_169<=',')||LA30_169=='/'||(LA30_169>=':' && LA30_169<=';')||LA30_169=='='||(LA30_169>='?' && LA30_169<='@')) ) {s = 127;}

                        else if ( ((LA30_169>='A' && LA30_169<='Z')||LA30_169=='_'||(LA30_169>='a' && LA30_169<='z')) ) {s = 168;}

                        else if ( ((LA30_169>='0' && LA30_169<='9')) ) {s = 169;}

                        else if ( (LA30_169=='!'||LA30_169=='\''||LA30_169=='*'||(LA30_169>='-' && LA30_169<='.')||LA30_169=='~') ) {s = 131;}

                        else if ( (LA30_169=='%') ) {s = 132;}

                        else if ( ((LA30_169>='\u0000' && LA30_169<='\t')||(LA30_169>='\u000B' && LA30_169<=' ')||LA30_169=='\"'||(LA30_169>='(' && LA30_169<=')')||LA30_169=='<'||LA30_169=='>'||(LA30_169>='[' && LA30_169<='^')||LA30_169=='`'||(LA30_169>='{' && LA30_169<='}')||(LA30_169>='\u007F' && LA30_169<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_169);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA30_195 = input.LA(1);

                         
                        int index30_195 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_195>='#' && LA30_195<='$')||LA30_195=='&'||(LA30_195>='+' && LA30_195<=',')||LA30_195=='/'||(LA30_195>=':' && LA30_195<=';')||LA30_195=='='||(LA30_195>='?' && LA30_195<='@')) ) {s = 127;}

                        else if ( ((LA30_195>='A' && LA30_195<='Z')||LA30_195=='_'||(LA30_195>='a' && LA30_195<='z')) ) {s = 129;}

                        else if ( ((LA30_195>='0' && LA30_195<='9')) ) {s = 130;}

                        else if ( (LA30_195=='!'||LA30_195=='\''||LA30_195=='*'||(LA30_195>='-' && LA30_195<='.')||LA30_195=='~') ) {s = 131;}

                        else if ( (LA30_195=='%') ) {s = 132;}

                        else if ( ((LA30_195>='\u0000' && LA30_195<='\t')||(LA30_195>='\u000B' && LA30_195<=' ')||LA30_195=='\"'||(LA30_195>='(' && LA30_195<=')')||LA30_195=='<'||LA30_195=='>'||(LA30_195>='[' && LA30_195<='^')||LA30_195=='`'||(LA30_195>='{' && LA30_195<='}')||(LA30_195>='\u007F' && LA30_195<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_195);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA30_120 = input.LA(1);

                        s = -1;
                        if ( ((LA30_120>='#' && LA30_120<='$')||LA30_120=='&'||(LA30_120>='+' && LA30_120<=',')||LA30_120=='/'||(LA30_120>=':' && LA30_120<=';')||LA30_120=='='||(LA30_120>='?' && LA30_120<='@')) ) {s = 120;}

                        else if ( ((LA30_120>='A' && LA30_120<='Z')||LA30_120=='_'||(LA30_120>='a' && LA30_120<='z')) ) {s = 121;}

                        else if ( ((LA30_120>='0' && LA30_120<='9')) ) {s = 122;}

                        else if ( (LA30_120=='*') ) {s = 123;}

                        else if ( (LA30_120=='%') ) {s = 124;}

                        else if ( (LA30_120=='!'||LA30_120=='\''||(LA30_120>='-' && LA30_120<='.')||LA30_120=='~') ) {s = 126;}

                        else if ( ((LA30_120>='\u0000' && LA30_120<=' ')||LA30_120=='\"'||(LA30_120>='(' && LA30_120<=')')||LA30_120=='<'||LA30_120=='>'||(LA30_120>='[' && LA30_120<='^')||LA30_120=='`'||(LA30_120>='{' && LA30_120<='}')||(LA30_120>='\u007F' && LA30_120<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA30_80 = input.LA(1);

                        s = -1;
                        if ( ((LA30_80>='#' && LA30_80<='$')||LA30_80=='&'||(LA30_80>='+' && LA30_80<=',')||LA30_80=='/'||(LA30_80>=':' && LA30_80<=';')||LA30_80=='='||(LA30_80>='?' && LA30_80<='@')) ) {s = 120;}

                        else if ( ((LA30_80>='A' && LA30_80<='Z')||LA30_80=='_'||(LA30_80>='a' && LA30_80<='z')) ) {s = 121;}

                        else if ( ((LA30_80>='0' && LA30_80<='9')) ) {s = 122;}

                        else if ( (LA30_80=='*') ) {s = 123;}

                        else if ( (LA30_80=='%') ) {s = 124;}

                        else if ( ((LA30_80>='\u0000' && LA30_80<=' ')||LA30_80=='\"'||(LA30_80>='(' && LA30_80<=')')||LA30_80=='<'||LA30_80=='>'||(LA30_80>='[' && LA30_80<='^')||LA30_80=='`'||(LA30_80>='{' && LA30_80<='}')||(LA30_80>='\u007F' && LA30_80<='\uFFFF')) ) {s = 125;}

                        else if ( (LA30_80=='!'||LA30_80=='\''||(LA30_80>='-' && LA30_80<='.')||LA30_80=='~') ) {s = 126;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA30_164 = input.LA(1);

                        s = -1;
                        if ( (LA30_164=='*') ) {s = 123;}

                        else if ( ((LA30_164>='A' && LA30_164<='Z')||LA30_164=='_'||(LA30_164>='a' && LA30_164<='z')) ) {s = 164;}

                        else if ( ((LA30_164>='#' && LA30_164<='$')||LA30_164=='&'||(LA30_164>='+' && LA30_164<=',')||LA30_164=='/'||(LA30_164>=':' && LA30_164<=';')||LA30_164=='='||(LA30_164>='?' && LA30_164<='@')) ) {s = 120;}

                        else if ( (LA30_164=='!'||LA30_164=='\''||(LA30_164>='-' && LA30_164<='.')||LA30_164=='~') ) {s = 126;}

                        else if ( ((LA30_164>='0' && LA30_164<='9')) ) {s = 165;}

                        else if ( (LA30_164=='%') ) {s = 124;}

                        else if ( ((LA30_164>='\u0000' && LA30_164<=' ')||LA30_164=='\"'||(LA30_164>='(' && LA30_164<=')')||LA30_164=='<'||LA30_164=='>'||(LA30_164>='[' && LA30_164<='^')||LA30_164=='`'||(LA30_164>='{' && LA30_164<='}')||(LA30_164>='\u007F' && LA30_164<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA30_132 = input.LA(1);

                         
                        int index30_132 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_132>='0' && LA30_132<='9')||(LA30_132>='A' && LA30_132<='F')||(LA30_132>='a' && LA30_132<='f')) ) {s = 170;}

                        else s = 133;

                         
                        input.seek(index30_132);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA30_122 = input.LA(1);

                        s = -1;
                        if ( ((LA30_122>='#' && LA30_122<='$')||LA30_122=='&'||(LA30_122>='+' && LA30_122<=',')||LA30_122=='/'||(LA30_122>=':' && LA30_122<=';')||LA30_122=='='||(LA30_122>='?' && LA30_122<='@')) ) {s = 120;}

                        else if ( ((LA30_122>='A' && LA30_122<='Z')||LA30_122=='_'||(LA30_122>='a' && LA30_122<='z')) ) {s = 121;}

                        else if ( ((LA30_122>='0' && LA30_122<='9')) ) {s = 122;}

                        else if ( (LA30_122=='*') ) {s = 123;}

                        else if ( (LA30_122=='%') ) {s = 124;}

                        else if ( (LA30_122=='!'||LA30_122=='\''||(LA30_122>='-' && LA30_122<='.')||LA30_122=='~') ) {s = 126;}

                        else if ( ((LA30_122>='\u0000' && LA30_122<=' ')||LA30_122=='\"'||(LA30_122>='(' && LA30_122<=')')||LA30_122=='<'||LA30_122=='>'||(LA30_122>='[' && LA30_122<='^')||LA30_122=='`'||(LA30_122>='{' && LA30_122<='}')||(LA30_122>='\u007F' && LA30_122<='\uFFFF')) ) {s = 125;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA30_167 = input.LA(1);

                        s = -1;
                        if ( ((LA30_167>='\u0000' && LA30_167<='/')||(LA30_167>=':' && LA30_167<='@')||(LA30_167>='G' && LA30_167<='`')||(LA30_167>='g' && LA30_167<='\uFFFF')) ) {s = 125;}

                        else if ( ((LA30_167>='0' && LA30_167<='9')||(LA30_167>='A' && LA30_167<='F')||(LA30_167>='a' && LA30_167<='f')) ) {s = 194;}

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA30_158 = input.LA(1);

                        s = -1;
                        if ( ((LA30_158>='#' && LA30_158<='$')||LA30_158=='&'||(LA30_158>='+' && LA30_158<=',')||LA30_158=='/'||(LA30_158>=':' && LA30_158<=';')||LA30_158=='='||(LA30_158>='?' && LA30_158<='@')) ) {s = 156;}

                        else if ( ((LA30_158>='A' && LA30_158<='Z')||LA30_158=='_'||(LA30_158>='a' && LA30_158<='z')) ) {s = 157;}

                        else if ( ((LA30_158>='0' && LA30_158<='9')) ) {s = 158;}

                        else if ( (LA30_158=='!'||LA30_158=='\''||LA30_158=='*'||(LA30_158>='-' && LA30_158<='.')||LA30_158=='~') ) {s = 159;}

                        else if ( (LA30_158=='%') ) {s = 160;}

                        else if ( ((LA30_158>='\u0000' && LA30_158<=' ')||LA30_158=='\"'||(LA30_158>='(' && LA30_158<=')')||LA30_158=='<'||LA30_158=='>'||(LA30_158>='[' && LA30_158<='^')||LA30_158=='`'||(LA30_158>='{' && LA30_158<='}')||(LA30_158>='\u007F' && LA30_158<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA30_157 = input.LA(1);

                        s = -1;
                        if ( ((LA30_157>='A' && LA30_157<='Z')||LA30_157=='_'||(LA30_157>='a' && LA30_157<='z')) ) {s = 190;}

                        else if ( ((LA30_157>='#' && LA30_157<='$')||LA30_157=='&'||(LA30_157>='+' && LA30_157<=',')||LA30_157=='/'||(LA30_157>=':' && LA30_157<=';')||LA30_157=='='||(LA30_157>='?' && LA30_157<='@')) ) {s = 156;}

                        else if ( ((LA30_157>='0' && LA30_157<='9')) ) {s = 191;}

                        else if ( (LA30_157=='!'||LA30_157=='\''||LA30_157=='*'||(LA30_157>='-' && LA30_157<='.')||LA30_157=='~') ) {s = 159;}

                        else if ( (LA30_157=='%') ) {s = 160;}

                        else if ( ((LA30_157>='\u0000' && LA30_157<=' ')||LA30_157=='\"'||(LA30_157>='(' && LA30_157<=')')||LA30_157=='<'||LA30_157=='>'||(LA30_157>='[' && LA30_157<='^')||LA30_157=='`'||(LA30_157>='{' && LA30_157<='}')||(LA30_157>='\u007F' && LA30_157<='\uFFFF')) ) {s = 155;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA30_81 = input.LA(1);

                         
                        int index30_81 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_81>='#' && LA30_81<='$')||LA30_81=='&'||(LA30_81>='+' && LA30_81<=',')||LA30_81=='/'||(LA30_81>=':' && LA30_81<=';')||LA30_81=='='||(LA30_81>='?' && LA30_81<='@')) ) {s = 127;}

                        else if ( ((LA30_81>='A' && LA30_81<='Z')||LA30_81=='_'||(LA30_81>='a' && LA30_81<='z')) ) {s = 129;}

                        else if ( ((LA30_81>='0' && LA30_81<='9')) ) {s = 130;}

                        else if ( (LA30_81=='!'||LA30_81=='\''||LA30_81=='*'||(LA30_81>='-' && LA30_81<='.')||LA30_81=='~') ) {s = 131;}

                        else if ( (LA30_81=='%') ) {s = 132;}

                        else if ( ((LA30_81>='\u0000' && LA30_81<='\t')||(LA30_81>='\u000B' && LA30_81<=' ')||LA30_81=='\"'||(LA30_81>='(' && LA30_81<=')')||LA30_81=='<'||LA30_81=='>'||(LA30_81>='[' && LA30_81<='^')||LA30_81=='`'||(LA30_81>='{' && LA30_81<='}')||(LA30_81>='\u007F' && LA30_81<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_81);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA30_130 = input.LA(1);

                         
                        int index30_130 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_130>='#' && LA30_130<='$')||LA30_130=='&'||(LA30_130>='+' && LA30_130<=',')||LA30_130=='/'||(LA30_130>=':' && LA30_130<=';')||LA30_130=='='||(LA30_130>='?' && LA30_130<='@')) ) {s = 127;}

                        else if ( ((LA30_130>='A' && LA30_130<='Z')||LA30_130=='_'||(LA30_130>='a' && LA30_130<='z')) ) {s = 129;}

                        else if ( ((LA30_130>='0' && LA30_130<='9')) ) {s = 130;}

                        else if ( (LA30_130=='!'||LA30_130=='\''||LA30_130=='*'||(LA30_130>='-' && LA30_130<='.')||LA30_130=='~') ) {s = 131;}

                        else if ( (LA30_130=='%') ) {s = 132;}

                        else if ( ((LA30_130>='\u0000' && LA30_130<='\t')||(LA30_130>='\u000B' && LA30_130<=' ')||LA30_130=='\"'||(LA30_130>='(' && LA30_130<=')')||LA30_130=='<'||LA30_130=='>'||(LA30_130>='[' && LA30_130<='^')||LA30_130=='`'||(LA30_130>='{' && LA30_130<='}')||(LA30_130>='\u007F' && LA30_130<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_130);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA30_192 = input.LA(1);

                        s = -1;
                        if ( ((LA30_192>='0' && LA30_192<='9')||(LA30_192>='A' && LA30_192<='F')||(LA30_192>='a' && LA30_192<='f')) ) {s = 224;}

                        else if ( ((LA30_192>='\u0000' && LA30_192<='/')||(LA30_192>=':' && LA30_192<='@')||(LA30_192>='G' && LA30_192<='`')||(LA30_192>='g' && LA30_192<='\uFFFF')) ) {s = 155;}

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA30_129 = input.LA(1);

                         
                        int index30_129 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA30_129>='A' && LA30_129<='Z')||LA30_129=='_'||(LA30_129>='a' && LA30_129<='z')) ) {s = 168;}

                        else if ( ((LA30_129>='#' && LA30_129<='$')||LA30_129=='&'||(LA30_129>='+' && LA30_129<=',')||LA30_129=='/'||(LA30_129>=':' && LA30_129<=';')||LA30_129=='='||(LA30_129>='?' && LA30_129<='@')) ) {s = 127;}

                        else if ( (LA30_129=='!'||LA30_129=='\''||LA30_129=='*'||(LA30_129>='-' && LA30_129<='.')||LA30_129=='~') ) {s = 131;}

                        else if ( ((LA30_129>='0' && LA30_129<='9')) ) {s = 169;}

                        else if ( (LA30_129=='%') ) {s = 132;}

                        else if ( ((LA30_129>='\u0000' && LA30_129<='\t')||(LA30_129>='\u000B' && LA30_129<=' ')||LA30_129=='\"'||(LA30_129>='(' && LA30_129<=')')||LA30_129=='<'||LA30_129=='>'||(LA30_129>='[' && LA30_129<='^')||LA30_129=='`'||(LA30_129>='{' && LA30_129<='}')||(LA30_129>='\u007F' && LA30_129<='\uFFFF')) && (( getCharPositionInLine() == 0 ))) {s = 133;}

                        else s = 128;

                         
                        input.seek(index30_129);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA30_117 = input.LA(1);

                        s = -1;
                        if ( ((LA30_117>='\u0000' && LA30_117<=' ')||LA30_117=='\"'||(LA30_117>='(' && LA30_117<=')')||LA30_117=='<'||LA30_117=='>'||(LA30_117>='[' && LA30_117<='^')||LA30_117=='`'||(LA30_117>='{' && LA30_117<='}')||(LA30_117>='\u007F' && LA30_117<='\uFFFF')) ) {s = 155;}

                        else if ( ((LA30_117>='#' && LA30_117<='$')||LA30_117=='&'||(LA30_117>='+' && LA30_117<=',')||LA30_117=='/'||(LA30_117>=':' && LA30_117<=';')||LA30_117=='='||(LA30_117>='?' && LA30_117<='@')) ) {s = 156;}

                        else if ( ((LA30_117>='A' && LA30_117<='Z')||LA30_117=='_'||(LA30_117>='a' && LA30_117<='z')) ) {s = 157;}

                        else if ( ((LA30_117>='0' && LA30_117<='9')) ) {s = 158;}

                        else if ( (LA30_117=='!'||LA30_117=='\''||LA30_117=='*'||(LA30_117>='-' && LA30_117<='.')||LA30_117=='~') ) {s = 159;}

                        else if ( (LA30_117=='%') ) {s = 160;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}