package meghna.com.blogistic1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_INVITE = 1;
    PhotoModal[] img;
    private GoogleApiClient mGoogleApiClient;
    private int idk;
    //private FirebaseAuth mAuth;
    private Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent() != null)
            idk = getIntent().getIntExtra("meghna.com.blogistic1.gemp",0);
        Method_img();
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(manager);
        inner adapter = new inner();
        rv.setAdapter(adapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new  ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .addApi(AppInvite.API)
                .build();
    }
    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }
    public void Method_img() {

        img = new PhotoModal[]{
                new PhotoModal(R.drawable.tech3, "TECHNOLOGY",1),
                new PhotoModal(R.drawable.start, "STARTUPS",2),
                new PhotoModal(R.drawable.market, "MARKETING",3),
                new PhotoModal(R.drawable.news1, "NEWS",4),
                new PhotoModal(R.drawable.photograph, "PHOTOGRAPHY",5),
                new PhotoModal(R.drawable.cooking, "Cooking",6),
                new PhotoModal(R.drawable.quotes, "Quotes",7),
                new PhotoModal(R.drawable.fashion1, "FASHION",8),
                new PhotoModal(R.drawable.game1, "GAMING",9),
                new PhotoModal(R.drawable.films1, "ENTERTAINMENT",10),
                new PhotoModal(R.drawable.travel, "TRAVEL",11),
                new PhotoModal(R.drawable.beauty, "BEAUTY",12),
        };
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Check how many invitations were sent.
                String[] ids = AppInviteInvitation
                        .getInvitationIds(resultCode, data);
            } else {
                // Sending failed or it was canceled, show failure message to
                // the user
                Toast.makeText(HomePageActivity.this, "Failed to send invitation.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_slideshow) {
            if (idk == 4) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // [START_EXCLUDE]
                                Intent inte = new Intent(HomePageActivity.this, LoginActivity.class);
                                startActivity(inte);
                          //       finish();
                                // [END_EXCLUDE]
                            }
                        });
            }
            else if(idk==2)
            {
                mRef = new Firebase(Constants.FIREBASE_URL);
                if (mRef.getAuth() == null)
                    {
                        Intent inte = new Intent(HomePageActivity.this, LoginActivity.class);
                        startActivity(inte);
                        //finish();
                    }
              //  mAuth = FirebaseAuth.getInstance();
                //mAuth.unauth();
                //       mAuth.signOut();
            }
        }
             else if (id == R.id.nav_manage) {
            sendInvitation();
            }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    class Holder extends RecyclerView.ViewHolder {
        ImageButton ib;
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            ib = (ImageButton) itemView.findViewById(R.id.img);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class inner extends RecyclerView.Adapter<Holder> {
        View.OnClickListener mOnClickListener;
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.photo_card_layout, parent, false);
            view.setOnClickListener(mOnClickListener);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {
            final int ima = img[position].imge;
            String s = img[position].text;
            if(ima>0)
              Glide.with(HomePageActivity.this).load(ima).into(holder.ib);
                holder.tv.setText(s);
          holder.ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomePageActivity.this, CategoriesActivity.class);
                    intent.putExtra("meghna.com.blogistic1.abc",img[position].pos);
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return img.length;
        }
    }
}

