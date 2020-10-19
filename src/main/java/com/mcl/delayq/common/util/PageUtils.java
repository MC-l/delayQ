package com.mcl.delayq.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @auth caiguowei
 * @date 2020/4/28
 */
public final class PageUtils {

    public static boolean isEmpty(IPage page){
        return page == null || CollectionUtils.isEmpty(page.getRecords());
    }

    public static IPage copyProperties(IPage page) {
        if (page == null){
            return new Page();
        }
        IPage copy = new Page();
        copy.setCurrent(page.getCurrent());
        copy.setSize(page.getSize());
        copy.setPages(page.getPages());
        copy.setTotal(page.getTotal());
        return copy;
    }

    public static <T,R> IPage<R> transferDataType(IPage<T> page, Class<R> clazz) {
        IPage iPage = copyProperties(page);
        if (isEmpty(page)){
            return iPage;
        }
        List<R> rs = ObjUtils.copyList(page.getRecords(), clazz);
        iPage.setRecords(rs);
        return iPage;
    }
    public static <T,R> IPage<R> transferDataType(IPage<T> page, Class<R> clazz, ObjUtils.Transfer transfer) {
        IPage iPage = copyProperties(page);
        if (isEmpty(page)){
            return iPage;
        }
        List<R> rs = ObjUtils.copyList(page.getRecords(), clazz, transfer);
        iPage.setRecords(rs);
        return iPage;
    }

    public static <R> IPage<R> returnEmptyIfPageEmpty(IPage page,Class<R> clazz){
        return isEmpty(page) ? copyProperties(page) : transferDataType(page,clazz);
    }
}
