package com.rsw.bean;

import java.util.List;

/**
 * Created by RSW on 2018/7/30.
 * com.rsw.bean
 */

public class ShequBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 6
         * content : 测试测量了了了来咯哦哦默默默默咯他哦了老K了了了了了叽叽叽叽理解理解了
         * headImg : https://renshwiu.oss-cn-hangzhou.aliyuncs.com/imgs/head_img/5e9d8361befac25dc04a1c781c51c9bf.jpg
         * img : https://renshwiu.oss-cn-hangzhou.aliyuncs.com/imgs/shihua/e9e078bddc4e474ba94a5445b41c8d77.jpg
         * nickName : 任仕
         * userId : 5e9d8361befac25dc04a1c781c51c9bf
         */

        private int id;
        private String content;
        private String headImg;
        private String img;
        private String nickName;
        private String userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
