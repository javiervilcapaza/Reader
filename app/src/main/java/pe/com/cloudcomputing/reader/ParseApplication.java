package pe.com.cloudcomputing.reader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Xavil on 28/08/2016.
 */

public class ParseApplication {

    private static final String TAG = "ParseApplication";
    private ArrayList<FeedEntry> applications;


    public ParseApplication() {
        this.applications = new ArrayList<FeedEntry>();
    }

    public ArrayList<FeedEntry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG :
//                        Log.d(TAG, "parse: Iniciando para: " + tagName);
                        if("entry".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "parse: finalizando tag para" + tagName);
                        if(inEntry){
                            if("entry".equalsIgnoreCase(tagName)){
                                applications.add(currentRecord);
                                inEntry = false;
                            }else if("name".equalsIgnoreCase(tagName)){
                                currentRecord.setName(textValue);
                            }else if("artist".equalsIgnoreCase(tagName)){
                                currentRecord.setArtist(textValue);
                            }else if("summary".equalsIgnoreCase(tagName)){
                                currentRecord.setSummary(textValue);
                            }else if("image".equalsIgnoreCase(tagName)){
                                currentRecord.setImageURL(textValue);
                            }
                        }
                        break;
                    default:
                        // TODO implementar esta seccion
                }
                eventType = xpp.next();
            }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
