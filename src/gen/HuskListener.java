// Generated from C:/Users/asus/OneDrive - University of Essex/Year 3/CE305 - Languages & Compilers/Assignment 2/Small Compiler/src/Husk.g4 by ANTLR 4.13.1
package gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HuskParser}.
 */
public interface HuskListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HuskParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(HuskParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(HuskParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(HuskParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(HuskParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InPlacePrimitiveAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterInPlacePrimitiveAssignment(HuskParser.InPlacePrimitiveAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InPlacePrimitiveAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitInPlacePrimitiveAssignment(HuskParser.InPlacePrimitiveAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InPlaceTypeAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterInPlaceTypeAssignment(HuskParser.InPlaceTypeAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InPlaceTypeAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitInPlaceTypeAssignment(HuskParser.InPlaceTypeAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code InPlaceTypeInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterInPlaceTypeInitialisation(HuskParser.InPlaceTypeInitialisationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code InPlaceTypeInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitInPlaceTypeInitialisation(HuskParser.InPlaceTypeInitialisationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DelayedAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDelayedAssignment(HuskParser.DelayedAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DelayedAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDelayedAssignment(HuskParser.DelayedAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DelayedInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterDelayedInitialisation(HuskParser.DelayedInitialisationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DelayedInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitDelayedInitialisation(HuskParser.DelayedInitialisationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(HuskParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(HuskParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterFuncDeclaration(HuskParser.FuncDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitFuncDeclaration(HuskParser.FuncDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnassignedDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterUnassignedDeclaration(HuskParser.UnassignedDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnassignedDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitUnassignedDeclaration(HuskParser.UnassignedDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(HuskParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(HuskParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#elseif}.
	 * @param ctx the parse tree
	 */
	void enterElseif(HuskParser.ElseifContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#elseif}.
	 * @param ctx the parse tree
	 */
	void exitElseif(HuskParser.ElseifContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElse(HuskParser.ElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElse(HuskParser.ElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#if}.
	 * @param ctx the parse tree
	 */
	void enterIf(HuskParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#if}.
	 * @param ctx the parse tree
	 */
	void exitIf(HuskParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(HuskParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(HuskParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#members}.
	 * @param ctx the parse tree
	 */
	void enterMembers(HuskParser.MembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#members}.
	 * @param ctx the parse tree
	 */
	void exitMembers(HuskParser.MembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(HuskParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(HuskParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(HuskParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(HuskParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#funcBlock}.
	 * @param ctx the parse tree
	 */
	void enterFuncBlock(HuskParser.FuncBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#funcBlock}.
	 * @param ctx the parse tree
	 */
	void exitFuncBlock(HuskParser.FuncBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link HuskParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(HuskParser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link HuskParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(HuskParser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AccessMember}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAccessMember(HuskParser.AccessMemberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AccessMember}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAccessMember(HuskParser.AccessMemberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperation(HuskParser.UnaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperation(HuskParser.UnaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(HuskParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(HuskParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionInfixCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionInfixCall(HuskParser.FunctionInfixCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionInfixCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionInfixCall(HuskParser.FunctionInfixCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperation(HuskParser.BinaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperation(HuskParser.BinaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralValue}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralValue(HuskParser.LiteralValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralValue}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralValue(HuskParser.LiteralValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(HuskParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(HuskParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParentheses(HuskParser.ParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParentheses(HuskParser.ParenthesesContext ctx);
}