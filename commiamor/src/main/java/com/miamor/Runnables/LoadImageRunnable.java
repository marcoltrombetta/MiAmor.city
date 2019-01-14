package com.miamor.Runnables;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marcot on 8/10/2015.
 */

public class LoadImageRunnable implements Runnable{
    public static interface ImageInteface{
        void loadComplete(Bitmap bmp, ImageView v);
    }

    private ImageInteface imageInteface;
    private String url="";
    private ImageView v;
    private int width=100;
    private int height=100;

    public LoadImageRunnable(ImageInteface imageInteface, String url, ImageView v){
        this.imageInteface=imageInteface;
        this.url=url;
        this.v=v;
    }

    public LoadImageRunnable(ImageInteface imageInteface, String url, ImageView v, int width, int height){
        this.imageInteface=imageInteface;
        this.url=url;
        this.v=v;
        this.width=width;
        this.height=height;
    }

    @Override
    public void run() {
            Bitmap bmp =null;
            try {
                URL url1 = new URL(url);
                bmp = decodeImageFile(url1,this.width,this.height);//BitmapFactory.decodeStream(url1.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageInteface.loadComplete(bmp,v);
        }

    private static Bitmap decodeImageFile(URL url1,int WIDTH,int HIGHT){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(url1.openConnection().getInputStream(),null,o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH=WIDTH;
            final int REQUIRED_HIGHT=HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;

           Bitmap bMap = BitmapFactory.decodeStream(url1.openConnection().getInputStream(), null, o2);

            return bMap;
        } catch (FileNotFoundException e) {
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
