package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by italo on 09/01/18.
 */

public class SendStudents extends AsyncTask<Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;

    public SendStudents(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        //Código executado em background
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String JSON = conversor.convertToJSON(alunos);
        WebClient client = new WebClient();
        String reposta = client.post(JSON);
        return reposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        //Código executado após fim de execução da thread
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    @Override
    protected void onPreExecute() {

        dialog = ProgressDialog.show(context, "Aguarde", "Calculando média....", true, true);

    }
}
