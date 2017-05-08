package com.nic.civilsurgeonnic;

/**
 * Created by Sehaj Risham on 3/6/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentInstitute;
import com.nic.civilsurgeonnic.TabbedFragments.FragmentMedicine;
import com.nic.civilsurgeonnic.TabbedFragments.TabbedFragment_DrugDetail;
import com.nic.civilsurgeonnic.TabbedFragments.TabbedFragment_InstituteDetail;

import utils.DbHandler;
import utils.RVAdapterDrugDetail;
import utils.RVAdapterInstituteDetail;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;
    FragmentMedicine med;
    FragmentInstitute ins;
    TabbedFragment_DrugDetail drug;
    TabbedFragment_InstituteDetail institute;

    MaterialSearchView searchView;
    RVAdapterDrugDetail rv1;
    RVAdapterInstituteDetail rv2;
    RecyclerView lvItems;
    DbHandler dbh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        searchView=(MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);





        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        dbh=new DbHandler(getActivity());
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        if(viewPager.getCurrentItem()==0)
        {
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    if(newText.equals("")) {

                        rv2 = new RVAdapterInstituteDetail(dbh.get_institute_detail(null));
                        lvItems.setAdapter(rv2);
                        rv2.notifyDataSetChanged();
                    }
                    else {
                        rv2 = new RVAdapterInstituteDetail(dbh.get_institute_detail(newText));
                        lvItems.setAdapter(rv2);
                        rv2.notifyDataSetChanged();
                    }

                    return false;
                }
            });
        }
        else
        {
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {


                    if(newText.equals("")) {

                        rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(null));
                        lvItems.setAdapter(rv1);
                        rv1.notifyDataSetChanged();
                    }
                    else {
                        rv1 = new RVAdapterDrugDetail(dbh.get_drug_detail(newText));
                        lvItems.setAdapter(rv1);
                        rv1.notifyDataSetChanged();
                    }

                    return false;
                }
            });
        }

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;




    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 :
                    institute=new TabbedFragment_InstituteDetail();
                    return institute;

                case 1 :
                    drug= new TabbedFragment_DrugDetail();
                    return drug;


            }
            if(position==0)
            {

            }
            else if (position==1)
            {

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Institute";
                case 1 :
                    return "Medicine";
            }
            return null;
        }
    }

}
