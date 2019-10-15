package com.hhdl.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hhdl.dao.TUserDao;
import com.hhdl.model.TUser;
import com.hhdl.service.TUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-03
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUser> implements TUserService {

}
