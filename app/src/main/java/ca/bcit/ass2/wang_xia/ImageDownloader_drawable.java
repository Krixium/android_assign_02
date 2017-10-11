package ca.bcit.ass2.wang_xia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 09/10/2017.
 */

public class ImageDownloader_drawable  extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakRef;
    private final WeakReference<View> parentContainer;

    public ImageDownloader_drawable(ImageView imgView, View parentContainer){
        imageViewWeakRef = new WeakReference<ImageView>(imgView);
        this.parentContainer = new WeakReference<View>(parentContainer);
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    @Nullable
    private Bitmap downloadBitmap(String url_str) {
        HttpURLConnection urlConnection = null;
        try {
            URL url_obj = new URL(url_str);
//            Log.d("IMAGEDOWNLOADER CLASS", url_str);
//            URL url_obj = new URL("http://flintstones.zift.ca/images/flintstone/dino.png");
            urlConnection = (HttpURLConnection) url_obj.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode !=  HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.d("IMAGEDOWNLOADER CLASS", "Error downloading image from " + url_str);
            Log.d("IMAGEDOWNLOADER CLASS", e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewWeakRef != null) {
            ImageView imageView = imageViewWeakRef.get();
            if (imageView != null) {
                if (bitmap != null) {
                    Log.d("INSIDE DOWNLOADER", "img successful");
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.d("INSIDE DOWNLOADER", "img not successful");
                    //Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.placeholder);
                    //imageView.setImageDrawable(placeholder);
                    imageView.setImageResource(R.drawable.placeholder);
                }
            }
        }
    }
}
