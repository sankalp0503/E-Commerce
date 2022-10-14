package com.pintoo.ems;


import com.pintoo.ems.Response.Cursor;
import com.pintoo.ems.utils.CursorUtil;

import java.io.IOException;

class DemoApplicationTests {


        public static void main(String[] args) throws IOException {
            Cursor cursor = new Cursor(0,2);
            String en  = CursorUtil.getEncoded(cursor);
            System.out.println( en);
            Cursor cursor1 = CursorUtil.getCursor(en);
            System.out.println( cursor1);
        }
    }

