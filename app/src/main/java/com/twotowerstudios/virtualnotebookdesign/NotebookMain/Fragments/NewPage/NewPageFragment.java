package com.twotowerstudios.virtualnotebookdesign.NotebookMain.Fragments.NewPage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.twotowerstudios.virtualnotebookdesign.Misc.Helpers;
import com.twotowerstudios.virtualnotebookdesign.Objects.Page;
import com.twotowerstudios.virtualnotebookdesign.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;


public class NewPageFragment extends DialogFragment implements CalendarDatePickerDialogFragment.OnDateSetListener {

	ArrayList<Page> list;
	Toolbar tbnewpage;
	EditText teNewpageName, teNewpageNumber;
	LinearLayout lldate;
	TextView tvDate;
	int accentColor;
	Calendar cal = Calendar.getInstance();
    private OnFragmentInteractionListener mListener;

    public NewPageFragment() {
        // Required empty public constructor
    }

	public interface OnFragmentInteractionListener {
		void onFragmentInteraction(String name, int pageNum, Calendar cal);
	}
    public static NewPageFragment newInstance(ArrayList<Page> list, int accentColor) {
        NewPageFragment fragment = new NewPageFragment();
        Bundle args = new Bundle();
		args.putParcelable("list", Parcels.wrap(list));
		args.putInt("accentColor", accentColor);
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
			list = Parcels.unwrap(getArguments().getParcelable("list"));
			accentColor = getArguments().getInt("accentColor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_page, container, false);
    }

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		tbnewpage = (Toolbar) v.findViewById(R.id.tbnewpage);
		teNewpageName = (EditText) v.findViewById(R.id.teNewpageName);
		teNewpageNumber = (EditText) v.findViewById(R.id.teNewpageNumber);
		lldate = (LinearLayout) v.findViewById(R.id.lldate);
		tvDate = (TextView) v.findViewById(R.id.tvDate);
		tbnewpage.setBackgroundColor(accentColor);
		if(Helpers.isColorDark(accentColor)) {
			tbnewpage.setTitleTextColor(Color.WHITE);
		}
		lldate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Newpagefragment","onClick called");
				CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
						.setOnDateSetListener(NewPageFragment.this);
				cdp.show(getFragmentManager(), "dateFrag");
			}
		});

		tbnewpage.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				boolean pageExists = false;
				for(Page page : list){
					if(page.getName().equalsIgnoreCase(teNewpageName.toString())){
						pageExists=true;
						Toast.makeText(getActivity(),"Page with the same title already exists", Toast.LENGTH_SHORT).show();
						break;
					}
				}
				if(!pageExists){
					int pageNum = Integer.parseInt(teNewpageNumber.getText().toString());
					mListener.onFragmentInteraction(teNewpageName.getText().toString(),pageNum,
					cal);
					dismiss();
				}
				return false;
			}
		});
		tbnewpage.setTitle("New Page");
		tbnewpage.inflateMenu(R.menu.newpage);

	}

	@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

	/**
	 * @param dialog      The view associated with this listener.
	 * @param year        The year that was set.
	 * @param monthOfYear The month that was set (0-11) for compatibility with {@link Calendar}.
	 * @param dayOfMonth  The day of the month that was set.
	 */
	@Override
	public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
		tvDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
		Log.d("OnDateSet", "Year == "+year);
		cal.set(year,monthOfYear, dayOfMonth);
	}

}
