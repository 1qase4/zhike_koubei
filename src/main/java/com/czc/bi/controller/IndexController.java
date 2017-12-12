package com.czc.bi.controller;

import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.dto.Simple;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.service.UserService;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.czc.bi.util.AlipayConstant.APPID;
import static com.czc.bi.util.AlipayConstant.REDIRECT_URI;

@Controller
public class IndexController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    @Autowired
    private EtlDateMapper etlDateMapper;

    @Autowired
    private UserService userService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping("/")
    public String welcomePage() {
        return "loginPage";
    }

    @Autowired
    private ShopService shopService;

    @RequestMapping("/shouye")
    public String shouye(HttpSession session, Model model, HttpServletResponse response) throws Exception {
        String account = (String) SecurityUtils.getSubject().getPrincipal();

        session.setAttribute("account", account);

        if (Constants.isWindowsOs) {
            logger.debug("windows set session never timeout");
            SecurityUtils.getSubject().getSession().setTimeout(180000000l);
        }

        // 检查用户Token
        boolean ok = userService.authUserToken(account);
        // 验证不通过, 指导用户去验证
        if (!ok) {
            logger.debug(String.format("用户[%s]token信息验证不通过,指导用户前往验证", account));
            String url =
                    String.format("https://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=%s&account=%s&redirect_uri=%s",
                            APPID,
                            account,
                            REDIRECT_URI
                    );
            response.sendRedirect(url);
            return null;
        }

        List<Simple<String, String>> shops = shopService.selectShopsByMerchant(account);
        model.addAttribute("shops", shops);

        // 根据账号 获取账号的当前数据时间
        EtlDateQuery etlDateQuery = new EtlDateQuery();
        etlDateQuery.setAccount(account);
        String today = etlDateMapper.selectByQuery(etlDateQuery).get(0).getPdate();

        session.setAttribute("today", new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse(today)));
        String weekOfYear = BaseUtil.getWeekOfYear(today);
        session.setAttribute("weekOfYear", weekOfYear);
        return "shouye";
    }

    @Autowired
    private ShopTokenMapper shopTokenMapper;

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }


    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }

    @RequestMapping("/todayProfile")
    public String todayProfile() {
        return "todayProfile";
    }

    @RequestMapping("/dataTrends")
    public String dataTrends() {
        return "dataTrends";
    }

    @RequestMapping("/oldNewUsers")
    public String oldNewUsers() {
        return "oldNewUsers";
    }

    @RequestMapping("/returnLoss")
    public String returnLoss() {
        return "returnLoss";
    }

    @RequestMapping("/clientFeature")
    public String clientFeature() {
        return "clientFeature";
    }

    @RequestMapping("/areaDistributed")
    public String areaDistributed() {
        return "areaDistributed";
    }

    @RequestMapping("/peripheryDistributed")
    public String peripheryDistributed() {
        return "peripheryDistributed";
    }

    @RequestMapping("/modify")
    public String modify() {
        return "modify";
    }


    // 支付宝回调url
    @RequestMapping(value = "/sqs_ret", produces = "text/plain;charset=UTF-8")
    public String ret(
            @RequestParam("app_id") String app_id,
            @RequestParam(value = "account", required = false) String account,
            @RequestParam("app_auth_code") String app_auth_code,
            HttpSession session) {

//        if (account == null) {
//            account = "";
//        }
        //String res = userService.authAlipay(app_id, account, app_auth_code);

        ShopToken shopToken = userService.alipayOpenAuth(app_auth_code);
        boolean b = userService.mergeLoaclAuth(shopToken, session);
        if(b){
            return "shouye";
        }else {
            return "bindingAccount";
        }

    }
}
