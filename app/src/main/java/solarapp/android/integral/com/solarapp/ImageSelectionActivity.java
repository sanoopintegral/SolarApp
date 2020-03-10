package solarapp.android.integral.com.solarapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import solarapp.android.integral.com.solarapp.databinding.ActivityImageSelectionBinding;

public class ImageSelectionActivity extends AppCompatActivity {

    private static final int CAMERA_CAPTURE_REQ = 200;
    ActivityImageSelectionBinding activityImageSelectionBinding;

    File savingfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageSelectionBinding = ActivityImageSelectionBinding.inflate(getLayoutInflater());
        setContentView(activityImageSelectionBinding.getRoot());

        activityImageSelectionBinding.btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCameraIntent();

            }
        });


    }

    private void openCameraIntent() {

        Intent cameracaptureintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = createFileToSave();
        Uri filesavinguri = FileProvider.getUriForFile(this, "solarapp.android.integral.com.solarapp.fileprovider", file);
        cameracaptureintent.putExtra(MediaStore.EXTRA_OUTPUT, filesavinguri);
        if (cameracaptureintent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(cameracaptureintent, CAMERA_CAPTURE_REQ);
        }
    }

    private File createFileToSave() {

        File picturedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String name = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        name = simpleDateFormat.format(new Date());


        savingfile = new File(picturedirectory, "JPEG_" + name + ".jpg");
        try {
            savingfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Success you are here..", Toast.LENGTH_LONG).show();
        return savingfile;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_CAPTURE_REQ) {

                if (savingfile != null) {

                    //thumbnail image shownn
//                        activityImageSelectionBinding.imgSelected.setImageURI(filesavinguri);
                    BitmapFactory.Options bmpoptions = new BitmapFactory.Options();
                    bmpoptions.inJustDecodeBounds = false;
                    bmpoptions.inSampleSize = 10;
                    Bitmap bitmap = BitmapFactory.decodeFile(savingfile.getPath(), bmpoptions);
                    activityImageSelectionBinding.imgSelected.setImageBitmap(bitmap);


                    addImageToGallery();
                }
            }
        }
    }

    private void addImageToGallery() {

        Intent galleryaddintent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        galleryaddintent.setData(Uri.parse(savingfile.getPath()));
        sendBroadcast(galleryaddintent);
    }
}
