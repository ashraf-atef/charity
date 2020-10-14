package org.revo.charity.controller.filter.vm;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.charity.controller.View;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CustomPage<T> extends PageImpl<T> {

    public CustomPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public static <T> CustomPage<T> of(Page<T> page, Pageable pageable) {
        return new CustomPage<>(page.getContent(), pageable, page.getTotalElements());
    }
    public CustomPage(List<T> content) {
        super(content);
    }

    @Override
    @JsonView(View.Page.class)
    public List<T> getContent() {
        return super.getContent();
    }

    @Override
    @JsonView(View.Page.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @JsonView(View.Page.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @JsonView(View.Page.class)
    public int getNumber() {
        return super.getNumber();
    }

    @Override
    @JsonView(View.Page.class)
    public int getSize() {
        return super.getSize();
    }

    @Override
    @JsonView(View.Page.class)
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    @JsonView(View.Page.class)
    public boolean isFirst() {
        return super.isFirst();
    }

    @Override
    @JsonView(View.Page.class)
    public Sort getSort() {
        return super.getSort();
    }
}
