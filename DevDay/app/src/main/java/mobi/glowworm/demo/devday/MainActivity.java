package mobi.glowworm.demo.devday;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.supportv7.widget.decorator.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static mobi.glowworm.demo.devday.R.string.app_name;


public class MainActivity extends AppCompatActivity {

    private CollapsingToolbarLayout ctb;
    private int mutedColor;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Top toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* Bottom toolbar. */
        Toolbar bottomToolbar = (Toolbar) findViewById(R.id.bottom_toolbar);
        bottomToolbar.inflateMenu(R.menu.menu_bottom);

        /* Create and customize RecyclerView. */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add 8 cards
        MyAdapter adapter = new MyAdapter(new String[8]);
//        recyclerView.setAdapter(adapter);

        //Your RecyclerView
//        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Your RecyclerView.Adapter
//        SimpleAdapter mAdapter = new SimpleAdapter(this, new String[]{
//                "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
//        });


        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(1, "Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(2, "Section 3"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "Section 4"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, adapter);

        mSectionedAdapter.setSections(sections.toArray(new SimpleSectionedRecyclerViewAdapter.Section[sections.size()]));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(mSectionedAdapter);










        /* Collapsing toolbar */
        ctb = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctb.setTitle(getString(app_name));
        /* Define the image */
        ImageView image = (ImageView) findViewById(R.id.image);
        /* Decode bitmap from the image */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        /* Generate palette from the image bitmap */
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                       /* Set toolbar color from the palette */
                ctb.setContentScrimColor(mutedColor);
            }
        });

       /* NavigationView */
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // On click of menu icon on toolbar
//        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // On click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Set item in checked state
                menuItem.setChecked(true);

                //TODO: handle navigation

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                return true;
            }
        });

        /* Floating Action Button. */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Do something awesome?",
                        Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    /* Create RecyClerView Adapter. */
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView title;

            public ViewHolder(View v) {
                super(v);
                view = v;
                title = (TextView) v.findViewById(R.id.card_title);
            }
        }

        public MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
            return new ViewHolder(cardview);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText("Card " + (position + 1));
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}