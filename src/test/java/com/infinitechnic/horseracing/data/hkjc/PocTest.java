package com.infinitechnic.horseracing.data.hkjc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PocTest {
    public static void main(String[] args) throws Exception {
        String REGEXP_RACE_INDEX = "^<a href=\"results.asp\\?racedate=([0-9]{2}/[0-9]{2}/[0-9]{4})\\&amp;raceno=([0-9]{2})\\&amp;venue=([A-Z]{2})\" class=\"htable_eng_text\">(.+)</a>$";
        Pattern PATTERN_RACE_INDEX = Pattern.compile(REGEXP_RACE_INDEX);

        String value = "<a href=\"results.asp?racedate=13/12/2015&amp;raceno=05&amp;venue=ST\" class=\"htable_eng_text\">248</a>";

        Matcher matcher = PATTERN_RACE_INDEX.matcher(value);
        if (matcher.matches()) {
            System.out.println("Matched");
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
        }
    }
}
