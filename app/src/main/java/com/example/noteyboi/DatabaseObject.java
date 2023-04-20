//package com.example.noteyboi;
//
//import io.realm.Realm;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;
//
//public class DatabaseObject extends RealmObject {
//    @Required
//    @PrimaryKey
//    private int id;
//    @Required
//    private String name;
//    private String note;
//    private long createdDate;
//    private long modifiedDate;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
//
//    public void setCreatedDate(long createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public void setModifiedDate(long modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public long getCreatedDate() {
//        return createdDate;
//    }
//
//    public long getModifiedDate() {
//        return modifiedDate;
//    }
//}
