// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g 2009-10-30 20:34:35

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Newline", "AnnotationKey", "AnnotationValue", "Trigger", "Identifier", "ClassNameAttribute", "ResourceTypeAttribute", "ResourceReferenceAttribute", "Or", "And", "Not", "LParen", "RParen", "String", "Equals", "Colon", "At", "Dot", "Whitespace", "ClassName", "Annotation", "NamespaceDelimiter", "IDLetter", "IDDigit", "BlockComment", "LineComment", "'consumer-resource'", "'supplier-resource'", "'constraint'", "'mustexist'", "'onfailure'", "'onsuccess'"
    };
    public static final int RParen=16;
    public static final int At=20;
    public static final int LParen=15;
    public static final int Trigger=7;
    public static final int LineComment=29;
    public static final int Newline=4;
    public static final int Colon=19;
    public static final int AnnotationValue=6;
    public static final int IDLetter=26;
    public static final int EOF=-1;
    public static final int AnnotationKey=5;
    public static final int Identifier=8;
    public static final int ClassName=23;
    public static final int BlockComment=28;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int ClassNameAttribute=9;
    public static final int T__35=35;
    public static final int Not=14;
    public static final int Dot=21;
    public static final int String=17;
    public static final int Annotation=24;
    public static final int Or=12;
    public static final int IDDigit=27;
    public static final int Whitespace=22;
    public static final int Equals=18;
    public static final int ResourceReferenceAttribute=11;
    public static final int NamespaceDelimiter=25;
    public static final int ResourceTypeAttribute=10;
    public static final int And=13;

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

                if ( (LA1_0==AnnotationKey||LA1_0==Trigger||(LA1_0>=30 && LA1_0<=32)||(LA1_0>=34 && LA1_0<=35)) ) {
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
            case 30:
                {
                alt2=3;
                }
                break;
            case 31:
                {
                alt2=4;
                }
                break;
            case 32:
                {
                alt2=5;
                }
                break;
            case 34:
                {
                alt2=6;
                }
                break;
            case 35:
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:114:1: consumerResource returns [Resource value] : 'consumer-resource' Identifier resourceTypeAttribute classNameAttribute ;
    public final Resource consumerResource() throws RecognitionException {
        Resource value = null;

        Token Identifier11=null;
        URI resourceTypeAttribute12 = null;

        TreatyParser.classNameAttribute_return classNameAttribute13 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:115:5: ( 'consumer-resource' Identifier resourceTypeAttribute classNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:115:9: 'consumer-resource' Identifier resourceTypeAttribute classNameAttribute
            {
            match(input,30,FOLLOW_30_in_consumerResource337); if (state.failed) return value;
            Identifier11=(Token)match(input,Identifier,FOLLOW_Identifier_in_consumerResource339); if (state.failed) return value;
            pushFollow(FOLLOW_resourceTypeAttribute_in_consumerResource341);
            resourceTypeAttribute12=resourceTypeAttribute();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_classNameAttribute_in_consumerResource343);
            classNameAttribute13=classNameAttribute();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createConsumerResource((Identifier11!=null?Identifier11.getText():null), resourceTypeAttribute12, (classNameAttribute13!=null?input.toString(classNameAttribute13.start,classNameAttribute13.stop):null));
                      
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

    public static class classNameAttribute_return extends ParserRuleReturnScope {
        public String value;
    };

    // $ANTLR start "classNameAttribute"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:121:1: classNameAttribute returns [String value] : ClassNameAttribute ;
    public final TreatyParser.classNameAttribute_return classNameAttribute() throws RecognitionException {
        TreatyParser.classNameAttribute_return retval = new TreatyParser.classNameAttribute_return();
        retval.start = input.LT(1);

        Token ClassNameAttribute14=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:122:5: ( ClassNameAttribute )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:122:9: ClassNameAttribute
            {
            ClassNameAttribute14=(Token)match(input,ClassNameAttribute,FOLLOW_ClassNameAttribute_in_classNameAttribute376); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.value = (ClassNameAttribute14!=null?ClassNameAttribute14.getText():null); 
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
    // $ANTLR end "classNameAttribute"


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
            match(input,31,FOLLOW_31_in_supplierResource402); if (state.failed) return value;
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
            match(input,32,FOLLOW_32_in_constraint496); if (state.failed) return value;
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:144:1: orConstraint returns [AbstractCondition value, Disjunction disjunction] : ( ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+ | andConstraint );
    public final TreatyParser.orConstraint_return orConstraint() throws RecognitionException {
        TreatyParser.orConstraint_return retval = new TreatyParser.orConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.andConstraint_return firstConstraint = null;

        TreatyParser.andConstraint_return nextConstraint = null;

        TreatyParser.andConstraint_return andConstraint21 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:5: ( ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+ | andConstraint )
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
            case 33:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:9: ( andConstraint ( Or andConstraint )+ )=>firstConstraint= andConstraint ( Or nextConstraint= andConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.disjunction = new Disjunction(); retval.value = retval.disjunction; 
                    }
                    pushFollow(FOLLOW_andConstraint_in_orConstraint541);
                    firstConstraint=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.disjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:194: ( Or nextConstraint= andConstraint )+
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
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:195: Or nextConstraint= andConstraint
                    	    {
                    	    match(input,Or,FOLLOW_Or_in_orConstraint546); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_andConstraint_in_orConstraint550);
                    	    nextConstraint=andConstraint();

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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:146:9: andConstraint
                    {
                    pushFollow(FOLLOW_andConstraint_in_orConstraint564);
                    andConstraint21=andConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (andConstraint21!=null?andConstraint21.value:null); 
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

    public static class andConstraint_return extends ParserRuleReturnScope {
        public AbstractCondition value;
        public Conjunction conjunction;
    };

    // $ANTLR start "andConstraint"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:149:1: andConstraint returns [AbstractCondition value, Conjunction conjunction] : ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint );
    public final TreatyParser.andConstraint_return andConstraint() throws RecognitionException {
        TreatyParser.andConstraint_return retval = new TreatyParser.andConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return firstConstraint = null;

        TreatyParser.notConstraint_return nextConstraint = null;

        TreatyParser.notConstraint_return notConstraint22 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:5: ( ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+ | notConstraint )
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
            case 33:
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:9: ( notConstraint ( And notConstraint )+ )=>firstConstraint= notConstraint ( And nextConstraint= notConstraint )+
                    {
                    if ( state.backtracking==0 ) {
                       retval.conjunction = new Conjunction(); retval.value = retval.conjunction; 
                    }
                    pushFollow(FOLLOW_notConstraint_in_andConstraint631);
                    firstConstraint=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.conjunction.addCondition((firstConstraint!=null?firstConstraint.value:null)); 
                    }
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:195: ( And nextConstraint= notConstraint )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==And) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:196: And nextConstraint= notConstraint
                    	    {
                    	    match(input,And,FOLLOW_And_in_andConstraint636); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_notConstraint_in_andConstraint640);
                    	    nextConstraint=notConstraint();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	       retval.conjunction.addCondition((nextConstraint!=null?nextConstraint.value:null)); 
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
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:151:9: notConstraint
                    {
                    pushFollow(FOLLOW_notConstraint_in_andConstraint654);
                    notConstraint22=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (notConstraint22!=null?notConstraint22.value:null); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:154:1: notConstraint returns [AbstractCondition value, Negation negation] : ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint );
    public final TreatyParser.notConstraint_return notConstraint() throws RecognitionException {
        TreatyParser.notConstraint_return retval = new TreatyParser.notConstraint_return();
        retval.start = input.LT(1);

        TreatyParser.notConstraint_return condition = null;

        TreatyParser.orConstraint_return orConstraint23 = null;

        ExistsCondition existsConstraint24 = null;

        RelationshipCondition relationshipConstraint25 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:5: ( Not condition= notConstraint | LParen orConstraint RParen | existsConstraint | relationshipConstraint )
            int alt7=4;
            switch ( input.LA(1) ) {
            case Not:
                {
                alt7=1;
                }
                break;
            case LParen:
                {
                alt7=2;
                }
                break;
            case 33:
                {
                alt7=3;
                }
                break;
            case Identifier:
                {
                alt7=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:155:9: Not condition= notConstraint
                    {
                    match(input,Not,FOLLOW_Not_in_notConstraint705); if (state.failed) return retval;
                    pushFollow(FOLLOW_notConstraint_in_notConstraint709);
                    condition=notConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.negation = new Negation(); retval.negation.addCondition((condition!=null?condition.value:null)); retval.value = retval.negation; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:156:9: LParen orConstraint RParen
                    {
                    match(input,LParen,FOLLOW_LParen_in_notConstraint722); if (state.failed) return retval;
                    pushFollow(FOLLOW_orConstraint_in_notConstraint724);
                    orConstraint23=orConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RParen,FOLLOW_RParen_in_notConstraint726); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (orConstraint23!=null?orConstraint23.value:null); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:157:9: existsConstraint
                    {
                    pushFollow(FOLLOW_existsConstraint_in_notConstraint740);
                    existsConstraint24=existsConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = existsConstraint24; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:158:9: relationshipConstraint
                    {
                    pushFollow(FOLLOW_relationshipConstraint_in_notConstraint764);
                    relationshipConstraint25=relationshipConstraint();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = relationshipConstraint25; 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:161:1: existsConstraint returns [ExistsCondition value] : 'mustexist' resource ;
    public final ExistsCondition existsConstraint() throws RecognitionException {
        ExistsCondition value = null;

        Resource resource26 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:5: ( 'mustexist' resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:162:9: 'mustexist' resource
            {
            match(input,33,FOLLOW_33_in_existsConstraint795); if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_existsConstraint797);
            resource26=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new ExistsCondition();
                          value.setResource(resource26);
                      
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:169:1: relationshipConstraint returns [RelationshipCondition value] : leftResource= resource relationshipType rightResource= resource ;
    public final RelationshipCondition relationshipConstraint() throws RecognitionException {
        RelationshipCondition value = null;

        Resource leftResource = null;

        Resource rightResource = null;

        TreatyParser.relationshipType_return relationshipType27 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:170:5: (leftResource= resource relationshipType rightResource= resource )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:170:9: leftResource= resource relationshipType rightResource= resource
            {
            pushFollow(FOLLOW_resource_in_relationshipConstraint832);
            leftResource=resource();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_relationshipType_in_relationshipConstraint834);
            relationshipType27=relationshipType();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_resource_in_relationshipConstraint838);
            rightResource=resource();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new RelationshipCondition();
                          value.setResource1(leftResource);
                          value.setResource2(rightResource);
                          value.setRelationship(new URI((relationshipType27!=null?input.toString(relationshipType27.start,relationshipType27.stop):null)));
                      
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:179:1: relationshipType : String ;
    public final TreatyParser.relationshipType_return relationshipType() throws RecognitionException {
        TreatyParser.relationshipType_return retval = new TreatyParser.relationshipType_return();
        retval.start = input.LT(1);

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:180:5: ( String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:180:8: String
            {
            match(input,String,FOLLOW_String_in_relationshipType866); if (state.failed) return retval;

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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:183:1: resource returns [Resource value] : resourceId= Identifier {...}?;
    public final Resource resource() throws RecognitionException {
        Resource value = null;

        Token resourceId=null;

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:184:5: (resourceId= Identifier {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:184:9: resourceId= Identifier {...}?
            {
            resourceId=(Token)match(input,Identifier,FOLLOW_Identifier_in_resource891); if (state.failed) return value;
            if ( !(( contract.getResource((resourceId!=null?resourceId.getText():null)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "resource", " contract.getResource($resourceId.text) != null ");
            }
            if ( state.backtracking==0 ) {

                          value = contract.getResource((resourceId!=null?resourceId.getText():null));
                      
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
    // $ANTLR end "resource"


    // $ANTLR start "onFailure"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:190:1: onFailure returns [URI value] : 'onfailure' action ;
    public final URI onFailure() throws RecognitionException {
        URI value = null;

        TreatyParser.action_return action28 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:191:5: ( 'onfailure' action )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:191:9: 'onfailure' action
            {
            match(input,34,FOLLOW_34_in_onFailure927); if (state.failed) return value;
            pushFollow(FOLLOW_action_in_onFailure929);
            action28=action();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new URI((action28!=null?input.toString(action28.start,action28.stop):null)); 
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
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:194:1: onSuccess returns [URI value] : 'onsuccess' action ;
    public final URI onSuccess() throws RecognitionException {
        URI value = null;

        TreatyParser.action_return action29 = null;


        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:195:5: ( 'onsuccess' action )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:195:9: 'onsuccess' action
            {
            match(input,35,FOLLOW_35_in_onSuccess955); if (state.failed) return value;
            pushFollow(FOLLOW_action_in_onSuccess957);
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
    // $ANTLR end "onSuccess"

    public static class action_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "action"
    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:198:1: action : String ;
    public final TreatyParser.action_return action() throws RecognitionException {
        TreatyParser.action_return retval = new TreatyParser.action_return();
        retval.start = input.LT(1);

        try {
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:199:5: ( String )
            // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:199:9: String
            {
            match(input,String,FOLLOW_String_in_action979); if (state.failed) return retval;

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
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:9: ( andConstraint ( Or andConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:10: andConstraint ( Or andConstraint )+
        {
        pushFollow(FOLLOW_andConstraint_in_synpred1_Treaty525);
        andConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:24: ( Or andConstraint )+
        int cnt8=0;
        loop8:
        do {
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==Or) ) {
                alt8=1;
            }


            switch (alt8) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:145:25: Or andConstraint
        	    {
        	    match(input,Or,FOLLOW_Or_in_synpred1_Treaty528); if (state.failed) return ;
        	    pushFollow(FOLLOW_andConstraint_in_synpred1_Treaty530);
        	    andConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt8 >= 1 ) break loop8;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(8, input);
                    throw eee;
            }
            cnt8++;
        } while (true);


        }
    }
    // $ANTLR end synpred1_Treaty

    // $ANTLR start synpred2_Treaty
    public final void synpred2_Treaty_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:9: ( notConstraint ( And notConstraint )+ )
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:10: notConstraint ( And notConstraint )+
        {
        pushFollow(FOLLOW_notConstraint_in_synpred2_Treaty615);
        notConstraint();

        state._fsp--;
        if (state.failed) return ;
        // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:24: ( And notConstraint )+
        int cnt9=0;
        loop9:
        do {
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==And) ) {
                alt9=1;
            }


            switch (alt9) {
        	case 1 :
        	    // /Users/carlos/Projects/Eclipse Workspace/treaty/grammar/Treaty.g:150:25: And notConstraint
        	    {
        	    match(input,And,FOLLOW_And_in_synpred2_Treaty618); if (state.failed) return ;
        	    pushFollow(FOLLOW_notConstraint_in_synpred2_Treaty620);
        	    notConstraint();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt9 >= 1 ) break loop9;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(9, input);
                    throw eee;
            }
            cnt9++;
        } while (true);


        }
    }
    // $ANTLR end synpred2_Treaty

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


 

    public static final BitSet FOLLOW_statment_in_contract70 = new BitSet(new long[]{0x0000000DC00000B0L});
    public static final BitSet FOLLOW_Newline_in_contract74 = new BitSet(new long[]{0x0000000DC00000B0L});
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
    public static final BitSet FOLLOW_30_in_consumerResource337 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_consumerResource339 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_consumerResource341 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_classNameAttribute_in_consumerResource343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ClassNameAttribute_in_classNameAttribute376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_supplierResource402 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_supplierResource404 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_resourceTypeAttribute_in_supplierResource406 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_resourceReferenceAttribute_in_supplierResource408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceTypeAttribute_in_resourceTypeAttribute441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ResourceReferenceAttribute_in_resourceReferenceAttribute467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_constraint496 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_orConstraint_in_constraint498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint541 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_orConstraint546 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint550 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_andConstraint_in_orConstraint564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint631 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_And_in_andConstraint636 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint640 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_notConstraint_in_andConstraint654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Not_in_notConstraint705 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_notConstraint_in_notConstraint709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LParen_in_notConstraint722 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_orConstraint_in_notConstraint724 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RParen_in_notConstraint726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existsConstraint_in_notConstraint740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationshipConstraint_in_notConstraint764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_existsConstraint795 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_resource_in_existsConstraint797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint832 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_relationshipType_in_relationshipConstraint834 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_resource_in_relationshipConstraint838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_relationshipType866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_resource891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_onFailure927 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_action_in_onFailure929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_onSuccess955 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_action_in_onSuccess957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_String_in_action979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andConstraint_in_synpred1_Treaty525 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Or_in_synpred1_Treaty528 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_andConstraint_in_synpred1_Treaty530 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_notConstraint_in_synpred2_Treaty615 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_And_in_synpred2_Treaty618 = new BitSet(new long[]{0x000000020000C100L});
    public static final BitSet FOLLOW_notConstraint_in_synpred2_Treaty620 = new BitSet(new long[]{0x0000000000002002L});

}