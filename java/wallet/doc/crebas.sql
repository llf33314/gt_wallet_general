/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/11/17 14:51:07                          */
/*==============================================================*/


drop table if exists t_wallet_api_log;

drop table if exists t_wallet_bank;

drop table if exists t_wallet_company;

drop table if exists t_wallet_individual;

drop table if exists t_wallet_member;

drop table if exists t_wallet_money;

drop table if exists t_wallet_pay_order;

/*==============================================================*/
/* Table: t_wallet_api_log                                      */
/*==============================================================*/
create table t_wallet_api_log
(
   id                   int(11) not null auto_increment comment '主键',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   params_json          varchar(1000) comment '请求参数',
   type                 int(5) comment '接口类型',
   result               varchar(1000) comment '返回结果',
   ctime                timestamp not null comment '创建时间',
   status               int(5) default 0 comment '状态',
   msg                  varchar(100) comment '描述',
   primary key (id)
);

alter table t_wallet_api_log comment '调用第三方api接口日志';

/*==============================================================*/
/* Table: t_wallet_bank                                         */
/*==============================================================*/
create table t_wallet_bank
(
   id                   int(11) not null auto_increment comment '主键',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   member_num           varchar(50) not null default '001' comment '会员账号',
   cardNo               varchar(100) comment '银行卡号',
   phone                varchar(100) comment '银行预留手机',
   card_check           int(5) default 2 comment '绑卡方式',
   identityType         int(5) default 1 comment '证件类型',
   identityNo           varchar(100) comment '证件号',
   validate             varchar(50) comment '信用卡必填，格式为年月，
            有效期，格式为年月如2103',
   cvv2                 varchar(50) comment 'CVV2',
   is_safe_card         boolean default false comment '是否为安全卡',
   union_bank           varchar(50) comment '支付行号',
   tranceNum            varchar(50) comment '流水号',
   transDate            varchar(10) comment '申请时间',
   bank_name            varchar(50) comment '银行名称',
   bank_ode             varchar(10) comment '银行代码',
   card_type            int(5) default 1 comment '银行卡类型1 储蓄卡2 信用卡',
   card_class           int(5) default 1 comment '1：个人银行卡 2：对公账号',
   card_lenth           int(5) default 0 comment '卡片长度',
   card_state           int(5) default 1 comment '状态（1：有效；0：无效）',
   name                 varchar(100),
   primary key (id)
);

alter table t_wallet_bank comment '银行卡';

/*==============================================================*/
/* Table: t_wallet_company                                      */
/*==============================================================*/
create table t_wallet_company
(
   id                   int(11) not null auto_increment comment '主键',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   member_num           varchar(50) not null default '001' comment '会员账号',
   do_business_url      varchar(100) comment '营业执照',
   license_url          varchar(100) comment '许可证',
   identitycard_url_1   varchar(100) comment '身份证正面',
   identitycard_url_2   varchar(100) comment '身份证反面',
   company_name         varchar(100) comment '企业名称',
   company_address      varchar(225) comment '地址',
   business_license     varchar(100) comment '营业执照号',
   organization_code    varchar(100) comment '组织机构代码',
   telephone            varchar(12) comment '联系电话',
   legal_name           varchar(30) comment '法人姓名',
   identity_type        int(6) comment '证件类型',
   legalIds             varchar(50) comment '法人证件号码',
   account_no           varchar(50) comment '企业对公账户',
   parent_bank_name     varchar(50) comment '银行名称',
   bank_ctiy_no         varchar(50) comment '开户行地区代码',
   bank_name            varchar(50) comment '开户行支行名',
   union_bank           varchar(50) comment '支付行号',
   primary key (id)
);

alter table t_wallet_company comment '企业会员明细';

/*==============================================================*/
/* Table: t_wallet_individual                                   */
/*==============================================================*/
create table t_wallet_individual
(
   id                   int(11) not null auto_increment comment '主键',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   member_num           varchar(50) not null default '001' comment '会员账号',
   name                 varchar(50) not null default '未知' comment '姓名',
   address              varchar(225) not null comment '地址',
   country              varchar(50) not null default '中国' comment '国家',
   province             varchar(50) not null default '广东省' comment '省份',
   area                 varchar(50) not null default '深圳市' comment '县市',
   identity_checked     boolean not null default false comment '是否进行实名认证true:是 false:否',
   identityCardNo       varchar(50) not null comment '身份证号码',
   pay_fail_amount      int(8) not null default 0 comment '支付失败次数',
   realNameTime         varchar(30) not null default '1970-01-01 00:00:00' comment '实名认证时间',
   remark               varchar(30) comment '备注',
   source               int(8) not null default 0 comment '访问终端1:Mobile 2:PC',
   identitycard_url_1   varchar(100) comment '身份证正面',
   identitycard_url_2   varchar(100) comment '身份证反面',
   primary key (id)
);

alter table t_wallet_individual comment '个人会员';

/*==============================================================*/
/* Table: t_wallet_member                                       */
/*==============================================================*/
create table t_wallet_member
(
   id                   int(11) not null auto_increment comment '主键',
   member_id            int(11) not null comment '(商家id或会员id)',
   member_type          int(11) not null default 1 comment '会员类型(1:个人会员 2:企业会员)',
   member_num           varchar(50) not null default '001' comment '会员账号',
   external_num         varchar(100) not null default 'external001' comment '会员账号(第三方平台账号)',
   pay_pass             varchar(50) not null default '123456' comment '支付密码',
   phone                varchar(100) not null default '12345678900' comment '手机号码',
   is_binding_phone     int(5) not null default 0 comment '是否已绑定手机(0:未绑定,1:已绑定)',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   status               int(5) not null default 0 comment '会员状态(-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)',
   memberinfo_json      varchar(1000) default 'no data' comment '会员信息',
   member_class         int(5) default 1 comment '内部会员类型(1：商家id，2：会员id)',
   set_pay_pwd          boolean default false comment '是否已设置支付密码true:是 false:否',
   registerIp           varchar(30) comment '创建ip',
   primary key (id)
);

alter table t_wallet_member comment '多粉钱包会员';

/*==============================================================*/
/* Table: t_wallet_money                                        */
/*==============================================================*/
create table t_wallet_money
(
   id                   int(11) not null auto_increment comment '主键',
   bus_id               int(11) not null comment '商家id',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   sys_order_no         varchar(50) comment '系统订单号',
   goods_type           int(5) default 0 comment '商品类型',
   goods_no             varchar(50) comment '商户系统商品编号',
   tradeCode            varchar(50) default '3001' comment '业务码',
   amount               decimal(10,2) default 0.00 comment '订单金额',
   fee                  decimal(10,2) comment '手续费',
   validate_type        int(5) default 0 comment '交易验证方式',
   ctime                timestamp not null comment '订单生成时间',
   pay_method           varchar(100) comment '支付方式',
   bankCardNo           varchar(50) comment '银行卡号/账号',
   bankCardPro          int(5) default 0 comment '银行卡/账户属性',
   industry_code        int(5) default 23 comment '行业代码',
   industry_name        varchar(50) default '快消品行业' comment '行业名称',
   visit_source         int(5) default 1 comment '访问终端类型',
   goods_summary        varchar(225) comment '摘要',
   extendInfo           varchar(225) comment '扩展信息',
   status               varchar(20) comment '支付状态 成功：success 进行中：pending 失败：fail',
   pay_fail_message     varchar(100) comment '支付失败信息',
   account_set_no       varchar(50) comment '账户集编号',
   withdraw_type        varchar(10) default 'T0' comment '提现方式',
   external_order_no    varchar(50) comment '云账号订单',
   primary key (id)
);

alter table t_wallet_money comment '提现记录表';

/*==============================================================*/
/* Table: t_wallet_pay_order                                    */
/*==============================================================*/
create table t_wallet_pay_order
(
   id                   int(11) not null auto_increment comment '主键',
   bus_id               int(11) not null comment '商家id',
   w_member_id          int(11) not null comment '多粉钱包会员id',
   member_id            int(11) comment 'member表会员id',
   payer_id             varchar(50) comment '商户系统用户标识',
   sys_order_no         varchar(50) comment '系统订单号',
   goods_type           int(5) default 0 comment '商品类型',
   goods_no             varchar(50) comment '商户系统商品编号',
   tradeCode            varchar(50) default '3001' comment '业务码',
   amount               decimal(10,2) default 0.00 comment '订单金额',
   fee                  decimal(10,2) comment '手续费',
   validate_type        int(5) default 0 comment '交易验证方式',
   ctime                timestamp not null comment '订单生成时间',
   pay_method           varchar(100) comment '支付方式',
   goods_name           varchar(50) comment '商品名称',
   goods_desc           varchar(100) comment '商品描述',
   industry_code        int(5) default 23 comment '行业代码',
   industry_name        varchar(50) default '快消品行业' comment '行业名称',
   visit_source         int(5) default 1 comment '访问终端类型',
   goods_summary        varchar(225) comment '摘要',
   extendInfo           varchar(225) comment '扩展信息',
   status               varchar(20) comment '支付状态 成功：success 进行中：pending 失败：fail',
   pay_fail_message     varchar(100) comment '支付失败信息',
   external_no          varchar(50) comment '多粉钱包订单号',
   tradeNo              varchar(50) comment '交易编号',
   cert_pay_order_no    varchar(50) comment '移动认证支付订单号',
   order_datetime       varchar(50) comment '移动认证支付订单生成时
            间',
   pay_code             varchar(20) comment 'POS 支付的付款码',
   we_chat_aapinfo      varchar(225) comment '微信支付信息(微信app 支付必传)',
   pay_info             varchar(225) comment '扫码支付信息/ JS 支付串信息  JS 支付必传;
            微信公众号JS 支付：返回
            json 字符串',
   needpassword         varchar(20) comment '新移动快捷支付支付密码标记',
   mobile_flag          int(5) comment '新移动快捷支付短信标记',
   sys_refund_no        varchar(50) comment '退款订单号',
   refund_amount        decimal(10,2) comment '退款总金额',
   refund_feeamount     decimal(10,2) comment '手续费退款金额',
   refund_external_no   varchar(50) comment '钱包退款单号',
   primary key (id)
);

alter table t_wallet_pay_order comment '钱包支付记录';

