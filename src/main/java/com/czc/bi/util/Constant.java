package com.czc.bi.util;

/**
 * Created by Administrator on 2017/9/28.
 */
public class Constant {

    // 支付宝请求配置
    public static final String GATEWAT;
    public static final String PPID;
    public static final String PRIVATE_KEY;
    public static final String FORMAT;
    public static final String CHARSET;
    public static final String ALIPAY_PUBLIC_KEY;
    public static final String SIGN_TYPE;

    // 沙盒配置
//    static {
//        GATEWAT = "https://openapi.alipaydev.com/gateway.do";
//        PPID = "2016090900470722";
//        PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDOt8YcIecpWQluYSRc3O6sR3F/P15SMNV3+3273bOuuP1qV2DYIiykZ2SqJqT3KlwT/+fXGc20DFDLUvebBA/NKCPEKgRAT/ZxCrDQ/+6XcjBfZKsv0T2BOmmVL7AIiBWO8M5ZRq+d+n5FhroHN4BFiMR2rwPoZG/1AxyKfsmeKq6RbX3xKcKQrCrVT/bCuN17F1AyAUT6jxclTCyErNnsfpuixIti9IRh3CiGN2n75UWDxmTPGKL1HitrZHHkMx3CE59pYv0BPTAQC7hSRQZQGPOYwgKuv8scn1KWL+A46eWe0BahaqOFtChb9NHfm/n33cMXx+LGfv/jrP8NK+kLAgMBAAECggEBAKYdF53TwKaBXgrNFSNqhoD6iEHw8iKqC2B3P/qPlax6C2pA/RmxnJ4gV8xDcZ0M9SE9K1i2644bt3EsIrz27pu1/cfM282ydB8ysBPDFj+tCyIdC1NJmVsXTXEM+TBh6rEK4UbsfoM8n+vO7/6Mow89cGLPERzWWQFSlsTUTcBCqTS5BBWGfh5gwcTK7PKc0b3KSLgb3ZH6CNmBBKWA1sOlJXiF0YSejymlRnOvRL6+El8ivwYW6iU2+COIS3eU53oBTSHQL4zFn93rw8Wpa0iXdgsnrsTPNmb1m5KSq+SsCHDGxNkSx9Moeao0TApzqEmH4MhvdyDKd9jjRFgrtgECgYEA+gQHfLme3QXcQeuoqKNk+n7/6rFOUDGeEUtlrx6IfzAoM0nmbKUmTy0TSmepfFNpFz1tPQeH+TAhANFnm0raH6m25ZeTIBThxDOC6QOenndTqp0veLKPiLel7HyzU2hiNePpyvFgwVdDQ+EQbCRALPKalr9idbftQRARbrkQcSECgYEA06pv4N6D6wB0NgokIJiwTWibvWDJ3148ECOpcdFKcahoaEfbiyZCJ1J5OXZz1RcvhuoKxBr9bo/S1Ie1SgTy9HTLmZD1scW3VILrMDWUtP2pfJ0B+5gZukGbAVb6IIO5+5PINTYMch23uRtaqhpnBrSHrsY2/uy6nRgm87WtWKsCgYABT/QaG39R+a3mSq5f9DubdP8y5CtdEGWcAaFPAgIFaONNIZ4sA9HnqHzpbxHcrTNoA9KG93snO7Fwp6i7j/EFKYbkPZ9wE6v7s6mEP5idjFLMGncdUxKdzRj53M20KmAZmFCp2AqB+UtJF+1WI/qSQdO95tNFeumr5cGxqxcDAQKBgFgcHcBcwEtGABoytIFBLjq67gGyVbZ+0r/hKHrxpWyUrwmhx/kvruARi9bowSUWJrmhmWOy3IZ2pkvERioOz1gFem9tkWk0JbGvYdnKpCGHalMuRJIH6cXH4492Nl7slrHr/a5w1lN2nXEXuvEOVo7K/aQ6bNBHmRMl56kRmpxLAoGBALDicSvODlNiq1j5fYsAwqoJp0Qg9ZHpowS34x1OfocMUbylY5hcuJLeOTe2XKmkmr0nOL+qHkYUkD4ESV5sWsU68Z8lkYaIGNv643kLR40wO40AC/Njs8TmE9wsntZvyOSBzmaBOfWJl3NiDvno4XW70wnZOZWXidJb2yd/ujxP";
//        FORMAT = "json";
//        CHARSET = "GBK";
//        ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsD8BZabPh+1VWUnlPCWOdvMkzGeev2vkE9nTM5F0Q8XL61bIbevlJ664s1Cz9q1+F/vqORe8LYQYRBQF9HZQqbF+UAhUExeU7qIdtER0nsiSUWpsoR955jGLXcm9R9QVYGa7NIhIkiqgrMg9sexnYfpuH6xkrvpc9zhPOsT3sKxNGwvRb4OTAyvuuuZDsyeSmtZsUBaq9X7eYzRs0XacNTUa+Q45vXEdG++Jo80slqisShkILSD+go6VmG3QTfx45hXwQOwUV186ucAEUaL5/s8rR2sAZTyaEKwiMeEcvebEahe1w76GsKMkjv877S/PrQKKBrN+IjUodpbDWsnzmQIDAQAB";
//        SIGN_TYPE = "RSA2";
//    }

    // 2016101202120719 配置
    static {
        GATEWAT = "https://openapi.alipay.com/gateway.do";
        PPID = "2016101202120719";
        PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX3bzjcN1jYzBjM0LYztcmJ2OD0v+StCzqpdI3TusAFe/EO7d65j4/ba22TCZOi+DOBbs3tCXlRxt1Un6UzKICQBYCZUSfMoaa9IUdXm6E8q/bgyvbWKcb+MvsmbqhbSFyPliUiwsAE8xB/a2h9OJtOCOESTJhHG8Qvv3YqjxiajgV2HWQcGiVMJJgVqcH9m37iNovj97L6Ti5B/CG12xetYWK5I3uKgkdNk83mwdAUUFX2e8q2hyD8bJ7w92dfa2trAdSY8YY1yVmOH5vRqy6/69igYsWZitOwwQn8H1Sm77PszYbISBwX4CVUu/3aydkCUo08Oz2NPYTAiCgj8WxAgMBAAECggEARsRJ/8YZM/YFl0RM0xDXiuzx33zUIT2abKFmquU4dqrCNrQJFNjzisUGHJuxggqGcBqVmih1PDj9X5dYauhMWjYYy3b7GiAGP3DQEtZtM8CPGAAw0J6oCE3QYOll4VEkM8M/rcB5GMkg9mNKpNyjInf1fPBK7Ju+WZRWX788MjNJ4Lk3s/RV/Kls555I359oe01YWOu/VhH0plQMTgf24Y7vmpTRxMKMPMTHmrOkiSj2BqFaGTQYufC1hVyuDGdy/u2CelXCSfsbX5znd8RrKKV79OXSQbBWrVuCsgoeh5jlrYYmcEjjmG7BzNwPprAXftGv5n8XfzMiVRL7QMg2jQKBgQDekJPNOJ56AmKRJJbIB6KMpYLF4awcHR/i/KyvI6Crq6DAGHkrPNWNdLjh37GE6GBdofe5AccWJYoUxuDVaZmTq8fpx/7ptuCsTIDC7NoDOsnlVOZlkA6sb8AGjFI7BA3pMV94eCl5toZ2B/pgxCe5IIUCzuvvQaVgEiFy7KaCtwKBgQCurjmyorYCcuv34uQbAW6Ld0Ea4ek5TucFPhLBng7vfvNUy6qyghUTfeWuD/gzu2OyncWR8DABKax8EO1ueseQPhdwfSwlgeGBQDV22W4BBBLHKGyauk0hqGWLv8ZBsFwuZ51Q9ZWzPRn/OvQL1jEyXrNKpB5oUp7HhLfvjRHy1wKBgQCnZX6FQCyR//E60ZZG5NcxidsOEmpsyssM4pbPM7DI7dF+KeoYxyyntD7KRIE0QcMZKZU0pU2kLp/oeJzqZv6HvxAhmbb0+gOKFBBUi+h9vV3VC0Iu6Wbf8z0ur6O5l5cO6X+xEE/Bp7MCW4XJ+j8WUos4TYHO9kT4OSrHUY77iQKBgGlqXwt8whZMQDcCR+I8RPgJadLNTq/0+9ngEfWiayUBGdusZUEA27M9oOQgYwrlJU9fgyGFL5MkESF2HbM0iUZ1EjQidgfUj8AufZVRtd8LFEH4FUJy96U+OLnTa9OsmJSwlvGKo2Ldlti+PRT/mATrvYtn66nNYmDJqS75lUFVAoGACxZV3BBfPAIZkxv+q64omqo4lp72ytp4dfuCoSBIE51MCnFUxzGYQZfwD9C8fw0Ak6LJn2yDtfxefCdJ1JEGB2W0FrOZz4uHzwb59kZLvrB6g6AdXEc9iasJ8MPcoQYn3iVhYGZBJfpoCZ2z8wK9oO+cZfOKKbQ9YykTBDafCmk=";
        FORMAT = "json";
        CHARSET = "GBK";
        ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAotc1C6bPPN0ScPAZmGN84N3rgZRS2X5ThhX/aKyWUB47tgnTk4dK978XdMV7Ye1LOhO8fTie7TwY4+7eM1Y0U6RWqe3j0wlKb/Ka/PBR1XX9nbSLzgEa/HWC9tjoOWHwInMpDzNrMs/QAYyrFlI/WkB3s+esOiDX8mCCGES27CQEm98V3KHN6P/2izsJ6wqyahGShSdhlAEvmg7ZSDUs+mrWvEJZOrQcpWMwWYnczwsqaLich76NZ9CJV2m9a/GF0J06xWPpA0dDvc6k/M/sgYZDEz8Lq64IjAcqk0Xwv3tqZ1aUnR2lLsyVAAZv5FWuGYH9Zg6NoJp2JHCLhbTCNQIDAQAB";
        SIGN_TYPE = "RSA2";
    }

    // 支付宝-报表-UK
    public static String UK_REPORT_SHOP_INFO_LIST = "QK171030940e6aqv";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_WEEK_SUM = "QK171025xq8h93fs";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_MONTH_SUM = "QK171025t560649g";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_FORWEEK = "QK171025k863e26v";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK = "QK171106873ffwly";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK = "QK171025l0z832ev";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_FORWEEK_SUMMARY = "QK171025r854lj1a";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_FORWEEK = "QK1710256q45gfz4";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS_FORMONTH_SUMMARY = "QK171025c03sh8p4";
    public static String UK_REPORT_YFY_SHOP_USRANALYSIS = "QK17102573r1022d";
    public static String UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD = "QK171024w055awh3";
    public static String UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORWEEK = "QK171024n83s62ea";
    public static String UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS = "QK171024vnw9va74";
    public static String UK_REPORT_YFY_SHOP_PROPERTY = "QK1711019f6d4557";
    public static String UK_REPORT_YFY_SHOP_PROPERTY_AREA = "QK171101gk69jc69";
    public static String UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS = "QK17110221vjfg3r";


    private Constant() {
    }

}
