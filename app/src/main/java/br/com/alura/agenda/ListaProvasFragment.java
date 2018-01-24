package br.com.alura.agenda;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Prova;

/**
 * Created by italo on 19/01/18.
 */

public class ListaProvasFragment extends android.support.v4.app.Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        //Fazendo "push" na lista de conteúdos da prova e criando uma prova de português
        List<String> topicosPort = Arrays.asList("Concordância Verbal", "Concordância Nominal");
        Prova provaProtugues = new Prova("Português", "25/03/2018", topicosPort);
        //Fazendo o mesmo aqui com mateḿatica
        List<String> topicosMat = Arrays.asList("Trigonometria", "Geometria Plana"," Geometria Espacial");
        final Prova provaMat = new Prova("Matemática", "12/04/2018", topicosMat);
        //Criando lista para passar para o adapter
        List<Prova> provas = Arrays.asList(provaProtugues, provaMat);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.lista_provas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prova prova = (Prova) adapterView.getItemAtPosition(i);

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
            }
        });
        return view;
    }
}
