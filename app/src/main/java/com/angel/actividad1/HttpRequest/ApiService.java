package com.angel.actividad1.HttpRequest;

import com.angel.actividad1.HttpRequest.Request.NoteSchema;
import com.angel.actividad1.HttpRequest.Response.Notes;
import com.angel.actividad1.HttpRequest.Response.SQL;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("notes/")
    Call<Notes[]> getNotes();

    @POST("notes/")
    Call<SQL> addNote(@Body NoteSchema noteSchema);

    @DELETE("notes/{id}/")
    Call<SQL> deleteNote(@Path("id") int id);
}
