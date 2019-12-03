package com.safepayu.wallet.adapter.hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safepayu.wallet.R;

import java.util.ArrayList;

public class GuestSelectAdapter extends RecyclerView.Adapter<GuestSelectAdapter.FlightLocationListViewHolder> {

    private Context context ;
    private int childNo=0,totalGuest=1,adultNo=1;
    public static ArrayList<Integer> RoomList,ChildNoList,AdultNoList,GuestNoList;

    public GuestSelectAdapter(Context context, ArrayList<Integer> RoomList1,ArrayList<Integer> ChildNoList1,
                              ArrayList<Integer> AdultNoList1,ArrayList<Integer> GuestNoList1) {
        this.context = context;
        this.RoomList = RoomList1;
        this.ChildNoList = ChildNoList1;
        this.AdultNoList = AdultNoList1;
        this.GuestNoList = GuestNoList1;
    }

    @NonNull
    @Override
    public GuestSelectAdapter.FlightLocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guest_select_adapter,parent,false);
        return new GuestSelectAdapter.FlightLocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestSelectAdapter.FlightLocationListViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return RoomList.size();
    }

    public class FlightLocationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvAdult,tvChild,tvRoomNum,tvRemove ;
        private RecyclerView recyclerViewChild;
        private ImageView ChildPlusBtn,MinusChildOrange,PlusAdultOrange,MinusAdultOrange;
        private ChildGuestAdapter childGuestAdapter;

        public FlightLocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            ChildPlusBtn=itemView.findViewById(R.id.ChildrenPlus_adapterGuests);
            MinusChildOrange = itemView.findViewById(R.id.ChildrenMinus_adapterGuest);
            PlusAdultOrange = itemView.findViewById(R.id.AdultsPlus_adapterGuest);
            MinusAdultOrange = itemView.findViewById(R.id.AdultsMinus_adapterGuest);
            tvAdult = itemView.findViewById(R.id.tvAdultsCount_adapterGuest);
            tvChild = itemView.findViewById(R.id.tvChildrenCount_adapterGuest);
            tvRoomNum = itemView.findViewById(R.id.roomNumber_adapterGuest);
            tvRemove = itemView.findViewById(R.id.removeBtn_adapterGuest);

            recyclerViewChild = itemView.findViewById(R.id.guest_selectChild_list);
            recyclerViewChild.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            recyclerViewChild.setNestedScrollingEnabled(false);
//            childGuestAdapter=new ChildGuestAdapter(context,ChildAdapterNo);
//            recyclerViewChild.setAdapter(childGuestAdapter);

            itemView.setOnClickListener(this);
        }

        public void bindData(final int position) {

            if (RoomList.size()==1) {
                tvRemove.setVisibility(View.GONE);
            }
            tvRoomNum.setText("In Room "+ (position+1));
            tvChild.setText(String.valueOf(ChildNoList.get(position)));
            tvAdult.setText(String.valueOf(AdultNoList.get(position)));

            ChildPlusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    childNo=ChildNoList.get(position);
                    totalGuest=GuestNoList.get(position);
                    if (childNo<2 && totalGuest<7){
                        childNo=childNo+1;
                        totalGuest=totalGuest+1;

                        ChildNoList.set(position,childNo);
                        GuestNoList.set(position,totalGuest);
                        tvChild.setText(String.valueOf(childNo));

                        childGuestAdapter=new ChildGuestAdapter(context,ChildNoList.get(position),position);
                        recyclerViewChild.setAdapter(childGuestAdapter);
                        childGuestAdapter.notifyDataSetChanged();
                        notifyDataSetChanged();

                        MinusChildOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus));

                        if (childNo==2){
                            ChildPlusBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                        }
                        if (totalGuest==6){
                            ChildPlusBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                            PlusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                        }
                    }else {
                        Toast.makeText(context, "Max 2 Children Can Be In One Room", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            MinusChildOrange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    childNo=ChildNoList.get(position);
                    totalGuest=GuestNoList.get(position);
                    if (childNo==0){
                        MinusChildOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus_gray));
                    }else {
                        childNo=childNo-1;
                        totalGuest=totalGuest-1;

                        ChildNoList.set(position,childNo);
                        GuestNoList.set(position,totalGuest);
                        tvChild.setText(String.valueOf(childNo));

                        childGuestAdapter=new ChildGuestAdapter(context,ChildNoList.get(position),position);
                        recyclerViewChild.setAdapter(childGuestAdapter);

                        childGuestAdapter.notifyDataSetChanged();
                        notifyDataSetChanged();

                        ChildPlusBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus));
                        PlusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus));

                        if (childNo==0) {
                            MinusChildOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus_gray));
                        }
                    }

                }
            });

            PlusAdultOrange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adultNo=AdultNoList.get(position);
                    totalGuest=GuestNoList.get(position);

                    if (adultNo==4){
                        Toast.makeText(context, "Max 4 Adult Can Be In One Room", Toast.LENGTH_SHORT).show();
                    }else {
                        if (adultNo<5 && totalGuest<7){
                            adultNo=adultNo+1;
                            totalGuest=totalGuest+1;
                            tvAdult.setText(String.valueOf(adultNo));
                            AdultNoList.set(position,adultNo);
                            GuestNoList.set(position,totalGuest);
                            MinusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus));

                            if (adultNo==4){
                                PlusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                            }
                            if (totalGuest==6){
                                ChildPlusBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                                PlusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_gray));
                            }
                        }else {
                            Toast.makeText(context, "Max 4 Adult Can Be In One Room", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            MinusAdultOrange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adultNo=AdultNoList.get(position);
                    totalGuest=GuestNoList.get(position);
                    if (adultNo==1){
                        MinusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus_gray));
                    }else {
                        adultNo=adultNo-1;
                        totalGuest=totalGuest-1;
                        AdultNoList.set(position,adultNo);
                        GuestNoList.set(position,totalGuest);
                        tvAdult.setText(String.valueOf(adultNo));

                        ChildPlusBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus));
                        PlusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus));

                        if (adultNo==1) {
                            MinusAdultOrange.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_minus_gray));
                        }
                    }
                }
            });

            tvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RoomList.remove(position);
                    ChildNoList.remove(position);
                    AdultNoList.remove(position);
                    GuestNoList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}

