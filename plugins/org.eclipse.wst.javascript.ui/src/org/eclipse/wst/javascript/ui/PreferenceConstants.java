/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * Created on Sep 2, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.eclipse.wst.javascript.ui;

/**
 * Preference constants used in the JDT-UI preference store. Clients should only read the
 * JDT-UI preference store using these values. Clients are not allowed to modify the 
 * preference store programmatically.
 * 
 * Copied from org.eclipse.jdt.internal.ui.text.JavaPairMatcher so we don't have to
 * depend on the org.eclipse.jdt.ui plugin.
 * 
 * Only the following 13 static constants are copied.
 */
public class PreferenceConstants {

	/**
	 * A named preference that controls whether bracket matching highlighting is turned on or off.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color used to highlight matching brackets.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a string 
	 * using class <code>PreferenceConverter</code>
	 * </p>
	 * 
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_MATCHING_BRACKETS_COLOR = "matchingBracketsColor"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows error
	 * indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_ERROR_INDICATION_IN_OVERVIEW_RULER = "errorIndicationInOverviewRuler"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows warning
	 * indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_WARNING_INDICATION_IN_OVERVIEW_RULER = "warningIndicationInOverviewRuler"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows task
	 * indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_TASK_INDICATION_IN_OVERVIEW_RULER = "taskIndicationInOverviewRuler"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows
	 * bookmark indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_BOOKMARK_INDICATION_IN_OVERVIEW_RULER = "bookmarkIndicationInOverviewRuler"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows
	 * search result indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_SEARCH_RESULT_INDICATION_IN_OVERVIEW_RULER = "searchResultIndicationInOverviewRuler"; //$NON-NLS-1$

	/**
	 * A named preference that controls whether the overview ruler shows
	 * unknown indicators.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 * @since 2.1
	 */
	public final static String EDITOR_UNKNOWN_INDICATION_IN_OVERVIEW_RULER = "othersIndicationInOverviewRuler"; //$NON-NLS-1$
}