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




insert into bgm values('1','江强华','bgm1','/bgm/bgm1.mp3');
insert into bgm values('2','胡丹','bgm2','/bgm/bgm2.mp3');
insert into bgm values('3','丹丹','bgm3','/bgm/bgm3.mp3');

-- 视频和用户表关联查询
select v.*,u.face_image as face_image,u.nickname as nickname from videos v
    left join users u on u.id = v.user_id
    where 1=1 and v.status = 1
    order by v.create_time desc


insert into search_records values('1','江强华');
insert into search_records values('2','江强华');
insert into search_records values('3','胡丹');
insert into search_records values('4','胡丹');
insert into search_records values('5','胡丹');
insert into search_records values('6','媳妇');

-- 查询热搜词汇,按照频率排序
select conent from search_records group by conent order by count(conent) desc;

-- 修改videos，增加描述
update videos set video_desc = '1' where id = '1806030Z3P0SWACH'

select * from search_records;
-- 清空表
truncate users_like_videos


select v.*,u.face_image as face_image , u.nickname as nickname from videos v
    left join users u on v.user_id = u.id
    where
      v.user_id in (select uf.user_id from users_fans uf where uf.fan_id = '18060277D28X28ZC')
      and v.status = 1
    order by v.create_time desc