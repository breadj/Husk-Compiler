package HuskAST;

import HuskAST.HuskAST.*;

public abstract class BaseASTVisitor<T> {

    public abstract T visitStart(StartNode startNode);

    public abstract T visitInPlaceAssignment(InPlaceAssignmentNode ipAssignNode);

    public abstract T visitInPlaceInitialisation(InPlaceInitialisationNode ipInitNode);

    public abstract T visitDelayedAssignment(DelayedAssignmentNode delayAssignNode);

    public abstract T visitDelayedInitialisation(DelayedInitialisationNode delayInitNode);

    public abstract T visitTypeDeclaration(TypeDeclarationNode typeDeclareNode);

    public abstract T visitFunctionDeclaration(FunctionDeclarationNode funcDeclareNode);

    public abstract T visitUnassignedDeclaration(UnassignedDeclarationNode unassignedDeclareNode);

    public abstract T visitConditional(ConditionalNode conditionalNode);

    public abstract T visitPrint(PrintNode printNode);

    public abstract T visitFunctionBlock(FunctionBlockNode funcBlockNode);

    public abstract T visitIfBlock(IfBlockNode ifBlockNode);

    public abstract T visitParentheses(ParenthesesNode parenthesesNode);

    public abstract T visitAccessMember(AccessMemberNode accessMemberNode);

    public abstract T visitUnaryOperation(UnaryOperationNode unaryOpNode);

    public abstract T visitBinaryOperation(BinaryOperationNode binaryOpNode);

    public abstract T visitLiteral(LiteralNode literalNode);

    public abstract T visitIdentifier(IdentifierNode idNode);

    public abstract T visitFunction(FunctionNode funcNode);


    public T dynamicVisitStatement(StatementNode statementNode) {
        return switch (statementNode) {
            case AssignmentNode an -> dynamicVisitAssignment(an);
            case TypeDeclarationNode tdc -> visitTypeDeclaration(tdc);
            case FunctionDeclarationNode fdn -> visitFunctionDeclaration(fdn);
            case UnassignedDeclarationNode udn -> visitUnassignedDeclaration(udn);
            case ConditionalNode cn -> visitConditional(cn);
            case PrintNode pn -> visitPrint(pn);
            default -> null;
        };
    }

    public T dynamicVisitAssignment(AssignmentNode assignmentNode) {
        return switch (assignmentNode) {
            case InPlaceAssignmentNode ipan -> visitInPlaceAssignment(ipan);
            case InPlaceInitialisationNode ipin -> visitInPlaceInitialisation(ipin);
            case DelayedAssignmentNode dan -> visitDelayedAssignment(dan);
            case DelayedInitialisationNode din -> visitDelayedInitialisation(din);
            default -> null;
        };
    }

    public T dynamicVisitExpression(ExpressionNode expressionNode) {
        return switch (expressionNode) {
            case ParenthesesNode pn -> visitParentheses(pn);
            case AccessMemberNode amn -> visitAccessMember(amn);
            case UnaryOperationNode uon -> visitUnaryOperation(uon);
            case BinaryOperationNode bon -> visitBinaryOperation(bon);
            case LiteralNode ln -> visitLiteral(ln);
            case IdentifierNode in -> visitIdentifier(in);
            case FunctionNode fn -> visitFunction(fn);
            default -> null;
        };
    }
}