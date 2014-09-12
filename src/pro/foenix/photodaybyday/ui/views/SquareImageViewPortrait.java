package pro.foenix.photodaybyday.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageViewPortrait extends ImageView {
	 public SquareImageViewPortrait(Context context) {
		    super(context);
		  }
 
		  public SquareImageViewPortrait(Context context, AttributeSet attrs) {
		    super(context, attrs);
		  }

		  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		    setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
		   /* int size = 0;
		    int width = getMeasuredWidth();
		    int height = getMeasuredHeight();
		 
		    if (width > height) {
		        size = height;
		    } else {
		        size = width;
		    }
		    setMeasuredDimension(size, size);*/
		  }
}
