package com.safepayu.wallet.adapter.fight;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.R;
import com.safepayu.wallet.models.response.booking.flight.AvailableFlightResponse;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FlightDetailAdapter extends RecyclerView.Adapter<FlightDetailAdapter.FlightDetailViewHolder> {

    private Context context;
    private List< AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.FlightSegmentsBean>mdata;

    public FlightDetailAdapter(Context context, List<AvailableFlightResponse.DataBean.DomesticOnwardFlightsBean.FlightSegmentsBean> mdata) {
        this.context = context;
        this.mdata = mdata;
    }



    @NonNull
    @Override
    public FlightDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_flight_detail_layout,parent,false);
        return new FlightDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightDetailViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class FlightDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFlightdetailName,tvFlightDetailDepTime,tvFlightDetailArrivalTime
                ,tvFlightDetailSourceName,tvFlightDetailDepDate,tvFlightDetailSourceAirpot,tvFlightDetailDestiName,tvFlightDetailDestiDate,tvFlightDetailDestiAirport,
                tvFlightDetailDuration;
        private ImageView imageFlightDetail;
        private CharSequence s;
        private Date date;

        public FlightDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFlightdetailName =itemView. findViewById(R.id.tv_flightdetail_name);
            tvFlightDetailDepTime = itemView.findViewById(R.id.tv_flight_detail_dep_time);
            tvFlightDetailArrivalTime = itemView. findViewById(R.id.tv_flight_detail_arrival_time);
            tvFlightDetailSourceName = itemView.findViewById(R.id.tv_flight_detail_source_name);
            tvFlightDetailDepDate = itemView.findViewById(R.id.tv_flight_detail_dep_date);
            tvFlightDetailSourceAirpot = itemView.findViewById(R.id.tv_flight_detail_source_airpot);
            tvFlightDetailDestiName = itemView.findViewById(R.id.tv_flight_detail_desti_name);
            tvFlightDetailDestiDate = itemView.findViewById(R.id.tv_flight_detail_desti_date);
            tvFlightDetailDestiAirport = itemView.findViewById(R.id.tv_flight_detail_destin_airport);
            tvFlightDetailDuration = itemView.findViewById(R.id.tv_flight_detail_duration);
            imageFlightDetail = itemView.findViewById(R.id.image_flight_detail);
        }

        public void bindData(int position) {

            tvFlightDetailSourceName.setText(mdata.get(position).getIntDepartureAirportName());
            tvFlightDetailDestiName.setText(mdata.get(position).getIntArrivalAirportName());
            Picasso.get().load("http://webapi.i2space.co.in/"+mdata.get(position).getImagePath()).into(imageFlightDetail);
            tvFlightdetailName.setText(mdata.get(position).getAirLineName()+ "  "+
                    mdata.get(position).getOperatingAirlineCode()+"-"+
                   mdata.get(position).getOperatingAirlineFlightNumber());
            tvFlightDetailSourceAirpot.setText(mdata.get(position).getIntDepartureAirportName()+" Airport");
            tvFlightDetailDestiAirport.setText(mdata.get(position).getIntArrivalAirportName()+" Airport");
            tvFlightDetailDuration.setText(mdata.get(position).getDuration());

            String Date, Date1, Time, h, m;
            String depTime = mdata.get(position).getDepartureDateTime();
            String[] separated = depTime.split("T");
            Date = separated[0];
            Time = separated[1];
            String[] separatedTime = Time.split(":");
            h = separatedTime[0];
            m = separatedTime[1];
            tvFlightDetailDepTime.setText(h + ":" + m);
            String[] sept = Date.split("-");
            String yd = sept[0];
            String mod = sept[1];
            String dd = sept[2];

            String dateZone= dd+"/"+mod+"/"+yd;
            date = new Date(dateZone);
            s  = DateFormat.format("dd-MMM-yyyy", date.getTime());
            String daymonthYear = (String) s;
            String[] sep = daymonthYear.split("-");
            String d = sep[0];
            String mo = sep[1];
            String y = sep[2];

            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEE");
            String dayOfWeek = simpledateformat.format(date);
            tvFlightDetailDepDate.setText(dayOfWeek+", "+d+mo+" "+y);


            String arrTime = mdata.get(position).getArrivalDateTime();
            String[] arrTimeseparated = arrTime.split("T");
            Date1 = arrTimeseparated[0];
            Time = arrTimeseparated[1];
            String[] separatedArrTime = Time.split(":");
            h = separatedArrTime[0];
            m = separatedArrTime[1];
            tvFlightDetailArrivalTime.setText(h + ":" + m);

            String[] septt = Date1.split("-");
            String ydd = septt[0];
            String modd = septt[1];
            String ddd = septt[2];

            String dateZonee= ddd+"/"+modd+"/"+ydd;
            date = new Date(dateZonee);
            s  = DateFormat.format("dd-MMM-yyyy", date.getTime());
            String daymonthYear1 = (String) s;
            String[] sep1 = daymonthYear1.split("-");
            String da = sep1[0];
            String mon = sep1[1];
            String ye = sep1[2];

            SimpleDateFormat sdateformat = new SimpleDateFormat("EEE");
            String day_Week = sdateformat.format(date);
            tvFlightDetailDestiDate.setText(day_Week+", "+da+mon+" "+ye);
        }
    }
}
