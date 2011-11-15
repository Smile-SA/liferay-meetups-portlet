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

package fr.smile.socialnetworking.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import fr.smile.socialnetworking.MeetupsEntryEndDateException;
import fr.smile.socialnetworking.MeetupsEntryStartDateException;
import fr.smile.socialnetworking.model.MeetupsEntry;
import fr.smile.socialnetworking.service.base.MeetupsEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MeetupsEntryLocalServiceImpl
	extends MeetupsEntryLocalServiceBaseImpl {

	public MeetupsEntry addMeetupsEntry(
			long userId, String title, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int endDateHour, int endDateMinute,
			int totalAttendees, int maxAttendees, double price,
			byte[] thumbnail)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(userId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new MeetupsEntryStartDateException());

		Date endDate = PortalUtil.getDate(
			endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, user.getTimeZone(),
			new MeetupsEntryEndDateException());

		Date now = new Date();

		long meetupsEntryId = counterLocalService.increment();

		MeetupsEntry meetupsEntry = meetupsEntryPersistence.create(
			meetupsEntryId);

		meetupsEntry.setCompanyId(user.getCompanyId());
		meetupsEntry.setUserId(user.getUserId());
		meetupsEntry.setUserName(user.getFullName());
		meetupsEntry.setCreateDate(now);
		meetupsEntry.setModifiedDate(now);
		meetupsEntry.setTitle(title);
		meetupsEntry.setDescription(description);
		meetupsEntry.setStartDate(startDate);
		meetupsEntry.setEndDate(endDate);
		meetupsEntry.setTotalAttendees(totalAttendees);
		meetupsEntry.setMaxAttendees(maxAttendees);
		meetupsEntry.setPrice(price);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			meetupsEntry.setThumbnailId(counterLocalService.increment());
		}

		meetupsEntryPersistence.update(meetupsEntry, false);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			ImageLocalServiceUtil.updateImage(
				meetupsEntry.getThumbnailId(), thumbnail);
		}

		return meetupsEntry;
	}
	
	public MeetupsEntry addMeetupsEntry(
			long userId, String title, String description, int startDateMonth,
			int startDateDay, int startDateYear, int startDateHour,
			int startDateMinute, int endDateMonth, int endDateDay,
			int endDateYear, int endDateHour, int endDateMinute,
			int totalAttendees, int maxAttendees, double price,
			byte[] thumbnail, long groupId)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(userId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new MeetupsEntryStartDateException());

		Date endDate = PortalUtil.getDate(
			endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, user.getTimeZone(),
			new MeetupsEntryEndDateException());

		Date now = new Date();

		long meetupsEntryId = counterLocalService.increment();

		MeetupsEntry meetupsEntry = meetupsEntryPersistence.create(
			meetupsEntryId);

		meetupsEntry.setCompanyId(user.getCompanyId());
		meetupsEntry.setUserId(user.getUserId());
		meetupsEntry.setUserName(user.getFullName());
		meetupsEntry.setCreateDate(now);
		meetupsEntry.setModifiedDate(now);
		meetupsEntry.setTitle(title);
		meetupsEntry.setDescription(description);
		meetupsEntry.setStartDate(startDate);
		meetupsEntry.setEndDate(endDate);
		meetupsEntry.setTotalAttendees(totalAttendees);
		meetupsEntry.setMaxAttendees(maxAttendees);
		meetupsEntry.setPrice(price);
		meetupsEntry.setGroupId(groupId);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			meetupsEntry.setThumbnailId(counterLocalService.increment());
		}

		meetupsEntryPersistence.update(meetupsEntry, false);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			ImageLocalServiceUtil.updateImage(
				meetupsEntry.getThumbnailId(), thumbnail);
		}

		return meetupsEntry;
	}

	public void deleteMeetupsEntry(long meetupsEntryId)
		throws PortalException, SystemException {

		MeetupsEntry meetupsEntry = meetupsEntryPersistence.findByPrimaryKey(
			meetupsEntryId);

		meetupsRegistrationPersistence.removeByMeetupsEntryId(meetupsEntryId);

		ImageLocalServiceUtil.deleteImage(meetupsEntry.getThumbnailId());

		meetupsEntryPersistence.remove(meetupsEntry);
	}
	
	/* Smile Add */
	public void deleteMeetupsEntries(long groupId)
	throws PortalException, SystemException {
		
		meetupsEntryPersistence.removeByGroupId(groupId);
		
	}

	public List<MeetupsEntry> getMeetupsEntriesByCompany(long companyId)
		throws SystemException {

		return meetupsEntryPersistence.findByCompanyId(companyId);
	}
	
	public List<MeetupsEntry> getMeetupsEntriesByGroup(long groupId)
	throws SystemException {

	return meetupsEntryPersistence.findByGroupId(groupId);
}

	public List<MeetupsEntry> getMeetupsEntriesByUser(long userId)
		throws SystemException {

		return meetupsEntryPersistence.findByUserId(userId);
	}

	public MeetupsEntry updateMeetupsEntry(
			long userId, long meetupsEntryId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			int totalAttendees, int maxAttendees, double price,
			byte[] thumbnail)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(userId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new MeetupsEntryStartDateException());

		Date endDate = PortalUtil.getDate(
			endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, user.getTimeZone(),
			new MeetupsEntryEndDateException());

		MeetupsEntry meetupsEntry = meetupsEntryPersistence.findByPrimaryKey(
			meetupsEntryId);

		meetupsEntry.setModifiedDate(new Date());
		meetupsEntry.setTitle(title);
		meetupsEntry.setDescription(description);
		meetupsEntry.setStartDate(startDate);
		meetupsEntry.setEndDate(endDate);
		meetupsEntry.setTotalAttendees(totalAttendees);
		meetupsEntry.setMaxAttendees(maxAttendees);
		meetupsEntry.setPrice(price);

		if ((thumbnail != null) && (thumbnail.length > 0) &&
			(meetupsEntry.getThumbnailId() == 0)) {

			meetupsEntry.setThumbnailId(counterLocalService.increment());
		}

		meetupsEntryPersistence.update(meetupsEntry, false);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			ImageLocalServiceUtil.updateImage(
				meetupsEntry.getThumbnailId(), thumbnail);
		}

		return meetupsEntry;
	}
	
	public MeetupsEntry updateMeetupsEntry(
			long userId, long meetupsEntryId, String title, String description,
			int startDateMonth, int startDateDay, int startDateYear,
			int startDateHour, int startDateMinute, int endDateMonth,
			int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
			int totalAttendees, int maxAttendees, double price,
			byte[] thumbnail, long groupId)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(userId);

		Date startDate = PortalUtil.getDate(
			startDateMonth, startDateDay, startDateYear, startDateHour,
			startDateMinute, user.getTimeZone(),
			new MeetupsEntryStartDateException());

		Date endDate = PortalUtil.getDate(
			endDateMonth, endDateDay, endDateYear, endDateHour,
			endDateMinute, user.getTimeZone(),
			new MeetupsEntryEndDateException());

		MeetupsEntry meetupsEntry = meetupsEntryPersistence.findByPrimaryKey(
			meetupsEntryId);

		meetupsEntry.setModifiedDate(new Date());
		meetupsEntry.setTitle(title);
		meetupsEntry.setDescription(description);
		meetupsEntry.setStartDate(startDate);
		meetupsEntry.setEndDate(endDate);
		meetupsEntry.setTotalAttendees(totalAttendees);
		meetupsEntry.setMaxAttendees(maxAttendees);
		meetupsEntry.setPrice(price);
		meetupsEntry.setGroupId(groupId);

		if ((thumbnail != null) && (thumbnail.length > 0) &&
			(meetupsEntry.getThumbnailId() == 0)) {

			meetupsEntry.setThumbnailId(counterLocalService.increment());
		}

		meetupsEntryPersistence.update(meetupsEntry, false);

		if ((thumbnail != null) && (thumbnail.length > 0)) {
			ImageLocalServiceUtil.updateImage(
				meetupsEntry.getThumbnailId(), thumbnail);
		}

		return meetupsEntry;
	}

}