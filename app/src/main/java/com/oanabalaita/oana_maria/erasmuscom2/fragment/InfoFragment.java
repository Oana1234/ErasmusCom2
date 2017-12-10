package com.oanabalaita.oana_maria.erasmuscom2.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oanabalaita.oana_maria.erasmuscom2.CategoriesAdapter;
import com.oanabalaita.oana_maria.erasmuscom2.Category;
import com.oanabalaita.oana_maria.erasmuscom2.R;

import java.util.ArrayList;
import java.util.List;


public class InfoFragment extends Fragment {


    private RecyclerView recyclerView;
    private CategoriesAdapter adapterCategory;
    private List<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info,container,false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_category);

        categoryList = new ArrayList<>();
        adapterCategory = new CategoriesAdapter(getActivity(), categoryList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCategory);

        prepareAlbums();



        return view;
    }


    private void prepareAlbums() {
        int[] picsCateg = new int[]{
                R.drawable.education5,
                R.drawable.administrativ3,
                R.drawable.sociala,
                R.drawable.food2,
                R.drawable.money,
                R.drawable.others

        };

        Category cat = new Category(picsCateg[0],"EDUCATION" );
        categoryList.add(cat);

        cat = new Category(picsCateg[1],"ADMINISTRATIVE");
        categoryList.add(cat);

        cat = new Category(picsCateg[2],"SOCIAL");
        categoryList.add(cat);

        cat = new Category(picsCateg[3],"GASTRONOMY");
        categoryList.add(cat);

        cat = new Category(picsCateg[4],"MONEY");
        categoryList.add(cat);

        cat = new Category(picsCateg[5],"OTHERS");
        categoryList.add(cat);

        adapterCategory.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}