package com.hugobrisson.teammatetest.model.sport;

import com.google.firebase.database.IgnoreExtraProperties;
import com.hugobrisson.teammatetest.database.TMDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@IgnoreExtraProperties
@Table(database = TMDatabase.class)
@org.parceler.Parcel(analyze = {Sport.class})
public class Sport extends BaseModel {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_LAST_UPDATED = "lastUpdated";

    @PrimaryKey
    @Column(name = COLUMN_ID)
    public String id;

    @Column(name = COLUMN_NAME)
    public String name;

    @Column(name = COLUMN_ICON)
    public String icon;

    @Column(name = COLUMN_LAST_UPDATED)
    public long lastUpdated;

    public Sport() {
    }

    public Sport(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

