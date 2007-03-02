/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.internal.debug.core.hcr;

import org.eclipse.wst.jsdt.core.Signature;
import org.eclipse.wst.jsdt.core.dom.ASTVisitor;
import org.eclipse.wst.jsdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.wst.jsdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.wst.jsdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.wst.jsdt.core.dom.ArrayAccess;
import org.eclipse.wst.jsdt.core.dom.ArrayCreation;
import org.eclipse.wst.jsdt.core.dom.ArrayInitializer;
import org.eclipse.wst.jsdt.core.dom.ArrayType;
import org.eclipse.wst.jsdt.core.dom.AssertStatement;
import org.eclipse.wst.jsdt.core.dom.Assignment;
import org.eclipse.wst.jsdt.core.dom.Block;
import org.eclipse.wst.jsdt.core.dom.BlockComment;
import org.eclipse.wst.jsdt.core.dom.BooleanLiteral;
import org.eclipse.wst.jsdt.core.dom.BreakStatement;
import org.eclipse.wst.jsdt.core.dom.CastExpression;
import org.eclipse.wst.jsdt.core.dom.CatchClause;
import org.eclipse.wst.jsdt.core.dom.CharacterLiteral;
import org.eclipse.wst.jsdt.core.dom.ClassInstanceCreation;
import org.eclipse.wst.jsdt.core.dom.CompilationUnit;
import org.eclipse.wst.jsdt.core.dom.ConditionalExpression;
import org.eclipse.wst.jsdt.core.dom.ConstructorInvocation;
import org.eclipse.wst.jsdt.core.dom.ContinueStatement;
import org.eclipse.wst.jsdt.core.dom.DoStatement;
import org.eclipse.wst.jsdt.core.dom.EmptyStatement;
import org.eclipse.wst.jsdt.core.dom.EnhancedForStatement;
import org.eclipse.wst.jsdt.core.dom.EnumConstantDeclaration;
import org.eclipse.wst.jsdt.core.dom.EnumDeclaration;
import org.eclipse.wst.jsdt.core.dom.ExpressionStatement;
import org.eclipse.wst.jsdt.core.dom.FieldAccess;
import org.eclipse.wst.jsdt.core.dom.FieldDeclaration;
import org.eclipse.wst.jsdt.core.dom.ForStatement;
import org.eclipse.wst.jsdt.core.dom.IMethodBinding;
import org.eclipse.wst.jsdt.core.dom.ITypeBinding;
import org.eclipse.wst.jsdt.core.dom.IfStatement;
import org.eclipse.wst.jsdt.core.dom.ImportDeclaration;
import org.eclipse.wst.jsdt.core.dom.InfixExpression;
import org.eclipse.wst.jsdt.core.dom.Initializer;
import org.eclipse.wst.jsdt.core.dom.InstanceofExpression;
import org.eclipse.wst.jsdt.core.dom.Javadoc;
import org.eclipse.wst.jsdt.core.dom.LabeledStatement;
import org.eclipse.wst.jsdt.core.dom.LineComment;
import org.eclipse.wst.jsdt.core.dom.MarkerAnnotation;
import org.eclipse.wst.jsdt.core.dom.MemberRef;
import org.eclipse.wst.jsdt.core.dom.MemberValuePair;
import org.eclipse.wst.jsdt.core.dom.MethodDeclaration;
import org.eclipse.wst.jsdt.core.dom.MethodInvocation;
import org.eclipse.wst.jsdt.core.dom.MethodRef;
import org.eclipse.wst.jsdt.core.dom.MethodRefParameter;
import org.eclipse.wst.jsdt.core.dom.Modifier;
import org.eclipse.wst.jsdt.core.dom.NormalAnnotation;
import org.eclipse.wst.jsdt.core.dom.NullLiteral;
import org.eclipse.wst.jsdt.core.dom.NumberLiteral;
import org.eclipse.wst.jsdt.core.dom.PackageDeclaration;
import org.eclipse.wst.jsdt.core.dom.ParameterizedType;
import org.eclipse.wst.jsdt.core.dom.ParenthesizedExpression;
import org.eclipse.wst.jsdt.core.dom.PostfixExpression;
import org.eclipse.wst.jsdt.core.dom.PrefixExpression;
import org.eclipse.wst.jsdt.core.dom.PrimitiveType;
import org.eclipse.wst.jsdt.core.dom.QualifiedName;
import org.eclipse.wst.jsdt.core.dom.QualifiedType;
import org.eclipse.wst.jsdt.core.dom.ReturnStatement;
import org.eclipse.wst.jsdt.core.dom.SimpleName;
import org.eclipse.wst.jsdt.core.dom.SimpleType;
import org.eclipse.wst.jsdt.core.dom.SingleMemberAnnotation;
import org.eclipse.wst.jsdt.core.dom.SingleVariableDeclaration;
import org.eclipse.wst.jsdt.core.dom.StringLiteral;
import org.eclipse.wst.jsdt.core.dom.SuperConstructorInvocation;
import org.eclipse.wst.jsdt.core.dom.SuperFieldAccess;
import org.eclipse.wst.jsdt.core.dom.SuperMethodInvocation;
import org.eclipse.wst.jsdt.core.dom.SwitchCase;
import org.eclipse.wst.jsdt.core.dom.SwitchStatement;
import org.eclipse.wst.jsdt.core.dom.SynchronizedStatement;
import org.eclipse.wst.jsdt.core.dom.TagElement;
import org.eclipse.wst.jsdt.core.dom.TextElement;
import org.eclipse.wst.jsdt.core.dom.ThisExpression;
import org.eclipse.wst.jsdt.core.dom.ThrowStatement;
import org.eclipse.wst.jsdt.core.dom.TryStatement;
import org.eclipse.wst.jsdt.core.dom.TypeDeclaration;
import org.eclipse.wst.jsdt.core.dom.TypeDeclarationStatement;
import org.eclipse.wst.jsdt.core.dom.TypeLiteral;
import org.eclipse.wst.jsdt.core.dom.TypeParameter;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationExpression;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationFragment;
import org.eclipse.wst.jsdt.core.dom.VariableDeclarationStatement;
import org.eclipse.wst.jsdt.core.dom.WhileStatement;
import org.eclipse.wst.jsdt.core.dom.WildcardType;

/**
 * Visits an AST to find a method declartion
 */
public class MethodSearchVisitor extends ASTVisitor {
    
    /**
     * Method to search for
     */
    private String fName;
    private String[] fParameterTypes;
    
    /**
     * The search result, or <code>null</code> if none
     */
    private MethodDeclaration fMatch;
    
    /**
     * Sets the signature of the method to search for
     * 
     * @param methodName name of method to search for
     * @param methodSignature signature of the method to search for
     */
    public void setTargetMethod(String methodName, String methodSignature) {
        fName = methodName;
        fParameterTypes = Signature.getParameterTypes(methodSignature);
        // convert parameter types same format that we get from the AST type bindings
        for (int i = 0; i < fParameterTypes.length; i++) {
            String type = fParameterTypes[i];
            type = type.replace('/','.');
            fParameterTypes[i] = type;
        }
        fMatch = null;
    }
    
    /**
     * Returns the search result, or <code>null</code> if none
     * 
     * @return matching method declartion or <code>null</code>
     */
    public MethodDeclaration getMatch() {
        return fMatch;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.eclipse.wst.jsdt.core.dom.ASTVisitor#visit(org.eclipse.wst.jsdt.core.dom.MethodDeclaration)
     */
    public boolean visit(MethodDeclaration node) {
        if (node.getName().getIdentifier().equals(fName)) {
            IMethodBinding methodBinding = node.resolveBinding();
            if (methodBinding != null) {
	            ITypeBinding[] typeBindings = methodBinding.getParameterTypes();
	            if (typeBindings.length == fParameterTypes.length) {
		            for (int i = 0; i < typeBindings.length; i++) {
		                ITypeBinding typeBinding = typeBindings[i];
		                String typeSignature = Signature.createTypeSignature(typeBinding.getQualifiedName(), true);
		                if (!fParameterTypes[i].equals(typeSignature)) {
		                    return true;
		                }
		            }
		            fMatch = node;
	            }
            }
        }
        return isSearching();
    }
    
    /**
     * Returns whether this visitor is still searching for a match.
     *  
     * @return whether this visitor is still searching for a match
     */
    private boolean isSearching() {
        return fMatch == null;
    }
    public boolean visit(AnnotationTypeDeclaration node) {
        return isSearching();
    }
    public boolean visit(AnnotationTypeMemberDeclaration node) {
        return isSearching();
    }
    public boolean visit(AnonymousClassDeclaration node) {
        return isSearching();
    }
    public boolean visit(ArrayAccess node) {
        return isSearching();
    }
    public boolean visit(ArrayCreation node) {
        return isSearching();
    }
    public boolean visit(ArrayInitializer node) {
        return isSearching();
    }
    public boolean visit(ArrayType node) {
        return isSearching();
    }
    public boolean visit(AssertStatement node) {
        return isSearching();
    }
    public boolean visit(Assignment node) {
        return isSearching();
    }
    public boolean visit(Block node) {
        return isSearching();
    }
    public boolean visit(BlockComment node) {
        return isSearching();
    }
    public boolean visit(BooleanLiteral node) {
        return isSearching();
    }
    public boolean visit(BreakStatement node) {
        return isSearching();
    }
    public boolean visit(CastExpression node) {
        return isSearching();
    }
    public boolean visit(CatchClause node) {
        return isSearching();
    }
    public boolean visit(CharacterLiteral node) {
        return isSearching();
    }
    public boolean visit(ClassInstanceCreation node) {
        return isSearching();
    }
    public boolean visit(CompilationUnit node) {
        return isSearching();
    }
    public boolean visit(ConditionalExpression node) {
        return isSearching();
    }
    public boolean visit(ConstructorInvocation node) {
        return isSearching();
    }
    public boolean visit(ContinueStatement node) {
        return isSearching();
    }
    public boolean visit(DoStatement node) {
        return isSearching();
    }
    public boolean visit(EmptyStatement node) {
        return isSearching();
    }
    public boolean visit(EnhancedForStatement node) {
        return isSearching();
    }
    public boolean visit(EnumConstantDeclaration node) {
        return isSearching();
    }
    public boolean visit(EnumDeclaration node) {
        return isSearching();
    }
    public boolean visit(ExpressionStatement node) {
        return isSearching();
    }
    public boolean visit(FieldAccess node) {
        return isSearching();
    }
    public boolean visit(FieldDeclaration node) {
        return isSearching();
    }
    public boolean visit(ForStatement node) {
        return isSearching();
    }
    public boolean visit(IfStatement node) {
        return isSearching();
    }
    public boolean visit(ImportDeclaration node) {
        return isSearching();
    }
    public boolean visit(InfixExpression node) {
        return isSearching();
    }
    public boolean visit(Initializer node) {
        return isSearching();
    }
    public boolean visit(InstanceofExpression node) {
        return isSearching();
    }
    public boolean visit(Javadoc node) {
        return isSearching();
    }
    public boolean visit(LabeledStatement node) {
        return isSearching();
    }
    public boolean visit(LineComment node) {
        return isSearching();
    }
    public boolean visit(MarkerAnnotation node) {
        return isSearching();
    }
    public boolean visit(MemberRef node) {
        return isSearching();
    }
    public boolean visit(MemberValuePair node) {
        return isSearching();
    }
    public boolean visit(MethodInvocation node) {
        return isSearching();
    }
    public boolean visit(MethodRef node) {
        return isSearching();
    }
    public boolean visit(MethodRefParameter node) {
        return isSearching();
    }
    public boolean visit(Modifier node) {
        return isSearching();
    }
    public boolean visit(NormalAnnotation node) {
        return isSearching();
    }
    public boolean visit(NullLiteral node) {
        return isSearching();
    }
    public boolean visit(NumberLiteral node) {
        return isSearching();
    }
    public boolean visit(PackageDeclaration node) {
        return isSearching();
    }
    public boolean visit(ParameterizedType node) {
        return isSearching();
    }
    public boolean visit(ParenthesizedExpression node) {
        return isSearching();
    }
    public boolean visit(PostfixExpression node) {
        return isSearching();
    }
    public boolean visit(PrefixExpression node) {
        return isSearching();
    }
    public boolean visit(PrimitiveType node) {
        return isSearching();
    }
    public boolean visit(QualifiedName node) {
        return isSearching();
    }
    public boolean visit(QualifiedType node) {
        return isSearching();
    }
    public boolean visit(ReturnStatement node) {
        return isSearching();
    }
    public boolean visit(SimpleName node) {
        return isSearching();
    }
    public boolean visit(SimpleType node) {
        return isSearching();
    }
    public boolean visit(SingleMemberAnnotation node) {
        return isSearching();
    }
    public boolean visit(SingleVariableDeclaration node) {
        return isSearching();
    }
    public boolean visit(StringLiteral node) {
        return isSearching();
    }
    public boolean visit(SuperConstructorInvocation node) {
        return isSearching();
    }
    public boolean visit(SuperFieldAccess node) {
        return isSearching();
    }
    public boolean visit(SuperMethodInvocation node) {
        return isSearching();
    }
    public boolean visit(SwitchCase node) {
        return isSearching();
    }
    public boolean visit(SwitchStatement node) {
        return isSearching();
    }
    public boolean visit(SynchronizedStatement node) {
        return isSearching();
    }
    public boolean visit(TagElement node) {
        return isSearching();
    }
    public boolean visit(TextElement node) {
        return isSearching();
    }
    public boolean visit(ThisExpression node) {
        return isSearching();
    }
    public boolean visit(ThrowStatement node) {
        return isSearching();
    }
    public boolean visit(TryStatement node) {
        return isSearching();
    }
    public boolean visit(TypeDeclaration node) {
        return isSearching();
    }
    public boolean visit(TypeDeclarationStatement node) {
        return isSearching();
    }
    public boolean visit(TypeLiteral node) {
        return isSearching();
    }
    public boolean visit(TypeParameter node) {
        return isSearching();
    }
    public boolean visit(VariableDeclarationExpression node) {
        return isSearching();
    }
    public boolean visit(VariableDeclarationFragment node) {
        return isSearching();
    }
    public boolean visit(VariableDeclarationStatement node) {
        return isSearching();
    }
    public boolean visit(WhileStatement node) {
        return isSearching();
    }
    public boolean visit(WildcardType node) {
        return isSearching();
    }
}
