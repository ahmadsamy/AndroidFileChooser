package ahmad.egypt.myfilechooser;



import android.os.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AppCompatRecViewActivity extends AppCompatActivity
{
	protected RecyclerView.Adapter<?> mAdapter;

    protected RecyclerView mRecView;
    private Handler mHandler = new Handler();
    private boolean mFinishedStart = false;
    private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mRecView.focusableViewAvailable(mRecView);
        }
    };

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        ensureList();
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mRequestFocus);
        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mRecView =findViewById(R.id.recyclerview);
        if (mRecView == null) {
            throw new RuntimeException(
				"Your content must have a RecyclerView whose id attribute is " +
				"'recyclerview'");
        }
        mRecView.setLayoutManager(new LinearLayoutManager(this));
        mRecView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        if (mFinishedStart) {
            setRecViewAdapter(mAdapter);
        }
        mHandler.post(mRequestFocus);
        mFinishedStart = true;
    }

    public void setRecViewAdapter(RecyclerView.Adapter<?> adapter) {
        synchronized (this) {
            ensureList();
            mAdapter = adapter;
            mRecView.setAdapter(adapter);
        }
    }

    public RecyclerView getRecView() {
        ensureList();
        return mRecView;
    }

    public RecyclerView.Adapter<?> getRecViewAdapter() {
        return mAdapter;
    }

    private void ensureList() {
        if (mRecView != null) {
            return;
        }
        setContentView(R.layout.recylcerview_layout);
    }

}
