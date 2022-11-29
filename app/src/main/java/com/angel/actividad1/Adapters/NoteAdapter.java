package com.angel.actividad1.Adapters;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.angel.actividad1.HttpRequest.API;
import com.angel.actividad1.HttpRequest.ApiService;
import com.angel.actividad1.HttpRequest.Response.Notes;
import com.angel.actividad1.HttpRequest.Response.SQL;
import com.angel.actividad1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteAdapter implements ListAdapter{

    Notes[] notes;
    FragmentActivity activity;
    API api;

    public NoteAdapter(Notes[] notes, FragmentActivity activity, API api) {
        this.notes    = notes;
        this.activity = activity;
        this.api      = api;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return notes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            view = layoutInflater.inflate(R.layout.notes_layout, null);
            TextView title = view.findViewById(R.id.txtAdapterTitle);
            TextView message = view.findViewById(R.id.txtAdapterMessage);
            Button btnAdapterDelete = view.findViewById(R.id.btnAdapterDelete);

            title.setText(notes[i].getTitle());
            message.setText(notes[i].getMessage());

            btnAdapterDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteNote(notes[i].getId());
                }
            });
        }
        return view;
    }

    private void deleteNote(int ID) {
        int id = ID;
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(api.getURL())
                    .addConverterFactory((GsonConverterFactory.create()))
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<SQL> call = apiService.deleteNote(id);
            call.enqueue(new Callback<SQL>() {
                @Override
                public void onResponse(Call<SQL> call, Response<SQL> response) {
                    if(!response.isSuccessful()){
                        Log.d("notes", "onResponse: " + response.code());
                        return;
                    } else{
                        // Obtenemos la respuesta en un array
                        SQL sql = response.body();
                        activity.recreate();
                        Toast.makeText(activity,"Nota borrada!",Toast.LENGTH_LONG).show();
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

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return notes.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
