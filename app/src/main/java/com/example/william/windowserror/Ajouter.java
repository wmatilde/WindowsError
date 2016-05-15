package com.example.william.windowserror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajouter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        this.envoyerErreur();
        retourMenu();
    }

    /*
    Méthode pour retourner sur le menu lors d'un appui sur le bouton retour du téléphone
    */
    private void retourMenu(){
        Intent intent = new Intent(Ajouter.this, Menu.class);
    }

    /*
    Méthode lors du clic sur le bouton
    */
    private void envoyerErreur() {
        ((Button) findViewById(R.id.btnsend)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String CodText = ((EditText) findViewById(R.id.CodText)).getText().toString();
                String DesText = ((EditText) findViewById(R.id.DesText)).getText().toString();
                //Si le champs est correctement rempli, alors
                if ((!(CodText.equals("")))&(CodText.length()==8)&(!(DesText.equals("")))) { // Si le champs du code est rempli
                        enregBDD(CodText, DesText); // On appelle la méthode envoiErreurWindows
                        Toast.makeText(Ajouter.this, "L'erreur à bien été enregistrée", Toast.LENGTH_SHORT).show();
                }else { // Sinon on affiche une erreur
                    Toast.makeText(Ajouter.this, "Un code de 8 caractères et une description doivent être remplis !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    Enregistrement dans la base de données distante
    */
    private void enregBDD(String CodText, String DesText) {
        // création de l'objet d'accès à distance avec reféfinition à la volée de la méthode onPostExecute
        AccesHTTP accesDonnees = new AccesHTTP(){
            @Override
            protected void onPostExecute(Long result) {
                // ret contient l'information récupérée
                Log.d("Retour du serveur", this.ret.toString()) ;
            }
        };
        // ajout des données en paramêtre
        accesDonnees.addParam("wi", "enreg");
        accesDonnees.addParam("code",CodText.toString());
        accesDonnees.addParam("description", DesText.toString());
        // envoi
        accesDonnees.execute("http://192.168.0.14/ProjetAndroid/getErreur.php");

    }
}
