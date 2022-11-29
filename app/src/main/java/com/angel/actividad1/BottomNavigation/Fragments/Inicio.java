package com.angel.actividad1.BottomNavigation.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.angel.actividad1.Adapters.NoteAdapter;
import com.angel.actividad1.HttpRequest.API;
import com.angel.actividad1.HttpRequest.ApiService;
import com.angel.actividad1.HttpRequest.Request.NoteSchema;
import com.angel.actividad1.HttpRequest.Response.Notes;
import com.angel.actividad1.HttpRequest.Response.SQL;
import com.angel.actividad1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Inicio extends Fragment {
    View view;
    API api = new API();

    ListView listViewNotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        getNotes();
        Button SendNote = view.findViewById(R.id.btnSend);

        SendNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNote();
            }
        });
        return view;
    }

    private void getNotes() {
        try {
            listViewNotes = view.findViewById(R.id.ListViewNotes);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(api.getURL())
                    .addConverterFactory((GsonConverterFactory.create()))
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<Notes[]> call = apiService.getNotes();
            call.enqueue(new Callback<Notes[]>() {
                @Override
                public void onResponse(Call<Notes[]> call, Response<Notes[]> response) {
                    if (!response.isSuccessful()) {
                        Log.d("notes", "onResponse: " + response.code());
                        return;
                    } else {
                        // Obtenemos la respuesta en un array
                        Notes[] notes = response.body();
                        /*for (Notes message : notes) {
                            Log.d("note",message.getMessage());
                        }*/
                        if (notes.length > 0) {
                            NoteAdapter noteAdapter = new NoteAdapter(notes, getActivity(), api);
                            listViewNotes.setAdapter(noteAdapter);
                        } else {
                            listViewNotes.setAdapter(null);
                        }
                    }

                }

                @Override
                public void onFailure(Call<Notes[]> call, Throwable t) {
                    Log.d("notes", "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("notes", "Catch: " + e.getMessage());
        }
    }

    private void SendNote() {
        EditText titulo = view.findViewById(R.id.txtTitulo);
        EditText mensaje = view.findViewById(R.id.txtMensaje);

        getNote(titulo.getText().toString(), mensaje.getText().toString());
    }

    private void getNote(String titulo, String mensaje) {

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(api.getURL())
                    .addConverterFactory((GsonConverterFactory.create()))
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            NoteSchema noteSchema = new NoteSchema(titulo, mensaje);
            Call<SQL> call = apiService.addNote(noteSchema);
            call.enqueue(new Callback<SQL>() {
                @Override
                public void onResponse(Call<SQL> call, Response<SQL> response) {
                    if (!response.isSuccessful()) {
                        Log.d("notes", "onResponse: " + response.code());
                        return;
                    } else {
                        // Obtenemos la respuesta en un array
                        SQL sql = response.body();
                        Toast.makeText(getContext(), "Nota agregada!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<SQL> call, Throwable t) {
                    Log.d("notes", "onFailure: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("notes", "Catch: " + e.getMessage());
        }
    }
}