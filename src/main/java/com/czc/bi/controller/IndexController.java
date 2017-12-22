package com.czc.bi.controller;

import com.alipay.api.AlipayClient;
import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.dto.Simple;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.service.AlipayService;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.service.UserService;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
//        String account = (String) SecurityUtils.getSubject().getPrincipal();
//
//        session.setAttribute("account", account);

        String account = (String) session.getAttribute("account");
        if (Constants.isWindowsOs) {
            logger.debug("windows set session never timeout");
            SecurityUtils.getSubject().getSession().setTimeout(180000000l);
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


    @Autowired
    private AlipayService alipayService;

    // 支付宝回调url
    @RequestMapping(value = "/sqs_ret", produces = "text/plain;charset=UTF-8")
    public String ret(HttpServletRequest request, HttpSession session) {
        String account = request.getParameter("account");
        // account not null, silent login
        if (account == null) {
            String authCode = request.getParameter("auth_code");
            String userid = alipayService.getUseridByAuthCode(authCode);
            if (userid == null) {
                return "404";
            }
            // get account by userid
            account = userService.getAccountByUserid(userid);
            if (account == null) {
                return "binding";
            } else {
                // auto login
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("alipay", "ORAYa0e874ac300c");
                // 获取shiro验证器
                Subject subject = SecurityUtils.getSubject();
                try {
                    // 将token交给shiro验证
                    subject.login(usernamePasswordToken);//完成登录
                    logger.debug(String.format("用户[%s]通过验证,准备跳转主页", account));
                    session.setAttribute("account", account);
                    return "redirect:shouye";
                } catch (Exception e) {
                    return "403";
                }
            }
        }
        // param has account, insert
        else{
            String app_auth_code = request.getParameter("app_auth_code");
            ShopToken shopToken = userService.alipayOpenAuth(app_auth_code);
            String s = userService.saveUser(shopToken);
            if(s != null){
                System.out.println(s);
                return "403";
            }
            return "loginPage";
        }
    }

    @RequestMapping("/bindAccount")
    public String bindAccount() {
        return "bindAccount";
    }
}
