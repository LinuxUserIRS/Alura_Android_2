package br.com.alura.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        //Fazendo "push" na lista de conteúdos da prova e criando uma prova de português
        List<String> topicosPort = Arrays.asList("Concordância Verbal", "Concordância Nominal");
        Prova provaProtugues = new Prova("Português", "25/03/2018", topicosPort);
        //Fazendo o mesmo aqui com mateḿatica
        List<String> topicosMat = Arrays.asList("Trigonometria", "Geometria Plana"," Geometria Espacial");
        final Prova provaMat = new Prova("Matemática", "12/04/2018", topicosMat);
        //Criando lista para passar para o adapter
        List<Prova> provas = Arrays.asList(provaProtugues, provaMat);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) findViewById(R.id.lista_provas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Prova prova = (Prova) adapterView.getItemAtPosition(i);
                Intent vaiParaDetalhes = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);
                startActivity(vaiParaDetalhes);
            }
        });
    }
}
