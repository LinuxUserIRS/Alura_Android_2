package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 37;
    private FormularioHelper helper;
    private String photoSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        final Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null){
            helper.preencheFormulario(aluno);
        }
        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Não sabemos onde a foto será salva. Então só chamar esse método não é suficiente.
                //Temos que especificar onde será salvo
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Crio uma string com o caminho da foto pois cada aplicativo só pode escrever em certas áreas.
                //Então preciso garantir que ele está escrevendo na área certa.
                //Para isso uso o método getExternalFilesDir que retorna onde a plicação está instalada.
                //O nome da foto é derivado de uma time stamp em milissegundos pois o nome precisa ser único para cada foto.
                //Senão cada foto seria sobrescrita a cada foto nova.
                photoSrc = getExternalFilesDir(null) + "/"+ System.currentTimeMillis() + ".jpg";
                File photoFile = new File(photoSrc);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                //É criada uma constante com o código associado a chamada da câmera. Boas práticas de programação.
                startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
            }
        });

    }

    //Esse método é sempre chamado após retornar de uma acitivty.
    //Por isso devemos difenciar estre os códigos dos métodos que iniciaram a activity.
    //Poderíamos ter um método que chama o microfone e depois volta para cá, por exemplo.
    //Então temos que diferenciar se estamos usando a câmera ou o microfone e tomar as ações adequadas nesse exemplo.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            helper.carregaImagem(photoSrc);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.getAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() != null){
                    dao.altera(aluno);
                }else{
                    dao.insere(aluno);
                }


                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
