package com.lucasnsilva17.logincombd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.OperacaoBancoException;
import com.lucasnsilva17.logincombd.conection.Conexao;
import com.lucasnsilva17.logincombd.model.Usuario;

public class UsuarioDAO {

    private Conexao conn;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context) {
        this.conn = new Conexao(context);
        this.banco = conn.getWritableDatabase();
    }

    public long inserirNovoUsuario(Usuario usuario) throws OperacaoBancoException {
        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getNome());
        values.put("senha", usuario.getSenha());

        try {
            return banco.insert("usuario", null, values);
        } catch (RuntimeException e) {
            throw new OperacaoBancoException();
        }

    }

    public List<Usuario> buscarUsuarios() throws OperacaoBancoException {
        List<Usuario> listReturn = new ArrayList<>();

        Cursor cursor = banco.query("usuario", new String[]{"id", "usuario", "senha"}, null, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                Usuario user = new Usuario();
                user.setId(cursor.getInt(0));
                user.setNome(cursor.getString(1));
                user.setSenha(cursor.getString(2));
                listReturn.add(user);
            }
        } catch (RuntimeException e) {
            throw new OperacaoBancoException();
        }

        return listReturn;
    }


    public List<Usuario> buscarUsuario(Usuario usuario) throws OperacaoBancoException {
        List<Usuario> listReturn = new ArrayList<>();

        String sql = "SELECT ID, USUARIO, SENHA FROM USUARIO WHERE USUARIO=? AND SENHA=?";
        try {
            Cursor cursor = banco.rawQuery(sql, new String[]{usuario.getNome(), usuario.getSenha()});

            while (cursor.moveToNext()) {
                Usuario user = new Usuario();
                user.setId(cursor.getInt(0));
                user.setNome(cursor.getString(1));
                user.setSenha(cursor.getString(2));
                listReturn.add(user);
            }
        } catch (RuntimeException e) {
            throw new OperacaoBancoException();
        }

        return listReturn;
    }

    public void excluir(Usuario usuario) throws OperacaoBancoException {
        try {
            banco.delete("usuario", "id = ?", new String[]{usuario.getId().toString()});
        } catch (RuntimeException e) {
            throw new OperacaoBancoException();
        }
    }

    public void atualizar(Usuario usuario) throws OperacaoBancoException {
        try {

            ContentValues values = new ContentValues();
            values.put("usuario", usuario.getNome());
            values.put("senha", usuario.getSenha());

            banco.update("usuario", values, "id = ? ", new String[]{usuario.getId().toString()});

        } catch (RuntimeException e) {
            throw new OperacaoBancoException();
        }
    }
}
