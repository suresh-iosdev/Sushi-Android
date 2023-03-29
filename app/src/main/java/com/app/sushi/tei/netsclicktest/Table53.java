package com.app.sushi.tei.netsclicktest;

import java.io.Serializable;

public class Table53 implements Serializable {

    private String data;

    public Table53(String data) {
        this.data = data;
    }

    public void parse() {
        String subData = data;
        if(!data.startsWith("53") && data.length()==183) {
            int totalLength = Integer.valueOf(data.substring(0, 3));
            System.out.println("LEN=" + totalLength);
            subData = data.substring(3,3+totalLength);
            System.out.println(subData);
            System.out.println(subData.length());

            totalLength = Integer.valueOf(subData.substring(2, 5));
            System.out.println("LEN=" + totalLength);
            subData = subData.substring(5+totalLength,subData.length());

            //String table53TAG = "53";
            //String table53Data = "";
            //table53Data = subData.substring(subData.indexOf(table53TAG),subData.length());
            //subData = table53Data;
        }
        data = subData;
    }

    public String getData() {
        return data;
    }
}
