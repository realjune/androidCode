


package org.accenture.product.lemonade;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


/**
 * Represents a set of icons chosen by the user or generated by the system.
 */
public class Folder extends LinearLayout implements DragSource, OnItemLongClickListener,
        OnItemClickListener, OnClickListener, View.OnLongClickListener {

    protected AbsListView mContent;
    protected DragController mDragController;

    protected Launcher mLauncher;

    protected Button mCloseButton;

    protected FolderInfo mInfo;

    /**
     * Which item is being dragged
     */
    protected ShortcutInfo mDragItem;

    /**
     * Used to inflate the Workspace from XML.
     *
     * @param context The application's context.
     * @param attrs The attribtues set containing the Workspace's customization values.
     */
    public Folder(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAlwaysDrawnWithCacheEnabled(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mContent = (AbsListView) findViewById(R.id.folder_content);
        mContent.setOnItemClickListener(this);
        mContent.setOnItemLongClickListener(this);

        mCloseButton = (Button) findViewById(R.id.folder_close);
        mCloseButton.setOnClickListener(this);
        mCloseButton.setOnLongClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        ShortcutInfo app = (ShortcutInfo) parent.getItemAtPosition(position);
        int[] pos = new int[2];
        v.getLocationOnScreen(pos);
        app.intent.setSourceBounds(new Rect(pos[0], pos[1],
                pos[0] + v.getWidth(), pos[1] + v.getHeight()));
        mLauncher.startActivitySafely(app.intent, app);
    }

    public void onClick(View v) {
        mLauncher.closeFolder(this);
    }

    public boolean onLongClick(View v) {
        mLauncher.closeFolder(this);
        mLauncher.showRenameDialog(mInfo);
        return true;
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (!view.isInTouchMode()) {
            return false;
        }

        ShortcutInfo app = (ShortcutInfo) parent.getItemAtPosition(position);

        mLauncher.showActions(app, view, new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                mLauncher.closeFolder(Folder.this);
            }
        });
        mDragController.startDrag(view, this, app, DragController.DRAG_ACTION_COPY);
        mDragItem = app;

        return true;
    }

    public void setDragController(DragController dragController) {
        mDragController = dragController;
    }

    public void onDropCompleted(View target, boolean success) {
    }

    /**
     * Sets the adapter used to populate the content area. The adapter must only
     * contains ShortcutInfo items.
     *
     * @param adapter The list of applications to display in the folder.
     */
    void setContentAdapter(BaseAdapter adapter) {
        mContent.setAdapter(adapter);
    }

    void notifyDataSetChanged() {
        ((BaseAdapter) mContent.getAdapter()).notifyDataSetChanged();
    }

    void setLauncher(Launcher launcher) {
        mLauncher = launcher;
    }

    /**
     * @return the FolderInfo object associated with this folder
     */
    FolderInfo getInfo() {
        return mInfo;
    }

    // When the folder opens, we need to refresh the GridView's selection by
    // forcing a layout
    void onOpen() {
        mContent.requestLayout();
    }

    void onClose() {
        final Workspace workspace = mLauncher.getWorkspace();
        workspace.getChildAt(workspace.getCurrentScreen()).requestFocus();
    }

    void bind(FolderInfo info) {
        mInfo = info;
        mCloseButton.setText(info.getTitle(mLauncher.getIconCache()));
    }
}
