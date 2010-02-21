/** 
 * The TreatyScript grammar for ANTLRv3. 
 */
grammar TreatyScript;

options {
	backtrack=true;
	memoize=true;
}

@lexer::header {
package net.java.treaty.script.generated;

import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;
}

@lexer::members {
static private Token NEWLINE_TOKEN = new CommonToken(Newline, "\n");

// Workaround until getText(), and setText() work as expected within lexer fragments.
private String lastAnnotationValue = null;

// Keeps track of the implicit line joining level, so statments between parentheses can can span multiple lines.
private int implicitLineJoiningLevel = 0;

private LinkedList<Token> tokens = new LinkedList<Token>();

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
}

@parser::header {
package net.java.treaty.script.generated;

import java.net.URI;

import net.java.treaty.*;
import net.java.treaty.script.TreatyRecognitionException;
}

@parser::members {
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
}

@rulecatch {
    catch (RecognitionException e) {
        reportError(e);
        throw e;
    } catch (Exception e) {
        throw new TreatyRecognitionException(input, e);
    }
}

contract[ContractVocabulary vocabulary] returns [Contract value]
@init  {
    contract = new Contract(); this.vocabulary = vocabulary;
    if (vocabulary == null)
        throw new IllegalArgumentException("'vocabulary' cannot be null");
}
@after { $value = contract; }
    :   (statment | Newline)* EOF
    ;

statment
    :   (   annotation        { contract.setProperty($annotation.key, $annotation.value); }
        |   trigger           { contract.addTrigger($trigger.value); }
        |   consumerResource  { contract.addConsumerResource($consumerResource.value); }
        |   supplierResource  { contract.addSupplierResource($supplierResource.value); }
        |   externalResource  { contract.addExternalResource($externalResource.value); }
        |   constraint        { contract.addCondition($constraint.value); }
        |   onFailure         { contract.addOnVerificationFailsAction($onFailure.value); }
        |   onSuccess         { contract.addOnVerificationSucceedsAction($onSuccess.value); }
        ) Newline
    ;

annotation returns [String key, String value]
    :   AnnotationKey AnnotationValue
        {
            $key = $AnnotationKey.text;
            $value = $AnnotationValue.text;
        }
    ;

trigger returns [URI value]
    :   Trigger  { $value = new URI($Trigger.text); }
    ;

consumerResource returns [Resource value]
    :   'consumer-resource' Identifier resourceTypeAttribute resourceNameAttribute
        {
            $value = createNamedResource($Identifier.text, $resourceTypeAttribute.value, $resourceNameAttribute.text);
        }
    ;

supplierResource returns [Resource value]
    :   'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute
        {
            $value = createReferenceResource($Identifier.text, $resourceTypeAttribute.value, $resourceReferenceAttribute.value);
        }
    ;
    
externalResource returns [Resource value]
    :   'external-resource' Identifier resourceTypeAttribute resourceNameAttribute
        {
            $value = createNamedResource($Identifier.text, $resourceTypeAttribute.value, $resourceNameAttribute.text);
        }
    ;

resourceNameAttribute returns [String value]
    :   ResourceNameAttribute  { $value = $ResourceNameAttribute.text; }
    ;

resourceTypeAttribute returns [URI value]
    :   ResourceTypeAttribute  { $value = new URI($ResourceTypeAttribute.text); }
    ;

resourceReferenceAttribute returns [String value]
    :   ResourceReferenceAttribute { $value = $ResourceReferenceAttribute.text; }
    ;
    
constraint returns [Condition value]
    :   'constraint' xorConstraint  { $value = $xorConstraint.value; }
    ;

xorConstraint returns [Condition value, ExclusiveDisjunctiveCondition xDisjunction]
    :   (orConstraint (XOr orConstraint)+) => { $xDisjunction = new ExclusiveDisjunctiveCondition(); $value = $xDisjunction; } firstConstraint=orConstraint { $xDisjunction.addCondition($firstConstraint.value); } (XOr nextConstraint=orConstraint { $xDisjunction.addCondition($nextConstraint.value); })+
    |   orConstraint                          { $value = $orConstraint.value; }
    ;
    
orConstraint returns [Condition value, DisjunctiveCondition disjunction]
    :   (andConstraint (Or andConstraint)+) => { $disjunction = new DisjunctiveCondition(); $value = $disjunction; } firstConstraint=andConstraint { $disjunction.addCondition($firstConstraint.value); } (Or nextConstraint=andConstraint { $disjunction.addCondition($nextConstraint.value); })+
    |   andConstraint                          { $value = $andConstraint.value; }
    ;

andConstraint returns [Condition value, ConjunctiveCondition conjunction]
    :   (notConstraint (And notConstraint)+) => { $conjunction = new ConjunctiveCondition(); $value = $conjunction; } firstConstraint=notConstraint { $conjunction.addCondition($firstConstraint.value); } (And nextConstraint=notConstraint { $conjunction.addCondition($nextConstraint.value); })+
    |   notConstraint                           { $value = $notConstraint.value; }
    ;

notConstraint returns [Condition value, NegatedCondition negation]
    :   Not condition=notConstraint  { $negation = new NegatedCondition(); $negation.addCondition($condition.value); $value = $negation; }
    |   LParen xorConstraint RParen  { $value = $xorConstraint.value; }
    |   existsConstraint             { $value = $existsConstraint.value; }
    |   relationshipConstraint       { $value = $relationshipConstraint.value; }
    |   propertyConstraint           { $value = $propertyConstraint.value; }
    ;

existsConstraint returns [ExistsCondition value]
    :   'mustexist' resource
        {
            $value = new ExistsCondition();
            $value.setResource($resource.value);
        }
    ;

relationshipConstraint returns [RelationshipCondition value]
    :   leftResource=resource relationshipType rightResource=resource
        { isValidRelationship($leftResource.value, $relationshipType.value, $rightResource.value) }?
        {
            $value = createRelationshipCondition($leftResource.value, $relationshipType.value, $rightResource.value);
        }
    ;
    catch [FailedPredicateException ex] {
        throw new TreatyRecognitionException(input,
            "'" + $relationshipConstraint.text + "' is not a valid relationship constraint.");
    }
    catch [TreatyException ex] {
        throw new TreatyRecognitionException(input, ex);
    }

relationshipType returns [URI value]
    :   Uri  { $value = new URI($Uri.text); }
    ;

propertyConstraint returns [PropertyCondition value]
    :   builtinProperyConstraint
        {
            $value = $builtinProperyConstraint.value;
        }
    |   resource propertyOperator propertyValue
        { isValidProperty($resource.value, $propertyOperator.value, $propertyValue.value) }?
        {
            $value = createPropertyCondition($resource.value, $propertyOperator.value, $propertyValue.value);
        }
    ;
    catch [FailedPredicateException ex] {
        throw new TreatyRecognitionException(input, 
            "'" + $propertyConstraint.text + "' is not a valid property constraint.");
    }
    catch [TreatyException ex] {
        throw new TreatyRecognitionException(input, ex);
    }
    
propertyOperator returns [URI value]
    :   Uri  { $value = new URI($Uri.text); }
    ;
    
builtinProperyConstraint returns [PropertyCondition value]
    :   decimalPropertyConstraint  { $value = $decimalPropertyConstraint.value; }
    |   stringPropertyConstraint   { $value = $stringPropertyConstraint.value; }
    ;

decimalPropertyConstraint returns [PropertyCondition value]
    :   decimalResource decimalPropertyOperator decimalLiteral
        {
            $value = createPropertyCondition($decimalResource.value, $decimalPropertyOperator.value, $decimalLiteral.value);
        }
    ;

decimalResource returns [Resource value]
    :   resource  { $value = $resource.value; }
    ;

decimalPropertyOperator returns [URI value]
    :   Equal     { $value = new URI("http://www.treaty.org/builtin/#eq"); }
    |   NotEqual  { $value = new URI("http://www.treaty.org/builtin/#neq"); }
    |   Gt        { $value = new URI("http://www.treaty.org/builtin/#gt"); }
    |   Gte       { $value = new URI("http://www.treaty.org/builtin/#gte"); }
    |   Lt        { $value = new URI("http://www.treaty.org/builtin/#lt"); }
    |   Lte       { $value = new URI("http://www.treaty.org/builtin/#lte"); }
    ;

decimalLiteral returns [Object value]
    :   integerLiteral        { $value = $integerLiteral.value; }
    |   floatingPointLiteral  { $value = $floatingPointLiteral.value; }
    ;

stringPropertyConstraint returns [PropertyCondition value]
    :   stringResource stringPropertyOperator stringLiteral
        {
            $value = createPropertyCondition($stringResource.value, $stringPropertyOperator.value, $stringLiteral.value);
        }
    ;

stringResource returns [Resource value]
    :   resource  { $value = $resource.value; }
    ;

stringPropertyOperator returns [URI value]
    :   Equal      { $value = new URI("http://www.treaty.org/builtin/#eq"); }
    |   NotEqual   { $value = new URI("http://www.treaty.org/builtin/#neq"); }
    |   'matches'  { $value = new URI("http://www.treaty.org/builtin/#matches"); }
    |   'in'       { $value = new URI("http://www.treaty.org/builtin/#in"); }
    ;

propertyValue returns [Object value]
    :   integerLiteral        { $value = $integerLiteral.value; }
    |   floatingPointLiteral  { $value = $floatingPointLiteral.value; }
    |   booleanLiteral        { $value = $booleanLiteral.value; }
    |   stringLiteral         { $value = $stringLiteral.value; }
    ;

integerLiteral returns [Integer value]
    :   IntegerLiteral  { $value = Integer.parseInt($IntegerLiteral.text); }
    ;

floatingPointLiteral returns [Double value]
    :   FloatingPointLiteral  { $value = Double.parseDouble($FloatingPointLiteral.text); }
    ;

booleanLiteral returns [Object value]
    :   'true'   { $value = true; }
    |   'false'  { $value = false; }
    ;

stringLiteral returns [Object value]
    :   StringLiteral  { $value = $StringLiteral.text; }
    ;

resource returns [Resource value]
    :   resourceId=Identifier  { contract.getResource($resourceId.text) != null }?
        {
            $value = contract.getResource($resourceId.text);
        }
    ;
    catch [FailedPredicateException ex] {
        throw new TreatyRecognitionException(input, "The resource '" + $resourceId.text + "' has not been declared.");
    }

onFailure returns [URI value]
    :   'onfailure' action=Uri  { $value = new URI($action.text); }
    ;

onSuccess returns [URI value]
    :   'onsuccess' action=Uri  { $value = new URI($action.text); }
    ;

And         : 'and' ;
Not         : 'not' ;
Or          : 'or'  ;
XOr         : 'xor' ;

LParen      : '(' { implicitLineJoiningLevel += 1; } ;
RParen      : ')' { implicitLineJoiningLevel -= 1; } ;

Amper       : '&'  ;
Apostrophe  : '\'' ;
Asterisk    : '*'  ;    
At          : '@'  ;
Colon       : ':'  ;
Comma       : ','  ;
Dollar      : '$'  ;
Dot         : '.'  ;
Equal       : '='  ;
Exclamation : '!'  ;
Hash        : '#'  ;    
Minus       : '-'  ;
NotEqual    : '!=' ;    
Percent     : '%'  ;
Plus        : '+'  ;
Question    : '?'  ;    
Semi        : ';'  ;
Slash       : '/'  ;
Tilde       : '~'  ;

Gt          : '>'  ;
Lt          : '<'  ;
Gte         : '>=' ;
Lte         : '<=' ;

IntegerLiteral
    :   ('0' | '1'..'9' '0'..'9'*)
    ;

FloatingPointLiteral
    :   
    |   ('0' | '1'..'9' '0'..'9'*) '.' ('0'..'9')* Exponent?
    |   '.' ('0'..'9')+ Exponent?
    |   ('0'..'9')+ Exponent
    ;

fragment
Exponent 
    :   ('e'|'E') ('+'|'-')? ('0'..'9')+
    ;

StringLiteral
    :   '"' ( EscapeSequence | ~('\\'|'"') )* '"'
        {
            String value = getText().substring(1, getText().length() - 1); // strip quotes
        }
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
DecimalDigit
    :   ('0'..'9')
    ;

fragment
HexDigit 
   :   ('0'..'9'|'a'..'f'|'A'..'F')
   ;

Trigger
    :   'on' Whitespace String
        {
            emit(new CommonToken(Trigger, $String.text));
        }
    ;

ResourceNameAttribute
    :   'name' Equal String
        {
            emit(new CommonToken(ResourceNameAttribute, $String.text));
        }
    ;

ResourceTypeAttribute
    :   'type' Equal String
        {
            emit(new CommonToken(ResourceTypeAttribute, $String.text));
        }
    ;

ResourceReferenceAttribute
    :   'ref' Equal String
        {
            emit(new CommonToken(ResourceReferenceAttribute, $String.text));
        }
    ;

fragment
String
    :   ~(' '|'\t'|'\f'|'\n'|'\r')*
    ;

Annotation
    :   At key=AnnotationKey Equal value=AnnotationValue
        {
            emit(new CommonToken(AnnotationKey, $key.text));
            emit(new CommonToken(AnnotationValue, lastAnnotationValue));
            emit(NEWLINE_TOKEN);
        }
    ;

fragment
AnnotationKey
    :   Identifier (NamespaceDelimiter Identifier)*
    ;
    
fragment
NamespaceDelimiter
    : Colon | Dot
    ;

fragment
AnnotationValue
@init { int startIndex = getCharIndex(); }
    :   ( options { greedy=false; } : . )* Newline
        {
            int endIndex = getCharIndex() - 1;
            int whitespace = $Newline.text.length();
            
            // Workaround until getText(), and setText() work as expected within lexer fragments
            lastAnnotationValue = input.substring(startIndex, endIndex - whitespace).trim();
        }
    ;

Identifier 
    :   IDLetter (IDLetter|IDDigit)*
    ;

fragment
IDLetter
    :   'a'..'z'
    |   'A'..'Z'
    |   '_'
    ;

fragment
IDDigit
    :   '0'..'9'
    ;

Newline
    :   '\r'? '\n'
        {
            if (implicitLineJoiningLevel > 0) {
                $channel=HIDDEN;
            }
        }
    ;

Whitespace
@init { $channel=HIDDEN; }
    :   (' '|'\t')+
    ;

BlockComment
@init { $channel=HIDDEN; }
    :   '/*' ( options { greedy=false; } : . )* '*/'
    ;

/** If a line comment is not on its own line, then we require some whitespace
 *  between it and the previous statement to prevent the lexer confusing urls with
 *  line comments.
 */
LineComment
@init { $channel=HIDDEN; }
    :   { getCharPositionInLine() == 0 }? => Whitespace? '//' ~('\n')*
    |   { getCharPositionInLine() >= 0 }? => Whitespace '//' ~('\n')*
    ;

/** Lines that are indented (have leading whitespace) are treated as though
 *  they are part of the preceeding statement.
 */
LeadingWhitespace
@init { $channel=HIDDEN; }
	:	{ getCharPositionInLine() == 0 && implicitLineJoiningLevel == 0 }? => Whitespace
		{
			// hide previous newline token
			Token lastToken = tokens.getLast();
			if (lastToken != null && lastToken.getType() == Newline) {
				lastToken.setChannel(HIDDEN);
			}
		}
	;

/* URI character classes 
 * Adapted from: http://www.antlr.org/grammar/1153976512034/ecmascriptA3.g
 */
Uri
    :   UriCharacter+
    ;

fragment
UriCharacter
    :   UriReserved
    |   UriUnescaped
    |   UriEscaped
    ;

fragment
UriReserved
    :   Semi | Slash | Question | Colon | At | Amper | Equal | Plus | Dollar | Comma | Hash
    ;

fragment
UriUnescaped
    :   UriAlpha
    |   DecimalDigit
    |   UriMark
    ;

fragment
UriAlpha
    :   Identifier
    ;

fragment
UriMark
    :   Minus | Dot | Exclamation | Tilde | Asterisk | Apostrophe
    ;

fragment
UriEscaped
    :  Percent HexDigit HexDigit
    ;