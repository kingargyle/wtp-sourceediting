/*******************************************************************************
 * Copyright (c) 2005-2007 Orangevolt (www.orangevolt.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Lars Gersmann (www.orangevolt.com) 
 *     
 *******************************************************************************/

package org.eclipse.wst.xml.ui.editor.outline.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.text.templates.SimpleTemplateVariableResolver;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateException;

public class OutlineTemplateContextType extends TemplateContextType implements IExecutableExtension {
	
	public static final String ID = "org.eclipse.wst.xml.ui.editor.outline.contextType";
	
	public static class ElementTextResolver extends SimpleTemplateVariableResolver {
		public ElementTextResolver() {
			super( "text()", "element text content");
		}
		
		@Override
		protected String resolve(TemplateContext context) {
			return super.resolve(context);
		}
	};
	
	public static class ElementLocalNameResolver extends SimpleTemplateVariableResolver {
		public ElementLocalNameResolver() {
			super( "local-name()", "local name of element");
		}
		
		@Override
		protected String resolve(TemplateContext context) {
			return super.resolve(context);
		}
	};
	
	public static class ElementXPathResolver extends SimpleTemplateVariableResolver {
		public ElementXPathResolver() {
			super( "<xpath-expr>", "element xpath expression");
		}
		
		@Override
		protected String resolve(TemplateContext context) {
			return super.resolve(context);
		}
	};
	
	public static class ElementAttributeResolver extends SimpleTemplateVariableResolver {
		public ElementAttributeResolver() {
			super( "@<attr-name>", "element attribute");
		}
		
		@Override
		protected String resolve(TemplateContext context) {
			return super.resolve(context);
		}
	};
	
	public OutlineTemplateContextType() {
		addResolver( new ElementTextResolver());
		addResolver( new ElementAttributeResolver());
		addResolver( new ElementLocalNameResolver());
		addResolver( new ElementXPathResolver());
	}
	
	@Override
	public void validate(String pattern) throws TemplateException {
		OutlineTemplateParser parser = new OutlineTemplateParser();
		parser.parse( pattern);
	}
		
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
	}
}
