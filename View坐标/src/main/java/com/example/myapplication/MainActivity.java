package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 注意：调用View的scrollTo()和scrollBy()是用于滑动View中的内容，而不是把某个View的位置进行改变。如果想改变莫个View在屏幕中的位置，可以使用如下的方法。
 * <p>
 * 调用public void offsetLeftAndRight(int offset)用于左右移动方法或public void offsetTopAndBottom(int offset)用于上下移动。
 * <p>
 * 如：button.offsetLeftAndRight(300)表示将button控件向左移动300个像素。
 * <p>
 * <p>
 * scrollTo(int x, int y) 是将View中内容滑动到相应的位置，参考的坐标系原点为parent View的左上角。
 * <p>
 * 从源码中可以看出，scrollTo移动的目标位置x和y值是以初始化的mScrollX和mScrollY为参考点的，只要位置发生偏移，就对mScrollX和mScrollY赋新值。注意，这里的（x，y）理解为针对初始化值的偏移量，比如，我想移动到（100,100）这个位置，那么偏移量就是（0,0）-（100,100）=（-100，100），所以调用的时候就是view.scrollTo（-100, -100），这样才能达到我们想要的偏移
 */
public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.show_tv);
    }

    /**
     * mScrollX：表示离视图起始位置的x水平方向的偏移量
     * mScrollY：表示离视图起始位置的y垂直方向的偏移量
     * 分别通过getScrollX() 和getScrollY()方法获得。
     * 注意：mScrollX和mScrollY指的并不是坐标，而是偏移量。
     * <p>
     * <p>
     * “滚动”的动作是由scrollTo()与scrollBy()方法产生的，但是他们都是瞬时完成“滚动”这一动作，而Scroller类就是在这一过程中添加中间过程，从而产生一个自然的动画。
     * <p>
     * 上面已经说明过Scroller类只是产生一个滚动的动画，而“滚动”这一动作还是由scrollTo()或者scrollBy()这两个方法完成的。现在把Scroller类的用法添加到上面演示scrollTo()方法的实例中去（只显示要改动代码的那部分）。如下：
     */
    int x = 2;
    int y = 2;

    public void test(View view) {

        x += 2;
        y += 2;
        //        view.scrollBy(x,y);
        //              view.scrollTo(20,20);//视图内容的移动,X轴，Y轴反向的移动
        view.setTranslationX(50);//视图的移动，X轴正向移动
        view.setTranslationY(50);
        //        view.setX(50);//相对于父视图左上角坐标系，X轴坐标为480像素
        //        view.setY(50);
        //        textView.setText("getScrollX=="+view.getScrollX()+"getScrollY=="+view.getScrollY());//getScrollX()和getScrollY()就，这两个方法就是获取滑动后屏幕相对于未滑动之前初始位置的相对位移。
    }
}
