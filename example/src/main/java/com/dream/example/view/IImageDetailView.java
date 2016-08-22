package com.dream.example.view;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Description: IImageDetailView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IImageDetailView extends IAppBaseView {

    void display(String url);

    void display(List<String> urlList);

    void display(List<String> urlList, int index);

    void addImage(PhotoView image);
}
