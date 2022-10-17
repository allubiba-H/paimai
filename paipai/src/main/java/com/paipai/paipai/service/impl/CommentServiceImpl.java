package com.paipai.paipai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paipai.paipai.entity.Comment;
import com.paipai.paipai.mapper.CommentMapper;
import com.paipai.paipai.service.ICommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Habibulla
 * @since 2022-09-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
