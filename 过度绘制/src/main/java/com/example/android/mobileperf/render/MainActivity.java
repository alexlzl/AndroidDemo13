/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.mobileperf.render;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 　Android Framework会通过裁剪（Clipping）的方式避免重绘不可见的元素，以此来优化性能。但这一优化对于一些复杂的自定义View无效，如果自定义View重写了onDraw()，系统无法知道View中各个元素的位置和层级关系，也就无法自动省略绘制不可见的元素。Canvas提供了一些特殊的方法，可以用来向Android Framework告知Canvas的哪些部分不可见、不需要绘制。其中最常用的方法是Canvas.clipRect()，可以定义绘制的边界，边界以外的部分不会进行绘制。Canvas.quickReject()可以用来测试指定区域是否在裁剪范围之外，如果要绘制的元素位于裁剪范围之外，就可以直接跳过绘制步骤。
 */
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup activityContainer = (ViewGroup) findViewById(R.id.activity_main_container);

        addButton(ChatumLatinumActivity.class,
                getString(R.string.title_activity_chatum_latinum), activityContainer);

        addButton(DroidCardsActivity.class,
                getString(R.string.title_activity_droid_cards), activityContainer);
        addButton(Main2Activity.class,
                getString(R.string.not_custom), activityContainer);
    }

    private void addButton(final Class destination, String description, ViewGroup parent) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemIntent = new Intent(MainActivity.this, destination);
                startActivity(problemIntent);
            }
        });
        button.setText(description);
        parent.addView(button);
    }
}