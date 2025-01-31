package iesmm.pmdm.pmdm_t5_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAOImpl extends SQLiteOpenHelper implements DAO {
    public static final String DATABASE_NAME = "alumnos.db"; // /data/data/iesmm.pmdm.pmdm_t5_sqlite/databases/alumnos.db
    private static final int VERSION = 13;
    public static final String TABLE_NAME = "alumno";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    public DAOImpl(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(20), SURNAME VARCHAR(50), MARKS VARCHAR(50)) ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public boolean insert(String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Datos a insertar
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        long result = db.insert(TABLE_NAME, null, contentValues);

        // Devuelve el row id, -1 si ha ocurrido algún error
        return result != -1;
    }

    public boolean update(String id, String name, String surname, String marks) {
        int nrows = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        // Datos a actualizar
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        nrows = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        return nrows > 0;
    }

    public int delete(String id) {
        int result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID=" + id);
            result = 1;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

        //return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    @Override
    public Cursor gatMarks(String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_4 + " LIKE '%" + marks + "%'", null);
        return res;
    }

    @Override
    public boolean borrarTodo() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME);
            result = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
