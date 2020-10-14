package org.revo.charity.controller.filter.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageable implements Pageable {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort sort = new Sort();

    @Override
    public long getOffset() {
        return this.pageNumber = this.pageSize;
    }

    @Override
    public org.springframework.data.domain.Sort getSort() {
        if (this.sort != null && sort.getOrders() != null && sort.getOrders().size() > 0) {
            List<org.springframework.data.domain.Sort.Order> collect = this.sort.getOrders().stream()
                    .map(itx -> new org.springframework.data.domain.Sort.Order(itx.getDirection(), itx.getProperty()))
                    .collect(Collectors.toList());
            return org.springframework.data.domain.Sort.by(collect);
        }
        return null;
    }


    public CustomPageable next() {
        return new CustomPageable(this.getPageNumber() + 1, this.getPageSize(), this.sort);
    }

    public CustomPageable previous() {
        return this.getPageNumber() == 0 ? this : new CustomPageable(this.getPageNumber() - 1, this.getPageSize(), this.sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return this.hasPrevious() ? this.previous() : this.first();
    }

    @Override
    public Pageable first() {
        return new CustomPageable(0, this.getPageSize(), this.sort);
    }

    @Override
    public boolean hasPrevious() {
        return this.pageNumber > 0;
    }

    @Override
    public String toString() {
        return "{" +
                "pn=" + pageNumber +
                ", ps=" + pageSize +
                ", s=" + sort +
                '}';
    }
}
