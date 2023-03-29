package com.app.sushi.tei.Model.Home;

import java.io.Serializable;

/**
 * Created by root on 30/3/18.
 */

public class SlotTime implements Serializable {

        private String mStartTime="";
        private String mEndTime="";

        public String getmStartTime() {
            return mStartTime;
        }

        public void setmStartTime(String mStartTime) {
            this.mStartTime = mStartTime;
        }

        public String getmEndTime() {
            return mEndTime;
        }

        public void setmEndTime(String mEndTime) {
            this.mEndTime = mEndTime;
        }
}
