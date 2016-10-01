package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

//    public static void main(String args[]) throws Exception {
//        Schema schema = new Schema(3, "greendao");
//        Entity box = schema.addEntity("Box");
//        box.addIdProperty();
//        box.addStringProperty("name");
//        box.addIntProperty("slots");
//        box.addStringProperty("description");
//        new DaoGenerator().generateAll(schema, args[0]);
//    }

    public static void main(String args[]) throws Exception {
        //http://www.ktfootball.com/
        //Schema对象接受2个参数，第一个参数是DB的版本号，通过更新版本号来更新数据库。第二个参数是自动生成代码的包路径。包路径系统自动生成
        Schema schema = new Schema( 11, "com.ktfootball.www.dao");
        // 1: 数据库版本号
        // com.xxx.bean:自动生成的Bean对象会放到/java-gen/com/xxx/bean中

//        schema.setDefaultJavaPackageDao("com.chongwuzhiwu.app.screen.dao");
        // DaoMaster.java、DaoSession.java、BeanDao.java会放到/java-gen/com/xxx/dao中

        // 上面这两个文件夹路径都可以自定义，也可以不设置

        initSideaandb(schema); // 初始化Bean了
        initVcrPath(schema); // 初始化Bean了
//        第一个参数是Schema对象，第二个参数是希望自动生成的代码对应的项目路径。
        new DaoGenerator().generateAll(schema, args[0]);// 自动创建
    }

    private static void initVcrPath(Schema schema) {
        Entity userBean = schema.addEntity("VcrPath");// 表名
        userBean.addIdProperty().autoincrement();
        userBean.addStringProperty("users");
        userBean.addIntProperty("add_scores");
        userBean.addIntProperty("result");
        userBean.addIntProperty("goals");
        userBean.addIntProperty("pannas");
        userBean.addIntProperty("fouls");
        userBean.addIntProperty("flagrant_fouls");
        userBean.addIntProperty("panna_ko");
        userBean.addIntProperty("abstained");
        userBean.addStringProperty("users_b");
        userBean.addIntProperty("add_scores_b");
        userBean.addIntProperty("result_b");
        userBean.addIntProperty("goals_b");
        userBean.addIntProperty("pannas_b");
        userBean.addIntProperty("fouls_b");
        userBean.addIntProperty("flagrant_fouls_b");
        userBean.addIntProperty("panna_ko_b");
        userBean.addIntProperty("abstained_b");
        userBean.addStringProperty("path");
        userBean.addStringProperty("time");
        userBean.addIntProperty("game_type");
        userBean.addStringProperty("user_id");
        userBean.addStringProperty("game_id");
        userBean.addStringProperty("user1_id");
        userBean.addStringProperty("user2_id");
        userBean.addStringProperty("league1_id");
        userBean.addStringProperty("league2_id");
        userBean.addStringProperty("picture");
        userBean.addStringProperty("battle_id");
        userBean.addBooleanProperty("isSuccess");
    }

    private static void initSideaandb(Schema schema) {
        Entity userBean = schema.addEntity("SideAandB");// 表名
        userBean.addIdProperty().autoincrement();
        userBean.addStringProperty("users");
        userBean.addIntProperty("add_scores");
        userBean.addIntProperty("result");
        userBean.addIntProperty("goals");
        userBean.addIntProperty("pannas");
        userBean.addIntProperty("fouls");
        userBean.addIntProperty("flagrant_fouls");
        userBean.addIntProperty("panna_ko");
        userBean.addIntProperty("abstained");
        userBean.addStringProperty("users_b");
        userBean.addIntProperty("add_scores_b");
        userBean.addIntProperty("result_b");
        userBean.addIntProperty("goals_b");
        userBean.addIntProperty("pannas_b");
        userBean.addIntProperty("fouls_b");
        userBean.addIntProperty("flagrant_fouls_b");
        userBean.addIntProperty("panna_ko_b");
        userBean.addIntProperty("abstained_b");
        userBean.addStringProperty("path");
        userBean.addStringProperty("time");
        userBean.addIntProperty("game_type");
        userBean.addStringProperty("user_id");
        userBean.addStringProperty("game_id");
        userBean.addStringProperty("user1_id");
        userBean.addStringProperty("user2_id");
        userBean.addStringProperty("league1_id");
        userBean.addStringProperty("league2_id");
        userBean.addStringProperty("picture");
        userBean.addStringProperty("battle_id");
    }

    private static void initGames(Schema schema) {
        Entity userBean = schema.addEntity("Games");// 表名
        userBean.addIdProperty().autoincrement();
        userBean.addStringProperty("game_id");
        userBean.addStringProperty("game_enter_users_count");
        userBean.addStringProperty("name");
        userBean.addStringProperty("time_start");
        userBean.addStringProperty("time_end");
        userBean.addStringProperty("avatar");
        userBean.addStringProperty("introduction");
        userBean.addStringProperty("location");
        userBean.addStringProperty("ktb");
        userBean.addStringProperty("enter_time_start");
        userBean.addStringProperty("enter_time_end");
        userBean.addStringProperty("place");
        userBean.addStringProperty("country");
        userBean.addStringProperty("city");
    }

    private static void initUsers(Schema schema) {
        Entity userBean = schema.addEntity("Users");// 表名
        userBean.addIdProperty().autoincrement();
        userBean.addStringProperty("user_id");
        userBean.addStringProperty("avatar");
        userBean.addStringProperty("nickname");
        userBean.addStringProperty("email");
        userBean.addStringProperty("phone");
        userBean.addStringProperty("grade");
        userBean.addStringProperty("scores");
        userBean.addStringProperty("power");
        userBean.addStringProperty("ktb");
        userBean.addStringProperty("age");
        userBean.addStringProperty("gender");
    }

    private static void initBags(Schema schema) {
        Entity userBean = schema.addEntity("Bags");// 表名
        userBean.addIdProperty().autoincrement();
        userBean.addStringProperty("name");
        userBean.addStringProperty("code");
    }

    private static void initUserBean(Schema schema) {
        //Entity表示一个实体可以对应成数据库中的表
        //系统自动会以传入的参数作为表的名字，这里表名就是NOTE
        Entity userBean = schema.addEntity("ScreenState");// 表名
        //当然也可以自己设置表的名字，像这样：
//        userBean.setTableName("user"); // 可以对表重命名
//        如果想ID自动增长可以像这样：
        userBean.addIdProperty().autoincrement();
//        userBean.addStringProperty("id").primaryKey().index();// 主键，索引
        userBean.addIntProperty("type");
        userBean.addLongProperty("timestamp");
        userBean.addBooleanProperty("isRrecord");
        userBean.addLongProperty("currentDay");
        userBean.addLongProperty("userpresent");
    }
}
