package in.astroconsult.astroconsult;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import in.astroconsult.astroconsult.Response.WalletHistoryModelResponse;

public class WalletListAdapter extends BaseAdapter {

    private Context context;
    private List<WalletHistoryModelResponse> modelList;
    private LayoutInflater layoutInflater;
    String url = "https://ivminfotech.com/Urban_Clap/";
    //Dialog dialog;

    public WalletListAdapter(Context context, List<WalletHistoryModelResponse> modelList) {
        this.context = context;
        this.modelList = modelList;
        //this.dialog = dialog;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return modelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.wallet_layout,null);

        String date = modelList.get(position).getData().get(0).getDate();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Integer.parseInt(date)*1000L);
        String date1 = DateFormat.format("dd-MM-yyyy  hh:mm",calendar).toString();

        //TextView walletName = (TextView)view.findViewById(R.id.ptextWallet);
        TextView walletName1 = (TextView)view.findViewById(R.id.p1textWallet);
        TextView walletNamw2 = (TextView)view.findViewById(R.id.p2textWallet);
        TextView walletName3 = (TextView)view.findViewById(R.id.p3textWallet);
        //ImageView imageView = (ImageView)view.findViewById(R.id.tvimageView);

        //walletName.setText(modelList.get(position).getData().get(0).getRemark());
        walletName1.setText(modelList.get(position).getData().get(0).getRemark());
        walletNamw2.setText("Rs. "+modelList.get(position).getData().get(0).getAmount());
        walletName3.setText(modelList.get(position).getData().get(0).getPaymentId());
        //Picasso.get().load(url+modelList.get(position).getImage()).into(imageView);

        return view;
    }
}