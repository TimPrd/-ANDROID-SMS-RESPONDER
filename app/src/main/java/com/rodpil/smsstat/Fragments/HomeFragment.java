package com.rodpil.smsstat.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodpil.smsstat.Utils.FileUtils;
import com.rodpil.smsstat.RecyclerView.MyAdapter;
import com.rodpil.smsstat.RecyclerView.MyObject;
import com.rodpil.smsstat.Contact.Person;
import com.rodpil.smsstat.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    private List<MyObject> cities = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addContact();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new MyAdapter(cities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void addContact() {
        List<Person> alContacts = null;
        try {
            alContacts = FileUtils.JSONParser(getActivity(),"contacts.json");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (alContacts != null) {
            Collections.sort(alContacts, (Person p1, Person p2) -> p1.getName().compareTo(p2.getName()));
        }

        if (alContacts != null) {
            for (Person p : alContacts)
                cities.add(new MyObject(p.getName(),p.getPhone(), p.getCounter()));
        }

    }

}