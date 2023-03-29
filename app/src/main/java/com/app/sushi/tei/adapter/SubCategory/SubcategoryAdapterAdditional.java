package com.app.sushi.tei.adapter.SubCategory;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Model.CompassAllProduct.ProductsItem;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.R;
import com.app.sushi.tei.dialog.CartDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryAdapterAdditional extends RecyclerView.Adapter<SubcategoryAdapterAdditional.ViewHolder> {
    private Context mContext;

    private    OnItemClickListeners mClickListener;
    private ArrayList<ProductsItem> subCategoryArrayList;
    private List<Products> productsList;
    private String imageurl;
    private String stock;

    public SubcategoryAdapterAdditional(Context mContext, List<Products> productsList, String imageurl) {
        this.mContext = mContext;
        this.productsList=productsList;
        this.imageurl=imageurl;
    }


    @Override
    public SubcategoryAdapterAdditional.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.adapter_subcategory_additional,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubcategoryAdapterAdditional.ViewHolder viewHolder, int i) {



         viewHolder.productName.setText(subCategoryArrayList.get(i).getProductName().replace("\\",""));
        viewHolder.product_short_description.setText(subCategoryArrayList.get(i).getProductShortDescription());
        viewHolder.price.setText(subCategoryArrayList.get(i).getProductPrice());

        if(imageurl!=null &&
                imageurl.length()>0) {

            Picasso.with(mContext).load(imageurl).
                    error(R.drawable.default_image).into(viewHolder.subImage);

        }else{

            Picasso.with(mContext).load(R.drawable.default_image).into(viewHolder.subImage);
        }


        stock=subCategoryArrayList.get(i).getProductStock();

        if (stock.equalsIgnoreCase("null")||stock.isEmpty()){



            viewHolder.mAddtoCart.setBackground(mContext.getResources().getDrawable(R.drawable.outofstockbackground));
            viewHolder.mAddtoCart.setText("Out Of Stock");

        }else {

            int  count= Integer.parseInt(stock);

            if (count==0||count<1){
                viewHolder.mAddtoCart.setBackground(mContext.getResources().getDrawable(R.drawable.outofstockbackground));
                viewHolder.mAddtoCart.setText("Out Of Stock");
            }else {

                viewHolder.mAddtoCart.setText("Add To Cart");
                viewHolder.mAddtoCart.setBackground(mContext.getResources().getDrawable(R.drawable.button_back));
            }

        }



    }

    @Override
    public int getItemCount() {

        return subCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView mAddtoCart,productName,product_short_description,price;
        private ImageView subImage;
        public ViewHolder(View itemView){
            super(itemView);
            mAddtoCart = itemView.findViewById(R.id.AddtoCartBtn);
            productName = itemView.findViewById(R.id.productName);
            product_short_description = itemView.findViewById(R.id.descriptions);
            price = itemView.findViewById(R.id.price);
            subImage=itemView.findViewById(R.id.subImage);
            mAddtoCart.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            new CartDialog(mContext,subCategoryArrayList,getAdapterPosition());
            //mClickListener.OnClick(v,getAdapterPosition());
        }

    }
    public void  setOnClickListeners(OnItemClickListeners onMClickListener){

        this.mClickListener=onMClickListener;}

    public interface OnItemClickListeners {

        void OnClick(View v, int position);

    }

}
