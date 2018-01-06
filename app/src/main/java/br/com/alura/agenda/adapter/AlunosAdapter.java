package br.com.alura.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.ListaAlunosActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by italo on 06/01/18.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context contexto;

    public AlunosAdapter(Context contexto, List<Aluno> alunos) {
        this.contexto = contexto;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        //ConvertView reaproveita as views que já foram instanciadas e só muda os valores
        //Esse if serve para checar isso e melhorar o desempenho do app
        View view = convertView;
        if(view==null){
            //Da mesma forma que usamos o Menu Inflater no FormularioActivity, vamos usar o LayoutInflater aqui
            LayoutInflater inflater = LayoutInflater.from(contexto);
            //Passo a view, o pai da view (a lista) e digo para não inserir a view automaticamente.
            //Se colocar "true", o Android vai tentar inserir a view 2 vezes no mesmo lugar e vai ocasionar uma exception
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        //Pego a referência à TextView que carrega o nome do aluno
        TextView campoNome = (TextView) view.findViewById(R.id.list_item_profileName);
        //Coloco o nome do aluno na TextView
        campoNome.setText(aluno.getNome());
        //Fazendo o mesmo aqui com o telefone
        TextView campoTelefone = (TextView) view.findViewById(R.id.list_item_profilePhone);
        campoTelefone.setText(aluno.getTelefone());
        //Fazendo o mesmo aqui com a imagem
        ImageView campoImagem = (ImageView) view.findViewById(R.id.list_item_profilePicture);
        String photoSrc = aluno.getFoto();
        if(photoSrc != null){
            //É criado um bitmap da imagem capturada
            Bitmap bitmap = BitmapFactory.decodeFile(photoSrc);
            //O bitmap é reduzido para caber bem na ImageView. O ideal é ficar abaixo de 512x512
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoImagem.setImageBitmap(scaledBitmap);
            campoImagem.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return view;
    }
}
