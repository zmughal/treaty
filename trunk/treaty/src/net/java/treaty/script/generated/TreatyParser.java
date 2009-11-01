// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-11-02 11:40:06

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Newline", "AnnotationKey", "AnnotationValue", "Trigger", "Identifier", "ResourceNameAttribute", "ResourceTypeAttribute", "ResourceReferenceAttribute", "Or", "XOr", "And", "Not", "LParen", "RParen", "Uri", "Amper", "Apostrophe", "Asterisk", "At", "Colon", "Comma", "Dollar", "Dot", "Equals", "Exclamation", "Hash", "Minus", "Percent", "Plus", "Question", "Semi", "Slash", "Tilde", "Whitespace", "String", "Annotation", "NamespaceDelimiter", "IDLetter", "IDDigit", "BlockComment", "LineComment", "UriCharacter", "UriReserved", "UriUnescaped", "UriEscaped", "UriAlpha", "DecimalDigit", "UriMark", "HexDigit", "'consumer-resource'", "'supplier-resource'", "'external-resource'", "'constraint'", "'mustexist'", "'onfailure'", "'onsuccess'"
    };
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

    private Resource createNamedResource(String id, URI resourceType, String name) {
        Resource resource = createResource(id, resourceType);
        
        resource.setName(name);
        
        return resource;
    }

    private Resource createReferenceResource(String id, URI resourceType, String reference) {
        Resource resource = createResource(id, resourceType);
        
        resource.setRef(reference);
        
        return resource;
    }

    private Resource createResource(String id, URI resourceType) {
        Resource resource = new Resource();
        
        resource.setId(id);
        resource.setType(resourceType);
        
        return resource;
    }



    // $ANTLR start "contract"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:90:1: contract returns [Contract value] : ( statment | Newline )* EOF ;
    public final Contract contract() throws RecognitionException {
        Contract value = null;

         contract = new Contract(); value = contract; 
        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:5: ( ( statment | Newline )* EOF )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:9: ( statment | Newline )* EOF
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:9: ( statment | Newline )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AnnotationKey||LA1_0==Trigger||(LA1_0>=53 && LA1_0<=56)||(LA1_0>=58 && LA1_0<=59)) ) {
                    alt1=1;
                }
                else if ( (LA1_0==Newline) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:10: statment
            	    {
            	    pushFollow(FOLLOW_statment_in_contract70);
            	    statment();

            	    state._fsp--;
            	    if (state.failed) return value;

            	    }
            	    break;
            	case 2 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:92:21: Newline
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:95:1: statment : ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline ;
    public final void statment() throws RecognitionException {
        TreatyParser.annotation_return annotation1 = null;

        URI trigger2 = null;

        Resource consumerResource3 = null;

        Resource supplierResource4 = null;

        Resource externalResource5 = null;

        AbstractCondition constraint6 = null;

        URI onFailure7 = null;

        URI onSuccess8 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:96:5: ( ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:96:9: ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:96:9: ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess )
            int alt2=8;
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
            case 56:
                {
                alt2=6;
                }
                break;
            case 58:
                {
                alt2=7;
                }
                break;
            case 59:
                {
                alt2=8;
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:96:13: annotation
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:97:13: trigger
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:98:13: consumerResource
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:99:13: supplierResource
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:100:13: externalResource
                    {
                    pushFollow(FOLLOW_externalResource_in_statment184);
                    externalResource5=externalResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addExternalResource(externalResource5); 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:101:13: constraint
                    {
                    pushFollow(FOLLOW_constraint_in_statment201);
                    constraint6=constraint();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addCondition(constraint6); 
                    }

                    }
                    break;
                case 7 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:102:13: onFailure
                    {
                    pushFollow(FOLLOW_onFailure_in_statment224);
                    onFailure7=onFailure();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationFailsAction(onFailure7); 
                    }

                    }
                    break;
                case 8 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:103:13: onSuccess
                    {
                    pushFollow(FOLLOW_onSuccess_in_statment248);
                    onSuccess8=onSuccess();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationSucceedsAction(onSuccess8); 
                    }

                    }
                    break;

            }

            match(input,Newline,FOLLOW_Newline_in_statment270); if (state.failed) return ;

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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:107:1: annotation returns [String key, String value] : AnnotationKey AnnotationValue ;
    public final TreatyParser.annotation_return annotation() throws RecognitionException {
        TreatyParser.annotation_return retval = new TreatyParser.annotation_return();
        retval.start = input.LT(1);

        Token AnnotationKey9=null;
        Token AnnotationValue10=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:108:5: ( AnnotationKey AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:108:9: AnnotationKey AnnotationValue
            {
            AnnotationKey9=(Token)match(input,AnnotationKey,FOLLOW_AnnotationKey_in_annotation293); if (state.failed) return retval;
            AnnotationValue10=(Token)match(input,AnnotationValue,FOLLOW_AnnotationValue_in_annotation295); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                          retval.key = (AnnotationKey9!=null?AnnotationKey9.getText():null);
                          retval.value = (AnnotationValue10!=null?AnnotationValue10.getText():null);
                      
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:115:1: trigger returns [URI value] : Trigger ;
    public final URI trigger() throws RecognitionException {
        URI value = null;

        Token Trigger11=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:116:5: ( Trigger )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:116:9: Trigger
            {
            Trigger11=(Token)match(input,Trigger,FOLLOW_Trigger_in_trigger328); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((Trigger11!=null?Trigger11.getText():null)); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:119:1: consumerResource returns [Resource value] : 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute ;
    public final Resource consumerResource() throws RecognitionException {
        Resource value = null;

        Token Identifier12=null;
        URI resourceTypeAttribute13 = null;

        TreatyParser.resourceNameAttribute_return resourceNameAttribute14 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:120:5: ( 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:120:9: 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute
            {
            match(input,53,FOLLOW_53_in_consumerResource354); if (state.failed) return value;
            Identifier12=(Token)match(input,Identifier,FOLLOW_Identifier_in_consumerResource356); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_consumerResource358);
            resourceTypeAttribute13=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceNameAttribute_in_consumerResource360);
            resourceNameAttribute14=resourceNameAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createNamedResource((Identifier12!=null?Identifier12.getText():null), resourceTypeAttribute13, (resourceNameAttribute14!=null?input.toString(resourceNameAttribute14.start,resourceNameAttribute14.stop):null));
                      
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


    // $ANTLR start "supplierResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:126:1: supplierResource returns [Resource value] : 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute ;
    public final Resource supplierResource() throws RecognitionException {
        Resource value = null;

        Token Identifier15=null;
        URI resourceTypeAttribute16 = null;

        String resourceReferenceAttribute17 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:127:5: ( 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:127:9: 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute
            {
            match(input,54,FOLLOW_54_in_supplierResource393); if (state.failed) return value;
            Identifier15=(Token)match(input,Identifier,FOLLOW_Identifier_in_supplierResource395); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_supplierResource397);
            resourceTypeAttribute16=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceReferenceAttribute_in_supplierResource399);
            resourceReferenceAttribute17=resourceReferenceAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createReferenceResource((Identifier15!=null?Identifier15.getText():null), resourceTypeAttribute16, resourceReferenceAttribute17);
                      
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


    // $ANTLR start "externalResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:133:1: externalResource returns [Resource value] : 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute ;
    public final Resource externalResource() throws RecognitionException {
        Resource value = null;

        Token Identifier18=null;
        URI resourceTypeAttribute19 = null;

        TreatyParser.resourceNameAttribute_return resourceNameAttribute20 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:134:5: ( 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:134:9: 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute
            {
            match(input,55,FOLLOW_55_in_externalResource436); if (state.failed) return value;
            Identifier18=(Token)match(input,Identifier,FOLLOW_Identifier_in_externalResource438); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_externalResource440);
            resourceTypeAttribute19=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceNameAttribute_in_externalResource442);
            resourceNameAttribute20=resourceNameAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createNamedResource((Identifier18!=null?Identifier18.getText():null), resourceTypeAttribute19, (resourceNameAttribute20!=null?input.toString(resourceNameAttribute20.start,resourceNameAttribute20.stop):null));
                      
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
    // $ANTLR end "externalResource"

    public static class resourceNameAttribute_return extends ParserRuleReturnScope {
        public String value;
    };

    // $ANTLR start "resourceNameAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:140:1: resourceNameAttribute returns [String value] : ResourceNameAttribute ;
    public final TreatyParser.resourceNameAttribute_return resourceNameAttribute() throws RecognitionException {
        TreatyParser.resourceNameAttribute_return retval = new TreatyParser.resourceNameAttribute_return();
        retval.start = input.LT(1);

        Token ResourceNameAttribute21=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:141:5: ( ResourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:141:9: ResourceNameAttribute
            {
            ResourceNameAttribute21=(Token)match(input,ResourceNameAttribute,FOLLOW_ResourceNameAttribute_in_resourceNameAttribute475); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.value = (ResourceNameAttribute21!=null?ResourceNameAttribute21.getText():null); 
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
    // $ANTLR end "resourceNameAttribute"


    // $ANTLR start "resourceTypeAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:144:1: resourceTypeAttribute returns [URI value] : ResourceTypeAttribute ;
    public final URI resourceTypeAttribute() throws RecognitionException {
        URI value = null;

        Token ResourceTypeAttribute22=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:5: ( ResourceTypeAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:9: ResourceTypeAttribute
            {
            ResourceTypeAttribute22=(Token)match(input,ResourceTypeAttribute,FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute501); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((ResourceTypeAttribute22!=null?ResourceTypeAttribute22.getText():null)); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:148:1: resourceReferenceAttribute returns [String value] : ResourceReferenceAttribute ;
    public final String resourceReferenceAttribute() throws RecognitionException {
        String value = null;

        Token ResourceReferenceAttribute23=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:149:5: ( ResourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:149:9: ResourceReferenceAttribute
            {
            ResourceReferenceAttribute23=(Token)match(input,ResourceReferenceAttribute,FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute527); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (ResourceReferenceAttribute23!=null?ResourceReferenceAttribute23.getText():null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:152:1: constraint returns [AbstractCondition value] : 'constraint' orConstraint ;
    public final AbstractCondition constraint() throws RecognitionException {
        AbstractCondition value = null;

        TreatyParser.orConstraint_return orConstraint24 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:153:5: ( 'constraint' orConstraint )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:153:9: 'constraint' orConstraint
            {
            match(input,56,FOLLOW_56_in_constraint556); if (state.failed) return value;
            pushFollow(FOLLOW_orConstraint_in_constraint558);
            orConstraint24=orConstraint();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (orConstraint24!=null?orConstraint24.value:null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:156:1: orConstraint returns [AbstractCondition value, Disjunction disjunction] : ( ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+ | xorConstraint );
    public final TreatyParser.orConstraint_return orConstraint() throws RecognitionException {
        TreatyParser.orConstraint_return retval = new TreatyParser.orConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.xorConstraint_return firstConstraint = null;

        TreatyParser.xorConstraint_return nextConstraint = null;

        TreatyParser.xorConstraint_return xorConstraint25 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:5: ( ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+ | xorConstraint )
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
            case 57:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:9: ( xorConstraint ( Or xorConstraint )+ )=>firstConstraint= xorConstraint ( Or nextConstraint= xorConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.disjunction = new Disjunction(); retval.value = retval.disjunction; 
                    }
                    pushFollow(FOLLOW_xorConstraint_in_orConstraint601);
                    firstConstraint=xorConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.disjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:194: ( Or nextConstraint= xorConstraint )+
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
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:195: Or nextConstraint= xorConstraint
                    	    {
                    	    match(input,Or,FOLLOW_Or_in_orConstraint606); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_xorConstraint_in_orConstraint610);
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:158:9: xorConstraint
                    {
                    pushFollow(FOLLOW_xorConstraint_in_orConstraint624);
                    xorConstraint25=xorConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (xorConstraint25!=null?xorConstraint25.value:null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:161:1: xorConstraint returns [AbstractCondition value, XDisjunction xDisjunction] : ( ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+ | andConstraint );
    public final TreatyParser.xorConstraint_return xorConstraint() throws RecognitionException {
        TreatyParser.xorConstraint_return retval = new TreatyParser.xorConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.andConstraint_return firstConstraint = null;

        TreatyParser.andConstraint_return nextConstraint = null;

        TreatyParser.andConstraint_return andConstraint26 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:5: ( ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+ | andConstraint )
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
            case 57:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:9: ( andConstraint ( XOr andConstraint )+ )=>firstConstraint= andConstraint ( XOr nextConstraint= andConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction = new XDisjunction(); retval.value = retval.xDisjunction; 
                    }
                    pushFollow(FOLLOW_andConstraint_in_xorConstraint691);
                    firstConstraint=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:199: ( XOr nextConstraint= andConstraint )+
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
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:200: XOr nextConstraint= andConstraint
                    	    {
                    	    match(input,XOr,FOLLOW_XOr_in_xorConstraint696); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_andConstraint_in_xorConstraint700);
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:163:9: andConstraint
                    {
                    pushFollow(FOLLOW_andConstraint_in_xorConstraint714);
                    andConstraint26=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (andConstraint26!=null?andConstraint26.value:null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:166:1: andConstraint returns [AbstractCondition value, Conjunction conjunction] : ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint );
    public final TreatyParser.andConstraint_return andConstraint() throws RecognitionException {
        TreatyParser.andConstraint_return retval = new TreatyParser.andConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return firstConstraint = null;

        TreatyParser.notConstraint_return nextConstraint = null;

        TreatyParser.notConstraint_return notConstraint27 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:5: ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint )
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
            case 57:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:9: ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.conjunction = new Conjunction(); retval.value = retval.conjunction; 
                    }
                    pushFollow(FOLLOW_notConstraint_in_andConstraint782);
                    firstConstraint=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.conjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:195: ( And nextConstraint= notConstraint )+
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
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:196: And nextConstraint= notConstraint
                    	    {
                    	    match(input,And,FOLLOW_And_in_andConstraint787); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_notConstraint_in_andConstraint791);
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:168:9: notConstraint
                    {
                    pushFollow(FOLLOW_notConstraint_in_andConstraint805);
                    notConstraint27=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (notConstraint27!=null?notConstraint27.value:null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:171:1: notConstraint returns [AbstractCondition value, Negation negation] : ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint );
    public final TreatyParser.notConstraint_return notConstraint() throws RecognitionException {
        TreatyParser.notConstraint_return retval = new TreatyParser.notConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return condition = null;

        TreatyParser.orConstraint_return orConstraint28 = null;

        ExistsCondition existsConstraint29 = null;

        RelationshipCondition relationshipConstraint30 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:172:5: ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint )
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
            case 57:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:172:9: Not condition= notConstraint
                    {
                    match(input,Not,FOLLOW_Not_in_notConstraint856); if (state.failed) return retval;
                    pushFollow(FOLLOW_notConstraint_in_notConstraint860);
                    condition=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.negation = new Negation(); retval.negation.addCondition((condition!=null?condition.value:null)); retval.value = retval.negation; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:173:9: LParen orConstraint RParen
                    {
                    match(input,LParen,FOLLOW_LParen_in_notConstraint873); if (state.failed) return retval;
                    pushFollow(FOLLOW_orConstraint_in_notConstraint875);
                    orConstraint28=orConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RParen,FOLLOW_RParen_in_notConstraint877); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (orConstraint28!=null?orConstraint28.value:null); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:174:9: existsConstraint
                    {
                    pushFollow(FOLLOW_existsConstraint_in_notConstraint891);
                    existsConstraint29=existsConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = existsConstraint29; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:175:9: relationshipConstraint
                    {
                    pushFollow(FOLLOW_relationshipConstraint_in_notConstraint915);
                    relationshipConstraint30=relationshipConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = relationshipConstraint30; 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:178:1: existsConstraint returns [ExistsCondition value] : 'mustexist' resource ;
    public final ExistsCondition existsConstraint() throws RecognitionException {
        ExistsCondition value = null;

        Resource resource31 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:179:5: ( 'mustexist' resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:179:9: 'mustexist' resource
            {
            match(input,57,FOLLOW_57_in_existsConstraint946); if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_existsConstraint948);
            resource31=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new ExistsCondition();
                          value.setResource(resource31);
                      
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:186:1: relationshipConstraint returns [RelationshipCondition value] : leftResource= resource relationshipType= Uri rightResource= resource ;
    public final RelationshipCondition relationshipConstraint() throws RecognitionException {
        RelationshipCondition value = null;

        Token relationshipType=null;
        Resource leftResource = null;

        Resource rightResource = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:187:5: (leftResource= resource relationshipType= Uri rightResource= resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:187:9: leftResource= resource relationshipType= Uri rightResource= resource
            {
            pushFollow(FOLLOW_resource_in_relationshipConstraint983);
            leftResource=resource();

            state._fsp--;
            if (state.failed) return value;
            relationshipType=(Token)match(input,Uri,FOLLOW_Uri_in_relationshipConstraint987); if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_relationshipConstraint991);
            rightResource=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new RelationshipCondition();
                          value.setResource1(leftResource);
                          value.setResource2(rightResource);
                          value.setRelationship(new URI((relationshipType!=null?relationshipType.getText():null)));
                      
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


    // $ANTLR start "resource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:196:1: resource returns [Resource value] : resourceId= Identifier {...}?;
    public final Resource resource() throws RecognitionException {
        Resource value = null;

        Token resourceId=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:197:5: (resourceId= Identifier {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:197:9: resourceId= Identifier {...}?
            {
            resourceId=(Token)match(input,Identifier,FOLLOW_Identifier_in_resource1026); if (state.failed) return value;
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:206:1: onFailure returns [URI value] : 'onfailure' action= Uri ;
    public final URI onFailure() throws RecognitionException {
        URI value = null;

        Token action=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:5: ( 'onfailure' action= Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:207:9: 'onfailure' action= Uri
            {
            match(input,58,FOLLOW_58_in_onFailure1072); if (state.failed) return value;
            action=(Token)match(input,Uri,FOLLOW_Uri_in_onFailure1076); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action!=null?action.getText():null)); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:210:1: onSuccess returns [URI value] : 'onsuccess' action= Uri ;
    public final URI onSuccess() throws RecognitionException {
        URI value = null;

        Token action=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:5: ( 'onsuccess' action= Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:211:9: 'onsuccess' action= Uri
            {
            match(input,59,FOLLOW_59_in_onSuccess1102); if (state.failed) return value;
            action=(Token)match(input,Uri,FOLLOW_Uri_in_onSuccess1106); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action!=null?action.getText():null)); 
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

    // $ANTLR start synpred1_Treaty
    public final void synpred1_Treaty_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:9: ( xorConstraint ( Or xorConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:10: xorConstraint ( Or xorConstraint )+
        {
        pushFollow(FOLLOW_xorConstraint_in_synpred1_Treaty585);
        xorConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:24: ( Or xorConstraint )+
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
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:25: Or xorConstraint
        	    {
        	    match(input,Or,FOLLOW_Or_in_synpred1_Treaty588); if (state.failed) return ;
        	    pushFollow(FOLLOW_xorConstraint_in_synpred1_Treaty590);
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
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:9: ( andConstraint ( XOr andConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:10: andConstraint ( XOr andConstraint )+
        {
        pushFollow(FOLLOW_andConstraint_in_synpred2_Treaty675);
        andConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:24: ( XOr andConstraint )+
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
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:25: XOr andConstraint
        	    {
        	    match(input,XOr,FOLLOW_XOr_in_synpred2_Treaty678); if (state.failed) return ;
        	    pushFollow(FOLLOW_andConstraint_in_synpred2_Treaty680);
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
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:9: ( notConstraint ( And notConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:10: notConstraint ( And notConstraint )+
        {
        pushFollow(FOLLOW_notConstraint_in_synpred3_Treaty766);
        notConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:24: ( And notConstraint )+
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
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:167:25: And notConstraint
        	    {
        	    match(input,And,FOLLOW_And_in_synpred3_Treaty769); if (state.failed) return ;
        	    pushFollow(FOLLOW_notConstraint_in_synpred3_Treaty771);
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


 

    public static final BitSet FOLLOW_statment_in_contract70 = new BitSet(new long[]{0x0DE00000000000B0L});
    public static final BitSet FOLLOW_Newline_in_contract74 = new BitSet(new long[]{0x0DE00000000000B0L});
    public static final BitSet FOLLOW_EOF_in_contract78 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_statment101 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_trigger_in_statment124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_consumerResource_in_statment150 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_supplierResource_in_statment167 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_externalResource_in_statment184 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_in_statment201 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onFailure_in_statment224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onSuccess_in_statment248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Newline_in_statment270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AnnotationKey_in_annotation293 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AnnotationValue_in_annotation295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Trigger_in_trigger328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_consumerResource354 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_consumerResource356 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_consumerResource358 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_resourceNameAttribute_in_consumerResource360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_supplierResource393 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_supplierResource395 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_supplierResource397 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_resourceReferenceAttribute_in_supplierResource399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_externalResource436 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_externalResource438 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_externalResource440 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_resourceNameAttribute_in_externalResource442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceNameAttribute_in_resourceNameAttribute475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_constraint556 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_orConstraint_in_constraint558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint601 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_orConstraint606 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint610 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_xorConstraint_in_orConstraint624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint691 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_XOr_in_xorConstraint696 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint700 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_andConstraint_in_xorConstraint714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint782 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_andConstraint787 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint791 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Not_in_notConstraint856 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_notConstraint860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LParen_in_notConstraint873 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_orConstraint_in_notConstraint875 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RParen_in_notConstraint877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existsConstraint_in_notConstraint891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationshipConstraint_in_notConstraint915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_existsConstraint946 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_resource_in_existsConstraint948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint983 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_Uri_in_relationshipConstraint987 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_resource1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_onFailure1072 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_Uri_in_onFailure1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_onSuccess1102 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_Uri_in_onSuccess1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xorConstraint_in_synpred1_Treaty585 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_synpred1_Treaty588 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_xorConstraint_in_synpred1_Treaty590 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_andConstraint_in_synpred2_Treaty675 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_XOr_in_synpred2_Treaty678 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_andConstraint_in_synpred2_Treaty680 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_notConstraint_in_synpred3_Treaty766 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_synpred3_Treaty769 = new BitSet(new long[]{0x0200000000018100L});
    public static final BitSet FOLLOW_notConstraint_in_synpred3_Treaty771 = new BitSet(new long[]{0x0000000000004002L});

}