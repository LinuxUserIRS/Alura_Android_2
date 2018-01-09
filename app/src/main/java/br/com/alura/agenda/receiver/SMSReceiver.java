package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by italo on 08/01/18.
 */

public class SMSReceiver extends BroadcastReceiver {
    //O método createFromPdu requer API 23 no mínimo, mas o projeto tá como API 15 min. Então tem esse @RequiresApi
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    //Esse contexto pode não ter vindo da nossa aplicação.
    //Então o Anroid já passa o contexto atual para nós.
    public void onReceive(Context context, Intent intent) {
        //putExtra passa algo para uma intent
        //getSerializableExtra pega algo da intent
        //Guardando várias PDUs em um array de objects
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        //O primeiro PDU sempre vai existir. Mas talvez existam outros.
        byte[] pdu = (byte[]) pdus[0];
        //Existe uma chave já pronta com o formato
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage SMS = SmsMessage.createFromPdu(pdu, formato);
        //Chamada de método para recuperar número de origem do SMS
        String telefone = SMS.getDisplayOriginatingAddress();
        AlunoDAO alunoDAO = new AlunoDAO(context);
        if(alunoDAO.isAluno(telefone)){
            Toast.makeText(context, "SMS Recebido de um Aluno", Toast.LENGTH_SHORT).show();
            //Essa função muda o som de recebimento de SMS quando o SMS recebido é de um aluno
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            //Toca o novo som de SMS recebido
            mp.start();
        }

    }
}
