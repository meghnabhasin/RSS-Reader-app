package meghna.com.blogistic1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CategoriesActivity extends AppCompatActivity {
    private IconModal[] t;
    int id[]=new int[6];
    private int idh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            idh = getIntent().getIntExtra("meghna.com.blogistic1.abc",0);
            switch (idh) {
                case 1:
                    technology();
                    break;
                case 2:
                    startups();
                    break;
                case 3:
                    marketing();
                    break;
                case 4:
                    news();
                    break;
                case 5:
                    photography();
                    break;
                case 6:
                    cooking();
                    break;
                case 7:
                    quotes();
                    break;
                case 8:
                    fashion();
                    break;
                case 9:
                    games();
                    break;
                case 10:
                    entertainment();
                    break;
                case 11:
                    travel();
                    break;
                case 12:
                    beauty();
                    break;
            }
        }
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvcat);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        RVAdapter adapter = new RVAdapter();
        rv.setAdapter(adapter);
    }

    public void technology() {
        t = new IconModal[]{
                new IconModal("The Verge", R.drawable.verge,1),
                new IconModal("TechCrunch", R.drawable.techcrunch,2),
                new IconModal("Android Central", R.drawable.android,3),
                new IconModal("Engadget", R.drawable.engadget,4),
                new IconModal("LifeHacker", R.drawable.lh,5),
                new IconModal("Wired",R.drawable.wired,6)
        };
    }

    public void beauty() {
        t = new IconModal[]{
                new IconModal("The Beauty Department", R.drawable.tbd,1),
                new IconModal("From Head To Toe", R.drawable.fht,2),
                new IconModal("Fashionista", R.drawable.fas,3),
                new IconModal("Refinery29", R.drawable.refinery,4),
                new IconModal("POPSUGAR Beauty", R.drawable.ps,5),
                new IconModal("Into The Gloss",R.drawable.itg,6)
        };
    }

    public void fashion() {
        t = new IconModal[]{
                new IconModal("Fashionista", R.drawable.fashionista,1),
                new IconModal("The Art of Manliness", R.drawable.man,2),
                new IconModal("Uncrate", R.drawable.uncrate,3),
                new IconModal("Cool Hunting", R.drawable.coolhunting,4),
                new IconModal("The Sartorialist", R.drawable.sator,5),
                new IconModal("Because Im Addicted",R.drawable.bia,6)
        };
    }

    public void startups() {
        t = new IconModal[]{
                new IconModal("Forbes-Entrepreneurs", R.drawable.images,1),
                new IconModal("VentureBeat", R.drawable.vb,2),
                new IconModal("Entrepreneur: Latest Articles", R.drawable.entre,3),
                new IconModal("Quick Sprout", R.drawable.quick,4),
                new IconModal("Blog- The Blog of Author Tim Ferriss", R.drawable.tfx,5),
                new IconModal("AVC",R.drawable.avc,6)
        };
    }

    public void entertainment() {
        t = new IconModal[]{
                new IconModal("Entertainment Weekly", R.drawable.ew,1),
                new IconModal("Bollywood Hungama", R.drawable.hungama,2),
                new IconModal("missmalini", R.drawable.missmal,3),
                new IconModal("Empire News", R.drawable.empirenews,4),
                new IconModal("/Film", R.drawable.fil,5),
                new IconModal("Hollywood Reporter",R.drawable.thr,6)
        };
    }

    public void news() {
        t = new IconModal[]{
                new IconModal("The Times Of India", R.drawable.toi,1),
                new IconModal("ABC News", R.drawable.abc,2),
                new IconModal("BBC", R.drawable.bbc,3),
                new IconModal("CNN", R.drawable.cnn,4),
                new IconModal("The Hindu", R.drawable.thehindu,5),
                new IconModal("Top Stories-Google News",R.drawable.googlenews,6)
        };
    }

    public void games() {
        t = new IconModal[]{
                new IconModal("Polygon", R.drawable.poly,1),
                new IconModal("IGN Video Gaming", R.drawable.ignn,2),
                new IconModal("Joystiq", R.drawable.joy,3),
                new IconModal("Kotaku", R.drawable.kota,4),
                new IconModal("Rock,Paper,Shotgun", R.drawable.rps,5),
                new IconModal("Penny Arcade",R.drawable.pennyarcade,6)
        };
    }

    public void cooking() {
        t = new IconModal[]{
                new IconModal("Food and Wine", R.drawable.faw,1),
                new IconModal("Baking Bite", R.drawable.bb,2),
                new IconModal("Pinch of Yum", R.drawable.poy,3),
                new IconModal("Cooking light", R.drawable.cooklight,4),
                new IconModal("Taste of Home", R.drawable.toh,5),
                new IconModal("Indian Simmer",R.drawable.indiansimmer,6)
        };
    }

    public void marketing() {
        t = new IconModal[]{
                new IconModal("Mashable", R.drawable.mashable,1),
                new IconModal("Quick Sprout", R.drawable.quick,2),
                new IconModal("HubSpot", R.drawable.hs,3),
                new IconModal("MozBlog", R.drawable.mozlogo,4),
                new IconModal("CopyBlogger", R.drawable.cb,5),
                new IconModal("Marketing Land",R.drawable.ml,6)
        };
    }

    public void quotes() {
        t = new IconModal[]{
                new IconModal("Brainy Quotes", R.drawable.bq,1),
                new IconModal("Theysaidso", R.drawable.bq,2),
                new IconModal("Quotes Daddy", R.drawable.qd,3),
                new IconModal("Quote Snack", R.drawable.qs,4),
                new IconModal("Daily-motivational-Quote", R.drawable.dmq,5)
        };
    }

    public void travel() {
        t = new IconModal[]{
                new IconModal("Intelligent Travel", R.drawable.it,1),
                new IconModal("The Points Guy",R.drawable.tpg,2),
                new IconModal("Travel+Leisure", R.drawable.tl,3),
                new IconModal("Million Mile Secrets", R.drawable.mms,4),
                new IconModal("Stuck in Customs", R.drawable.sic,5),
                new IconModal("NYT > Travel",R.drawable.nyt,6)
        };
    }

    public void photography() {
        t = new IconModal[]{
                new IconModal("The Impossible Cool", R.drawable.tic,1),
                new IconModal("The Big Picture", R.drawable.tbp,2),
                new IconModal("National Geographic", R.drawable.ng,3),
                new IconModal("Digital Photography School", R.drawable.dps,4),
                new IconModal("500px: Editors' Choice", R.drawable.ec,5),
                new IconModal("Stuck in Customs",R.drawable.sic,6)
        };
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.im);
           // name.setOnClickListener(this);
        }
    }

    class RVAdapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.categories_card_layout, parent, false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder,final int position) {
            String nam = t[position].name;
            int ima = t[position].icon;
            holder.name.setText(nam);
            Glide.with(CategoriesActivity.this).load(ima).into(holder.image);
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(CategoriesActivity.this,FeedsActivity.class);
                    in.putExtra("meghna.com.blogistic1.abcd",t[position].pos);
                    in.putExtra("meghna.com.blogistic1.ab",idh);
                    startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return t.length;
        }
    }
}
