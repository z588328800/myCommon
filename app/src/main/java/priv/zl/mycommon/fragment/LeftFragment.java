package priv.zl.mycommon.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import priv.zl.mycommon.R;
import priv.zl.mycommon.activity.MainActivity;

public class LeftFragment extends BaseFragment {
    ListView lvList;
    private int mPosition;
    private MyAdapter adapter;

    public static final String[] titles = {"标题1", "标题2", "标题3", "标题4", "标题5"};

    @Override
    protected void initView(View rootView) {
        lvList = rootView.findViewById(R.id.lv_list);
        setData();

    }

    @Override
    public int theFragmentLayoutId() {
        return R.layout.fragment_left;
    }

    @Override
    public void initData() {

    }

    private void setData() {
        mPosition = 0;
        adapter = new MyAdapter();
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                adapter.notifyDataSetChanged(); //刷新侧边栏 ，这里是为了改变侧边栏被选中的项目的颜色

               // toggle();//打开或者关闭侧边栏

                //侧边栏点击后执行的操作


            }
        });


    }


    //开关侧边栏
    private void toggle() {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu(); //获取侧边栏对象
        slidingMenu.toggle(); //如果当前状态是开，调用这个方法后就关闭；反之亦然
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public String getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(mActivity, R.layout.item_left_fragment, null);
            TextView tvList = convertView.findViewById(R.id.tv_list);
            tvList.setText(getItem(position));
            if (position == mPosition) {  //设置控件的enable，用于设置seletor的颜色
                tvList.setEnabled(true);
            } else {
                tvList.setEnabled(false);
            }

            return convertView;
        }
    }


}
