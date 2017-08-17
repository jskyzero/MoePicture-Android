# Android-Study

`jskyzero` `2017/08/09`

## 前言
+ Android 开发分为移动设备开发/系统开发/应用程序开发，这里我们主要讨论的是软件上使用系统接口/硬件上基于现有硬件和底层驱动的应用程序开发。
+ 学习前置知识：Java/Linux/数据库/网络协议/服务端经验/XML
+ 运行环境：这就很有意思了好像在Android 5.0及后续Android版本中ART作为正式的运行时库取代了以往的Dalvik虚拟机。具体的[安卓系统结构](https://hit-alibaba.github.io/interview/Android/basic/Android-Arch.html)参考下图

![安卓系统结构](https://raw.githubusercontent.com/HIT-Alibaba/interview/master/img/android-system-architecture.jpg)

## Lab0
+ Activities：应用由装着UI/Code的Activities组成
+ Intents：核心消息系统，包含要执行的动作，可以用于启动Activities或者部件间通信

## Lab1
+ View：TextView/RadioButton/Checkbox/Button/List/EditText
+ Layout：Linear/Table/Relative/Frame/Grid
+ 度量单位：sp所为文字大小的单位，dip作为其他元素的单位。

## Lab2
+ super.setContentView(R.layout.main_activity)
+ super.findViewById(R.id.signin_button)
```Java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, "ToastMessage", Toast.LENGTH_SHORT).show()
    }
})
```

```Java
final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
alertDialog.setTitle("Title").create();

alertDialog.show()
```

## Lab3
+ Activity：可视化用户界面
+ Service：后台服务
+ BroadcastReceiver：广播接收器
+ ContentProvider：跨应用数据交换标准
+ Intent：沟通桥梁