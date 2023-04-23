package com.example.noteyboi;

//import io.realm.Realm;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;

import java.util.ArrayList;

//public class DatabaseObject extends RealmObject {
public class DatabaseObject {
    //    @Required
//    @PrimaryKey
    private int Id;
    //    @Required
    private String Name;
    private String Note;
    private long createdDate;
    private long modifiedDate;
    private String Folder;
    private ArrayList<String> Tags;

    public DatabaseObject(int id, String name, String note, long createdDate, long modifiedDate) {
        Id = id;
        Name = name;
        Note = note;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
////Getters and Setters/////////////////////////////////////////////////////////////////////////////
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getFolder() {
        return Folder;
    }

    public void setFolder(String folder) {
        Folder = folder;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }
}
