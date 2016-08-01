package com.hugobrisson.teammatetest.common.listener;

import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public interface DaoInterface<T extends BaseModel> {

    List<T> get();

    List<T> getByValue(String value, NameAlias key);

}
