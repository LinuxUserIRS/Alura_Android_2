package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by italo on 08/01/18.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    //Esse contexto pode não ter vindo da nossa aplicação.
    //Então o Anroid já passa o contexto atual para nós.
    public void onReceive(Context context, Intent intent) {
        //putExtra passa algo para uma intent
        //getSerializableExtra pega algo da intent
        //Guardando várias PDUs em um array de objects
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        //SmsMessage SMS = SmsMessage.createFromPdu();
        Toast.makeText(context, "SMS Recebido", Toast.LENGTH_SHORT).show();
    }
}
