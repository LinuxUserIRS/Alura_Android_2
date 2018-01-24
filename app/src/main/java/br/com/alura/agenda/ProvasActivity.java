package br.com.alura.agenda;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        //Replace substitui um frame layout por um fragment
        tx.replace(R.id.frame_principal, new ListaProvasFragment());
        if(atModoPaisagem()){
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }
        tx.commit();
    }

    private boolean atModoPaisagem() {
        return getResources().getBoolean(R.bool.modo_paisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(!atModoPaisagem()){
            FragmentTransaction tx = fragmentManager.beginTransaction();
            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle param = new Bundle();
            param.putSerializable("prova", prova);
            detalhesProvaFragment.setArguments(param);
            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            //Adiciona a transação na pilha que, normalmente, só tem acitivtys
            //para que o botão voltar volte para a lista de provas e não de alunos
            //O parâmetro null diz respeito ao algum marcador (ID ou algo assim) caso seja necessário localizar essa transação na pilha depois
            tx.addToBackStack(null);
            tx.commit();
        }else{
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) fragmentManager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCampos(prova);
        }

    }
}
