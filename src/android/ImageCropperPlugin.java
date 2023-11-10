package com.mrustudio.plugin;
// package com.mrustudio.plugin.ImageCropperPlugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

// import android.graphics.Color;

// import com.yalantis.ucrop.UCrop;
// import com.theartofdev.edmodo.cropper.CropImage;
// import com.theartofdev.edmodo.cropper.CropImageView;
//2023 update -> https://github.com/CanHub/Android-Image-Cropper
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ImageCropperPlugin extends CordovaPlugin {
    private CallbackContext callbackContext;
    private Uri inputUri;
    private Uri outputUri;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
      if (action.equals("cropImage")) {
          String imagePath = args.getString(0);
        //   String sourceType = args.getString(1);
        //   JSONObject jsonObject = new JSONObject(sourceType);
        //   String targetSourceType  = jsonObject.getString("sourceType");

        //   this.inputUri = targetSourceType.equals("1") ? Uri.parse(imagePath) : Uri.parse("file:///"+imagePath);
          this.inputUri = Uri.parse(imagePath);
          this.outputUri = Uri.fromFile(new File(getTempDirectoryPath() + "/" + System.currentTimeMillis()+ "-cropped.jpg"));

          PluginResult pr = new PluginResult(PluginResult.Status.NO_RESULT);
          pr.setKeepCallback(true);
          callbackContext.sendPluginResult(pr);
          this.callbackContext = callbackContext;

          cordova.setActivityResultCallback(this);
          CropImage.activity(this.inputUri)
                //   .setActivityMenuIconColor(Color.GREEN)
                //   .setAllowRotation(true)
                //   .setActivityTitle("Edit Image")
                  .start(cordova.getActivity());

          return true;
      }
      return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intent);
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = result.getUri();//CropImage.getOutput(intent);
                    this.callbackContext.success("file://" + imageUri.getPath()); // + "?" + System.currentTimeMillis()
                    this.callbackContext = null;
                    //   ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                    //   Toast.makeText(
                    //           this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                    //       .show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                try {
                    JSONObject err = new JSONObject();
                    err.put("message", "Error on cropping");
                    err.put("code", String.valueOf(resultCode));
                    this.callbackContext.error(err);
                    this.callbackContext = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private String getTempDirectoryPath() {
        File cache = null;

        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/" + cordova.getActivity().getPackageName() + "/cache/");
        }
        // Use internal storage
        else {
            cache = cordova.getActivity().getCacheDir();
        }

        // Create the cache directory if it doesn't exist
        cache.mkdirs();
        return cache.getAbsolutePath();
    }

    public Bundle onSaveInstanceState() {
        Bundle state = new Bundle();

        if (this.inputUri != null) {
            state.putString("inputUri", this.inputUri.toString());
        }

        if (this.outputUri != null) {
            state.putString("outputUri", this.outputUri.toString());
        }

        return state;
    }

    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {

        if (state.containsKey("inputUri")) {
            this.inputUri = Uri.parse(state.getString("inputUri"));
        }

        if (state.containsKey("outputUri")) {
            this.inputUri = Uri.parse(state.getString("outputUri"));
        }

        this.callbackContext = callbackContext;
    }
}
