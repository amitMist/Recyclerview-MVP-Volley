package amit.recruitment.com.earthquake;

import android.content.Context;

/**
 * Created by amit on 3/22/18.
 */

public interface MainInteractor {

    void initNetworkCall(Context context, String url);
    void onDestroy();

}
