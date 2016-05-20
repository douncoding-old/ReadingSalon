package com.douncoding.readingsalon.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.douncoding.readingsalon.dao.Contents;
import com.douncoding.readingsalon.dao.Member;

import com.douncoding.readingsalon.dao.ContentsDao;
import com.douncoding.readingsalon.dao.MemberDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contentsDaoConfig;
    private final DaoConfig memberDaoConfig;

    private final ContentsDao contentsDao;
    private final MemberDao memberDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contentsDaoConfig = daoConfigMap.get(ContentsDao.class).clone();
        contentsDaoConfig.initIdentityScope(type);

        memberDaoConfig = daoConfigMap.get(MemberDao.class).clone();
        memberDaoConfig.initIdentityScope(type);

        contentsDao = new ContentsDao(contentsDaoConfig, this);
        memberDao = new MemberDao(memberDaoConfig, this);

        registerDao(Contents.class, contentsDao);
        registerDao(Member.class, memberDao);
    }
    
    public void clear() {
        contentsDaoConfig.getIdentityScope().clear();
        memberDaoConfig.getIdentityScope().clear();
    }

    public ContentsDao getContentsDao() {
        return contentsDao;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

}