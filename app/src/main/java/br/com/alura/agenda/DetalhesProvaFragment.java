package br.com.alura.agenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.modelo.Prova;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesProvaFragment extends Fragment {
    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);
        //Pegando os campos pelo ID
        campoMateria = view.findViewById(R.id.detalhes_prova_materia);
        campoData = view.findViewById(R.id.detalhes_prova_data);
        listaTopicos = view.findViewById(R.id.detalhes_prova_topicos);

        Bundle param = getArguments();
        //Se estivermos em modo paisagem, o fragment vai ter um parâmetro
        if(param!=null){
            //Se tiver parâmetro, vai ser passado para a função para popular os campos
            populaCampos((Prova) param.getSerializable("prova"));
        }

        return view;
    }

    public void populaCampos(Prova prova){
        //Populando os campos
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());
        ArrayAdapter<String> adapterTopicos = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapterTopicos);
    }

}
