package heapStark.blogCode.JSQLParser;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.junit.Test;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

public class CCJSqlParserManagerTest {

    String sql = "select name,age from test.test";

    @Test
    public void testParser() throws Exception {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectitems = plain.getSelectItems();
        List<String> str_items = new ArrayList<String>();
        if (selectitems != null) {
            for (int i = 0; i < selectitems.size(); i++) {
                str_items.add(selectitems.get(i).toString());
            }
        }
        System.out.println(str_items);
    }
}
