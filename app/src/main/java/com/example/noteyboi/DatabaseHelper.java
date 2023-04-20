//package com.example.noteyboi;
//
//import android.content.Context;
//import android.database.Cursor;
//
//
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.RealmObject;
//import io.realm.RealmResults;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;
//
//
//
//public class DatabaseHelper extends RealmObject {
//    private static final String TABLE_NAME = "SimpleNotesDB";
//    private static final int DATABASE_VERSION = 2;
//
//
//    private Realm realm;
//
//    public DatabaseHelper(Context context){
//        super();
//        Realm.init(context);
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                .name(TABLE_NAME + ".realm")
//                .schemaVersion(DATABASE_VERSION)
//                .build();
//        Realm.setDefaultConfiguration(realmConfig);
//    }
//
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        realm.close();
////    }
//
//
//    public boolean addData(String Name, String Note){
//
////        Number id = realm.where(DataModal.class).max("id");
//
//        // on below line we are
//        // creating a variable for our id.
//        long nextId;
//
//        // validating if id is null or not.
////        if (id == null) {
////            // if id is null
////            // we are passing it as 1.
////            nextId = 1;
////        } else {
////            // if id is not null then
////            // we are incrementing it by 1
////            nextId = id.intValue() + 1;
////        }
//
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                DatabaseObject Obj = new DatabaseObject();
//                Obj.setId(1);
//                Obj.setName(Name);
//                Obj.setNote(Note);
//                Obj.setModifiedDate(System.currentTimeMillis());
//                Obj.setCreatedDate(System.currentTimeMillis());
//                realm.copyToRealm(Obj);
//            }
//        });
//
//        //negative one if not inserted correctly
////        if (true){
//////            if (result == -1){
////            return false;
////        }
////        else{
//            return true;
////        }
//    }
//
//    public Cursor getData(){
////        SQLiteDatabase db = this.getWritableDatabase();
////        String query = "SELECT * FROM " + TABLE_NAME;
////        Cursor = db.rawQuery(query, null);
////        return data;
//        RealmResults<DatabaseHelper> data = realm.where(DatabaseHelper.class).findAll();
//        data = data.sort("modifiedDate");
//        return (Cursor) data;
//    }
//
//    public void UpdateRow(String Name, String Note, int Id){
////        String nID = Integer.toString(Id);
//
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                DatabaseObject Obj = realm.where(DatabaseObject.class).equalTo("id", Id).findFirst();
//                Obj.setName(Name);
//                Obj.setNote(Note);
//                Obj.setModifiedDate(System.currentTimeMillis());
//            }
//        });
//
//    }
//
//    public void Delete(int Id){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.where(DatabaseObject.class).equalTo("id", Id)
//                        .findFirst()
//                        .deleteFromRealm();
//            }
//        });
//    }
//};