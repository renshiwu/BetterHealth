package com.rsw.bean;

/**
 * Created by Administrator on 2018/8/19.
 */

public class UserInfo {
    @Override
    public String toString() {
        return "UserInfo{" +
                "tbUserInfo=" + tbUserInfo +
                ", state=" + state +
                '}';
    }

    /**
     * tbUserInfo : {"id":1,"headImg":"https://renshwiu.oss-cn-hangzhou.aliyuncs.com/imgs/head_img/5584166d9bd4b22f54a82fcba0329c62.jpg","userId":"5584166d9bd4b22f54a82fcba0329c62"}
     * state : true
     */

    private TbUserInfoBean tbUserInfo;
    private boolean        state;

    public TbUserInfoBean getTbUserInfo() {
        return tbUserInfo;
    }

    public void setTbUserInfo(TbUserInfoBean tbUserInfo) {
        this.tbUserInfo = tbUserInfo;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public static class TbUserInfoBean {
        @Override
        public String toString() {
            return "TbUserInfoBean{" +
                    "id=" + id +
                    ", headImg='" + headImg + '\'' +
                    ", userId='" + userId + '\'' +
                    ", nackName='" + nackName + '\'' +
                    ", background='" + background + '\'' +
                    '}';
        }

        /**
         * id : 1
         * headImg : https://renshwiu.oss-cn-hangzhou.aliyuncs.com/imgs/head_img/5584166d9bd4b22f54a82fcba0329c62.jpg
         * userId : 5584166d9bd4b22f54a82fcba0329c62
         */

        private int    id;
        private String headImg;
        private String userId;
        private String nackName;

        private String background;

        public String getNackName() {
            return nackName;
        }

        public void setNackName(String nackName) {
            this.nackName = nackName;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
