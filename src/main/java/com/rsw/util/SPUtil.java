package com.rsw.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZXB on 2016/12/9.
 */

public class SPUtil {

    public static void deleteAll(Context con, String configName) {
        SharedPreferences sp = con.getSharedPreferences(configName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private static SharedPreferences getSharedPreferances(Context context) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * obj仅限int,float,boolean,long,String类型
     *
     * @param context
     * @param key
     * @param obj
     */
    public static void put(Context context, String key, Object obj) {
        SharedPreferences sp = getSharedPreferances(context);
        Editor editor = sp.edit();
        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (int) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (long) obj);
        }
        editor.commit();
    }

    public static void put(Context context, String key, Set<String> set) {
        SharedPreferences sp = getSharedPreferances(context);
        Editor editor = sp.edit();
        editor.putStringSet(key, set);
        editor.commit();
    }


    public static String getString(Context context, String key) {
        SharedPreferences sp = getSharedPreferances(context);
        String result = sp.getString(key, "");
        return result;
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sp = getSharedPreferances(context);
        int result = sp.getInt(key, 0);
        return result;
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sp = getSharedPreferances(context);
        long result = sp.getLong(key, 0L);
        return result;
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSharedPreferances(context);
        boolean result = sp.getBoolean(key, false);
        return result;
    }

    public static Set<String> getSet(Context context, String key) {
        SharedPreferences sp = getSharedPreferances(context);
        Set<String> set = sp.getStringSet(key, new HashSet<String>());
        return set;
    }

    public static void saveInfo(Context context, String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map itemMap = datas.get(i);
            Iterator iterator = itemMap.entrySet().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                try {
                    object.put((String) entry.getKey(), entry.getValue());
                } catch (JSONException e) {

                }
            }
            mJsonArray.put(object);

        }
        SharedPreferences sharedPreferances = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Editor edit = sharedPreferances.edit();
        edit.putString(key, mJsonArray.toString());
        edit.commit();
    }

    public static void saveInfoToJson(Context context, String key, String s) {
        SharedPreferences sharedPreferances = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Editor edit = sharedPreferances.edit();
        edit.putString(key, s.toString());
        edit.commit();
    }

    public static JSONArray getInfoFromJson(Context context, String key) {
        SharedPreferences sp1 = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String result1 = sp1.getString(key, "");
        try {
            JSONArray array = new JSONArray(result1);
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, String>> getInfo(Context context, String key) {
        List<Map<String, String>> datas = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map itemMap = new HashMap();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {
        }
        return datas;
    }

    public void saveMapMessage(Context context, String key, Map<Object, Object> map) {
        JSONArray jsonArray = new JSONArray();
        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
        JSONObject jsonObject = new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            try {
                jsonObject.put((String) entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        jsonArray.put(jsonObject);

        SharedPreferences sharedPreferances = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Editor edit = sharedPreferances.edit();
        edit.putString(key, jsonArray.toString());
        edit.commit();  //将数据持久化到存储介质

    }

    public static Map<Object, Object> getMapMessage(Context context, String key) {
        Map<Object, Object> datas = new HashMap<>();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String result = sp.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map itemMap = new HashMap();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        datas.put(name, value);
                    }
                }
            }
        } catch (JSONException e) {

        }

        return datas;
    }

    /**
     * delete key
     *
     * @param context
     * @param key
     */
    public static void removeParam(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
