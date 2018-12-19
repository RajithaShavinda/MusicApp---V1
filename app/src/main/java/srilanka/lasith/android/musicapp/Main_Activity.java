package srilanka.lasith.android.musicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;

public class Main_Activity extends AppCompatActivity {

    RecyclerView rView;
    FirebaseDatabase fDb;
    DatabaseReference dRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = findViewById(R.id.r_view);
        rView.setHasFixedSize(true);


        rView.setLayoutManager(new LinearLayoutManager(this));


        fDb = FirebaseDatabase.getInstance();
        dRef = fDb.getReference("Data");

        Toast.makeText(Main_Activity.this," Loading...",Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();


        // Toast.makeText(Main_Activity.this," 1",Toast.LENGTH_LONG).show();
        FirebaseRecyclerAdapter<Song, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Song, ViewHolder>(
                Song.class,
                R.layout.row,
                ViewHolder.class,
                dRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Song model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getLink(), model.getDes(), model.getUrl());


            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);




                viewHolder.setOnCLickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Toast.makeText(Main_Activity.this," Please Wait!!",Toast.LENGTH_LONG).show();


                        TextView tname = view.findViewById(R.id.txt_name);
                        TextView tdes = view.findViewById(R.id.txt_des);
                        TextView turl = view.findViewById(R.id.txt_url);

                        ImageView img = view.findViewById(R.id.img_image);


                        String name = tname.getText().toString();
                        String des = tdes.getText().toString();
                        String url = turl.getText().toString();

                        Drawable drawable = img.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        Intent intent = new Intent(view.getContext(), Play_Activity.class);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();

                        intent.putExtra("image", bytes);
                        intent.putExtra("name", name);
                        intent.putExtra("des", des);
                        intent.putExtra("url", url);


                        startActivity(intent);


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


                return viewHolder;
            }
        };
        rView.setAdapter(firebaseRecyclerAdapter);


    }


    public void dataSearch(String text) {

        Query query = dRef.orderByChild("name").startAt(text).endAt(text + "\uf8ff");

        FirebaseRecyclerAdapter<Song, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Song, ViewHolder>(
                Song.class,
                R.layout.row,
                ViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Song model, int position) {

                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getLink(), model.getDes(), model.getUrl());
            }


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                viewHolder.setOnCLickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TextView tname = view.findViewById(R.id.txt_name);
                        TextView tdes = view.findViewById(R.id.txt_des);
                        TextView turl = view.findViewById(R.id.txt_url);

                        ImageView img = view.findViewById(R.id.img_image);


                        String name = tname.getText().toString();
                        String des = tdes.getText().toString();
                        String url = turl.getText().toString();

                        Drawable drawable = img.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        Intent intent = new Intent(view.getContext(), Play_Activity.class);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();

                        intent.putExtra("image", bytes);
                        intent.putExtra("name", name);
                        intent.putExtra("des", des);
                        intent.putExtra("url", url);
                        startActivity(intent);


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


                return viewHolder;
            }


        };
        rView.setAdapter(firebaseRecyclerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                dataSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                dataSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
