package com.dream.example.data;

import com.dream.example.data.entity.Gank;

import java.util.List;

/**
 * 干货数据集合对象
 */
public class GankData extends BaseData {

    private List<Gank> results;

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }
}
