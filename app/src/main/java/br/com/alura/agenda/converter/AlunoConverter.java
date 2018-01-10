package br.com.alura.agenda.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by italo on 09/01/18.
 */

public class AlunoConverter {
    public String convertToJSON(List<Aluno> alunos){
        //Classe usada para criar JSON já que codar na mão é trabalhoso e propenso a erros
        JSONStringer JSS = new JSONStringer();
        try {
            //Object equivale a {
            //Key é uma chave de acesso
            //Array equivale a [
            //Key().value() especifica um par chave/valor
            JSS.object().key("list").array().object().key("aluno").array();
            for(Aluno aluno : alunos){
                JSS.object();
                JSS.key("id").value(aluno.getId());
                JSS.key("nome").value(aluno.getNome());
                JSS.key("endereco").value(aluno.getEndereco());
                JSS.key("telefone").value(aluno.getTelefone());
                JSS.key("site").value(aluno.getSite());
                JSS.key("nota").value(aluno.getNota());
                JSS.endObject();
            }
            JSS.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JSS.toString();
    }
}
