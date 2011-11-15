create index IX_81FDCE46 on SN_SMILE_MeetupsEntry (companyId);
create index IX_3B112E48 on SN_SMILE_MeetupsEntry (groupId);
create index IX_C2FFBE82 on SN_SMILE_MeetupsEntry (groupId, userId);
create index IX_1E24F2DC on SN_SMILE_MeetupsEntry (userId);

create index IX_7085BAE5 on SN_SMILE_MeetupsRegistration (meetupsEntryId);
create index IX_FDA144CB on SN_SMILE_MeetupsRegistration (meetupsEntryId, status);
create index IX_7D747A1F on SN_SMILE_MeetupsRegistration (userId, meetupsEntryId);