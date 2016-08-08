package com.hugobrisson.teammatetest.model.user;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.hugobrisson.teammatetest.database.TMDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@IgnoreExtraProperties
@Table(database = TMDatabase.class)
@org.parceler.Parcel(analyze = {User.class})
public class User extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIRTHDATE = "birthDate";
    public static final String COLUMN_PROFILE_PHOTO = "profilePhoto";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TUTORIALFINISHED = "tutorialFinished";
    public static final String COLUMN_LAST_UPDATED = "lastUpdated";

    @PrimaryKey
    @Column(name = COLUMN_ID)
    public String id;

    @Column(name = COLUMN_MAIL)
    public String mail;

    @Column(name = COLUMN_PASSWORD)
    public String password;

    @Column(name = COLUMN_NAME)
    public String name;

    @Column(name = COLUMN_GENDER)
    public Gender gender;

    @Column(name = COLUMN_BIRTHDATE)
    public long birthDate;

    @Column(name = COLUMN_PROFILE_PHOTO)
    public String profilePhoto;

    @Column(name = COLUMN_DESCRIPTION)
    public String description;

    @Column(name = COLUMN_TUTORIALFINISHED)
    public boolean tutorialFinished = false;

    @Column(name = COLUMN_LAST_UPDATED)
    public long lastUpdated;


    public User() {
    }

    public User(String id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    /*
    private User(Parcel in) {
        id = in.readString();
        mail = in.readString();
        password = in.readString();
        name = in.readString();
        gender = (Gender) in.readSerializable();
        birthDate = (Date) in.readSerializable();
        profilePhoto = in.readString();
        description = in.readString();
        tutorialFinished = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mail);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeSerializable(gender);
        dest.writeSerializable(birthDate);
        dest.writeString(profilePhoto);
        dest.writeString(description);
        dest.writeByte((byte) (tutorialFinished ? 1 : 0));
    }
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Gender getGenderAsEnum() {
        return gender;
    }

    public void setGenderAsEnum(Gender gender) {
        this.gender = gender;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getTutorialFinished() {
        return tutorialFinished;
    }

    public void setTutorialFinished(boolean tutorialFinished) {
        this.tutorialFinished = tutorialFinished;
    }

    public String getGender() {
        if (gender == null) {
            return null;
        } else {
            return gender.name();
        }
    }

    public void setGender(String lifecycleString) {
        if (lifecycleString == null) {
            gender = null;
        } else {
            this.gender = Gender.valueOf(lifecycleString);
        }
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


/*
    public List<String> getInfo() {
        return new ArrayList<>(Arrays.asList(getDescription(), TMDateConverterUtils.getDate(getBirthDate().getTime()), ""));
    }*/
}

