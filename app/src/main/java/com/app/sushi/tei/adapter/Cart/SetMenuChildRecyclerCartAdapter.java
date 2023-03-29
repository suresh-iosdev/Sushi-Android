package com.app.sushi.tei.adapter.Cart;

import android.content.Context;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.adapter.Products.SetMenuModifierRecyclerAdapter;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;

import java.util.List;

import static com.app.sushi.tei.activity.HomeActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.HomeActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.HomeActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.HomeActivity.quantityCost;
import static com.app.sushi.tei.activity.HomeActivity.txtModifierPrice;


public class SetMenuChildRecyclerCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<SetMenuModifier> setMenuModifierList;
    int minmaxSelect = 0;
    private ChildHolderApply0 childHolderApply0;
    private ChildHolderApply1 childHolderApply1;
    private int mParentPosition = 0;
    boolean checked = false;
    private List<SetMenuTitle> setMenuTitleList;
    private String Status="";
    private Integer totalCount;
    private Double subtotalPrice = 0.0;

    public SetMenuChildRecyclerCartAdapter(Context mContext, List<SetMenuModifier> setMenuModifierList, int minmaxSelect,
                                           List<SetMenuTitle> setMenuTitle, int position, String status) {
        this.mContext = mContext;
        this.setMenuModifierList = setMenuModifierList;
        this.minmaxSelect = minmaxSelect;
        this.setMenuTitleList = setMenuTitle;
        this.Status =status;
        mParentPosition = position;
    }

    @Override
    public int getItemViewType(int position) {

        return minmaxSelect;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (minmaxSelect == 1) {

            if( GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")){

                if( GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")){

                    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_1_child_item, parent, false);
                    childHolderApply1 = new ChildHolderApply1(view);

                    return childHolderApply1;

                } else{

                    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_0_child_item, parent, false);

                    childHolderApply0 = new ChildHolderApply0(view);

                    return childHolderApply0;
                }

            }else{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_0_child_item, parent, false);

                childHolderApply0 = new ChildHolderApply0(view);

                return childHolderApply0;
            }

        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setmenu_apply_0_child_item, parent, false);

            childHolderApply0 = new ChildHolderApply0(view);

            return childHolderApply0;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ChildHolderApply0)
        {

            double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());

            childHolderApply0 = (ChildHolderApply0) holder;

            String prices= String.valueOf(price);
            if(prices.equalsIgnoreCase("0.0")){
                childHolderApply0.txtModifierName.setText(setMenuModifierList.get(position).getmModifierAliasName());
            }else {
                childHolderApply0.txtModifierName.setText(setMenuModifierList.get(position).getmModifierAliasName() + "(" + prices + ")");
            }

            if (setMenuModifierList.get(position).getModifierHeadingList() != null) {

                if (setMenuModifierList.get(position).getModifierHeadingList().size() > 0) {

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

                    childHolderApply0.modifiersRecyclerView.setLayoutManager(layoutManager);

                    SetMenuModifierRecyclerAdapter setMenuModifierRecyclerAdapter = new SetMenuModifierRecyclerAdapter(mContext,
                            setMenuModifierList.get(position).getModifierHeadingList(), setMenuTitleList.get(mParentPosition).getmAppliedPrice(), Status);

                    childHolderApply0.modifiersRecyclerView.setAdapter(setMenuModifierRecyclerAdapter);
                    childHolderApply0.modifiersRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    childHolderApply0.modifiersRecyclerView.setNestedScrollingEnabled(false);
                    childHolderApply0.modifiersRecyclerView.setHasFixedSize(true);

                } else {
                }

            } else {
            }


            if (setMenuModifierList.get(position).isChecked())
            {
                childHolderApply0.imgChecked.setImageResource(R.drawable.asset53);
                childHolderApply0.modifiersRecyclerView.setVisibility(View.VISIBLE);

            } else {

                childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);
                childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);
            }



           /* if(position==mPosition)
            {

                if(setMenuModifierList.get(position).isChecked())
                {
                    childHolderApply0.imgChecked.setImageResource(R.drawable.modifier_checked);
                }else{
                    childHolderApply0.imgChecked.setImageResource(R.drawable.modifier_unchecked);
                }

            }else{
                setMenuModifierList.get(position).setChecked(false);
                childHolderApply0.imgChecked.setImageResource(R.drawable.modifier_unchecked);
                childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);
            }
*/

            childHolderApply0.txtModifierName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (setMenuModifierList.get(position).getModifierHeadingList().size() > 0){
                        checked=true;
                    }else{
                        checked=false;
                    }
                    if( setMenuModifierList.get(position).getsub_modifier_apply().equalsIgnoreCase("0")){

                        if( setMenuModifierList.get(position).getsub_multipleselection_apply().equalsIgnoreCase("1")){
                                multiselection(position);

                        }else{

                            singleSelection(position);
                        }

                    }else{

                        singleSelection(position);
                    }


                 /*   if( GlobalValues.MAXIMUMSELECTED.equalsIgnoreCase("0")){

                        double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                        double p = 0;

//                    mPosition = position;
                        for (int i = 0; i < setMenuModifierList.size(); i++) {

                            if (i != position) {

                                if (!setMenuModifierList.get(position).getHasModifier()) {

                                    if (setMenuModifierList.get(i).isChecked()) {
                                        try {
                                            p = Double.valueOf(setMenuModifierList.get(i).getmModifierPrice());
                                        } catch (Exception e) {
                                            p = 0;
                                            e.printStackTrace();
                                        }
                                        mSetMenuPrice = mSetMenuPrice - p;
                                    }
                                }
                                setMenuModifierList.get(i).setChecked(false);
                            }

                        }

                        if (setMenuModifierList.get(position).isChecked())
                        {

                            setMenuModifierList.get(position).setChecked(false);

                            if (!setMenuModifierList.get(position).getHasModifier())
                            {

                                mSetMenuPrice = mSetMenuPrice - price;
                                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                                quantityCost = mSetMenuPrice;
                                Double  mtotalPrice=mSetMenuPrice* mSetMenuQuantity;
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));



                            }

                        } else
                        {

                            setMenuModifierList.get(position).setChecked(true);

                            if (setMenuModifierList.get(position).getHasModifier()) {


                                childHolderApply0.modifiersRecyclerView.setVisibility(View.VISIBLE);

                            } else {

                                childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);


                                subtotalPrice=mSetMenuBasePrice+price;
                                mSetMenuPrice = subtotalPrice;
                                quantityCost = mSetMenuPrice*mSetMenuQuantity ;


                                Double  mtotalPrice=mSetMenuPrice*mSetMenuQuantity;
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));




                            }

                        }


                        notifyDataSetChanged();

                    }else{


                        double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                        double p = 0;


                        if (setMenuModifierList.get(position).isChecked())
                        {

                            setMenuModifierList.get(position).setChecked(false);
                            childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);
                            if (!setMenuModifierList.get(position).getHasModifier())
                            {

                                mSetMenuPrice = mSetMenuPrice - price;
                                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                                quantityCost = mSetMenuPrice;
                                Double  mtotalPrice=mSetMenuPrice* mSetMenuQuantity;
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));



                            }

                        } else {


                            double temporaryPrice = mSetMenuPrice;
                            //double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());




                            if (setMenuModifierList.get(position).isChecked()) {


                                childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);
                                setMenuModifierList.get(position).setChecked(false);

                                Double move = 0.0;
                                Double totalSubPrice = 0.0;
                                for (int i = 0; i < setMenuModifierList.size(); i++) {


                                    if (setMenuModifierList.get(i).isChecked()) {

                                        totalSubPrice = totalSubPrice + Double.valueOf(setMenuModifierList.get(i).getmModifierPrice());

                                    } else {
                                        try {
                                            move = Double.valueOf(setMenuModifierList.get(i).getmModifierPrice());



                                        } catch (Exception e) {

                                            e.printStackTrace();
                                        }

                                    }
                                }

                                price += Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());

                                temporaryPrice = temporaryPrice + totalSubPrice;
                                mSetMenuPrice = temporaryPrice;

                                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                                txtModifierPrice.setText(String.valueOf(Utility.setPriceSpan(mContext, quantityCost)));


                            } else {

                                childHolderApply0.imgChecked.setImageResource(R.drawable.asset53);
                                setMenuModifierList.get(position).setChecked(true);

                                Double move = 0.0;
                                Double totalSubPrice = 0.0;

                                totalSubPrice = totalSubPrice + Double.valueOf(setMenuModifierList.get(position).getmModifierPrice());




                                temporaryPrice = temporaryPrice + totalSubPrice;
                                mSetMenuPrice = temporaryPrice;

                                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));


                            }


                            notifyItemChanged(position);
                    }  }*/

                }
            });
        } else if (holder instanceof ChildHolderApply1)
        {

            childHolderApply1 = (ChildHolderApply1) holder;

            if (setMenuModifierList.get(position).getmModifierPrice().length() > 0 &&
                    !setMenuModifierList.get(position).getmModifierPrice().equalsIgnoreCase("0.00")) {

                childHolderApply1.txtModifierName.setText(setMenuModifierList.get(position).getmModifierAliasName()
                        + "(+" + setMenuModifierList.get(position).getmModifierPrice() + ")");
            } else {
                childHolderApply1.txtModifierName.setText(setMenuModifierList.get(position).getmModifierAliasName());
            }




            if(Status.equals("create"))
            {
                childHolderApply1.txtQuantity.setText(setMenuModifierList.get(position).getmQuantity() + "");


/*
                Toast.makeText(mContext,"create"+setMenuModifierList.get(position).getmQuantity(),Toast.LENGTH_SHORT).show();
*/

            }
            else
            {
                childHolderApply1.txtQuantity.setText(setMenuModifierList.get(position).getmQuantityUpdates() + "");

/*
                Toast.makeText(mContext,"update : "+setMenuModifierList.get(position).getmQuantityUpdates(),Toast.LENGTH_SHORT).show();
*/

            }



            childHolderApply1.imgIncreement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count=0;

                    if(Status.equals("create")) {
                        count = setMenuModifierList.get(position).getmQuantity();

                    } else {
                        count = setMenuModifierList.get(position).getmQuantityUpdates();
                    }

                    int totalcount = setMenuTitleList.get(mParentPosition).getmTotalQuantity();
                    int minselect = setMenuTitleList.get(mParentPosition).getmMinSelect();
                    int maxselect = setMenuTitleList.get(mParentPosition).getmMaxSelect();

                    count++;
                    totalcount++;

                    if (totalcount <= maxselect)
                    {

                        setMenuModifierList.get(position).setmQuantity(count);
                        setMenuModifierList.get(position).setmQuantityUpdates(count);

                        setMenuTitleList.get(mParentPosition).setmTotalQuantity(totalcount);



                        if (Integer.parseInt(setMenuTitleList.get(mParentPosition).getmAppliedPrice()) == 1) {

                            double price;

                            try {
                                price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                            } catch (Exception e) {
                                e.printStackTrace();
                                price = 0;
                            }

                           // mSetMenuPrice = mSetMenuPrice + price;


                            mSetmenuoverallprices = price * count;


                          Double quantityCost_Menu = mSetmenuoverallprices +  quantityCost;


                            mquanititycost_src = quantityCost_Menu;


                          txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_Menu));

                        } else {

                            if (totalcount > minselect)
                            {

                                double price = 0.0;


                              /*  for (int i = 0; i < setMenuTitleList.size(); i++)
                                {


                  *//*                  for (int j = 0; j < setMenuTitleList.get(i).getSetMenuModifierList().size(); j++) {

                                        try {

                                            int q1 = minselect - (totalcount - setMenuTitleList.get(i).getSetMenuModifierList().get(j).getmQuantity());

                                            if (q1 <= 0) {
                                                q1 = 0;
                                            }

                                            int q2 = setMenuTitleList.get(i).getSetMenuModifierList().get(j).getmQuantity() - q1;

                                            if (q2 <= 0) {
                                                q2 = 0;
                                            }

                                            double p = Double.parseDouble(setMenuTitleList.get(i).getSetMenuModifierList().get(j).getmModifierPrice());

                                            price += p * q2;

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }*//*
                                }*/

                                try {
                                    price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    price = 0;
                                }


                                int Total = totalcount - minselect;


                                mSetmenuoverallprices = price * Total;



                                Double quantityCost_Menu = mSetmenuoverallprices +  quantityCost;


                                mquanititycost_src = quantityCost_Menu;






                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_Menu));

                            }

                        }


                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "You have reached the maximum selection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            childHolderApply1.imgDecreement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int count=0;

                    if(Status.equals("create"))
                    {
                        count = setMenuModifierList.get(position).getmQuantity();


                    }
                    else
                    {
                        count = setMenuModifierList.get(position).getmQuantityUpdates();


                    }

                    int totalcount = setMenuTitleList.get(mParentPosition).getmTotalQuantity();
                    int minselect = setMenuTitleList.get(mParentPosition).getmMinSelect();
                    int maxselect = setMenuTitleList.get(mParentPosition).getmMaxSelect();

                    if (count > 0) {

                        count--;
                        totalcount--;

                        setMenuTitleList.get(mParentPosition).setmTotalQuantity(totalcount);

                        setMenuModifierList.get(position).setmQuantity(count);

                        setMenuModifierList.get(position).setmQuantityUpdates(count);


                        if (Integer.parseInt(setMenuTitleList.get(mParentPosition).getmAppliedPrice()) == 1) {

                            double price;

                            try {
                                price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                            } catch (Exception e) {
                                e.printStackTrace();
                                price = 0;
                            }

                            mSetmenuoverallprices = price * count;



                          Double quantityCost_Menu = mSetmenuoverallprices +  quantityCost;

                            mquanititycost_src = quantityCost_Menu;



                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_Menu));

                        } else {

                            double price = 0.0;

                            if (totalcount >= minselect)
                            {


            /*                    for (int i = 0; i < setMenuTitleList.size(); i++)
                                {


                                    for (int j = 0; j < setMenuTitleList.get(i).getSetMenuModifierList().size(); j++) {

                                        try {

                                            int q1 = minselect - (totalcount - setMenuTitleList.get(i).getSetMenuModifierList().get(j).getmQuantity());

                                            if (q1 <= 0) {
                                                q1 = 0;
                                            }



                                            int q2 = setMenuTitleList.get(i).getSetMenuModifierList().get(j).getmQuantity() - q1;



                                            if (q2 <= 0) {
                                                q2 = 0;
                                            }

                                            double p = Double.parseDouble(setMenuTitleList.get(i)
                                                    .getSetMenuModifierList().get(j).getmModifierPrice());



                                            price += p * q2;




                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }*/

                                try {
                                    price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    price = 0;
                                }



                                int Total = totalcount - minselect;


                                mSetmenuoverallprices = price * Total;




                                Double quantityCost_Menu = mSetmenuoverallprices +  quantityCost;


                                mquanititycost_src = quantityCost_Menu;

                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_Menu));

                            }


                            /*mSetMenuPrice = mSetMenuBasePrice + price;

                            Double quantityCost = mSetMenuPrice * mSetMenuQuantity;

                           */




                        }




                        notifyDataSetChanged();

                    }
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return setMenuModifierList.size();
    }

    public void updateExpandableView() {


    }

    public class ChildHolderApply0 extends RecyclerView.ViewHolder {

        private TextView txtModifierName;
        private ImageView imgChecked;
        private RecyclerView modifiersRecyclerView;


        public ChildHolderApply0(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            imgChecked = (ImageView) itemView.findViewById(R.id.imgChecked);
            modifiersRecyclerView = (RecyclerView) itemView.findViewById(R.id.modifiersRecyclerView);
        }
    }

    public class ChildHolderApply1 extends RecyclerView.ViewHolder {

        private TextView txtModifierName, txtQuantity;
        private ImageView imgDecreement, imgIncreement;

        public ChildHolderApply1(View itemView) {
            super(itemView);

            txtModifierName = (TextView) itemView.findViewById(R.id.txtModifierName);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            imgDecreement = (ImageView) itemView.findViewById(R.id.imgDecreement);
            imgIncreement = (ImageView) itemView.findViewById(R.id.imgIncreement);
        }
    }

    public void singleSelection(int position){
        {

            double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
            double p = 0;

//                    mPosition = position;
            for (int i = 0; i < setMenuModifierList.size(); i++) {

                if (i != position) {

                    if (!setMenuModifierList.get(position).getHasModifier()) {

                        if (setMenuModifierList.get(i).isChecked()) {
                            try {
                                p = Double.valueOf(setMenuModifierList.get(i).getmModifierPrice());
                            } catch (Exception e) {
                                p = 0;
                                e.printStackTrace();
                            }
                            mSetMenuPrice = mSetMenuPrice - p;
                        }
                    }
                    setMenuModifierList.get(i).setChecked(false);
                }

            }
            if (setMenuModifierList.get(position).isChecked()) {


                setMenuModifierList.get(position).setChecked(false);

                if (setMenuModifierList.get(position).getHasModifier()) {


                    Double modifierPrice = 0.0;
                    List<ModifierHeading> modifierHeadings = setMenuModifierList.get(position).getModifierHeadingList();
                    List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                    for (int i = 0; i < modifiersValueList.size(); i++) {


                        if (modifiersValueList.get(i).getChekced()) {
                            modifierPrice = Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());
                        }
                        modifiersValueList.get(i).setChekced(false);
                    }

                    subtotalPrice = mSetMenuPrice - price-modifierPrice;
                    mSetMenuPrice = subtotalPrice;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;
                    Double mtotalPrice = mSetMenuPrice * mSetMenuQuantity;
                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));



                } else {
                    subtotalPrice = mSetMenuPrice - price;
                    mSetMenuPrice = subtotalPrice;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;
                    Double mtotalPrice = mSetMenuPrice * mSetMenuQuantity;
                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));
                }

            } else {

                setMenuModifierList.get(position).setChecked(true);
                Double modifierPrice = 0.0;
                if(checked){
                    mSetMenuPrice=mSetMenuBasePrice;
                    checked=false;
                }
                if (setMenuModifierList.get(position).getHasModifier()) {

                    List<ModifierHeading> modifierHeadings = setMenuModifierList.get(position).getModifierHeadingList();
                    List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                    for (int i = 0; i < modifiersValueList.size(); i++) {

                        if (modifiersValueList.get(i).getChekced()) {
                            modifierPrice = Double.valueOf(modifiersValueList.get(i).getmModifierValuePrice());
                        }
                        modifiersValueList.get(i).setChekced(false);
                    }

                    subtotalPrice = mSetMenuPrice - price;
                    mSetMenuPrice = subtotalPrice;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;

                    Double mtotalPrice = mSetMenuPrice * mSetMenuQuantity;
                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));
                    childHolderApply0.modifiersRecyclerView.setVisibility(View.VISIBLE);

                } else {


                    childHolderApply0.modifiersRecyclerView.setVisibility(View.GONE);

                    subtotalPrice = mSetMenuPrice + price;
                    mSetMenuPrice = subtotalPrice;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;


                    Double mtotalPrice = mSetMenuPrice * mSetMenuQuantity;
                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));

                }

            }

            notifyDataSetChanged();


        }

    }

    public void multiselection(int position){

        double price = Double.parseDouble(setMenuModifierList.get(position).getmModifierPrice());
        if (setMenuModifierList.get(position).isChecked()){
            childHolderApply0.imgChecked.setImageResource(R.drawable.asset53);
        }else{
            childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);
        }


        if (setMenuModifierList.get(position).isChecked()) {

            setMenuModifierList.get(position).setChecked(false);
            childHolderApply0.imgChecked.setImageResource(R.drawable.asset54);

            if (!setMenuModifierList.get(position).getHasModifier())
            {

                mSetMenuPrice = mSetMenuPrice - price;
                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                quantityCost = mSetMenuPrice;
                Double mtotalPrice=mSetMenuPrice* mSetMenuQuantity;
                txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));


            }else{

                mSetMenuPrice = mSetMenuPrice - price;
                quantityCost = mSetMenuPrice * mSetMenuQuantity;
                quantityCost = mSetMenuPrice;
                Double mtotalPrice=mSetMenuPrice* mSetMenuQuantity;
                txtModifierPrice.setText(Utility.setPriceSpan(mContext, mtotalPrice));
            }

        }else {


            setMenuModifierList.get(position).setChecked(true);
            childHolderApply0.imgChecked.setImageResource(R.drawable.asset53);


            double temporaryPrice = mSetMenuPrice;


            Double totalSubPrice = 0.0;


            totalSubPrice = totalSubPrice + Double.valueOf(setMenuModifierList.get(position).getmModifierPrice());



            temporaryPrice = temporaryPrice + totalSubPrice;
            mSetMenuPrice = temporaryPrice;

            quantityCost = mSetMenuPrice * mSetMenuQuantity;
            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));


        }
        notifyDataSetChanged();





    }
}
