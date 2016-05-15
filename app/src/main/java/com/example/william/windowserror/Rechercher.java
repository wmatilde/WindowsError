package com.example.william.windowserror;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;

public class Rechercher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);
        this.rechercherErreur();
        retourMenu();

    }
/*
Méthode lors du clic sur le bouton
*/
    private void rechercherErreur() {
        ((Button) findViewById(R.id.btnCherche)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String txtCode = ((EditText) findViewById(R.id.txtCode)).getText().toString();
                //Si le champs est correctement rempli, alors
                if ((!(txtCode.equals("")))&(txtCode.length()==8)) { // Si le champs du code est rempli
                    recupErreurWindows(txtCode); // On appelle la méthode recupErreurWindows
                    ((TextView) findViewById(R.id.lbl1)).setText("Description de l'erreur 0x"+ txtCode.toString()+" :");
                } else { // Sinon on affiche une erreur
                    Toast.makeText(Rechercher.this, "Veuillez saisir un code comportant 8 caractères !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/*
Méthode pour récupérer la description de la base de données
*/
    private void recupErreurWindows(String txtCode){
        AccesHTTP accesBDD = new AccesHTTP(){
            @Override
            protected void onPostExecute(Long result) {
                try {
                    JSONArray tJson = new JSONArray(this.ret);
                    ((EditText) findViewById(R.id.txtCode)).getText().toString();
                    if (tJson.length()!=0) {  // Si la taille de tJson n'est pas nulle
                        String description = tJson.getJSONObject(0).getString("description"); // je récupère description
                        TextView des= (TextView) findViewById(R.id.textView3); // Pour l'afficher dans un textView
                        des.setText(description);
                    }
                }catch (JSONException e){
                    Log.d("log", "Pb decodage JSON " + e.toString());
                    Toast.makeText(Rechercher.this, "Le code que vous avez saisi n'est pas répertorié dans notre base de données", Toast.LENGTH_SHORT).show();
                }
            }
        };
        accesBDD.addParam("wi", "recup");
        accesBDD.addParam("num", txtCode);
        accesBDD.execute("http://192.168.0.14/ProjetAndroid/getErreur.php");
    }
/**
 * Méthode pour retourner sur le menu lors d'un appui sur le bouton retour du téléphone
*/
    private void retourMenu(){
        Intent intent = new Intent(Rechercher.this, Menu.class);
    }

}
