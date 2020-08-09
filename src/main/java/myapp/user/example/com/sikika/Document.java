package myapp.user.example.com.sikika;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Document extends AppCompatActivity {

    //private static final Object SELECTED_ITEMS = ;
    private TextView notification;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private Uri pdfUri;
    private static final String TAG = "FileUtils";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }
        setContentView(R.layout.activity_document);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        Button selectFile = (Button) findViewById(R.id.selectFile);
        Button upload = (Button) findViewById(R.id.upload);

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null) {
                   // uploadFile(pdfUri);
                } else {
                    Toast.makeText(Document.this, "Select a file..", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void selectPdf() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "choose File");
        startActivityForResult(chooseFile, 86);

//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT); //fetch files
//        startActivityForResult(Intent.createChooser(intent,"Select pdf file"),86);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null) { //if file selection is successful

//            //get uri of selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    }
                    else if(uriString.startsWith("file://")){
                        displayName = myFile.getName();
                    }

                }finally{
                        cursor.close();
                    }
                   // break;
            }
                super.onActivityResult(requestCode,resultCode,data);

//            FileInputStream fis;
//            fis= openFileInput("test.txt");
//            StringBuffer fileContent =new StringBuffer("");
//            byte [] buffer = new byte[1024];
//            while ((n = fis.read(buffer)) != -1){
//                fileContent.append(new String(buffer,0,n));
//            }
//            Log.i(TAG,"Uri = " +uri.toString());
//            try{
//                //ger the file path from the uri
//                final String path = FileUtils.getPath(this,uri);
//                Toast.makeText(Document.this,"File selected"+path,Toast.LENGTH_SHORT).show();
//            }catch (Exception e){
//                Log.e("File selector activity","File select error", e);
//            }
//            String src = uri.getPath();
//
//           // pdfUri =data.getData();
//            notification.setText("A file is selected : " +data.getData().getLastPathSegment());
//        }else {
//            Toast.makeText(Document.this,"Please select a file",Toast.LENGTH_SHORT).show();
//        }
        }
//
//        private void uploadFile (Uri pdfUri){
//            progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setTitle("Uploading File...");
//            progressDialog.setProgress(0);
//            progressDialog.show();
//
//            final String fileName = System.currentTimeMillis() + "";
//            StorageReference storageReference = storage.getReference();
//            storageReference.child("Uploads").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//                    DatabaseReference reference = database.getReference();
//                    reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(Document.this, "File uploaded Succesfully.", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                Toast.makeText(Document.this, "File  not uploaded.", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Document.this, "File  not uploaded Succesfully.", Toast.LENGTH_SHORT).show();
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                    progressDialog.setProgress(currentProgress);
//                }
//            });
//        }


//    final int takeFlags = getIntent().getFlags()&(Intent.FLAG_GRANT_READ_URI_PERMISSION
//    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //checkout for freshest data

        // @Override
        // public ContentResolver getContentResolver().takePersistableUriPermission(uri,takeFlags);
        // }
    }


}


