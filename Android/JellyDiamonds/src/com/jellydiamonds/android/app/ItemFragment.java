package com.jellydiamonds.android.app;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v4.app.Fragment;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the
 * ListView with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemFragment extends Fragment implements OnItemClickListener {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

	// TODO: Rename and change types of parameters

	//private OnFragmentInteractionListener mListener;
	private static ItemFragment mInstance = null;
	
	/**
	 * The fragment's ListView/GridView.
	 */
	private ListView mListView = null;
	private TextView mTextView = null;
	/**
	 * The Adapter which will be used to populate the ListView/GridView with
	 * Views.
	 */
	private ListAdapter mAdapter;

	// TODO: Rename and change types of parameters
	public static ItemFragment newInstance() {
		
		if( ItemFragment.mInstance == null )
			ItemFragment.mInstance = new ItemFragment();
		
		return ItemFragment.mInstance;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemFragment() {
	}

	public void setAdapter( ListAdapter listAdapter )
	{
		this.mAdapter = listAdapter;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_list, container, false);

		// Set the adapter
		mListView = (ListView) view.findViewById(R.id.list_display);
		mTextView = (TextView) view.findViewById(R.id.empty_list);
		
		((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
		mListView.setEmptyView(mTextView);
		// Set OnItemClickListener so we can be notified on item clicks
		mListView.setOnItemClickListener(this);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		/*try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}*/
	}

	@Override
	public void onDetach() {
		super.onDetach();
		//mListener = null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Display detailedView
		android.app.FragmentManager l_fragment_manager = null;
		GemDetailedFragment l_gemDetailedFragment = null;
		
		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		
		Log.d("ITEMFRAGMENT", " item number " + position + " was clicked (id=" + id +")");
		l_fragment_manager = getFragmentManager();
		l_gemDetailedFragment = GemDetailedFragment.newInstance();
		
		l_fragment_manager.beginTransaction().replace(R.id.content_frame,l_gemDetailedFragment).commit();
		
	}

	/**
	 * The default content for this Fragment has a TextView that is shown when
	 * the list is empty. If you would like to change the text, call this method
	 * to supply the text it should use.
	 */
	public void setEmptyText(CharSequence emptyText) {
		View emptyView = mListView.getEmptyView();

		if (emptyText instanceof TextView) {
			((TextView) emptyView).setText(emptyText);
		}
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	/*public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(String id);
	}*/

}
