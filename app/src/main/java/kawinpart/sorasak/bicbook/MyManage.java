package kawinpart.sorasak.bicbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sssssssss on 30/3/2559.
 */
public class MyManage {

    // Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_id_card = "ID_Card";
    public static final String column_password = "Password";

    public MyManage(Context context) {

        // Connected Database
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    } // Constructor

    public long addUser(String strIDcard,
                        String strPassword) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_id_card, strIDcard);
        contentValues.put(column_password, strPassword);

        return sqLiteDatabase.insert(user_table, null, contentValues);
    }


} // Main Class
