package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.*;
import com.library.pojo.Book;
import com.library.pojo.Warehouse;
import com.library.response.*;
import com.library.service.BookService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public Result bookPageSelect3(Integer page, Integer pageSize) {//随机推荐36本
        // 计算 OFFSET
        int offset = (page - 1) * pageSize;

        // 随机查询 36 条记录
        List<Book> allRecords = bookMapper.getRandomBook();

        // 获取总记录数
        Long count = (long) allRecords.size();  // 假设总数是 36

        // 进行分页，使用 subList 获取指定范围的记录
        List<Book> records = allRecords.stream()
                .skip(offset)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<BookResponse> list = new ArrayList<>();

        for (Book book : records) {
            BookResponse bookResponse = new BookResponse(book);
            bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));
            list.add(bookResponse);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取第" + page + "页, 共" + pageSize + "条数据");
        data.put("page", page);
        data.put("count", pageSize);
        data.put("pageTotal", (int) Math.ceil(count / (double) pageSize));
        data.put("bookTotal", count);
        data.put("source", list);

        return Result.ok(data);
    }

    @Override
    public Result selectBookById(Integer id) {

        Book book = bookMapper.selectById(id);
        BookResponse bookResponse = new BookResponse(book);
        bookResponse.setTname(typeMapper.selectTnameById(book.getTid()));

        List<BookNameAndValue> bookNameAndValues = bookMapper.selectBookNameAndValueById(id);

        // 获取当前日期的年份和月份
        LocalDate currentDate = LocalDate.now();

        List<String> yearMonths = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // 计算当前日期前第i个月的年份和月份
            LocalDate date = currentDate.minusMonths(i);
            int year = date.getYear();
            int month = date.getMonthValue();
            // 格式化成"yyyy-MM"的字符串
            String yearMonth = String.format("%d-%02d", year, month);
            yearMonths.add(yearMonth);
        }

        // 提取月份和借阅数量到对应的列表中
        List<String> gradientBarX = new ArrayList<>();
        List<String> gradientBarY = new ArrayList<>();

        //  获取每个月份每个图书的借阅量数据，并加入source
        for (int j = 0; j < yearMonths.size(); j++) {
                // 根据产品ID和月份查询借阅数据
                Integer order = historyMapper.selectOrdersByYearMonthAndBookId( j,id);

                gradientBarX.add(yearMonths.get(j));
                gradientBarY.add(String.valueOf(order));
        }
        Collections.reverse(gradientBarX);
        Collections.reverse(gradientBarY);

        Map data = new LinkedHashMap();
        data.put("tip","成功获取指定图书");
        data.put("gradientBarX",gradientBarX);
        data.put("gradientBarY",gradientBarY);
        data.put("pie",bookNameAndValues);
        data.put("book",bookResponse);
        System.out.println(data);
        return Result.ok(data);
    }

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

    public Result getRandomBook() {
        List<Book> bookList = bookMapper.getRandomBook();
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
        System.out.println(bookList);
        System.out.println(bookResponseList);
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

    @Override
    public Result selectWarehouseByBookId(Integer id) {
        List<Warehouse> list = warehouseMapper.selectWarehouseByBid(id);
        return Result.ok(list);
    }

}




