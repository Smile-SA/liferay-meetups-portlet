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

package fr.smile.socialnetworking.service.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import fr.smile.socialnetworking.NoSuchMeetupsEntryException;
import fr.smile.socialnetworking.model.MeetupsEntry;
import fr.smile.socialnetworking.model.impl.MeetupsEntryImpl;
import fr.smile.socialnetworking.model.impl.MeetupsEntryModelImpl;

/**
 * The persistence implementation for the meetups entry service.
 * 
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 * 
 * @author Brian Wing Shun Chan
 * @see MeetupsEntryPersistence
 * @see MeetupsEntryUtil
 * @generated
 */
public class MeetupsEntryPersistenceImpl extends BasePersistenceImpl<MeetupsEntry> implements MeetupsEntryPersistence {

    public static final String     FINDER_CLASS_NAME_ENTITY       = MeetupsEntryImpl.class.getName();
    public static final String     FINDER_CLASS_NAME_LIST         = FINDER_CLASS_NAME_ENTITY + ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID  = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          MeetupsEntryImpl.class,
                                                                          FINDER_CLASS_NAME_LIST, "findByCompanyId",
                                                                          new String[] { Long.class.getName(),

                                                                          "java.lang.Integer", "java.lang.Integer",
            "com.liferay.portal.kernel.util.OrderByComparator"           });
    public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          Long.class, FINDER_CLASS_NAME_LIST,
                                                                          "countByCompanyId",
                                                                          new String[] { Long.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_USERID     = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          MeetupsEntryImpl.class,
                                                                          FINDER_CLASS_NAME_LIST, "findByUserId",
                                                                          new String[] { Long.class.getName(),

                                                                          "java.lang.Integer", "java.lang.Integer",
            "com.liferay.portal.kernel.util.OrderByComparator"           });
    public static final FinderPath FINDER_PATH_COUNT_BY_USERID    = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          Long.class, FINDER_CLASS_NAME_LIST,
                                                                          "countByUserId",
                                                                          new String[] { Long.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_GROUPID    = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          MeetupsEntryImpl.class,
                                                                          FINDER_CLASS_NAME_LIST, "findByGroupId",
                                                                          new String[] { Long.class.getName(),

                                                                          "java.lang.Integer", "java.lang.Integer",
            "com.liferay.portal.kernel.util.OrderByComparator"           });
    public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID   = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          Long.class, FINDER_CLASS_NAME_LIST,
                                                                          "countByGroupId",
                                                                          new String[] { Long.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_G_U        = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          MeetupsEntryImpl.class,
                                                                          FINDER_CLASS_NAME_LIST, "findByG_U",
                                                                          new String[] { Long.class.getName(),
            Long.class.getName(),

            "java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator" });
    public static final FinderPath FINDER_PATH_COUNT_BY_G_U       = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          Long.class, FINDER_CLASS_NAME_LIST,
                                                                          "countByG_U", new String[] {
            Long.class.getName(), Long.class.getName()                   });
    public static final FinderPath FINDER_PATH_FIND_ALL           = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          MeetupsEntryImpl.class,
                                                                          FINDER_CLASS_NAME_LIST, "findAll",
                                                                          new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL          = new FinderPath(
                                                                          MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED,
                                                                          MeetupsEntryModelImpl.FINDER_CACHE_ENABLED,
                                                                          Long.class, FINDER_CLASS_NAME_LIST,
                                                                          "countAll", new String[0]);

    /**
     * Caches the meetups entry in the entity cache if it is enabled.
     * 
     * @param meetupsEntry the meetups entry to cache
     */
    @Override
    public void cacheResult(MeetupsEntry meetupsEntry) {
        EntityCacheUtil.putResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                meetupsEntry.getPrimaryKey(), meetupsEntry);

        meetupsEntry.resetOriginalValues();
    }

    /**
     * Caches the meetups entries in the entity cache if it is enabled.
     * 
     * @param meetupsEntries the meetups entries to cache
     */
    @Override
    public void cacheResult(List<MeetupsEntry> meetupsEntries) {
        for (MeetupsEntry meetupsEntry : meetupsEntries) {
            if (EntityCacheUtil.getResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                    meetupsEntry.getPrimaryKey(), this) == null) {
                cacheResult(meetupsEntry);
            }
        }
    }

    /**
     * Clears the cache for all meetups entries.
     * 
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and
     * {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache() {
        if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
            CacheRegistryUtil.clear(MeetupsEntryImpl.class.getName());
        }

        EntityCacheUtil.clearCache(MeetupsEntryImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the meetups entry.
     * 
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and
     * {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache(MeetupsEntry meetupsEntry) {
        EntityCacheUtil.removeResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                meetupsEntry.getPrimaryKey());
    }

    /**
     * Creates a new meetups entry with the primary key. Does not add the meetups entry to the database.
     * 
     * @param meetupsEntryId the primary key for the new meetups entry
     * @return the new meetups entry
     */
    @Override
    public MeetupsEntry create(long meetupsEntryId) {
        MeetupsEntry meetupsEntry = new MeetupsEntryImpl();

        meetupsEntry.setNew(true);
        meetupsEntry.setPrimaryKey(meetupsEntryId);

        return meetupsEntry;
    }

    /**
     * Removes the meetups entry with the primary key from the database. Also notifies the appropriate model listeners.
     * 
     * @param primaryKey the primary key of the meetups entry to remove
     * @return the meetups entry that was removed
     * @throws com.liferay.portal.NoSuchModelException if a meetups entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry remove(Serializable primaryKey) throws NoSuchModelException, SystemException {
        return remove(((Long) primaryKey).longValue());
    }

    /**
     * Removes the meetups entry with the primary key from the database. Also notifies the appropriate model listeners.
     * 
     * @param meetupsEntryId the primary key of the meetups entry to remove
     * @return the meetups entry that was removed
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry remove(long meetupsEntryId) throws NoSuchMeetupsEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            MeetupsEntry meetupsEntry = (MeetupsEntry) session
                    .get(MeetupsEntryImpl.class, Long.valueOf(meetupsEntryId));

            if (meetupsEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + meetupsEntryId);
                }

                throw new NoSuchMeetupsEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + meetupsEntryId);
            }

            return meetupsEntryPersistence.remove(meetupsEntry);
        } catch (NoSuchMeetupsEntryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    /**
     * Removes the meetups entry from the database. Also notifies the appropriate model listeners.
     * 
     * @param meetupsEntry the meetups entry to remove
     * @return the meetups entry that was removed
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry remove(MeetupsEntry meetupsEntry) throws SystemException {
        return super.remove(meetupsEntry);
    }

    @Override
    protected MeetupsEntry removeImpl(MeetupsEntry meetupsEntry) throws SystemException {
        meetupsEntry = toUnwrappedModel(meetupsEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, meetupsEntry);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                meetupsEntry.getPrimaryKey());

        return meetupsEntry;
    }

    @Override
    public MeetupsEntry updateImpl(fr.smile.socialnetworking.model.MeetupsEntry meetupsEntry, boolean merge)
            throws SystemException {
        meetupsEntry = toUnwrappedModel(meetupsEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, meetupsEntry, merge);

            meetupsEntry.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                meetupsEntry.getPrimaryKey(), meetupsEntry);

        return meetupsEntry;
    }

    protected MeetupsEntry toUnwrappedModel(MeetupsEntry meetupsEntry) {
        if (meetupsEntry instanceof MeetupsEntryImpl) {
            return meetupsEntry;
        }

        MeetupsEntryImpl meetupsEntryImpl = new MeetupsEntryImpl();

        meetupsEntryImpl.setNew(meetupsEntry.isNew());
        meetupsEntryImpl.setPrimaryKey(meetupsEntry.getPrimaryKey());

        meetupsEntryImpl.setMeetupsEntryId(meetupsEntry.getMeetupsEntryId());
        meetupsEntryImpl.setGroupId(meetupsEntry.getGroupId());
        meetupsEntryImpl.setCompanyId(meetupsEntry.getCompanyId());
        meetupsEntryImpl.setUserId(meetupsEntry.getUserId());
        meetupsEntryImpl.setUserName(meetupsEntry.getUserName());
        meetupsEntryImpl.setCreateDate(meetupsEntry.getCreateDate());
        meetupsEntryImpl.setModifiedDate(meetupsEntry.getModifiedDate());
        meetupsEntryImpl.setTitle(meetupsEntry.getTitle());
        meetupsEntryImpl.setDescription(meetupsEntry.getDescription());
        meetupsEntryImpl.setStartDate(meetupsEntry.getStartDate());
        meetupsEntryImpl.setEndDate(meetupsEntry.getEndDate());
        meetupsEntryImpl.setTotalAttendees(meetupsEntry.getTotalAttendees());
        meetupsEntryImpl.setMaxAttendees(meetupsEntry.getMaxAttendees());
        meetupsEntryImpl.setPrice(meetupsEntry.getPrice());
        meetupsEntryImpl.setThumbnailId(meetupsEntry.getThumbnailId());

        return meetupsEntryImpl;
    }

    /**
     * Finds the meetups entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it
     * could not be found.
     * 
     * @param primaryKey the primary key of the meetups entry to find
     * @return the meetups entry
     * @throws com.liferay.portal.NoSuchModelException if a meetups entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByPrimaryKey(Serializable primaryKey) throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Finds the meetups entry with the primary key or throws a
     * {@link fr.smile.socialnetworking.NoSuchMeetupsEntryException} if it could not be found.
     * 
     * @param meetupsEntryId the primary key of the meetups entry to find
     * @return the meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByPrimaryKey(long meetupsEntryId) throws NoSuchMeetupsEntryException, SystemException {
        MeetupsEntry meetupsEntry = fetchByPrimaryKey(meetupsEntryId);

        if (meetupsEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + meetupsEntryId);
            }

            throw new NoSuchMeetupsEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + meetupsEntryId);
        }

        return meetupsEntry;
    }

    /**
     * Finds the meetups entry with the primary key or returns <code>null</code> if it could not be found.
     * 
     * @param primaryKey the primary key of the meetups entry to find
     * @return the meetups entry, or <code>null</code> if a meetups entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry fetchByPrimaryKey(Serializable primaryKey) throws SystemException {
        return fetchByPrimaryKey(((Long) primaryKey).longValue());
    }

    /**
     * Finds the meetups entry with the primary key or returns <code>null</code> if it could not be found.
     * 
     * @param meetupsEntryId the primary key of the meetups entry to find
     * @return the meetups entry, or <code>null</code> if a meetups entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry fetchByPrimaryKey(long meetupsEntryId) throws SystemException {
        MeetupsEntry meetupsEntry = (MeetupsEntry) EntityCacheUtil.getResult(
                MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class, meetupsEntryId, this);

        if (meetupsEntry == _nullMeetupsEntry) {
            return null;
        }

        if (meetupsEntry == null) {
            Session session = null;

            boolean hasException = false;

            try {
                session = openSession();

                meetupsEntry = (MeetupsEntry) session.get(MeetupsEntryImpl.class, Long.valueOf(meetupsEntryId));
            } catch (Exception e) {
                hasException = true;

                throw processException(e);
            } finally {
                if (meetupsEntry != null) {
                    cacheResult(meetupsEntry);
                }
                else
                    if (!hasException) {
                        EntityCacheUtil.putResult(MeetupsEntryModelImpl.ENTITY_CACHE_ENABLED, MeetupsEntryImpl.class,
                                meetupsEntryId, _nullMeetupsEntry);
                    }

                closeSession(session);
            }
        }

        return meetupsEntry;
    }

    /**
     * Finds all the meetups entries where companyId = &#63;.
     * 
     * @param companyId the company ID to search with
     * @return the matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByCompanyId(long companyId) throws SystemException {
        return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the meetups entries where companyId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param companyId the company ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @return the range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByCompanyId(long companyId, int start, int end) throws SystemException {
        return findByCompanyId(companyId, start, end, null);
    }

    /**
     * Finds an ordered range of all the meetups entries where companyId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param companyId the company ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByCompanyId(long companyId, int start, int end, OrderByComparator orderByComparator)
            throws SystemException {
        Object[] finderArgs = new Object[] { companyId,

        String.valueOf(start), String.valueOf(end), String.valueOf(orderByComparator) };

        List<MeetupsEntry> list = (List<MeetupsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 + (orderByComparator.getOrderByFields().length * 3));
            }
            else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
            }

            else {
                query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(companyId);

                list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID, finderArgs);
                }
                else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first meetups entry in the ordered set where companyId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param companyId the company ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByCompanyId_First(long companyId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        List<MeetupsEntry> list = findByCompanyId(companyId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("companyId=");
            msg.append(companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the last meetups entry in the ordered set where companyId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param companyId the company ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByCompanyId_Last(long companyId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        int count = countByCompanyId(companyId);

        List<MeetupsEntry> list = findByCompanyId(companyId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("companyId=");
            msg.append(companyId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the meetups entries before and after the current meetups entry in the ordered set where companyId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param meetupsEntryId the primary key of the current meetups entry
     * @param companyId the company ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry[] findByCompanyId_PrevAndNext(long meetupsEntryId, long companyId,
            OrderByComparator orderByComparator) throws NoSuchMeetupsEntryException, SystemException {
        MeetupsEntry meetupsEntry = findByPrimaryKey(meetupsEntryId);

        Session session = null;

        try {
            session = openSession();

            MeetupsEntry[] array = new MeetupsEntryImpl[3];

            array[0] = getByCompanyId_PrevAndNext(session, meetupsEntry, companyId, orderByComparator, true);

            array[1] = meetupsEntry;

            array[2] = getByCompanyId_PrevAndNext(session, meetupsEntry, companyId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected MeetupsEntry getByCompanyId_PrevAndNext(Session session, MeetupsEntry meetupsEntry, long companyId,
            OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
        }
        else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

        query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

        if (orderByComparator != null) {
            String[] orderByFields = orderByComparator.getOrderByFields();

            if (orderByFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    }
                    else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    }
                    else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        else {
            query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(companyId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(meetupsEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<MeetupsEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        }
        else {
            return null;
        }
    }

    /**
     * Finds all the meetups entries where userId = &#63;.
     * 
     * @param userId the user ID to search with
     * @return the matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByUserId(long userId) throws SystemException {
        return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the meetups entries where userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param userId the user ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @return the range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByUserId(long userId, int start, int end) throws SystemException {
        return findByUserId(userId, start, end, null);
    }

    /**
     * Finds an ordered range of all the meetups entries where userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param userId the user ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByUserId(long userId, int start, int end, OrderByComparator orderByComparator)
            throws SystemException {
        Object[] finderArgs = new Object[] { userId,

        String.valueOf(start), String.valueOf(end), String.valueOf(orderByComparator) };

        List<MeetupsEntry> list = (List<MeetupsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 + (orderByComparator.getOrderByFields().length * 3));
            }
            else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_USERID_USERID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
            }

            else {
                query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(userId);

                list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERID, finderArgs);
                }
                else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first meetups entry in the ordered set where userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByUserId_First(long userId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        List<MeetupsEntry> list = findByUserId(userId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the last meetups entry in the ordered set where userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByUserId_Last(long userId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        int count = countByUserId(userId);

        List<MeetupsEntry> list = findByUserId(userId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the meetups entries before and after the current meetups entry in the ordered set where userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param meetupsEntryId the primary key of the current meetups entry
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry[] findByUserId_PrevAndNext(long meetupsEntryId, long userId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        MeetupsEntry meetupsEntry = findByPrimaryKey(meetupsEntryId);

        Session session = null;

        try {
            session = openSession();

            MeetupsEntry[] array = new MeetupsEntryImpl[3];

            array[0] = getByUserId_PrevAndNext(session, meetupsEntry, userId, orderByComparator, true);

            array[1] = meetupsEntry;

            array[2] = getByUserId_PrevAndNext(session, meetupsEntry, userId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected MeetupsEntry getByUserId_PrevAndNext(Session session, MeetupsEntry meetupsEntry, long userId,
            OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
        }
        else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

        query.append(_FINDER_COLUMN_USERID_USERID_2);

        if (orderByComparator != null) {
            String[] orderByFields = orderByComparator.getOrderByFields();

            if (orderByFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    }
                    else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    }
                    else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        else {
            query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(userId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(meetupsEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<MeetupsEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        }
        else {
            return null;
        }
    }

    /**
     * Finds all the meetups entries where groupId = &#63;.
     * 
     * @param groupId the group ID to search with
     * @return the matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByGroupId(long groupId) throws SystemException {
        return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the meetups entries where groupId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @return the range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByGroupId(long groupId, int start, int end) throws SystemException {
        return findByGroupId(groupId, start, end, null);
    }

    /**
     * Finds an ordered range of all the meetups entries where groupId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByGroupId(long groupId, int start, int end, OrderByComparator orderByComparator)
            throws SystemException {
        Object[] finderArgs = new Object[] { groupId,

        String.valueOf(start), String.valueOf(end), String.valueOf(orderByComparator) };

        List<MeetupsEntry> list = (List<MeetupsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 + (orderByComparator.getOrderByFields().length * 3));
            }
            else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
            }

            else {
                query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(groupId);

                list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID, finderArgs);
                }
                else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first meetups entry in the ordered set where groupId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByGroupId_First(long groupId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        List<MeetupsEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("groupId=");
            msg.append(groupId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the last meetups entry in the ordered set where groupId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByGroupId_Last(long groupId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        int count = countByGroupId(groupId);

        List<MeetupsEntry> list = findByGroupId(groupId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("groupId=");
            msg.append(groupId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the meetups entries before and after the current meetups entry in the ordered set where groupId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param meetupsEntryId the primary key of the current meetups entry
     * @param groupId the group ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry[] findByGroupId_PrevAndNext(long meetupsEntryId, long groupId,
            OrderByComparator orderByComparator) throws NoSuchMeetupsEntryException, SystemException {
        MeetupsEntry meetupsEntry = findByPrimaryKey(meetupsEntryId);

        Session session = null;

        try {
            session = openSession();

            MeetupsEntry[] array = new MeetupsEntryImpl[3];

            array[0] = getByGroupId_PrevAndNext(session, meetupsEntry, groupId, orderByComparator, true);

            array[1] = meetupsEntry;

            array[2] = getByGroupId_PrevAndNext(session, meetupsEntry, groupId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected MeetupsEntry getByGroupId_PrevAndNext(Session session, MeetupsEntry meetupsEntry, long groupId,
            OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
        }
        else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

        query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

        if (orderByComparator != null) {
            String[] orderByFields = orderByComparator.getOrderByFields();

            if (orderByFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    }
                    else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    }
                    else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        else {
            query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(groupId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(meetupsEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<MeetupsEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        }
        else {
            return null;
        }
    }

    /**
     * Finds all the meetups entries where groupId = &#63; and userId = &#63;.
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @return the matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByG_U(long groupId, long userId) throws SystemException {
        return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the meetups entries where groupId = &#63; and userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @return the range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByG_U(long groupId, long userId, int start, int end) throws SystemException {
        return findByG_U(groupId, userId, start, end, null);
    }

    /**
     * Finds an ordered range of all the meetups entries where groupId = &#63; and userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findByG_U(long groupId, long userId, int start, int end,
            OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] { groupId, userId,

        String.valueOf(start), String.valueOf(end), String.valueOf(orderByComparator) };

        List<MeetupsEntry> list = (List<MeetupsEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
                this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(4 + (orderByComparator.getOrderByFields().length * 3));
            }
            else {
                query = new StringBundler(4);
            }

            query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_G_U_GROUPID_2);

            query.append(_FINDER_COLUMN_G_U_USERID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
            }

            else {
                query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(groupId);

                qPos.add(userId);

                list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_U, finderArgs);
                }
                else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first meetups entry in the ordered set where groupId = &#63; and userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByG_U_First(long groupId, long userId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        List<MeetupsEntry> list = findByG_U(groupId, userId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("groupId=");
            msg.append(groupId);

            msg.append(", userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the last meetups entry in the ordered set where groupId = &#63; and userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a matching meetups entry could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry findByG_U_Last(long groupId, long userId, OrderByComparator orderByComparator)
            throws NoSuchMeetupsEntryException, SystemException {
        int count = countByG_U(groupId, userId);

        List<MeetupsEntry> list = findByG_U(groupId, userId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("groupId=");
            msg.append(groupId);

            msg.append(", userId=");
            msg.append(userId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchMeetupsEntryException(msg.toString());
        }
        else {
            return list.get(0);
        }
    }

    /**
     * Finds the meetups entries before and after the current meetups entry in the ordered set where groupId = &#63; and
     * userId = &#63;.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param meetupsEntryId the primary key of the current meetups entry
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next meetups entry
     * @throws fr.smile.socialnetworking.NoSuchMeetupsEntryException if a meetups entry with the primary key could not
     *         be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public MeetupsEntry[] findByG_U_PrevAndNext(long meetupsEntryId, long groupId, long userId,
            OrderByComparator orderByComparator) throws NoSuchMeetupsEntryException, SystemException {
        MeetupsEntry meetupsEntry = findByPrimaryKey(meetupsEntryId);

        Session session = null;

        try {
            session = openSession();

            MeetupsEntry[] array = new MeetupsEntryImpl[3];

            array[0] = getByG_U_PrevAndNext(session, meetupsEntry, groupId, userId, orderByComparator, true);

            array[1] = meetupsEntry;

            array[2] = getByG_U_PrevAndNext(session, meetupsEntry, groupId, userId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected MeetupsEntry getByG_U_PrevAndNext(Session session, MeetupsEntry meetupsEntry, long groupId, long userId,
            OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
        }
        else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_MEETUPSENTRY_WHERE);

        query.append(_FINDER_COLUMN_G_U_GROUPID_2);

        query.append(_FINDER_COLUMN_G_U_USERID_2);

        if (orderByComparator != null) {
            String[] orderByFields = orderByComparator.getOrderByFields();

            if (orderByFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    }
                    else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    }
                    else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                }
                else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    }
                    else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        else {
            query.append(MeetupsEntryModelImpl.ORDER_BY_JPQL);
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(groupId);

        qPos.add(userId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(meetupsEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<MeetupsEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        }
        else {
            return null;
        }
    }

    /**
     * Finds all the meetups entries.
     * 
     * @return the meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the meetups entries.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @return the range of meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the meetups entries.
     * 
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and
     * <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the
     * first result in the set. Setting both <code>start</code> and <code>end</code> to
     * {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     * 
     * @param start the lower bound of the range of meetups entries to return
     * @param end the upper bound of the range of meetups entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<MeetupsEntry> findAll(int start, int end, OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] { String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator) };

        List<MeetupsEntry> list = (List<MeetupsEntry>) FinderCacheUtil
                .getResult(FINDER_PATH_FIND_ALL, finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 + (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_MEETUPSENTRY);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

                sql = query.toString();
            }
            else {
                sql = _SQL_SELECT_MEETUPSENTRY.concat(MeetupsEntryModelImpl.ORDER_BY_JPQL);
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end, false);

                    Collections.sort(list);
                }
                else {
                    list = (List<MeetupsEntry>) QueryUtil.list(q, getDialect(), start, end);
                }
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, finderArgs);
                }
                else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Removes all the meetups entries where companyId = &#63; from the database.
     * 
     * @param companyId the company ID to search with
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeByCompanyId(long companyId) throws SystemException {
        for (MeetupsEntry meetupsEntry : findByCompanyId(companyId)) {
            meetupsEntryPersistence.remove(meetupsEntry);
        }
    }

    /**
     * Removes all the meetups entries where userId = &#63; from the database.
     * 
     * @param userId the user ID to search with
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeByUserId(long userId) throws SystemException {
        for (MeetupsEntry meetupsEntry : findByUserId(userId)) {
            meetupsEntryPersistence.remove(meetupsEntry);
        }
    }

    /**
     * Removes all the meetups entries where groupId = &#63; from the database.
     * 
     * @param groupId the group ID to search with
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeByGroupId(long groupId) throws SystemException {
        for (MeetupsEntry meetupsEntry : findByGroupId(groupId)) {
            meetupsEntryPersistence.remove(meetupsEntry);
        }
    }

    /**
     * Removes all the meetups entries where groupId = &#63; and userId = &#63; from the database.
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeByG_U(long groupId, long userId) throws SystemException {
        for (MeetupsEntry meetupsEntry : findByG_U(groupId, userId)) {
            meetupsEntryPersistence.remove(meetupsEntry);
        }
    }

    /**
     * Removes all the meetups entries from the database.
     * 
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeAll() throws SystemException {
        for (MeetupsEntry meetupsEntry : findAll()) {
            meetupsEntryPersistence.remove(meetupsEntry);
        }
    }

    /**
     * Counts all the meetups entries where companyId = &#63;.
     * 
     * @param companyId the company ID to search with
     * @return the number of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countByCompanyId(long companyId) throws SystemException {
        Object[] finderArgs = new Object[] { companyId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID, finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(companyId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID, finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the meetups entries where userId = &#63;.
     * 
     * @param userId the user ID to search with
     * @return the number of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countByUserId(long userId) throws SystemException {
        Object[] finderArgs = new Object[] { userId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID, finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_USERID_USERID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(userId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID, finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the meetups entries where groupId = &#63;.
     * 
     * @param groupId the group ID to search with
     * @return the number of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countByGroupId(long groupId) throws SystemException {
        Object[] finderArgs = new Object[] { groupId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID, finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(groupId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID, finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the meetups entries where groupId = &#63; and userId = &#63;.
     * 
     * @param groupId the group ID to search with
     * @param userId the user ID to search with
     * @return the number of matching meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countByG_U(long groupId, long userId) throws SystemException {
        Object[] finderArgs = new Object[] { groupId, userId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U, finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(3);

            query.append(_SQL_COUNT_MEETUPSENTRY_WHERE);

            query.append(_FINDER_COLUMN_G_U_GROUPID_2);

            query.append(_FINDER_COLUMN_G_U_USERID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(groupId);

                qPos.add(userId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the meetups entries.
     * 
     * @return the number of meetups entries
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countAll() throws SystemException {
        Object[] finderArgs = new Object[0];

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL, finderArgs, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_MEETUPSENTRY);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Initializes the meetups entry persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(com.liferay.util.service.ServiceProps
                .get("value.object.listener.fr.smile.socialnetworking.model.MeetupsEntry")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<MeetupsEntry>> listenersList = new ArrayList<ModelListener<MeetupsEntry>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<MeetupsEntry>) InstanceFactory.newInstance(listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(MeetupsEntryImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }

    @BeanReference(type = MeetupsEntryPersistence.class)
    protected MeetupsEntryPersistence        meetupsEntryPersistence;
    @BeanReference(type = MeetupsRegistrationPersistence.class)
    protected MeetupsRegistrationPersistence meetupsRegistrationPersistence;
    @BeanReference(type = ResourcePersistence.class)
    protected ResourcePersistence            resourcePersistence;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence                userPersistence;
    private static final String              _SQL_SELECT_MEETUPSENTRY                = "SELECT meetupsEntry FROM MeetupsEntry meetupsEntry";
    private static final String              _SQL_SELECT_MEETUPSENTRY_WHERE          = "SELECT meetupsEntry FROM MeetupsEntry meetupsEntry WHERE ";
    private static final String              _SQL_COUNT_MEETUPSENTRY                 = "SELECT COUNT(meetupsEntry) FROM MeetupsEntry meetupsEntry";
    private static final String              _SQL_COUNT_MEETUPSENTRY_WHERE           = "SELECT COUNT(meetupsEntry) FROM MeetupsEntry meetupsEntry WHERE ";
    private static final String              _FINDER_COLUMN_COMPANYID_COMPANYID_2    = "meetupsEntry.companyId = ?";
    private static final String              _FINDER_COLUMN_USERID_USERID_2          = "meetupsEntry.userId = ?";
    private static final String              _FINDER_COLUMN_GROUPID_GROUPID_2        = "meetupsEntry.groupId = ?";
    private static final String              _FINDER_COLUMN_G_U_GROUPID_2            = "meetupsEntry.groupId = ? AND ";
    private static final String              _FINDER_COLUMN_G_U_USERID_2             = "meetupsEntry.userId = ?";
    private static final String              _ORDER_BY_ENTITY_ALIAS                  = "meetupsEntry.";
    private static final String              _NO_SUCH_ENTITY_WITH_PRIMARY_KEY        = "No MeetupsEntry exists with the primary key ";
    private static final String              _NO_SUCH_ENTITY_WITH_KEY                = "No MeetupsEntry exists with the key {";
    private static final boolean             _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil
                                                                                             .getBoolean(PropsUtil
                                                                                                     .get(PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
    private static Log                       _log                                    = LogFactoryUtil
                                                                                             .getLog(MeetupsEntryPersistenceImpl.class);
    private static MeetupsEntry              _nullMeetupsEntry                       = new MeetupsEntryImpl() {

                                                                                         @Override
                                                                                         public Object clone() {
                                                                                             return this;
                                                                                         }

                                                                                         @Override
                                                                                         public CacheModel<MeetupsEntry> toCacheModel() {
                                                                                             return _nullMeetupsEntryCacheModel;
                                                                                         }
                                                                                     };

    private static CacheModel<MeetupsEntry>  _nullMeetupsEntryCacheModel             = new CacheModel<MeetupsEntry>() {

                                                                                         @Override
                                                                                         public MeetupsEntry toEntityModel() {
                                                                                             return _nullMeetupsEntry;
                                                                                         }
                                                                                     };
}