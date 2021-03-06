/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.html.core.internal.validate;

abstract class AbstractErrorInfo implements ErrorInfo, ErrorState {


	private int state = NONE_ERROR;
	private Segment seg = null;

	public AbstractErrorInfo(int state, Segment seg) {
		super();
		this.state = state;
		this.seg = seg;
	}

	abstract public String getHint();

	abstract public short getTargetType();

	public int getLength() {
		return (seg == null) ? 0 : seg.getLength();
	}

	public int getOffset() {
		return (seg == null) ? 0 : seg.getOffset();
	}

	public int getState() {
		return this.state;
	}
}
