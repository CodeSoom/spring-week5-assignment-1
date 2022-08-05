package com.codesoom.assignment.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ListToDelete {
    @NotNull(message = "아이디 목록은 Null 일 수 없습니다.")
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }
}
