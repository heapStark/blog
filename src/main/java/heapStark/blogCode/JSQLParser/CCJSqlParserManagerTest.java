package heapStark.blogCode.JSQLParser;

import heapStark.blogCode.proxy.cglib.CglibProxy;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

public class CCJSqlParserManagerTest {
    private final static Logger logger = LoggerFactory.getLogger(CglibProxy.class);
    String sql = "select a.name,a.age from a inner join b where b.name=a.name";
    private final String sql2 ="select distinct (g.bankno) , b.bankname , count(*)   from (select e.corpno,               e.acctno,               e.feeitemno,               e.orgno,               d.corpname,               d.orgname,               e.bankno          from CP_CONTRACTINFO e         INNER join (select distinct t.corpno,                                    t.payaccno acctno,                                    t.feeitemno,                                    corpname,                                    transtype,                                    orgname                      from CP_TRNDETAIL t                      left join cp_corpinfo a on t.corpno = a.corpno                      left join TMS_ORGINFO c on c.orgno = t.orgno                     where t.wrkdate >= '20170101'                       and t.wrkdate <= '20171231'                       and t.transtype = '302'                       and a.status = '00'                    union                    select distinct f.corpno,                                    e.accno acctno,                                    e.feeitemno,                                    corpname,                                    e.transtype,                                    orgname                      from CP_BATCHTRNDETAIL e                      left join CP_BATCHTRN f on e.transtype = f.transtype                                             and e.subnode = f.subnode                                             and e.subdate = f.subdate                                             and e.transno = f.transno                      left join cp_corpinfo a on f.corpno = a.corpno                      left join TMS_ORGINFO c on c.orgno = a.orgno                     where e.wrkdate >= '20170101'                       and e.wrkdate <= '20171231'                       and a.status = '00'                       and e.transtype = '304') d                on e.corpno = d.corpno               and e.acctno = d.acctno               a";
    private  String sql3 = "select distinct (g.bankno) 付款行行号, b.bankname 付款行名称, count(*) 总数  from (select e.corpno,               e.acctno,               e.feeitemno,               e.orgno,               d.corpname,               d.orgname,               e.bankno          from CP_CONTRACTINFO e         INNER join (select distinct t.corpno,                                    t.payaccno acctno,                                    t.feeitemno,                                    corpname,                                    transtype,                                    orgname                      from CP_TRNDETAIL t                      left join cp_corpinfo a on t.corpno = a.corpno                      left join TMS_ORGINFO c on c.orgno = t.orgno                     where t.wrkdate >= '20170101'                       and t.wrkdate <= '20171231'                       and t.transtype = '302'                       and a.status = '00'                    union                    select distinct f.corpno,                                    e.accno acctno,                                    e.feeitemno,                                    corpname,                                    e.transtype,                                    orgname                      from CP_BATCHTRNDETAIL e                      left join CP_BATCHTRN f on e.transtype = f.transtype                                             and e.subnode = f.subnode                                             and e.subdate = f.subdate                                             and e.transno = f.transno                      left join cp_corpinfo a on f.corpno = a.corpno                      left join TMS_ORGINFO c on c.orgno = a.orgno                     where e.wrkdate >= '20170101'                       and e.wrkdate <= '20171231'                       and a.status = '00'                       and e.transtype = '304') d                on e.corpno = d.corpno               and e.acctno = d.acctno";
    @Test
    public void testParser() throws Exception {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Statement select =  parserManager.parse(new StringReader(sql));
        logger.info("{}",select);
        MDC.put("key","value");
        logger.info(MDC.get("key"));
    }
}
