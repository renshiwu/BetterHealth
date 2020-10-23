/*
 * Copyright 2016. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rsw.util;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <br /> author: chenshufei
 * <br /> date: 15/12/12
 * <br /> email: chenshufei2@sina.com
 */
public class DateUtils {
    /**
     * 调整Picker布局
     *
     * @param frameLayout
     */
    public static void resizePicker(FrameLayout frameLayout) {
        List<NumberPicker> numberPickers = findNumberPicker(frameLayout);
        for (NumberPicker numberPicker : numberPickers) {
            resizeNumberPicker(numberPicker);
        }
    }

    /**
     * 获取ViewGroup中的NumberPicker组件
     *
     * @param viewGroup
     * @return
     */
    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> numberPickers = new ArrayList<>();
        View child;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    numberPickers.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return numberPickers;
    }

    /**
     * 调整NumberPicker大小
     *
     * @param numberPicker
     */
    private static void resizeNumberPicker(NumberPicker numberPicker) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(15, 0, 15, 0);
        numberPicker.setLayoutParams(params);
    }

    public int getTodayNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String dday = sdf.format(new Date());
        return Integer.valueOf(dday);
    }


    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }



    @SuppressLint("SimpleDateFormat")
    public static String StringToDate(String dateStr) {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = null;
        try {
            dt = timesdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        if (dt != null) {
            c.setTime(dt);
        }
        String res = "";
        long temptime = (now.getTimeInMillis() - c.getTimeInMillis()) / 1000 / 60;
        if (0 < temptime && temptime < 6 && !dateStr.endsWith("00:00:00")) {
            res = "刚刚";
            return res;
        }

        if (6 <= temptime && temptime < 60 && !dateStr.endsWith("00:00:00")) {
            res = temptime + "分钟前";
            return res;
        }
        if (0 < (temptime / 60) && (temptime / 60) < 1 && !dateStr.endsWith("00:00:00")) {
            res = temptime + "分钟前";
            return res;
        }
        temptime = temptime / 60;
        if (1 < temptime && temptime < 24 && !dateStr.endsWith("00:00:00")) {
            res = temptime + "小时前";
            return res;
        }

        String tempstr = "";
        if (dateStr.endsWith("00:00:00")) {
            dateStr = dateStr.substring(0, 10);
        }
        if (dateStr.length() > 16) {
            tempstr = dateStr.substring(10, 16);
            dateStr = dateStr.substring(0, 16);
            //yyyy-MM-dd HH:mm:ss
        }
        SimpleDateFormat tempsdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dt = tempsdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        now = Calendar.getInstance();
        c = Calendar.getInstance();
        if (dt != null) {
            c.setTime(dt);
        }
        if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
            res = "今天";
            if (tempstr != "") {
                res = tempstr;
            }
            return res;
        }

        now.add(Calendar.DATE, -1);
        if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
            res = "昨天";
            return res;
        }
        now.add(Calendar.DATE, -1);
        if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
            res = "前天";
            return res;
        }
        if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && res != tempstr
                && !"".equals(res) && res != "昨天" && res != "前天") {
            res = dateStr.replace(String.valueOf(c.get(Calendar.YEAR)) + "-",
                    "");
            return res;
        }
        if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && "".equals(res)
                && res != "昨天" && res != "前天") {
            res = dateStr.replace(String.valueOf(c.get(Calendar.YEAR)) + "-",
                    "");
            return res;
        }
        if (c.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
            res = dateStr;
        }

        return res;
    }
}
