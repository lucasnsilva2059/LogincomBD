package com.lucasnsilva17.logincombd.activits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lucasnsilva17.logincombd.R;
import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.CamposVaziosExceptions;
import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.OperacaoBancoException;
import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.SenhasDiferentesException;
import com.lucasnsilva17.logincombd.dao.UsuarioDAO;
import com.lucasnsilva17.logincombd.model.Usuario;

public class CadUsuarioActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private EditText senha2;

    private UsuarioDAO dao;

    private Usuario usuarioAtualizar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        usuario = (EditText) findViewById(R.id.txtCadUsuario);
        senha = (EditText) findViewById(R.id.txtCadSenha);
        senha2 = (EditText) findViewById(R.id.txtCadSenha2);
        dao = new UsuarioDAO(this);

        Intent intent = getIntent();
        if (intent.hasExtra("usuario")) {
            usuarioAtualizar = (Usuario) intent.getSerializableExtra("usuario");
            usuario.setText(usuarioAtualizar.getNome());
        }
    }

    public void insertUser(View view) {

        try {
            validaCampos();
        } catch (CamposVaziosExceptions e) {
            mensagem(e.getMessage());
        } catch (SenhasDiferentesException e) {
            mensagem(e.getMessage());
        }

        if (usuarioAtualizar == null) {

            Usuario user = new Usuario();
            user.setNome(usuario.getText().toString());
            user.setSenha(senha.getText().toString());

            long idUser = 0;
            try {
                idUser = dao.inserirNovoUsuario(user);
            } catch (OperacaoBancoException e) {
                mensagem("Erro ao atualizar usuario");
            }
            mensagem("Usuario inserido com o ID: " + idUser);

            usuario.setText("");
            senha.setText("");
            senha2.setText("");
        } else {
            Usuario user = new Usuario();
            user.setId(usuarioAtualizar.getId());
            user.setNome(usuario.getText().toString());
            user.setSenha(senha.getText().toString());

            try {
                dao.atualizar(user);
            } catch (OperacaoBancoException e) {
                mensagem("Erro ao atualizar usuario");
            }

            mensagem("Usuario " + user.getNome() + " atualizado");

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void validaCampos() throws CamposVaziosExceptions, SenhasDiferentesException{
        if (usuario.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || senha2.getText().toString().isEmpty()) {
            CamposVaziosExceptions exception = new CamposVaziosExceptions();
            exception.setMessage("Preencha todos os campos");
            throw exception;
        } else if (!senha.getText().toString().equals(senha2.getText().toString())) {
            SenhasDiferentesException exception = new SenhasDiferentesException();
            exception.setMessage("As senhas informadas não combinam");
            throw exception;
        }
    }

    private void mensagem(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    public void voltarLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
