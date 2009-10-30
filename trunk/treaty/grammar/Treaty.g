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
}

@parser::header {
package net.java.treaty.script.generated;

import java.net.URI;

import net.java.treaty.*;
import net.java.treaty.script.TreatyRecognitionException;
}

@parser::members {
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
    :   'consumer-resource' Identifier resourceTypeAttribute nameAttribute
        {
            $value = createConsumerResource($Identifier.text, $resourceTypeAttribute.value, $nameAttribute.text);
        }
    ;

nameAttribute returns [String value]
    :   NameAttribute  { $value = $NameAttribute.text; }
    ;

supplierResource returns [Resource value]
    :   'supplier-resource' Identifier resourceTypeAttribute resourceReferenceAttribute
        {
            $value = createSupplierResource($Identifier.text, $resourceTypeAttribute.value, $resourceReferenceAttribute.value);
        }
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
    :   (andConstraint (Or andConstraint)+) => { $disjunction = new Disjunction(); $value = $disjunction; } firstConstraint=andConstraint { $disjunction.addCondition($firstConstraint.value); } (Or nextConstraint=andConstraint { $disjunction.addCondition($nextConstraint.value); })+
    |   andConstraint                          { $value = $andConstraint.value; }
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
    :   leftResource=resource relationshipType rightResource=resource
        {
            $value = new RelationshipCondition();
            $value.setResource1($leftResource.value);
            $value.setResource2($rightResource.value);
            $value.setRelationship(new URI($relationshipType.text));
        }
    ;

relationshipType
    :  String
    ;

resource returns [Resource value]
    :   resourceId=Identifier  { contract.getResource($resourceId.text) != null }?
        {
            $value = contract.getResource($resourceId.text);
        }
    ;

onFailure returns [URI value]
    :   'onfailure' action  { $value = new URI($action.text); }
    ;

onSuccess returns [URI value]
    :   'onsuccess' action  { $value = new URI($action.text); }
    ;

action
    :   String
    ;

And         : 'and' ;
Or          : 'or'  ;
Not         : 'not' ;

LParen      : '(' { implicitLineJoiningLevel += 1; } ;
RParen      : ')' { implicitLineJoiningLevel -= 1; } ;

Equals      : '=' ;
Colon       : ':' ;
At          : '@' ;
Dot         : '.' ; 

Trigger
    :   'on' Whitespace String
        {
            emit(new CommonToken(Trigger, $String.text));
        }
    ;

NameAttribute
    :   'name' Equals String
        {
            emit(new CommonToken(NameAttribute, $String.text));
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

Annotation
    :   At key=AnnotationKey Equals value=AnnotationValue
        {
            emit(new CommonToken(AnnotationKey, $key.text));
            emit(new CommonToken(AnnotationValue, lastAnnotationValue));
            emit(new CommonToken(Newline, "\n"));
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
    
LineComment
@init { $channel=HIDDEN; }
    :   '//' ~('\n')*
    ;

String
    :   ~(' '|'\t'|'\f'|'\n'|'\r')*
    ;