package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.*;
import com.library.pojo.Book;
import com.library.pojo.Type;
import com.library.pojo.Warehouse;
import com.library.response.*;
import com.library.service.BookService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.library.utils.ResultCodeEnum.requested_resource_no_modified;

/**
*针对表【book】的数据库操作Service实现
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
    implements BookService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private StockinMapper stockinMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Result createBook(BookResponse bookResponse) {
        bookResponse.setTid(typeMapper.selectIdByTname(bookResponse.getTname()));
        Book book = new Book(bookResponse);
        int i = bookMapper.insert(book);

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getBname,book.getBname())
                .eq(Book::getTid,book.getTid())
                .eq(Book::getAuthor,book.getAuthor())
                .eq(Book::getPress,book.getPress())
                .eq(Book::getIntroduce,book.getIntroduce())
                .eq(Book::getPoster,book.getPoster());

        Map data = new HashMap();
        if (i == 0) {
            data.put("tip","创建图书失败");
            return Result.ok(requested_resource_no_modified);
        }else{
            data.put("tip","成功创建图书");
            data.put("book",bookResponse);
            return Result.ok(data);
        }
    }


    @Override
    public Result bookPageSelect(Integer page, Integer pageSize) {

        Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);

        IPage<Map<String, Object>> result = bookMapper.selectMapsPage(mapPage,null);
        List<Map<String, Object>> records = result.getRecords();

        Long count = bookMapper.selectCount(null);
        List list = new ArrayList<>();

        for (Map<String, Object> record : records) {
            Book book = new Book();
            book.setId((Integer) record.get("id"));
            book.setBname((String) record.get("bname"));
            book.setTid((Integer) record.get("tid"));
            book.setAuthor((String) record.get("author"));
            book.setPress((String) record.get("press"));
            book.setBtimes((Integer) record.get("btimes"));
            book.setIntroduce((String) record.get("introduce"));
            book.setPoster((String) record.get("poster"));

            BookResponse bookResponse = new BookResponse(book);
            bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));

            list.add(bookResponse);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页,共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("bookTotal",count);
        data.put("source",list);

        return Result.ok(data);
    }
    @Override
    public Result bookPageSelect1(Integer page, Integer pageSize , Integer tid) {


    if (tid == null) {
        bookPageSelect(page,pageSize);
    }

    Integer id = tid;

    // 构造查询条件
    QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("tid", id);  // 筛选条件，只选择 tid 等于 id 的记录

    // 构造分页对象
    Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);
    IPage<Map<String, Object>> result = bookMapper.selectMapsPage(mapPage, queryWrapper);
    List<Map<String, Object>> records = result.getRecords();

    Integer count = bookMapper.selectBookCountByTid(id);

    List list = new ArrayList<>();

    for (Map<String, Object> record : records) {
        Book book = new Book();
        book.setId((Integer) record.get("id"));
        book.setBname((String) record.get("bname"));
        book.setTid((Integer) record.get("tid"));
        book.setAuthor((String) record.get("author"));
        book.setPress((String) record.get("press"));
        book.setBtimes((Integer) record.get("btimes"));
        book.setIntroduce((String) record.get("introduce"));
        book.setPoster((String) record.get("poster"));

        BookResponse bookResponse = new BookResponse(book);
        bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));

        list.add(bookResponse);
        }

    Map data = new LinkedHashMap();
    data.put("tip", "成功获取第" + page + "页,共" + pageSize + "条数据");
    data.put("page", page);
    data.put("count", pageSize);
    data.put("pageTotal", (int) Math.ceil(count / pageSize));
    data.put("bookTotal", count);
    data.put("source", list);

    return Result.ok(data);
    }

    @Override
    public Result bookPageSelect2(Integer page, Integer pageSize, String BnameOrAuthor) {
        if (BnameOrAuthor == null) {
            bookPageSelect(page, pageSize);
        }

        // 构造查询条件
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("tid").like("bname", BnameOrAuthor).or().like("author", BnameOrAuthor) ;

        // 构造分页对象
        Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);
        IPage<Map<String, Object>> result = bookMapper.selectMapsPage(mapPage, queryWrapper);
        List<Map<String, Object>> records = result.getRecords();

        Integer count = bookMapper.selectBookCountByBnameOrAuthor(BnameOrAuthor);

        List list = new ArrayList<>();

        for (Map<String, Object> record : records) {
            Book book = new Book();
            book.setId((Integer) record.get("id"));
            book.setBname((String) record.get("bname"));
            book.setTid((Integer) record.get("tid"));
            book.setAuthor((String) record.get("author"));
            book.setPress((String) record.get("press"));
            book.setBtimes((Integer) record.get("btimes"));
            book.setIntroduce((String) record.get("introduce"));
            book.setPoster((String) record.get("poster"));

            BookResponse bookResponse = new BookResponse(book);
            bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));

            list.add(bookResponse);
        }

        Map data = new LinkedHashMap();
        data.put("tip", "成功获取第" + page + "页,共" + pageSize + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil(count / pageSize));
        data.put("bookTotal", count);
        data.put("source", list);

        return Result.ok(data);
    }


    @Override
    public Result selectBookById(Integer id) {

        Book book = bookMapper.selectById(id);
        BookResponse bookResponse = new BookResponse(book);
        bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));
        System.out.println(bookResponse);
        Map data = new LinkedHashMap();
        data.put("tip","成功获取指定图书");
        data.put("book",bookResponse);

        return Result.ok(data);
    }

//    @Override
//    public Result selectBookById(Integer id) {
//
//        Product product = productMapper.selectProductById(id);
//
//        List<ProductNameAndValue> productNameAndValues = productMapper.selectProductNameAndValueById(id);
//
//        List<ProductYearAndSalesResponse> productYearAndSalesResponse = productMapper.selectYearAndSalesById(id);
//
//        // 提取年份和销售数量到对应的列表中
//        List<String> gradientBarX = new ArrayList<>();
//        List<String> gradientBarY = new ArrayList<>();
//        for (ProductYearAndSalesResponse response : productYearAndSalesResponse) {
//            gradientBarX.add(response.getYear());
//            gradientBarY.add(response.getSalesCount());
//        }
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功获取指定产品");
//        data.put("gradientBarX",gradientBarX);
//        data.put("gradientBarY",gradientBarY);
//        data.put("pie",productNameAndValues);
//        data.put("product",product);
//
//        return Result.ok(data);
//    }

    @Override
    public Result UpdateBookById(Integer id, String bname, String tname, String author, String press,String introduce,String poster) {
        Book book = new Book();
        book.setId(id);
        book.setBname(bname);
        book.setAuthor(author);
        book.setPress(press);
        book.setIntroduce(introduce);
        book.setPoster(poster);
        book.setTid(typeMapper.selectIdByTname(tname));
        bookMapper.updateById(book);

        Map data = new LinkedHashMap();
        data.put("tip","成功更新图书信息");
        data.put("book",book);
        return Result.ok(data);
    }

    @Override
    public Result deleteBookById(Integer id) {
        bookMapper.deleteById(id);

        Map data = new LinkedHashMap();
        data.put("tip","成功删除图书");

        return Result.ok(data);
    }

    @Override
    public Result getAllBook() {
        List<Book> bookList = bookMapper.getAllBook();
        List<BookResponse> bookResponseList = new ArrayList<>();

        for (Book book : bookList) {
            // 查询对应的类型名称 tname
            String tname = typeMapper.selectTnameById(book.getTid());
            // 创建 BookResponse 对象，包含原始的 Book 属性和额外的 tname 属性
            BookResponse bookResponse = new BookResponse(book);
            // 设置 tname 属性
            bookResponse.setTname(tname);
            // 将 BookResponse 对象添加到列表中
            bookResponseList.add(bookResponse);
        }

        return Result.ok(bookResponseList);
    }

    @Override
    public Result selectBookByTid(Integer tid)  {
        List<Book> bookList = bookMapper.selectBookByTid(tid);
        List<BookResponse> bookResponseList = new ArrayList<>();

        for (Book book : bookList) {
            // 查询对应的类型名称 tname
            String tname = typeMapper.selectTnameById(book.getTid());
            // 创建 BookResponse 对象，包含原始的 Book 属性和额外的 tname 属性
            BookResponse bookResponse = new BookResponse(book);
            // 设置 tname 属性
            bookResponse.setTname(tname);
            // 将 BookResponse 对象添加到列表中
            bookResponseList.add(bookResponse);
        }
        return Result.ok(bookResponseList);
    }

//    @Override
//    @Transactional
//    public Result deleteProductById(Integer id) {
//
//        Map data = new LinkedHashMap();
//        data.put("tip","成功删除产品");
//        countResponse count = new countResponse();
//
//        int orderCount = orderMapper.deleteOrderByProductId(id);
//        count.setCount(orderCount);
//        data.put("orderCount",count);
//
//        int supplyCount = supplyMapper.deleteSupplyByProductId(id);
//        count.setCount(supplyCount);
//        data.put("supplyCount",count);
//
//        int inventoryCount = inventoryMapper.deleteInventoryByProductId(id);
//        count.setCount(inventoryCount);
//        data.put("inventoryCount",count);
//
//        int deleteProduct = productMapper.deleteById(id);
//
//        return Result.ok(data);
//    }

    @Override
    public Result selectWarehouseByBookId(Integer id) {
        List<Warehouse> list = warehouseMapper.selectWarehouseByBid(id);
        return Result.ok(list);
    }

}




