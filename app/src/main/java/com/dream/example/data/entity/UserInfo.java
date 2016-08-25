package com.dream.example.data.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.yapp.core.data.entity.DataEntity;
import org.yapp.db.annotation.Column;
import org.yapp.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserInfo <br>
 * Description: 用户信息. <br>
 * Date: 2016-8-18 上午09:49:39 <br>
 *
 * @author ysj
 * @version 1.0
 * @since JDK 1.7
 */
@Table(name = "card_user")
public class UserInfo extends DataEntity<UserInfo> implements Parcelable, Comparable<UserInfo> {
    private static final long serialVersionUID = 1L;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "name")
    private String name;// 姓名

    @Column(name = "name")
    private String nickname;// 昵称

    @Column(name = "position")
    private String position;// 职位

    @Column(name = "company")
    private String company;// 公司

    @Column(name = "url")
    private String url;// 网址

    @Column(name = "address")
    private String address; // 地址

    @Column(name = "qqList")
    private List<Map<String, Object>> qqList = new ArrayList<>(); // QQ列表

    @Column(name = "phoneList")
    private List<Map<String, Object>> phoneList = new ArrayList<>(); // 手机列表

    @Column(name = "emailList")
    private List<Map<String, Object>> emailList = new ArrayList<>(); // 邮箱列表

    private String sortKey = "";// 排序Key
    private String nameSimplePy = "";// 简拼
    private String nameFullPy = "";// 全拼
    private String simpleNumber = "";

    private String comSimplePy = "";// 公司简拼
    private String comFullPy = "";// 公司全拼
    private String posSimplePy = "";// 职位简拼
    private String posFullPy = "";// 职位全拼
    private Bitmap photo;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Map<String, Object>> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Map<String, Object>> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Map<String, Object>> getQqList() {
        return qqList;
    }

    public void setQqList(List<Map<String, Object>> qqList) {
        this.qqList = qqList;
    }

    public List<Map<String, Object>> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Map<String, Object>> emailList) {
        this.emailList = emailList;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getNameSimplePy() {
        return nameSimplePy;
    }

    public void setNameSimplePy(String nameSimplePy) {
        this.nameSimplePy = nameSimplePy;
    }

    public String getNameFullPy() {
        return nameFullPy;
    }

    public void setNameFullPy(String nameFullPy) {
        this.nameFullPy = nameFullPy;
    }

    public String getSimpleNumber() {
        return simpleNumber;
    }

    public void setSimpleNumber(String simpleNumber) {
        this.simpleNumber = simpleNumber;
    }

    public String getComSimplePy() {
        return comSimplePy;
    }

    public void setComSimplePy(String comSimplePy) {
        this.comSimplePy = comSimplePy;
    }

    public String getComFullPy() {
        return comFullPy;
    }

    public void setComFullPy(String comFullPy) {
        this.comFullPy = comFullPy;
    }

    public String getPosSimplePy() {
        return posSimplePy;
    }

    public void setPosSimplePy(String posSimplePy) {
        this.posSimplePy = posSimplePy;
    }

    public String getPosFullPy() {
        return posFullPy;
    }

    public void setPosFullPy(String posFullPy) {
        this.posFullPy = posFullPy;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public int compareTo(UserInfo another) {
        return this.getSortKey().compareTo(another.getSortKey());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeString(position);
        dest.writeString(company);
        dest.writeString(url);
        dest.writeString(address);
        dest.writeList(qqList);
        dest.writeList(phoneList);
        dest.writeList(emailList);
        dest.writeString(sortKey);
        dest.writeString(nameSimplePy);
        dest.writeString(nameFullPy);
        dest.writeString(simpleNumber);
        dest.writeString(comSimplePy);
        dest.writeString(comFullPy);
        dest.writeString(posSimplePy);
        dest.writeString(posFullPy);
        dest.writeValue(photo);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public UserInfo(Parcel in) {
        name = in.readString();
        nickname = in.readString();
        position = in.readString();
        company = in.readString();
        url = in.readString();
        address = in.readString();
        qqList = in.readArrayList(ArrayList.class.getClassLoader());
        phoneList = in.readArrayList(ArrayList.class.getClassLoader());
        emailList = in.readArrayList(ArrayList.class.getClassLoader());
        sortKey = in.readString();
        nameSimplePy = in.readString();
        nameFullPy = in.readString();
        simpleNumber = in.readString();
        comSimplePy = in.readString();
        comFullPy = in.readString();
        posSimplePy = in.readString();
        posFullPy = in.readString();
        photo = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    public UserInfo() {
    }
}
