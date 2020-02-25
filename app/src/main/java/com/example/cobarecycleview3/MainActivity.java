package com.example.cobarecycleview3;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cobarecycleview3.adapter.HotelAdapter;
import com.example.cobarecycleview3.model.Hotel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HotelAdapter.IHotelAdapter {

    public static final String HOTEL = "hotel";
    ArrayList<Hotel> mList = new ArrayList<>();
    ArrayList<Hotel> mListAll = new ArrayList<>();
    HotelAdapter mAdapter;
    boolean isFiltered;
    ArrayList<Integer> mListMapFilter = new ArrayList<>();
    String mQuery;
    static final int REQUEST_CODE_EDIT = 99;
    static final int REQUEST_CODE_ADD = 88;
    int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new HotelAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
        fillData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdd();
            }
        });
    }

    private void goAdd() {
        startActivityForResult(new Intent(this, InputActivity.class), REQUEST_CODE_ADD);
    }

    private void fillData() {
        Resources resources = getResources();
        String[] arJudul = resources.getStringArray(R.array.places);
        String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
        String[] arDetail = resources.getStringArray(R.array.place_details);
        String[] arLokasi = resources.getStringArray(R.array.place_locations);
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        String[] arFoto = new String[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            int id = a.getResourceId(i, 0);
            arFoto[i] = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(id) + '/'
                    + resources.getResourceTypeName(id) + '/'
                    + resources.getResourceEntryName(id);
//            arFoto[i] = a.getDrawable(i);
//            BitmapDrawable bd = (BitmapDrawable) a.getDrawable(i);
//            RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(getResources(), bd.getBitmap());
//            rbd.setCircular(true);
//            arFoto[i] = rbd;
        }
        a.recycle();

        for (int i = 0; i < arJudul.length; i++) {
            mList.add(new Hotel(arJudul[i], arDeskripsi[i], arDetail[i], arLokasi[i], arFoto[i]));
        }
        mAdapter.notifyDataSetChanged();
    }

//    public class MainActivity extends AppCompatActivity implements HotelAdapter.IHotelAdapter{
//        public static final String HOTEL = "hotal";
//        ArrayList<Hotel> mList = new ArrayList<>();
//        HotelAdapter mAdapter;
//
//        @Override
//        public void doClick(int pos){
//            Intent intent = new Intent(this, DetailActivity.class);
//            intent.putExtra(HOTEL, mList.get(pos));
//            startActivity(intent);
//        }

//        @Override
//        protected void onCreate(Bundle savedInstanceState){
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        }
//    }

//    ArrayList

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQuery = newText.toLowerCase();
                doFilter(mQuery);
                return true;
            }
        });
        return true;
    }

    private void doFilter(String query) {
        if (!isFiltered) {
            mListAll.clear();
            mListAll.addAll(mList);
            isFiltered = true;
        }

        mList.clear();
        if (query.isEmpty()) {
            mList.addAll(mListAll);
            isFiltered = false;
        } else {
            mListMapFilter.clear();
            for (int i = 0; i < mListAll.size(); i++) {
                Hotel hotel = mListAll.get(i);
                if (hotel.judul.toLowerCase().contains(query) || hotel.deskripsi.toLowerCase().contains(query) || hotel.lokasi.toLowerCase().contains(query)) {
                    mList.add(hotel);
                    mListMapFilter.add(i);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(HOTEL, mList.get(pos));
        startActivity(intent);
    }

    @Override
    public void doEdit(int pos) {
        itemPos = pos;
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra(HOTEL, mList.get(pos));
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    public void doDelete(int pos) {
        itemPos = pos;
        final Hotel hotel = mList.get(pos);
        mList.remove(itemPos);
        if (isFiltered) mListAll.remove(mListMapFilter.get(itemPos).intValue());
        mAdapter.notifyDataSetChanged();
        Snackbar.make(findViewById(R.id.fab), hotel.judul + " Terhapus.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.add(itemPos, hotel);
                if (isFiltered) mListAll.add(mListMapFilter.get(itemPos), hotel);
                mAdapter.notifyDataSetChanged();
            }
        }).show();
    }

    @Override
    public void doFav(int pos) {
        // belum ditambahkan
    }

    @Override
    public void doShare(int pos) {
        // belum ditambahkan
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            Hotel hotel = (Hotel) data.getSerializableExtra(HOTEL);
            mList.add(hotel);
            if (isFiltered) mListAll.add(hotel);
            doFilter(mQuery);
            //mAdapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            Hotel hotel = (Hotel) data.getSerializableExtra(HOTEL);
            mList.remove(itemPos);
            if (isFiltered) mListAll.remove(mListMapFilter.get(itemPos).intValue());
            mList.add(itemPos, hotel);
            if (isFiltered) mListAll.add(mListMapFilter.get(itemPos), hotel);
            mAdapter.notifyDataSetChanged();
        }
    }
}
