package com.hugobrisson.teammatetest.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TMDatabase.NAME, version = TMDatabase.VERSION, foreignKeysSupported = true)
public class TMDatabase {
    public static final String NAME = "App";

    public static final int VERSION = 1;
}

