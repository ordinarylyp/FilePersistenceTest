package lyp.com.filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.edit);
        String inputText = Load();
        if(!TextUtils.isEmpty(inputText)){
            edit.setText(inputText);
            edit.setSelection(inputText.length());// 将输入光标移到文本末尾位置
            Toast.makeText(this,"重启读取存储的内容成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        Save(inputText);
    }
    //将内容存储在文件中
    public void Save(String inputText){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            // openFileOutput方法接收两个参数：
            // 第一个是不包含路径的文件名 (默认存储到/data/data/<package name>/files/目录下)
            // 第二个是文件的操作模式,有两种可选：
            // 1.MODE_PRIVATE(默认)：当指定同样文件名的时候,所写入的内容将会覆盖原文件中的内容
            // 2.MODE_APPEND：若该文件已存在就往文件里面追加内容,不存在就创建新文件。
            // (注：其他操作模式过于危险,在 Android 4.2 已被废弃)
            // openFileOutput方法返回的是一个FileOutputStream对象

            out=openFileOutput("data", Context.MODE_PRIVATE);
            // 构建OutputStreamWriter对象后,构建BufferedWriter对象
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //将文本内容写入文件中
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //从文件中读取数据
    public String Load(){
        FileInputStream in = null;
        BufferedReader reader =null;
        StringBuilder content = new StringBuilder();
        try{
            // openFileInput只接收一个参数，即要读取的文件名,然后系统会自动到
            // /data/data/<package name>/files/目录下去加载这个文件,并返回一个FileInputStream对象.
            in = openFileInput("data");
            // 构建InputStreamReader对象后,构建BufferedReader对象
            reader = new BufferedReader( new InputStreamReader(in));
            String line = "";
            // 读取内容
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
