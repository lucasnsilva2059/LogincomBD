package com.lucasnsilva17.logincombd.activits;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.lucasnsilva17.logincombd.R;

import java.util.ArrayList;
import java.util.List;

import com.lucasnsilva17.logincombd.adapters.UsuarioListAdapter;
import com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions.OperacaoBancoException;
import com.lucasnsilva17.logincombd.dao.UsuarioDAO;
import com.lucasnsilva17.logincombd.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private ListView listaUsuarios;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaUsuarios = findViewById(R.id.lista_usuarios);
        dao = new UsuarioDAO(this);

        try {
            usuarios = dao.buscarUsuarios();
            usuariosFiltrados.addAll(usuarios);
        }catch(OperacaoBancoException e){
            usuarios = new ArrayList<>();
        }

        UsuarioListAdapter adaptador = new UsuarioListAdapter(this, R.layout.adapter_view_layout, usuariosFiltrados);
        listaUsuarios.setAdapter(adaptador);

        /*Cria a ação de aparecer o menu apos um atributos ficar seleecionado*/
        registerForContextMenu(listaUsuarios);
    }

    public void voltarLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void procuraUsuarios(String nome) {
        usuariosFiltrados.clear();

        for (Usuario user : usuarios) {
            if (user.getNome().toLowerCase().contains(nome.toLowerCase())) {
                usuariosFiltrados.add(user);
            }
        }
        listaUsuarios.invalidateViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            usuarios = dao.buscarUsuarios();
            usuariosFiltrados.clear();
            usuariosFiltrados.addAll(usuarios);
        }catch(OperacaoBancoException e){
            mensagem("Erro ao deletar registro");
        }finally {
            listaUsuarios.invalidateViews();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                procuraUsuarios(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraUsuarios(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu_contexto, menu);

    }

    private Usuario usuarioSelecionado(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        return usuariosFiltrados.get(menuInfo.position);
    }

    public void excluir(MenuItem item) {
        final Usuario usuarioSelecionado = usuarioSelecionado(item);


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir o usuário" + usuarioSelecionado.getNome() + "?")
                .setNegativeButton("NÂO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            usuariosFiltrados.remove(usuarioSelecionado);
                            usuarios.remove(usuarioSelecionado);
                            dao.excluir(usuarioSelecionado);
                        }catch (OperacaoBancoException e){
                            usuariosFiltrados.add(usuarioSelecionado);
                            usuarios.add(usuarioSelecionado);

                            mensagem("Erro ao deletar registro!");
                        } finally {
                            listaUsuarios.invalidateViews();
                        }
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item) {

        final Usuario usuarioSelecionado = usuarioSelecionado(item);

        Intent intent = new Intent(this, CadUsuarioActivity.class);
        intent.putExtra("usuario", usuarioSelecionado);
        startActivity(intent);
    }

    private void mensagem(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
