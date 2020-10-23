package com.rsw.bean;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the tb_cp_class database table.
 */

public class CpClassBean {

    @Override
    public String toString() {
        return "CpClassBean{" +
                "cpClass=" + list +
                '}';
    }

    private List<CpClass> list;

    public List<CpClass> getCpClass() {
        return list;
    }

    public void setCpClass(List<CpClass> list) {
        this.list = list;
    }

    public static class CpClass implements Serializable {

        @Override
        public String toString() {
            return "CpClass{" +
                    "id=" + id +
                    ", classid=" + classid +
                    ", name='" + name + '\'' +
                    '}';
        }

        private int id;

        private int classid;

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

    }


}