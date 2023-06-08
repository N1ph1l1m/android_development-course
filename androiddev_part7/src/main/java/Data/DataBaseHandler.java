package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Model.Car;
import Utils.Utils;

public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String CREATE_CARS_TABLE = "CREATE TABLE " + Utils.TABLE_NAME +
                 "("
                     + Utils.KEY_ID + "INTEGER PRIMARY KEY,"
                     + Utils.KEY_NAME + "TEXT,"
                     + Utils.KEY_PRICE + "TEXT" +
                 ")";

         db.execSQL(CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);
    }

    public void addCar(Car car ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, car.getName());
        contentValues.put(Utils.KEY_PRICE, car.getPrice());


        db.insert(Utils.TABLE_NAME,null,contentValues);
        db.close();
    }

    public Car getCar(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Utils.TABLE_NAME, new String[]{Utils.KEY_ID,Utils.KEY_NAME,Utils.KEY_PRICE}, Utils.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();

        }

        Car car = new Car(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));

        return car;
//        db.close();
    }
}
