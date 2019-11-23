package com.example.fall_1st;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecastActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Weather Forecast";
    private Context thisApp;
    private EditText currentTemperatureText,minTemperatureText,maxTemperatureText, uvRating;
    private ImageView weatherImage;
    private ProgressBar progressBar;
    String queryURL;
   // String iconName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        thisApp=this;
        weatherImage=(ImageView)findViewById(R.id.weatherImage);
        currentTemperatureText = (EditText)findViewById(R.id.currentTemperature);
        minTemperatureText = (EditText)findViewById(R.id.minTemperature);
        maxTemperatureText=(EditText)findViewById(R.id.maxTemperature);
        uvRating= (EditText) findViewById(R.id.uvRating);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);  //show the progress bar
        queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        WeatherForecast networkThread = new WeatherForecast(queryURL);
        networkThread.execute(queryURL);
    }

    //                                               Type1, Type2   Type3
    private class WeatherForecast extends AsyncTask<String, Integer, String> {
        String currentTemperature,minTemperature,maxTemperature, iconName;
        Bitmap image;
        String uvRatingValue;
        public WeatherForecast(String s) {
        //    responseType = s;
        }
        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            String ret = null;


            try {       // Connect to the server:
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;         //While not the end of the document:
                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    switch (EVENT_TYPE) {
                        case START_TAG:         //This is a start tag < ... >
                            String tagName = xpp.getName(); // What kind of tag?
                            if (tagName.equals("temperature")) {
                                currentTemperature = xpp.getAttributeValue(null, "value");
                                Log.d("AsyncTask", "Found currentTemperature: " + currentTemperature);
                                publishProgress(25);

                                minTemperature = xpp.getAttributeValue(null, "min");
                                Log.d("AsyncTask", "Found minTemperature: " + minTemperature);
                                publishProgress(50);

                                maxTemperature = xpp.getAttributeValue(null, "max");
                                Log.d("AsyncTask", "Found maxTemperature: " + minTemperature);
                                publishProgress(75);
                            } else if (tagName.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                                Log.d("AsyncTask", "Found Icon: " + iconName);
                                publishProgress(1);

                            }

                            break;
                        case END_TAG:           //This is an end tag: </ ... >
                            break;
                        case TEXT:              //This is text between tags < ... > Hello world </ ... >
                            break;
                    }
                    xpp.next(); // move the pointer to next XML element
                }//end of XML  weather reading

                //download an image
                File file = getBaseContext().getFileStreamPath(iconName +".png");
                // File file = new File(getApplicationContext().getFilesDir(),"iconName");//create a new file object
                URL iconURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");

               // Bitmap image = null;
                HttpURLConnection iconConnection = (HttpURLConnection) iconURL.openConnection();
                iconConnection.connect();

                if (fileExistance(iconName + ".png")) {
                    Log.d(ACTIVITY_NAME, "Looking for file" + iconName + ".png");
                    Log.d(ACTIVITY_NAME, "Icon downloaded, found locally");

                    int responseCode = iconConnection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(iconConnection.getInputStream());
                    }

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconName + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = BitmapFactory.decodeStream(fis);

                } else {
                    Log.d(ACTIVITY_NAME, "Looking for file" + iconName + ".png");
                    Log.d(ACTIVITY_NAME, "Icon does not exist, need to download");

                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                publishProgress(100);

                //get Json for UV Rating
                //String uvRating = null;
                String uvQueryURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
                  // Connect to the server:
                    URL urlUvQuery = new URL(uvQueryURL);
                    HttpURLConnection uvQueryConnection = (HttpURLConnection) urlUvQuery.openConnection();
                    InputStream uvRatingStream = uvQueryConnection.getInputStream();

                    //Set up the JSON object parser:
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(uvRatingStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();
                    JSONObject jObject = new JSONObject(result);
                    Double uvValue = jObject.getDouble("value");
                    uvRatingValue = uvValue + "";
                    Log.i("UV is:", "" + uvValue);
                    Log.e("UV is:", "" + uvRatingValue);

                } catch (JSONException | MalformedURLException mfe) {
                    ret = "Malformed URL exception";
                } catch (IOException ioe) {
                    ret = "IO Exception. Is the Wifi connected?";
                } catch (XmlPullParserException pe) {
                    ret = "XML Pull exception. The XML is not properly formed";
                }
                //What is returned here will be passed as a parameter to onPostExecute:
                return ret;


        }


        @Override                       //Type 2
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(0);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
            //Update GUI stuff only:

        }

        @Override                   //Type 3
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);

            currentTemperatureText.append(currentTemperature + "℃");
            minTemperatureText.append(minTemperature + "℃");
            maxTemperatureText.append(maxTemperature + "℃");
            uvRating.append(uvRatingValue);
            weatherImage.setImageBitmap(image);

            progressBar.setVisibility(View.INVISIBLE);
            //update GUI Stuff:

        }
        }


    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    }
