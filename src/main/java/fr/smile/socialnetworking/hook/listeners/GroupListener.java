/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package fr.smile.socialnetworking.hook.listeners;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Group;

import fr.smile.socialnetworking.service.MeetupsEntryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupListener extends BaseModelListener<Group> {

	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			if (group.isUser()) {
				MeetupsEntryLocalServiceUtil.deleteMeetupsEntries(group.getGroupId());
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}