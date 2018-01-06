package br.com.alura.agenda;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        //Pega o auno clicado para uso nas opções do menu de contexto
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        //Adicionando opção de enviar SMS.
        MenuItem enviarSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+aluno.getTelefone()));
        enviarSMS.setIntent(intentSMS);

        //Adicionando opção de visualização no mapa
        MenuItem verNoMapa = menu.add("Ver no mapa");
        Intent intentMapa= new Intent(Intent.ACTION_VIEW);
        //O protocolo para geolocalização requer a lat e long como parâmetros.
        //Mas pode-se fazer um query para buscar pelo endereço direto nos parâmetros.
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        verNoMapa.setIntent(intentMapa);

        //Adicionando opção de chamada telefônica.
        MenuItem ligarParaAluno = menu.add("Ligar para aluno");
        ligarParaAluno.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Verifico se a permissão já foi dada.
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    //Peço a permissão
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
                }
                Intent intentChamarAluno = new Intent(Intent.ACTION_CALL);
                intentChamarAluno.setData(Uri.parse("tel:"+aluno.getTelefone()));
                startActivity(intentChamarAluno);
                return false;
            }
        });

        //Opção de visitar o site. Cria o item e a intent e chama o site do aluno.
        MenuItem visitarSite = menu.add("Ir para o site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String siteDoAluno = aluno.getSite();
        //O "http://" é o protocolo que permite ao OS detectar o que está sendo aberto.
        //Sendo assim, ele é obrigatório para que o Android saiba que precise abrir o navegador
        if(!siteDoAluno.startsWith("http://")){
            siteDoAluno = "http://"+siteDoAluno;
        }
        intentSite.setData(Uri.parse(siteDoAluno));
        visitarSite.setIntent(intentSite);

        //Opção de deletar o aluno. Chama um toast e o método delete do AlunoDAO para apgar.
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                //Esse booleano retornado especifica se vão haver outros callbacks.
                //Se estiver falso, o evento é tratado só aqui.
                //Se estiver verdadeiro, o evento é tratado em todos os listeners que estão esperando por esse evento.
                return false;
            }
        });
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //Esse adapter só suporta TextViews. Sendo assim, precisamos criar nosso próprio adapter para trabalhar com views customizadas.
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.activity_list_item, alunos);
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }
}
