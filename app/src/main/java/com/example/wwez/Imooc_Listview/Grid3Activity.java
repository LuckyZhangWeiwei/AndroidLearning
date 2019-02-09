package com.example.wwez.Imooc_Listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wwez.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Grid3Activity extends AppCompatActivity {

    private GridView gridView;
    private List<String> imgList;
    private List<ImageInfo>imageInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid3);

        initData();
        initUI();
    }

    private void initUI() {
        gridView = findViewById(R.id.gridview);
        GridAdapter3 adapter = new GridAdapter3(this, imageInfoList);
        gridView.setAdapter(adapter);
//        ImageLoaderTask task = new ImageLoaderTask(adapter);
//        task.execute();
    }

    Bitmap getImagefromNetWork(String path)
    {
        try {

            URL url=new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10*1000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void initData() {
        imgList = new ArrayList<String>();
        imgList.add("http://img5.duitang.com/uploads/item/201406/26/20140626164837_dzKds.jpeg");
        imgList.add("http://img2.imgtn.bdimg.com/it/u=3980629563,3881837630&fm=21&gp=0.jpg");
        imgList.add("http://img5q.duitang.com/uploads/item/201505/08/20150508155052_XJaNW.jpeg");
        imgList.add("http://img4.duitang.com/uploads/item/201407/02/20140702105736_FdN5P.jpeg");
        imgList.add("http://img2.imgtn.bdimg.com/it/u=2866652161,3841912673&fm=21&gp=0.jpg");
        imgList.add("http://img4.imgtn.bdimg.com/it/u=883757693,2063816225&fm=21&gp=0.jpg");
        imgList.add("http://cdn.duitang.com/uploads/item/201309/26/20130926110955_QtUdX.jpeg");
        imgList.add("http://zjimg.5054399.com/allimg/160815/14_160815161625_9.jpg");
        imgList.add("http://i-7.vcimg.com/trim/09ce7067d2467c54cf05bbd271ee3ec8430415/trim.jpg");

        imageInfoList = new ArrayList<ImageInfo>();
        for (int i = 0; i < 9; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImagePath(imgList.get(i));
            imageInfo.setText("图片" + i);
            imageInfoList.add(imageInfo);
        }
    }

    class ImageInfo {
        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        String imagePath;
        Bitmap bitmap;
        String text;
    }

    class GridAdapter3 extends BaseAdapter {
        private Context context;
        private List<ImageInfo> imageInfos;

        public GridAdapter3(Context context, List<ImageInfo> imageInfos) {
            this.context = context;
            this.imageInfos = imageInfos;
        }

        @Override
        public int getCount() {
            return imageInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return imageInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ImageInfo imageInfo = imageInfos.get(position);
            if(convertView == null) {
                convertView = View.inflate(context, R.layout.item_gridview2, null);
                holder = new ViewHolder();
                holder.img_appIcon = convertView.findViewById(R.id.img_appIcon);
                holder.tv_appName = convertView.findViewById(R.id.tv_appName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//           if(imageInfo.getBitmap() == null) {
//                holder.img_appIcon.setImageResource(R.mipmap.ic_launcher);
//           } else {
//                holder.img_appIcon.setImageBitmap(imageInfo.getBitmap());
//           }

            Glide.with(context).load(imageInfo.getImagePath()).placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.img_appIcon);

            holder.tv_appName.setText(imageInfo.getText());
            return convertView;
        }

        class ViewHolder {
            ImageView img_appIcon;
            TextView tv_appName;
        }
    }

    class ImageLoaderTask extends AsyncTask<Void, Void, Void> {

        public ImageLoaderTask(GridAdapter3 adapter) {
            this.adapter = adapter;
        }

        GridAdapter3 adapter;

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<this.adapter.getCount();i++) {
                ImageInfo imageInfo =(ImageInfo) adapter.getItem(i);
                imageInfo.setBitmap(getImagefromNetWork(imageInfo.getImagePath()));
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
        }
    }
}
