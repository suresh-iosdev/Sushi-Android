package com.app.sushi.tei.activity.payment;

import android.app.Activity;

public abstract class BaseActivity extends Activity {
    private final ProductRepository repository = new ProductRepository();

    protected ProductRepository repository() {
        return repository;
    }
}
