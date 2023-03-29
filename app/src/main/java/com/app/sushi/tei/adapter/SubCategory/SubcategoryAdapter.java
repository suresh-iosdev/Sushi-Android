package com.app.sushi.tei.adapter.SubCategory;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.sushi.tei.Model.ProductList.ProductSubCategory;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.R;
import com.app.sushi.tei.adapter.Products.ProductListRecyclerAdapter;
import com.app.sushi.tei.fragment.subcategory.SubcategoryFragment;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {
    private Context mContext;

    private List<ProductSubCategory> productSubCategoryList;
    private List<Products> productsList;
    private List<String> SubcategoryList;

    SubcategoryFragment subcategoryFragment;

    boolean isLoading = false;

    public SubcategoryAdapter(Activity mContext, List<ProductSubCategory> productSubCategoryList, List<String> SubcategoryList) {
        this.mContext = mContext;
        this.productSubCategoryList = productSubCategoryList;
        this.SubcategoryList = SubcategoryList;
    }

    public SubcategoryAdapter(Activity mContext, List<ProductSubCategory> productSubCategoryList, List<String> SubcategoryList, SubcategoryFragment subcategoryFragment) {
        this.mContext = mContext;
        this.productSubCategoryList = productSubCategoryList;
        this.SubcategoryList = SubcategoryList;
        this.subcategoryFragment = subcategoryFragment;
    }

    @Override
    public SubcategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_subcategory, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.SubCategory.setText(SubcategoryList.get(i).toUpperCase());

        productsList = productSubCategoryList.get(i).getProductsList();

//        try {//test
//            int x = productsList.size();
//            for (int it = 10; it < x; it++) {
//                productsList.remove(i);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        for (int i1 = 0; i1 < productsList.size(); i1++) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        viewHolder.recyclerViewAdditional.setLayoutManager(linearLayoutManager);
        final ProductListRecyclerAdapter subcategoryAdapter = new ProductListRecyclerAdapter(mContext, productsList, SubcategoryList.get(i));
//        final ProductListRecyclerAdapter subcategoryAdapter = new ProductListRecyclerAdapter(mContext, productsList, SubcategoryList.get(i));
        viewHolder.recyclerViewAdditional.setAdapter(subcategoryAdapter);
        subcategoryAdapter.notifyDataSetChanged();

//        viewHolder.txtLoadMore.setVisibility(View.VISIBLE);
//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                if (!isLoading) {
//                    isLoading = true;
//                    subcategoryAdapter.loadAllMoreData();
//                    if (!(subcategoryAdapter.allProductList.size() > subcategoryAdapter.productsList.size())) {
//                        viewHolder.txtLoadMore.setVisibility(View.GONE);
//                    }
//                    isLoading = false;
//                }
//            }
//        }, 5000);
//        new SwipeDetector(viewHolder.recyclerViewAdditional).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
//            @Override
//            public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum swipeType) {
//                if(swipeType==SwipeDetector.SwipeTypeEnum.TOP_TO_BOTTOM){
//
//                }
//
//            }
//        });
//        final View viewTemp = viewHolder.loadingPB;
//        viewHolder.loadingPB.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
////                int rowHeight = viewTemp.getGlobalVisibleRect();
//
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ((NestedScrollView) ((Activity) mContext).findViewById(R.id.nestedScrollView)).setNestedScrollingEnabled(false);
//
//            ((NestedScrollView) ((Activity) mContext).findViewById(R.id.nestedScrollView)).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

//                    long height = v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight();
////                    long height = v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight();
//                    if ((scrollY >= height)) {
//                        // in this method we are incrementing page number,
//                        // making progress bar visible and calling get data method.
////                        subcategoryAdapter.page++;
////                        viewHolder.loadingPB.setVisibility(View.VISIBLE);
////                        subcategoryAdapter.onScrolledToBottom();
//////                        getDataFromAPI(page, limit);

////                        if (!isLoading) {
////                            isLoading = true;
////                            subcategoryAdapter.loadMoreData();
////                            if (!(subcategoryAdapter.allProductList.size() > subcategoryAdapter.productsList.size())) {
////                                viewHolder.txtLoadMore.setVisibility(View.GONE);
////                            }
////                            isLoading = false;
////                        }
//                    }
//                }
//            });
//        }
        viewHolder.txtLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLoading) {
                    isLoading = true;
                    subcategoryAdapter.loadMoreData();
                    if (!(subcategoryAdapter.allProductList.size() > subcategoryAdapter.productsList.size())) {
                        viewHolder.txtLoadMore.setVisibility(View.GONE);
                    }
                    isLoading = false;
                }
            }
        });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    Toast.makeText(mContext, "Scrolling", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    Toast.makeText(mContext, "Scrolling", Toast.LENGTH_SHORT).show();
//                }
//            };
////            viewHolder.recyclerViewAdditional.addOnScrollListener(onScrollListener);
//            viewHolder.recyclerViewAdditional.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                }
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                    if (dy < 0) {
//                        // Recycle view scrolling up...
//
//
//                    } else if (dy > 0) {
//                        // Recycle view scrolling down...
//
////                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == showingContactUserArrayList.size() - 1) {
////                            //bottom of list!
////                            loadMoreData();
////                        }
//                    }
//                }
//            });
//
//            viewHolder.idNestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    // on scroll change we are checking when users scroll as bottom.
//                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                        // in this method we are incrementing page number,
//                        // making progress bar visible and calling get data method.
//                        subcategoryAdapter.page++;
//                        viewHolder.loadingPB.setVisibility(View.VISIBLE);
//                        subcategoryAdapter.onScrolledToBottom();
////                        getDataFromAPI(page, limit);
//
//                    }
//                }
//            });
//        } else {
//        viewHolder.recyclerViewAdditional.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);

//                if (dx != 0 & dy != 0)
//                    if (!recyclerView.canScrollVertically(1)) {

//                        subcategoryAdapter.onScrolledToBottom();
//                    }
//            }
//        });
//        }

          /*  LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
            viewHolder.recyclerViewAdditional.setLayoutManager(linearLayoutManager);
            SubcategoryAdapterAdditional subcategoryAdapter=new SubcategoryAdapterAdditional(mContext,productsList,productSubCategoryList.get(i).getmSubCategoryImage());
            viewHolder.recyclerViewAdditional.setAdapter(subcategoryAdapter);*/
    }

//    }

    @Override
    public int getItemCount() {
        return productSubCategoryList != null ? productSubCategoryList.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView SubCategory;
        private TextView txtLoadMore;
        private RecyclerView recyclerViewAdditional;
        //        private NestedScrollView idNestedSV;
        private ProgressBar loadingPB;

        public ViewHolder(View itemView) {
            super(itemView);
            SubCategory = itemView.findViewById(R.id.SubCategory);
            recyclerViewAdditional = itemView.findViewById(R.id.recyclerViewAdditional);
//            idNestedSV = itemView.findViewById(R.id.idNestedSV);
            loadingPB = itemView.findViewById(R.id.idPBLoading);
            txtLoadMore = itemView.findViewById(R.id.txtLoadMore);

        }


        @Override
        public void onClick(View v) {

        }

    }

    public void afterDelay() {
//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                if (!isLoading) {
//                    isLoading = true;
//                    subcategoryAdapter.loadMoreData();
//                    if (!(subcategoryAdapter.allProductList.size() > subcategoryAdapter.productsList.size())) {
//                        viewHolder.txtLoadMore.setVisibility(View.GONE);
//                    }
//                    isLoading = false;
//                }
//            }
//        }, 100);
    }
}
