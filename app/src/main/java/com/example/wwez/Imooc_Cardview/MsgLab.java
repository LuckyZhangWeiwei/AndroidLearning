package com.example.wwez.Imooc_Cardview;

import com.example.wwez.myapplication.R;
import com.sxwz.qcodelib.database.annotate.Id;

import java.util.ArrayList;
import java.util.List;

public class MsgLab {
    public static List<Msg> generateMockList() {
        List<Msg> msgList = new ArrayList<>();
        Msg msg1 = new Msg(1, R.drawable.img01,"打发大师傅","阿斯顿发送到噶速度发我二人发的说法we噶地方撒上的");
        msgList.add(msg1);
        Msg msg2 = new Msg(2, R.drawable.img02,"阿斯顿发生电饭锅","阿斯顿发送到噶速度发大师法师打发斯蒂芬我二人发的说法we噶地方撒上的");
        msgList.add(msg2);
        Msg msg3 = new Msg(3, R.drawable.img03,"爱迪生副总裁徐","阿斯顿发送阿斯顿发生地方到噶速度发我二人发的说法we噶地方撒上的");
        msgList.add(msg3);
        Msg msg4 = new Msg(4, R.drawable.img04,"打发大师去玩儿群翁人情味二傅","阿阿斯蒂芬大师法师打发第三方斯顿发送到噶速度发我二人发的说法we噶地方撒上的");
        msgList.add(msg4);
        Msg msg5 = new Msg(5, R.drawable.img05,"打发大自动发送到发斯蒂芬师傅","阿的官方活动已经有反弹与人体斯顿发送到噶速度发我二人发的说法we噶地方撒上的");
        msgList.add(msg5);
        return msgList;
    }
}
