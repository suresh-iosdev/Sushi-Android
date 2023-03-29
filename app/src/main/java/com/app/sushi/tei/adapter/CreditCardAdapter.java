package com.app.sushi.tei.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.Model.Cards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.PaymentActivity;

import java.util.ArrayList;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.VHItem>
{


    ArrayList<Cards> cardsArrayLists = new ArrayList<>();
    Activity context;
    String Cardid,Customer_Id;

    public CreditCardAdapter(Activity mcontext, ArrayList<Cards> mcardsArrayList)
    {
        this.context = mcontext;
        this.cardsArrayLists =mcardsArrayList;

    }
    @Override
    public VHItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.base_credit_card_item, parent, false);

        VHItem dataObjectHolder = new VHItem(view);


        return dataObjectHolder;    }

    @Override
    public void onBindViewHolder(VHItem holder, final int position)
    {


        if (holder instanceof VHItem)
        {

            Cards cards = (Cards) cardsArrayLists.get(position);

            holder.lastFourDigitTextView.setText(cardsArrayLists.get(position).getLast4());
            ((VHItem) holder).setItem(cards);
            ((VHItem) holder).deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setMessage("Are you sure, want to delete this card?");
                    alertDialog.setTitle("Delete");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {




                           // deleteCard(cardsArrayLists.get(position).getId(), position);


                            ((PaymentActivity)context).deleteCard(cardsArrayLists.get(position).getId(), position);


                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();


                }
            });


            holder.card_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder stringBuilder = new StringBuilder();

                    if (cardsArrayLists.get(position).getLast4() != null && !cardsArrayLists.get(position).getLast4().equals("")) {

                        if (cardsArrayLists.get(position).getLast4().length() == 4)
                            stringBuilder.append(cardsArrayLists.get(position).getLast4());
                        else if (cardsArrayLists.get(position).getLast4().length() >= 5)
/*
                            stringBuilder.append(cardsArrayLists.get(position).getLast4().substring(0, 4));
*/
                            stringBuilder.append("XXXX");

                        else
                            stringBuilder.append("XXXX");

                    } else {
                        stringBuilder.append("XXXX");
                    }
                    stringBuilder.append(" XXXX ");


                    stringBuilder.append(cardsArrayLists.get(position).getLast4());


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setMessage("Would you like to use this card? " + "XXXX XXXX XXXX "+cardsArrayLists.get(position).getLast4());
                    alertDialog.setTitle("Payment");
                    alertDialog.setPositiveButton("Yes Proceed", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ((PaymentActivity)context).passSavedCard(cardsArrayLists, position);


                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return cardsArrayLists.size();
    }

    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView firstFourDigitTextView, lastFourDigitTextView, deleteTextView;

        LinearLayout card_layout;

        Cards card;

        public VHItem(View itemView) {
            super(itemView);

            firstFourDigitTextView = (TextView) itemView.findViewById(R.id.firstFourDigitTextView);
            lastFourDigitTextView = (TextView) itemView.findViewById(R.id.lastFourDigitTextView);
            deleteTextView = (TextView) itemView.findViewById(R.id.deleteTextView);
            card_layout=(LinearLayout)itemView.findViewById(R.id.card_layouts);

            itemView.setOnClickListener(this);

        }


        public void setItem(Cards card) {
            this.card = card;
        }

        @Override
        public void onClick(View v) {




        }

    }







}
