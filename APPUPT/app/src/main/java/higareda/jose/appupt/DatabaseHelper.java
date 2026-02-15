package higareda.jose.appupt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "uptecamac.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Aquí pegas el comando CREATE TABLE de tu archivo, pero simplificado para SQLite
        String CREATE_TABLE = "CREATE TABLE registros (" +
                "curp TEXT PRIMARY KEY," +
                "nombre TEXT," +
                "apellidos TEXT," +
                "rfc TEXT," +
                "nss TEXT," +
                "telefono TEXT," +
                "correo TEXT," +
                "sexo TEXT," +
                "matricula TEXT," +
                "foto TEXT," +
                "foto_validada INTEGER," +
                "fecha TEXT," +
                "curp_pdf TEXT," +
                "acta_pdf TEXT," +
                "estado TEXT DEFAULT 'pendiente')";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registros");
        onCreate(db);
    }
}