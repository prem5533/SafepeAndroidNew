package com.safepayu.wallet.models.response;

import java.util.Comparator;

public class AllListData {
    public String id;
    public String period;
    public String r_rate;
    public String amount;

    public static Comparator<AllListData> sortByGrpId = (s1, s2) -> {
        int grp_id1 = Integer.parseInt(s1.id);
        int grp_id2 = Integer.parseInt(s2.id);
        return grp_id2 - grp_id1;
    };
}
