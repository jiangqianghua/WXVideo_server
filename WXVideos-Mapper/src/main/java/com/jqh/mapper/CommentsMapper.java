package com.jqh.mapper;

import com.jqh.pojo.Comments;
import com.jqh.utils.MyMapper;
import com.jqh.vo.CommentsVo;

import java.util.List;

public interface CommentsMapper extends MyMapper<Comments> {

    //select c.*, u.face_image as face_image , u.nickname from comments c left join users u on c.from_user_id = u.id     where c.video_id = '1807081YAXYZFFFW' order by c.create_time desc;
    public List<CommentsVo> queryComments(String videoId);
}