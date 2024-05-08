grammar Husk;

start
    :   statement* EOF;

statement
    :   assignment
    |   declaration
    |   conditional
    |   print
    ;


assignment
    :   PRIMITIVE ID '=' expression ';'         #InPlacePrimitiveAssignment
    |   TYPE ID '=' expression ';'              #InPlaceTypeAssignment
    |   TYPE ID '=>' args=expression+ ';'       #InPlaceTypeInitialisation
    |   ID '=' expression ';'                   #DelayedAssignment
    |   ID '=>' args=expression+ ';'            #DelayedInitialisation
    ;

declaration
    :   'type' TYPE '=' members                 #TypeDeclaration
    |   'func' ID '=' parameters funcBlock      #FuncDeclaration
    |   type=( PRIMITIVE | TYPE ) ID ';'        #UnassignedDeclaration
    ;

conditional
    :   if elseif* else?
    ;

elseif
    :   'else if' expression ifBlock
    ;

else
    :   'else' ifBlock
    ;

if
    :   'if' expression ifBlock
    ;

print
    :   'print' expression ';'
    ;


members
    :   '{' ( parameter ',' )* parameter '}'
    ;

parameters
    :   ( parameter '->' )* returnType=( PRIMITIVE | TYPE )
    ;

parameter
    : type=( PRIMITIVE | TYPE ) ID
    ;

funcBlock
    :   '{' statement* 'return' expression ';' '}'
    ;

ifBlock
    :   '{' statement* '}'
    ;


expression
    :   '(' expression ')'                          #Parentheses
    |   ID ( '.' ID )+                              #AccessMember
    |   '-' expression                              #UnaryOperation
    |   lhs=expression op='%' rhs=expression        #BinaryOperation
    |   lhs=expression op='/' rhs=expression        #BinaryOperation
    |   lhs=expression op='*' rhs=expression        #BinaryOperation
    |   lhs=expression op='-' rhs=expression        #BinaryOperation
    |   lhs=expression op='+' rhs=expression        #BinaryOperation
    |   lhs=expression op='==' rhs=expression       #BinaryOperation
    |   lhs=expression op='>=' rhs=expression       #BinaryOperation
    |   lhs=expression op='<=' rhs=expression       #BinaryOperation
    |   lhs=expression op='>' rhs=expression        #BinaryOperation
    |   lhs=expression op='<' rhs=expression        #BinaryOperation
    |   LITERAL                                     #LiteralValue
    |   ID                                          #Identifier
    |   func=ID args=expression+                    #FunctionCall
    |   expression '`' func=ID '`' expression+      #FunctionInfixCall
    ;


// typing
PRIMITIVE   :   'Num' | 'Str';
TYPE    :   [A-Z] [_a-zA-Z0-9]*;
ID      :   [a-z] [_a-zA-Z0-9]*;
LITERAL :   NUM | STRING;
NUM     :   FLOAT | INT;
STRING  :   '"' ~["]* '"';
fragment FLOAT  :   INT? '.' INT;
fragment INT    :   [0-9]+;

COMMENT :   '//' ~[\r\n]* -> skip;
WS      :   [ \t\r\n]+ -> skip;