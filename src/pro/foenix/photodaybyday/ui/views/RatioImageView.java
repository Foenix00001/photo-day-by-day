package pro.foenix.photodaybyday.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RatioImageView extends ImageView {
	private static final float RATIO = 3f / 2f;

	public RatioImageView(Context context) {
		super(context);
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
		  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		   
		      int width = getMeasuredWidth();
		      int height = getMeasuredHeight();
		      int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
		      int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();
		   
		      int maxWidth = (int) (heigthWithoutPadding * RATIO);
		      int maxHeight = (int) (widthWithoutPadding / RATIO);
		   
		      if (widthWithoutPadding  > maxWidth) {
		          width = maxWidth + getPaddingLeft() + getPaddingRight();
		      } else {
		          height = maxHeight + getPaddingTop() + getPaddingBottom();
		      }
		   
		      setMeasuredDimension(width, height);
		  }
}
