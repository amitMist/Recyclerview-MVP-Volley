package amit.recruitment.com.earthquake.interfaces;

import android.content.Context;

/**
 * Created by amit on 3/23/18.
 */

public interface MainInteractor {

    void initNetworkCall(Context context, String url);
    void onDestroy();

}
