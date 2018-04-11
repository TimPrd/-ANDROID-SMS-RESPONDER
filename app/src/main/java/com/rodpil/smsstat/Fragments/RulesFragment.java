package com.rodpil.smsstat.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rodpil.smsstat.Contact.Person;
import com.rodpil.smsstat.R;
import com.rodpil.smsstat.Utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RulesFragment extends Fragment {
    private EditText name, phone, message, contains;
    private Button btnAddRule;
    private CheckBox chkContains;
    List<String> spinnerArray = new ArrayList<String>();

    public RulesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.phone = (EditText) view.findViewById(R.id.rules_phone);
        this.name = (EditText) view.findViewById(R.id.rules_name);
        this.chkContains = (CheckBox) view.findViewById(R.id.rules_chk_contains);
        this.contains = (EditText) view.findViewById(R.id.rules_contains);
        this.btnAddRule = (Button) view.findViewById(R.id.rules_btnAdd);
        this.message = (EditText) view.findViewById(R.id.rules_message);

        List<Person> alContacts = null;

        try {
            alContacts = FileUtils.JSONParser(getActivity(), "contacts.json");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        for (Person p : alContacts)
            spinnerArray.add(p.getName() + " - " + p.getPhone());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) view.findViewById(R.id.rules_spinner);
        sItems.setAdapter(adapter);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = parent.getItemAtPosition(position).toString().indexOf('-');
                name.setText(parent.getItemAtPosition(position).toString().substring(0, index));
                phone.setText(parent.getItemAtPosition(position).toString().substring(index + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnAddRule.setOnClickListener(v -> {

            //Method
            JSONObject compressed = new JSONObject();
            try {
                compressed.put("message", message.getText());
                if (chkContains.isChecked())
                    compressed.put("contains", contains.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //method
            InputStream is = null;
            try {
                is = getContext().openFileInput("contacts.json");

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

                    String phone = c.getString("phone");
                    if (phone.equals(this.phone.getText().toString().trim())) {
                        JSONArray e = c.getJSONArray("rules");
                        e.put(compressed);
                        c.put("rules", e);
                    }
                }

                json.put("contacts", contacts);
                FileUtils.writeFile(getContext(), "contacts.json", json);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            // Code here executes on main thread after user presses button
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules, container, false);
    }


    public void addRule() {

    }

}