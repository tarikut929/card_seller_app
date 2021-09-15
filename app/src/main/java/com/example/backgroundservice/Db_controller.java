package com.example.backgroundservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Db_controller extends SQLiteOpenHelper {
    Context context;
    public Db_controller(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "CARD.db", factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(ID INTEGER DEFAULT 0 , name TEXT,phoneNumber TEXT PRIMARY KEY,amount TEXT,limitAmount TEXT);");
        db.execSQL("CREATE TABLE cards (ID INTEGER PRIMARY KEY AUTOINCREMENT,cardNo TEXT,cardAmount INTEGER, bought INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE userHistory (ID INTEGER PRIMARY KEY AUTOINCREMENT, phoneNumber TEXT ,cardAmount TEXT, date TEXT,pay INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE callerPhone (phoneNumber TEXT  ,cardAmount TEXT)");
        db.execSQL("CREATE TABLE middleValue (amountCard TEXT NOT NULL  ,bought INTEGER,ring INTEGER,recieved INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(String name, String phoneNumber, String amount, String limit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("amount", amount);
        contentValues.put("limitAmount", limit);
        contentValues.put("phoneNumber", phoneNumber);
        try {
            this.getWritableDatabase().insertOrThrow("users", "", contentValues);
        }catch (Exception e){
        }
    }

    public void deleteUser(String phoneNumber) {
        this.getWritableDatabase().delete("users", "phoneNumber='" + phoneNumber + "'", null);
    }

    public void updateUser(String oldname, String newname) {
        getWritableDatabase().execSQL("UPDATE users SET name='" + newname + "' WHERE name='" + oldname + "'");
    }

    public void updateUserProfile(String oldname, String newname, String oldphone, String newPhone, String amount, String limit) {
        //getWritableDatabase().execSQL("UPDATE users SET name='"+newname+"'WHERE phone='"+oldphone+"'");
        getWritableDatabase().execSQL("UPDATE users SET phoneNumber='" + newPhone + "' WHERE phoneNumber='" + oldphone + "'");

    }

    public void listAllUser(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        textView.setText("");
        while (cursor.moveToNext()) {
            textView.append(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + "\n");
        }
    }

    public void listAllUserName(String[] usersArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        int i = 0;
        while (cursor.moveToNext()) {
            usersArray[i] = cursor.getString(1);
            i++;
        }
    }


    public void listAllUserPhone(String[] phoneArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        int i = 0;
        while (cursor.moveToNext()) {
            phoneArray[i] = cursor.getString(2);
            i++;
        }
    }

    public void listAllUserAmount(String[] phoneArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        int i = 0;
        while (cursor.moveToNext()) {
            phoneArray[i] = cursor.getString(3);
            i++;
        }
    }
    public int sizeOfCardArray(int isRechargedInt,String amountText){
        int size = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM cards WHERE cardAmount='" + amountText + "'", null);
        while (cursor.moveToNext()) {
            size++;
        }
        Log.d("LLL","size found "+size+ " "+amountText);
        return size;
    }

    public void listCards(String[] cardsArray,int isRechargedInt,String amountText){
        amountText = amountText;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM cards  WHERE  cardAmount='" + amountText + "'   AND  bought='" + isRechargedInt + "'" , null);
        int i = 0;
        while (cursor.moveToNext()) {
            cardsArray[i] = cursor.getString(1);
            i++;
        }
    }

    public String cardAmountToRecharge(String callerPhone) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users where phoneNumber='"+callerPhone+"' " , null);
        String amountTorecharge = "";
        while (cursor.moveToNext()) {
            amountTorecharge = cursor.getString(3);
        }
        return amountTorecharge;
    }

    public void listAllUserLimit(String[] phoneArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        int i = 0;
        while (cursor.moveToNext()) {
            phoneArray[i] = cursor.getString(4);
            i++;
        }
    }

    public void listAllUserNoCardSelled(String[] usersArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
        int i = 0;
        while (cursor.moveToNext()) {
            usersArray[i] = cursor.getString(3);
            i++;
        }
    }
    //card
    public void insertCard(String cardNo,int text) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cardNo", cardNo);
        contentValues.put("cardAmount", text);
        this.getWritableDatabase().insertOrThrow("cards", "", contentValues);
    }
    public void saveCallerNumber(String phoneNumber,String cardAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("cardAmount", cardAmount);


        this.getWritableDatabase().insertOrThrow("callerPhone", "", contentValues);

    }


    //caller number
    public String[] showMissedcallNumber() {
        String the_number = "";
        String[] number_and_amount = new String[2];
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM callerPhone", null);
        while (cursor.moveToNext()) {
            number_and_amount[0] = cursor.getString(0);
            number_and_amount[1] = cursor.getString(1);
        }
        this.getWritableDatabase().delete("callerPhone", "phoneNumber='" + the_number + "'", null);
        return number_and_amount;
    }
    //
    public void insertMiddleValueAmount(String amountText){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amountCard", amountText);
        contentValues.put("bought", getMiddleValueBought());

        Log.d("HHH","in db "+amountText);
        this.getWritableDatabase().insertOrThrow("middleValue", "", contentValues);
    }
    public void insertMiddleValueBought(int isRecharged){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amountCard", getMiddleValueAmount());
        contentValues.put("bought", isRecharged);
        this.getWritableDatabase().insertOrThrow("middleValue", "", contentValues);
    }
    public String getMiddleValueAmount(){
        String amountText = "";
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM middleValue", null);
        while (cursor.moveToNext()) {
            amountText = cursor.getString(0);
            Log.d("HHHj","in db "+amountText);

        }
        return amountText;
    }
    public int getMiddleValueBought(){
        int isRecharged = -1;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM middleValue", null);
        while (cursor.moveToNext()) {
            isRecharged = cursor.getInt(1);
            Log.d("Newnew"," "+isRecharged);
        }
        return isRecharged;
    }
    public void removeCard(String cardNo){
        this.getWritableDatabase().delete("cards", "cardNo='" + cardNo + "'", null);
    }



    //user History
    public void insertUserHistory(String cardAmount, String date, String phoneNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("date", date);
        contentValues.put("cardAmount", cardAmount);
        this.getWritableDatabase().insertOrThrow("userHistory", "", contentValues);
    }

    public void listAllUserHistoryDate(String[] dateArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory INNER JOIN users ON userHistory.phoneNumber=users.phoneNumber ", null);
        int i = 0;
        while (cursor.moveToNext()) {
            dateArray[i] = cursor.getString(3);
            i++;
        }
    }
    public void listAllUserHistoryName(String[] nameArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory INNER JOIN users ON userHistory.phoneNumber=users.phoneNumber ", null);
      //  Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM users", null);
       // Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory", null);

        int i = 0;
        while (cursor.moveToNext()) {
            nameArray[i] = cursor.getString(6);
            i++;
        }
    }
    public void listAllUserHistoryPhone(String[] phoneArray) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory INNER JOIN users ON userHistory.phoneNumber=users.phoneNumber ", null);
        int i = 0;
        while (cursor.moveToNext()) {
            phoneArray[i] = cursor.getString(7);
            i++;
        }
    }
    public void listAllUserHistoryAmount() {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory INNER JOIN users ON userHistory.phoneNumber=users.phoneNumber ", null);
        while (cursor.moveToNext()) {
            Log.d("oooo","ooo "+cursor.getString(2));

        }
    }
    public void listUserHistoryID(String phone, int[] userId){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory where phoneNumber='"+phone+"' " , null);
        int i = 0;
        while (cursor.moveToNext()) {
            userId[i] = cursor.getInt(0);
            i++;
        }

    }

    public void listUserHistoryAmount(String phone, String[] amountArray){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory where phoneNumber='"+phone+"' " , null);
        int i = 0;
        while (cursor.moveToNext()) {
            amountArray[i] = cursor.getString(2);
            i++;
        }

    }


    public void listUserHistoryDate(String phone, String[] amountArray){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory where phoneNumber='"+phone+"' " , null);
        int i = 0;
        while (cursor.moveToNext()) {
            amountArray[i] = cursor.getString(3);
            i++;
        }

    }
    public void listUserHistoryPay(String phone, int[] amountArray){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM userHistory where phoneNumber='"+phone+"' " , null);
        int i = 0;
        while (cursor.moveToNext()) {
            amountArray[i] = cursor.getInt(4);
            i++;
        }

    }
    public void setUserHistoryPayed(int pay,int id){
        //ContentValues contentValues = new ContentValues();
        //contentValues.put("pay", 1);
        //this.getWritableDatabase().insertOrThrow("userHistory", "", contentValues);
        this.getWritableDatabase().execSQL("UPDATE userHistory SET pay='"+pay+"' WHERE id="+id+"");
        //this.getWritableDatabase().update("userHistory",contentValues,"id =1 ",)
        Log.d("TARrr","Done");

    }




    public void insertIsRing(int isRing){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amountCard", getMiddleValueAmount());
        contentValues.put("bought", getMiddleValueBought());
        contentValues.put("ring",isRing);
        this.getWritableDatabase().insertOrThrow("middleValue", "", contentValues);
    }
    public int getIsRing(){
       int ring = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM middleValue", null);
        while (cursor.moveToNext()) {
            ring = cursor.getInt(2);
            Log.d("Newnew"," "+ring);
        }
        return ring;
    }
    public void insertIsRecieved(int isRecieved){
        ContentValues contentValues = new ContentValues();
        contentValues.put("amountCard", getMiddleValueAmount());
        contentValues.put("bought", getMiddleValueBought());
        contentValues.put("ring",getIsRing());
        contentValues.put("recieved",isRecieved);
        this.getWritableDatabase().insertOrThrow("middleValue", "", contentValues);
    }
    public int getIsRecieved(){
        int recieved = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM middleValue", null);
        while (cursor.moveToNext()) {
            recieved = cursor.getInt(3);
            Log.d("Newnew"," "+recieved);
        }
        this.getWritableDatabase().delete("middleValue", "ring='" + recieved + "'", null);

        return recieved;
    }

}