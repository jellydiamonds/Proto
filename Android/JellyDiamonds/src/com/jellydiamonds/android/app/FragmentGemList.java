package com.jellydiamonds.android.app;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class FragmentGemList extends ListFragment {
	
	private List<Object> mItems = null;
	
	
	public FragmentGemList()
	{
		
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        //setListAdapter(new ArrayAdapter<String>(getActivity(),
        //        R.layout., new String[] {"Ceci","est","un","test"} ));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        //View detailsFrame = getActivity().findViewById(R.id.details);
        //mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        //if (savedInstanceState != null) {
            // Restore last state for checked position.
            //mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        //}

        //if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            //showDetails(mCurCheckPosition);
        //}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //showDetails(position);
    }

	
	
}
