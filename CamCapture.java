package edu.umbc.kl15773.faceprofiling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;

public class CamCapture extends AppCompatActivity {
    String API_KEY = "Dsg35uBYXNmsh5bVq70BhG1PCxXNp1x0LACjsn4SSr2u56lMDd";
    String albumKey, albumName = "PhotoDatabase";
    Button uploadB;
    EditText nameTxt, descTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_capture);

        //
        uploadB = (Button) findViewById(R.id.sumbitB);
        nameTxt = (EditText) findViewById(R.id.profileNameTxt);
        descTxt = (EditText) findViewById(R.id.profileDescTxt);
    }

    protected void createAlbum(){
        HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/album")
                .header("X-Mashape-Key", API_KEY)
                .field("album", albumName)
                .asJson();
        // saves album's key
        System.out.println(response);
        //albumKey = (String) response["albumkey"];
    }

    protected void updateAlbum(String filePath, String name){
        HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/album_train")
                .header("X-Mashape-Key", API_KEY)
                .field("album", "CELEBS")
                .field("albumkey", albumKey)
                .field("entryid", name)
                .field("files", new File(filePath))
                .asJson();
    }

    protected void findMatchId(String file){
        HttpResponse<JsonNode> response = Unirest.post("https://lambda-face-recognition.p.mashape.com/recognize")
                .header("X-Mashape-Key", API_KEY)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .field("album", albumName)
                .field("albumkey", albumKey)
                .field("urls", new File(file))
                .asJson();
        // finds id with highest confidence

    }
}
