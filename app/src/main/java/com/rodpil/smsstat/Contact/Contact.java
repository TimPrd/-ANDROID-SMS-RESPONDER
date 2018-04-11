package com.rodpil.smsstat.Contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.rodpil.smsstat.Utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by licence on 13/03/2018.
 */

public class Contact extends AsyncTask<Void, Void, Void> {

    private String number;
    private String name;
    private Context _context;
    private ContentResolver _resolver;
    ArrayList<HashMap<String, String>> contactList;

    public Contact(Context context, ContentResolver resolver) {
        this._context = context;
        this._resolver = resolver;
    }


    public static List getContacts(ContentResolver resolver) {
        /*Uri uri = android.provider.Contacts.People.CONTENT_URI;
        Cursor cr = resolver.query(uri,null,null,null,null);
        cr.moveToFirst();
        List<Contact> alContacts = new ArrayList();
        do{
            String phone = cr.getString(cr.getColumnIndex(Contacts.People.NUMBER));
            String name = cr.getString(cr.getColumnIndex(Contacts.People.NAME));
            alContacts.add(new Contact(phone,name));
        }while(cr.moveToNext());*/
        List<Contact> alContacts = new ArrayList();

        Cursor cr = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cr.moveToNext()) {
            String name = cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //alContacts.add(new Contact(phone, name));
            Log.i("tes", "Name: " + name);
            Log.i("tes", "Phone Number: " + phone);

        }
        cr.close();

        return alContacts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray rules = new JSONArray();


        Cursor cr = _resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        int i =0;
        while (cr.moveToNext()) {


            String name = cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            try {
                JSONObject currentObj = new JSONObject();
                currentObj.put("name", name);
                currentObj.put("phone", phone);
                currentObj.put("SMS", 1);
                currentObj.put("rules", rules);
                jsonArray.put(currentObj);


                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            json.put("Contacts", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //String FILENAME = "storage.json";
        //File directory = _context.getFilesDir();
        //File file = new File(directory, FILENAME);


       FileUtils.writeFile(_context, "contacts.json", json);

        //Log.i("file",file.toString());
        //Toast.makeText(_context, Boolean.toString( file.exists() ) ,
        //      Toast.LENGTH_LONG).show();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        String e = FileUtils.readFile(_context,"contacts.json");
        Toast.makeText(_context, e.toString(), Toast.LENGTH_LONG).show();

    }

}
