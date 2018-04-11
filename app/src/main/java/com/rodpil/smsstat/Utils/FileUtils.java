package com.rodpil.smsstat.Utils;

import android.content.Context;
import android.util.Log;

import com.rodpil.smsstat.Contact.Person;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    public static List<Person> JSONParser(Context _context, String filename) throws IOException, JSONException {
        List<Person> listPerson = new ArrayList<>();

        InputStream is = _context.openFileInput(filename);
        int size = is.available();

        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String jsonString = new String(buffer, "UTF-8");
        JSONObject json = new JSONObject(jsonString);

        // Getting JSON Array node
        JSONArray contacts = json.getJSONArray("Contacts");

        // looping through All Contacts
        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);
            String name = c.getString("name");
            String phone = c.getString("phone");
            String counter = c.getString("SMS");
            listPerson.add(new Person(name,phone, counter));
        }
        return listPerson;
    }


    public static Boolean checkIfExists(Context _context, String filename) {
        Boolean bool = false;
        try {
            InputStream inputStream = _context.openFileInput(filename);
            bool = (inputStream != null);
            inputStream.close();
            return bool;
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static String readFile(@NotNull Context _context, String filename) {
        String ret = "";

        try {
            InputStream inputStream = _context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.i("JIJEIDJIEJFIJE", ret);
        return ret;
    }


    public static void writeFile(Context _context, String fileName, Object data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(_context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}
