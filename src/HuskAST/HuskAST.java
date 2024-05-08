package HuskAST;

import java.util.List;

public class HuskAST {

    public static class TypeID {
        public String type;
        public String id;

        public TypeID(String type, String id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public String toString() {
            return id + ':' + type;
        }
    }

    public abstract static class HuskASTNode {

    }


    public static class StartNode extends HuskASTNode {
        public List<StatementNode> statements;

        public StartNode(List<StatementNode> statements) {
            this.statements = statements;
        }
    }


    public abstract static class StatementNode extends HuskASTNode {

    }

    public abstract static class AssignmentNode extends StatementNode {
        public TypeID typeID;
    }

    // regards both primitive and type (in-place) assignments
    public static class InPlaceAssignmentNode extends AssignmentNode {
        public ExpressionNode expression;

        public InPlaceAssignmentNode(TypeID typeID, ExpressionNode expression) {
            this.typeID = typeID;
            this.expression = expression;
        }
    }

    public static class InPlaceInitialisationNode extends AssignmentNode {
        public List<ExpressionNode> expressions;

        public InPlaceInitialisationNode(TypeID typeID, List<ExpressionNode> expressions) {
            this.typeID = typeID;
            this.expressions = expressions;
        }
    }

    // regards both primitive and type (delayed) assignments
    public static class DelayedAssignmentNode extends AssignmentNode {
        public ExpressionNode expression;

        public DelayedAssignmentNode(TypeID typeID, ExpressionNode expression) {
            this.typeID = typeID;
            this.expression = expression;
        }
    }

    public static class DelayedInitialisationNode extends AssignmentNode {
        public List<ExpressionNode> expressions;

        public DelayedInitialisationNode(TypeID typeID, List<ExpressionNode> expressions) {
            this.typeID = typeID;
            this.expressions = expressions;
        }
    }

    public static class TypeDeclarationNode extends StatementNode {
        public String type;

        public List<TypeID> members;

        public TypeDeclarationNode(String type, List<TypeID> members) {
            this.type = type;
            this.members = members;
        }
    }

    public static class FunctionDeclarationNode extends StatementNode {
        public String id;

        public List<TypeID> parameters;
        public String returnType;

        public FunctionBlockNode functionBlock;

        public FunctionDeclarationNode(String id, List<TypeID> parameters, String returnType, FunctionBlockNode functionBlock) {
            this.id = id;
            this.parameters = parameters;
            this.returnType = returnType;
            this.functionBlock = functionBlock;
        }
    }

    public static class UnassignedDeclarationNode extends StatementNode {
        public TypeID typeID;

        public UnassignedDeclarationNode(TypeID typeID) {
            this.typeID = typeID;
        }
    }

    public static class ConditionalNode extends StatementNode {
        public ExpressionNode ifExpression;
        public IfBlockNode ifBlock;

        public List<ExpressionNode> elseifExpressions;
        public List<IfBlockNode> elseifBlocks;

        public IfBlockNode elseBlock;

        public ConditionalNode(ExpressionNode ifExpression, IfBlockNode ifBlock, List<ExpressionNode> elseifExpressions, List<IfBlockNode> elseifBlocks, IfBlockNode elseBlock) {
            this.ifExpression = ifExpression;
            this.ifBlock = ifBlock;
            this.elseifExpressions = elseifExpressions;
            this.elseifBlocks = elseifBlocks;
            this.elseBlock = elseBlock;
        }
    }

    public static class PrintNode extends StatementNode {
        public ExpressionNode expression;

        public PrintNode(ExpressionNode expression) {
            this.expression = expression;
        }
    }


    public static class FunctionBlockNode extends HuskASTNode {
        public List<StatementNode> statements;
        public ExpressionNode returnExpression;

        public FunctionBlockNode(List<StatementNode> statements, ExpressionNode returnExpression) {
            this.statements = statements;
            this.returnExpression = returnExpression;
        }
    }

    public static class IfBlockNode extends HuskASTNode {
        public List<StatementNode> statements;

        public IfBlockNode(List<StatementNode> statements) {
            this.statements = statements;
        }
    }


    public abstract static class ExpressionNode extends HuskASTNode {

    }

    public static class ParenthesesNode extends ExpressionNode {
        public ExpressionNode internalExpression;

        public ParenthesesNode(ExpressionNode internalExpression) {
            this.internalExpression = internalExpression;
        }
    }

    public static class AccessMemberNode extends ExpressionNode {
        List<TypeID> typeIDs;

        public AccessMemberNode(List<TypeID> typeIDs) {
            this.typeIDs = typeIDs;
        }
    }

    public static class UnaryOperationNode extends ExpressionNode {
        public ExpressionNode operand;

        public UnaryOperationNode(ExpressionNode operand) {
            this.operand = operand;
        }
    }

    public static class BinaryOperationNode extends ExpressionNode {
        public ExpressionNode left;
        public String operation;
        public ExpressionNode right;

        public BinaryOperationNode(ExpressionNode left, String operation, ExpressionNode right) {
            this.left = left;
            this.operation = operation;
            this.right = right;
        }
    }

    public static class LiteralNode extends ExpressionNode {
        public String type;
        public Object value;

        public LiteralNode(String type, Object value) {
            this.type = type;
            this.value = value;
        }
    }

    public static class IdentifierNode extends ExpressionNode {
        public String type;
        public String id;

        public IdentifierNode(String type, String id) {
            this.type = type;
            this.id = id;
        }
    }

    public static class FunctionNode extends ExpressionNode {
        public String id;

        public List<ExpressionNode> args;

        public FunctionNode(String id, List<ExpressionNode> args) {
            this.id = id;
            this.args = args;
        }
    }
}
