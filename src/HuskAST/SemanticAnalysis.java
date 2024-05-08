package HuskAST;

import HuskAST.HuskAST.*;

import java.util.HashMap;
import java.util.List;

public class SemanticAnalysis extends BaseASTVisitor<TypeID> {
    private boolean catastrophic_failure = false;

    private final HashMap<String, List<TypeID>> customTypes = new HashMap<>();
    private final HashMap<String, FunctionDeclarationNode> functions = new HashMap<>();
    private final HashMap<String, String> unassignedIDsWithTypes = new HashMap<>();

    public static boolean analyse(StartNode startNode) {
        TypeID failure = new SemanticAnalysis().visitStart(startNode);

        return failure == null;
    }

    @Override
    public TypeID visitStart(StartNode startNode) {
        for (StatementNode statement : startNode.statements) {
            dynamicVisitStatement(statement);
        }

        /*if (unassignedIDsWithTypes.size() != 0) {
            System.out.println("Warning! Unassigned IDs:\n\t" +
                    String.join(",", unassignedIDsWithTypes.keySet()) + '\n');
        }*/

        return catastrophic_failure ? null : new TypeID("", "");
    }

    @Override
    public TypeID visitInPlaceAssignment(InPlaceAssignmentNode ipAssignNode) {
        TypeID assignTypeID = dynamicVisitExpression(ipAssignNode.expression);
        if (!ipAssignNode.typeID.type.equals(assignTypeID.type)) {
            System.out.println("Cannot assign a value of a specific type to an ID of a differing type:\n\t" +
                    ipAssignNode.typeID.toString() + '=' + assignTypeID.toString());
            catastrophic_failure = true;
        }

        return null;
    }

    @Override
    public TypeID visitInPlaceInitialisation(InPlaceInitialisationNode ipInitNode) {
        List<TypeID> members = customTypes.get(ipInitNode.typeID.type);

        if (ipInitNode.expressions.size() != members.size()) {
            System.out.println("Initialisation list doesn't have the same number of members as declared:\n\t" +
                    ipInitNode.typeID.type + " requires " + members.size() + " values," +
                    " given " + ipInitNode.expressions.size());
            catastrophic_failure = true;
        }
        else {
            for (int i = 0; i < members.size(); i++) {
                TypeID givenMember = dynamicVisitExpression(ipInitNode.expressions.get(i));
                TypeID actualMember = members.get(i);
                if (!givenMember.type.equals(actualMember.type)) {
                    System.out.println("Initialisation value type doesn't match declaration member type:\n\t" +
                            ipInitNode.typeID.id + ":type-member[" + (i + 1) + "]:" + givenMember.type +
                            " should be " + actualMember.type);
                    catastrophic_failure = true;
                }
            }
        }

        return null;
    }

    @Override
    public TypeID visitDelayedAssignment(DelayedAssignmentNode delayAssignNode) {
        /*if (!unassignedIDsWithTypes.containsKey(delayAssignNode.typeID.id)) {
            System.out.println("Cannot assign to an ID that has already been assigned to:\n\t" +
                    delayAssignNode.typeID);
            catastrophic_failure = true;
        }
        else {
            String unassignedType = unassignedIDsWithTypes.get(delayAssignNode.typeID.id);
            TypeID assignTypeID = dynamicVisitExpression(delayAssignNode.expression);
            if (!unassignedType.equals(delayAssignNode.typeID.type)) {
                System.out.println("Cannot assign a value of a specific type to an ID of a differing type:\n\t" +
                        delayAssignNode.typeID.toString() + '=' + assignTypeID.toString());
                catastrophic_failure = true;
            }

            unassignedIDsWithTypes.remove(delayAssignNode.typeID.id);
        }*/

        TypeID assignTypeID = dynamicVisitExpression(delayAssignNode.expression);
        if (!delayAssignNode.typeID.type.equals(assignTypeID.type)) {
            System.out.println("Cannot assign a value of a specific type to an ID of a differing type:\n\t" +
                    delayAssignNode.typeID.toString() + '=' + assignTypeID.toString());
            catastrophic_failure = true;
        }

        return null;
    }

    @Override
    public TypeID visitDelayedInitialisation(DelayedInitialisationNode delayInitNode) {
        /*if (!unassignedIDsWithTypes.containsKey(delayInitNode.typeID.id)) {
            System.out.println("Cannot assign to an ID that has already been assigned to:\n\t" +
                    delayInitNode.typeID);
            catastrophic_failure = true;
        }
        else {
            List<TypeID> members = customTypes.get(delayInitNode.typeID.type);

            if (delayInitNode.expressions.size() != members.size()) {
                System.out.println("Initialisation list doesn't have the same number of members as declared:\n\t" +
                        delayInitNode.typeID.type + " requires " + members.size() + " values," +
                        " given " + delayInitNode.expressions.size());
                catastrophic_failure = true;
            }
            else {
                for (int i = 0; i < members.size(); i++) {
                    TypeID givenMember = dynamicVisitExpression(delayInitNode.expressions.get(i));
                    TypeID actualMember = members.get(i);
                    if (!givenMember.type.equals(actualMember.type)) {
                        System.out.println("Initialisation value type doesn't match declaration member type:\n\t" +
                                delayInitNode.typeID.id + ":type-member[" + (i + 1) + "]:" + givenMember.type +
                                " should be " + actualMember.type);
                        catastrophic_failure = true;
                    }
                }
            }

            unassignedIDsWithTypes.remove(delayInitNode.typeID.id);
        }*/

        List<TypeID> members = customTypes.get(delayInitNode.typeID.type);
        if (delayInitNode.expressions.size() != members.size()) {
            System.out.println("Initialisation list doesn't have the same number of members as declared:\n\t" +
                    delayInitNode.typeID.type + " requires " + members.size() + " values," +
                    " given " + delayInitNode.expressions.size());
            catastrophic_failure = true;
        }
        else {
            for (int i = 0; i < members.size(); i++) {
                TypeID givenMember = dynamicVisitExpression(delayInitNode.expressions.get(i));
                TypeID actualMember = members.get(i);
                if (!givenMember.type.equals(actualMember.type)) {
                    System.out.println("Initialisation value type doesn't match declaration member type:\n\t" +
                            delayInitNode.typeID.id + ":type-member[" + (i + 1) + "]:" + givenMember.type +
                            " should be " + actualMember.type);
                    catastrophic_failure = true;
                }
            }
        }

        return null;
    }

    @Override
    public TypeID visitTypeDeclaration(TypeDeclarationNode typeDeclareNode) {
        customTypes.put(typeDeclareNode.type, typeDeclareNode.members);

        return null;
    }

    @Override
    public TypeID visitFunctionDeclaration(FunctionDeclarationNode funcDeclareNode) {
        functions.put(funcDeclareNode.id, funcDeclareNode);

        String blockReturnType = visitFunctionBlock(funcDeclareNode.functionBlock).type;
        if (!funcDeclareNode.returnType.equals(blockReturnType)) {
            System.out.println("Function return type must be the same as the block return value type:\n\t" +
                    funcDeclareNode.id + ":func->returnType:" + funcDeclareNode.returnType +
                    " while block returns " + blockReturnType);
            catastrophic_failure = true;
        }

        return null;
    }

    @Override
    public TypeID visitUnassignedDeclaration(UnassignedDeclarationNode unassignedDeclareNode) {
        unassignedIDsWithTypes.put(unassignedDeclareNode.typeID.id, unassignedDeclareNode.typeID.type);

        return null;
    }

    @Override
    public TypeID visitConditional(ConditionalNode conditionalNode) {
        if (!"Num".equals(dynamicVisitExpression(conditionalNode.ifExpression).type)) {
            catastrophic_failure = true;
        }
        visitIfBlock(conditionalNode.ifBlock);

        for (int i = 0; i < conditionalNode.elseifExpressions.size(); i++) {
            if (dynamicVisitExpression(conditionalNode.elseifExpressions.get(i)) == null) {
                catastrophic_failure = true;
            }
            visitIfBlock(conditionalNode.elseifBlocks.get(i));
        }

        if (conditionalNode.elseBlock != null) {
            visitIfBlock(conditionalNode.elseBlock);
        }

        return null;
    }

    @Override
    public TypeID visitPrint(PrintNode printNode) {
        if (dynamicVisitExpression(printNode.expression) == null) {
            catastrophic_failure = true;
        }

        return null;
    }

    @Override
    public TypeID visitFunctionBlock(FunctionBlockNode funcBlockNode) {
        for (StatementNode statement : funcBlockNode.statements) {
            dynamicVisitStatement(statement);
        }

        return dynamicVisitExpression(funcBlockNode.returnExpression);
    }

    @Override
    public TypeID visitIfBlock(IfBlockNode ifBlockNode) {
        for (StatementNode statement : ifBlockNode.statements) {
            dynamicVisitStatement(statement);
        }

        return null;
    }

    @Override
    public TypeID visitParentheses(ParenthesesNode parenthesesNode) {
        return dynamicVisitExpression(parenthesesNode.internalExpression);
    }

    @Override
    public TypeID visitAccessMember(AccessMemberNode accessMemberNode) {
        StringBuilder id = new StringBuilder();

        id.append(accessMemberNode.typeIDs.get(0).id);
        for (int i = 1; i < accessMemberNode.typeIDs.size(); i++) {
            id.append('.').append(accessMemberNode.typeIDs.get(i).id);
        }

        return new TypeID(accessMemberNode.typeIDs.get(accessMemberNode.typeIDs.size() - 1).type, id.toString());
    }

    @Override
    public TypeID visitUnaryOperation(UnaryOperationNode unaryOpNode) {
        TypeID operandType = dynamicVisitExpression(unaryOpNode.operand);

        if (!operandType.type.equals("Num")) {
            System.out.println("Cannot do unary negation on a type that isn't a Num:" +
                    "attempted to negate " + operandType.toString());
            catastrophic_failure = true;
        }

        return new TypeID("Num", "UnaryOperation");
    }

    @Override
    public TypeID visitBinaryOperation(BinaryOperationNode binaryOpNode) {
        TypeID left = dynamicVisitExpression(binaryOpNode.left);
        String operation = binaryOpNode.operation;
        TypeID right = dynamicVisitExpression(binaryOpNode.right);

        if (!(left.type.equals("Num") || left.type.equals("Str"))) {
            System.out.println("Cannot operate on custom types:\n\t" +
                    left.toString());
            catastrophic_failure = true;
        }
        if (!(right.type.equals("Num") || right.type.equals("Str"))) {
            System.out.println("Cannot operate on custom types:\n\t" +
                    right.toString());
            catastrophic_failure = true;
        }

        String returnType = switch (operation) {
            case "%", "/", "*", "-" -> left.type.equals("Num") && left.type.equals(right.type) ? "Num" : null;
            case "+", ">=", "<=", ">", "<" -> left.type.equals(right.type) ? left.type : null;
            case "==" -> "Num";
            default -> null;
        };

        if (returnType == null) {
            System.out.println("Incompatible types for binary operation (" + operation + "):\n\t" +
                    left.toString() + ' ' + operation + ' ' + right.toString());
            catastrophic_failure = true;
        }

        return new TypeID(returnType, "BinaryOperation");
    }

    @Override
    public TypeID visitLiteral(LiteralNode literalNode) {
        return new TypeID(literalNode.type, literalNode.value.toString());
    }

    @Override
    public TypeID visitIdentifier(IdentifierNode idNode) {
        return new TypeID(idNode.type, idNode.id);
    }

    @Override
    public TypeID visitFunction(FunctionNode funcNode) {
        FunctionDeclarationNode funcDec = functions.get(funcNode.id);

        if (funcNode.args.size() != funcDec.parameters.size()) {
            System.out.println("Function call doesn't have the same number of arguments as declared:\n\t" +
                    funcNode.id + " requires " + funcDec.parameters.size() + " args, given " + funcNode.args.size());
            catastrophic_failure = true;
        }
        else {
            for (int i = 0; i < funcNode.args.size(); i++) {
                TypeID callType = dynamicVisitExpression(funcNode.args.get(i));
                TypeID decType = funcDec.parameters.get(i);
                if (!decType.type.equals(callType.type)) {
                    System.out.println("Function call argument type doesn't match declaration parameter type:\n\t" +
                            funcNode.id + ":func-param[" + (i + 1) + "]:" + callType.type + " should be " + decType.type);
                    catastrophic_failure = true;
                }
            }
        }

        return new TypeID(funcDec.returnType, funcDec.id);
    }
}
