// Generated from C:/Users/asus/OneDrive - University of Essex/Year 3/CE305 - Languages & Compilers/Assignment 2/Small Compiler/src/Husk.g4 by ANTLR 4.13.1
package gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HuskParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HuskVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HuskParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(HuskParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(HuskParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InPlacePrimitiveAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInPlacePrimitiveAssignment(HuskParser.InPlacePrimitiveAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InPlaceTypeAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInPlaceTypeAssignment(HuskParser.InPlaceTypeAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InPlaceTypeInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInPlaceTypeInitialisation(HuskParser.InPlaceTypeInitialisationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DelayedAssignment}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelayedAssignment(HuskParser.DelayedAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DelayedInitialisation}
	 * labeled alternative in {@link HuskParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelayedInitialisation(HuskParser.DelayedInitialisationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypeDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(HuskParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDeclaration(HuskParser.FuncDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnassignedDeclaration}
	 * labeled alternative in {@link HuskParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnassignedDeclaration(HuskParser.UnassignedDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(HuskParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#elseif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseif(HuskParser.ElseifContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#else}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse(HuskParser.ElseContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(HuskParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(HuskParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#members}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMembers(HuskParser.MembersContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(HuskParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(HuskParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#funcBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncBlock(HuskParser.FuncBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link HuskParser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(HuskParser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AccessMember}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessMember(HuskParser.AccessMemberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperation(HuskParser.UnaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Identifier}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(HuskParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionInfixCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionInfixCall(HuskParser.FunctionInfixCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryOperation}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOperation(HuskParser.BinaryOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralValue}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralValue(HuskParser.LiteralValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(HuskParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parentheses}
	 * labeled alternative in {@link HuskParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentheses(HuskParser.ParenthesesContext ctx);
}