package br.com.alura.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by italo on 24/01/18.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //Método que conversa assincronamente com os servers do Google
        //Requer a implementação do método OnMapReady
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Inicializando string com o endereço a ser buscado
        String end = "Rua Emiliano Braga, 635";
        //Inicializando objeto LatLng que é necessário como parâmetro
        LatLng posicaoEscola = null;
        try {
            posicaoEscola = getEndereco(end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Se resultado do método getEndereco não nulo, chamo a função de centralização e zoom do mapa
        if(posicaoEscola != null){
            //Passando LatLng e nível de zoom
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoEscola, 17);
            //Atualizando tela do mapa
            googleMap.moveCamera(update);
        }

        //Instanciando BD para carregar lista de alunos
        AlunoDAO dao = new AlunoDAO(getContext());
        LatLng coord=null;
        for (Aluno aluno : dao.buscaAlunos()){
            try {
                //Pegando endereço de cada um dos alunos
                coord = getEndereco(aluno.getEndereco());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Se busca de latitude e longitude retorna não nulo
            if(coord!=null){
                //Instancio marcador
                MarkerOptions marcador = new MarkerOptions();
                //Seto a posição do marcador
                marcador.position(coord);
                //Seto o título do marcador
                marcador.title(aluno.getNome());
                //Seto o subtexto do marcador como a nota do aluno
                marcador.snippet(String.valueOf(aluno.getNota()));
                //Adiciono o marcador no mapa
                googleMap.addMarker(marcador);
            }
        }
        //Fecho a conexão com o BD
        dao.close();
        new Localizador(getContext(), googleMap);
    }

    private LatLng getEndereco(String Endereco) throws IOException {
        LatLng posicao=null;
        //Chamando geocoder para converter endereço em latitude e longitude
        //Requer acesso a recursos do sistema, logo, precisa de contexto
        Geocoder geocoder = new Geocoder(getContext());
        //Buscando o endereço e selecionando apenas um resultado
        List<Address> result = geocoder.getFromLocationName(Endereco, 1);
        //Se a lista de resultados for não nula, instancio um objeto LatLng
        //com a latitude e longitude
        if(!result.isEmpty()){
            posicao = new LatLng(result.get(0).getLatitude(), result.get(0).getLongitude());
        }
        return posicao;
    }
}
