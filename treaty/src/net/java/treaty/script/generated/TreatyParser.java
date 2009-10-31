// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-10-31 13:11:30

package net.java.treaty.script.generated;

import java.net.URI;

import net.java.treaty.*;
import net.java.treaty.script.TreatyRecognitionException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/** 
 * A Treaty script grammar for ANTLRv3. 
 */
public class TreatyParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Newline", "AnnotationKey", "AnnotationValue", "Trigger", "Identifier", "NameAttribute", "ResourceTypeAttribute", "ResourceReferenceAttribute", "Or", "XOr", "And", "Not", "LParen", "RParen", "Uri", "Amper", "Apostrophe", "Asterisk", "At", "Colon", "Comma", "Dollar", "Dot", "Equals", "Exclamation", "Hash", "Minus", "Percent", "Plus", "Question", "Semi", "Slash", "Tilde", "Whitespace", "String", "Annotation", "NamespaceDelimiter", "IDLetter", "IDDigit", "BlockComment", "LineComment", "UriCharacter", "UriReserved", "UriUnescaped", "UriEscaped", "UriAlpha", "DecimalDigit", "UriMark", "HexDigit", "'consumer-resource'", "'supplier-resource'", "'constraint'", "'mustexist'", "'onfailure'", "'onsuccess'"
    };
    public static final int NameAttribute=9;
    public static final int Hash=29;
    public static final int LineComment=44;
    public static final int Newline=4;
    public static final int AnnotationValue=6;
    public static final int Uri=18;
    public static final int DecimalDigit=50;
    public static final int IDLetter=41;
    public static final int AnnotationKey=5;
    public static final int EOF=-1;
    public static final int Percent=31;
    public static final int HexDigit=52;
    public static final int Identifier=8;
    public static final int Amper=19;
    public static final int BlockComment=43;
    public static final int T__55=55;
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
    public static final int UriReserved=46;
    public static final int Semi=34;
    public static final int UriAlpha=49;
    public static final int Not=15;
    public static final int Dot=26;
    public static final int UriEscaped=48;
    public static final int Annotation=39;
    public static final int Comma=24;
    public static final int Equals=27;
    public static final int Apostrophe=20;
    public static final int Slash=35;
    public static final int ResourceTypeAttribute=10;

    // delegates
    // delegators


        public TreatyParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TreatyParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TreatyParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g"; }


    private Contract contract;

    private Resource createConsumerResource(String id, URI resourceType, String className) {
        Resource resource = createResource(id, resourceType);
        
        resource.setName(className);
        
        return resource;
    }

    private Resource createSupplierResource(String id, URI resourceType, String resourceReference) {
        Resource resource = createResource(id, resourceType);
        
        resource.setRef(resourceReference);
        
        return resource;
    }

    private Resource createResource(String id, URI resourceType) {
        Resource resource = new Resource();
        
        resource.setId(id);
        resource.setType(resourceType);
        
        return resource;
    }



    // $ANTLR start "contract"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:86:1: contract returns [Contract value] : ( statment | Newline )* EOF ;
    public final Contract contract() throws RecognitionException {
        Contract value = null;

         contract = new Contract(); value = contract; 
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:88:5: ( ( statment | Newline )* EOF )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:88:9: ( statment | Newline )* EOF
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:88:9: ( statment | Newline )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AnnotationKey||LA1_0==Trigger||(LA1_0>=53 && LA1_0<=55)||(LA1_0>=57 && LA1_0<=58)) ) {
                    alt1=1;
                }
                else if ( (LA1_0==Newline) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:88:10: statment
            	    {
            	    pushFollow(FOLLOW_statment_in_contract70);
            	    statment();

            	    state._fsp--;
            	    if (state.failed) return value;

            	    }
            	    break;
            	case 2 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:88:21: Newline
            	    {
            	    match(input,Newline,FOLLOW_Newline_in_contract74); if (state.failed) return value;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_contract78); if (state.failed) return value;

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "contract"


    // $ANTLR start "statment"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:91:1: statment : ( annotation | trigger | consumerResource | supplierResource | constraint | onFailure | onSuccess ) Newline ;
    public final void statment() throws RecognitionException {
        TreatyParser.annotation_return annotation1 = null;

        URI trigger2 = null;

        Resource consumerResource3 = null;

        Resource supplierResource4 = null;

        AbstractCondition constraint5 = null;

        URI onFailure6 = null;

        URI onSuccess7 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:5: ( ( annotation | trigger | consumerResource | supplierResource | constraint | onFailure | onSuccess ) Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:9: ( annotation | trigger | consumerResource | supplierResource | constraint | onFailure | onSuccess ) Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:9: ( annotation | trigger | consumerResource | supplierResource | constraint | onFailure | onSuccess )
            int alt2=7;
            switch ( input.LA(1) ) {
            case AnnotationKey:
                {
                alt2=1;
                }
                break;
            case Trigger:
                {
                alt2=2;
                }
                break;
            case 53:
                {
                alt2=3;
                }
                break;
            case 54:
                {
                alt2=4;
                }
                break;
            case 55:
                {
                alt2=5;
                }
                break;
            case 57:
                {
                alt2=6;
                }
                break;
            case 58:
                {
                alt2=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:13: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_statment101);
                    annotation1=annotation();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.setProperty((annotation1!=null?annotation1.key:null), (annotation1!=null?annotation1.value:null)); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:93:13: trigger
                    {
                    pushFollow(FOLLOW_trigger_in_statment124);
                    trigger2=trigger();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addTrigger(trigger2); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:94:13: consumerResource
                    {
                    pushFollow(FOLLOW_consumerResource_in_statment150);
                    consumerResource3=consumerResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addConsumerResource(consumerResource3); 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:95:13: supplierResource
                    {
                    pushFollow(FOLLOW_supplierResource_in_statment167);
                    supplierResource4=supplierResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addSupplierResource(supplierResource4); 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:96:13: constraint
                    {
                    pushFollow(FOLLOW_constraint_in_statment184);
                    constraint5=constraint();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addCondition(constraint5); 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:97:13: onFailure
                    {
                    pushFollow(FOLLOW_onFailure_in_statment207);
                    onFailure6=onFailure();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationFailsAction(onFailure6); 
                    }

                    }
                    break;
                case 7 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:98:13: onSuccess
                    {
                    pushFollow(FOLLOW_onSuccess_in_statment231);
                    onSuccess7=onSuccess();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationSucceedsAction(onSuccess7); 
                    }

                    }
                    break;

            }

            match(input,Newline,FOLLOW_Newline_in_statment253); if (state.failed) return ;

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return ;
    }
    // $ANTLR end "statment"

    public static class annotation_return extends ParserRuleReturnScope {
        public String key;
        public String value;
    };

    // $ANTLR start "annotation"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:102:1: annotation returns [String key, String value] : AnnotationKey AnnotationValue ;
    public final TreatyParser.annotation_return annotation() throws RecognitionException {
        TreatyParser.annotation_return retval = new TreatyParser.annotation_return();
        retval.start = input.LT(1);

        Token AnnotationKey8=null;
        Token AnnotationValue9=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:103:5: ( AnnotationKey AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:103:9: AnnotationKey AnnotationValue
            {
            AnnotationKey8=(Token)match(input,AnnotationKey,FOLLOW_AnnotationKey_in_annotation276); if (state.failed) return retval;
            AnnotationValue9=(Token)match(input,AnnotationValue,FOLLOW_AnnotationValue_in_annotation278); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                          retval.key = (AnnotationKey8!=null?AnnotationKey8.getText():null);
                          retval.value = (AnnotationValue9!=null?AnnotationValue9.getText():null);
                      
            }

            }

            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "annotation"


    // $ANTLR start "trigger"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:110:1: trigger returns [URI value] : Trigger ;
    public final URI trigger() throws RecognitionException {
        URI value = null;

        Token Trigger10=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:111:5: ( Trigger )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:111:9: Trigger
            {
            Trigger10=(Token)match(input,Trigger,FOLLOW_Trigger_in_trigger311); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((Trigger10!=null?Trigger10.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "trigger"


    // $ANTLR start "consumerResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:114:1: consumerResource returns [Resource value] : 'consumer-resource' Identifier resourceTypeAttribute nameAttribute ;
    public final Resource consumerResource() throws RecognitionException {
        Resource value = null;

        Token Identifier11=null;
        URI resourceTypeAttribute12 = null;

        TreatyParser.nameAttribute_return nameAttribute13 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:115:5: ( 'consumer-resource' Identifier resourceTypeAttribute nameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:115:9: 'consumer-resource' Identifier resourceTypeAttribute nameAttribute
            {
            match(input,53,FOLLOW_53_in_consumerResource337); if (state.failed) return value;
            Identifier11=(Token)match(input,Identifier,FOLLOW_Identifier_in_consumerResource339); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_consumerResource341);
            resourceTypeAttribute12=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_nameAttribute_in_consumerResource343);
            nameAttribute13=nameAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createConsumerResource((Identifier11!=null?Identifier11.getText():null), resourceTypeAttribute12, (nameAttribute13!=null?input.toString(nameAttribute13.start,nameAttribute13.stop):null));
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "consumerResource"

    public static class nameAttribute_return extends ParserRuleReturnScope {
        public String value;
    };

    // $ANTLR start "nameAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:121:1: nameAttribute returns [String value] : NameAttribute ;
    public final TreatyParser.nameAttribute_return nameAttribute() throws RecognitionException {
        TreatyParser.nameAttribute_return retval = new TreatyParser.nameAttribute_return();
        retval.start = input.LT(1);

        Token NameAttribute14=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:122:5: ( NameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:122:9: NameAttribute
            {
            NameAttribute14=(Token)match(input,NameAttribute,FOLLOW_NameAttribute_in_nameAttribute376); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.value = (NameAttribute14!=null?NameAttribute14.getText():null); 
            }

            }

            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nameAttribute"


    // $ANTLR start "supplierResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:125:1: supplierResource returns [Resource value] : 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute ;
    public final Resource supplierResource() throws RecognitionException {
        Resource value = null;

        Token Identifier15=null;
        URI resourceTypeAttribute16 = null;

        String resourceReferenceAttribute17 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:126:5: ( 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:126:9: 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute
            {
            match(input,54,FOLLOW_54_in_supplierResource402); if (state.failed) return value;
            Identifier15=(Token)match(input,Identifier,FOLLOW_Identifier_in_supplierResource404); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_supplierResource406);
            resourceTypeAttribute16=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceReferenceAttribute_in_supplierResource408);
            resourceReferenceAttribute17=resourceReferenceAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createSupplierResource((Identifier15!=null?Identifier15.getText():null), resourceTypeAttribute16, resourceReferenceAttribute17);
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "supplierResource"


    // $ANTLR start "resourceTypeAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:132:1: resourceTypeAttribute returns [URI value] : ResourceTypeAttribute ;
    public final URI resourceTypeAttribute() throws RecognitionException {
        URI value = null;

        Token ResourceTypeAttribute18=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:133:5: ( ResourceTypeAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:133:9: ResourceTypeAttribute
            {
            ResourceTypeAttribute18=(Token)match(input,ResourceTypeAttribute,FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute441); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((ResourceTypeAttribute18!=null?ResourceTypeAttribute18.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "resourceTypeAttribute"


    // $ANTLR start "resourceReferenceAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:136:1: resourceReferenceAttribute returns [String value] : ResourceReferenceAttribute ;
    public final String resourceReferenceAttribute() throws RecognitionException {
        String value = null;

        Token ResourceReferenceAttribute19=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:137:5: ( ResourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:137:9: ResourceReferenceAttribute
            {
            ResourceReferenceAttribute19=(Token)match(input,ResourceReferenceAttribute,FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute467); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (ResourceReferenceAttribute19!=null?ResourceReferenceAttribute19.getText():null); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "resourceReferenceAttribute"


    // $ANTLR start "constraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:140:1: constraint returns [AbstractCondition value] : 'constraint' orConstraint ;
    public final AbstractCondition constraint() throws RecognitionException {
        AbstractCondition value = null;

        TreatyParser.orConstraint_return orConstraint20 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:141:5: ( 'constraint' orConstraint )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:141:9: 'constraint' orConstraint
            {
            match(input,55,FOLLOW_55_in_constraint496); if (state.failed) return value;
            pushFollow(FOLLOW_orConstraint_in_constraint498);
            orConstraint20=orConstraint();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (orConstraint20!=null?orConstraint20.value:null); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "constraint"

    public static class orConstraint_return extends ParserRuleReturnScope {
        public AbstractCondition value;
        public Disjunction disjunction;
    };

    // $ANTLR start "orConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:144:1: orConstraint returns [AbstractCondition value, Disjunction disjunction] : ( ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+ | xorConstraint );
    public final TreatyParser.orConstraint_return orConstraint() throws RecognitionException {
        TreatyParser.orConstraint_return retval = new TreatyParser.orConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.xorConstraint_return firstConstraint = null;

        TreatyParser.xorConstraint_return nextConstraint = null;

        TreatyParser.xorConstraint_return xorConstraint21 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:5: ( ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+ | xorConstraint )
            int alt4=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA4_1 = input.LA(2);

                if ( (synpred1_Treaty()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
                }
                break;
            case LParen:
                {
                int LA4_2 = input.LA(2);

                if ( (synpred1_Treaty()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }
                }
                break;
            case 56:
                {
                int LA4_3 = input.LA(2);

                if ( (synpred1_Treaty()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 3, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
                {
                int LA4_4 = input.LA(2);

                if ( (synpred1_Treaty()) ) {
                    alt4=1;
                }
                else if ( (true) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:9: ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.disjunction = new Disjunction(); retval.value = retval.disjunction; 
                    }
                    pushFollow(FOLLOW_xorConstraint_in_orConstraint541);
                    firstConstraint=xorConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.disjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:194: ( Or nextConstraint= xorConstraint )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==Or) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:195: Or nextConstraint= xorConstraint
                    	    {
                    	    match(input,Or,FOLLOW_Or_in_orConstraint546); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_xorConstraint_in_orConstraint550);
                    	    nextConstraint=xorConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.disjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:146:9: xorConstraint
                    {
                    pushFollow(FOLLOW_xorConstraint_in_orConstraint564);
                    xorConstraint21=xorConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (xorConstraint21!=null?xorConstraint21.value:null); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "orConstraint"

    public static class xorConstraint_return extends ParserRuleReturnScope {
        public AbstractCondition value;
        public XDisjunction xDisjunction;
    };

    // $ANTLR start "xorConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:149:1: xorConstraint returns [AbstractCondition value, XDisjunction xDisjunction] : ( ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+ | andConstraint );
    public final TreatyParser.xorConstraint_return xorConstraint() throws RecognitionException {
        TreatyParser.xorConstraint_return retval = new TreatyParser.xorConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.andConstraint_return firstConstraint = null;

        TreatyParser.andConstraint_return nextConstraint = null;

        TreatyParser.andConstraint_return andConstraint22 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:2: ( ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+ | andConstraint )
            int alt6=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA6_1 = input.LA(2);

                if ( (synpred2_Treaty()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
                }
                break;
            case LParen:
                {
                int LA6_2 = input.LA(2);

                if ( (synpred2_Treaty()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case 56:
                {
                int LA6_3 = input.LA(2);

                if ( (synpred2_Treaty()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 3, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
                {
                int LA6_4 = input.LA(2);

                if ( (synpred2_Treaty()) ) {
                    alt6=1;
                }
                else if ( (true) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:6: ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction = new XDisjunction(); retval.value = retval.xDisjunction; 
                    }
                    pushFollow(FOLLOW_andConstraint_in_xorConstraint628);
                    firstConstraint=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:196: ( XOr nextConstraint= andConstraint )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==XOr) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:197: XOr nextConstraint= andConstraint
                    	    {
                    	    match(input,XOr,FOLLOW_XOr_in_xorConstraint633); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_andConstraint_in_xorConstraint637);
                    	    nextConstraint=andConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.xDisjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:151:6: andConstraint
                    {
                    pushFollow(FOLLOW_andConstraint_in_xorConstraint648);
                    andConstraint22=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (andConstraint22!=null?andConstraint22.value:null); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "xorConstraint"

    public static class andConstraint_return extends ParserRuleReturnScope {
        public AbstractCondition value;
        public Conjunction conjunction;
    };

    // $ANTLR start "andConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:154:1: andConstraint returns [AbstractCondition value, Conjunction conjunction] : ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint );
    public final TreatyParser.andConstraint_return andConstraint() throws RecognitionException {
        TreatyParser.andConstraint_return retval = new TreatyParser.andConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return firstConstraint = null;

        TreatyParser.notConstraint_return nextConstraint = null;

        TreatyParser.notConstraint_return notConstraint23 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:5: ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint )
            int alt8=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA8_1 = input.LA(2);

                if ( (synpred3_Treaty()) ) {
                    alt8=1;
                }
                else if ( (true) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
                }
                break;
            case LParen:
                {
                int LA8_2 = input.LA(2);

                if ( (synpred3_Treaty()) ) {
                    alt8=1;
                }
                else if ( (true) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 2, input);

                    throw nvae;
                }
                }
                break;
            case 56:
                {
                int LA8_3 = input.LA(2);

                if ( (synpred3_Treaty()) ) {
                    alt8=1;
                }
                else if ( (true) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 3, input);

                    throw nvae;
                }
                }
                break;
            case Identifier:
                {
                int LA8_4 = input.LA(2);

                if ( (synpred3_Treaty()) ) {
                    alt8=1;
                }
                else if ( (true) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:9: ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.conjunction = new Conjunction(); retval.value = retval.conjunction; 
                    }
                    pushFollow(FOLLOW_notConstraint_in_andConstraint713);
                    firstConstraint=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.conjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:195: ( And nextConstraint= notConstraint )+
                    int cnt7=0;
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==And) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:196: And nextConstraint= notConstraint
                    	    {
                    	    match(input,And,FOLLOW_And_in_andConstraint718); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_notConstraint_in_andConstraint722);
                    	    nextConstraint=notConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.conjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt7 >= 1 ) break loop7;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(7, input);
                                throw eee;
                        }
                        cnt7++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:156:9: notConstraint
                    {
                    pushFollow(FOLLOW_notConstraint_in_andConstraint736);
                    notConstraint23=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (notConstraint23!=null?notConstraint23.value:null); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "andConstraint"

    public static class notConstraint_return extends ParserRuleReturnScope {
        public AbstractCondition value;
        public Negation negation;
    };

    // $ANTLR start "notConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:159:1: notConstraint returns [AbstractCondition value, Negation negation] : ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint );
    public final TreatyParser.notConstraint_return notConstraint() throws RecognitionException {
        TreatyParser.notConstraint_return retval = new TreatyParser.notConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return condition = null;

        TreatyParser.orConstraint_return orConstraint24 = null;

        ExistsCondition existsConstraint25 = null;

        RelationshipCondition relationshipConstraint26 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:160:5: ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint )
            int alt9=4;
            switch ( input.LA(1) ) {
            case Not:
                {
                alt9=1;
                }
                break;
            case LParen:
                {
                alt9=2;
                }
                break;
            case 56:
                {
                alt9=3;
                }
                break;
            case Identifier:
                {
                alt9=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:160:9: Not condition= notConstraint
                    {
                    match(input,Not,FOLLOW_Not_in_notConstraint787); if (state.failed) return retval;
                    pushFollow(FOLLOW_notConstraint_in_notConstraint791);
                    condition=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.negation = new Negation(); retval.negation.addCondition((condition!=null?condition.value:null)); retval.value = retval.negation; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:161:9: LParen orConstraint RParen
                    {
                    match(input,LParen,FOLLOW_LParen_in_notConstraint804); if (state.failed) return retval;
                    pushFollow(FOLLOW_orConstraint_in_notConstraint806);
                    orConstraint24=orConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RParen,FOLLOW_RParen_in_notConstraint808); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (orConstraint24!=null?orConstraint24.value:null); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:9: existsConstraint
                    {
                    pushFollow(FOLLOW_existsConstraint_in_notConstraint822);
                    existsConstraint25=existsConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = existsConstraint25; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:163:9: relationshipConstraint
                    {
                    pushFollow(FOLLOW_relationshipConstraint_in_notConstraint846);
                    relationshipConstraint26=relationshipConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = relationshipConstraint26; 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "notConstraint"


    // $ANTLR start "existsConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:166:1: existsConstraint returns [ExistsCondition value] : 'mustexist' resource ;
    public final ExistsCondition existsConstraint() throws RecognitionException {
        ExistsCondition value = null;

        Resource resource27 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:5: ( 'mustexist' resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:9: 'mustexist' resource
            {
            match(input,56,FOLLOW_56_in_existsConstraint877); if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_existsConstraint879);
            resource27=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new ExistsCondition();
                          value.setResource(resource27);
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "existsConstraint"


    // $ANTLR start "relationshipConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:174:1: relationshipConstraint returns [RelationshipCondition value] : leftResource= resource relationshipType rightResource= resource ;
    public final RelationshipCondition relationshipConstraint() throws RecognitionException {
        RelationshipCondition value = null;

        Resource leftResource = null;

        Resource rightResource = null;

        TreatyParser.relationshipType_return relationshipType28 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:175:5: (leftResource= resource relationshipType rightResource= resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:175:9: leftResource= resource relationshipType rightResource= resource
            {
            pushFollow(FOLLOW_resource_in_relationshipConstraint914);
            leftResource=resource();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_relationshipType_in_relationshipConstraint916);
            relationshipType28=relationshipType();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_relationshipConstraint920);
            rightResource=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new RelationshipCondition();
                          value.setResource1(leftResource);
                          value.setResource2(rightResource);
                          value.setRelationship(new URI((relationshipType28!=null?input.toString(relationshipType28.start,relationshipType28.stop):null)));
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "relationshipConstraint"

    public static class relationshipType_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "relationshipType"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:184:1: relationshipType : Uri ;
    public final TreatyParser.relationshipType_return relationshipType() throws RecognitionException {
        TreatyParser.relationshipType_return retval = new TreatyParser.relationshipType_return();
        retval.start = input.LT(1);

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:185:5: ( Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:185:8: Uri
            {
            match(input,Uri,FOLLOW_Uri_in_relationshipType948); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationshipType"


    // $ANTLR start "resource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:188:1: resource returns [Resource value] : resourceId= Identifier {...}?;
    public final Resource resource() throws RecognitionException {
        Resource value = null;

        Token resourceId=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:189:5: (resourceId= Identifier {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:189:9: resourceId= Identifier {...}?
            {
            resourceId=(Token)match(input,Identifier,FOLLOW_Identifier_in_resource973); if (state.failed) return value;
            if ( !(( contract.getResource((resourceId!=null?resourceId.getText():null)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "resource", " contract.getResource($resourceId.text) != null ");
            }
            if ( state.backtracking==0 ) {

                          value = contract.getResource((resourceId!=null?resourceId.getText():null));
                      
            }

            }

        }
        catch (FailedPredicateException ex) {

                    throw new FailedPredicateException(input, "resource", (resourceId!=null?resourceId.getText():null) + "' has not been declared");
                
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "resource"


    // $ANTLR start "onFailure"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:198:1: onFailure returns [URI value] : 'onfailure' action ;
    public final URI onFailure() throws RecognitionException {
        URI value = null;

        TreatyParser.action_return action29 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:199:5: ( 'onfailure' action )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:199:9: 'onfailure' action
            {
            match(input,57,FOLLOW_57_in_onFailure1019); if (state.failed) return value;
            pushFollow(FOLLOW_action_in_onFailure1021);
            action29=action();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action29!=null?input.toString(action29.start,action29.stop):null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "onFailure"


    // $ANTLR start "onSuccess"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:202:1: onSuccess returns [URI value] : 'onsuccess' action ;
    public final URI onSuccess() throws RecognitionException {
        URI value = null;

        TreatyParser.action_return action30 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:203:5: ( 'onsuccess' action )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:203:9: 'onsuccess' action
            {
            match(input,58,FOLLOW_58_in_onSuccess1047); if (state.failed) return value;
            pushFollow(FOLLOW_action_in_onSuccess1049);
            action30=action();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action30!=null?input.toString(action30.start,action30.stop):null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return value;
    }
    // $ANTLR end "onSuccess"

    public static class action_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "action"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:206:1: action : Uri ;
    public final TreatyParser.action_return action() throws RecognitionException {
        TreatyParser.action_return retval = new TreatyParser.action_return();
        retval.start = input.LT(1);

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:5: ( Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:9: Uri
            {
            match(input,Uri,FOLLOW_Uri_in_action1071); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(this.input, e);
            }
        finally {
        }
        return retval;
    }
    // $ANTLR end "action"

    // $ANTLR start synpred1_Treaty
    public final void synpred1_Treaty_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:9: ( xorConstraint ( Or xorConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:10: xorConstraint ( Or xorConstraint )+
        {
        pushFollow(FOLLOW_xorConstraint_in_synpred1_Treaty525);
        xorConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:24: ( Or xorConstraint )+
        int cnt10=0;
        loop10:
        do {
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Or) ) {
                alt10=1;
            }


            switch (alt10) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:25: Or xorConstraint
        	    {
        	    match(input,Or,FOLLOW_Or_in_synpred1_Treaty528); if (state.failed) return ;
        	    pushFollow(FOLLOW_xorConstraint_in_synpred1_Treaty530);
        	    xorConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt10 >= 1 ) break loop10;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(10, input);
                    throw eee;
            }
            cnt10++;
        } while (true);


        }
    }
    // $ANTLR end synpred1_Treaty

    // $ANTLR start synpred2_Treaty
    public final void synpred2_Treaty_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:6: ( andConstraint ( XOr andConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:7: andConstraint ( XOr andConstraint )+
        {
        pushFollow(FOLLOW_andConstraint_in_synpred2_Treaty612);
        andConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:21: ( XOr andConstraint )+
        int cnt11=0;
        loop11:
        do {
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==XOr) ) {
                alt11=1;
            }


            switch (alt11) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:22: XOr andConstraint
        	    {
        	    match(input,XOr,FOLLOW_XOr_in_synpred2_Treaty615); if (state.failed) return ;
        	    pushFollow(FOLLOW_andConstraint_in_synpred2_Treaty617);
        	    andConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt11 >= 1 ) break loop11;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(11, input);
                    throw eee;
            }
            cnt11++;
        } while (true);


        }
    }
    // $ANTLR end synpred2_Treaty

    // $ANTLR start synpred3_Treaty
    public final void synpred3_Treaty_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:9: ( notConstraint ( And notConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:10: notConstraint ( And notConstraint )+
        {
        pushFollow(FOLLOW_notConstraint_in_synpred3_Treaty697);
        notConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:24: ( And notConstraint )+
        int cnt12=0;
        loop12:
        do {
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==And) ) {
                alt12=1;
            }


            switch (alt12) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:25: And notConstraint
        	    {
        	    match(input,And,FOLLOW_And_in_synpred3_Treaty700); if (state.failed) return ;
        	    pushFollow(FOLLOW_notConstraint_in_synpred3_Treaty702);
        	    notConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt12 >= 1 ) break loop12;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(12, input);
                    throw eee;
            }
            cnt12++;
        } while (true);


        }
    }
    // $ANTLR end synpred3_Treaty

    // Delegated rules

    public final boolean synpred2_Treaty() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Treaty_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Treaty() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Treaty_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Treaty() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Treaty_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_statment_in_contract70 = new BitSet(new long[]{0x06E00000000000B0L});
    public static final BitSet FOLLOW_Newline_in_contract74 = new BitSet(new long[]{0x06E00000000000B0L});
    public static final BitSet FOLLOW_EOF_in_contract78 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_statment101 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_trigger_in_statment124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_consumerResource_in_statment150 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_supplierResource_in_statment167 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_in_statment184 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onFailure_in_statment207 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onSuccess_in_statment231 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Newline_in_statment253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AnnotationKey_in_annotation276 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AnnotationValue_in_annotation278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Trigger_in_trigger311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_consumerResource337 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_consumerResource339 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_consumerResource341 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_nameAttribute_in_consumerResource343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NameAttribute_in_nameAttribute376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_supplierResource402 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_supplierResource404 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_supplierResource406 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_resourceReferenceAttribute_in_supplierResource408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_constraint496 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_orConstraint_in_constraint498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint541 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_orConstraint546 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint550 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint628 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_XOr_in_xorConstraint633 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint637 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint713 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_andConstraint718 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint722 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Not_in_notConstraint787 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_notConstraint791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LParen_in_notConstraint804 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_orConstraint_in_notConstraint806 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RParen_in_notConstraint808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existsConstraint_in_notConstraint822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationshipConstraint_in_notConstraint846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_existsConstraint877 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_resource_in_existsConstraint879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint914 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_relationshipType_in_relationshipConstraint916 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Uri_in_relationshipType948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_resource973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_onFailure1019 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_action_in_onFailure1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_onSuccess1047 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_action_in_onSuccess1049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Uri_in_action1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xorConstraint_in_synpred1_Treaty525 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_synpred1_Treaty528 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_xorConstraint_in_synpred1_Treaty530 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_andConstraint_in_synpred2_Treaty612 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_XOr_in_synpred2_Treaty615 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_andConstraint_in_synpred2_Treaty617 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_notConstraint_in_synpred3_Treaty697 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_synpred3_Treaty700 = new BitSet(new long[]{0x0100000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_synpred3_Treaty702 = new BitSet(new long[]{0x0000000000004002L});

}