package com.dmoffat.website;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * @author dan
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Jsoup.clean("<script>Hello world!</script>This is great <a href='http://google.com'>dsadassad</a>", Whitelist.simpleText()));
    }
}
