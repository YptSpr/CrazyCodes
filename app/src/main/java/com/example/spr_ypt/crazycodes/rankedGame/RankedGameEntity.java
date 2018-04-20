package com.example.spr_ypt.crazycodes.rankedGame;

import java.util.List;

public class RankedGameEntity {


    /**
     * result : null
     * match : {"cd":239027,"cdt":480000,"stat":200,"matchId":9}
     * own : {"uid":"849026413","buffs":{},"debuffs":null,"rid":"849026413","dan":{"name":"进击青铜","id":0,"level":1,"icon":"C97D9519-28CF-86E5-9D78-CB76970360B420171221"},"score":0}
     * opp : {"uid":"490869679","buffs":{},"debuffs":null,"rid":"490869679","dan":{"name":"进击青铜","id":0,"level":1,"icon":"C97D9519-28CF-86E5-9D78-CB76970360B420171221"},"score":0}
     * stage : {"cd":0,"stage":"kill","cdt":0,"stat":1,"desc":"kill to do","newStat":false,"progress":{"max":7000,"crt":0}}
     * stat : 300
     * matching : null
     * ts : 1523154026939
     */

    private ResultBean result;//结束状态
    private MatchBean match;// 当前用户PK状态
    private PlayerBean own;// 用户自己的信息
    private PlayerBean opp;// 用户对手的数据
    private StageBean stage;
    private int stat;//当前用户状态，100表示无状态，200表示正在匹配，300表示正在PK
//    private Object matching;//参数类型
    private long ts;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public MatchBean getMatch() {
        return match;
    }

    public void setMatch(MatchBean match) {
        this.match = match;
    }

    public PlayerBean getOwn() {
        return own;
    }

    public void setOwn(PlayerBean own) {
        this.own = own;
    }

    public PlayerBean getOpp() {
        return opp;
    }

    public void setOpp(PlayerBean opp) {
        this.opp = opp;
    }

    public StageBean getStage() {
        return stage;
    }

    public void setStage(StageBean stage) {
        this.stage = stage;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public static class ResultBean{

        /**
         * danPatch : 0
         * danToast : 胜利 经验140
         * mvps : ["419896168"]
         * dan : {"nextScore":2500,"crtScore":2174,"icon":"EAE37E3C-8CE7-D752-8829-5EA91D7DF15F20171221","name":"奋斗白银2星","id":4,"level":2,"rgb":"#ffc108"}
         * scorePatch : 140
         */

        private int danPatch;
        private String danToast;
        private DanBean dan;
        private int scorePatch;
        private List<MvpBean> mvps;

        public int getDanPatch() {
            return danPatch;
        }

        public void setDanPatch(int danPatch) {
            this.danPatch = danPatch;
        }

        public String getDanToast() {
            return danToast;
        }

        public void setDanToast(String danToast) {
            this.danToast = danToast;
        }

        public DanBean getDan() {
            return dan;
        }

        public void setDan(DanBean dan) {
            this.dan = dan;
        }

        public int getScorePatch() {
            return scorePatch;
        }

        public void setScorePatch(int scorePatch) {
            this.scorePatch = scorePatch;
        }

        public List<MvpBean> getMvps() {
            return mvps;
        }

        public void setMvps(List<MvpBean> mvps) {
            this.mvps = mvps;
        }

        public static class MvpBean{

        }

        public static class DanBean {
            /**
             * nextScore : 2500
             * crtScore : 2174
             * icon : EAE37E3C-8CE7-D752-8829-5EA91D7DF15F20171221
             * name : 奋斗白银2星
             * id : 4
             * level : 2
             * rgb : #ffc108
             */

            private int nextScore;
            private int crtScore;
            private String icon;
            private String name;
            private int id;
            private int level;
            private String rgb;

            public int getNextScore() {
                return nextScore;
            }

            public void setNextScore(int nextScore) {
                this.nextScore = nextScore;
            }

            public int getCrtScore() {
                return crtScore;
            }

            public void setCrtScore(int crtScore) {
                this.crtScore = crtScore;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getRgb() {
                return rgb;
            }

            public void setRgb(String rgb) {
                this.rgb = rgb;
            }
        }
    }

    public static class MatchBean {
        /**
         * cd : 239027
         * cdt : 480000 毫秒
         * stat : 200
         * matchId : 9
         */

        private long cd;// 倒计时 毫秒
        private long cdt;// 总时间
        private int stat;// PK状态，200表示正在PK，300表示结束
        private int matchId;

        public long getCd() {
            return cd;
        }

        public void setCd(long cd) {
            this.cd = cd;
        }

        public long getCdt() {
            return cdt;
        }

        public void setCdt(long cdt) {
            this.cdt = cdt;
        }

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }

        public int getMatchId() {
            return matchId;
        }

        public void setMatchId(int matchId) {
            this.matchId = matchId;
        }
    }

    public static class PlayerBean {
        /**
         * uid : 849026413
         * buffs : {}
         * debuffs : null
         * rid : 849026413
         * dan : {"name":"进击青铜","id":0,"level":1,"icon":"C97D9519-28CF-86E5-9D78-CB76970360B420171221"}
         * score : 0
         */

        private String uid;
        private BuffsBean buffs;
        private BuffsBean debuffs;//是否和buffs相同
        private String rid;
        private DanBean dan;
        private int score;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public BuffsBean getBuffs() {
            return buffs;
        }

        public void setBuffs(BuffsBean buffs) {
            this.buffs = buffs;
        }

        public Object getDebuffs() {
            return debuffs;
        }

        public void setDebuffs(BuffsBean debuffs) {
            this.debuffs = debuffs;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public DanBean getDan() {
            return dan;
        }

        public void setDan(DanBean dan) {
            this.dan = dan;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public static class BuffsBean {
        }

        public static class DanBean {
            /**
             * name : 进击青铜
             * id : 0
             * level : 1
             * icon : C97D9519-28CF-86E5-9D78-CB76970360B420171221
             */

            private String name;
            private int id;
            private int level;
            private String icon;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }

    public static class StageBean{

        /**
         * cd : 0
         * stage : kill
         * cdt : 0
         * stat : 1
         * desc : kill to do
         * newStat : false
         * progress : {"max":7000,"crt":0}
         */

        private int cd;
        private String stage;
        private int cdt;
        private int stat;
        private String desc;
        private boolean newStat;
        private ProgressBean progress;
        private ProgressBean oppProgress;

        public int getCd() {
            return cd;
        }

        public void setCd(int cd) {
            this.cd = cd;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public int getCdt() {
            return cdt;
        }

        public void setCdt(int cdt) {
            this.cdt = cdt;
        }

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isNewStat() {
            return newStat;
        }

        public void setNewStat(boolean newStat) {
            this.newStat = newStat;
        }

        public ProgressBean getProgress() {
            return progress;
        }

        public void setProgress(ProgressBean progress) {
            this.progress = progress;
        }

        public ProgressBean getOppProgress() {
            return oppProgress;
        }

        public void setOppProgress(ProgressBean oppProgress) {
            this.oppProgress = oppProgress;
        }

        public static class ProgressBean {
            /**
             * max : 7000
             * crt : 0
             */

            private int max;
            private int crt;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getCrt() {
                return crt;
            }

            public void setCrt(int crt) {
                this.crt = crt;
            }
        }
    }


}
