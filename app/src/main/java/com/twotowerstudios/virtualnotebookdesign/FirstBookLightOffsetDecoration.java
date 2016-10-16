package com.twotowerstudios.virtualnotebookdesign;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Panther II on 14/10/2016.
 */


class FirstBookLightOffsetDecoration extends RecyclerView.ItemDecoration {
	private int firstItemOffset;

	public FirstBookLightOffsetDecoration(int beginningOffset) {
		firstItemOffset = beginningOffset;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		int dataSize = state.getItemCount();
		int position = parent.getChildAdapterPosition(view);
		if (dataSize > 0 && position ==  0) {
			outRect.set(firstItemOffset, 0, 0, 0);
		} else {
			outRect.set(0, 0, 0, 0);
		}
		/**if (dataSize > 0 && position == dataSize-1){
			outRect.set(0,0,mBottomOffset,0);
		}else {
			outRect.set(0, 0, 0, 0);
		}*/

	}
}