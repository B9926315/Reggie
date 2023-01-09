package com.BYX.service.impl;

import com.BYX.mapper.AddressBookMapper;
import com.BYX.pojo.AddressBook;
import com.BYX.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author Planck
 * @Date 2023-01-07 - 19:34
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
