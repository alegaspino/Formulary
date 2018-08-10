package com.lego.formulary;

import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseTable {

    private final DatabaseOpenHelper databaseOpenHelper;

    private static final String TAG = "FormulaDatabase";

    private static final String DATABASE_NAME = "table_formula";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;

    private static final String COL_KEY = "NAME";
    private static final String COL_VALUE = "FORMULA";
    private static final String[] ALL_COL = new String[] {COL_KEY};

    public DatabaseTable(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    public Cursor getFormulas(String query) {
        String selection = COL_KEY + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, ALL_COL);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = sqLiteQueryBuilder.query(databaseOpenHelper.getReadableDatabase(), columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        private final Context helperContext;
        private SQLiteDatabase database;

        private static final String FTS_TABLE_CREATE = "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts3 (" + COL_KEY + ", " + COL_VALUE + ")";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            database = sqLiteDatabase;
            database.execSQL(FTS_TABLE_CREATE);
            loadDatabase();
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(sqLiteDatabase);
        }

        private void loadDatabase() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        loadFormulas();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadFormulas() throws IOException {
            final Resources resources =  helperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.formulas);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    String[] formulas = TextUtils.split(line, "~");
                    if (formulas.length < 2) {
                        continue;
                    }
                    long id = addFormula(formulas[0].trim(), formulas[1].trim());
                }
            } finally {
                bufferedReader.close();
            }
        }

        private long addFormula(String key, String value) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_KEY, key);
            contentValues.put(COL_VALUE, value);

            return database.insert(FTS_VIRTUAL_TABLE, null, contentValues);
        }
    }
}
