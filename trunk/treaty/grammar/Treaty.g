/** 
 * A Treaty script grammar for ANTLRv3. 
 */
grammar Treaty;

@lexer::header {
package net.java.treaty.script.generated;

import java.util.List;
import java.util.LinkedList;

import net.java.treaty.script.TreatyRecognitionException;
}

@lexer::members {
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
}

@parser::header {
package net.java.treaty.script.generated;

import java.net.URI;

import net.java.treaty.*;
import net.java.treaty.script.TreatyRecognitionException;
}

@parser::members {
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
}

@rulecatch {
    catch (RecognitionException e) {
        reportError(e);
        throw e;
    } catch (Exception e) {
        throw new TreatyRecognitionException(this.input, e);
    }
}

contract returns [Contract value]
@init { contract = new Contract(); $value = contract; }
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
    
constraint returns [AbstractCondition value]
    :   'constraint' orConstraint  { $value = $orConstraint.value; }
    ;

orConstraint returns [AbstractCondition value, Disjunction disjunction]
    :   (xorConstraint (Or xorConstraint)+) => { $disjunction = new Disjunction(); $value = $disjunction; } firstConstraint=xorConstraint { $disjunction.addCondition($firstConstraint.value); } (Or nextConstraint=xorConstraint { $disjunction.addCondition($nextConstraint.value); })+
    |   xorConstraint                          { $value = $xorConstraint.value; }
    ;

xorConstraint returns [AbstractCondition value, XDisjunction xDisjunction]
    :   (andConstraint (XOr andConstraint)+) => { $xDisjunction = new XDisjunction(); $value = $xDisjunction; } firstConstraint=andConstraint { $xDisjunction.addCondition($firstConstraint.value); } (XOr nextConstraint=andConstraint { $xDisjunction.addCondition($nextConstraint.value); })+
    |   andConstraint                           { $value = $andConstraint.value; }
    ;

andConstraint returns [AbstractCondition value, Conjunction conjunction]
    :   (notConstraint (And notConstraint)+) => { $conjunction = new Conjunction(); $value = $conjunction; } firstConstraint=notConstraint { $conjunction.addCondition($firstConstraint.value); } (And nextConstraint=notConstraint { $conjunction.addCondition($nextConstraint.value); })+
    |   notConstraint                           { $value = $notConstraint.value; }
    ;

notConstraint returns [AbstractCondition value, Negation negation]
    :   Not condition=notConstraint  { $negation = new Negation(); $negation.addCondition($condition.value); $value = $negation; }
    |   LParen orConstraint RParen   { $value = $orConstraint.value; }
    |   existsConstraint             { $value = $existsConstraint.value; }
    |   relationshipConstraint       { $value = $relationshipConstraint.value; }
    ;

existsConstraint returns [ExistsCondition value]
    :   'mustexist' resource
        {
            $value = new ExistsCondition();
            $value.setResource($resource.value);
        }
    ;

relationshipConstraint returns [RelationshipCondition value]
    :   leftResource=resource relationshipType=Uri rightResource=resource
        {
            $value = new RelationshipCondition();
            $value.setResource1($leftResource.value);
            $value.setResource2($rightResource.value);
            $value.setRelationship(new URI($relationshipType.text));
        }
    ;

resource returns [Resource value]
    :   resourceId=Identifier  { contract.getResource($resourceId.text) != null }?
        {
            $value = contract.getResource($resourceId.text);
        }
    ;
    catch [FailedPredicateException ex] {
        throw new FailedPredicateException(input, "resource", $resourceId.text + "' has not been declared");
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
Equals      : '='  ;
Exclamation : '!'  ;
Hash        : '#'  ;    
Minus       : '-'  ;
Percent     : '%'  ;
Plus        : '+'  ;
Question    : '?'  ;    
Semi        : ';'  ;
Slash       : '/'  ;
Tilde       : '~'  ;

Trigger
    :   'on' Whitespace String
        {
            emit(new CommonToken(Trigger, $String.text));
        }
    ;

ResourceNameAttribute
    :   'name' Equals String
        {
            emit(new CommonToken(ResourceNameAttribute, $String.text));
        }
    ;

ResourceTypeAttribute
    :   'type' Equals String
        {
            emit(new CommonToken(ResourceTypeAttribute, $String.text));
        }
    ;

ResourceReferenceAttribute
    :   'ref' Equals String
        {
            emit(new CommonToken(ResourceReferenceAttribute, $String.text));
        }
    ;

fragment
String
    :   ~(' '|'\t'|'\f'|'\n'|'\r')*
    ;

Annotation
    :   At key=AnnotationKey Equals value=AnnotationValue
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
 *  between it and previous statement to prevent the lexer confusing urls with
 *  line comments.
 */
LineComment
@init { $channel=HIDDEN; }
    :   { getCharPositionInLine() == 0 }? => Whitespace? '//' ~('\n')*
    |   { getCharPositionInLine() >= 0 }? => Whitespace '//' ~('\n')*
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
    :   Semi | Slash | Question | Colon | At | Amper | Equals | Plus | Dollar | Comma | Hash
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

fragment
HexDigit
    :   ('0'..'9'|'a'..'f'|'A'..'F')
    ;

fragment
DecimalDigit
    :   ('0'..'9')
    ;