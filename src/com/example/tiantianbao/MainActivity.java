package com.example.tiantianbao;

import java.util.ArrayList;
import java.util.List;



import com.example.tiantianbao.Weather.OnClick;

import Tools.NameIDMap;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity  implements OnClick{
	private Weather weather;
	private ArrayAdapter<String> aa;
	private List<String> cityName ;
	private ListView city_name;
	private ActionBarDrawerToggle toggle;
	private DrawerLayout d_layout;
	private NameIDMap  nidm ;
    
	String head_text ;
	EditText head_e ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		city_name = (ListView) findViewById(R.id.city_name);
		d_layout = (DrawerLayout) findViewById(R.id.d_layout);

		weather = new Weather("101050101");
		cityName = new ArrayList<String>();
		nidm = new NameIDMap();

		cityName.add("哈尔滨");
		cityName.add("北京");
		cityName.add("昆明");
		cityName.add("大理");



		aa = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, cityName);
		//city_name.add
		
		//View view = inflater.inflate(R.layout.head,null);
		
		city_name.setAdapter(aa);
		toggle = new ActionBarDrawerToggle(this,  d_layout , R.drawable.ic_launcher, 
				R.string.open, 
				R.string.close);
		d_layout.setDrawerListener(toggle);      
		setList(); 
		// 给主页面中相关组件添加事件
		//        FragmentManager manager = getFragmentManager();
		//		//得到碎片的事物
		//		FragmentTransaction transaction = manager.beginTransaction();

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle b = new Bundle();
		b.putString("critId", "");
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.content_layout, weather);
		transaction.commit(); 
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){

			boolean flag = d_layout.isDrawerOpen(city_name);
			if(flag){//菜单是打开的状态
				d_layout.closeDrawer(city_name);
			}else{
				d_layout.openDrawer(city_name);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	 * 设置事件
	 * 
	 */
	public void setList(){
		city_name.setOnItemClickListener(new OnItemClickListener() {
			//替换碎片内容
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//Log.e("cityName.get(position)",cityName.get(position));
				String  ciry_id = nidm.get(cityName.get(position));
				//setId(ciry_id);
				if(ciry_id != null){
					Bundle b = new Bundle();
					b.putString("critId", ciry_id);
					//weather.setArguments(b);
					Weather  w =new Weather(ciry_id);
					//Fragment newf = new Ex;
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.add(R.id.content_layout,w);
					transaction.commit(); 
					
				}
			}
		});

		//删除城市
		city_name.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					cityName.remove(position);
					aa.notifyDataSetChanged();
				}
				return false;
			}
		});

	}

	@Override
	public void onclick() {	
		Button b1;
		Builder builder = new Builder(MainActivity.this);
		builder.setTitle("增加城市");
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		LinearLayout head = (LinearLayout)inflater.inflate(R.layout.head,null);	
		builder.setView(head);
		head_text = null;
		b1 = (Button)head.findViewById(R.id.head_sousu);

		
		head_e = (EditText)head.findViewById(R.id.head_text);
		head_text = head_e .getText().toString();
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(head_text != null && head_text.length() != 0){
					String name = nidm.get(head_text);
					if(name != null && name.length() != 0 ){
						
						if(cityName.indexOf(head_text) != -1 ){
							head_e.setText("以存在");
						}else{
							cityName.add(head_text);
							aa.notifyDataSetChanged();
							head_e.setText("添加成功");
						}
					}
					else{
						head_e.setText("搜索不到");
					}
				}
				else{
					head_e.setText("没有输入内容");
				}
			}
		});
		
		builder.show();
	}
}
