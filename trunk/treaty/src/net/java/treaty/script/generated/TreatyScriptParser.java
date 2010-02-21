// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g 2010-02-22 12:04:51

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
 * The TreatyScript grammar for ANTLRv3. 
 */
public class TreatyScriptParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Newline", "AnnotationKey", "AnnotationValue", "Trigger", "Identifier", "ResourceNameAttribute", "ResourceTypeAttribute", "ResourceReferenceAttribute", "XOr", "Or", "And", "Not", "LParen", "RParen", "Uri", "Equal", "NotEqual", "Gt", "Gte", "Lt", "Lte", "IntegerLiteral", "FloatingPointLiteral", "StringLiteral", "Amper", "Apostrophe", "Asterisk", "At", "Colon", "Comma", "Dollar", "Dot", "Exclamation", "Hash", "Minus", "Percent", "Plus", "Question", "Semi", "Slash", "Tilde", "Exponent", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "DecimalDigit", "Whitespace", "String", "Annotation", "NamespaceDelimiter", "IDLetter", "IDDigit", "BlockComment", "LineComment", "LeadingWhitespace", "UriCharacter", "UriReserved", "UriUnescaped", "UriEscaped", "UriAlpha", "UriMark", "'consumer-resource'", "'supplier-resource'", "'external-resource'", "'constraint'", "'mustexist'", "'matches'", "'in'", "'true'", "'false'", "'onfailure'", "'onsuccess'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int Gt=21;
    public static final int Hash=37;
    public static final int LineComment=58;
    public static final int Exponent=45;
    public static final int Newline=4;
    public static final int LeadingWhitespace=59;
    public static final int AnnotationValue=6;
    public static final int Uri=18;
    public static final int DecimalDigit=50;
    public static final int IDLetter=55;
    public static final int Percent=39;
    public static final int AnnotationKey=5;
    public static final int EOF=-1;
    public static final int HexDigit=49;
    public static final int Identifier=8;
    public static final int Amper=28;
    public static final int BlockComment=57;
    public static final int Lt=23;
    public static final int XOr=12;
    public static final int UriCharacter=60;
    public static final int ResourceNameAttribute=9;
    public static final int UriUnescaped=62;
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
    public static final int NotEqual=20;
    public static final int Gte=22;
    public static final int Question=41;
    public static final int Equal=19;
    public static final int UriMark=65;
    public static final int StringLiteral=27;
    public static final int Plus=40;
    public static final int Minus=38;
    public static final int UriReserved=61;
    public static final int Semi=42;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int UnicodeEscape=47;
    public static final int FloatingPointLiteral=26;
    public static final int UriAlpha=64;
    public static final int Not=15;
    public static final int Dot=35;
    public static final int UriEscaped=63;
    public static final int IntegerLiteral=25;
    public static final int Annotation=53;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int Comma=33;
    public static final int T__74=74;
    public static final int OctalEscape=48;
    public static final int EscapeSequence=46;
    public static final int T__73=73;
    public static final int Apostrophe=29;
    public static final int Slash=43;
    public static final int ResourceTypeAttribute=10;

    // delegates
    // delegators


        public TreatyScriptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TreatyScriptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[73+1];
             
             
        }
        

    public String[] getTokenNames() { return TreatyScriptParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g"; }


    private Contract contract;
    private ContractVocabulary vocabulary;

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

    private boolean isValidRelationship(Resource sourceResource, URI relationship, Resource targetResource) throws TreatyException {
        /* It appears that in order to support backtracking these semantic predicates should return true when called with
         * null arguments.
         */
        if (sourceResource == null || relationship == null || targetResource == null)
            return true;
            
        if (!vocabulary.getRelationships().contains(relationship))
            return false;

        URI domain = vocabulary.getDomain(relationship);
        URI range = vocabulary.getRange(relationship);
            
        if (!domain.equals(sourceResource.getType()) && !vocabulary.getSuperTypes(sourceResource.getType()).contains(domain))
            return false;
            
        if (!range.equals(targetResource.getType()) && !vocabulary.getSuperTypes(targetResource.getType()).contains(range))
            return false;
            
        return true;
    }

    private boolean isValidProperty(Resource resource, URI property, Object value) throws TreatyException {
        /* It appears that in order to support backtracking these semantic predicates should return true when called with
         * null arguments.
         */
        if (resource == null || property == null || value == null)
            return true;
            
        if (!vocabulary.getProperties().contains(property))
            return false;

        return true;
    }

    private RelationshipCondition createRelationshipCondition(Resource sourceResource, URI relationship, Resource targetResource) {
        RelationshipCondition condition = new RelationshipCondition();
        
        condition.setResource1(sourceResource);
        condition.setResource2(targetResource);
        condition.setRelationship(relationship);
        
        return condition;
    }

    private PropertyCondition createPropertyCondition(Resource resource, URI property, Object value) {
        PropertyCondition condition = new PropertyCondition();
        
        condition.setResource(resource);
        condition.setOperator(property);
        condition.setValue(value);
        
        return condition;
    }



    // $ANTLR start "contract"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:151:1: contract[ContractVocabulary vocabulary] returns [Contract value] : ( statment | Newline )* EOF ;
    public final Contract contract(ContractVocabulary vocabulary) throws RecognitionException {
        Contract value = null;
        int contract_StartIndex = input.index();

            contract = new Contract(); this.vocabulary = vocabulary;
            if (vocabulary == null)
                throw new IllegalArgumentException("'vocabulary' cannot be null");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:158:5: ( ( statment | Newline )* EOF )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:158:9: ( statment | Newline )* EOF
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:158:9: ( statment | Newline )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AnnotationKey||LA1_0==Trigger||(LA1_0>=66 && LA1_0<=69)||(LA1_0>=75 && LA1_0<=76)) ) {
                    alt1=1;
                }
                else if ( (LA1_0==Newline) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:158:10: statment
            	    {
            	    pushFollow(FOLLOW_statment_in_contract94);
            	    statment();

            	    state._fsp--;
            	    if (state.failed) return value;

            	    }
            	    break;
            	case 2 :
            	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:158:21: Newline
            	    {
            	    match(input,Newline,FOLLOW_Newline_in_contract98); if (state.failed) return value;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_contract102); if (state.failed) return value;

            }

            if ( state.backtracking==0 ) {
               value = contract; 
            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, contract_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "contract"


    // $ANTLR start "statment"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:161:1: statment : ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline ;
    public final void statment() throws RecognitionException {
        int statment_StartIndex = input.index();
        TreatyScriptParser.annotation_return annotation1 = null;

        URI trigger2 = null;

        Resource consumerResource3 = null;

        Resource supplierResource4 = null;

        Resource externalResource5 = null;

        Condition constraint6 = null;

        URI onFailure7 = null;

        URI onSuccess8 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return ; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:162:5: ( ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:162:9: ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess ) Newline
            {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:162:9: ( annotation | trigger | consumerResource | supplierResource | externalResource | constraint | onFailure | onSuccess )
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
            case 66:
                {
                alt2=3;
                }
                break;
            case 67:
                {
                alt2=4;
                }
                break;
            case 68:
                {
                alt2=5;
                }
                break;
            case 69:
                {
                alt2=6;
                }
                break;
            case 75:
                {
                alt2=7;
                }
                break;
            case 76:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:162:13: annotation
                    {
                    pushFollow(FOLLOW_annotation_in_statment125);
                    annotation1=annotation();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.setProperty((annotation1!=null?annotation1.key:null), (annotation1!=null?annotation1.value:null)); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:163:13: trigger
                    {
                    pushFollow(FOLLOW_trigger_in_statment148);
                    trigger2=trigger();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addTrigger(trigger2); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:164:13: consumerResource
                    {
                    pushFollow(FOLLOW_consumerResource_in_statment174);
                    consumerResource3=consumerResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addConsumerResource(consumerResource3); 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:165:13: supplierResource
                    {
                    pushFollow(FOLLOW_supplierResource_in_statment191);
                    supplierResource4=supplierResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addSupplierResource(supplierResource4); 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:166:13: externalResource
                    {
                    pushFollow(FOLLOW_externalResource_in_statment208);
                    externalResource5=externalResource();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addExternalResource(externalResource5); 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:167:13: constraint
                    {
                    pushFollow(FOLLOW_constraint_in_statment225);
                    constraint6=constraint();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addCondition(constraint6); 
                    }

                    }
                    break;
                case 7 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:168:13: onFailure
                    {
                    pushFollow(FOLLOW_onFailure_in_statment248);
                    onFailure7=onFailure();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationFailsAction(onFailure7); 
                    }

                    }
                    break;
                case 8 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:169:13: onSuccess
                    {
                    pushFollow(FOLLOW_onSuccess_in_statment272);
                    onSuccess8=onSuccess();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       contract.addOnVerificationSucceedsAction(onSuccess8); 
                    }

                    }
                    break;

            }

            match(input,Newline,FOLLOW_Newline_in_statment294); if (state.failed) return ;

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, statment_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "statment"

    public static class annotation_return extends ParserRuleReturnScope {
        public String key;
        public String value;
    };

    // $ANTLR start "annotation"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:173:1: annotation returns [String key, String value] : AnnotationKey AnnotationValue ;
    public final TreatyScriptParser.annotation_return annotation() throws RecognitionException {
        TreatyScriptParser.annotation_return retval = new TreatyScriptParser.annotation_return();
        retval.start = input.LT(1);
        int annotation_StartIndex = input.index();
        Token AnnotationKey9=null;
        Token AnnotationValue10=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:174:5: ( AnnotationKey AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:174:9: AnnotationKey AnnotationValue
            {
            AnnotationKey9=(Token)match(input,AnnotationKey,FOLLOW_AnnotationKey_in_annotation317); if (state.failed) return retval;
            AnnotationValue10=(Token)match(input,AnnotationValue,FOLLOW_AnnotationValue_in_annotation319); if (state.failed) return retval;
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, annotation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotation"


    // $ANTLR start "trigger"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:181:1: trigger returns [URI value] : Trigger ;
    public final URI trigger() throws RecognitionException {
        URI value = null;
        int trigger_StartIndex = input.index();
        Token Trigger11=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:182:5: ( Trigger )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:182:9: Trigger
            {
            Trigger11=(Token)match(input,Trigger,FOLLOW_Trigger_in_trigger352); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((Trigger11!=null?Trigger11.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, trigger_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "trigger"


    // $ANTLR start "consumerResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:185:1: consumerResource returns [Resource value] : 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute ;
    public final Resource consumerResource() throws RecognitionException {
        Resource value = null;
        int consumerResource_StartIndex = input.index();
        Token Identifier12=null;
        URI resourceTypeAttribute13 = null;

        TreatyScriptParser.resourceNameAttribute_return resourceNameAttribute14 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:186:5: ( 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:186:9: 'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute
            {
            match(input,66,FOLLOW_66_in_consumerResource378); if (state.failed) return value;
            Identifier12=(Token)match(input,Identifier,FOLLOW_Identifier_in_consumerResource380); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_consumerResource382);
            resourceTypeAttribute13=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceNameAttribute_in_consumerResource384);
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, consumerResource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "consumerResource"


    // $ANTLR start "supplierResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:192:1: supplierResource returns [Resource value] : 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute ;
    public final Resource supplierResource() throws RecognitionException {
        Resource value = null;
        int supplierResource_StartIndex = input.index();
        Token Identifier15=null;
        URI resourceTypeAttribute16 = null;

        String resourceReferenceAttribute17 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:193:5: ( 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:193:9: 'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute
            {
            match(input,67,FOLLOW_67_in_supplierResource417); if (state.failed) return value;
            Identifier15=(Token)match(input,Identifier,FOLLOW_Identifier_in_supplierResource419); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_supplierResource421);
            resourceTypeAttribute16=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceReferenceAttribute_in_supplierResource423);
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, supplierResource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "supplierResource"


    // $ANTLR start "externalResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:199:1: externalResource returns [Resource value] : 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute ;
    public final Resource externalResource() throws RecognitionException {
        Resource value = null;
        int externalResource_StartIndex = input.index();
        Token Identifier18=null;
        URI resourceTypeAttribute19 = null;

        TreatyScriptParser.resourceNameAttribute_return resourceNameAttribute20 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:200:5: ( 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:200:9: 'external-resource' Identifier resourceTypeAttribute resourceNameAttribute
            {
            match(input,68,FOLLOW_68_in_externalResource460); if (state.failed) return value;
            Identifier18=(Token)match(input,Identifier,FOLLOW_Identifier_in_externalResource462); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_externalResource464);
            resourceTypeAttribute19=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resourceNameAttribute_in_externalResource466);
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, externalResource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "externalResource"

    public static class resourceNameAttribute_return extends ParserRuleReturnScope {
        public String value;
    };

    // $ANTLR start "resourceNameAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:206:1: resourceNameAttribute returns [String value] : ResourceNameAttribute ;
    public final TreatyScriptParser.resourceNameAttribute_return resourceNameAttribute() throws RecognitionException {
        TreatyScriptParser.resourceNameAttribute_return retval = new TreatyScriptParser.resourceNameAttribute_return();
        retval.start = input.LT(1);
        int resourceNameAttribute_StartIndex = input.index();
        Token ResourceNameAttribute21=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:207:5: ( ResourceNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:207:9: ResourceNameAttribute
            {
            ResourceNameAttribute21=(Token)match(input,ResourceNameAttribute,FOLLOW_ResourceNameAttribute_in_resourceNameAttribute499); if (state.failed) return retval;
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, resourceNameAttribute_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "resourceNameAttribute"


    // $ANTLR start "resourceTypeAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:210:1: resourceTypeAttribute returns [URI value] : ResourceTypeAttribute ;
    public final URI resourceTypeAttribute() throws RecognitionException {
        URI value = null;
        int resourceTypeAttribute_StartIndex = input.index();
        Token ResourceTypeAttribute22=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:211:5: ( ResourceTypeAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:211:9: ResourceTypeAttribute
            {
            ResourceTypeAttribute22=(Token)match(input,ResourceTypeAttribute,FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute525); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((ResourceTypeAttribute22!=null?ResourceTypeAttribute22.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, resourceTypeAttribute_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "resourceTypeAttribute"


    // $ANTLR start "resourceReferenceAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:214:1: resourceReferenceAttribute returns [String value] : ResourceReferenceAttribute ;
    public final String resourceReferenceAttribute() throws RecognitionException {
        String value = null;
        int resourceReferenceAttribute_StartIndex = input.index();
        Token ResourceReferenceAttribute23=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:215:5: ( ResourceReferenceAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:215:9: ResourceReferenceAttribute
            {
            ResourceReferenceAttribute23=(Token)match(input,ResourceReferenceAttribute,FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute551); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (ResourceReferenceAttribute23!=null?ResourceReferenceAttribute23.getText():null); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, resourceReferenceAttribute_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "resourceReferenceAttribute"


    // $ANTLR start "constraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:218:1: constraint returns [Condition value] : 'constraint' xorConstraint ;
    public final Condition constraint() throws RecognitionException {
        Condition value = null;
        int constraint_StartIndex = input.index();
        TreatyScriptParser.xorConstraint_return xorConstraint24 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:219:5: ( 'constraint' xorConstraint )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:219:9: 'constraint' xorConstraint
            {
            match(input,69,FOLLOW_69_in_constraint580); if (state.failed) return value;
            pushFollow(FOLLOW_xorConstraint_in_constraint582);
            xorConstraint24=xorConstraint();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (xorConstraint24!=null?xorConstraint24.value:null); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, constraint_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "constraint"

    public static class xorConstraint_return extends ParserRuleReturnScope {
        public Condition value;
        public ExclusiveDisjunctiveCondition xDisjunction;
    };

    // $ANTLR start "xorConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:222:1: xorConstraint returns [Condition value, ExclusiveDisjunctiveCondition xDisjunction] : ( ( orConstraint ( XOr orConstraint )+ )=>firstConstraint= orConstraint ( XOr nextConstraint= orConstraint )+ | orConstraint );
    public final TreatyScriptParser.xorConstraint_return xorConstraint() throws RecognitionException {
        TreatyScriptParser.xorConstraint_return retval = new TreatyScriptParser.xorConstraint_return();
        retval.start = input.LT(1);
        int xorConstraint_StartIndex = input.index();
        TreatyScriptParser.orConstraint_return firstConstraint = null;

        TreatyScriptParser.orConstraint_return nextConstraint = null;

        TreatyScriptParser.orConstraint_return orConstraint25 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:5: ( ( orConstraint ( XOr orConstraint )+ )=>firstConstraint= orConstraint ( XOr nextConstraint= orConstraint )+ | orConstraint )
            int alt4=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA4_1 = input.LA(2);

                if ( (synpred11_TreatyScript()) ) {
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

                if ( (synpred11_TreatyScript()) ) {
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
            case 70:
                {
                int LA4_3 = input.LA(2);

                if ( (synpred11_TreatyScript()) ) {
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

                if ( (synpred11_TreatyScript()) ) {
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:9: ( orConstraint ( XOr orConstraint )+ )=>firstConstraint= orConstraint ( XOr nextConstraint= orConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction = new ExclusiveDisjunctiveCondition(); retval.value = retval.xDisjunction; 
                    }
                    pushFollow(FOLLOW_orConstraint_in_xorConstraint625);
                    firstConstraint=orConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.xDisjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:213: ( XOr nextConstraint= orConstraint )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==XOr) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:214: XOr nextConstraint= orConstraint
                    	    {
                    	    match(input,XOr,FOLLOW_XOr_in_xorConstraint630); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_orConstraint_in_xorConstraint634);
                    	    nextConstraint=orConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.xDisjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:224:9: orConstraint
                    {
                    pushFollow(FOLLOW_orConstraint_in_xorConstraint648);
                    orConstraint25=orConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (orConstraint25!=null?orConstraint25.value:null); 
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, xorConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "xorConstraint"

    public static class orConstraint_return extends ParserRuleReturnScope {
        public Condition value;
        public DisjunctiveCondition disjunction;
    };

    // $ANTLR start "orConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:227:1: orConstraint returns [Condition value, DisjunctiveCondition disjunction] : ( ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+ | andConstraint );
    public final TreatyScriptParser.orConstraint_return orConstraint() throws RecognitionException {
        TreatyScriptParser.orConstraint_return retval = new TreatyScriptParser.orConstraint_return();
        retval.start = input.LT(1);
        int orConstraint_StartIndex = input.index();
        TreatyScriptParser.andConstraint_return firstConstraint = null;

        TreatyScriptParser.andConstraint_return nextConstraint = null;

        TreatyScriptParser.andConstraint_return andConstraint26 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:5: ( ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+ | andConstraint )
            int alt6=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA6_1 = input.LA(2);

                if ( (synpred14_TreatyScript()) ) {
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

                if ( (synpred14_TreatyScript()) ) {
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
            case 70:
                {
                int LA6_3 = input.LA(2);

                if ( (synpred14_TreatyScript()) ) {
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

                if ( (synpred14_TreatyScript()) ) {
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:9: ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.disjunction = new DisjunctiveCondition(); retval.value = retval.disjunction; 
                    }
                    pushFollow(FOLLOW_andConstraint_in_orConstraint719);
                    firstConstraint=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.disjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:203: ( Or nextConstraint= andConstraint )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==Or) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:204: Or nextConstraint= andConstraint
                    	    {
                    	    match(input,Or,FOLLOW_Or_in_orConstraint724); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_andConstraint_in_orConstraint728);
                    	    nextConstraint=andConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.disjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:229:9: andConstraint
                    {
                    pushFollow(FOLLOW_andConstraint_in_orConstraint742);
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, orConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "orConstraint"

    public static class andConstraint_return extends ParserRuleReturnScope {
        public Condition value;
        public ConjunctiveCondition conjunction;
    };

    // $ANTLR start "andConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:232:1: andConstraint returns [Condition value, ConjunctiveCondition conjunction] : ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint );
    public final TreatyScriptParser.andConstraint_return andConstraint() throws RecognitionException {
        TreatyScriptParser.andConstraint_return retval = new TreatyScriptParser.andConstraint_return();
        retval.start = input.LT(1);
        int andConstraint_StartIndex = input.index();
        TreatyScriptParser.notConstraint_return firstConstraint = null;

        TreatyScriptParser.notConstraint_return nextConstraint = null;

        TreatyScriptParser.notConstraint_return notConstraint27 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:5: ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint )
            int alt8=2;
            switch ( input.LA(1) ) {
            case Not:
                {
                int LA8_1 = input.LA(2);

                if ( (synpred17_TreatyScript()) ) {
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

                if ( (synpred17_TreatyScript()) ) {
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
            case 70:
                {
                int LA8_3 = input.LA(2);

                if ( (synpred17_TreatyScript()) ) {
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

                if ( (synpred17_TreatyScript()) ) {
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:9: ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.conjunction = new ConjunctiveCondition(); retval.value = retval.conjunction; 
                    }
                    pushFollow(FOLLOW_notConstraint_in_andConstraint809);
                    firstConstraint=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.conjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:204: ( And nextConstraint= notConstraint )+
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
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:205: And nextConstraint= notConstraint
                    	    {
                    	    match(input,And,FOLLOW_And_in_andConstraint814); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_notConstraint_in_andConstraint818);
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:234:9: notConstraint
                    {
                    pushFollow(FOLLOW_notConstraint_in_andConstraint832);
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, andConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "andConstraint"

    public static class notConstraint_return extends ParserRuleReturnScope {
        public Condition value;
        public NegatedCondition negation;
    };

    // $ANTLR start "notConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:237:1: notConstraint returns [Condition value, NegatedCondition negation] : ( Not condition= notConstraint | LParen xorConstraint RParen | existsConstraint | relationshipConstraint | propertyConstraint );
    public final TreatyScriptParser.notConstraint_return notConstraint() throws RecognitionException {
        TreatyScriptParser.notConstraint_return retval = new TreatyScriptParser.notConstraint_return();
        retval.start = input.LT(1);
        int notConstraint_StartIndex = input.index();
        TreatyScriptParser.notConstraint_return condition = null;

        TreatyScriptParser.xorConstraint_return xorConstraint28 = null;

        ExistsCondition existsConstraint29 = null;

        TreatyScriptParser.relationshipConstraint_return relationshipConstraint30 = null;

        TreatyScriptParser.propertyConstraint_return propertyConstraint31 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:238:5: ( Not condition= notConstraint | LParen xorConstraint RParen | existsConstraint | relationshipConstraint | propertyConstraint )
            int alt9=5;
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
            case 70:
                {
                alt9=3;
                }
                break;
            case Identifier:
                {
                int LA9_4 = input.LA(2);

                if ( (LA9_4==Uri) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==Identifier) ) {
                        alt9=4;
                    }
                    else if ( ((LA9_5>=IntegerLiteral && LA9_5<=StringLiteral)||(LA9_5>=73 && LA9_5<=74)) ) {
                        alt9=5;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;
                    }
                }
                else if ( ((LA9_4>=Equal && LA9_4<=Lte)||(LA9_4>=71 && LA9_4<=72)) ) {
                    alt9=5;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 4, input);

                    throw nvae;
                }
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:238:9: Not condition= notConstraint
                    {
                    match(input,Not,FOLLOW_Not_in_notConstraint883); if (state.failed) return retval;
                    pushFollow(FOLLOW_notConstraint_in_notConstraint887);
                    condition=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.negation = new NegatedCondition(); retval.negation.addCondition((condition!=null?condition.value:null)); retval.value = retval.negation; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:239:9: LParen xorConstraint RParen
                    {
                    match(input,LParen,FOLLOW_LParen_in_notConstraint900); if (state.failed) return retval;
                    pushFollow(FOLLOW_xorConstraint_in_notConstraint902);
                    xorConstraint28=xorConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RParen,FOLLOW_RParen_in_notConstraint904); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (xorConstraint28!=null?xorConstraint28.value:null); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:240:9: existsConstraint
                    {
                    pushFollow(FOLLOW_existsConstraint_in_notConstraint917);
                    existsConstraint29=existsConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = existsConstraint29; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:241:9: relationshipConstraint
                    {
                    pushFollow(FOLLOW_relationshipConstraint_in_notConstraint941);
                    relationshipConstraint30=relationshipConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (relationshipConstraint30!=null?relationshipConstraint30.value:null); 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:242:9: propertyConstraint
                    {
                    pushFollow(FOLLOW_propertyConstraint_in_notConstraint959);
                    propertyConstraint31=propertyConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (propertyConstraint31!=null?propertyConstraint31.value:null); 
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
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, notConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "notConstraint"


    // $ANTLR start "existsConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:245:1: existsConstraint returns [ExistsCondition value] : 'mustexist' resource ;
    public final ExistsCondition existsConstraint() throws RecognitionException {
        ExistsCondition value = null;
        int existsConstraint_StartIndex = input.index();
        Resource resource32 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:246:5: ( 'mustexist' resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:246:9: 'mustexist' resource
            {
            match(input,70,FOLLOW_70_in_existsConstraint994); if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_existsConstraint996);
            resource32=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new ExistsCondition();
                          value.setResource(resource32);
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, existsConstraint_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "existsConstraint"

    public static class relationshipConstraint_return extends ParserRuleReturnScope {
        public RelationshipCondition value;
    };

    // $ANTLR start "relationshipConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:253:1: relationshipConstraint returns [RelationshipCondition value] : leftResource= resource relationshipType rightResource= resource {...}?;
    public final TreatyScriptParser.relationshipConstraint_return relationshipConstraint() throws RecognitionException {
        TreatyScriptParser.relationshipConstraint_return retval = new TreatyScriptParser.relationshipConstraint_return();
        retval.start = input.LT(1);
        int relationshipConstraint_StartIndex = input.index();
        Resource leftResource = null;

        Resource rightResource = null;

        URI relationshipType33 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:254:5: (leftResource= resource relationshipType rightResource= resource {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:254:9: leftResource= resource relationshipType rightResource= resource {...}?
            {
            pushFollow(FOLLOW_resource_in_relationshipConstraint1031);
            leftResource=resource();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_relationshipType_in_relationshipConstraint1033);
            relationshipType33=relationshipType();

            state._fsp--;
            if (state.failed) return retval;
            pushFollow(FOLLOW_resource_in_relationshipConstraint1037);
            rightResource=resource();

            state._fsp--;
            if (state.failed) return retval;
            if ( !(( isValidRelationship(leftResource, relationshipType33, rightResource) )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "relationshipConstraint", " isValidRelationship($leftResource.value, $relationshipType.value, $rightResource.value) ");
            }
            if ( state.backtracking==0 ) {

                          retval.value = createRelationshipCondition(leftResource, relationshipType33, rightResource);
                      
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (FailedPredicateException ex) {

                    throw new TreatyRecognitionException(input,
                        "'" + input.toString(retval.start,input.LT(-1)) + "' is not a valid relationship constraint.");
                
        }
        catch (TreatyException ex) {

                    throw new TreatyRecognitionException(input, ex);
                
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, relationshipConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationshipConstraint"


    // $ANTLR start "relationshipType"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:268:1: relationshipType returns [URI value] : Uri ;
    public final URI relationshipType() throws RecognitionException {
        URI value = null;
        int relationshipType_StartIndex = input.index();
        Token Uri34=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:269:5: ( Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:269:9: Uri
            {
            Uri34=(Token)match(input,Uri,FOLLOW_Uri_in_relationshipType1100); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((Uri34!=null?Uri34.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, relationshipType_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "relationshipType"

    public static class propertyConstraint_return extends ParserRuleReturnScope {
        public PropertyCondition value;
    };

    // $ANTLR start "propertyConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:272:1: propertyConstraint returns [PropertyCondition value] : ( builtinProperyConstraint | resource propertyOperator propertyValue {...}?);
    public final TreatyScriptParser.propertyConstraint_return propertyConstraint() throws RecognitionException {
        TreatyScriptParser.propertyConstraint_return retval = new TreatyScriptParser.propertyConstraint_return();
        retval.start = input.LT(1);
        int propertyConstraint_StartIndex = input.index();
        PropertyCondition builtinProperyConstraint35 = null;

        Resource resource36 = null;

        URI propertyOperator37 = null;

        Object propertyValue38 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:273:5: ( builtinProperyConstraint | resource propertyOperator propertyValue {...}?)
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Identifier) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==Uri) ) {
                    alt10=2;
                }
                else if ( ((LA10_1>=Equal && LA10_1<=Lte)||(LA10_1>=71 && LA10_1<=72)) ) {
                    alt10=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:273:9: builtinProperyConstraint
                    {
                    pushFollow(FOLLOW_builtinProperyConstraint_in_propertyConstraint1126);
                    builtinProperyConstraint35=builtinProperyConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {

                                  retval.value = builtinProperyConstraint35;
                              
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:277:9: resource propertyOperator propertyValue {...}?
                    {
                    pushFollow(FOLLOW_resource_in_propertyConstraint1146);
                    resource36=resource();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_propertyOperator_in_propertyConstraint1148);
                    propertyOperator37=propertyOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    pushFollow(FOLLOW_propertyValue_in_propertyConstraint1150);
                    propertyValue38=propertyValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( !(( isValidProperty(resource36, propertyOperator37, propertyValue38) )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "propertyConstraint", " isValidProperty($resource.value, $propertyOperator.value, $propertyValue.value) ");
                    }
                    if ( state.backtracking==0 ) {

                                  retval.value = createPropertyCondition(resource36, propertyOperator37, propertyValue38);
                              
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (FailedPredicateException ex) {

                    throw new TreatyRecognitionException(input, 
                        "'" + input.toString(retval.start,input.LT(-1)) + "' is not a valid property constraint.");
                
        }
        catch (TreatyException ex) {

                    throw new TreatyRecognitionException(input, ex);
                
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, propertyConstraint_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "propertyConstraint"


    // $ANTLR start "propertyOperator"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:291:1: propertyOperator returns [URI value] : Uri ;
    public final URI propertyOperator() throws RecognitionException {
        URI value = null;
        int propertyOperator_StartIndex = input.index();
        Token Uri39=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:292:5: ( Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:292:9: Uri
            {
            Uri39=(Token)match(input,Uri,FOLLOW_Uri_in_propertyOperator1217); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((Uri39!=null?Uri39.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, propertyOperator_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "propertyOperator"


    // $ANTLR start "builtinProperyConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:295:1: builtinProperyConstraint returns [PropertyCondition value] : ( decimalPropertyConstraint | stringPropertyConstraint );
    public final PropertyCondition builtinProperyConstraint() throws RecognitionException {
        PropertyCondition value = null;
        int builtinProperyConstraint_StartIndex = input.index();
        PropertyCondition decimalPropertyConstraint40 = null;

        PropertyCondition stringPropertyConstraint41 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:296:5: ( decimalPropertyConstraint | stringPropertyConstraint )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==Identifier) ) {
                switch ( input.LA(2) ) {
                case Equal:
                    {
                    int LA11_2 = input.LA(3);

                    if ( (LA11_2==StringLiteral) ) {
                        alt11=2;
                    }
                    else if ( ((LA11_2>=IntegerLiteral && LA11_2<=FloatingPointLiteral)) ) {
                        alt11=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return value;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case NotEqual:
                    {
                    int LA11_3 = input.LA(3);

                    if ( (LA11_3==StringLiteral) ) {
                        alt11=2;
                    }
                    else if ( ((LA11_3>=IntegerLiteral && LA11_3<=FloatingPointLiteral)) ) {
                        alt11=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return value;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case 71:
                case 72:
                    {
                    alt11=2;
                    }
                    break;
                case Gt:
                case Gte:
                case Lt:
                case Lte:
                    {
                    alt11=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return value;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:296:9: decimalPropertyConstraint
                    {
                    pushFollow(FOLLOW_decimalPropertyConstraint_in_builtinProperyConstraint1247);
                    decimalPropertyConstraint40=decimalPropertyConstraint();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = decimalPropertyConstraint40; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:297:9: stringPropertyConstraint
                    {
                    pushFollow(FOLLOW_stringPropertyConstraint_in_builtinProperyConstraint1260);
                    stringPropertyConstraint41=stringPropertyConstraint();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = stringPropertyConstraint41; 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, builtinProperyConstraint_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "builtinProperyConstraint"


    // $ANTLR start "decimalPropertyConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:300:1: decimalPropertyConstraint returns [PropertyCondition value] : decimalResource decimalPropertyOperator decimalLiteral ;
    public final PropertyCondition decimalPropertyConstraint() throws RecognitionException {
        PropertyCondition value = null;
        int decimalPropertyConstraint_StartIndex = input.index();
        Resource decimalResource42 = null;

        URI decimalPropertyOperator43 = null;

        Object decimalLiteral44 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:301:5: ( decimalResource decimalPropertyOperator decimalLiteral )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:301:9: decimalResource decimalPropertyOperator decimalLiteral
            {
            pushFollow(FOLLOW_decimalResource_in_decimalPropertyConstraint1287);
            decimalResource42=decimalResource();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_decimalPropertyOperator_in_decimalPropertyConstraint1289);
            decimalPropertyOperator43=decimalPropertyOperator();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_decimalLiteral_in_decimalPropertyConstraint1291);
            decimalLiteral44=decimalLiteral();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createPropertyCondition(decimalResource42, decimalPropertyOperator43, decimalLiteral44);
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, decimalPropertyConstraint_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "decimalPropertyConstraint"


    // $ANTLR start "decimalResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:307:1: decimalResource returns [Resource value] : resource ;
    public final Resource decimalResource() throws RecognitionException {
        Resource value = null;
        int decimalResource_StartIndex = input.index();
        Resource resource45 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:308:5: ( resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:308:9: resource
            {
            pushFollow(FOLLOW_resource_in_decimalResource1324);
            resource45=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = resource45; 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, decimalResource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "decimalResource"


    // $ANTLR start "decimalPropertyOperator"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:311:1: decimalPropertyOperator returns [URI value] : ( Equal | NotEqual | Gt | Gte | Lt | Lte );
    public final URI decimalPropertyOperator() throws RecognitionException {
        URI value = null;
        int decimalPropertyOperator_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:312:5: ( Equal | NotEqual | Gt | Gte | Lt | Lte )
            int alt12=6;
            switch ( input.LA(1) ) {
            case Equal:
                {
                alt12=1;
                }
                break;
            case NotEqual:
                {
                alt12=2;
                }
                break;
            case Gt:
                {
                alt12=3;
                }
                break;
            case Gte:
                {
                alt12=4;
                }
                break;
            case Lt:
                {
                alt12=5;
                }
                break;
            case Lte:
                {
                alt12=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:312:9: Equal
                    {
                    match(input,Equal,FOLLOW_Equal_in_decimalPropertyOperator1350); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#eq"); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:313:9: NotEqual
                    {
                    match(input,NotEqual,FOLLOW_NotEqual_in_decimalPropertyOperator1366); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#neq"); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:314:9: Gt
                    {
                    match(input,Gt,FOLLOW_Gt_in_decimalPropertyOperator1379); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#gt"); 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:315:9: Gte
                    {
                    match(input,Gte,FOLLOW_Gte_in_decimalPropertyOperator1398); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#gte"); 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:316:9: Lt
                    {
                    match(input,Lt,FOLLOW_Lt_in_decimalPropertyOperator1416); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#lt"); 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:317:9: Lte
                    {
                    match(input,Lte,FOLLOW_Lte_in_decimalPropertyOperator1435); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#lte"); 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, decimalPropertyOperator_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "decimalPropertyOperator"


    // $ANTLR start "decimalLiteral"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:320:1: decimalLiteral returns [Object value] : ( integerLiteral | floatingPointLiteral );
    public final Object decimalLiteral() throws RecognitionException {
        Object value = null;
        int decimalLiteral_StartIndex = input.index();
        Integer integerLiteral46 = null;

        Double floatingPointLiteral47 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:321:5: ( integerLiteral | floatingPointLiteral )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IntegerLiteral) ) {
                alt13=1;
            }
            else if ( (LA13_0==FloatingPointLiteral) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:321:9: integerLiteral
                    {
                    pushFollow(FOLLOW_integerLiteral_in_decimalLiteral1466);
                    integerLiteral46=integerLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = integerLiteral46; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:322:9: floatingPointLiteral
                    {
                    pushFollow(FOLLOW_floatingPointLiteral_in_decimalLiteral1485);
                    floatingPointLiteral47=floatingPointLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = floatingPointLiteral47; 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, decimalLiteral_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "decimalLiteral"


    // $ANTLR start "stringPropertyConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:325:1: stringPropertyConstraint returns [PropertyCondition value] : stringResource stringPropertyOperator stringLiteral ;
    public final PropertyCondition stringPropertyConstraint() throws RecognitionException {
        PropertyCondition value = null;
        int stringPropertyConstraint_StartIndex = input.index();
        Resource stringResource48 = null;

        URI stringPropertyOperator49 = null;

        Object stringLiteral50 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:326:5: ( stringResource stringPropertyOperator stringLiteral )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:326:9: stringResource stringPropertyOperator stringLiteral
            {
            pushFollow(FOLLOW_stringResource_in_stringPropertyConstraint1511);
            stringResource48=stringResource();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_stringPropertyOperator_in_stringPropertyConstraint1513);
            stringPropertyOperator49=stringPropertyOperator();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_stringLiteral_in_stringPropertyConstraint1515);
            stringLiteral50=stringLiteral();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createPropertyCondition(stringResource48, stringPropertyOperator49, stringLiteral50);
                      
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, stringPropertyConstraint_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "stringPropertyConstraint"


    // $ANTLR start "stringResource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:332:1: stringResource returns [Resource value] : resource ;
    public final Resource stringResource() throws RecognitionException {
        Resource value = null;
        int stringResource_StartIndex = input.index();
        Resource resource51 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:333:5: ( resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:333:9: resource
            {
            pushFollow(FOLLOW_resource_in_stringResource1548);
            resource51=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = resource51; 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, stringResource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "stringResource"


    // $ANTLR start "stringPropertyOperator"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:336:1: stringPropertyOperator returns [URI value] : ( Equal | NotEqual | 'matches' | 'in' );
    public final URI stringPropertyOperator() throws RecognitionException {
        URI value = null;
        int stringPropertyOperator_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:337:5: ( Equal | NotEqual | 'matches' | 'in' )
            int alt14=4;
            switch ( input.LA(1) ) {
            case Equal:
                {
                alt14=1;
                }
                break;
            case NotEqual:
                {
                alt14=2;
                }
                break;
            case 71:
                {
                alt14=3;
                }
                break;
            case 72:
                {
                alt14=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:337:9: Equal
                    {
                    match(input,Equal,FOLLOW_Equal_in_stringPropertyOperator1574); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#eq"); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:338:9: NotEqual
                    {
                    match(input,NotEqual,FOLLOW_NotEqual_in_stringPropertyOperator1591); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#neq"); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:339:9: 'matches'
                    {
                    match(input,71,FOLLOW_71_in_stringPropertyOperator1605); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#matches"); 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:340:9: 'in'
                    {
                    match(input,72,FOLLOW_72_in_stringPropertyOperator1618); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new URI("http://www.treaty.org/builtin/#in"); 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, stringPropertyOperator_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "stringPropertyOperator"


    // $ANTLR start "propertyValue"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:343:1: propertyValue returns [Object value] : ( integerLiteral | floatingPointLiteral | booleanLiteral | stringLiteral );
    public final Object propertyValue() throws RecognitionException {
        Object value = null;
        int propertyValue_StartIndex = input.index();
        Integer integerLiteral52 = null;

        Double floatingPointLiteral53 = null;

        Object booleanLiteral54 = null;

        Object stringLiteral55 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:344:5: ( integerLiteral | floatingPointLiteral | booleanLiteral | stringLiteral )
            int alt15=4;
            switch ( input.LA(1) ) {
            case IntegerLiteral:
                {
                alt15=1;
                }
                break;
            case FloatingPointLiteral:
                {
                alt15=2;
                }
                break;
            case 73:
            case 74:
                {
                alt15=3;
                }
                break;
            case StringLiteral:
                {
                alt15=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:344:9: integerLiteral
                    {
                    pushFollow(FOLLOW_integerLiteral_in_propertyValue1649);
                    integerLiteral52=integerLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = integerLiteral52; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:345:9: floatingPointLiteral
                    {
                    pushFollow(FOLLOW_floatingPointLiteral_in_propertyValue1668);
                    floatingPointLiteral53=floatingPointLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = floatingPointLiteral53; 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:346:9: booleanLiteral
                    {
                    pushFollow(FOLLOW_booleanLiteral_in_propertyValue1681);
                    booleanLiteral54=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = booleanLiteral54; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:347:9: stringLiteral
                    {
                    pushFollow(FOLLOW_stringLiteral_in_propertyValue1700);
                    stringLiteral55=stringLiteral();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = stringLiteral55; 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, propertyValue_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "propertyValue"


    // $ANTLR start "integerLiteral"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:350:1: integerLiteral returns [Integer value] : IntegerLiteral ;
    public final Integer integerLiteral() throws RecognitionException {
        Integer value = null;
        int integerLiteral_StartIndex = input.index();
        Token IntegerLiteral56=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:351:5: ( IntegerLiteral )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:351:9: IntegerLiteral
            {
            IntegerLiteral56=(Token)match(input,IntegerLiteral,FOLLOW_IntegerLiteral_in_integerLiteral1733); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = Integer.parseInt((IntegerLiteral56!=null?IntegerLiteral56.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, integerLiteral_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "integerLiteral"


    // $ANTLR start "floatingPointLiteral"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:354:1: floatingPointLiteral returns [Double value] : FloatingPointLiteral ;
    public final Double floatingPointLiteral() throws RecognitionException {
        Double value = null;
        int floatingPointLiteral_StartIndex = input.index();
        Token FloatingPointLiteral57=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:355:5: ( FloatingPointLiteral )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:355:9: FloatingPointLiteral
            {
            FloatingPointLiteral57=(Token)match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_floatingPointLiteral1759); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = Double.parseDouble((FloatingPointLiteral57!=null?FloatingPointLiteral57.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 31, floatingPointLiteral_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "floatingPointLiteral"


    // $ANTLR start "booleanLiteral"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:358:1: booleanLiteral returns [Object value] : ( 'true' | 'false' );
    public final Object booleanLiteral() throws RecognitionException {
        Object value = null;
        int booleanLiteral_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:359:5: ( 'true' | 'false' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==73) ) {
                alt16=1;
            }
            else if ( (LA16_0==74) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:359:9: 'true'
                    {
                    match(input,73,FOLLOW_73_in_booleanLiteral1785); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = true; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:360:9: 'false'
                    {
                    match(input,74,FOLLOW_74_in_booleanLiteral1799); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = false; 
                    }

                    }
                    break;

            }
        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 32, booleanLiteral_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "booleanLiteral"


    // $ANTLR start "stringLiteral"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:363:1: stringLiteral returns [Object value] : StringLiteral ;
    public final Object stringLiteral() throws RecognitionException {
        Object value = null;
        int stringLiteral_StartIndex = input.index();
        Token StringLiteral58=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:364:5: ( StringLiteral )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:364:9: StringLiteral
            {
            StringLiteral58=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_stringLiteral1825); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = (StringLiteral58!=null?StringLiteral58.getText():null); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 33, stringLiteral_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "stringLiteral"


    // $ANTLR start "resource"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:367:1: resource returns [Resource value] : resourceId= Identifier {...}?;
    public final Resource resource() throws RecognitionException {
        Resource value = null;
        int resource_StartIndex = input.index();
        Token resourceId=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:368:5: (resourceId= Identifier {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:368:9: resourceId= Identifier {...}?
            {
            resourceId=(Token)match(input,Identifier,FOLLOW_Identifier_in_resource1853); if (state.failed) return value;
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

                    throw new TreatyRecognitionException(input, "The resource '" + (resourceId!=null?resourceId.getText():null) + "' has not been declared.");
                
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 34, resource_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "resource"


    // $ANTLR start "onFailure"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:377:1: onFailure returns [URI value] : 'onfailure' action= Uri ;
    public final URI onFailure() throws RecognitionException {
        URI value = null;
        int onFailure_StartIndex = input.index();
        Token action=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:378:5: ( 'onfailure' action= Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:378:9: 'onfailure' action= Uri
            {
            match(input,75,FOLLOW_75_in_onFailure1899); if (state.failed) return value;
            action=(Token)match(input,Uri,FOLLOW_Uri_in_onFailure1903); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action!=null?action.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 35, onFailure_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "onFailure"


    // $ANTLR start "onSuccess"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:381:1: onSuccess returns [URI value] : 'onsuccess' action= Uri ;
    public final URI onSuccess() throws RecognitionException {
        URI value = null;
        int onSuccess_StartIndex = input.index();
        Token action=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:382:5: ( 'onsuccess' action= Uri )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:382:9: 'onsuccess' action= Uri
            {
            match(input,76,FOLLOW_76_in_onSuccess1929); if (state.failed) return value;
            action=(Token)match(input,Uri,FOLLOW_Uri_in_onSuccess1933); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action!=null?action.getText():null)); 
            }

            }

        }

            catch (RecognitionException e) {
                reportError(e);
                throw e;
            } catch (Exception e) {
                throw new TreatyRecognitionException(input, e);
            }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 36, onSuccess_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "onSuccess"

    // $ANTLR start synpred11_TreatyScript
    public final void synpred11_TreatyScript_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:9: ( orConstraint ( XOr orConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:10: orConstraint ( XOr orConstraint )+
        {
        pushFollow(FOLLOW_orConstraint_in_synpred11_TreatyScript609);
        orConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:23: ( XOr orConstraint )+
        int cnt17=0;
        loop17:
        do {
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==XOr) ) {
                alt17=1;
            }


            switch (alt17) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:223:24: XOr orConstraint
        	    {
        	    match(input,XOr,FOLLOW_XOr_in_synpred11_TreatyScript612); if (state.failed) return ;
        	    pushFollow(FOLLOW_orConstraint_in_synpred11_TreatyScript614);
        	    orConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt17 >= 1 ) break loop17;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(17, input);
                    throw eee;
            }
            cnt17++;
        } while (true);


        }
    }
    // $ANTLR end synpred11_TreatyScript

    // $ANTLR start synpred14_TreatyScript
    public final void synpred14_TreatyScript_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:9: ( andConstraint ( Or andConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:10: andConstraint ( Or andConstraint )+
        {
        pushFollow(FOLLOW_andConstraint_in_synpred14_TreatyScript703);
        andConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:24: ( Or andConstraint )+
        int cnt18=0;
        loop18:
        do {
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==Or) ) {
                alt18=1;
            }


            switch (alt18) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:228:25: Or andConstraint
        	    {
        	    match(input,Or,FOLLOW_Or_in_synpred14_TreatyScript706); if (state.failed) return ;
        	    pushFollow(FOLLOW_andConstraint_in_synpred14_TreatyScript708);
        	    andConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt18 >= 1 ) break loop18;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(18, input);
                    throw eee;
            }
            cnt18++;
        } while (true);


        }
    }
    // $ANTLR end synpred14_TreatyScript

    // $ANTLR start synpred17_TreatyScript
    public final void synpred17_TreatyScript_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:9: ( notConstraint ( And notConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:10: notConstraint ( And notConstraint )+
        {
        pushFollow(FOLLOW_notConstraint_in_synpred17_TreatyScript793);
        notConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:24: ( And notConstraint )+
        int cnt19=0;
        loop19:
        do {
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==And) ) {
                alt19=1;
            }


            switch (alt19) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/TreatyScript.g:233:25: And notConstraint
        	    {
        	    match(input,And,FOLLOW_And_in_synpred17_TreatyScript796); if (state.failed) return ;
        	    pushFollow(FOLLOW_notConstraint_in_synpred17_TreatyScript798);
        	    notConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt19 >= 1 ) break loop19;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(19, input);
                    throw eee;
            }
            cnt19++;
        } while (true);


        }
    }
    // $ANTLR end synpred17_TreatyScript

    // Delegated rules

    public final boolean synpred17_TreatyScript() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_TreatyScript_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_TreatyScript() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_TreatyScript_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_TreatyScript() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_TreatyScript_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_statment_in_contract94 = new BitSet(new long[]{0x00000000000000B0L,0x000000000000183CL});
    public static final BitSet FOLLOW_Newline_in_contract98 = new BitSet(new long[]{0x00000000000000B0L,0x000000000000183CL});
    public static final BitSet FOLLOW_EOF_in_contract102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_statment125 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_trigger_in_statment148 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_consumerResource_in_statment174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_supplierResource_in_statment191 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_externalResource_in_statment208 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_in_statment225 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onFailure_in_statment248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_onSuccess_in_statment272 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_Newline_in_statment294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AnnotationKey_in_annotation317 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AnnotationValue_in_annotation319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Trigger_in_trigger352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_consumerResource378 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_consumerResource380 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_consumerResource382 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_resourceNameAttribute_in_consumerResource384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_supplierResource417 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_supplierResource419 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_supplierResource421 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_resourceReferenceAttribute_in_supplierResource423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_externalResource460 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_externalResource462 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_externalResource464 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_resourceNameAttribute_in_externalResource466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceNameAttribute_in_resourceNameAttribute499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_constraint580 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_xorConstraint_in_constraint582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orConstraint_in_xorConstraint625 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_XOr_in_xorConstraint630 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_orConstraint_in_xorConstraint634 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_orConstraint_in_xorConstraint648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint719 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_Or_in_orConstraint724 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint728 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint809 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_andConstraint814 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint818 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Not_in_notConstraint883 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_notConstraint_in_notConstraint887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LParen_in_notConstraint900 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_xorConstraint_in_notConstraint902 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RParen_in_notConstraint904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existsConstraint_in_notConstraint917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationshipConstraint_in_notConstraint941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyConstraint_in_notConstraint959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_existsConstraint994 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_resource_in_existsConstraint996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint1031 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_relationshipType_in_relationshipConstraint1033 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint1037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Uri_in_relationshipType1100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinProperyConstraint_in_propertyConstraint1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_propertyConstraint1146 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_propertyOperator_in_propertyConstraint1148 = new BitSet(new long[]{0x000000000E000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_propertyValue_in_propertyConstraint1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Uri_in_propertyOperator1217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decimalPropertyConstraint_in_builtinProperyConstraint1247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringPropertyConstraint_in_builtinProperyConstraint1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decimalResource_in_decimalPropertyConstraint1287 = new BitSet(new long[]{0x0000000001F80000L});
    public static final BitSet FOLLOW_decimalPropertyOperator_in_decimalPropertyConstraint1289 = new BitSet(new long[]{0x0000000006000000L});
    public static final BitSet FOLLOW_decimalLiteral_in_decimalPropertyConstraint1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_decimalResource1324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Equal_in_decimalPropertyOperator1350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NotEqual_in_decimalPropertyOperator1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Gt_in_decimalPropertyOperator1379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Gte_in_decimalPropertyOperator1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Lt_in_decimalPropertyOperator1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Lte_in_decimalPropertyOperator1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integerLiteral_in_decimalLiteral1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_floatingPointLiteral_in_decimalLiteral1485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringResource_in_stringPropertyConstraint1511 = new BitSet(new long[]{0x0000000000180000L,0x0000000000000180L});
    public static final BitSet FOLLOW_stringPropertyOperator_in_stringPropertyConstraint1513 = new BitSet(new long[]{0x000000000E000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_stringLiteral_in_stringPropertyConstraint1515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_stringResource1548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Equal_in_stringPropertyOperator1574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NotEqual_in_stringPropertyOperator1591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_stringPropertyOperator1605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_stringPropertyOperator1618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integerLiteral_in_propertyValue1649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_floatingPointLiteral_in_propertyValue1668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_propertyValue1681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_propertyValue1700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IntegerLiteral_in_integerLiteral1733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FloatingPointLiteral_in_floatingPointLiteral1759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_booleanLiteral1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_booleanLiteral1799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_stringLiteral1825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_resource1853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_onFailure1899 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_Uri_in_onFailure1903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_onSuccess1929 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_Uri_in_onSuccess1933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orConstraint_in_synpred11_TreatyScript609 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_XOr_in_synpred11_TreatyScript612 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_orConstraint_in_synpred11_TreatyScript614 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_andConstraint_in_synpred14_TreatyScript703 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_Or_in_synpred14_TreatyScript706 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_andConstraint_in_synpred14_TreatyScript708 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_notConstraint_in_synpred17_TreatyScript793 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_And_in_synpred17_TreatyScript796 = new BitSet(new long[]{0x0000000000018100L,0x0000000000000040L});
    public static final BitSet FOLLOW_notConstraint_in_synpred17_TreatyScript798 = new BitSet(new long[]{0x0000000000004002L});

}