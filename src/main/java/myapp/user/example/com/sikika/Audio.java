package myapp.user.example.com.sikika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Audio extends AppCompatActivity {


    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private static String audioFilePath;
    private  Button stopPlayingButton;
    private static String mFileName = null;
    private  Button playButton;
    private  Button stopButton;
    private Uri filePath;
    private static final String LOG_TAG = "AudioRecording";
    private  Button recordButton;
    private Button uploadButton;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private boolean isRecording=false;
    StorageReference storageReference;


  // PackageManager packageManager = getPackageManager();
   // List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
   // ListView appList;
  //  List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(Audio.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
//            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
        setContentView(R.layout.activity_audio);

        PackageManager packageManager = getPackageManager();
        //get firebase storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        recordButton = (Button) findViewById(R.id.btnRecord);
        stopButton = (Button) findViewById(R.id.btnStop);
        playButton = (Button) findViewById(R.id.btnPlay);
        stopPlayingButton = (Button) findViewById(R.id.btnstopPlaying);
        uploadButton=(Button)findViewById(R.id.btnUpload);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/AudioRecording.3gp";

//        if (!hasMicrophone()){
//            stopButton.setEnabled(false);
//            playButton.setEnabled(false);
//            recordButton.setEnabled(false);
//            stopPlayingButton.setEnabled(false);
//        } else {
//            audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/myaudio.3gp";
//        }

    }
//        protected boolean hasMicrophone () {
//            PackageManager packageManager = this.getPackageManager();
//            return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
//        }

        public void recordAudio (View view) throws IOException {
            isRecording = true;
            if (CheckPermissions()) {
                stopButton.setEnabled(true);
                playButton.setEnabled(false);
                recordButton.setEnabled(false);
                stopPlayingButton.setEnabled(false);
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(audioFilePath);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                try {

                    mediaRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                mediaRecorder.start();
                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
            }
            else {
                RequestPermissions();
            }
        }

        public void stopAudio (View view){
            if (mediaRecorder!=null) {
                stopButton.setEnabled(false);
                playButton.setEnabled(true);
                recordButton.setEnabled(false);
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
            //if(isRecording){
            //isRecording =false;
            //} else {
//            mediaPlayer.release();
//            mediaPlayer = null;
            //recordButton.setEnabled(true);
            //}
        }

        public void playAudio (View view) throws IOException {
            playButton.setEnabled(false);
            recordButton.setEnabled(false);
            stopButton.setEnabled(true);

            mediaPlayer = new MediaPlayer();
//        mediaPlayer.setDataSource(audioFilePath);
//        mediaPlayer.prepare();
//        mediaPlayer.start();
            try {
                mediaPlayer.setDataSource(mFileName);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        }

        public void uploadAudio(View view){
        if (filePath!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("audio/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Audio.this,"Uploaded",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Audio.this,"Failed" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded"+(int)progress+"%");
                        }
                    });
        }
        }

//    public void setStopPlayingAudio(View view){
//        stopButton.setEnabled(false);
//        playButton.setEnabled(true);
//        stopPlayingButton.setEnabled(false);
//
//        if(isRecording){
//            recordButton.setEnabled(false);
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder =null;
//            isRecording =false;
//        } else {
//            mediaPlayer.release();
//            mediaPlayer = null;
//            recordButton.setEnabled(true);
//        }
//    }
        private void RequestPermissions() {
    ActivityCompat.requestPermissions(Audio.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
}
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    switch (requestCode) {
        case REQUEST_AUDIO_PERMISSION_CODE:
            if (grantResults.length> 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord && permissionToStore) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                }
            }
            break;
    }
}
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

}
