package com.hugobrisson.teammatetest.common.model;

import com.hugobrisson.teammatetest.common.listener.DaoInterface;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public class DaoCommon<T extends BaseModel> implements DaoInterface<T> {

    private static final String TAG = ServiceCommon.class.getSimpleName();

    private Class<T> mClass;

    public DaoCommon(Class<T> sClass) {
        mClass = sClass;
    }

    @Override
    public List<T> get() {
        return new Select().from(mClass).queryList();
    }

    @Override
    public List<T> getByValue(String value, NameAlias key) {
        return new Select().from(mClass).where(Condition.column(key).is(value)).queryList();
    }

}

