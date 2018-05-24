FilePersistenceTest
===================================
Android 文件存储最重要的就是将数据存储到文件中和从文件中读取数据
#### 将数据存储在文件中
最主要的代码是save()
```Java
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
```
Context类提供了一个openFileOutput()方法，可以用于将数据存储在指定的文件中。openFileOutput方法接收两个参数：第一个是不包含路径的
文件名 (默认存储到/data/data/<package name>/files/目录下)第二个是文件的操作模式,有两种可选：1.MODE_PRIVATE(默认)：当指定同样文件
名的时候,所写入的内容将会覆盖原文件中的内容。2.MODE_APPEND：若该文件已存在就往文件里面追加内容,不存在就创建新文件。(注：其他操作模式过
于危险,在 Android 4.2 已被废弃)，还需要注意的是openFileOutput方法返回的是一个FileOutputStream对象。
#### 从文件中读取数据
load()方法
```Java

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
```
Context 类中还提供了一个 openFileInput() 方法，用于从文件中读取数据。openFileInput只接收一个参数，即要读取的文件名,然后系统会自动到
/data/data/<package name>/files/目录下去加载这个文件,并返回一个FileInputStream对象。
#### 最后
将上诉代码整合到[MainActivity](/app/src/main/java/lyp/com/filepersistencetest/MainActivity.java)中，
并改变[activity_main.xml](/app/src/main/res/layout/activity_main.xml)中的布局，验证代码。
使用Android Device Monitor工具打开，查看应用的数据

![data](/img/data.png "data")

最后运行的结果，吧数据输入之后返回重启应用之后的运行结果为：

![memory](/img/memory.png "memory")








