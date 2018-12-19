package srilanka.lasith.android.musicapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {


    private ViewHolder.ClickListener listener;
    View mView;


    public interface ClickListener{

        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);

    }


    public void setOnCLickListener(ViewHolder.ClickListener click_listener){
        listener=click_listener;
    }

    public ViewHolder(View itemView) {
        super(itemView);

        mView =itemView;


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // if (listener != null)


                    listener.onItemClick(v, getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null)

                {
                    listener.onItemLongClick(v, getAdapterPosition());
                }
                return true;
            }
        });
    }



    public void setDetails(Context ctx, String n, String l, String d, String u){

        TextView name= mView.findViewById(R.id.txt_name);
        TextView des= mView.findViewById(R.id.txt_des);
        TextView url= mView.findViewById(R.id.txt_url);

        ImageView img= mView.findViewById(R.id.img_image);

        name.setText(n);
        des.setText(d);
        url.setText(u);

        Picasso.get().load(l).into(img);


    }



}
