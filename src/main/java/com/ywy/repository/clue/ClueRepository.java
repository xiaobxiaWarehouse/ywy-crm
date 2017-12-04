package com.ywy.repository.clue;

import com.ywy.entity.clue.CrmClueJob;
import com.ywy.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClueRepository extends BaseRepository<CrmClueJob> {

    public long insert(CrmClueJob t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }
}
