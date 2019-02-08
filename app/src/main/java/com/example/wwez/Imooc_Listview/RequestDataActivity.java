package com.example.wwez.Imooc_Listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.wwez.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestDataActivity extends AppCompatActivity {
    LessonResult lessonResult = new LessonResult();
    ListView listView;
    int page = 1;
    int mtotalItemCount;
    int mlastItemCount;
    boolean check;
    RequestDataAdapter requestDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);

        listView = findViewById(R.id.main_list_view);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footer = layoutInflater.inflate(R.layout.main_list_view_footer, null);
        listView.addFooterView(footer);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(mtotalItemCount == mlastItemCount && scrollState == SCROLL_STATE_IDLE) {
                    if(!check) {
                        check = true;
                        page++;
                        new RequestDataAsyncTask().execute(page);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mtotalItemCount = totalItemCount;
                mlastItemCount = firstVisibleItem + visibleItemCount;
            }
        });
        new RequestDataAsyncTask().execute(page);
    }

    public class RequestDataAsyncTask extends AsyncTask<Integer, Void, LessonResult> {

        @Override
        protected LessonResult doInBackground(Integer... page) {
            return request("http://www.imooc.com/api/teacher?type=2&page="+page[0]);
        }

        private LessonResult request(String urlString) {
            try {
                URL url = new URL(urlString);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(30000);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK) {
                        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line=bufferedReader.readLine())!= null) {
                            sb.append(line);
                        }
                        JSONObject jsonObject = new JSONObject(sb.toString());
                        lessonResult.setmStatus(jsonObject.getInt("status"));
                        lessonResult.setmMessage(jsonObject.getString("msg"));
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        List<LessonInfo> lessonInfos = new ArrayList<>();
                        for (int index = 0; index < dataArray.length(); index++) {
                            JSONObject tempObj = (JSONObject)dataArray.get(index);
                            final String name = tempObj.getString("name");
                            final String iconUrl = tempObj.getString("picSmall");
                            URL imageUrl =new URL(iconUrl);
                            Bitmap bm = BitmapFactory.decodeStream(imageUrl.openStream());
                            LessonInfo lessonInfo = new LessonInfo();
                            lessonInfo.setmName(name);
                            lessonInfo.setmIconUrl(bm);
                            lessonInfos.add(lessonInfo);
                        }
                        lessonResult.setmLessInfos(lessonInfos);
                        check = false;
                        return lessonResult;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LessonResult lessonResult) {
            if(lessonResult == null || lessonResult.getmLessInfos().size()==0) {
              View footer = listView.findViewById(R.id.main_list_view_footer);
              footer.findViewById(R.id.progressBar).setVisibility(View.GONE);
              footer.findViewById(R.id.textView).setVisibility(View.VISIBLE);
            } else {
                if(page == 1) {
                    requestDataAdapter = new RequestDataAdapter(RequestDataActivity.this, lessonResult.getmLessInfos());
                    listView.setAdapter(requestDataAdapter);
                } else{
                    requestDataAdapter.appendData(lessonResult.getmLessInfos());
                    requestDataAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
