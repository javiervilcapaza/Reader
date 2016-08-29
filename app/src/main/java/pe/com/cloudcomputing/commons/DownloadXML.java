package pe.com.cloudcomputing.commons;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Xavil on 28/08/2016.
 */

public class DownloadXML {
    private static final String TAG = "DownloadXML";

    public static final String descargar(String string) {
        StringBuilder xmlResult = new StringBuilder();
        try {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            Log.d(TAG, "response code: " + response);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int charReader;
            char[] inputBuffer = new char[500];
            while (true) {
                charReader = reader.read(inputBuffer);
                if (charReader < 0) {
                    break;
                }
                if (charReader > 0) {
                    xmlResult.append(String.copyValueOf(inputBuffer, 0, charReader));
                }
            }
            reader.close();
            return xmlResult.toString();
        } catch (MalformedURLException e) {
            Log.d(TAG, "Error malformed" + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "IOException error" + e.getMessage());
        } catch (SecurityException e) {
            Log.d(TAG, "Security Exception error" + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "Error" + e.getMessage());
        }
        return null;
    }
}
