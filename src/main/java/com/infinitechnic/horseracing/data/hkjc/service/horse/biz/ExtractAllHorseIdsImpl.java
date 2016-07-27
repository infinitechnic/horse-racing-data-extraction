package com.infinitechnic.horseracing.data.hkjc.service.horse.biz;

import com.infinitechnic.horseracing.data.hkjc.dao.horse.HorseDao;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Horse;
import com.infinitechnic.horseracing.data.hkjc.entity.horse.Record;
import com.infinitechnic.horseracing.data.hkjc.entity.jockey.Jockey;
import com.infinitechnic.horseracing.data.hkjc.entity.trainer.Trainer;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceFailureException;
import com.infinitechnic.horseracing.data.hkjc.exception.ServiceRenderException;
import com.infinitechnic.util.DateUtil;
import com.infinitechnic.util.NumberUtil;
import com.infinitechnic.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExtractAllHorseIdsImpl implements ExtractAllHorseIds {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REGEXP_LINK = "^<a href=\"(.+)\" class=\"table_text\">.+</a>$";
    private static final Pattern PATTERN_LINK = Pattern.compile(REGEXP_LINK);

    private static final String REGEXP_HORSE = "^<a href=\"horse.asp\\?HorseNo=(.+)\" class=\"title_text\">.+</a>$";
    private static final Pattern PATTERN_HORSE = Pattern.compile(REGEXP_HORSE);

    @Override
    public List<String> render() throws ServiceRenderException {
        List<String> ids = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.hkjc.com/chinese/racing/SelectHorse.asp").get();
            Elements elements = doc.select("table[class=bigborder]");
            Elements liElements = elements.get(3).select("li");
            for (int i=0; i<liElements.size(); i++) {
                ids.addAll(extractHorseIds(liElements.get(i)));
            }
        } catch (Exception e) {
            throw new ServiceFailureException(e);
        }
        return ids;
    }

    private List<String> extractHorseIds(Element li) throws IOException {
        List<String> ids = new ArrayList<>();
        Matcher matcher = PATTERN_LINK.matcher(li.html());
        if (matcher.matches()) {
            Document doc = Jsoup.connect(String.format("http://www.hkjc.com/chinese/racing/%s", matcher.group(1))).get();
            Elements tables = doc.select("table[class=bigborder]");
            Elements idElements = tables.get(1).select("li");
            for (int i=0; i<idElements.size(); i++) {
                Matcher horseMatcher = PATTERN_HORSE.matcher(idElements.get(i).html());
                if (horseMatcher.matches()) {
                    ids.add(horseMatcher.group(1));
                }
            }
        }
        return ids;
    }
}
