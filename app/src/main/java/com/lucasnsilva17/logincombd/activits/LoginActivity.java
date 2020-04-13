package com.lucasnsilva17.logincombd.activits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lucasnsilva17.logincombd.R;

import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.OperacaoBancoException;
import com.lucasnsilva17.logincombd.dao.UsuarioDAO;
import com.lucasnsilva17.logincombd.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dao= new UsuarioDAO(this);
    }

    public void doLogin(View view){
        TextView usuario = (TextView) findViewById(R.id.txtLogin);
        TextView senha = (TextView) findViewById(R.id.txtSenha);

        String tUsuario = usuario.getText().toString();
        String tSenha = senha.getText().toString();

        Usuario user = new Usuario();

        user.setNome(tUsuario);
        user.setSenha(tSenha);

        List<Usuario> listaUsuarios= null;

        try {
            listaUsuarios = dao.buscarUsuario(user);
        }catch(OperacaoBancoException e){
             listaUsuarios=new ArrayList<>();
            mensagem("Erro ao busar usuario");
        }

        if(listaUsuarios.isEmpty()){
            mensagem("Usuário não encontrado!");
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void validaCampos(Usuario user){

    }

    private void mensagem(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastrar, menu);
        return true;
    }

    public void cadastrar(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), CadUsuarioActivity.class);
        startActivity(intent);
    }
}
