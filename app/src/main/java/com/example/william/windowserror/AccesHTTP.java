package com.example.william.windowserror;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AccesHTTP extends AsyncTask<String, Integer, Long> {
	public String ret="";
	private ArrayList<NameValuePair> parametres;
	
	public AccesHTTP(){
		parametres = new ArrayList<NameValuePair>();
	}
	
	public void addParam(String nom, String valeur){
		parametres.add(new BasicNameValuePair(nom,valeur));
	}
	
	@Override
	protected Long doInBackground(String... urls) {

		HttpClient cnxHttp = new DefaultHttpClient();
		HttpPost paramCnx = new HttpPost(urls[0]);
		
		try {
		    paramCnx.setEntity(new UrlEncodedFormEntity(parametres));
		    HttpResponse reponse = cnxHttp.execute(paramCnx);
		    ret = EntityUtils.toString(reponse.getEntity());

		} catch (ClientProtocolException e) {
		    Log.d("ClientProtocolException", e.toString()) ;
		} catch (IOException e) {
            Log.d("Erreur IOException", e.toString()) ;
		}

        return null;
	}

	@Override
	protected void onPostExecute(Long result) {
		// preciser le code dans la classe qui l'appelle en surchargeant la methode
    }
	
}
