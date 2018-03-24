package amit.recruitment.com.earthquake;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import amit.recruitment.com.earthquake.data.Earthquake;
import amit.recruitment.com.earthquake.interfaces.MainView;

/**
 * @author amit kumar saha
 * Created by amit on 3/23/18.
 */

public class MainActivity extends AppCompatActivity implements MainView,SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;

    LinearLayoutManager linearLayoutManager;
    EarthquakeAdapter mEarthquakeAdapter;

    private MainPresenterImpl mMainPresenter;


    private boolean wasLoadingState=false;
    private boolean wasRestoringState=false;

    private final String LOADING_TAG = "MainActivity_LOADING";
    private final String CONTENT_TAG = "MainActivity_CONTENT";
    private final String STATE_TAG = "MainActivity_KeyForLayoutManagerState";

    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.eqRecyclerList);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(this);
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        if(savedInstanceState!=null){
            wasLoadingState = savedInstanceState.getBoolean(LOADING_TAG,false);
            wasRestoringState = savedInstanceState.getBoolean(CONTENT_TAG,false);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(STATE_TAG);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter = new MainPresenterImpl(this);

        if(wasLoadingState){
            // it was loading already so restart fetching anyway
            mMainPresenter.getDataForList(getApplicationContext(),false);
        }else{
            // it was not loading now it wither restores cached data or fetch from network
            mMainPresenter.getDataForList(getApplicationContext(),wasRestoringState);
        }

    }

    @Override
    public void onGetDataSuccess(String message, List<Earthquake> list) {
        mEarthquakeAdapter = new EarthquakeAdapter(getApplicationContext(),list);
        mRecyclerView.setAdapter(mEarthquakeAdapter);

        if(savedRecyclerLayoutState!=null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

        }
        savedRecyclerLayoutState=null;

    }

    @Override
    public void onGetDataFailure(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        hideProgress();
        /*mProgressDialog = DelayedProgressDialog.make(this,getString(R.string.dialog_title),getString(R.string.dialog_message),true);
        mProgressDialog.show();*/
        mSwipeContainer.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        /*if(mProgressDialog!=null){
            mProgressDialog.dismiss();
            mProgressDialog=null;
        }*/

        if(mSwipeContainer!=null && mSwipeContainer.isRefreshing()){
            mSwipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if(mEarthquakeAdapter!=null && mEarthquakeAdapter.getItemCount()!=0){
            // for data restoring purpose
            outState.putBoolean(CONTENT_TAG,true);
        }else{
            outState.putBoolean(CONTENT_TAG,false);
        }

        if(mSwipeContainer!=null && mSwipeContainer.isRefreshing()){
            // saving the loading state
            outState.putBoolean(LOADING_TAG,true);
        }else{
            outState.putBoolean(LOADING_TAG,false);
        }
        /*if(mProgressDialog!=null && mProgressDialog.isShowing()){
            // saving the loading state
            outState.putBoolean(LOADING_TAG,true);
        }else{
            outState.putBoolean(LOADING_TAG,false);
        }*/

        outState.putParcelable(STATE_TAG, linearLayoutManager.onSaveInstanceState());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        boolean isRestoringVal=false;
        boolean isLoadingState=false;

        if(savedInstanceState!=null){
            isRestoringVal= savedInstanceState.getBoolean(CONTENT_TAG,false);
            isLoadingState = savedInstanceState.getBoolean(LOADING_TAG,false);
        }
        if(isLoadingState){
            // it was loading already so restart fetching anyway
            mMainPresenter.getDataForList(getApplicationContext(),false);
        }else{
            // it was not loading now it wither restores cached data or fetch from network
            mMainPresenter.getDataForList(getApplicationContext(),isRestoringVal);
        }
        savedRecyclerLayoutState = savedInstanceState.getParcelable(STATE_TAG);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_earthquake, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mMainPresenter.getDataForList(getApplicationContext(),false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        mMainPresenter.onDestroy();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        //force refresh
        mMainPresenter.getDataForList(getApplicationContext(),false);
    }
}
