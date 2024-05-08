package HuskAST;

import HuskAST.HuskAST.*;

public class HuskToPython extends BaseASTVisitor<StringBuilder> {

    private int indents = 0;



    public static String generate(StartNode startNode) {
        return new HuskToPython().visitStart(startNode).toString();
    }

    @Override
    public StringBuilder visitStart(StartNode startNode) {
        StringBuilder sb = new StringBuilder();

        for (StatementNode statement : startNode.statements) {
            sb.append(dynamicVisitStatement(statement));
        }

        return sb;
    }

    @Override
    public StringBuilder visitInPlaceAssignment(InPlaceAssignmentNode ipAssignNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(ipAssignNode.typeID.id).append(' ').append('=').append(' ');
        sb.append(dynamicVisitExpression(ipAssignNode.expression));

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitInPlaceInitialisation(InPlaceInitialisationNode ipInitNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(ipInitNode.typeID.id).append(' ').append('=').append(' ')
                .append("_init_").append(ipInitNode.typeID.type).append('(');
        sb.append(dynamicVisitExpression(ipInitNode.expressions.get(0)));
        for (int i = 1; i < ipInitNode.expressions.size(); i++) {
            sb.append(',').append(' ').append(dynamicVisitExpression(ipInitNode.expressions.get(i)));
        }
        sb.append(')');

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitDelayedAssignment(DelayedAssignmentNode delayAssignNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(delayAssignNode.typeID.id).append(' ').append('=').append(' ');
        sb.append(dynamicVisitExpression(delayAssignNode.expression));

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitDelayedInitialisation(DelayedInitialisationNode delayInitNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(delayInitNode.typeID.id).append(' ').append('=').append(' ')
                .append("_init_").append(delayInitNode.typeID.type).append('(');
        sb.append(dynamicVisitExpression(delayInitNode.expressions.get(0)));
        for (int i = 1; i < delayInitNode.expressions.size(); i++) {
            sb.append(',').append(' ').append(dynamicVisitExpression(delayInitNode.expressions.get(i)));
        }
        sb.append(')');

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitTypeDeclaration(TypeDeclarationNode typeDeclareNode) {
        StringBuilder sb = new StringBuilder();

        sb.append("def _init_").append(typeDeclareNode.type).append('(').append(typeDeclareNode.members.get(0).id);
        for (int i = 1; i < typeDeclareNode.members.size(); i++) {
            sb.append(',').append(' ').append(typeDeclareNode.members.get(i).id);
        }
        sb.append(')').append(':').append('\n');

        indents++;
        sb.append(getIndents()).append("return { ");
        indents--;
        sb.append('"').append(typeDeclareNode.members.get(0).id).append('"')
                .append(':').append(typeDeclareNode.members.get(0).id);
        for (int i = 1; i < typeDeclareNode.members.size(); i++) {
            sb.append(',').append(' ').append('"').append(typeDeclareNode.members.get(i).id).append('"').append(':')
                    .append(typeDeclareNode.members.get(i).id);
        }
        sb.append(' ').append('}');

        return sb.append('\n').append('\n');
    }

    @Override
    public StringBuilder visitFunctionDeclaration(FunctionDeclarationNode funcDeclareNode) {
        StringBuilder sb = new StringBuilder();

        sb.append("def ").append(funcDeclareNode.id).append('(');
        if (funcDeclareNode.parameters.size() > 0) {
            sb.append(funcDeclareNode.parameters.get(0).id);
            for (int i = 1; i < funcDeclareNode.parameters.size(); i++) {
                sb.append(',').append(' ').append(funcDeclareNode.parameters.get(i).id);
            }
        }
        sb.append(')').append(':').append('\n');

        indents++;
        sb.append(visitFunctionBlock(funcDeclareNode.functionBlock));
        indents--;

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitUnassignedDeclaration(UnassignedDeclarationNode unassignedDeclareNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(unassignedDeclareNode.typeID.id).append(' ').append('=').append(' ').append("None");

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitConditional(ConditionalNode conditionalNode) {
        StringBuilder sb = new StringBuilder();

        sb.append("if ").append(dynamicVisitExpression(conditionalNode.ifExpression))
                .append(':').append('\n');
        indents++;
        sb.append(visitIfBlock(conditionalNode.ifBlock));
        indents--;

        for (int i = 0; i < conditionalNode.elseifExpressions.size(); i++) {
            sb.append(getIndents()).append("elif ")
                    .append(dynamicVisitExpression(conditionalNode.elseifExpressions.get(i))).append(':').append('\n');
            indents++;
            sb.append(visitIfBlock(conditionalNode.elseifBlocks.get(i)));
            indents--;
        }

        if (conditionalNode.elseBlock != null) {
            sb.append(getIndents()).append("else:\n");
            indents++;
            sb.append(visitIfBlock(conditionalNode.elseBlock));
            indents--;
        }

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitPrint(PrintNode printNode) {
        StringBuilder sb = new StringBuilder();

        sb.append("print(").append(dynamicVisitExpression(printNode.expression)).append(')');

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitFunctionBlock(FunctionBlockNode funcBlockNode) {
        StringBuilder sb = new StringBuilder();

        for (StatementNode statement : funcBlockNode.statements) {
            sb.append(getIndents()).append(dynamicVisitStatement(statement));
        }
        sb.append(getIndents()).append("return ").append(dynamicVisitExpression(funcBlockNode.returnExpression));

        return sb.append('\n');
    }

    @Override
    public StringBuilder visitIfBlock(IfBlockNode ifBlockNode) {
        StringBuilder sb = new StringBuilder();

        for (StatementNode statement : ifBlockNode.statements) {
            sb.append(getIndents()).append(dynamicVisitStatement(statement));
        }

        return sb;
    }

    @Override
    public StringBuilder visitParentheses(ParenthesesNode parenthesesNode) {
        StringBuilder sb = new StringBuilder();

        sb.append('(').append(dynamicVisitExpression(parenthesesNode.internalExpression)).append(')');

        return sb;
    }

    @Override
    public StringBuilder visitAccessMember(AccessMemberNode accessMemberNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(accessMemberNode.typeIDs.get(0).id);
        for (int i = 1; i < accessMemberNode.typeIDs.size(); i++) {
            sb.append('[').append('"').append(accessMemberNode.typeIDs.get(i).id).append('"').append(']');
        }

        return sb;
    }

    @Override
    public StringBuilder visitUnaryOperation(UnaryOperationNode unaryOpNode) {
        StringBuilder sb = new StringBuilder();

        sb.append('-').append(dynamicVisitExpression(unaryOpNode.operand));

        return sb;
    }

    @Override
    public StringBuilder visitBinaryOperation(BinaryOperationNode binaryOpNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(dynamicVisitExpression(binaryOpNode.left)).append(' ').append(binaryOpNode.operation).append(' ')
                .append(dynamicVisitExpression(binaryOpNode.right));

        return sb;
    }

    @Override
    public StringBuilder visitLiteral(LiteralNode literalNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(literalNode.value.toString());

        return sb;
    }

    @Override
    public StringBuilder visitIdentifier(IdentifierNode idNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(idNode.id);

        return sb;
    }

    @Override
    public StringBuilder visitFunction(FunctionNode funcNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(funcNode.id).append('(');

        if (funcNode.args.size() > 0) {
            sb.append(dynamicVisitExpression(funcNode.args.get(0)));
            for (int i = 1; i < funcNode.args.size(); i++) {
                sb.append(", ").append(dynamicVisitExpression(funcNode.args.get(i)));
            }

        }
        sb.append(')');

        return sb;
    }

    @Override
    public StringBuilder dynamicVisitStatement(StatementNode statementNode) {
        return super.dynamicVisitStatement(statementNode);
    }

    @Override
    public StringBuilder dynamicVisitAssignment(AssignmentNode assignmentNode) {
        return super.dynamicVisitAssignment(assignmentNode);
    }

    @Override
    public StringBuilder dynamicVisitExpression(ExpressionNode expressionNode) {
        return super.dynamicVisitExpression(expressionNode);
    }

    private StringBuilder getIndents() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t".repeat(indents));

        return sb;
    }
}
