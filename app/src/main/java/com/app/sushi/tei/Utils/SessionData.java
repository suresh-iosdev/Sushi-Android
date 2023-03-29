package com.app.sushi.tei.Utils;

import com.app.sushi.tei.fragment.subcategory.SubcategoryFragment;

public class SessionData {

   public static SessionData sessionData;

    public static SessionData getInstance() {
        if (sessionData==null){
            return new SessionData();
        }else {
            return sessionData;
        }
    }

    public static SubcategoryFragment subcategoryFragment;

    public  SubcategoryFragment getSubcategoryFragment() {
        return subcategoryFragment;
    }

    public  void setSubcategoryFragment(SubcategoryFragment subcategoryFragment) {
        SessionData.subcategoryFragment = subcategoryFragment;
    }
}
