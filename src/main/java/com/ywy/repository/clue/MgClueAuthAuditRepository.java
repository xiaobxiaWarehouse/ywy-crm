package com.ywy.repository.clue;

import org.springframework.stereotype.Repository;

import com.ywy.entity.clue.MgClueAuthAudit;
import com.ywy.repository.BaseRepository;
@Repository
public class MgClueAuthAuditRepository extends BaseRepository<MgClueAuthAudit>{
	public long insert(MgClueAuthAudit t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }
}
