package com.ktfootball.app;

/**
 * Created by jy on 16/5/18.
 */
public class Constants {


    public static final String HOST = "http://www.ktfootball.com";
    public static final String KTHOST = HOST + "/apiv2/";
    //    public static final String KTHOST = HOST;
    //更改战队头像、
    public static final String CHANGE_LEAGUE_AVATAR = KTHOST + "users/change_league_avatar";
    //修改个人头像
    public static final String UPLOAD_AVATAR = KTHOST + "users/upload_avatar";
    //更改战队名称
    public static final String CHANGE_LEAGUE_NAME = KTHOST + "users/change_league_name";
    //意见反馈
    public static final String FEEDBACK = KTHOST + "users/feedback";
    //更新个人信息
    public static final String PROFILE = KTHOST + "users/profile";
    //退出战队
    public static final String LEAVE_LEAGUE = KTHOST + "users/leave_league";
    //获取班级数据
    public static final String GET_CLUB_SCHOOL_CLASS_DATA = KTHOST + "offline/get_club_school_class_data?authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8&club_id=";
    //获取token
    public static final String GET_ROLE = KTHOST + "users/get_role";
    //更新学生信息
    public static final String UPDATE_USER_INFO = KTHOST + "school_class/update_user_info";
    //用户的APP课程列表
    public static final String USER_APP_CARTOONS = KTHOST + "study/user_app_cartoons";
    //APP课程详情
    public static final String APP_CARTOON = KTHOST + "study/app_cartoon";
    //用户全部APP课程学习情况
    public static final String USER_APP_CARTOONS_STUDY = KTHOST + "study/user_app_cartoons_study";
    //APP课程列表
    public static final String APP_CARTOONS = KTHOST + "study/app_cartoons";
    //添加到我的课程列表(post)
    public static final String ADD_TO_USER_APP_CARTOONS = KTHOST + "study/add_to_user_app_cartoons";
    //完成学习
    public static final String STUDY_FINISHED = KTHOST + "study/study_finished";
    //自我评分(post)
    public static final String STUDY_SELF_SCORE_EVALUATION = KTHOST + "study/study_self_score_evaluation";
    //城市列表(get)
    public static final String GET_CITYS = KTHOST + "bc_orders/get_citys";
    //城市列表(get)
    public static final String GET_CITY_PRICE = KTHOST + "bc_orders/get_city_price";
    //提交订单(post)
    public static final String CREATE_ORDER = KTHOST + "bc_orders/create_order";
    //包场赛事支付成功call_back(支付宝)(post)
    public static final String PAYMENT_CALLBACK = KTHOST + "bc_orders/payment_callback";
    //订单列表(get)
    public static final String USER_BC_ORDERS = KTHOST + "bc_orders/user_bc_orders";
    //订单详情(get)
    public static final String BC_ORDER_DETAIL = KTHOST + "bc_orders/bc_order_detail";
    //明星列表(get)
    public static final String STAR_USERS = KTHOST + "stars/star_users";
    //明星信息(get)
    public static final String STAR_USER_PROFILE = KTHOST + "stars/star_user_profile";
    //明星评论过的视频(get)
    public static final String COMMENTED_VIDEOS = KTHOST + "stars/commented_videos";
    //获取明星推送库中推送的视频(get)
    public static final String GET_PUSH_VIDEOS = KTHOST + "stars/get_push_videos";
    //视频详情(get)
    public static final String VIDEO = KTHOST + "stars/video";
    //订单列表(get)
    public static final String CLUB_BC_ORDERS = KTHOST + "bc_jd/club_bc_orders";
    //明星点评(post)
    public static final String VIDEO_COMMENT = KTHOST + "stars/video_comment";
    //添加评论(用于影片评论页面的添加评论)(post)
    public static final String ADD_COMMENT = KTHOST + "videos/add_comment";
    //回复明星点评(post)
    public static final String VIDEO_COMMENT_REPLY = KTHOST + "stars/video_comment_reply";


    //包场赛事登录(post)
    public static final String LOGIN = KTHOST + "bc_jd/login";
    //比赛地点
    public static final String GAME_LIST = KTHOST + "games/list";
    //气场追踪
    public static final String BAG_TRACE = KTHOST + "games/bag_trace";
    //扫描用户参赛资格(get)(扫描参赛选手)
    public static final String SCAN_USER = KTHOST + "games/scan_user";
    //提交战队(post)(用于2v2扫描一组选手后提交)
    public static final String POST_LEAGUE = KTHOST + "games/post_league";
    //提交战队(post)(用于3v3扫描一组选手后提交)
    public static final String POST_LEAGUE_3V3 = KTHOST + "games/post_league_3v3";
    //对战开始(post)(扫描选手后点击开始按钮)
    public static final String BATTLE_START = KTHOST + "games/battle_start";
    //提交结果(post)(用于比赛结束)
    public static final String POST_RESULT = KTHOST + "games/post_result";
    //上传视频(post)
    public static final String UPLOAD_VIDEO = KTHOST + "games/upload_video";
    //待确定订单详情(get)
    public static final String BC_ORDER_DETAIL_NOT_CONFIRM = KTHOST + "bc_jd/bc_order_detail_not_confirm";
    //待完成订单详情(get)
    public static final String BC_ORDER_DETAIL_UNFINISHED = KTHOST + "bc_jd/bc_order_detail_unfinished";
    //已完成订单详情(get)
    public static final String BC_ORDER_DETAIL_FINISHED = KTHOST + "bc_jd/bc_order_detail_finished";
    //我的赛事(get)(用于裁判在附近可制裁的页面)
    public static final String NEAR_GAMES = KTHOST + "games/club_games";
    //新建比赛(get)(用于新建比赛页面)
    public static final String NEW = KTHOST + "games/new";//http://www.ktfootball.com/apiv2/games/new
    //创建比赛(post)(用于提交新建比赛)
    public static final String CREATE = KTHOST + "games/create";
    //更新赛事(post)(用于裁判更新赛事)
    public static final String UPDATE = KTHOST + "games/update";
    //百度搜索地点(get)(用于新建比赛页面)
    public static final String SEARCH_PLACE_BY_BAIDU = KTHOST + "games/search_place_by_baidu";
    //查询用户所有忙碌日期(get)
    public static final String FIND_USER_BUSY_DATE = KTHOST + "bc_jd/find_user_busy_date";
    //设置或取消忙碌日期(post)
    public static final String SET_USER_BUSY_DATE = KTHOST + "bc_jd/set_user_busy_date";
    //设置或取消忙碌日期(post)
    public static final String COMFIRM_BC_ORDER = KTHOST + "bc_jd/comfirm_bc_order";
    //设置或取消忙碌日期(post)
    public static final String FINISH_BC_ORDER = KTHOST + "bc_jd/finish_bc_order";
    //俱乐部下赛事详情(get)
    public static final String GAME_DETAIL = KTHOST + "club_app/game_detail";
    //用户消息(用于消息栏页面)(get)
    public static final String USER_MESSAGE = KTHOST + "users/user_messages";
    //用户組隊消息(用于消息栏页面)(get)
    public static final String USER_Team_MESSAGE = KTHOST + "users/league_invitations";
    //同樣組隊消息
    public static final String USER_TEAM_AGREE_MESSAGE = KTHOST + "users/agree_invite_league";
    //拒絕組隊消息
    public static final String USER_TEAM_DISAAGREE_MESSAGE = KTHOST + "users/disagree_invite_league";
    //用户约战消息
    public static final String USER_BATTLE_MESSAGE = KTHOST + "users/battle_invitations";
    //同樣y约战消息
    public static final String USER_BATTLE_AGREE_MESSAGE = KTHOST + "users/agree_invite_battle";
    //拒絕约战消息
    public static final String USER_BATTLE_DISAAGREE_MESSAGE = KTHOST + "users/disagree_invite_battle";
    //系統消息已讀
    public static final String RED_MESSAGE = KTHOST + "users/read_message";
    //修改密码
    public static final String CHANGE_PWD = KTHOST + "users/update_password";
    //充值列表
    public static final String RECHARGE_LIST = KTHOST + "orders/recharge_ktb";
    //充值KT币
    public static final String CHONGZHI_KT = KTHOST + "orders/recharge_ktb";
    //支付成功call_back(充值KT币)(post)
    public static final String RECHARG_KEB_BACK = KTHOST + "orders/recharge_ktb_callback";
    //用户粉丝
    public static final String USER_FANS = KTHOST + "users/fans";
    //我的关注
    public static final String USER_FOLLOWERS = KTHOST + "users/followers";


    //============================================== IntentCode =====================================================

    public static final String USER_ID = "user_id";
    public static final String APP_CARTOON_ID = "app_cartoon_id";
    public static final String SUB_NAME = "sub_name";
    public static final String SUPERSTAR_ID = "superstar_id";
    public static final String EXTRA_VIDEOS_ID = "videos_id";
    public static final String EXTRA_SCORES = "scores";

    public static final int PAY_SUCCESS = 1001;
    public static final int TO_PAY_AVTIVITY = 1002;
    public static final int TO_WEEKSELECT_AVTIVITY = 1003;
    public static final int TO_WANTTOMAKE_AVTIVITY = 1004;
    public static final String IS_FIRST = "if_first";


    //用户id
    public static final String GAME = "game";
    public static final String GAME_ID = "game_id";
    public static final String KT_CODE = "kt_code";
    public static final String CAPTURE_CODE = "capture_code";
    public static final String TWO_IMAGEVIEW_CODE = "two_imageview_code";
    public static final String IMAGEVIEW_CODE = "imageview_code";
    public static final String USER_TEAM = "user_team";
    public static final String VEDIOCODE = "vedioCode";
    public static final String LEAGUE1 = "league1";
    public static final String LEAGUE2 = "league2";
    public static final String USER_A = "user_a";
    public static final String USER_B = "user_b";
    public static final String USER_C = "user_c";
    public static final String USER_D = "user_d";
    public static final String USER_E = "user_e";
    public static final String USER_F = "user_f";
    public static final String QICHANG_CODE = "qichang_code";
    public static final String PICTUREPATH = "picturePath";

    public static final int START_MSG = 1;
    public static final int STOP_MSG = 2;
    public static final int UPDATA_LEFT_ADD = 3;
    public static final int UPDATA_RIGHT_ADD = 4;
    public static final String CURRENT_VCR_TIME = "current_vcr_time";
    public static final String LEFT_BALL_GRADE = "left_ball_grade";
    public static final String LEFT_PASS_GRADE = "left_pass_grade";
    public static final String LEFT_X_GRADE = "left_x_grade";
    public static final String RIGHT_BALL_GRADE = "right_ball_grade";
    public static final String RIGHT_PASS_GRADE = "right_pass_grade";
    public static final String RIGHT_X_GRADE = "right_x_grade";
    public static final String VCR_PATH = "vcr_path";
    public static final String PRE_CURRENT_GAME_ID = "current_game_id";//提交比分时数据
    public static final String BATTLE_ID = "battle_id";//提交比分时数据
    public static final String POSTRESULT_BEAN = "PostResult";//提交比分时数据
    public static final String ORDER_MODEL = "order_model";

    public static final String LEFT_USER = "left_user";
    public static final String RIGHT_USER = "right_user";
    public static final String LEFT_GRADE = "left_grade";
    public static final String RIGHT_GRADE = "right_grade";

    public static final int REQUEST_CODE_SCAN = 0x0000;
    public static final String DECODED_CONTENT_KEY = "codedContent";
    public static final String DECODED_BITMAP_KEY = "codedBitmap";


    public static final int CAPTUREACTIVITY_RESULT_OK = 1001;
}
