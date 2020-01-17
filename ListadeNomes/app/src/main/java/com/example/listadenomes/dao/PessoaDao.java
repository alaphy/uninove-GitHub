package com.example.listadenomes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.listadenomes.modelo.Pessoa;

import java.util.ArrayList;

public class PessoaDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBPessoa.db";
    private static final int VERSION = 1;
    private static final String TABELA = "pessoa";
    private static final String ID = "id";
    private static final String NOME = "nome";

    public PessoaDao(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+" ( " +
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+NOME+" TEXT ) ;";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE ISEXISTS"+TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    public long salvarPessoa(Pessoa p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,p.getNome());


        retornoDB = getWritableDatabase().insert(TABELA,null,values);

        return retornoDB;
    }


    public long alterarPessoa(Pessoa p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,p.getNome());


        String[] args = {String.valueOf(p.getId())};

        retornoDB = getWritableDatabase().update(TABELA,values,ID+"=?",args);

        return retornoDB;
    }


    public long exluirPessoa(Pessoa p){

        long retornoDB;



        String[] args = {String.valueOf(p.getId())};

        retornoDB = getWritableDatabase().delete(TABELA,ID+"=?",args);

        return retornoDB;
    }



    public ArrayList<Pessoa> selectAllPessoa(){

        String[] coluns = {ID,NOME};

        Cursor cursor = getWritableDatabase().query(TABELA,coluns,null,null,null,null,"upper (nome)",null);

        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        while (cursor.moveToNext()){
            Pessoa p = new Pessoa();

            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));

            listPessoa.add(p);

        }

        return listPessoa;
    }

}
