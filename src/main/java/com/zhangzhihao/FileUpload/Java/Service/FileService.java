package com.zhangzhihao.FileUpload.Java.Service;

import com.zhangzhihao.FileUpload.Java.Dao.Query;
import com.zhangzhihao.FileUpload.Java.Model.File;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class FileService extends BaseService<File> {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isMd5Exist(String md5) {
        Query query = new Query(entityManager);
        Object resultMD5 = query.from(File.class)
                .select()
                .whereEqual("MD5", md5)
                .createTypedQuery()
                .getSingleResult();

        return resultMD5 != null;

    }
}
