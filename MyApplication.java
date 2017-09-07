package meghna.com.blogistic1;

import com.firebase.client.Firebase;

/**
 * Created by Meghna on 7/27/2016.
 */
public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
