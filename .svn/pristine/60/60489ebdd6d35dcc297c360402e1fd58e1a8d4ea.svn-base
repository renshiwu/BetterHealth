package com.rsw.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/22.
 */

public class CpTwoClassBean {
    @Override
    public String toString() {
        return "CpClassBean{" +
                "cpClass=" + list +
                '}';
    }

    private List<CpTwoClass> list;

    public List<CpTwoClass> getCpTwoClass() {
        return list;
    }

    public void setCpTwoClass(List<CpTwoClass> list) {
        this.list = list;
    }

    public static class CpTwoClass implements Serializable {

        @Override
        public String toString() {
            return "CpTwoClass{" +
                    "id=" + id +
                    ", classid=" + classid +
                    ", name='" + name + '\'' +
                    '}';
        }

        private int id;

        private int classid;

        private int twoClassid;

        private String name;

        private String img;

        private String imgpath;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClassid() {
            return this.classid;
        }

        public void setClassid(int classid) {
            this.classid = classid;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTwoClassid() {
            return this.twoClassid;
        }

        public void setTwoClassid(int twoClassid) {
            this.twoClassid = twoClassid;
        }
    }
}
