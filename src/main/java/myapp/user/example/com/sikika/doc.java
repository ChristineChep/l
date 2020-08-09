package myapp.user.example.com.sikika;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class doc extends AppCompatActivity {

    private TextView notification;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private Uri pdfUri;
    private ProgressDialog progressDialog;
    private static final int CREATE_FILE = 1;
    private static final int PICK_PDF_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        Button selectFile = (Button) findViewById(R.id.selectFile1);
        Button upload = (Button) findViewById(R.id.upload1);


    }


    public void openDirectory(Uri uriToLoad){
        //choose a directory using the systems ile picker
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        //provide read accesss to files and subdirectories in the user-selected directory
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //optionally,speciy a URI for the directory that should be opened in the system file picker when it loads
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI,uriToLoad);

        startActivityForResult(intent,9);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 9 && resultCode == Activity.RESULT_OK) {
            //the result data contains a URI for the directory the user selected
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                //Perorm operations on the document
            }
        }
    }
}




