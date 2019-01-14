package com.miamor.Runnables;

import android.content.Context;
import com.miamor.Obj.Globals;
import com.miamor.Obj.MultipartUtility;
import com.miamor.Preferences.Authentication;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Marcot on 8/11/2015.
 */
public class ImageUploadRunnable implements Runnable {
    public static interface ImageUploadInterface{
        public void onStart();
        public void onComplete();
        public void onError(Exception ex);
    }

    int serverResponseCode = 0;
    String sourceFileUri="";
    private String upLoadServerUri = "";

    private ImageUploadInterface imageUploadInterface;
    File sourceFile;
    final String uploadFileName = "service_lifecycle.png";

    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;

    Authentication auth;
    int vendorId=0;
    String custId="";
    String token="";
    Context ctx;

    public ImageUploadRunnable(Context ctx, ImageUploadInterface imageUploadInterface, int vendorId, String sourceFileUri){
        auth=new Authentication(ctx);
        sourceFileUri=sourceFileUri.replace("file:","");
        sourceFile= new File(sourceFileUri);
        this.imageUploadInterface=imageUploadInterface;
        this.vendorId=vendorId;
        this.sourceFileUri=sourceFileUri;
        this.upLoadServerUri= Globals.ServerUrl + "/vendor/MApp_AddVenorPic";
        this.ctx=ctx;
    }

    @Override
    public void run() {
        imageUploadInterface.onStart();

        if(auth.isLogged()) {
            upload();
        }
    }

    private void upload(){
        imageUploadInterface.onStart();

        String charset = "UTF-8";
        String requestURL = upLoadServerUri;

        try {
            Header header=new BasicHeader("Authorization", "Bearer " + auth.read().getToke());
            MultipartUtility multipart = new MultipartUtility(requestURL, charset,header);

            multipart.addFormField("CustId", auth.read().getCustId());
            multipart.addFormField("VendorId",  Integer.toString(vendorId));

            multipart.addFilePart("File", sourceFile);

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
            imageUploadInterface.onComplete();

        } catch (IOException ex) {
            imageUploadInterface.onError(ex);
        }
    }
}
