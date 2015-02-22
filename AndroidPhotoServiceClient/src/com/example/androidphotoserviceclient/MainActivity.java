package com.example.androidphotoserviceclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.GsonBuilder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
   
    
    

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment   {
		private static final int CAMERA_REQUEST = 1888;
		public PlaceholderFragment() {
		}
		 private final static String SERVICE_URI = "http://192.168.1.5/WcfAndroidPhotoServis/WcfAndroidImageService.svc/";
		  ImageView imageView = null;
		  byte[] photoasbytearray = null;
		  Photo ph = null;
		 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {  
		        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
		            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
		            
		            
		            imageView.setImageBitmap(photo);
		            
		            //getting photo as byte array
		            ByteArrayOutputStream stream = new ByteArrayOutputStream();
		            photo.compress(Bitmap.CompressFormat.JPEG, 100,stream);
		            photoasbytearray = stream.toByteArray();
		            
		            //give a name of the image here as date
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmm");
		            String currentDateandTime = sdf.format(new Date());
		            
		            ph = new Photo();
		            
		            String encodedImage = Base64.encodeToString(photoasbytearray,Base64.DEFAULT);
		            ph.photoasBase64=encodedImage;
		           
		            ph.photoName= currentDateandTime+".jpeg";
		            
		           
						
					
		            
		        }  
		    }
		 
		 
		private void SendToServer(Photo ph2) throws UnsupportedEncodingException {
			// TODO Auto-generated method stub
			 HttpPost httpPost = new HttpPost(SERVICE_URI+"LoadPhoto");
				
		       
			 httpPost.setHeader("Content-Type", "application/json; charset=UTF-8"); 
			 HttpClient httpClient = new DefaultHttpClient(getHttpParameterObj(17000,17000));
			 // Building the JSON object.
			
			 com.google.gson.Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			 String json = gson.toJson(ph2);

			 StringEntity entity = new StringEntity(json, HTTP.UTF_8);
			 Log.d("WebInvoke", "data : " + json.toString());
			
			 
			 httpPost.setEntity(entity);
			 // Making the call.
			 HttpResponse response = null;
			try {
				response = httpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
			 // Getting data from the response to see if it was a success.
			 BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
						 "UTF-8"));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 String jsonResultStr = null;
			try {
				jsonResultStr = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			  Log.d("WebInvoke", "donnen  deger : " + jsonResultStr);
		}


	
			private HttpParams getHttpParameterObj(int timeOutConnection,int timeOutSocket)
			{
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeOutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, timeOutSocket);
			return httpParameters;
			}
		


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			
			
			    imageView  = (ImageView)rootView.findViewById(R.id.imageView1);
		        Button btnOpenCam = (Button) rootView.findViewById(R.id.btnOpenCam);
		        Button btnSendServer = (Button) rootView.findViewById(R.id.btnSendServer);
			
		        btnOpenCam.setOnClickListener(new OnClickListener() {
				

					@Override
					public void onClick(View v) {
						// Start camera activity here
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
					}
				});
		        
		 
			
		        btnSendServer.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						try {
							SendToServer(ph);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			
			
			
			return rootView;
		}
	}

}
