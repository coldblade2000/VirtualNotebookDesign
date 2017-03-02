package com.twotowerstudios.virtualnotebookdesign.NotebookSelection;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.twotowerstudios.virtualnotebookdesign.R;

/**
 * Created by Panther II on 15/11/2016.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
									   FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
				super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
						nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
							   View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
				dyUnconsumed);

		if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
			child.hide();

		} else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
			if (child.getId()== R.id.fabSelection){
				if(NotebookSelection.isMainfabOpen()){
					child.show();
				}else{

				}
			}else{
				child.show();
			}

		}
	}
	public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
		super();
	}
}