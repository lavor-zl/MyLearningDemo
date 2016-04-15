package com.example.shitian.sugarormdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


public class SugarORMMainActivity extends Activity {

    private android.widget.Button add;
    private android.widget.Button delete;
    private android.widget.Button update;
    private android.widget.Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_ormmain);
        this.query = (Button) findViewById(R.id.query);
        this.update = (Button) findViewById(R.id.update);
        this.delete = (Button) findViewById(R.id.delete);
        this.add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SugarORMMainActivity.this,AddActivity.class);
//                startActivity(intent);
                //新增一条数据
                Person person=new Person(1,"lavor","男");
                person.save();
                //增加多条数据
                List<Person> persons=new ArrayList<Person>();
                persons.add(new Person(12,"lily","女"));
                persons.add(new Person(13,"lucy","女"));
                Person.saveInTx(persons);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SugarORMMainActivity.this,DeleteActivity.class);
//                startActivity(intent);
                //删除一条数据
                Person person= Person.findById(Person.class,1);
                person.delete();

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SugarORMMainActivity.this,UpdateActivity.class);
//                startActivity(intent);
                //更新数据
                Person person= Person.findById(Person.class,1);
                person.name="jack";
                person.save();
                //我们还可以通过唯一值的字段来更新数据
                Person person1= new Person(2,"mike","男");
                person1.save();
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(SugarORMMainActivity.this,QueryActivity.class);
//                startActivity(intent);
                //查询数据
                Person person= Person.findById(Person.class,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sugar_ormmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
