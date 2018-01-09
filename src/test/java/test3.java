import javax.xml.bind.SchemaOutputResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/16.
 * @version: V1.0
 */
public class test3 {
    public static void main(String[] args) {
        for (int i = 0; i < 464; i++) {
            System.out.println(
                    String.format("curl http://epaper.maxonmotor.com/cn/data/b9990/b999042/img/normal/bk_%d.jpg",i));
        }

    }
}

