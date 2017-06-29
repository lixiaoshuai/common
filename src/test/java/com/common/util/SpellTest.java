package com.common.util;

import com.common.util.Spell;

public class SpellTest {

	  //测试
    public static void main(String[] args) {
        System.out.println(Spell.getPinYin("李"));
        System.out.println(Spell.getPinYinHeaderChar("李小").toUpperCase());
    }
}
