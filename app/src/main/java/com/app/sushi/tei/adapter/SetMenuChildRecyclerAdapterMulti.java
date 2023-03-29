package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;

import java.util.List;


public class SetMenuChildRecyclerAdapterMulti extends RecyclerView.Adapter<SetMenuChildRecyclerAdapterMulti.MyViewHolder> {
    private Context mContext;
    private List<SetMenuTitle> setMenuTitleList;
    private List<SetMenuModifier> arrProduct;
    private int pos;
    private String applyPrice = "0";
    private int editType;
    private Incrementbutton incrementbutton;

    public SetMenuChildRecyclerAdapterMulti(Context mContext, List<SetMenuModifier> arrProduct, int pos, List<SetMenuTitle> setMenuTitleList, String applyPrice, int editType) {
        this.mContext = mContext;
        this.arrProduct = arrProduct;
        this.pos = pos;
        this.setMenuTitleList = setMenuTitleList;
        this.applyPrice = applyPrice;
        this.editType = editType;
    }

    public SetMenuChildRecyclerAdapterMulti(Context mContext, List<SetMenuModifier> arrProduct, int pos, List<SetMenuTitle> setMenuTitleList, String applyPrice, int editType, Incrementbutton incrementbutton) {
        this.mContext = mContext;
        this.arrProduct = arrProduct;
        this.pos = pos;
        this.setMenuTitleList = setMenuTitleList;
        this.applyPrice = applyPrice;
        this.editType = editType;
        this.incrementbutton = incrementbutton;
    }

    public interface Incrementbutton {
        void addtoSubtotla(String AddValue);


        void subtoSubtotla(String SubValue);


    }

    @Override
    public SetMenuChildRecyclerAdapterMulti.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_setmenu_apply_1_child_item, parent, false);

        return new SetMenuChildRecyclerAdapterMulti.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SetMenuChildRecyclerAdapterMulti.MyViewHolder holder, final int position) {

        if (arrProduct.get(position).getmModifierAliasName().length() > 0 &&
                !arrProduct.get(position).getmModifierPrice().equalsIgnoreCase("0.00")) {

            holder.txtModifierName.setText(arrProduct.get(position).getmModifierAliasName()
                    + "(+" + arrProduct.get(position).getmModifierPrice() + ")");
        } else {
            holder.txtModifierName.setText(arrProduct.get(position).getmModifierAliasName());
        }

        if (editType == 1) {
            holder.txtQuantity.setText(arrProduct.get(position).getTotalQuantity());
        } else if(editType ==2) {
            holder.txtQuantity.setText(arrProduct.get(position).getTotalQuantity());
        }

        holder.imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, ""+pos, Toast.LENGTH_SHORT).show();


                if (setMenuTitleList.get(pos).gettQuantity() < setMenuTitleList.get(pos).getmMaxSelect()) {
                    int count = Integer.parseInt(holder.txtQuantity.getText().toString());

                    count++;

                    holder.txtQuantity.setText(String.valueOf(count));
                    arrProduct.get(position).setTotalQuantity(String.valueOf(count));
                    int toCount = setMenuTitleList.get(pos).gettQuantity();
                    toCount++;
//
                    setMenuTitleList.get(pos).settQuantity(toCount);


                    if (applyPrice.equalsIgnoreCase("1")) {

                        if (editType == 1)
                        {
                           // TODO
                           // ((CartActivity) mContext).makeAddTotalCalculation(arrProduct.get(position).getmModifierPrice());

                        }
                        else if (editType == 2) {

                            incrementbutton.addtoSubtotla(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice()) ));

                        } else {


                          //  ((SearchProductDetailsActivity) mContext).makeAddTotalCalculation(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice())));
                        }


                    } else {
                        if (setMenuTitleList.get(pos).gettQuantity() > setMenuTitleList.get(pos).getmMinSelect()) {

                            if (editType == 1) {
                                // TODO
                             //   ((CartActivity) mContext).makeAddTotalCalculation(arrProduct.get(position).getmModifierPrice());
                            } else if (editType == 2) {

                                incrementbutton.addtoSubtotla(arrProduct.get(position).getmModifierPrice());

                            } else {
                               // ((SearchProductDetailsActivity) mContext).makeAddTotalCalculation(arrProduct.get(position).getmModifierPrice());
                            }
                            Double val = arrProduct.get(position).getTotalpPrize() + Double.valueOf(arrProduct.get(position).getmModifierPrice());

                            arrProduct.get(position).setTotalpPrize(val);

//                        Toast.makeText(mContext, "" + arrProduct.get(position).get(0).getProductPrice(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        holder.imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(holder.txtQuantity.getText().toString());

                if (count > 0) {
                    count--;
                    holder.txtQuantity.setText(String.valueOf(count));
                    arrProduct.get(position).setTotalQuantity(String.valueOf(count));
                    int toCount = setMenuTitleList.get(pos).gettQuantity();

                    toCount--;
                    setMenuTitleList.get(pos).settQuantity(toCount);


                    if (applyPrice.equalsIgnoreCase("1")) {

                        if (editType == 1) {
                            // TODO
                           // ((CartActivity) mContext).makeSubstTotalCalculation(arrProduct.get(position).getmModifierPrice());
                        } else if (editType == 2) {

                            if (Integer.valueOf(holder.txtQuantity.getText().toString()) == 0) {
                                incrementbutton.subtoSubtotla(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice()) * 1));
                            } else {
                                incrementbutton.subtoSubtotla(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice())));
                            }
                        } else {
                            if (Integer.valueOf(holder.txtQuantity.getText().toString()) == 0) {
                                //((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice()) * 1));
                            } else {
                               // ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(String.valueOf(Double.valueOf(arrProduct.get(position).getmModifierPrice())));
                            }
                        }


                    } else {
                        if (setMenuTitleList.get(pos).gettQuantity() >= setMenuTitleList.get(pos).getmMinSelect()) {
//
                            Double va = 0.00;

//

                            if (setMenuTitleList.get(pos).gettQuantity() == setMenuTitleList.get(pos).getmMinSelect()) {
                                for (int i = 0; i < setMenuTitleList.get(pos).getSetMenuModifierList().size(); i++) {
                                    if (setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getTotalpPrize() > 0.00) {
//
                                        va = setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getTotalpPrize() - Double.valueOf(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                        setMenuTitleList.get(pos).getSetMenuModifierList().get(i).setTotalpPrize(va);
                                        if (va == 0.00) {

                                            if (editType == 1) {
                                                // TODO
                                               // ((CartActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                            } else if (editType == 2) {

                                                incrementbutton.subtoSubtotla(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());

                                            } else {
                                               // ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                            }

                                            return;
                                        }

                                        if (editType == 1) {
                                            // TODO
                                           // ((CartActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                        } else if (editType == 2) {

                                            incrementbutton.subtoSubtotla(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());

                                        } else {
                                           // ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                        }

                                        return;


                                    } else {


//
                                    }
                                }
                            } else {

                                if (arrProduct.get(position).getTotalpPrize() > 0.00) {
                                    va = arrProduct.get(position).getTotalpPrize() - Double.valueOf(arrProduct.get(position).getmModifierPrice());
                                    arrProduct.get(position).setTotalpPrize(va);

                                    if (editType == 1) {
                                        // TODO
                                      //  ((CartActivity) mContext).makeSubstTotalCalculation(arrProduct.get(position).getmModifierPrice());
                                    } else if (editType == 2) {

                                        incrementbutton.subtoSubtotla(arrProduct.get(position).getmModifierPrice());

                                    } else {
                                       // ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(arrProduct.get(position).getmModifierPrice());
                                    }

                                } else {
                                    for (int i = 0; i < setMenuTitleList.get(pos).getSetMenuModifierList().size(); i++) {
                                        if (setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getTotalpPrize() > 0.00) {
//

                                            va = setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getTotalpPrize() - Double.valueOf(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                            setMenuTitleList.get(pos).getSetMenuModifierList().get(i).setTotalpPrize(va);
                                            if (va == 0.00) {
                                                if (editType == 1) {
                                                    // TODO
                                                  //  ((CartActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                                } else if (editType == 2) {

                                                    incrementbutton.subtoSubtotla(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());

                                                } else {
                                                  //  ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                                }

                                                return;
                                            }

                                            if (editType == 1) {
                                                // TODO
                                                //((CartActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                            } else if (editType == 2) {

                                                incrementbutton.subtoSubtotla(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());

                                            } else {
                                               // ((SearchProductDetailsActivity) mContext).makeSubstTotalCalculation(setMenuTitleList.get(pos).getSetMenuModifierList().get(i).getmModifierPrice());
                                            }

                                            return;


                                        } else {


                                        }
                                    }
                                }


//
//
                            }


//


                        }
                    }


                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrProduct != null ? arrProduct.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtModifierName;
        private ImageView imgDecreement;
        private TextView txtQuantity;
        private ImageView imgIncreement;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            imgDecreement = (ImageView) itemView.findViewById(R.id.imgDecreement);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            imgIncreement = (ImageView) itemView.findViewById(R.id.imgIncreement);
        }
    }
}
