package masterung.androidthai.in.th.lovefriend.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import masterung.androidthai.in.th.lovefriend.R;

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.MyViewHolder>{

    private Context context;
    private List<String> titleNameStringList,
            lastPostStringList,
            pathIconStringList;

    private LayoutInflater layoutInflater;

    public ListFriendAdapter(Context context,
                             List<String> titleNameStringList,
                             List<String> lastPostStringList,
                             List<String> pathIconStringList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.titleNameStringList = titleNameStringList;
        this.lastPostStringList = lastPostStringList;
        this.pathIconStringList = pathIconStringList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recyclerview_list_friend, parent,
                false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String titleString = titleNameStringList.get(position);
        String lastPostString = lastPostStringList.get(position);
        String pathIconString = pathIconStringList.get(position);

        holder.titleNameTextView.setText(titleString);
        holder.lastPostTextView.setText(lastPostString);

        Picasso.get()
                .load(pathIconString)
                .resize(100,100)
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return titleNameStringList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titleNameTextView, lastPostTextView;
        private CircleImageView circleImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleNameTextView = itemView.findViewById(R.id.txtTitleName);
            lastPostTextView = itemView.findViewById(R.id.txtLastPost);
            circleImageView = itemView.findViewById(R.id.imvIcon);


        }
    }   // MyViewHolder Class

}   // Main Class
