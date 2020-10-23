package com.rsw.bean;

import java.util.List;

/**
 * The persistent class for the tb_cp_hot database table.
 */


public class HotBean {
    @Override
    public String toString() {
        return "HotBean{" +
                "list=" + list +
                '}';
    }

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * id : 1
         * name : 养生
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}











