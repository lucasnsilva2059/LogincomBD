package com.lucasnsilva17.logincombd.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lucasnsilva17.logincombd.R;
import com.lucasnsilva17.logincombd.model.Usuario;

import java.util.List;

public class UsuarioListAdapter extends ArrayAdapter<Usuario>{

    private static final String TAG="UsuarioListAdapter";
    private Context context;
    private int resource;

    public UsuarioListAdapter(Context context, int resource, List<Usuario> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Usuario usuario = new Usuario();
        usuario.setId(getItem(position).getId());
        usuario.setNome(getItem(position).getNome());
        usuario.setSenha(getItem(position).getSenha());

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvId = (TextView) convertView.findViewById(R.id.txtIdAD);
        TextView tvUsuario = (TextView) convertView.findViewById(R.id.txtUsuarioAD);
        TextView tvSenha = (TextView) convertView.findViewById(R.id.txtSenhaAD);

        StringBuilder stringBuilderId = new StringBuilder();
        stringBuilderId.append("ID: ");
        stringBuilderId.append(usuario.getId().toString());

        StringBuilder stringBuilderNome = new StringBuilder();
        stringBuilderNome.append("Nome: ");
        stringBuilderNome.append(usuario.getNome());

        StringBuilder stringBuilderSenha = new StringBuilder();
        stringBuilderSenha.append("Senha: ");
        stringBuilderSenha.append(usuario.getSenha());

        tvId.setText(stringBuilderId);
        tvUsuario.setText(stringBuilderNome);
        tvSenha.setText(stringBuilderSenha);

        return convertView;
    }
}
