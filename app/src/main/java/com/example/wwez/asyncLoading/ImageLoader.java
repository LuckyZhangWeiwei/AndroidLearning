package com.example.wwez.asyncLoading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.wwez.myapplication.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ImageLoader {
//    private ImageView imageView;
//    private String url;
    private LruCache<String, Bitmap> mCache;
    private ListView mListView;
    private Set<NewsAsyncTask> mTasks;

    public ImageLoader(ListView listview) {
        mListView = listview;
        mTasks = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url, Bitmap bitmap){
        if(getBitmapFromCache(url) == null){
            mCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(imageView.getTag().equals(url)){
//                imageView.setImageBitmap((Bitmap) msg.obj);
//            }
//        }
//    };
//    public void showImageByThread(ImageView imageView, final String url){
//        this.imageView = imageView;
//        this.url = url;
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Bitmap bitmap = getBitMapFromUrl(url);
//                    Message message = Message.obtain();
//                    message.obj = bitmap;
//                    handler.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

    private Bitmap showBitMapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageview, String url){
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap == null){
            imageview.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageview.setImageBitmap(bitmap);
        }
    }

    //加载从start 到 end 的图片
    public void loadImages(int start, int end){
        for(int i=start; i<end; i++ ){
            String url = NewsAdapter.URLs[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if(bitmap == null){
               NewsAsyncTask task = new NewsAsyncTask(url);
               task.execute(url);
               mTasks.add(task);
            } else {
                ImageView imageview = mListView.findViewWithTag(url);
                imageview.setImageBitmap(bitmap);
            }
        }
    }

    public void cancelAllTask() {
        if(mTasks != null) {
            for(NewsAsyncTask task :mTasks){
                task.cancel(false);
            }
        }
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{
        private String url;
        public NewsAsyncTask(String url ){
            this.url = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = showBitMapFromUrl(params[0]);
            if(bitmap != null)
            {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = mListView.findViewWithTag(url);
            if(imageView != null && bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
    }
}
