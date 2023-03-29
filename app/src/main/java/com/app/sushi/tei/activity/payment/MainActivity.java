package com.app.sushi.tei.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sushi.tei.R;

import co.omise.android.ui.AuthorizingPaymentActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ProductListAdapter listAdapter = null;

    private static int AUTHORIZING_PAYMENT_REQUEST_CODE = 0x3D5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pactivity_main);

        listAdapter = new ProductListAdapter(repository().all());

        ListView productList = (ListView) findViewById(R.id.list_products);
        productList.setAdapter(listAdapter);
        productList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product product = (Product) listAdapter.getItem(position);
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra(CheckoutActivity.EXTRA_PRODUCT_ID, product.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_authorizing_payment_action) {
            Intent intent = new Intent(this, AuthorizingPaymentActivity.class);
            intent.putExtra(AuthorizingPaymentActivity.EXTRA_AUTHORIZED_URLSTRING, "https://pay.omise.co/offsites/");
            intent.putExtra(AuthorizingPaymentActivity.EXTRA_EXPECTED_RETURN_URLSTRING_PATTERNS, new String[] {"http://www.example.com/orders"} );
            startActivityForResult(intent, MainActivity.AUTHORIZING_PAYMENT_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.AUTHORIZING_PAYMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            String url = data.getStringExtra(AuthorizingPaymentActivity.EXTRA_RETURNED_URLSTRING);
            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
