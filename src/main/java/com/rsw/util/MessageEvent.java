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


public class MessageEvent {

    private int    code;
    private String message;

    public static final int CODE_LOGING_SUCCESS               = 1028;
    public static final int CODE_LOGINGOUT_SUCCESS               = 1029;
    public static final int CODE_UPDATE_COLLECTION_STATE               = 1030;
    public static final int CODE_UPDATENICK_SUCCESS               = 1031;
    public static final int CODE_REGIEST_SUCCESS               = 1032;
    public static final int CODE_ADDSHIHUA_SUCCESS               = 1033;


    public MessageEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
