package com.hugobrisson.teammatetest.model.user.dao;

import android.content.Context;

import com.google.firebase.database.Query;
import com.hugobrisson.teammatetest.common.manager.PrefManager;
import com.hugobrisson.teammatetest.common.utils.TMConstantKey;
import com.hugobrisson.teammatetest.model.user.User;
import com.hugobrisson.teammatetest.model.user.User_Table;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

public class UserDao {

    private static Context mContext;
    private static UserDao mInstance;

    public static UserDao getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new UserDao();
        }
        return mInstance;
    }

    public User getCurrentUser() {
        String mail = PrefManager.getInstance(mContext).getString(TMConstantKey.PREF_MAIL, null);
        User user = null;
        if (mail != null) {
            user = new Select().from(User.class).where(Condition.column(User_Table.mail.getNameAlias()).is(mail)).querySingle();
        }
        return user;
    }

}
