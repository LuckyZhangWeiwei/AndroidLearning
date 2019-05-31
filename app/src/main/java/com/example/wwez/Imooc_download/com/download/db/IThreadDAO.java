package com.example.wwez.Imooc_download.com.download.db;

import com.example.wwez.Imooc_download.com.download.entry.ThreadInfo;

import java.util.List;

public interface IThreadDAO {

    public void insertThread(ThreadInfo threadInfo);

    public void deleteThread(String url, int thread_id);

    public void updateThread(String url, int thread_id, long finished);

    public List<ThreadInfo> getThreads(String url);

    public boolean isExists(String url, int thread_id);
}
