package lens.inmo360;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.couchbase.lite.android.AndroidContext;

import java.util.ArrayList;

import lens.inmo360.daos.PropertiesDAO;
import lens.inmo360.dtos.PropertyDTO;
import lens.inmo360.managers.CouchBaseManager;
import lens.inmo360.model.Filter;
import lens.inmo360.model.House;
import lens.inmo360.model.Property;
import lens.inmo360.views.LoginActivity;
import lens.inmo360.views.MainFragment;
import lens.inmo360.views.SyncFragment;
import lens.inmo360.views.SyncLocalDataFragment;
import lens.inmo360.views.SyncServerDataFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SyncLocalDataFragment.FragmentUpdateListener,
        SyncServerDataFragment.ServerFragmentUpdateListener,
        MainFragment.OnSyncFragmentSelectedListener{

    private DrawerLayout mDrawer;
    private Toast mToast;
    private ListView mainListView ;
    private CouchBaseManager CBLManager = new CouchBaseManager();
    private SeekBar seekbar;
    private TextView seekBarValue;
    private LinearLayout province;
    private LinearLayout location;
    private LinearLayout category;
    private LinearLayout antiquity;
    private TextView province_filter;
    private TextView location_filter;
    private TextView category_filter;
    private TextView antiquity_filter;
    private Context context = this;
    private PropertyDTO dto = new PropertyDTO();
    private PropertyDTO finalDto = new PropertyDTO();
    private RadioGroup radioGroup;
    private RadioButton sales;
    private RadioButton rental;
    private EditText since;
    private EditText until;
    NavigationView navigationView = null;
    private ArrayList<String> cities = new ArrayList<String>();

    public NavigationView getNavDrawer(){
        return navigationView;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CBLManager.initCBL(new AndroidContext(this));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String  Email = sharedPreferences.getString("Email", "No Email") ;
        if(Email=="No Email") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Load the first Fragment
        Fragment mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentsContainer,mainFragment);
        fragmentTransaction.commit();

        // MY CODE
       // ArrayList<House> houseArray = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_filter_houses) {

            MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Filtro")
                .customView(R.layout.dialog_filterview, true)
                .positiveText(R.string.acceptButton)
                .neutralText(R.string.clearButton)
                .negativeText(R.string.cancelNegativeButton)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        try{dto.setMinPrice(Integer.parseInt(since.getText().toString()));}
                        catch(Exception e) {dto.setMinPrice(null);}
                        try{dto.setMaxPrice(Integer.parseInt(until.getText().toString()));}
                        catch(Exception e) {dto.setMaxPrice(null);}
                        finalDto.clone(dto);
                        dialog.dismiss();

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ((MainFragment)fragmentManager.getFragments().get(0)).UpdatePropertiesToShow(PropertiesDAO.getBy(finalDto));

                        //ArrayList<Property> props = PropertiesDAO.getBy(finalDto);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        province_filter.setText(R.string.select_filter);
                        location_filter.setText(R.string.select_filter);
                        category_filter.setText(R.string.select_filter);
                        antiquity_filter.setText(R.string.select_filter);
                        seekbar.setProgress(0);
                        radioGroup.clearCheck();
                        until.setText(null, TextView.BufferType.EDITABLE);
                        since.setText(null, TextView.BufferType.EDITABLE);
                        dto.clear();
                    }
                })
                .build();

            seekbar = (SeekBar)dialog.getCustomView().findViewById(R.id.seekbar);
            seekBarValue = (TextView)dialog.getCustomView().findViewById(R.id.seekbarvalue);
            province = (LinearLayout) dialog.getCustomView().findViewById(R.id.province);
            province_filter = (TextView) dialog.getCustomView().findViewById(R.id.province_filter);
            location = (LinearLayout)dialog.getCustomView().findViewById(R.id.region);
            location_filter = (TextView) dialog.getCustomView().findViewById(R.id.region_filter);
            category = (LinearLayout)dialog.getCustomView().findViewById(R.id.property);
            category_filter = (TextView) dialog.getCustomView().findViewById(R.id.property_filter);
            radioGroup = (RadioGroup)dialog.getCustomView().findViewById(R.id.radioGroup);
            sales = (RadioButton)dialog.getCustomView().findViewById(R.id.sales);
            rental = (RadioButton)dialog.getCustomView().findViewById(R.id.rental);
            since = (EditText) dialog.getCustomView().findViewById(R.id.editText1);
            until = (EditText) dialog.getCustomView().findViewById(R.id.editText2);
            antiquity = (LinearLayout)dialog.getCustomView().findViewById(R.id.antiquity);
            antiquity_filter = (TextView) dialog.getCustomView().findViewById(R.id.antiquity_filter);

            if(finalDto.getProvince()!=null)province_filter.setText(finalDto.getProvince());
            if(finalDto.getLocationsString()!=null) location_filter.setText(finalDto.getLocationsString());
            if(finalDto.getCategoriesString()!=null)category_filter.setText(finalDto.getLocationsString());
            if(finalDto.getRooms()!=null){
                Resources res = getResources();
                String[] ambient = res.getStringArray(R.array.ambientes);
                seekBarValue.setText(ambient[finalDto.getRooms()]);
                seekbar.setProgress(finalDto.getRooms());
            }
            if(finalDto.getOperation()!=null){
                if(finalDto.getOperation()=="Alquiler")rental.setChecked(true);
                if(finalDto.getOperation()=="Venta")sales.setChecked(true);
            }
            if(finalDto.getMinPrice()!=null){since.setText(finalDto.getMinPrice().toString(), TextView.BufferType.EDITABLE);}
            if(finalDto.getMaxPrice()!=null){until.setText(finalDto.getMaxPrice().toString(), TextView.BufferType.EDITABLE);}
            if(finalDto.getAntiquity()!=null){antiquity_filter.setText(finalDto.getAntiquity());}
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Resources res = getResources();
                        String[] ambient = res.getStringArray(R.array.ambientes);
                        seekBarValue.setText(ambient[progress]);
                        dto.setRooms(progress);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

            // Insert the fragment by replacing any existing fragment
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final ArrayList<String> provinces = ((MainFragment)fragmentManager.getFragments().get(0)).getPropertiesProvince();

                province.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context)
                                .title(getString(R.string.Province))
                                .items(provinces)
                                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        //showToast(text.toString());
                                        dto.setProvince(text.toString());
                                        if(province_filter.getText() != text){
                                            location_filter.setText(R.string.select_filter);
                                            dto.setLocation(null);
                                        }
                                        province_filter.setText(text);
                                        cities = ((MainFragment)fragmentManager.getFragments().get(0)).getPropertiesCitiesByProvince(dto.getProvince());
                                        if(text=="")province_filter.setText(R.string.select_filter);
                                        return true; // allow selection
                                    }
                                })
                                .positiveText(R.string.choose)
                                .show();
                    }
                });

                location.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context)
                                .title(getString(R.string.Region))
                                .items(cities)
                                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        StringBuilder str = new StringBuilder();
                                        for (int i = 0; i < which.length; i++) {
                                            if (i > 0) str.append(", ");
                                            str.append(text[i]);
                                        }
                                        //showToast(str.toString());
                                        String[] mEntriesString = new String[text.length];
                                        int i=0;
                                        for(CharSequence ch: text){
                                            mEntriesString[i++] = ch.toString();
                                        }
                                        dto.setLocation(mEntriesString);
                                        dto.setLocationsString(str.toString());
                                        location_filter.setText(str.toString());
                                        if(text.length==0)location_filter.setText(R.string.select_filter);
                                        return true; // allow selection
                                    }
                                })
                                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.clearSelectedIndices();
                                    }
                                })
                                .alwaysCallMultiChoiceCallback()
                                .positiveText(R.string.choose)
                                .autoDismiss(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .neutralText(R.string.clear_selection)
                                .show();
                    }
                });

                category.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context)
                                .title(R.string.category)
                                .items(R.array.category)
                                .itemsCallbackMultiChoice(new Integer[]{}, new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        StringBuilder str = new StringBuilder();
                                        for (int i = 0; i < which.length; i++) {
                                            if (i > 0) str.append(", ");
                                            str.append(text[i]);
                                        }
                                        //showToast(str.toString());
                                        String[] mEntriesString = new String[text.length];
                                        int i=0;
                                        for(CharSequence ch: text){
                                            mEntriesString[i++] = ch.toString();
                                        }
                                        dto.setCategory(mEntriesString);
                                        dto.setCategoriesString(str.toString());
                                        category_filter.setText(str.toString());
                                        if(text.length==0)category_filter.setText(R.string.select_filter);
                                        return true; // allow selection
                                    }
                                })
                                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.clearSelectedIndices();
                                    }
                                })
                                .alwaysCallMultiChoiceCallback()
                                .positiveText(R.string.choose)
                                .autoDismiss(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                    }
                                })
                                .neutralText(R.string.clear_selection)
                                .show();
                    }
                });

            antiquity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(context)
                            .title(getString(R.string.antiquity))
                            .items(R.array.antiguedad)
                            .itemsCallbackSingleChoice(10, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    //showToast(text.toString());
                                    dto.setAntiquity(text.toString());
                                    antiquity_filter.setText(text);
                                    if(text=="")antiquity_filter.setText(R.string.select_filter);
                                    return true; // allow selection
                                }
                            })
                            .positiveText(R.string.choose)
                            .show();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rental:
                if (checked)
                    dto.setOperation(getString(R.string.rental));
                    break;
            case R.id.sales:
                if (checked)
                    dto.setOperation(getString(R.string.sales));
                    break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(item.getItemId()) {
            case R.id.nav_sync:
                fragmentClass = SyncFragment.class;
                break;
            case R.id.nav_properties:
                fragmentClass = MainFragment.class;
                break;
            default:
                fragmentClass = MainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentsContainer, fragment).commit();

        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onPropertiesAdded() {

    }

    @Override
    public void onPropertiesDeleted(ArrayList<Property> properties) {

    }

    @Override
    public void onSyncFragmentSelected() {
        navigationView.setCheckedItem(R.id.nav_sync);
    }
}
