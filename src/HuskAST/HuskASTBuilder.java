package HuskAST;

import HuskAST.HuskAST.*;

import gen.HuskBaseVisitor;
import gen.HuskParser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HuskASTBuilder extends HuskBaseVisitor<HuskAST.HuskASTNode> {
    private boolean catastrophic_failure = false;

    private Scope scope;
    private final HashSet<String> types;
    private final HashMap<String, HashMap<String, String>> customTypes;


    private HuskASTBuilder() {
        scope = Scope.CreateBaseScope();
        types = new HashSet<>();
        customTypes = new HashMap<>();

        types.add("Num");
        types.add("Str");
        types.add("Char");
    }

    public static HuskASTNode buildAST(StartContext ctx) {
        return new HuskASTBuilder().visitStart(ctx);
    }

    @Override
    public HuskASTNode visitStart(StartContext ctx) {
        List<StatementNode> statements = new ArrayList<>();

        for (StatementContext sc : ctx.statement()) {
            statements.add((StatementNode)dynamicVisitStatement(sc));
        }

        return catastrophic_failure ? null : new StartNode(statements);
    }

    @Override
    public HuskASTNode visitInPlacePrimitiveAssignment(InPlacePrimitiveAssignmentContext ctx) {
        String primitiveType = ctx.PRIMITIVE().getText();
        String id = ctx.ID().getText();
        addIDTypeToScope(id, primitiveType);

        ExpressionNode expression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new InPlaceAssignmentNode(new TypeID(primitiveType, id), expression);
    }

    @Override
    public HuskASTNode visitInPlaceTypeAssignment(InPlaceTypeAssignmentContext ctx) {
        String customType = ctx.TYPE().getText();
        String id = ctx.ID().getText();
        addIDTypeToScope(id, customType);

        ExpressionNode expression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new InPlaceAssignmentNode(new TypeID(customType, id), expression);
    }

    @Override
    public HuskASTNode visitInPlaceTypeInitialisation(InPlaceTypeInitialisationContext ctx) {
        String customType = ctx.TYPE().getText();
        String id = ctx.ID().getText();
        addIDTypeToScope(id, customType);

        List<ExpressionNode> arguments = new ArrayList<>();
        for (ExpressionContext argument : ctx.expression()) {
            arguments.add((ExpressionNode)dynamicVisitExpression(argument));
        }

        return new InPlaceInitialisationNode(new TypeID(customType, id), arguments);
    }

    @Override
    public HuskASTNode visitDelayedAssignment(DelayedAssignmentContext ctx) {
        String id = ctx.ID().getText();
        String type;
        if (!scope.IDTypes().containsKey(id)) {
            System.out.println("Cannot assign to an ID that doesn't exist:\n\t" +
                    id + ":?");
            return null;
        }
        type = scope.IDTypes().get(id);
        types.add(type);

        ExpressionNode expression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new DelayedAssignmentNode(new TypeID(type, id), expression);
    }

    @Override
    public HuskASTNode visitDelayedInitialisation(DelayedInitialisationContext ctx) {
        String id = ctx.ID().getText();
        String type;
        if (!scope.IDTypes().containsKey(id)) {
            System.out.println("Cannot initialise to an ID that doesn't exist:\n\t" +
                    id + ":?");
            return null;
        }
        type = scope.IDTypes().get(id);
        types.add(type);

        List<ExpressionNode> arguments = new ArrayList<>();
        for (ExpressionContext argument : ctx.expression()) {
            arguments.add((ExpressionNode)dynamicVisitExpression(argument));
        }

        return new DelayedInitialisationNode(new TypeID(type, id), arguments);
    }

    @Override
    public HuskASTNode visitTypeDeclaration(TypeDeclarationContext ctx) {
        String typeName = ctx.TYPE().getText();
        if (types.contains(typeName)) {
            System.out.println("Cannot declare a type that already exists:\n\t" +
                    typeName);
            return null;
        }
        types.add(typeName);

        List<TypeID> members = new ArrayList<>();
        HashMap<String, String> typeIDTypes = new HashMap<>();
        for (ParameterContext member : ctx.members().parameter()) {
            String type = member.type.getText();
            String id = member.ID().getText();
            if (!types.contains(type)) {
                System.out.println("Cannot use types that don't exist for custom type members:\n\t" +
                        typeName + "->" + id + ":" + type);
                continue;
            }

            members.add(new TypeID(type, id));
            typeIDTypes.put(id, type);
        }
        customTypes.put(typeName, typeIDTypes);

        return new TypeDeclarationNode(typeName, members);
    }

    @Override
    public HuskASTNode visitFuncDeclaration(FuncDeclarationContext ctx) {
        boolean failed = false;

        String funcName = ctx.ID().getText();
        addIDTypeToScope(funcName, "func");
        scope = Scope.CreateFunctionScope(scope);

        List<TypeID> parameters = new ArrayList<>();
        for (ParameterContext parameter : ctx.parameters().parameter()) {
            String type = parameter.type.getText();
            String id = parameter.ID().getText();
            if (!types.contains(type)) {
                System.out.println("Parameter types of a function cannot be a type that doesn't exist:\n\t" +
                        funcName + ":func-param=" + id + ":" + type);
                failed = true;
                continue;
            }

            if (scope.IDTypes().containsKey(id)) {
                System.out.println("Cannot use duplicate IDs for function parameters:\n\t" +
                        id + ':' + type);
                failed = true;
                continue;
            }
            scope.IDTypes().put(id, type);

            parameters.add(new TypeID(type, id));
        }

        String returnType = ctx.parameters().returnType.getText();
        if (!types.contains(returnType)) {
            System.out.println("Return type of a function cannot be a type that doesn't exist:\n\t" +
                    funcName + ":func->returnType:" + returnType);
            failed = true;
        }

        FunctionBlockNode funcBlock = (FunctionBlockNode)visitFuncBlock(ctx.funcBlock());
        if (funcBlock == null) {
            failed = true;
        }

        scope = scope.Parent();
        return failed ? null : new FunctionDeclarationNode(funcName, parameters, returnType, funcBlock);
    }

    @Override
    public HuskASTNode visitUnassignedDeclaration(UnassignedDeclarationContext ctx) {
        String type = ctx.type.getText();
        String id = ctx.ID().getText();
        addIDTypeToScope(id, type);

        return new UnassignedDeclarationNode(new TypeID(type, id));
    }

    @Override
    public HuskASTNode visitConditional(ConditionalContext ctx) {
        scope = Scope.CreateConditionalScope(scope);

        ExpressionNode ifExpression = (ExpressionNode)dynamicVisitExpression(ctx.if_().expression());
        IfBlockNode ifBlock = (IfBlockNode)visitIfBlock(ctx.if_().ifBlock());

        List<ExpressionNode> elseifExpressions = new ArrayList<>();
        List<IfBlockNode> elseifBlocks = new ArrayList<>();
        for (ElseifContext elseif : ctx.elseif()) {
            elseifExpressions.add((ExpressionNode)dynamicVisitExpression(elseif.expression()));
            elseifBlocks.add((IfBlockNode)visitIfBlock(elseif.ifBlock()));
        }

        IfBlockNode elseBlock = (IfBlockNode)visitIfBlock(ctx.else_().ifBlock());

        scope = scope.Parent();
        return new ConditionalNode(ifExpression, ifBlock, elseifExpressions, elseifBlocks, elseBlock);
    }

    @Override
    public HuskASTNode visitPrint(PrintContext ctx) {
        ExpressionNode expression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new PrintNode(expression);
    }


    // blocks

    @Override
    public HuskASTNode visitFuncBlock(FuncBlockContext ctx) {
        List<StatementNode> statements = new ArrayList<>();
        for (StatementContext statement : ctx.statement()) {
            statements.add((StatementNode)dynamicVisitStatement(statement));
        }

        ExpressionNode returnExpression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new FunctionBlockNode(statements, returnExpression);
    }

    @Override
    public HuskASTNode visitIfBlock(IfBlockContext ctx) {
        List<StatementNode> statements = new ArrayList<>();
        for (StatementContext statement : ctx.statement()) {
            statements.add((StatementNode)dynamicVisitStatement(statement));
        }

        return new IfBlockNode(statements);
    }



    // THE BIG BOY - EXPRESSION
    @Override
    public HuskASTNode visitParentheses(ParenthesesContext ctx) {
        ExpressionNode internalExpression = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new ParenthesesNode(internalExpression);
    }

    @Override
    public HuskASTNode visitAccessMember(AccessMemberContext ctx) {
        List<TypeID> typeIDs = new ArrayList<>();

        String obj = ctx.ID(0).getText();
        if (!scope.IDTypes().containsKey(obj)) {
            System.out.println("Cannot access the member of a type that doesn't exist:\n\t" +
                    obj + ":?");
            return null;
        }
        String objType = scope.IDTypes().get(obj);
        typeIDs.add(new TypeID(objType, obj));

        for (int i = 1; i < ctx.ID().size(); i++) {
            obj = ctx.ID(i).getText();
            if (!(customTypes.containsKey(objType) && customTypes.get(objType).containsKey(obj))) {
                System.out.println("Cannot access a member that doesn't exist:\n\t" +
                        obj + ":?");
                return null;
            }
            objType = customTypes.get(objType).get(obj);
            typeIDs.add(new TypeID(objType, obj));
        }

        return new AccessMemberNode(typeIDs);
    }

    @Override
    public HuskASTNode visitUnaryOperation(UnaryOperationContext ctx) {
        ExpressionNode operand = (ExpressionNode)dynamicVisitExpression(ctx.expression());

        return new UnaryOperationNode(operand);
    }

    @Override
    public HuskASTNode visitBinaryOperation(BinaryOperationContext ctx) {
        ExpressionNode left = (ExpressionNode)dynamicVisitExpression(ctx.lhs);
        String operation = ctx.op.getText();
        ExpressionNode right = (ExpressionNode)dynamicVisitExpression(ctx.rhs);

        return new BinaryOperationNode(left, operation, right);
    }

    @Override
    public HuskASTNode visitLiteralValue(LiteralValueContext ctx) {
        String tempValue = ctx.getText();
        String type;
        Object value;
        if (tempValue.matches("\\d+|(\\d*\\.\\d+)")) {
            type = "Num";
            value = Float.parseFloat(tempValue);
        }
        else if (tempValue.matches("\".*?\"")) {
            type = "Str";
            value = tempValue;
        }
        else {
            System.out.println("Cannot parse literal value:\n\t" +
                    tempValue);
            return null;
        }

        return new LiteralNode(type, value);
    }

    @Override
    public HuskASTNode visitIdentifier(IdentifierContext ctx) {
        String id = ctx.ID().getText();
        if (!scope.IDTypes().containsKey(id)) {
            System.out.println("Cannot use an ID that doesn't exist:\n\t" +
                    id + ":?");
            return null;
        }

        return new IdentifierNode(scope.IDTypes().get(id), id);
    }

    @Override
    public HuskASTNode visitFunctionCall(FunctionCallContext ctx) {
        String id = ctx.ID().getText();
        if (!scope.IDTypes().containsKey(id)) {
            System.out.println("Cannot call a function that has not been declared:\n\t" +
                    id + ":func");
            return null;
        }
        else if (!scope.IDTypes().get(id).equals("func")) {
            System.out.println("Cannot call an ID as a function:\n\t" +
                    id + ":" + scope.IDTypes().get(id));
            return null;
        }

        List<ExpressionNode> arguments = new ArrayList<>();
        for (ExpressionContext argument : ctx.expression()) {
            arguments.add((ExpressionNode)dynamicVisitExpression(argument));
        }

        return new FunctionNode(id, arguments);
    }

    @Override
    public HuskASTNode visitFunctionInfixCall(FunctionInfixCallContext ctx) {
        String id = ctx.ID().getText();
        if (!scope.IDTypes().containsKey(id)) {
            System.out.println("Cannot call a function that has not been declared:\n\t" +
                    id + ":func");
            return null;
        }
        else if (!scope.IDTypes().get(id).equals("func")) {
            System.out.println("Cannot call an ID as a function:\n\t" +
                    id + ":" + scope.IDTypes().get(id));
            return null;
        }

        List<ExpressionNode> arguments = new ArrayList<>();
        for (ExpressionContext argument : ctx.expression()) {
            arguments.add((ExpressionNode)dynamicVisitExpression(argument));
        }   // still treats the first arg in the same ordered list of all args

        return new FunctionNode(id, arguments);
    }

    // quality of life
    public HuskASTNode dynamicVisitStatement(StatementContext ctx) {
        if (ctx.assignment() != null)
            return dynamicVisitAssignment(ctx.assignment());
        if (ctx.declaration() != null)
            return dynamicVisitDeclaration(ctx.declaration());
        if (ctx.conditional() != null)
            return visitConditional(ctx.conditional());
        if (ctx.print() != null)
            return visitPrint(ctx.print());

        return null;
    }

    public HuskASTNode dynamicVisitDeclaration(DeclarationContext ctx) {
        return switch (ctx) {
            case TypeDeclarationContext tdc -> visitTypeDeclaration(tdc);
            case FuncDeclarationContext fdc -> visitFuncDeclaration(fdc);
            case UnassignedDeclarationContext udc -> visitUnassignedDeclaration(udc);
            default -> null;
        };
    }

    public HuskASTNode dynamicVisitAssignment(AssignmentContext ctx) {
        return switch (ctx) {
            case InPlacePrimitiveAssignmentContext ippac -> visitInPlacePrimitiveAssignment(ippac);
            case InPlaceTypeAssignmentContext iptac -> visitInPlaceTypeAssignment(iptac);
            case InPlaceTypeInitialisationContext iptic -> visitInPlaceTypeInitialisation(iptic);
            case DelayedAssignmentContext dac -> visitDelayedAssignment(dac);
            case DelayedInitialisationContext dic -> visitDelayedInitialisation(dic);
            default -> null;
        };
    }

    public HuskASTNode dynamicVisitExpression(ExpressionContext ctx) {
        return switch (ctx) {
            case ParenthesesContext pc -> visitParentheses(pc);
            case AccessMemberContext amc -> visitAccessMember(amc);
            case UnaryOperationContext uoc -> visitUnaryOperation(uoc);
            case BinaryOperationContext boc -> visitBinaryOperation(boc);
            case LiteralValueContext lvc -> visitLiteralValue(lvc);
            case IdentifierContext ic -> visitIdentifier(ic);
            case FunctionCallContext fcc -> visitFunctionCall(fcc);
            case FunctionInfixCallContext ficc -> visitFunctionInfixCall(ficc);
            default -> null;
        };
    }

    private void addIDTypeToScope(String id, String type) {
        if (scope.IDTypes().containsKey(id)) {
            System.out.println("The same ID cannot be declared twice in the same scope:\n\t" + id + ":" + type);
        }
        else {
            scope.IDTypes().put(id, type);
            types.add(type);
        }
    }
}
