CREATE DATABASE IF NOT EXISTS wxvideos default charset utf8 COLLATE utf8_general_ci;
create table `users`(
    `id` varchar(64) not null comment '全局唯一id',
    `username` varchar(20) not null comment '用户名称',
    `password` varchar(64) not null comment '密码',
    `face_image` varchar(255) comment '头像连接',
    `nickname` varchar(20) not null comment '昵称',
    `fans_counts` int not null comment '粉丝数量',
    `follow_counts` int not null comment '关注数量',
    `receive_like_counts` int not null comment '关注作品的数量',
    primary key (`id`)
) comment '用户表';

create table `users_fans`(
    `id` varchar(64) not null comment '全局唯一id',
    `user_id` varchar(64) not null comment '用户id',
    `fan_id` varchar(64) not null comment '粉丝id',
    primary key (`id`)
) comment '用户和粉丝关系表';

create table `videos`(
    `id` varchar(64) not null comment '全局唯一id',
     `user_id` varchar(64) not null comment '创建视频的用户id',
     `audio_id` varchar(64) comment '背景音乐的id',
     `video_desc` varchar(128) comment '视频描述',
     `video_path` varchar(255) not null comment '视频地址',
     `video_seconds` float not null comment '视频播放秒数',
     `video_width` int comment '视频宽度',
     `video_height` int comment '视频高度',
     `cover_path` varchar(255) not null comment '封面',
     `like_counts` int not null comment '点赞数量',
     `status` int not null comment '状态 1 发布成功  2 禁止播放',
     `create_time` timestamp not null default current_timestamp,
     primary key (`id`)

) comment '视频表';

create table `users_like_videos`(
    `id` varchar(64) not null comment '全局唯一id',
    `user_id` varchar(64) not null comment '用户id',
    `video_id` varchar(64) not null comment '视频id',
    primary key (`id`)
) comment '用户和视频喜欢的关系表';

create table `users_report`(
    `id` varchar(64) not null comment '全局唯一id',
    `deal_user_id` varchar(64) not null comment '被举报的用户id',
    `deal_video_id` varchar(64) not null comment '被举报的视频id',
    `title` varchar(128) not null comment '举报主题',
    `content` varchar(255) comment '举报内容',
    `userid` varchar(64) not null comment '举报人的id',
    `create_time` timestamp not null default current_timestamp,
    primary key(`id`)
) comment '用户举报表';

create table `bgm`(
   `id` varchar(64) not null comment '全局唯一id',
   `author` varchar(255) not null comment '背景创建者',
   `name` varchar(255) not null comment '歌曲的名称',
   `path` varchar(255) not null comment '歌曲的播放路径',
   primary key(`id`)
) comment '背景音乐表';


create table `search_records`(
    `id` varchar(64) not null comment '全局唯一id',
    `conent` varchar(255) not null comment '搜索词汇',
    primary key(`id`)
) comment '搜索的词汇表';

create table `comments`(
    `id` varchar(64) not null comment '全局唯一id',
    `video_id` varchar(64) not null comment '视频id',
    `from_user_id` varchar(64) not null comment '留言用户id',
    `comment` text  not null comment '留言信息',
    `create_time` timestamp not null default current_timestamp,
    primary key(`id`)
) comment '留言表';







create table `product_info`(
	`product_id` varchar(32) not null,
	`product_name` varchar(64) not null comment '商品名称',
	`product_price` decimal(8,2) not null comment '单价',
	`product_stock` int not null comment '库存',
	`product_description` varchar(64) comment '描述',
	`product_icon` varchar(512) comment '小图',
	`product_status` tinyint(3) not null default '0' comment '商品状态 0正常,1下架',
	`category_type` int not null comment '类目编号',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '更细时间',
	primary key (`product_id`)
) comment '商品表';