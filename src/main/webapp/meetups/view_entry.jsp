<%
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
%>

<%@ include file="/init.jsp" %>

<%
Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<style type="text/css">
.social-networking-portlet-meetups .response {
	float: left;
	margin-right: 15px;
	position: relative;
}

.social-networking-portlet-meetups .response .comments {
	background: #E2E6E8;
	border: 1px solid #6B767B;
	bottom: 110%;
	display: none;
	left: -20px;
	padding: 5px;
	position: absolute;
	width: 200px;
}

.social-networking-portlet-meetups .response.hovering .comments {
	display: block;
}

.social-networking-portlet-meetups .response .comments .indicator {
	background: url(../images/indicator.png) no-repeat;
	bottom: -15px;
	height: 15px;
	left: 50px;
	position: absolute;
	width: 15px;
}
</style>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "attending");

String redirect = ParamUtil.getString(request, "redirect");

long meetupsEntryId = ParamUtil.getLong(request, "meetupsEntryId");

MeetupsEntry meetupsEntry = null;

try {
	meetupsEntry = MeetupsEntryLocalServiceUtil.getMeetupsEntry(meetupsEntryId);
}
catch (NoSuchMeetupsEntryException nsmee) {
%>

	<span class="portlet-msg-error">
		The meetup could not be found.
	</span>

<%
	return;
}

MeetupsRegistration meetupsRegistration = null;

try {
	meetupsRegistration = MeetupsRegistrationLocalServiceUtil.getMeetupsRegistration(themeDisplay.getUserId(), meetupsEntryId);
}
catch (NoSuchMeetupsRegistrationException nsmre) {
}

int status = MeetupsConstants.STATUS_YES;

if (meetupsRegistration != null) {
	status = meetupsRegistration.getStatus();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("jspPage", "/meetups/view_entry.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("meetupsEntryId", String.valueOf(meetupsEntryId));

String thumbnailURL = null;

if (meetupsEntry.getThumbnailId() == 0) {
	thumbnailURL = request.getContextPath() + "/meetups/images/calendar.png";
}
else {
	thumbnailURL = themeDisplay.getPathImage() + "/meetups?img_id=" + meetupsEntry.getThumbnailId() + "&t=" + ImageServletTokenUtil.getToken(meetupsEntry.getThumbnailId());
}
%>

<img alt="" src="<%= thumbnailURL %>" style="float: left; margin-right: 10px;" />

<h4>
	<%= meetupsEntry.getTitle() %>
</h4>

<br />

<div>
	<%= meetupsEntry.getDescription() %>
</div>

<br />

<div>
	<%= dateFormatDateTime.format(meetupsEntry.getStartDate()) %> &#150; <%= dateFormatDateTime.format(meetupsEntry.getEndDate()) %>
</div>

<br/>

<%
int yesTotal = MeetupsRegistrationLocalServiceUtil.getMeetupsRegistrationsCount(meetupsEntryId, MeetupsConstants.STATUS_YES);
%>

<c:if test="<%= yesTotal > 1 %>">
	<div>
		<%= LanguageUtil.format(pageContext, "x-people-are-planning-to-attend-this-meetup", String.valueOf(yesTotal)) %>
	</div>

	<br />
</c:if>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<form action="<portlet:actionURL name="updateMeetupsRegistration" />" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />updateMeetupsRegistration(); return false;">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= currentURL %>" />
		<input name="<portlet:namespace />meetupsEntryId" type="hidden" value="<%= meetupsEntryId %>" />

		<liferay-ui:message key="will-you-attend" />

		<input <%= (status == MeetupsConstants.STATUS_YES) ? "checked" : "" %> name="<portlet:namespace />status" type="radio" value="<%= MeetupsConstants.STATUS_YES %>" /> <liferay-ui:message key="yes" />

		<input <%= (status == MeetupsConstants.STATUS_NO) ? "checked" : "" %> name="<portlet:namespace />status" type="radio" value="<%= MeetupsConstants.STATUS_NO %>" /> <liferay-ui:message key="no" />

		<input <%= (status == MeetupsConstants.STATUS_MAYBE) ? "checked" : "" %> name="<portlet:namespace />status" type="radio" value="<%= MeetupsConstants.STATUS_MAYBE %>" /> <liferay-ui:message key="maybe" />

		<br /><br />

		<liferay-ui:input-field model="<%= MeetupsRegistration.class %>" bean="<%= meetupsRegistration %>" field="comments" />

		<br /><br />

		<input type="submit" value="<liferay-ui:message key="register" />" />

		</form>

		<br />

		<liferay-ui:tabs
			names="attending,not-attending,maybe-attending"
			portletURL="<%= portletURL %>"
		/>

		<%
		int tabs1Status = MeetupsConstants.STATUS_YES;

		if (tabs1.equals("not-attending")) {
			tabs1Status = MeetupsConstants.STATUS_NO;
		}
		else if (tabs1.equals("maybe-attending")) {
			tabs1Status = MeetupsConstants.STATUS_MAYBE;
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 10, portletURL, null, null);

		int total = MeetupsRegistrationLocalServiceUtil.getMeetupsRegistrationsCount(meetupsEntryId, tabs1Status);

		searchContainer.setTotal(total);

		List<MeetupsRegistration> results = MeetupsRegistrationLocalServiceUtil.getMeetupsRegistrations(meetupsEntryId, tabs1Status, searchContainer.getStart(), searchContainer.getEnd());

		for (MeetupsRegistration curMeetupsRegistration : results) {
		%>

			<div class="response">
				<liferay-ui:user-display
					userId="<%= curMeetupsRegistration.getUserId() %>"
					userName="<%= curMeetupsRegistration.getUserName() %>"
					displayStyle="<%= 2 %>"
				/>

				<c:if test="<%= Validator.isNotNull(curMeetupsRegistration.getComments()) %>">
					<div class="comments">
						<%= curMeetupsRegistration.getComments() %>

						<span class="indicator"></span>
					</div>
				</c:if>
			</div>

		<%
		}
		%>

		<div class="taglib-search-iterator-page-iterator-bottom">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</div>

		<aui:script use="aui-base">
			var meetups = A.one('.social-networking-portlet-meetups');

			if (meetups) {
				meetups.delegate(
					'mouseenter',
					function(event) {
						event.currentTarget.addClass('hovering');
					},
					'.response'
				);

				meetups.delegate(
					'mouseleave',
					function(event) {
						event.currentTarget.removeClass('hovering');
					},
					'.response'
				);
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<div>
			You have to be signed in to register for this meetup. Please <a href="<%= themeDisplay.getURLSignIn() %>">sign in</a> or <a href="<%= themeDisplay.getURLCreateAccount() %>">create an account</a> if you do not already have one.
		</div>
	</c:otherwise>
</c:choose>